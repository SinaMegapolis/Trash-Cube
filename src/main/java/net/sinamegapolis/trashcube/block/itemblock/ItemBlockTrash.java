package net.sinamegapolis.trashcube.block.itemblock;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sinamegapolis.trashcube.block.BlockTrash;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockTrash extends ItemBlock {
    public ItemBlockTrash(Block block) {
        super(block);
        setRegistryName(block.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(GuiScreen.isShiftKeyDown()) {
            tooltip.add(I18n.format("texts.tooltip.trashcube.line1"));
            tooltip.add(I18n.format("texts.tooltip.trashcube.line2"));
            tooltip.add(I18n.format("texts.tooltip.trashcube.line3"));
        }else{
            tooltip.add(I18n.format("texts.tooltip.moreinfo"));
        }
    }
}
