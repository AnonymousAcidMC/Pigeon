package anonymousacid.pigeon.gui;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import anonymousacid.pigeon.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;

public class EditGuiLocations extends GuiScreen {
	Minecraft mc = Minecraft.getMinecraft();
	private GuiButton cooldown;
	private GuiButton latency;
	private GuiButton saveButton;
	private GuiButton cancelButton;
	private GuiButton resetButton;
	
	ScaledResolution scaled = new ScaledResolution(mc);
	int screenWidth = scaled.getScaledWidth();
	int screenHeight = scaled.getScaledHeight();
	
	@Override
	public void initGui() {
		super.initGui();
		
		cooldown = new GuiButton(0, ConfigHandler.cdGuiX, ConfigHandler.cdGuiY, " ");
		cooldown.setWidth(10);
		cooldown.height = 10;
		buttonList.add(cooldown);
		
		latency = new GuiButton(0, ConfigHandler.latencyX, ConfigHandler.latencyY, " ");
		latency.setWidth(10);
		latency.height = 10;
		buttonList.add(latency);
		
		saveButton = new GuiButton(0, screenWidth/2-105, screenHeight/2, "Save");
		saveButton.setWidth(105);
		buttonList.add(saveButton);
		
		cancelButton = new GuiButton(0, saveButton.xPosition+105, screenHeight/2, "Cancel");
		cancelButton.setWidth(105);
		buttonList.add(cancelButton);
		
		resetButton = new GuiButton(0, screenWidth/2-50, cancelButton.yPosition+cancelButton.height, "Reset");
		resetButton.setWidth(100);
		buttonList.add(resetButton);
		
		
		
		for(int i=0; i<buttonList.size(); i++) {
			buttonList.get(i).id = 0;
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		for(int i=0; i<buttonList.size(); i++) {
			if(button == buttonList.get(i)) {
				buttonList.get(i).id = 1;
			}
		}
		saveButton.xPosition = screenWidth/2-105;
		saveButton.yPosition = screenHeight/2;
		cancelButton.xPosition = saveButton.xPosition+105;
		cancelButton.yPosition = screenHeight/2;
		resetButton.xPosition = screenWidth/2-50;
		resetButton.yPosition = cancelButton.yPosition+cancelButton.height;
		if(button == saveButton) {
			ConfigHandler.writeInt("miscellaneous", "cdGuiX", cooldown.xPosition);
			ConfigHandler.writeInt("miscellaneous", "cdGuiY", cooldown.yPosition);
			ConfigHandler.writeInt("miscellaneous", "latencyX", latency.xPosition);
			ConfigHandler.writeInt("miscellaneous", "latencyY", latency.yPosition);
			mc.thePlayer.closeScreen();
		}
		if(button == cancelButton) {
			mc.thePlayer.closeScreen();
		}
		if(button == resetButton) {
			ConfigHandler.writeInt("miscellaneous", "cdGuiX", ConfigHandler.cdGuiX);
			ConfigHandler.writeInt("miscellaneous", "cdGuiY", ConfigHandler.cdGuiY);
		}
	}
	
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		for(int i=0; i<buttonList.size(); i++) {
			if(buttonList.get(i).id == 1) {
				buttonList.get(i).xPosition = mouseX-buttonList.get(i).width/2;
				buttonList.get(i).yPosition = mouseY-buttonList.get(i).height/2;
			}
		}
		saveButton.xPosition = screenWidth/2-105;
		saveButton.yPosition = screenHeight/2;
		cancelButton.xPosition = saveButton.xPosition+105;
		cancelButton.yPosition = screenHeight/2;
		resetButton.xPosition = screenWidth/2-50;
		resetButton.yPosition = cancelButton.yPosition+cancelButton.height;
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		boolean foundMouse = false;
		for(int i=0; i<buttonList.size(); i++) {
			if(buttonList.get(i).isMouseOver()) {
				buttonList.get(i).id = 0;
				foundMouse = true;
			}
		}
		if(!foundMouse) {
			for(int i=0; i<buttonList.size(); i++) {
				buttonList.get(i).id = 0;
			}
		}
		saveButton.xPosition = screenWidth/2-105;
		saveButton.yPosition = screenHeight/2;
		cancelButton.xPosition = saveButton.xPosition+105;
		cancelButton.yPosition = screenHeight/2;
		resetButton.xPosition = screenWidth/2-50;
		resetButton.yPosition = cancelButton.yPosition+cancelButton.height;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		drawString(mc.fontRendererObj, "Items on Cooldown", cooldown.xPosition, cooldown.yPosition+20, Integer.parseInt("55FF55", 16));
		drawString(mc.fontRendererObj, "0 ms", latency.xPosition, latency.yPosition, Integer.parseInt("55FF55", 16));
		mc.getRenderItem().renderItemAndEffectIntoGUI(mc.thePlayer.getCurrentEquippedItem(), cooldown.xPosition, cooldown.yPosition);
		
		for(int i=0; i<buttonList.size(); i++) {
			if(buttonList.get(i) instanceof GuiButton) {
				GuiButton btn = (GuiButton) buttonList.get(i);
				if(btn.isMouseOver()) {
					if(btn.equals(cooldown)) {
						String[] desc = {EnumChatFormatting.GREEN + "Cooldown Gui"};
						List temp = Arrays.asList(desc);
						drawHoveringText(temp, mouseX, mouseY);	
					}
				}
			}
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
}
