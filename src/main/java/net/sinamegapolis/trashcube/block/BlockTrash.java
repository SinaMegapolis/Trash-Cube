package net.sinamegapolis.trashcube.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.sinamegapolis.trashcube.block.itemblock.ItemBlockTrash;
import net.sinamegapolis.trashcube.init.IHasModel;
import net.sinamegapolis.trashcube.init.ModRegistry;
import net.sinamegapolis.trashcube.tileentity.TileEntityTrash;

import java.util.ArrayList;

public class BlockTrash extends Block implements IHasModel {

    public static ItemBlockTrash instance;

    public BlockTrash(String name)
    {
        super(Material.WOOD);
        setCreativeTab(ModRegistry.TAB);
        setHardness(0.8f);
        setRegistryName(name);
        setUnlocalizedName(net.sinamegapolis.trashcube.TrashCube.MODID + "." + name);
        ModRegistry.BLOCKS.add(this);
        instance = new ItemBlockTrash(this);
        ModRegistry.ITEMS.add(instance);
    }

    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
                0, new ModelResourceLocation(getRegistryName(), "inventory"));

    }


    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {return new TileEntityTrash();}

    /**
     * Called when the block is right clicked by a player.
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockDestroyedByPlayer(worldIn, pos, state);
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
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
