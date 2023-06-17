package io.github.anonymousacid.pigeon.init;

import io.github.anonymousacid.pigeon.proxy.ClientProxy;
import io.github.anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class Keybinds {

	@SubscribeEvent(priority=EventPriority.HIGH, receiveCanceled=true)
	public static void onEvent(KeyInputEvent event) {
		
		KeyBinding[] keyBinds = ClientProxy.keyBindings;
		
		if (keyBinds[0].isPressed()) {
			Utils.sendMessage("test");
		}
	}
	
}
