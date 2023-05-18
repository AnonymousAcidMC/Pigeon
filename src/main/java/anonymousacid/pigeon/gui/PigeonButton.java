package anonymousacid.pigeon.gui;

import java.awt.event.MouseListener;

import anonymousacid.pigeon.handlers.ConfigHandler;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class PigeonButton extends GuiButton {
	public boolean clicked = false;

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
	
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		clicked = isMouseOver() && super.mousePressed(mc, mouseX, mouseY);
		if(clicked)
			Utils.sendMessage("pressed");
		return super.mousePressed(mc, mouseX, mouseY);
	}
	
}
