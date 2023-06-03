package io.github.anonymousacid.pigeon.features.chat.kaomojis;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.lwjgl.input.Keyboard;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.github.anonymousacid.pigeon.handlers.ConfigHandler;
import io.github.anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ChatKaomojis {
	
	public static ChatKaomojis instance = new ChatKaomojis();
	Minecraft mc = Minecraft.getMinecraft();
	public ArrayList<String> typedChars = new ArrayList<String>();
	public static boolean done = false;
	public static String result = "";
	public static ArrayList<String> kaomojiMatches = new ArrayList<String>();
	private static String currentMsg = "";
	public static boolean chatOpen = false;
	public static boolean showSuggestions = false;
	public static int foundMatches = 0;
	private static boolean clearedSentMsgs = false;
	public static int selectedIndex = 0;
	public static int index1 = 0;
	public static int index2 = 1;
	public static int index3 = 2;
	private static int cursorPos = 1;
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onTick(TickEvent.ClientTickEvent event) throws Exception {
		if(!ConfigHandler.chatKaomojis) return;
		if(!showSuggestions && clearedSentMsgs) {
			kaomojiMatches.clear();
			clearedSentMsgs = false;
		}
		
		if(showSuggestions) {
			if(FMLClientHandler.instance().isGUIOpen(GuiChat.class)) {
				try {
					Utils.getChatInputField().setText(currentMsg);
					Utils.getChatInputField().setCursorPosition(cursorPos);
				} catch (Error e) {}
			} else {
				showSuggestions = false;
			}
		}
		if(ChatKaomojiData.kaomojiTimer != 0) {
			ChatKaomojiData.kaomojiTimer--;
		} else {
			ChatKaomojiData.kaomojiTimer = 5;
			if(FMLClientHandler.instance().isGUIOpen(GuiChat.class)) {
				chatOpen=true;
				Gson gson = new Gson();
				InputStream in = this.getClass().getResourceAsStream("/assets/pigeon/data/kaomojis.json");
				byte[] bytes = IOUtils.toByteArray(in);
				String kaomojiJSON = new String(bytes, StandardCharsets.UTF_8);
				JsonArray kaomojis = gson.fromJson(kaomojiJSON, JsonArray.class);
				try {
					int firstColon = 0;
					if(Keyboard.getEventKey()==Keyboard.KEY_TAB && Keyboard.getEventKeyState() && ChatKaomojiData.tabKeyTimer == 0) {
						foundMatches = 0;
						currentMsg = Utils.getChatInputField().getText();
						cursorPos = Utils.getChatInputField().getCursorPosition();
						ChatKaomojiData.tabKeyTimer = 6;
						GuiTextField inputField = Utils.getChatInputField();
						FontRenderer renderer = mc.fontRendererObj;
						String substringText = inputField.getText().substring(0, inputField.getCursorPosition());
						substringText = substringText.substring(substringText.lastIndexOf(":"));
						substringText = substringText.substring(1);	
						if(substringText.contains(" ")) {
							substringText = null;
						}
						if(substringText != null) {
							kaomojiMatches.clear();
							String kaomojiInput = substringText;
							for(JsonElement e : kaomojis) {
								JsonObject obj = e.getAsJsonObject();
								JsonArray tags = obj.get("tags").getAsJsonArray();
								String kaomoji = obj.get("string").getAsString();
								for(int i=0; i<obj.get("tags").getAsJsonArray().size(); i++) {
									if(tags.get(i).getAsString().startsWith(kaomojiInput)) {
										foundMatches = foundMatches+1;
										if(kaomojiMatches.size()>=4 && kaomojiMatches.size() != 0) {
											if(kaomojiMatches.get(0) != kaomojiMatches.get(1)) {
												kaomojiMatches.set(0, kaomojiMatches.get(1));
											}
											if(kaomojiMatches.get(1) != kaomojiMatches.get(2)) {
												kaomojiMatches.set(1, kaomojiMatches.get(2));
											}
											if(kaomojiMatches.get(2) != kaomojiMatches.get(3)) {
												kaomojiMatches.set(2, kaomojiMatches.get(3));
											}
											if(kaomojiMatches.get(3) != kaomoji) {
												kaomojiMatches.set(3, kaomoji);
											}
										} else if(kaomojiMatches.size()!=0) {
											if(kaomojiMatches.get(kaomojiMatches.size()-1)!=kaomoji) {
												kaomojiMatches.add(kaomoji);
											}
										} else {
											kaomojiMatches.add(kaomoji);
										}
									}
								}
							}
						}
						if(showSuggestions) {
							showSuggestions = false;
							kaomojiMatches.clear();
						} else if(!showSuggestions && substringText != null){
							showSuggestions = true;
						}
						
						if(foundMatches == 0) {
							showSuggestions = false;
						}
					} else if(Keyboard.getEventKey()==Keyboard.KEY_SPACE && Keyboard.getEventKeyState() && showSuggestions) {
						String str = currentMsg.substring(0, cursorPos);
						String str1 = str.substring(0, str.lastIndexOf(":"));
						String str2 = KaomojiGui.selectedKaomoji;
						String str3 = currentMsg.substring(cursorPos);
						try {
							showSuggestions = false;
							Utils.getChatInputField().setText(str1+str2+str3);
						} catch (Error e) {
							e.printStackTrace();
						}
						kaomojiMatches.clear();
					} else {
						if(selectedIndex < 0 && selectedIndex == -1) {
							selectedIndex = 0;
						} else if (selectedIndex > 2) {
							selectedIndex = 2;
						}
					}
					if(Keyboard.getEventKey() == Keyboard.KEY_UP  && Keyboard.getEventKeyState() && ChatKaomojiData.scrollTimer == 0 && showSuggestions) {
						clearedSentMsgs = true;
						ChatKaomojiData.scrollTimer = 6;
						if(selectedIndex == 2) {
							index1 = 1;
							index2 = 2;
							index3 = 3;
						} else {
							selectedIndex = selectedIndex+1;
						}
					} else if(Keyboard.getEventKey() == Keyboard.KEY_DOWN && Keyboard.getEventKeyState() && ChatKaomojiData.scrollTimer == 0 && showSuggestions) {
						clearedSentMsgs = true;
						ChatKaomojiData.scrollTimer = 6;
						if(selectedIndex == 0 && index3 == 3) {
							index1 = 0;
							index2 = 1;
							index3 = 2;
							selectedIndex = 2;
						} else {
							selectedIndex = selectedIndex-1;
						}
					}
				} catch (Exception e) {
					
				}
				
				/**
				 * Searches the input field for kaomoji names surrounded by colons and replaces them with the kaomoji.
				 */
				if(ChatKaomojiData.globalSearchTimer == 0) {
					try {
						GuiTextField inputField = Utils.getChatInputField();
						FontRenderer renderer = mc.fontRendererObj;
						for(JsonElement e : kaomojis) {
							JsonObject obj = e.getAsJsonObject();
							JsonArray tags = obj.get("tags").getAsJsonArray();
							String kaomoji = obj.get("string").getAsString();
							for(int i=0; i<tags.size(); i++) {
								if(inputField.getText().contains(":"+tags.get(i).getAsString()+":")) {
									inputField.setText(inputField.getText().replace(":"+tags.get(i).getAsString()+":", kaomoji));
								}
							}
						}
					} catch (Error e) {
						
					}
				}
			} else {
				chatOpen = false;
				showSuggestions = false;
				kaomojiMatches.clear();
			}
		}
	}
}
