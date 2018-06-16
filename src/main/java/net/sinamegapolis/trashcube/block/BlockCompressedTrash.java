package net.sinamegapolis.trashcube.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.oredict.DyeUtils;
import net.sinamegapolis.trashcube.block.itemblock.ItemBlockCompressedTrash;
import net.sinamegapolis.trashcube.init.IHasModel;
import net.sinamegapolis.trashcube.init.ModRegistry;
import net.sinamegapolis.trashcube.tileentity.TileEntityCompressedTrash;

public class BlockCompressedTrash extends Block implements IHasModel {

    public static ItemBlockCompressedTrash instanceItemBlockCompressedTrash;

    public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);
    public static String[] colorNames ={"white","orange","magenta","light_blue","yellow","lime","pink","gray","silver","cyan","purple","blue","brown","green","red","black"};

    public BlockCompressedTrash(String name)
    {
        super(Material.ROCK);
        setCreativeTab(ModRegistry.TAB);
        setHardness(0.8f);
        setRegistryName(name);
        setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
        setUnlocalizedName(net.sinamegapolis.trashcube.TrashCube.MODID + "." + name);
        ModRegistry.BLOCKS.add(this);
        instanceItemBlockCompressedTrash = new ItemBlockCompressedTrash(this);
        ModRegistry.ITEMS.add(instanceItemBlockCompressedTrash);
    }

    @Override
    public void registerModels() {
        for (int i = 0; i < 16; i++)
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(getRegistryName(), "color=" + colorNames[i]));
    }


        @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        DyeUtils.colorFromStack(playerIn.getHeldItem(hand)).ifPresent(edc ->{
                worldIn.setBlockState(pos, this.blockState.getBaseState().withProperty(COLOR,edc));
        });
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {return new TileEntityCompressedTrash();}

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(COLOR).getDyeDamage();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byDyeDamage(meta));
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COLOR);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for(int i = 0; i < 16; i++) list.add(new ItemStack(this, 1, i));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(COLOR, EnumDyeColor.byDyeDamage(meta));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(COLOR).getDyeDamage();
    }

}
