package anonymousacid.pigeon.gui;

import java.awt.event.MouseListener;

import org.lwjgl.input.Mouse;

import anonymousacid.pigeon.gui.config.ConfigGui;
import anonymousacid.pigeon.handlers.ConfigHandler;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class PigeonButton extends GuiButton {
	public boolean pressed = false;
	public boolean dragged = false;
	
	private int pressedMouseX = 0;
	private int pressedMouseY = 0;

	public PigeonButton(int buttonId, int x, int y, int buttonSize, String buttonText) {
		super(buttonId, x, y, buttonSize, buttonSize, buttonText);
	}
	
	@Override
	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
		if(pressed && Mouse.isButtonDown(0)) {
			double distFromPressedPos = Math.sqrt(
					Math.pow(mouseX - pressedMouseX, 2) + Math.pow(mouseY - pressedMouseY, 2) 
					);
			
			if(distFromPressedPos > width) {
				xPosition = mouseX-width/2;
				yPosition = mouseY-height/2;
				dragged = true;
			}
		}
	}
	
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		pressed = this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
		
		Utils.sendMessage("pressed");
		
		pressedMouseX = mouseX;
		pressedMouseY = mouseY;
		
		//Return false to prevent opening advancements screen when clicked in player inventory
		return false;
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		Utils.sendMessage("mouse released");
		if(pressed && !dragged) {
			Minecraft.getMinecraft().displayGuiScreen(new ConfigGui());
		}
		else if (pressed && dragged) {
			ConfigHandler.writeInt(ConfigHandler.MISCELLANEOUS_CATEGORY, "pigeonButtonX", xPosition);
			ConfigHandler.writeInt(ConfigHandler.MISCELLANEOUS_CATEGORY, "pigeonButtonY", yPosition);
		}
		
		dragged = false;
		pressed = false;
	}
	
}
