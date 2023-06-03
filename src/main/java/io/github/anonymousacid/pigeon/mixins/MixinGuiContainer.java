package io.github.anonymousacid.pigeon.mixins;

import static io.github.anonymousacid.pigeon.McIf.mc;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.anonymousacid.pigeon.Pigeon;
import io.github.anonymousacid.pigeon.events.ChestSlotClickedEvent;
import io.github.anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.common.MinecraftForge;

@Mixin(GuiContainer.class)
/**
 * Posts event used for handling clicks on container GUIs.
 */
public class MixinGuiContainer {
	
	@Inject(method = "handleMouseClick", at = @At("HEAD"), cancellable = true)
	public void onClick(Slot slotIn, int slotId, int clickedButton, int clickType, CallbackInfo ci) {
        if (slotIn == null || slotIn.getStack() == null) return; 
        if (!(mc.currentScreen instanceof GuiChest)) return;
        
        Container containerChest = ((GuiChest)mc.currentScreen).inventorySlots;
        
        if (!(containerChest instanceof ContainerChest)) return;
        
        GuiChest chest = (GuiChest)mc.currentScreen;
        IInventory inventory = ((ContainerChest)containerChest).getLowerChestInventory();
        String inventoryName = inventory.getDisplayName().getUnformattedText();
        
        ChestSlotClickedEvent event = new ChestSlotClickedEvent(chest, inventory, inventoryName, slotIn, slotId, slotIn.getStack(), clickedButton, clickType);
        MinecraftForge.EVENT_BUS.post(event);
        
        if(event.isCanceled() && ci.isCancellable()) {
        	ci.cancel();
        	return;
        }
        
    }
	
	@Inject(method = "initGui", at = @At("RETURN"))
	public void onInitGui(CallbackInfo ci) {
		
		//need to cast this current GuiContainer to an Mixin Accessor interface
		//first, cast it to a GuiScreen to access buttonList since it is a parent field
		AccessorGuiScreen screenAccessor = 
				(AccessorGuiScreen)(GuiScreen) ((Object)this);
		
		//Access the private field
		List<GuiButton> buttonList = screenAccessor.getButtonList();
		buttonList.add(Pigeon.pigeonButton);
	}
}
