package net.sinamegapolis.trashcube.tileentity;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.sinamegapolis.trashcube.block.BlockCompressedTrash;
import net.sinamegapolis.trashcube.block.BlockTrash;
import net.sinamegapolis.trashcube.config.TrashCubeConfig;
import net.sinamegapolis.trashcube.config.TrashCubeConfig;
import net.sinamegapolis.trashcube.init.ModRegistry;
import net.sinamegapolis.trashcube.item.ItemBlacklistModule;
import net.sinamegapolis.trashcube.item.ItemWhitelistModule;
import net.sinamegapolis.trashcube.structure.StructureList;
import net.sinamegapolis.trashcube.utill.TrashCubeItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.logging.Logger;

public class TileEntityTrash extends TileEntity implements ITickable{

    private TrashCubeItemStackHandler trashInventory = new TrashCubeItemStackHandler(TrashCubeConfig.slotAmount);
    private static ArrayList<BlockPos> cubeStructure;
    private boolean isStructureSet = false;
    private boolean saidTheStructureCompletedMessage=false;
    private ItemStack listUpgrade;
    private String moduleName;
    private boolean nModuleAttached;
    private boolean bModuleAttached;
    private boolean wModuleAttached;
    private int progressStep = 0;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        compound.setTag("Inventory", trashInventory.serializeNBT());
        compound.setBoolean("isStructureSet", isStructureSet);
        compound.setBoolean("sTSCM", saidTheStructureCompletedMessage);
        if(moduleName!=null)
            compound.setString("moduleName", moduleName);
        compound.setBoolean("nModuleAttached", nModuleAttached);
        compound.setBoolean("bModuleAttached", bModuleAttached);
        compound.setBoolean("wModuleAttached", wModuleAttached);
        compound.setInteger("progressStep", progressStep);
        if(listUpgrade!=null)
            compound.setTag("listUpgrade", listUpgrade.serializeNBT());
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
        nModuleAttached = compound.getBoolean("nModuleAttached");
        bModuleAttached = compound.getBoolean("bModuleAttached");
        wModuleAttached = compound.getBoolean("wModuleAttached");
        progressStep = compound.getInteger("progressStep");
        if(compound.getCompoundTag("listUpgrade")!=null)
            listUpgrade.deserializeNBT(compound.getCompoundTag("listUpgrade"));
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
        //checks if any of slots is full
        for(int n = 0; n< TrashCubeConfig.slotAmount; n++){
            ItemStack stack = trashInventory.getStackInSlot(n);
            if(listUpgrade!=null && !listUpgrade.isEmpty() && !stack.isEmpty()) {
                if (!checkItemStackForWhitelist(stack)&&listUpgrade.getItem()instanceof ItemWhitelistModule) {
                    dropItem(this.getWorld(), this.getPos().east(), stack);
                    trashInventory.setStackInSlot(n, ItemStack.EMPTY);
                }
                if (checkItemStackForBlacklist(stack) && listUpgrade.getItem()instanceof ItemBlacklistModule) {
                    dropItem(this.getWorld(), this.getPos().east(), stack);
                    trashInventory.setStackInSlot(n, ItemStack.EMPTY);
                }
            }
            //defined again to make sure nothing goes wrong
            stack = trashInventory.getStackInSlot(n);
            if(!stack.isEmpty()) {
                fullSlots = fullSlots + 1;
                slotsWithAnItem.add(n);
            }
        }
        if(fullSlots==TrashCubeConfig.slotAmount && checkIfShouldDump()){
            //checks if there is a chest next to the TrashCube and if so, insert every ItemStack into the chest
            if(this.getWorld().getTileEntity(this.getPos().west())!=null && this.getWorld().getTileEntity(this.getPos().west()).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)){
                TileEntity te = this.getWorld().getTileEntity(this.getPos().west());
                IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                ItemStack stack = ItemHandlerHelper.insertItem(handler, new ItemStack(BlockCompressedTrash.instanceItemBlockCompressedTrash,1,15), false);
                if(!stack.isEmpty())
                    dropItem(world, this.getPos().east(), stack);
            }else {
                ArrayList<Integer> indexList = new ArrayList<>();
                //Chooses the structure this Trash Cube will Build
                if (cubeStructure == null || cubeStructure.isEmpty())
                    isStructureSet = false;
                if (!isStructureSet) {
                    cubeStructure = StructureList.getRandomCubeStructure(this.getPos());
                    isStructureSet = true;
                }
                if (!cubeStructure.isEmpty()) {
                    //adds BlockPoses from the structure to the list and makes sure that the path isn't blocked
                    for (int i = 0; i < cubeStructure.size(); i++) {
                        IBlockState state = this.getWorld().getBlockState(cubeStructure.get(i));
                        if (state == Blocks.AIR.getDefaultState() || state.getBlock().isReplaceable(this.getWorld(), cubeStructure.get(i)))
                            indexList.add(i);
                        if (state != Blocks.AIR.getDefaultState() && state != ModRegistry.CompressedTrashBlock.getDefaultState() && !state.getBlock().isReplaceable(this.getWorld(), cubeStructure.get(i)))
                            isPathBlocked = true;
                    }
                    if (!isPathBlocked) {
                        if (indexList.isEmpty()) {
                            if (!saidTheStructureCompletedMessage) {
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
                        //if the path is blocked it will inform the player who's name is given by Notification Upgrade then it will drop items from its inventory out
                        BlockPos pos = this.getPos().east();
                        try {
                            EntityPlayer player = this.getWorld().getPlayerEntityByName(moduleName);
                            if (player != null && this.isPlayerNearby(player, this.getPos()))
                                player.sendStatusMessage(new TextComponentString(new TextComponentTranslation("texts.pathblocked.part1").getUnformattedComponentText() + " [x:" + this.getPos().getX() + ",y:" + this.getPos().getY() + ",z:" + this.getPos().getZ() + "] " + new TextComponentTranslation("texts.pathblocked.part2").getUnformattedComponentText()), false);
                        } catch (NullPointerException e) {

                        }
                        for (int slot : slotsWithAnItem) {
                            ItemStack itemStack = trashInventory.getStackInSlot(slot);
                            if (itemStack != ItemStack.EMPTY)
                                this.dropItem(this.getWorld(), pos, itemStack);

                        }
                    }
                }
            }
                for (int slot : slotsWithAnItem) {
                    trashInventory.setStackInSlot(slot, ItemStack.EMPTY);
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

    public boolean isbModuleAttached() {
        return bModuleAttached;
    }

    public void setbModuleAttached(boolean bModuleAttached) {
        this.bModuleAttached = bModuleAttached;
        this.markDirty();
    }

    public boolean iswModuleAttached() {
        return wModuleAttached;
    }

    public void setwModuleAttached(boolean bModuleAttached) {
        this.wModuleAttached = bModuleAttached;
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

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();
        nbtTag.setBoolean("nModuleAttached", nModuleAttached);
        nbtTag.setBoolean("bModuleAttached", bModuleAttached);
        nbtTag.setBoolean("wModuleAttached", wModuleAttached);
        return nbtTag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        handleUpdateTag(pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        nModuleAttached = tag.getBoolean("nModuleAttached");
        bModuleAttached = tag.getBoolean("bModuleAttached");
        wModuleAttached = tag.getBoolean("wModuleAttached");
    }

    private boolean checkItemStackForWhitelist(ItemStack stack){
        try {
            ItemStackHandler handler = null;
            //double check
            if (listUpgrade.getItem() instanceof ItemWhitelistModule) {
                handler = (ItemStackHandler) listUpgrade.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            }
            if (handler != null && !stack.isEmpty()) {
                for (int i = 0; i < handler.getSlots(); i++) {
                    if (handler.getStackInSlot(i).getItem() == stack.getItem())
                        return true;
                }
            }
            return false;
        }catch (NullPointerException e){
            return false;
        }
    }

    private boolean checkItemStackForBlacklist(ItemStack stack){
        try {
            ItemStackHandler handler = null;
            //double check
            if (listUpgrade.getItem() instanceof ItemBlacklistModule) {
                handler = (ItemStackHandler) listUpgrade.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            }
            if (handler != null && !stack.isEmpty()) {
                for (int i = 0; i < handler.getSlots(); i++) {
                    if (handler.getStackInSlot(i).getItem() == stack.getItem())
                        return true;
                }
            }
            return false;
        }catch (NullPointerException e){
            return false;
        }
    }

    public void setListUpgrade(ItemStack stack){
        listUpgrade=stack;
    }

    private void dropItem(World worldIn, BlockPos pos, ItemStack stack){
        InventoryHelper.spawnItemStack(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), stack);
    }

    private boolean checkIfShouldDump(){
        World world = this.getWorld();
        if(progressStep!=12){
            progressStep += 1;
            world.setBlockState(this.getPos(), world.getBlockState(this.getPos()).cycleProperty(BlockTrash.PROGRESS_STEP));
            return false;
        }else{
            progressStep = 0;
            world.setBlockState(this.getPos(), this.getBlockType().getDefaultState());
            return true;
        }
    }
}
