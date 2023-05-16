package anonymousacid.pigeon.features.chat.chatbubbles;

import anonymousacid.pigeon.handlers.ConfigHandler;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatBubbles {
	
	public static ChatBubbles instance = new ChatBubbles();
	Minecraft mc = Minecraft.getMinecraft();
	
	/**
	 * When getting a chat message, find the person that sent the message, and the message its self,
	 * then add that information into ArrayLists which will be rendered as floating text.
	 * 
	 * Fun Fact: this feature took around 3 days straight of researching :'D
	 */
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onChatMessage(ClientChatReceivedEvent event) {
		if(!ConfigHandler.chatBubbles) return;
		String unformattedMessage = StringUtils.stripControlCodes(event.message.getUnformattedText());
		unformattedMessage = Utils.removeChatChannelPrefix(unformattedMessage);
		String formattedMessage = event.message.getFormattedText();
		formattedMessage = Utils.removeChatChannelPrefix(formattedMessage);
		String[] hypixelRanks = {
				"[VIP]",
				"[VIP+]",
				"[MVP]",
				"[MVP++]",
				"[MVP+]",
				"[YOUTUBE]",
				"[APPLE]",
				"[BUILD-TEAM]",
				"[BUILD-TEAM+]",
				"[Ex-Helper]",
				"[JR-HELPER]", 
				"[HELPER]",
				"[MOD]",
				"[ADMIN]",
				"[MCPROHOSTING]",
				"[MOJANG]",
				"[OWNER]"};
		if(!formattedMessage.contains("â—�")) {
			if(Utils.stringContainsItemFromList(unformattedMessage, hypixelRanks)) {
				//finding the player that sent the message
				String[] splitMessage = unformattedMessage.split(":");
				String chatSenderName = splitMessage[0];
				//get chat sender's name
//				for(int i=0; i<hypixelRanks.length; i++) {
//					if(splitMessage[0].contains(hypixelRanks[i])) {
//						chatSenderName = splitMessage[0].replace(hypixelRanks[i], "");
//						chatSenderName = chatSenderName.replace(" ", "");
//						break;
//					}
//				}
				chatSenderName = chatSenderName.replaceAll("\\[.*?\\]", "");
				chatSenderName = chatSenderName.replace(" ", "");
				
				//If there was a message past the player's name in chat
				//Ex "[MVP] Player213: hello!"
				if(splitMessage.length > 1) {
					String playerMessage = splitMessage[1]; /*1st segment of player message*/
					String playerMessage2 = " "; /*2nd segment of player message if applicable*/
					
					//If the message contains more colons, then get the message as a whole including the colons.
					//Ex "[MVP] Player: this is a : message"
					//   would be split into ["[MVP] Player", " this is a ", " message"]
					//   This loop gets the full message as one string: "this is a : message"
					for(int i=1; i<splitMessage.length-1; i++) {
						if(splitMessage[i+1] != null) {
							playerMessage = playerMessage+":"+splitMessage[i+1];
						}
					}
					
					//splits message into two chat bubbles if larger than 50 characters
					if(playerMessage.length() > 50) {
						String splitPlayerMessage = playerMessage.substring(0, 50);
						int lastSpace = splitPlayerMessage.lastIndexOf(" ", splitPlayerMessage.length()-1);
						
						//If there is a space at the end of the 1st message segment
						if(lastSpace != -1) {
							//Move it from the 1st to the 2nd
							playerMessage = playerMessage.substring(0, lastSpace);
							playerMessage2 = playerMessage.substring(lastSpace);	
						} else {
							playerMessage = playerMessage.substring(0, playerMessage.length()/2);
							playerMessage2 = playerMessage.substring(playerMessage.length()/2);
						}
					}
					///If the second part of the message is STILL too long, cut off that part
					// of the message and add ellipsis at the end.
					if(playerMessage2.length() > 50) {
						playerMessage2 = playerMessage2.substring(0,50) + "...";
					}
					
					//Add the chat channel before the chat bubble to be rendered depending on which channel the
					//message was sent in.
					if(event.message.getFormattedText().startsWith("§r§2Guild")) {
						playerMessage = "§2Guild > §r" + playerMessage;
					} else if(event.message.getFormattedText().startsWith("§r§9Party")) {
						playerMessage = "§9Party §8> §r" + playerMessage;
					} else if(event.message.getFormattedText().startsWith("§r§bCo-op")) {
						playerMessage = "§bCo-op > §r" + playerMessage;
					}
					
					//Removes duplicate chat senders and their messages
					if(ChatBubbleNametags.chatSenderMessages.size() != 0 && ChatBubbleNametags.chatSenderNames.size() != 0 && ChatBubbleNametags.chatSenderMessages2.size() != 0) {
						for(int i=0; i<ChatBubbleNametags.chatSenderNames.size(); i++) {
							if(ChatBubbleNametags.chatSenderNames.get(i).equals(chatSenderName)) {
								ChatBubbleNametags.chatSenderNames.remove(i);
								ChatBubbleNametags.chatSenderMessages.remove(i);
								ChatBubbleNametags.chatSenderMessages2.remove(i);
								ChatBubbleNametags.messageTimer.remove(i);
							}
						}
					}
					ChatBubbleNametags.chatSenderNames.add(chatSenderName.replace(" ", ""));
					ChatBubbleNametags.chatSenderMessages.add(playerMessage.trim());
					ChatBubbleNametags.chatSenderMessages2.add(playerMessage2);
					ChatBubbleNametags.messageTimer.add(ConfigHandler.chatBubbleDuration);
				}
			//no-rank chat sender
			} else if(formattedMessage.replaceAll("\\[.*?\\]", "").replace(" ", "").startsWith("§7")) {
	
				String[] splitMessage = unformattedMessage.split(":");
				
				//gets no-rank chat sender's name
				String chatSenderName = splitMessage[0];
				
				chatSenderName = chatSenderName.replaceAll("\\[.*?\\]", "");
				chatSenderName = chatSenderName.trim();
				
				if(splitMessage.length > 1) {
					String playerMessage = splitMessage[1];
					String playerMessage2 = " ";
					//gets chat sender's message
					for(int i=1; i<splitMessage.length-1; i++) {
						if(splitMessage[i+1] != null) {
							playerMessage = playerMessage+":"+splitMessage[i+1];
						}
					}
					//splits message if larger than 50 characters
					String splitPlayerMessage = "";
					if(playerMessage.length()>50) {
						splitPlayerMessage = playerMessage.substring(0, 50);
						int lastSpace = splitPlayerMessage.lastIndexOf(" ", splitPlayerMessage.length()-1);
						if(lastSpace != 0-1) {
							playerMessage2 = playerMessage.substring(lastSpace);
							playerMessage = playerMessage.substring(0, lastSpace);	
						} else {
							playerMessage2 = playerMessage.substring((int)(playerMessage.length()/2));
							playerMessage = playerMessage.substring(0, (int)(playerMessage.length()/2));
						}
					}
					
					//If second part of message is too long, cut it off at 50 length and add elipsis at end.
					if(playerMessage2.length()>50) {
						playerMessage2 = playerMessage2.substring(0,50) + "...";
					}
					
					//Adds corresponding chat channel before chat message
					if(event.message.getFormattedText().startsWith("§r§2Guild")) {
						playerMessage = "§2Guild > §r" + playerMessage;
					} else if(event.message.getFormattedText().startsWith("§r§9Party")) {
						playerMessage = "§9Party §8> §r" + playerMessage;
					} else if(event.message.getFormattedText().startsWith("§r§bCo-op")) {
						playerMessage = "§bCo-op > §r" + playerMessage;
					}
					
					//Removes duplicate chat senders and their messages
					if(ChatBubbleNametags.chatSenderMessages.size() != 0 && ChatBubbleNametags.chatSenderNames.size() != 0) {
						for(int i=0; i<ChatBubbleNametags.chatSenderNames.size(); i++) {
							if(ChatBubbleNametags.chatSenderNames.get(i).equals(chatSenderName)) {
								ChatBubbleNametags.chatSenderNames.remove(i);
								ChatBubbleNametags.chatSenderMessages.remove(i);
								ChatBubbleNametags.chatSenderMessages2.remove(i);
								ChatBubbleNametags.messageTimer.remove(i);
							}
						}
					}
					ChatBubbleNametags.chatSenderNames.add(chatSenderName);
					ChatBubbleNametags.chatSenderMessages.add(playerMessage.trim());
					ChatBubbleNametags.chatSenderMessages2.add(playerMessage2);
					ChatBubbleNametags.messageTimer.add(ConfigHandler.chatBubbleDuration);
				}
			}
		}
	}
}
