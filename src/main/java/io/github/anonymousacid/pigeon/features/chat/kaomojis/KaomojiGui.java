package io.github.anonymousacid.pigeon.features.chat.kaomojis;

import io.github.anonymousacid.pigeon.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class KaomojiGui extends Gui {
	Minecraft mc = Minecraft.getMinecraft();
	private final ResourceLocation background = new ResourceLocation(Reference.MODID, "textures/kaomoji_background.png");
	public static String selectedKaomoji = "";
	
	//I heard that rendering in a constructor is weird, but cry about it.
	public KaomojiGui(Minecraft mc) {
		ScaledResolution scaled = new ScaledResolution(mc);
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();
		FontRenderer renderer = mc.fontRendererObj;
		mc.renderEngine.bindTexture(background);
		if(ChatKaomojis.kaomojiMatches.size()!=0&&ChatKaomojis.chatOpen) {
			drawTexturedModalRect(0,
					(((height / 2) - 4)+200)-23, 0, 0, 132, 35);
		}
		if(ChatKaomojis.kaomojiMatches.size()!=0 && ChatKaomojis.chatOpen) {
			int colorWhite = Integer.parseInt("FFFFFF", 16);
			int colorOrange = Integer.parseInt("FFAA00", 16);
			int color1 = colorWhite;
			int color2 = colorWhite;
			int color3 = colorWhite;
			if(ChatKaomojis.selectedIndex == 0) {
				selectedKaomoji = ChatKaomojis.kaomojiMatches.get(ChatKaomojis.index1);
				color1 = colorOrange;
			} else if(ChatKaomojis.selectedIndex == 1) {
				selectedKaomoji = ChatKaomojis.kaomojiMatches.get(ChatKaomojis.index2);
				color2 = colorOrange;
			} else if(ChatKaomojis.selectedIndex == 2) {
				selectedKaomoji = ChatKaomojis.kaomojiMatches.get(ChatKaomojis.index3);
				color3 = colorOrange;
			}
			drawString(renderer, ChatKaomojis.kaomojiMatches.get(ChatKaomojis.index1), 
					/*x*/0, 
					/*y*/((height / 2) - 4)+200,
					/*color*/color1);
			if(ChatKaomojis.kaomojiMatches.size()>1) {
				drawString(renderer, ChatKaomojis.kaomojiMatches.get(ChatKaomojis.index2), 
						/*x*/0, 
						/*y*/((height / 2) - 4)-(renderer.FONT_HEIGHT)+200, 
						/*color*/color2);
			}
			if(ChatKaomojis.kaomojiMatches.size()>2) {
				drawString(renderer, ChatKaomojis.kaomojiMatches.get(ChatKaomojis.index3), 
						/*x*/0, 
						/*y*/(height / 2) - 4-(renderer.FONT_HEIGHT*2)+200, 
						/*color*/color3);
			}
		}
	}
	
}
