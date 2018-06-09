package net.sinamegapolis.trashcube.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.sinamegapolis.trashcube.block.BlockTrash;
import net.sinamegapolis.trashcube.init.ModRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

public class TileEntityTrash extends TileEntity implements ITickable{

    public ItemStackHandler trashInventory = new ItemStackHandler(1);
    public static BlockPos lastCompressedTrashBlockPos;

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        compound.setTag("Inventory", trashInventory.serializeNBT());
        if(lastCompressedTrashBlockPos!=null) {
            compound.setInteger("x", lastCompressedTrashBlockPos.getX());
            compound.setInteger("y", lastCompressedTrashBlockPos.getY());
            compound.setInteger("z", lastCompressedTrashBlockPos.getZ());
        }
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        trashInventory.deserializeNBT(compound.getCompoundTag("Inventory"));
        lastCompressedTrashBlockPos= new BlockPos(compound.getInteger("x"),compound.getInteger("y"),compound.getInteger("z"));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T)trashInventory;
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        int fullSlots = 0;
        ArrayList<Integer> slotsWithAnItem = new ArrayList<>();
        BlockPos pos;
        for(int n=0; n<1; n++){
            if(trashInventory.getStackInSlot(n) != ItemStack.EMPTY) {
                fullSlots = fullSlots + 1;
                slotsWithAnItem.add(n);
            }
        }
        if(fullSlots==1){
            if(lastCompressedTrashBlockPos == null || lastCompressedTrashBlockPos == new BlockPos(0,0,0))
                pos=this.getPos();
            else
                pos=lastCompressedTrashBlockPos;
            int side = new Random().nextInt(6);
            if(side==1 && this.getWorld().getBlockState(pos.east()) != Blocks.AIR)
                lastCompressedTrashBlockPos = pos.east();
            if(side==2 && this.getWorld().getBlockState(pos.south()) != Blocks.AIR)
                lastCompressedTrashBlockPos = pos.south();
            if(side==3 && this.getWorld().getBlockState(pos.west()) != Blocks.AIR)
                lastCompressedTrashBlockPos = pos.west();
            if(side==4 && this.getWorld().getBlockState(pos.north()) != Blocks.AIR)
                lastCompressedTrashBlockPos = pos.north();
            if(side==5 && this.getWorld().getBlockState(pos.up()) != Blocks.AIR)
                lastCompressedTrashBlockPos = pos.up();
            if(side==6 && this.getWorld().getBlockState(pos.down()) != Blocks.AIR)
                lastCompressedTrashBlockPos = pos.down();
            this.getWorld().setBlockState(lastCompressedTrashBlockPos, ModRegistry.CompressedTrashBlock.getDefaultState());
            for(int slot : slotsWithAnItem){
                trashInventory.setStackInSlot(slot, ItemStack.EMPTY);
            }
        }
    }


}
