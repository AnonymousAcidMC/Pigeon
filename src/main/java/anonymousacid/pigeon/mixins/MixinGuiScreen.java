package anonymousacid.pigeon.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import anonymousacid.pigeon.Pigeon;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;

@Mixin(GuiScreen.class)
public class MixinGuiScreen {
	
	@Inject(method = "actionPerformed", at = @At("HEAD")) 
	public void onButtonClick(GuiButton button, CallbackInfo ci) {
		if((Object)this instanceof GuiContainer) {
			if(button == Pigeon.pigeonButton) {
				Utils.sendMessage("test");
			}
		}		
	}
}
