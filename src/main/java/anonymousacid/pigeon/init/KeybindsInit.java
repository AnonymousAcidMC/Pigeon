package anonymousacid.pigeon.init;

import anonymousacid.pigeon.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

/**
 * Might be used some day
 */
public class KeybindsInit {
//	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.HIGH, receiveCanceled=true)
	public static void onEvent(KeyInputEvent event) {
		
		KeyBinding[] keyBinds = ClientProxy.keyBindings;
		
		if (keyBinds[0].isPressed()) {
			Minecraft.getMinecraft().thePlayer.sendChatMessage("REEEEE");
		}
	}
	
}
