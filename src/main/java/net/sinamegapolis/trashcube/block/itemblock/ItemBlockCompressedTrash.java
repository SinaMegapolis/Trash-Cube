package net.sinamegapolis.trashcube.block.itemblock;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sinamegapolis.trashcube.block.BlockCompressedTrash;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class ItemBlockCompressedTrash extends ItemBlock {
    public ItemBlockCompressedTrash(BlockCompressedTrash block) {
        super(block);
        setRegistryName(block.getRegistryName());
        setHasSubtypes(true);
    }



    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + stack.getMetadata();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentTranslation("texts.tooltip.compressedtrashblock.line1").getUnformattedComponentText());
        tooltip.add(new TextComponentTranslation("texts.tooltip.compressedtrashblock.line2").getUnformattedComponentText());
        tooltip.add(new TextComponentTranslation("texts.tooltip.compressedtrashblock.line3").getUnformattedComponentText());
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return 5 * 20;
    }
}
