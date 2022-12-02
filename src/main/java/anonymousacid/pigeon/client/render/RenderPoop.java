package anonymousacid.pigeon.client.render;

import anonymousacid.pigeon.Reference;
import anonymousacid.pigeon.client.fakeentities.EntityPoop;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPoop extends RenderLiving<EntityPoop>{
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/pigeon_poop.png");

	public RenderPoop(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPoop entity) {
		return TEXTURE;
	}
	
}
