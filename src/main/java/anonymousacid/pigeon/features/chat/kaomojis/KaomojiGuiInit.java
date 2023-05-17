package anonymousacid.pigeon.features.chat.kaomojis;

import anonymousacid.pigeon.McIf;
import anonymousacid.pigeon.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class KaomojiGuiInit {
	public static KaomojiGuiInit instance = new KaomojiGuiInit(); 
	Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event) {
		if(!ConfigHandler.chatKaomojis) return;
		if(!ChatKaomojis.showSuggestions) return;
		if(ChatKaomojis.foundMatches == 0) return;
		if(event.type == ElementType.CHAT) {
			new KaomojiGui(McIf.minecraft());
		}
    }
}
