package net.sinamegapolis.trashcube.block.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.sinamegapolis.trashcube.block.BlockCompressedTrash;

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
}
