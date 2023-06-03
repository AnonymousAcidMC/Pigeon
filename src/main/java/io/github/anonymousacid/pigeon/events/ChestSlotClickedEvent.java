package io.github.anonymousacid.pigeon.events;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class ChestSlotClickedEvent extends Event {
    public final GuiChest chest;
    public final IInventory inventory;
    public final String inventoryName;
    public final Slot slot;
    public final int slotId;
    public final ItemStack item;
    public final int clickedButton;
    public final int clickType;

    public ChestSlotClickedEvent(GuiChest chest, IInventory inventory, String inventoryName, Slot slot, int slotId, ItemStack item, int clickedButton, int clickType) {
        this.chest = chest;
        this.inventory = inventory;
        this.inventoryName = inventoryName;
        this.slot = slot;
        this.slotId = slotId;
        this.item = item;
        this.clickedButton = clickedButton;
        this.clickType = clickType;
    }
}
