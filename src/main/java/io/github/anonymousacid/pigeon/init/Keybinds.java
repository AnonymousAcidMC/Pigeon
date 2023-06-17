package io.github.anonymousacid.pigeon.init;

import io.github.anonymousacid.pigeon.features.chat.kaomojis.Kaomojis;
import io.github.anonymousacid.pigeon.proxy.ClientProxy;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

import static io.github.anonymousacid.pigeon.McIf.*;

public class Keybinds {

	public static Keybinds instance = new Keybinds();

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onKeyPressed(KeyInputEvent event) {
		
		KeyBinding[] keyBinds = ClientProxy.keyBindings;
		if(keyBinds.length == 0) return;

		if (keyBinds[0].isPressed()) {

		}

	}
	
}
