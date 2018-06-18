package net.sinamegapolis.trashcube.utill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.sinamegapolis.trashcube.init.ModRegistry;
import net.sinamegapolis.trashcube.item.ItemBlacklistModule;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    public static final int BLACKLIST = 0;
    public static final int WHITELIST = 0;
    @Nullable
    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return ID==BLACKLIST? new ContainerBlacklistUpgrade(player.inventory, player.getHeldItem(EnumHand.MAIN_HAND)):null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return ID==BLACKLIST? new GuiContainerBlackListUpgrade(getServerGuiElement(ID, player, world, x, y, z),player.inventory):null;
    }
}
