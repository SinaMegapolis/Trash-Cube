package net.sinamegapolis.trashcube.init;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.sinamegapolis.trashcube.block.BlockCompressedTrash;
import net.sinamegapolis.trashcube.block.BlockTrash;
import net.sinamegapolis.trashcube.block.itemblock.ItemBlockCompressedTrash;
import net.sinamegapolis.trashcube.tileentity.TileEntityCompressedTrash;
import net.sinamegapolis.trashcube.tileentity.TileEntityTrash;

import java.util.ArrayList;
import java.util.List;

public class ModRegistry {
    public static final List<net.minecraft.block.Block> BLOCKS = new ArrayList<net.minecraft.block.Block>();
    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final Block TrashBlock = new BlockTrash("trashblock");
    public static final Block CompressedTrashBlock = new BlockCompressedTrash("compressedtrash");

    @SubscribeEvent
    public void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
        GameRegistry.registerTileEntity(TileEntityTrash.class, TrashBlock.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityCompressedTrash.class, CompressedTrashBlock.getRegistryName());
    }


    @SubscribeEvent
    public void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public void onBlockColorHandlerRegister(ColorHandlerEvent.Block event){
        event.getBlockColors().registerBlockColorHandler(((sstate, worldInn, Bpos, tintIndex) -> sstate.getValue(BlockCompressedTrash.COLOR).getColorValue()),CompressedTrashBlock);
    }

    @SubscribeEvent
    public void onItemColorHandlerRegister(ColorHandlerEvent.Item event){
        event.getItemColors().registerItemColorHandler((stack,tintIndex)->{
            if(stack.getItem()instanceof ItemBlockCompressedTrash){
                return EnumDyeColor.byDyeDamage(stack.getItemDamage()).getColorValue();
            }
            return 0xFFFFFF;
        },BlockCompressedTrash.instanceItemBlockCompressedTrash);
    }

}