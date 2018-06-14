package net.sinamegapolis.trashcube.init;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.sinamegapolis.trashcube.TrashCube;
import net.sinamegapolis.trashcube.block.BlockCompressedTrash;
import net.sinamegapolis.trashcube.block.BlockTrash;
import net.sinamegapolis.trashcube.block.itemblock.ItemBlockCompressedTrash;
import net.sinamegapolis.trashcube.block.itemblock.ItemBlockTrash;
import net.sinamegapolis.trashcube.item.ItemNotificationModule;
import net.sinamegapolis.trashcube.loot.LootEntryItemStack;
import net.sinamegapolis.trashcube.tileentity.TileEntityCompressedTrash;
import net.sinamegapolis.trashcube.tileentity.TileEntityTrash;

import java.util.ArrayList;
import java.util.List;

public class ModRegistry {
    public static final List<net.minecraft.block.Block> BLOCKS = new ArrayList<net.minecraft.block.Block>();
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final CreativeTabs TAB = new CreativeTabs(TrashCube.MODID) {

        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(BlockCompressedTrash.instanceItemBlockCompressedTrash,1,15);
        }

    };
    public static final Block TrashBlock = new BlockTrash("trashblock");
    public static final Block CompressedTrashBlock = new BlockCompressedTrash("compressedtrash");
    public static final Item NotificationModule = new ItemNotificationModule("nmodule");

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
        event.getBlockColors().registerBlockColorHandler(((sstate, worldInn, bPos, tintIndex) -> sstate.getValue(BlockCompressedTrash.COLOR).getColorValue()),CompressedTrashBlock);
    }

    @SubscribeEvent
    public void onItemColorHandlerRegister(ColorHandlerEvent.Item event){
        event.getItemColors().registerItemColorHandler((stack,tintIndex)->{
            if(stack.getItem()instanceof ItemBlockCompressedTrash)
                return EnumDyeColor.byDyeDamage(stack.getItemDamage()).getColorValue();
            return 0xFFFFFF;
        },BlockCompressedTrash.instanceItemBlockCompressedTrash);
    }

    @SubscribeEvent
    public void onLootRegister(LootTableLoadEvent event){
        if(event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR) || event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CROSSING) || event.getName().equals(LootTableList.CHESTS_STRONGHOLD_LIBRARY)
           || event.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT)){
            LootPool lootpool = event.getTable().getPool("main");
            if(lootpool != null){
                lootpool.addEntry(new LootEntryItemStack("trashcube:trashblock",new ItemStack(new ItemBlockTrash(TrashBlock), 2),15,3));
            }
        }
    }
}
