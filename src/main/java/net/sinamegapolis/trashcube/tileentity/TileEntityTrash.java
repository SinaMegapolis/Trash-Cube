package net.sinamegapolis.trashcube.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.sinamegapolis.trashcube.init.ModRegistry;
import net.sinamegapolis.trashcube.structure.StructureList;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileEntityTrash extends TileEntity implements ITickable{

    private ItemStackHandler trashInventory = new ItemStackHandler(1);
    private static ArrayList<BlockPos> cubeStructure;
    private int lastBlockIndex=0;
    private boolean isStructureSet = false;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        compound.setTag("Inventory", trashInventory.serializeNBT());
        compound.setInteger("lastBlockIndex", lastBlockIndex);
        compound.setBoolean("isStructureSet", isStructureSet);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        trashInventory.deserializeNBT(compound.getCompoundTag("Inventory"));
        lastBlockIndex = compound.getInteger("lastBlockIndex");
        isStructureSet = compound.getBoolean("isStructureSet");
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
        boolean isPathBlocked = false;
        for(int n=0; n<1; n++){
            if(trashInventory.getStackInSlot(n) != ItemStack.EMPTY) {
                fullSlots = fullSlots + 1;
                slotsWithAnItem.add(n);
            }
        }
        if(fullSlots==1){
            ArrayList<Integer> indexList = new ArrayList<>();
            //Chooses the structure this Trash Cube will Build
            if(cubeStructure==null)
                isStructureSet = false;
            if(!isStructureSet){
                cubeStructure = StructureList.getRandomCubeStructure(this.getPos());
                isStructureSet = true;
            }
            if(!cubeStructure.isEmpty()) {
                for (int i = 0; i < cubeStructure.size(); i++) {
                    IBlockState state = this.getWorld().getBlockState(cubeStructure.get(i));
                    if (state == Blocks.AIR.getDefaultState() || state.getBlock().isReplaceable(this.getWorld(), cubeStructure.get(i)))
                        indexList.add(i);
                    if (state != Blocks.AIR.getDefaultState() && state != ModRegistry.CompressedTrashBlock.getDefaultState() && !state.getBlock().isReplaceable(this.getWorld(),cubeStructure.get(i) ))
                        isPathBlocked = true;
                }
                if (!isPathBlocked) {
                    if (indexList.isEmpty()) {
                        if (this.getWorld().setBlockState(cubeStructure.get(lastBlockIndex), ModRegistry.CompressedTrashBlock.getDefaultState()))
                            lastBlockIndex = lastBlockIndex + 1;
                    } else {
                        this.getWorld().setBlockState(cubeStructure.get(indexList.get(0)), ModRegistry.CompressedTrashBlock.getDefaultState());
                        indexList.remove(0);
                    }
                } else {
                    BlockPos pos = this.getPos().east();
                    //TODO: Send message to all  nearby players
                    EntityPlayer player = this.getWorld().getClosestPlayer(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), 25, false);
                    if (player != null) {
                        player.sendStatusMessage(new TextComponentTranslation("texts.pathblocked"), false);
                    }
                    for (int slot : slotsWithAnItem) {
                        ItemStack itemStack = trashInventory.getStackInSlot(slot);
                        if (itemStack != ItemStack.EMPTY)
                            this.getWorld().spawnEntity(new EntityItem(this.getWorld(), pos.getX(), pos.getY(), pos.getZ(), itemStack));
                    }
                }
                for (int slot : slotsWithAnItem) {
                    trashInventory.setStackInSlot(slot, ItemStack.EMPTY);
                }
            }
        }
    }




}
