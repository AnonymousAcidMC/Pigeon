package anonymousacid.pigeon.proxy;

import static anonymousacid.pigeon.McIf.minecraft;

import anonymousacid.pigeon.client.fakeentities.EntityFerocityMelee;
import anonymousacid.pigeon.client.fakeentities.EntityHealerWish;
import anonymousacid.pigeon.client.fakeentities.EntityPigeon;
import anonymousacid.pigeon.client.fakeentities.EntityPoop;
import anonymousacid.pigeon.client.fakeentities.EntityVoidgloomShield;
import anonymousacid.pigeon.client.fakeentities.EntityWishEffect;
import anonymousacid.pigeon.client.model.ModelFerocityMelee;
import anonymousacid.pigeon.client.model.ModelHealerWish;
import anonymousacid.pigeon.client.model.ModelPigeon;
import anonymousacid.pigeon.client.model.ModelPoop;
import anonymousacid.pigeon.client.model.ModelVoidgloomShield;
import anonymousacid.pigeon.client.model.ModelWishEffect;
import anonymousacid.pigeon.client.render.RenderFerocityMelee;
import anonymousacid.pigeon.client.render.RenderHealerWish;
import anonymousacid.pigeon.client.render.RenderPigeon;
import anonymousacid.pigeon.client.render.RenderPoop;
import anonymousacid.pigeon.client.render.RenderVoidgloomShield;
import anonymousacid.pigeon.client.render.RenderWishEffect;
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
		RenderManager renderm = minecraft().getRenderManager();
		RenderingRegistry.registerEntityRenderingHandler(EntityVoidgloomShield.class, new RenderVoidgloomShield(renderm, new ModelVoidgloomShield(), 0.0f));
		RenderingRegistry.registerEntityRenderingHandler(EntityHealerWish.class, new RenderHealerWish(renderm, new ModelHealerWish(), 0.0f));
		RenderingRegistry.registerEntityRenderingHandler(EntityPigeon.class, new RenderPigeon(renderm, new ModelPigeon(), 0.0f));
		RenderingRegistry.registerEntityRenderingHandler(EntityWishEffect.class, new RenderWishEffect(renderm, new ModelWishEffect(), 0.0f));
		RenderingRegistry.registerEntityRenderingHandler(EntityPoop.class, new RenderPoop(renderm, new ModelPoop(), 0.0f));
		RenderingRegistry.registerEntityRenderingHandler(EntityFerocityMelee.class, new RenderFerocityMelee(renderm, new ModelFerocityMelee(), 0.0f));
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
//		keyBindings[0] = new KeyBinding("anonymousacid.pigeon.init.KeyBindsInit.GuiKeybind", Keyboard.KEY_P, "Amogus");
//		
//		for (int i = 0; i < keyBindings.length; i++) {
//			ClientRegistry.registerKeyBinding(keyBindings[i]);
//		}
	}
	
	@Override
	public void registerRenderInformation() {
		
	}
}
