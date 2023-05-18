package anonymousacid.pigeon.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class PigeonButton extends GuiButton {

	public PigeonButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}
	
	@Override
	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
		super.mouseDragged(mc, mouseX, mouseY);
		if(isMouseOver()) {
			xPosition = mouseX-width/2;
			yPosition = mouseY-height/2;
		}
	}
}
