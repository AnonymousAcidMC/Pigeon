package io.github.anonymousacid.pigeon.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHealerWish extends ModelBase{
	private final ModelRenderer hand1;
	private final ModelRenderer hand2;
	private final ModelRenderer bb_main;

	public ModelHealerWish() {
		textureWidth = 32;
		textureHeight = 32;

		hand1 = new ModelRenderer(this);
		hand1.setRotationPoint(0.0F, 22.0F, -1.0F);
		hand1.cubeList.add(new ModelBox(hand1, 10, 0, -1.0F, -16.0F, -1.0F, 1, 15, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 9, 0, -1.0F, -11.0F, -3.0F, 1, 10, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 11, 0, -1.0F, -14.0F, -2.0F, 1, 13, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 9, 0, -1.0F, -8.0F, -4.0F, 1, 7, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 0, 0, -1.0F, -17.0F, -1.0F, 1, 1, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 0, 0, -1.0F, -16.0F, -2.0F, 1, 2, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 0, 0, -1.0F, -14.0F, -3.0F, 1, 3, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 0, 0, -1.0F, -11.0F, -4.0F, 1, 3, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 0, 0, -1.0F, -8.0F, -5.0F, 1, 5, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 17, 0, -1.0F, -2.0F, -5.0F, 1, 3, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 17, 0, -1.0F, -1.0F, -6.0F, 1, 2, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 17, 0, -1.0F, 0.0F, -7.0F, 1, 1, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 23, 0, -1.0F, -1.0F, -1.0F, 1, 1, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 23, 0, -1.0F, 0.0F, -2.0F, 1, 1, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 23, 0, -1.0F, 1.0F, -7.0F, 1, 1, 5, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 23, 0, -1.0F, -3.0F, -5.0F, 1, 1, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 23, 0, -1.0F, -2.0F, -6.0F, 1, 1, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 23, 0, -1.0F, -1.0F, -7.0F, 1, 1, 1, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 17, 0, -1.0F, -1.0F, -4.0F, 1, 2, 2, 0.0F, false));
		hand1.cubeList.add(new ModelBox(hand1, 18, 0, -1.0F, -1.0F, -2.0F, 1, 1, 1, 0.0F, false));

		hand2 = new ModelRenderer(this);
		hand2.setRotationPoint(-2.0F, 24.0F, 2.0F);
		hand2.cubeList.add(new ModelBox(hand2, 10, 0, 1.0F, -18.0F, -2.0F, 1, 15, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 10, 1, 1.0F, -13.0F, 0.0F, 1, 10, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 10, 0, 1.0F, -16.0F, -1.0F, 1, 13, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 10, 1, 1.0F, -10.0F, 1.0F, 1, 7, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 0, 0, 1.0F, -19.0F, -2.0F, 1, 1, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 0, 0, 1.0F, -18.0F, -1.0F, 1, 2, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 0, 0, 1.0F, -16.0F, 0.0F, 1, 3, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 0, 0, 1.0F, -13.0F, 1.0F, 1, 3, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 0, 0, 1.0F, -10.0F, 2.0F, 1, 5, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 23, 0, 1.0F, -3.0F, -2.0F, 1, 1, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 23, 0, 1.0F, -2.0F, -1.0F, 1, 1, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 23, 0, 1.0F, -1.0F, 0.0F, 1, 1, 5, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 17, 0, 1.0F, -2.0F, 0.0F, 1, 1, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 0, 18, 1.0F, -3.0F, -1.0F, 1, 1, 2, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 17, 0, 1.0F, -3.0F, 1.0F, 1, 2, 2, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 17, 0, 1.0F, -4.0F, 2.0F, 1, 3, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 17, 0, 1.0F, -3.0F, 3.0F, 1, 2, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 17, 0, 1.0F, -2.0F, 4.0F, 1, 1, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 23, 0, 1.0F, -5.0F, 2.0F, 1, 1, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 23, 0, 1.0F, -4.0F, 3.0F, 1, 1, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 23, 0, 1.0F, -3.0F, 4.0F, 1, 1, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 23, 0, 1.0F, -2.0F, 5.0F, 1, 1, 1, 0.0F, false));
		hand2.cubeList.add(new ModelBox(hand2, 23, 0, 1.0F, -2.0F, -11.0F, 1, 1, 1, 0.0F, false));

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -1.0F, -18.0F, -1.0F, 1, 15, 1, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		hand1.render(f5);
		hand2.render(f5);
		bb_main.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
