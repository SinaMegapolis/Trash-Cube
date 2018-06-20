package net.sinamegapolis.trashcube.utill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.sinamegapolis.trashcube.othersutill.SlotFilter;

public class ContainerListUpgrade extends Container {

    private ItemStack listType;
    public ContainerListUpgrade(InventoryPlayer playerInv, ItemStack stack) {
        listType=stack;
        ItemStackHandler inventory = (ItemStackHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        initPlayerInv(playerInv);
        initBlacklistInv(inventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            int containerSlots = inventorySlots.size() - playerIn.inventory.mainInventory.size();

            if (index < containerSlots) {
                if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    private void initPlayerInv(InventoryPlayer playerInv){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; k++) {
            addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
        }
    }

    private void initBlacklistInv(IItemHandler handler){

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new SlotFilter(handler, j + i * 9 , 8 + j * 18, 21 + i * 18));
            }
        }
    }

    @Override
    public ItemStack slotClick(int slotId, int clickedButton, ClickType mode, EntityPlayer playerIn)
    {
        int id = slotId - 36;
        if (id >= 0 && id < 27 && getSlot(slotId)instanceof SlotFilter)
        {

            if (mode == ClickType.PICKUP || mode == ClickType.PICKUP_ALL ||
                    mode == ClickType.SWAP) // 1 is shift-click
            {
                Slot slot = this.inventorySlots.get(slotId);

                ItemStack dropping = playerIn.inventory.getItemStack();
                ItemStack copy = dropping.copy();
                copy.setCount(1);
                for(int i = 36; i<63; i++){
                 if(this.inventorySlots.get(i).getStack().getItem().equals(copy.getItem())) {
                     slot.putStack(ItemStack.EMPTY);
                     return ItemStack.EMPTY;
                 }
                }
                //these lines below are just in case, don't get confused
                if (dropping.getCount() > 0)
                {
                    slot.putStack(copy);
                }
                else if (slot.getStack().getCount() > 0)
                {
                    slot.putStack(ItemStack.EMPTY);
                }

                return slot.getStack().copy();
            }

            return ItemStack.EMPTY;
        }

        return super.slotClick(slotId, clickedButton, mode, playerIn);
    }

    public ItemStack getListType() {
        return listType;
    }
}
