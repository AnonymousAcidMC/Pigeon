package io.github.anonymousacid.pigeon.init;

import io.github.anonymousacid.pigeon.features.chat.Kaomojis;
import io.github.anonymousacid.pigeon.proxy.ClientProxy;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class Keybinds {

	public static Keybinds instance = new Keybinds();

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onKeyPressed(KeyInputEvent event) {
		
		KeyBinding[] keyBinds = ClientProxy.keyBindings;

		if (keyBinds[0].isPressed()) { //kaomoji suggestions
			Kaomojis.showSuggestions = !Kaomojis.showSuggestions;
		}
	}
	
}
