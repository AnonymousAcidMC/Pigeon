package io.github.anonymousacid.pigeon.features.chat.chatbubbles;

import io.github.anonymousacid.pigeon.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ChatBubbleTimer {
	
	public static ChatBubbleTimer instance = new ChatBubbleTimer();
	Minecraft mc = Minecraft.getMinecraft();
	
	/**
	 * Removes messages from the ArrayLists after the chat bubble duration for the message
	 * has ran out.
	 */
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if(!ConfigHandler.chatBubbles) return;
		if(event.phase != TickEvent.Phase.START) return;
		try {
			if(event.phase == TickEvent.Phase.START) {
				if(!ChatBubbleNametags.messageTimer.isEmpty()) {
					for(int i=0; i<ChatBubbleNametags.messageTimer.size(); i++) {
						if(ChatBubbleNametags.messageTimer.get(i)>0) {
							ChatBubbleNametags.messageTimer.set(i, ChatBubbleNametags.messageTimer.get(i)-1);
						} else {
							ChatBubbleNametags.chatSenderMessages.remove(i);
							ChatBubbleNametags.chatSenderMessages2.remove(i);
							ChatBubbleNametags.chatSenderNames.remove(i);
							ChatBubbleNametags.messageTimer.remove(i);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
