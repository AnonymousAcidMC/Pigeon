package anonymousacid.pigeon.gui;

import static anonymousacid.pigeon.McIf.*;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mixin(GuiContainer.class)
public class PigeonInventoryGui {
	public static GuiButton pigeonButton;
	
	@Inject(method = "drawScreen", at = @At("RETURN"))
	public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
		pigeonButton.drawButton(mc, mouseX, mouseY);
	}
	
}
