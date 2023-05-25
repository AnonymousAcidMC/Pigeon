package anonymousacid.pigeon.client.render;

import anonymousacid.pigeon.Reference;
import anonymousacid.pigeon.client.fakeentities.EntityPigeon;
import anonymousacid.pigeon.client.fakeentities.EntityPigeon2;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPigeon2 extends RenderLiving<EntityPigeon2> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/pigeon.png");

	public RenderPigeon2(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityPigeon2 entity) {
		return TEXTURE;
	}
}
