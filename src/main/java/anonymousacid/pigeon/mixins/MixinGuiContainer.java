package anonymousacid.pigeon.mixins;

import static anonymousacid.pigeon.McIf.mc;

import java.lang.reflect.Field;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import anonymousacid.pigeon.Pigeon;
import anonymousacid.pigeon.events.ChestSlotClickedEvent;
import anonymousacid.pigeon.handlers.ConfigHandler;
import net.minecraft.client.gui.GuiButton;
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
 * (It doesnt work right now)
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
	
	//TODO: Find a way to add the pigeon button into the GuiContainer's buttonList
	//look through other mods' source code for reference
}
