package anonymousacid.pigeon.client.render;

import anonymousacid.pigeon.Reference;
import anonymousacid.pigeon.client.fakeentities.EntityWishEffect;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWishEffect extends RenderLiving<EntityWishEffect>{
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/wish_effect.png");
	
	public RenderWishEffect(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityWishEffect entity) {
		return TEXTURE;
	}
}
