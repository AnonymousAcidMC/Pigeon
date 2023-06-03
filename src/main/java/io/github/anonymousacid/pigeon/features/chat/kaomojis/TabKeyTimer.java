package io.github.anonymousacid.pigeon.features.chat.kaomojis;

import io.github.anonymousacid.pigeon.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TabKeyTimer {
	
	public static TabKeyTimer instance = new TabKeyTimer();
	Minecraft mc = Minecraft.getMinecraft();
			
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if(!ConfigHandler.chatKaomojis) return;
		if(event.phase == TickEvent.Phase.START) {
			if(ChatKaomojiData.tabKeyTimer != 0) {
				ChatKaomojiData.tabKeyTimer = ChatKaomojiData.tabKeyTimer - 1;
			} 
			if(ChatKaomojiData.scrollTimer != 0) {
				ChatKaomojiData.scrollTimer = ChatKaomojiData.scrollTimer - 1;
			}
			if(ChatKaomojiData.globalSearchTimer != 0) {
				ChatKaomojiData.globalSearchTimer = ChatKaomojiData.globalSearchTimer - 1;
			}
		}
	}
}
