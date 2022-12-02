package anonymousacid.pigeon.client.render;

import anonymousacid.pigeon.Reference;
import anonymousacid.pigeon.client.fakeentities.EntityFerocityMelee;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFerocityMelee extends RenderLiving<EntityFerocityMelee> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/ferocity_melee.png");
	
	
	public RenderFerocityMelee(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityFerocityMelee entity) {
		return TEXTURE;
	}
	
	@Override
	public void doRender(EntityFerocityMelee entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
}
