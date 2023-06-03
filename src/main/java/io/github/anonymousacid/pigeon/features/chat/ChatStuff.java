package io.github.anonymousacid.pigeon.features.chat;

import static io.github.anonymousacid.pigeon.McIf.*;

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
/**
 * This class just gets private fields from the in-game chat object which can be used by other classes.
 */
public class ChatStuff {
	public static List<ChatLine> chatLines = Lists.<ChatLine>newArrayList();
	public static int scrollPos = 0;
	
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e) {
		if(e.phase != TickEvent.Phase.START) return;
		try {
			GuiNewChat chat = mc.ingameGUI.getChatGUI();
			Field allChatLines;
			allChatLines = GuiNewChat.class.getDeclaredField("field_146253_i");
			allChatLines.setAccessible(true);
			List<ChatLine> list = (List<ChatLine>)allChatLines.get(chat);
			if(list.size()==0) {
				allChatLines = GuiNewChat.class.getDeclaredField("chatLines");
				allChatLines.setAccessible(true);
				list = (List<ChatLine>)allChatLines.get(chat);
			}
			chatLines = list;
			
			Field scrollField;
			scrollField = GuiNewChat.class.getDeclaredField("scrollPos");
			scrollField.setAccessible(true);
			int scroll = (int)scrollField.get(chat);
			scrollPos = scroll;
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException er) {
			
		}
	}
}
