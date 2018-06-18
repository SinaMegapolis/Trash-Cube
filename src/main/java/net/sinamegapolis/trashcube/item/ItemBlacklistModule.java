package net.sinamegapolis.trashcube.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.sinamegapolis.trashcube.TrashCube;
import net.sinamegapolis.trashcube.init.IHasModel;
import net.sinamegapolis.trashcube.init.ModRegistry;
import net.sinamegapolis.trashcube.utill.ItemStackHandlerList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemBlacklistModule extends Item implements IHasModel{


    public ItemBlacklistModule(String name) {
        setRegistryName(name);
        setUnlocalizedName(TrashCube.MODID + "." + name);
        setCreativeTab(ModRegistry.TAB);
        ModRegistry.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentTranslation("texts.tooltip.bmodule.line1").getUnformattedComponentText());
        tooltip.add(new TextComponentTranslation("texts.tooltip.lazy.line1").getUnformattedComponentText());
        tooltip.add(new TextComponentTranslation("texts.tooltip.lazy.line2").getUnformattedComponentText());
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ICapabilitySerializable<NBTTagCompound>() {
            private ItemStackHandler blackList = new ItemStackHandler(27);
            @Override
            public NBTTagCompound serializeNBT() {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setTag("blacklist", blackList.serializeNBT());
                return compound;
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
                nbt.removeTag("Size");
                blackList.deserializeNBT(nbt.getCompoundTag("blacklist"));
            }

            @Override
            public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
                return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
            }

            @Nullable
            @Override
            public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
                return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)blackList : null;
            }
        };
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        player.openGui(TrashCube.instance, 0, worldIn, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
