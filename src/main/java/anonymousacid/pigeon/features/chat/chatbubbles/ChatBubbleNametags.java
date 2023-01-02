package anonymousacid.pigeon.features.chat.chatbubbles;

import static anonymousacid.pigeon.McIf.player;

import java.util.ArrayList;

import anonymousacid.pigeon.handlers.ConfigHandler;
import anonymousacid.pigeon.utils.RenderUtils;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent.Specials.Pre;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatBubbleNametags {
	
	public static ChatBubbleNametags instance = new ChatBubbleNametags();
	Minecraft mc = Minecraft.getMinecraft();
	
	//these four are parallel to each other
	public static ArrayList<String> chatSenderMessages = new ArrayList<String>();
	public static ArrayList<String> chatSenderMessages2 = new ArrayList<String>();
	public static ArrayList<String> chatSenderNames = new ArrayList<String>();
	public static ArrayList<Integer> messageTimer = new ArrayList<Integer>();
	
	/**
	 * Renders chat bubbles by using code similar to Minecraft's nametag code.
	 */
	@SubscribeEvent
	public void renderChatMessages(Pre<EntityLivingBase> e) {
		if(!ConfigHandler.chatBubbles) return;
		if(ChatBubbleNametags.messageTimer.isEmpty()) return;
		if(e.entity instanceof EntityPlayer || e.entity instanceof EntityPlayerSP || e.entity instanceof EntityOtherPlayerMP) {
			if(!Utils.canSeeEntity(player(), e.entity)) return;
			for(int i=0; i<chatSenderNames.size(); i++) {
				if(e.entity.toString().split("'")[1].equals(chatSenderNames.get(i))) {
					RenderUtils.renderFloatingText(chatSenderMessages.get(i), e.x, e.y+e.entity.height+1.25, e.z, Integer.parseInt("FFFFFF", 16), 1.5f);
					if(chatSenderMessages2.get(i) != " ") {
						RenderUtils.renderFloatingText(chatSenderMessages2.get(i), e.x, e.y+e.entity.height+1.05, e.z, Integer.parseInt("FFFFFF", 16), 1.5f);
					}
				}
			}
		}
	}
}
