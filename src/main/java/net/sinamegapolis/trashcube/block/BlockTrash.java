package net.sinamegapolis.trashcube.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.sinamegapolis.trashcube.TrashCube;
import net.sinamegapolis.trashcube.block.itemblock.ItemBlockTrash;
import net.sinamegapolis.trashcube.init.IHasModel;
import net.sinamegapolis.trashcube.init.ModRegistry;
import net.sinamegapolis.trashcube.item.ItemNotificationModule;
import net.sinamegapolis.trashcube.tileentity.TileEntityTrash;

import java.util.ArrayList;

public class BlockTrash extends Block implements IHasModel {

    private ItemBlockTrash instance;

    public BlockTrash(String name)
    {
        super(Material.WOOD);
        setCreativeTab(ModRegistry.TAB);
        setHardness(0.8f);
        setRegistryName(name);
        setUnlocalizedName(TrashCube.MODID + "." + name);
        ModRegistry.BLOCKS.add(this);
        instance = new ItemBlockTrash(this);
        ModRegistry.ITEMS.add(instance);
    }

    public ItemBlockTrash getInstance() {
        return instance;
    }

    @Override
    public void registerModels() {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {return new TileEntityTrash();}

    /**
     * Called when the block is right clicked by a player.
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        float i = 1f/16;
        TileEntityTrash te = (TileEntityTrash)worldIn.getTileEntity(pos);
        if(3*i<=hitY && hitY<=9*i && 5*i<=hitZ && hitZ<=11*i){
            if (worldIn.isRemote)
                return true;

            if (playerIn.getHeldItem(hand).getItem() == ModRegistry.NotificationModule && !te.isnModuleAttached()) {
                te.setModuleName(playerIn.getHeldItem(hand).getDisplayName());
                te.setnModuleAttached(true);
                worldIn.notifyBlockUpdate(te.getPos(), state, state, 2);
                if(!playerIn.capabilities.isCreativeMode) {
                    ItemStack stack = playerIn.getHeldItem(hand);
                    stack.shrink(1);
                    playerIn.setHeldItem(hand, stack);
                }
                return true;
            }
            if(te.isnModuleAttached()){
                if(!te.getModuleName().equals(new TextComponentTranslation("item.trashcube.nmodule.name").getUnformattedComponentText()))
                    playerIn.addItemStackToInventory(new ItemStack(ModRegistry.NotificationModule,1).setStackDisplayName(te.getModuleName()));
                if(te.getModuleName().equals(new TextComponentTranslation("item.trashcube.nmodule.name").getUnformattedComponentText()))
                    playerIn.addItemStackToInventory(new ItemStack(ModRegistry.NotificationModule,1));
                te.setnModuleAttached(false);
                worldIn.notifyBlockUpdate(te.getPos(), state, state, 2);
                return true;}
                if(playerIn.getHeldItem(hand).getItem()==ModRegistry.BlacklistModule && !te.isbModuleAttached() && !te.iswModuleAttached()){
            te.setbModuleAttached(true);
            worldIn.notifyBlockUpdate(te.getPos(), state, state, 2);
                    if(!playerIn.capabilities.isCreativeMode) {
                        ItemStack stack = playerIn.getHeldItem(hand);
                        stack.shrink(1);
                        playerIn.setHeldItem(hand, stack);
                    }
                    return true;
                }
                if(te.isbModuleAttached()){
                    playerIn.addItemStackToInventory(new ItemStack(ModRegistry.BlacklistModule,1));
                    te.setbModuleAttached(false);
                    worldIn.notifyBlockUpdate(te.getPos(), state, state, 2);
                    return true;
                }
        if(playerIn.getHeldItem(hand).getItem()==ModRegistry.WhitelistModule && !te.iswModuleAttached() && !te.isbModuleAttached()){
            te.setwModuleAttached(true);
            worldIn.notifyBlockUpdate(te.getPos(), state, state, 2);
            if(!playerIn.capabilities.isCreativeMode) {
                ItemStack stack = playerIn.getHeldItem(hand);
                stack.shrink(1);
                playerIn.setHeldItem(hand, stack);
            }
            return true;
        }
        if(te.iswModuleAttached()){
            playerIn.addItemStackToInventory(new ItemStack(ModRegistry.WhitelistModule,1));
            te.setwModuleAttached(false);
            worldIn.notifyBlockUpdate(te.getPos(), state, state, 2);
            return true;
        }}
                return false;
    }


    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity trashEntity = worldIn.getTileEntity(pos);
        if (trashEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)) {
            ItemStackHandler handler = (ItemStackHandler) trashEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            ArrayList<ItemStack> items = new ArrayList<>();
            for (int n = 0; n < handler.getSlots(); n++) {
                if (handler.getStackInSlot(n) != ItemStack.EMPTY) {
                    items.add(handler.getStackInSlot(n));
                }
            }
            for (ItemStack item : items) {
                worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), item));
            }
        }
        super.breakBlock(worldIn, pos, state);
    }


}
