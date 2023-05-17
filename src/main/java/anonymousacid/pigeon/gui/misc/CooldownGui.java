package anonymousacid.pigeon.gui.misc;

import anonymousacid.pigeon.features.misc.cooldown.CooldownHandler;
import anonymousacid.pigeon.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class CooldownGui extends Gui {
	Minecraft mc = Minecraft.getMinecraft();
	
	public CooldownGui(Minecraft mc) {
		if(!ConfigHandler.cdGui) return;
		ScaledResolution scaled = new ScaledResolution(mc);
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();
		int x = ConfigHandler.cdGuiX;
		int y = ConfigHandler.cdGuiY;
		for(int i=0; i<CooldownHandler.itemsOnCooldown.size(); i++) {
			if(CooldownHandler.itemsOnCooldown.get(i) != null) {
				if(i==0) {
					mc.getRenderItem().renderItemAndEffectIntoGUI(CooldownHandler.itemsOnCooldown.get(i), x+(10*i), y);
				} else {
					mc.getRenderItem().renderItemAndEffectIntoGUI(CooldownHandler.itemsOnCooldown.get(i), x+(10*i)+5, y);
				}
			}
		}
		boolean foundDisplayeditem = false;
		for(int i=0; i<CooldownHandler.displayedItems.length; i++) {
			if(CooldownHandler.displayedItems[i]) {
				foundDisplayeditem = true;
				break;
			}
		}
		drawString(mc.fontRendererObj, "Items on Cooldown", x, y+20, Integer.parseInt("55FF55", 16));
	}
}
