package io.github.anonymousacid.pigeon.client.model;

import io.github.anonymousacid.pigeon.client.fakeentities.EntityFerocityMelee;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class ModelFerocityMelee extends ModelBase {
	private ModelRenderer body;

	public ModelFerocityMelee() {
		textureWidth = 16;
		textureHeight = 16;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 0, -6.0F, -1.0F, -2.0F, 12, 1, 3, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 0, -4.0F, -1.0F, -4.0F, 8, 1, 2, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 0, -2.0F, -1.0F, -6.0F, 4, 1, 2, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 0, -7.0F, -1.0F, 1.0F, 2, 1, 4, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 0, 5.0F, -1.0F, 1.0F, 2, 1, 4, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 0, -5.0F, -1.0F, 1.0F, 3, 1, 2, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 0, 5.0F, -1.0F, 5.0F, 1, 1, 2, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 0, -6.0F, -1.0F, 5.0F, 1, 1, 2, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		body.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_,
			float partialTickTime) {
		super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
		EntityFerocityMelee entity = (EntityFerocityMelee)entitylivingbaseIn;
		body.rotateAngleX = MathHelper.wrapAngleTo180_float(entity.targPlayerPitch)/60;
		body.rotateAngleY = MathHelper.wrapAngleTo180_float(entity.targPlayerYaw)/60;
	}
}
