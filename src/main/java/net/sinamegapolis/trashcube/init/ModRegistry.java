package net.sinamegapolis.trashcube.init;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.sinamegapolis.trashcube.TrashCube;
import net.sinamegapolis.trashcube.block.BlockCompressedTrash;
import net.sinamegapolis.trashcube.block.BlockTrash;
import net.sinamegapolis.trashcube.block.itemblock.ItemBlockCompressedTrash;
import net.sinamegapolis.trashcube.block.itemblock.ItemBlockTrash;
import net.sinamegapolis.trashcube.config.TrashCubeConfig;
import net.sinamegapolis.trashcube.item.ItemBlacklistModule;
import net.sinamegapolis.trashcube.item.ItemNotificationModule;
import net.sinamegapolis.trashcube.item.ItemWhitelistModule;
import net.sinamegapolis.trashcube.loot.LootEntryItemStack;
import net.sinamegapolis.trashcube.tileentity.TileEntityCompressedTrash;
import net.sinamegapolis.trashcube.tileentity.TileEntityTrash;
import net.sinamegapolis.trashcube.tileentity.renderer.TESRTrash;

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
    public static final Item BlacklistModule = new ItemBlacklistModule("bmodule");
    public static final Item WhitelistModule = new ItemWhitelistModule("wmodule");

    @SubscribeEvent
    public void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
        GameRegistry.registerTileEntity(TileEntityTrash.class, TrashBlock.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityCompressedTrash.class, CompressedTrashBlock.getRegistryName());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrash.class, new TESRTrash());

        ResourceLocation optional = new ResourceLocation("");
        if(TrashCubeConfig.shouldHavePainfulCraftingRecipes && !TrashCubeConfig.shouldHaveCraftingRecipes) {
            GameRegistry.addShapedRecipe(new ResourceLocation(TrashCube.MODID,"trashcube_recipe"), optional, new ItemStack(((BlockTrash)TrashBlock).getInstance(),1),
                    "LPR",
                    "PHP",
                    "GPD",
                    'L', new ItemStack(Items.DYE, 1, EnumDyeColor.SILVER.getDyeDamage()),
                    'P', Blocks.PISTON,
                    'R',Items.REDSTONE,
                    'H',Blocks.HOPPER,
                    'G',new ItemStack(Items.DYE, 1, EnumDyeColor.GRAY.getDyeDamage()),
                    'D',Blocks.DROPPER
            );
            GameRegistry.addShapedRecipe(new ResourceLocation("notification_upgrade_recipe"), optional, new ItemStack(NotificationModule,1),
                    "GSD",
                    "SIS",
                    "GSR",
                    'S',Items.STICK,
                    'I',Items.IRON_INGOT,
                    'G',new ItemStack(Items.DYE,1, EnumDyeColor.GRAY.getDyeDamage()),
                    'D',new ItemStack(Items.DYE,1, EnumDyeColor.YELLOW.getDyeDamage()),
                    'R',Items.REDSTONE
            );
            GameRegistry.addShapedRecipe(new ResourceLocation("blacklist_upgrade_recipe"), optional, new ItemStack(BlacklistModule,1),
                    "GSB",
                    "SIS",
                    "GSL",
                    'S',Items.STICK,
                    'I',Items.IRON_INGOT,
                    'G',new ItemStack(Items.DYE,1, EnumDyeColor.GRAY.getDyeDamage()),
                    'B',new ItemStack(Items.DYE,1, EnumDyeColor.BLACK.getDyeDamage()),
                    'L',Items.GLOWSTONE_DUST
            );
            GameRegistry.addShapedRecipe(new ResourceLocation("whitelist_upgrade_recipe"), optional, new ItemStack(WhitelistModule,1),
                    "GSW",
                    "SIS",
                    "GSL",
                    'S',Items.STICK,
                    'I',Items.IRON_INGOT,
                    'G',new ItemStack(Items.DYE,1, EnumDyeColor.GRAY.getDyeDamage()),
                    'W',new ItemStack(Items.DYE,1, EnumDyeColor.WHITE.getDyeDamage()),
                    'L',Items.GLOWSTONE_DUST
            );
        }else if(TrashCubeConfig.shouldHaveCraftingRecipes && !TrashCubeConfig.shouldHavePainfulCraftingRecipes){
            GameRegistry.addShapedRecipe(new ResourceLocation(TrashCube.MODID,"trashcube_recipe"), optional, new ItemStack(((BlockTrash)TrashBlock).getInstance(),1),
                    "GIR",
                    "I I",
                    " I ",
                    'R',Items.REDSTONE,
                    'G',new ItemStack(Items.DYE, 1, EnumDyeColor.GRAY.getDyeDamage()),
                    'I',Items.IRON_INGOT
            );
            GameRegistry.addShapedRecipe(new ResourceLocation("notification_upgrade_recipe"), optional, new ItemStack(NotificationModule,1),
                    " D ",
                    " I ",
                    " R ",
                    'I',Items.IRON_INGOT,
                    'D',new ItemStack(Items.DYE,1, EnumDyeColor.YELLOW.getDyeDamage()),
                    'R',Items.REDSTONE
            );
            GameRegistry.addShapedRecipe(new ResourceLocation("blacklist_upgrade_recipe"), optional, new ItemStack(BlacklistModule,1),
                    " B ",
                    " I ",
                    " R ",
                    'I',Items.IRON_INGOT,
                    'B',new ItemStack(Items.DYE,1, EnumDyeColor.BLACK.getDyeDamage()),
                    'R',Items.REDSTONE
            );
            GameRegistry.addShapedRecipe(new ResourceLocation("whitelist_upgrade_recipe"), optional, new ItemStack(WhitelistModule,1),
                    " W ",
                    " I ",
                    " R ",
                    'I',Items.IRON_INGOT,
                    'W',new ItemStack(Items.DYE,1, EnumDyeColor.WHITE.getDyeDamage()),
                    'R',Items.REDSTONE
            );
        }
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
            if(lootpool != null && !TrashCubeConfig.shouldHavePainfulCraftingRecipes && !TrashCubeConfig.shouldHaveCraftingRecipes){
                lootpool.addEntry(new LootEntryItemStack("trashcube:trashblock",new ItemStack(new ItemBlockTrash(TrashBlock), 2),15,3));
                lootpool.addEntry(new LootEntryItemStack("trashcube:nmodule",new ItemStack(ModRegistry.NotificationModule),16,3));
                lootpool.addEntry(new LootEntryItemStack("trashcube:bmodule",new ItemStack(ModRegistry.WhitelistModule),16,3));
                lootpool.addEntry(new LootEntryItemStack("trashcube:wmodule",new ItemStack(ModRegistry.BlacklistModule),16,3));
            }
        }
    }

}
