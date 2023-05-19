package anonymousacid.pigeon.gui;

import org.lwjgl.input.Mouse;

import anonymousacid.pigeon.Reference;
import anonymousacid.pigeon.gui.config.ConfigGui;
import anonymousacid.pigeon.handlers.ConfigHandler;
import anonymousacid.pigeon.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class PigeonButton extends GuiButton {
	
	public boolean pressed = false;
	public boolean dragged = false;
	
	//These are positions based on the middle of the button.
	private int pressedX = 0;
	private int pressedY = 0;
	
    //texture is 50x100 pixels
	private static final ResourceLocation pigeonButtonTexture = new ResourceLocation(Reference.MODID, "textures/gui/pigeon_button.png");
	
	public PigeonButton(int buttonId, int x, int y, int buttonSize) {
		super(buttonId, x, y, buttonSize, buttonSize, "");
	}
	
	@Override
	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
		if(pressed && Mouse.isButtonDown(0)) {
			
			//If not dragged yet, constantly check if it is being dragged (true when going out of button bounds)
			if(!dragged) {
				double distFromPressedPos = Math.sqrt(
						Math.pow(mouseX - pressedX, 2) + Math.pow(mouseY - pressedY, 2) 
						);
				
				dragged = distFromPressedPos > width/2;
			}
			else {//If being dragged, then move to mouse.
				xPosition = mouseX-width/2;
				yPosition = mouseY-height/2;
			}
			
		}
	}
	
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		pressed = enabled && 
				visible && mouseX >= xPosition && mouseY >= yPosition &&
				mouseX < xPosition + width && mouseY < yPosition + height;
		
		pressedX = xPosition + width/2;
		pressedY = yPosition + height/2;
		
		//Return false to prevent opening advancements screen when clicked in player inventory
		return false;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		
		//just copied the minecraft drawButton code and modified it :/
		if(visible) {
            FontRenderer fontrenderer = mc.fontRendererObj;
            mc.getTextureManager().bindTexture(pigeonButtonTexture);
            
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            
            hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
            int hoverState = hovered && !pressed ? 1 : 0;
            
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            
            drawModalRectWithCustomSizedTexture(xPosition, yPosition, 0, hoverState*50, 50, 50, 50, 100);
            
            mouseDragged(mc, mouseX, mouseY);
            
            int j = 14737632;
            if (packedFGColour != 0) {
                j = packedFGColour;
            }
            else
            if (!enabled) {
                j = 10526880;
            }
            else if (hovered) {
                j = 16777120;
            }
            
            RenderUtils.drawEntityOnScreen(xPosition+width/2, yPosition+width/2, 15, (float)(100) - mouseX, (float)(75 + 140) - mouseY, ConfigGui.pigeon);
		}
		
		//if mouse released
		if(pressed && !Mouse.isButtonDown(0)) {
			
			if(!dragged) {//if was pressed, but not dragged
				Minecraft.getMinecraft().displayGuiScreen(new ConfigGui()); //Show pigeon config Gui
			}
			else if (dragged) {//if pressed, and dragged 
				//save the position the button was dragged to
				ConfigHandler.writeInt(ConfigHandler.MISCELLANEOUS_CATEGORY, "pigeonButtonX", xPosition);
				ConfigHandler.writeInt(ConfigHandler.MISCELLANEOUS_CATEGORY, "pigeonButtonY", yPosition);
			}
			
			dragged = false;
			pressed = false;
		}
	}
	
}
