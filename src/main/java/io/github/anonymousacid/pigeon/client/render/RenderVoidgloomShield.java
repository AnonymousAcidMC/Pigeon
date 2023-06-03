package io.github.anonymousacid.pigeon.client.render;

import io.github.anonymousacid.pigeon.Reference;
import io.github.anonymousacid.pigeon.client.fakeentities.EntityVoidgloomShield;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVoidgloomShield extends RenderLiving<EntityVoidgloomShield>{
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/voidgloomshield.png");
	
	public RenderVoidgloomShield(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityVoidgloomShield entity) {
		return TEXTURE;
	}
	
}
