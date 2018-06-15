package net.sinamegapolis.trashcube.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sinamegapolis.trashcube.TrashCube;
import net.sinamegapolis.trashcube.init.IHasModel;
import net.sinamegapolis.trashcube.init.ModRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class ItemNotificationModule extends Item implements IHasModel {
    public ItemNotificationModule(String name) {
        setRegistryName(name);
        setUnlocalizedName(TrashCube.MODID + "." + name);
        setCreativeTab(ModRegistry.TAB);
        ModRegistry.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Notification Module!");
        tooltip.add("Use this to warn only one player when Trash Cube's Structure is fully built.");
        tooltip.add("To Specify the player Change it's name to The Player's username.");
    }
}
