package io.github.anonymousacid.pigeon.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWishEffect extends ModelBase {
	private final ModelRenderer base;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer rings;
	private final ModelRenderer ring2_r1;
	private final ModelRenderer ring2_r2;
	private final ModelRenderer plus1;
	private final ModelRenderer cube_r3;
	private final ModelRenderer plus2;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer plus3;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;

	public ModelWishEffect() {
		textureWidth = 32;
		textureHeight = 32;

		base = new ModelRenderer(this);
		base.setRotationPoint(-0.5F, 23.5F, 0.5F);
		base.cubeList.add(new ModelBox(base, 0, 0, -5.5F, -0.5F, 6.5F, 11, 1, 1, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -6.5F, -0.5F, 5.5F, 1, 1, 1, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, 5.5F, -0.5F, 5.5F, 1, 1, 1, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, 5.5F, -0.5F, -6.5F, 1, 1, 1, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -6.5F, -0.5F, -6.5F, 1, 1, 1, 0.0F, false));

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.5F, 0.5F, -0.5F);
		base.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, -1.5708F, 0.0F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 0, -5.0F, -1.0F, -7.0F, 11, 1, 1, 0.0F, false));
		cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 0, -5.0F, -1.0F, 7.0F, 11, 1, 1, 0.0F, false));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.5F, 0.5F, -0.5F);
		base.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 3.1416F, 0.0F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 0, -5.0F, -1.0F, 6.0F, 11, 1, 1, 0.0F, false));

		rings = new ModelRenderer(this);
		rings.setRotationPoint(-0.5F, 19.0F, 0.5F);
		rings.cubeList.add(new ModelBox(rings, 3, 10, -5.5F, 2.0F, 6.5F, 11, 1, 1, 0.0F, false));
		rings.cubeList.add(new ModelBox(rings, 3, 10, -6.5F, 2.0F, 5.5F, 1, 1, 1, 0.0F, false));
		rings.cubeList.add(new ModelBox(rings, 3, 10, 5.5F, 2.0F, 5.5F, 1, 1, 1, 0.0F, false));
		rings.cubeList.add(new ModelBox(rings, 3, 10, 5.5F, 2.0F, -6.5F, 1, 1, 1, 0.0F, false));
		rings.cubeList.add(new ModelBox(rings, 3, 10, -6.5F, 2.0F, -6.5F, 1, 1, 1, 0.0F, false));
		rings.cubeList.add(new ModelBox(rings, 0, 7, -5.5F, -3.0F, 6.5F, 11, 1, 1, 0.0F, false));
		rings.cubeList.add(new ModelBox(rings, 0, 7, -6.5F, -3.0F, 5.5F, 1, 1, 1, 0.0F, false));
		rings.cubeList.add(new ModelBox(rings, 0, 7, 5.5F, -3.0F, 5.5F, 1, 1, 1, 0.0F, false));
		rings.cubeList.add(new ModelBox(rings, 0, 7, 5.5F, -3.0F, -6.5F, 1, 1, 1, 0.0F, false));
		rings.cubeList.add(new ModelBox(rings, 0, 7, -6.5F, -3.0F, -6.5F, 1, 1, 1, 0.0F, false));

		ring2_r1 = new ModelRenderer(this);
		ring2_r1.setRotationPoint(0.5F, -2.0F, -0.5F);
		rings.addChild(ring2_r1);
		setRotationAngle(ring2_r1, 0.0F, -1.5708F, 0.0F);
		ring2_r1.cubeList.add(new ModelBox(ring2_r1, 0, 7, -5.0F, -1.0F, -7.0F, 11, 1, 1, 0.0F, false));
		ring2_r1.cubeList.add(new ModelBox(ring2_r1, 0, 7, -5.0F, -1.0F, 7.0F, 11, 1, 1, 0.0F, false));
		ring2_r1.cubeList.add(new ModelBox(ring2_r1, 3, 10, -5.0F, 4.0F, -7.0F, 11, 1, 1, 0.0F, false));
		ring2_r1.cubeList.add(new ModelBox(ring2_r1, 3, 10, -5.0F, 4.0F, 7.0F, 11, 1, 1, 0.0F, false));

		ring2_r2 = new ModelRenderer(this);
		ring2_r2.setRotationPoint(0.5F, -2.0F, -0.5F);
		rings.addChild(ring2_r2);
		setRotationAngle(ring2_r2, 0.0F, 3.1416F, 0.0F);
		ring2_r2.cubeList.add(new ModelBox(ring2_r2, 0, 7, -5.0F, -1.0F, 6.0F, 11, 1, 1, 0.0F, false));
		ring2_r2.cubeList.add(new ModelBox(ring2_r2, 3, 10, -5.0F, 4.0F, 6.0F, 11, 1, 1, 0.0F, false));

		plus1 = new ModelRenderer(this);
		plus1.setRotationPoint(10.5F, 22.5F, 2.5F);
		plus1.cubeList.add(new ModelBox(plus1, 1, 7, -1.5F, -0.5F, -0.5F, 3, 1, 1, 0.0F, false));

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(-0.5F, 0.5F, 0.5F);
		plus1.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 0.0F, 1.5708F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 1, 7, -2.0F, -1.0F, -1.0F, 3, 1, 1, 0.0F, false));

		plus2 = new ModelRenderer(this);
		plus2.setRotationPoint(-1.8772F, 22.5F, -11.6964F);
		

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(-0.1228F, 0.5F, 0.6964F);
		plus2.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.6109F, 0.0F, 1.5708F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 17, 12, -2.0F, -1.0F, -1.0F, 3, 1, 1, 0.0F, false));

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(-0.1228F, 0.5F, 0.6964F);
		plus2.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0F, 0.6109F, 0.0F);
		cube_r5.cubeList.add(new ModelBox(cube_r5, 17, 12, -1.0F, -1.0F, -1.0F, 3, 1, 1, 0.0F, false));

		plus3 = new ModelRenderer(this);
		plus3.setRotationPoint(-9.8772F, 22.5F, 11.3036F);
		

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(-0.1228F, 0.5F, 0.6964F);
		plus3.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.6109F, 0.0F, 1.5708F);
		cube_r6.cubeList.add(new ModelBox(cube_r6, 17, 12, -2.0F, -1.0F, -1.0F, 3, 1, 1, 0.0F, false));

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(-0.1228F, 0.5F, 0.6964F);
		plus3.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.0F, 0.6109F, 0.0F);
		cube_r7.cubeList.add(new ModelBox(cube_r7, 17, 12, -1.0F, -1.0F, -1.0F, 3, 1, 1, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		base.render(f5);
		rings.render(f5);
		plus1.render(f5);
		plus2.render(f5);
		plus3.render(f5);
		if(rings.offsetY <= -1) {
			rings.offsetY = 0;
		}
		if(plus1.offsetY <= -0.9f) {
			plus1.offsetY = 0;
			plus2.offsetY = 0;
			plus3.offsetY = 0;
		}
		plus1.rotateAngleY += 0.4;
		plus2.rotateAngleY += 0.4;
		plus3.rotateAngleY += 0.4;
		plus1.offsetY -= 0.1;
		plus2.offsetY -= 0.1;
		plus3.offsetY -= 0.1;
		
		rings.offsetY -= 0.15;
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
