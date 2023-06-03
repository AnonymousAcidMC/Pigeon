package io.github.anonymousacid.pigeon.proxy;

import static io.github.anonymousacid.pigeon.McIf.mc;

import io.github.anonymousacid.pigeon.client.fakeentities.EntityFerocityMelee;
import io.github.anonymousacid.pigeon.client.fakeentities.EntityHealerWish;
import io.github.anonymousacid.pigeon.client.fakeentities.EntityPigeon;
import io.github.anonymousacid.pigeon.client.fakeentities.EntityPigeon2;
import io.github.anonymousacid.pigeon.client.fakeentities.EntityPoop;
import io.github.anonymousacid.pigeon.client.fakeentities.EntityVoidgloomShield;
import io.github.anonymousacid.pigeon.client.fakeentities.EntityWishEffect;
import io.github.anonymousacid.pigeon.client.model.ModelFerocityMelee;
import io.github.anonymousacid.pigeon.client.model.ModelHealerWish;
import io.github.anonymousacid.pigeon.client.model.ModelPigeon;
import io.github.anonymousacid.pigeon.client.model.ModelPigeon2;
import io.github.anonymousacid.pigeon.client.model.ModelPoop;
import io.github.anonymousacid.pigeon.client.model.ModelVoidgloomShield;
import io.github.anonymousacid.pigeon.client.model.ModelWishEffect;
import io.github.anonymousacid.pigeon.client.render.RenderFerocityMelee;
import io.github.anonymousacid.pigeon.client.render.RenderHealerWish;
import io.github.anonymousacid.pigeon.client.render.RenderPigeon;
import io.github.anonymousacid.pigeon.client.render.RenderPigeon2;
import io.github.anonymousacid.pigeon.client.render.RenderPoop;
import io.github.anonymousacid.pigeon.client.render.RenderVoidgloomShield;
import io.github.anonymousacid.pigeon.client.render.RenderWishEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

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
		RenderingRegistry.registerEntityRenderingHandler(EntityPigeon2.class, new RenderPigeon2(renderm, new ModelPigeon2(), 0f));
		
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
		
//		keyBindings = new KeyBinding[1];
//		keyBindings[0] = new KeyBinding("io.github.anonymousacid.pigeon.init.KeyBindsInit.GuiKeybind", Keyboard.KEY_P, "Amogus");
//		
//		for (int i = 0; i < keyBindings.length; i++) {
//			ClientRegistry.registerKeyBinding(keyBindings[i]);
//		}
	}
	
	@Override
	public void registerRenderInformation() {
		
	}
}
