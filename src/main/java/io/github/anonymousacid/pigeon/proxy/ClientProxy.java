package io.github.anonymousacid.pigeon.proxy;

import static io.github.anonymousacid.pigeon.McIf.mc;

import io.github.anonymousacid.pigeon.client.fakeentities.*;
import io.github.anonymousacid.pigeon.client.model.*;
import io.github.anonymousacid.pigeon.client.render.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends CommonProxy {
	
	public static KeyBinding[] keyBindings;
	
	@Override
	public void registerRenders() {
		
	}
	
	@Override
	public void registerEntityRenders() {
		RenderManager renderm = mc.getRenderManager();
		RenderingRegistry.registerEntityRenderingHandler(EntityVoidgloomShield.class, new RenderVoidgloomShield(renderm, new ModelVoidgloomShield(), 0f));
		RenderingRegistry.registerEntityRenderingHandler(EntityHealerWish.class, new RenderHealerWish(renderm, new ModelHealerWish(), 0f));

		RenderingRegistry.registerEntityRenderingHandler(EntityPigeon.class, new RenderPigeon(renderm, new ModelPigeon(), 0f));
		
		RenderingRegistry.registerEntityRenderingHandler(EntityWishEffect.class, new RenderWishEffect(renderm, new ModelWishEffect(), 0f));
		RenderingRegistry.registerEntityRenderingHandler(EntityPoop.class, new RenderPoop(renderm, new ModelPoop(), 0f));
		RenderingRegistry.registerEntityRenderingHandler(EntityFerocityMelee.class, new RenderFerocityMelee(renderm, new ModelFerocityMelee(), 0f));
	}
	
	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}
	
	@Override
	public boolean isSinglePlayer() {
		return Minecraft.getMinecraft().isSingleplayer();
	}
	
	@Override
	public boolean isDedicatedServer() {
		return false;
	}
	
	@Override
	public void preInit() {
		
	}
	
	@Override
	public void init() {

		keyBindings = new KeyBinding[0];
//		keyBindings[0] = new KeyBinding("Kaomoji Suggestions", Keyboard.KEY_TAB, "Pigeon");

		for (int i = 0; i < keyBindings.length; i++) {
			ClientRegistry.registerKeyBinding(keyBindings[i]);
		}
	}
	
	@Override
	public void registerRenderInformation() {
		
	}
}
