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
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.sinamegapolis.trashcube.block.BlockTrash;
import net.sinamegapolis.trashcube.init.ModRegistry;
import net.sinamegapolis.trashcube.structure.StructureList;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.logging.Logger;

public class TileEntityTrash extends TileEntity implements ITickable{

    private ItemStackHandler trashInventory = new ItemStackHandler(1);
    private static ArrayList<BlockPos> cubeStructure;
    private boolean isStructureSet = false;
    private boolean saidTheStructureCompletedMessage=false;
    private String moduleName;
    private boolean nModuleAttached;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        compound.setTag("Inventory", trashInventory.serializeNBT());
        compound.setBoolean("isStructureSet", isStructureSet);
        compound.setBoolean("sTSCM", saidTheStructureCompletedMessage);
        if(moduleName!=null)
            compound.setString("moduleName", moduleName);
        compound.setBoolean("doesHaveModule", nModuleAttached);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        trashInventory.deserializeNBT(compound.getCompoundTag("Inventory"));
        isStructureSet = compound.getBoolean("isStructureSet");
        saidTheStructureCompletedMessage = compound.getBoolean("sTSCM");
        moduleName = compound.getString("moduleName");
        nModuleAttached = compound.getBoolean("doesHaveModule");
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
                        if(!saidTheStructureCompletedMessage) {
                            try {
                                EntityPlayer player = this.getWorld().getPlayerEntityByName(moduleName);
                                if (player != null && this.isPlayerNearby(player, this.getPos())) {
                                    player.sendStatusMessage(new TextComponentString(new TextComponentTranslation("texts.structureComplete.line1").getUnformattedComponentText() + " [x:" + this.getPos().getX() + ",y:" + this.getPos().getY() + ",z:" + this.getPos().getZ() + "] " + new TextComponentTranslation("texts.structureComplete.line2").getUnformattedComponentText()), false);
                                    saidTheStructureCompletedMessage = true;
                                }
                            } catch (NullPointerException e) {
                                saidTheStructureCompletedMessage = false;
                            }
                        }
                    } else {
                        this.getWorld().setBlockState(cubeStructure.get(indexList.get(0)), ModRegistry.CompressedTrashBlock.getDefaultState());
                        indexList.remove(0);
                    }
                } else {
                    BlockPos pos = this.getPos().east();
                    try {
                        EntityPlayer player = this.getWorld().getPlayerEntityByName(moduleName);
                        if (player != null && this.isPlayerNearby(player, this.getPos()))
                            player.sendStatusMessage(new TextComponentString(new TextComponentTranslation("texts.pathblocked.part1").getUnformattedComponentText() + " [x:" + this.getPos().getX() + ",y:" + this.getPos().getY() + ",z:" + this.getPos().getZ() + "] " + new TextComponentTranslation("texts.pathblocked.part2").getUnformattedComponentText()), false);
                    }catch(NullPointerException e){

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

    public String getModuleName(){
        return moduleName;
    }
    public void setModuleName(String name){
        moduleName = name;
        this.markDirty();
    }

    public boolean isnModuleAttached() {
        return nModuleAttached;
    }

    public void setnModuleAttached(boolean nModuleAttached) {
        this.nModuleAttached = nModuleAttached;
        this.markDirty();
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    public boolean isPlayerNearby(EntityPlayer player, BlockPos pos){
        BlockPos playerPos = player.getPosition();
        int blockX=pos.getX();
        int blockY=pos.getY();
        int blockZ=pos.getZ();
        int x1=blockX-8,x2=blockX+8;
        int y1=blockY-3,y2=blockY+5;
        int z1=blockZ-8,z2=blockZ+8;
        return x1 <= playerPos.getX() && playerPos.getX() <= x2
                && y1 <= playerPos.getY() && playerPos.getY() <= y2
                && z1 <= playerPos.getZ() && playerPos.getZ() <= z2;
    }
}
