package net.sinamegapolis.trashcube.othersutill;

import net.minecraftforge.items.ItemStackHandler;
import net.sinamegapolis.trashcube.config.TrashCubeConfig;

public class TrashCubeItemStackHandler extends ItemStackHandler {
    public TrashCubeItemStackHandler(int size){
        super(size);
    }
    @Override
    public int getSlotLimit(int slot) {
        return TrashCubeConfig.maxStackSize;
    }
}
