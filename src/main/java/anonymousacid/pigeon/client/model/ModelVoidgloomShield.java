package anonymousacid.pigeon.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelVoidgloomShield extends ModelBase {
	private final ModelRenderer body;
	private final ModelRenderer layer1;
	private final ModelRenderer layer2;
	private final ModelRenderer handle;

	public ModelVoidgloomShield() {
		
		textureWidth = 32;
		textureHeight = 32;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(body, 0.0F, -1.5708F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 0, -4.0F, 10.0F, -6.0F, 2, 12, 12, 0.0F, false));

		layer1 = new ModelRenderer(this);
		layer1.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(layer1, 0.0F, -1.5708F, 0.0F);
		layer1.cubeList.add(new ModelBox(layer1, 1, 12, -1.0F, 9.0F, -6.0F, -1, 1, 12, 0.0F, false));
		layer1.cubeList.add(new ModelBox(layer1, 29, 25, -2.0F, 23.0F, -6.0F, 1, -14, -1, 0.0F, false));
		layer1.cubeList.add(new ModelBox(layer1, 1, 12, -1.0F, 22.0F, -6.0F, -1, 1, 12, 0.0F, false));
		layer1.cubeList.add(new ModelBox(layer1, 29, 25, -2.0F, 23.0F, 7.0F, 1, -14, -1, 0.0F, false));

		layer2 = new ModelRenderer(this);
		layer2.setRotationPoint(2.0F, -1.0F, 1.0F);
		setRotationAngle(layer2, 0.0F, -1.5708F, 0.0F);
		layer2.cubeList.add(new ModelBox(layer2, 0, 9, -1.0F, 9.0F, -6.0F, -1, 1, 16, 0.0F, false));
		layer2.cubeList.add(new ModelBox(layer2, 0, 9, -1.0F, 24.0F, -6.0F, -1, 1, 16, 0.0F, false));
		layer2.cubeList.add(new ModelBox(layer2, 17, 25, -2.0F, 24.0F, -5.0F, 1, -14, -1, 0.0F, false));
		layer2.cubeList.add(new ModelBox(layer2, 28, 25, -2.0F, 24.0F, 10.0F, 1, -14, -1, 0.0F, false));

		handle = new ModelRenderer(this);
		handle.setRotationPoint(0.0F, 24.0F, 0.0F);
		handle.cubeList.add(new ModelBox(handle, 25, 1, 2.0F, -9.0F, -2.0F, 1, 2, 2, 0.0F, false));
		handle.cubeList.add(new ModelBox(handle, 18, 1, -3.0F, -9.0F, -2.0F, 1, 2, 2, 0.0F, false));
		handle.cubeList.add(new ModelBox(handle, 18, 0, -3.0F, -9.0F, 0.0F, 6, 2, 1, 0.0F, false));
		
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		body.render(f5);
		layer1.render(f5);
		layer2.render(f5);
		handle.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
