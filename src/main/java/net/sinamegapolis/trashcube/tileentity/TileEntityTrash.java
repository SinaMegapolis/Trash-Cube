package net.sinamegapolis.trashcube.tileentity;

import com.typesafe.config.ConfigException;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.sinamegapolis.trashcube.block.BlockTrash;
import net.sinamegapolis.trashcube.block.itemblock.ItemBlockCompressedTrash;
import net.sinamegapolis.trashcube.init.ModRegistry;
import net.sinamegapolis.trashcube.structure.StructureList;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

public class TileEntityTrash extends TileEntity implements ITickable{

    public ItemStackHandler trashInventory = new ItemStackHandler(5);
    public static ArrayList<BlockPos> cubeStructure1;
    public int lastBlockIndex=0;

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        compound.setTag("Inventory", trashInventory.serializeNBT());
        compound.setInteger("lastBlockIndex", lastBlockIndex);
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        trashInventory.deserializeNBT(compound.getCompoundTag("Inventory"));
        lastBlockIndex = compound.getInteger("lastBlockIndex");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == EnumFacing.UP || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == EnumFacing.UP)
            return (T)trashInventory;
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        int fullSlots = 0;
        ArrayList<Integer> slotsWithAnItem = new ArrayList<>();
        cubeStructure1 = StructureList.getCubeStructure1(this.getPos());
        boolean isPathBlocked = false;
        for(int n=0; n<5; n++){
            if(trashInventory.getStackInSlot(n) != ItemStack.EMPTY) {
                fullSlots = fullSlots + 1;
                slotsWithAnItem.add(n);
            }
        }
        if(fullSlots==5){
            ArrayList<Integer> IndexList = new ArrayList<>();
            for (int i = 0; i < cubeStructure1.size(); i++) {
                IBlockState state = this.getWorld().getBlockState(cubeStructure1.get(i));
                if(state==Blocks.AIR.getDefaultState())
                    IndexList.add(i);
                if(state != Blocks.AIR.getDefaultState() && state != ModRegistry.CompressedTrashBlock.getDefaultState())
                    isPathBlocked = true;
            }
            if(!isPathBlocked){
            if(IndexList.size()==0){
                if(this.getWorld().setBlockState(cubeStructure1.get(lastBlockIndex), ModRegistry.CompressedTrashBlock.getDefaultState()))
                    lastBlockIndex = lastBlockIndex + 1;
            }else {
                this.getWorld().setBlockState(cubeStructure1.get(IndexList.get(0)), ModRegistry.CompressedTrashBlock.getDefaultState());
                IndexList.remove(0);
            }}else{
                BlockPos pos = this.getPos().east();
                //TODO: Send message to all players
                EntityPlayer player = this.getWorld().getClosestPlayer(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), 25, false);
                player.sendStatusMessage(new TextComponentTranslation("texts.pathblocked"), false);
                for (int slot : slotsWithAnItem) {
                    ItemStack itemStack = trashInventory.getStackInSlot(slot);
                    if(itemStack!= ItemStack.EMPTY)
                        this.getWorld().spawnEntity(new EntityItem(this.getWorld(),pos.getX(),pos.getY(),pos.getZ(),itemStack));
                }
            }
            for(int slot : slotsWithAnItem){
                trashInventory.setStackInSlot(slot, ItemStack.EMPTY);
            }
        }
    }




}
