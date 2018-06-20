package net.sinamegapolis.trashcube.utill;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.sinamegapolis.trashcube.item.ItemBlacklistModule;
import net.sinamegapolis.trashcube.item.ItemWhitelistModule;

public class GuiContainerListUpgrade extends GuiContainer {

    private InventoryPlayer playerInv;
    private ItemStack stack;
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation("trashcube", "textures/gui/listinventory.png");
    private Container container;

    public GuiContainerListUpgrade(Container container, InventoryPlayer playerInv) {
        super(container);
        this.playerInv = playerInv;
        this.container = container;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        if(container instanceof ContainerListUpgrade)
            stack = ((ContainerListUpgrade) container).getListType();
        if(!stack.isEmpty()){
            Item item = stack.getItem();
            if(item instanceof ItemBlacklistModule){
                fontRenderer.drawString("BlackList", xSize / 2 - fontRenderer.getStringWidth("BlackList Upgrade") / 2 , 6, 0x202020);
                fontRenderer.drawString("Upgrade", xSize / 2 - fontRenderer.getStringWidth("BlackList Upgrade") / 2 + 46, 6, 0x404040);
            }
            if(item instanceof ItemWhitelistModule){
                fontRenderer.drawString("WhiteList", xSize / 2 - fontRenderer.getStringWidth("WhiteList Upgrade") / 2 , 6, 0xF9F9F9);
                fontRenderer.drawString("Upgrade", xSize / 2 - fontRenderer.getStringWidth("WhiteList Upgrade") / 2 + 46, 6, 0x404040);
            }

        }
    }

}