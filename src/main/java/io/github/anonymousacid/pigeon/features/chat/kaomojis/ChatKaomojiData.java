package io.github.anonymousacid.pigeon.features.chat.kaomojis;

import net.minecraft.client.Minecraft;

/**
 * Class used for handling information for the chat kaomojis.
 */
public class ChatKaomojiData {
	public static boolean chatKaomojisOn = false;
	public static int kaomojiTimer = 5;
	public static int tabKeyTimer = 7;
	public static int scrollTimer = 6;
	public static int globalSearchTimer = 7;
	Minecraft mc = Minecraft.getMinecraft();
}
