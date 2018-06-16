package net.sinamegapolis.trashcube.block.itemblock;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sinamegapolis.trashcube.block.BlockTrash;

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
        tooltip.add(new TextComponentTranslation("texts.tooltip.trashcube.line1").getUnformattedComponentText());
        tooltip.add(new TextComponentTranslation("texts.tooltip.trashcube.line2").getUnformattedComponentText());
        tooltip.add(new TextComponentTranslation("texts.tooltip.trashcube.line3").getUnformattedComponentText());
    }
}
