package anonymousacid.pigeon.client.model;

import anonymousacid.pigeon.client.fakeentities.EntityPigeon2;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class ModelPigeon2 extends ModelBase {
	private final ModelRenderer leftLeg;
	private byte leftLegXDir = -1;
	private final ModelRenderer rightLeg;
	private byte rightLegXDir = 1;
	
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer tail;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer wing1;
	private final ModelRenderer wing2;
	
	private static double maxLegXRot = Math.toRadians(70);
	private static double legXRotIncrement = Math.toRadians(10);

	public ModelPigeon2() {
		textureWidth = 64;
		textureHeight = 64;

		leftLeg = new ModelRenderer(this);
		leftLeg.setRotationPoint(2.5F, 20.0F, -0.5F);
		leftLeg.cubeList.add(new ModelBox(leftLeg, 6, 20, -0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F, false));
		leftLeg.cubeList.add(new ModelBox(leftLeg, 0, 26, -0.5F, 3.0F, -1.5F, 1, 1, 1, 0.0F, false));
		leftLeg.cubeList.add(new ModelBox(leftLeg, 0, 26, -1.5F, 3.0F, -0.5F, 1, 1, 1, 0.0F, false));
		leftLeg.cubeList.add(new ModelBox(leftLeg, 0, 26, 0.5F, 3.0F, -0.5F, 1, 1, 1, 0.0F, false));
		leftLeg.cubeList.add(new ModelBox(leftLeg, 12, 50, -1.5F, -1.0F, -1.5F, 3, 2, 3, 0.0F, false));

		rightLeg = new ModelRenderer(this);
		rightLeg.setRotationPoint(-2.5F, 20.0F, 0.0F);
		rightLeg.cubeList.add(new ModelBox(rightLeg, 6, 20, -0.5F, 0.0F, -1.0F, 1, 4, 1, 0.0F, false));
		rightLeg.cubeList.add(new ModelBox(rightLeg, 0, 26, -0.5F, 3.0F, -2.0F, 1, 1, 1, 0.0F, false));
		rightLeg.cubeList.add(new ModelBox(rightLeg, 0, 26, -1.5F, 3.0F, -1.0F, 1, 1, 1, 0.0F, false));
		rightLeg.cubeList.add(new ModelBox(rightLeg, 0, 26, 0.5F, 3.0F, -1.0F, 1, 1, 1, 0.0F, false));
		rightLeg.cubeList.add(new ModelBox(rightLeg, 35, 47, -1.5F, -2.0F, -2.0F, 3, 3, 3, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 17.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 31, 14, -3.0F, 2.0F, -4.0F, 6, 1, 7, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 26, -5.0F, 1.0F, -5.0F, 10, 1, 9, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 0, -5.0F, -2.0F, -6.0F, 10, 3, 11, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 14, -5.0F, -3.0F, -7.0F, 10, 1, 11, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 29, 26, -4.0F, -4.0F, -7.0F, 8, 1, 8, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 31, 0, -3.0F, -5.0F, -7.0F, 6, 1, 7, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 31, 8, -5.0F, -2.0F, -7.0F, 10, 1, 1, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 12.0F, -3.5F);
		head.cubeList.add(new ModelBox(head, 33, 35, -3.0F, -1.0F, -2.5F, 6, 1, 5, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 18, -2.0F, 0.0F, -2.5F, 4, 1, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 46, 46, -2.0F, 0.0F, -1.5F, 4, 1, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 18, -2.0F, 0.0F, 1.5F, 4, 1, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 18, -2.0F, -1.0F, -3.5F, 4, 1, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 18, -2.0F, -1.0F, 2.5F, 4, 1, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 31, 41, -2.0F, -2.0F, -2.5F, 4, 1, 5, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 12, 45, -2.0F, -3.0F, -2.5F, 4, 1, 4, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 48, 22, -2.0F, -4.0F, -2.5F, 4, 1, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 23, -1.0F, -3.0F, -3.5F, 2, 1, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 7, 14, -3.0F, -3.0F, -1.5F, 1, 1, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 7, 14, 2.0F, -3.0F, -1.5F, 1, 1, 1, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 15.0F, 4.0F);
		

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 10.0F, -5.0F);
		tail.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.48F, 0.0F, 0.0F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 16, 40, -3.0F, -11.0F, 2.0F, 6, 1, 4, 0.0F, false));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, 11.0F, -4.0F);
		tail.addChild(cube_r2);
		setRotationAngle(cube_r2, -0.48F, 0.0F, 0.0F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 44, 41, -2.0F, -11.0F, 2.0F, 4, 1, 4, 0.0F, false));

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, 13.0F, -1.0F);
		tail.addChild(cube_r3);
		setRotationAngle(cube_r3, -0.48F, 0.0F, 0.0F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 0, 14, -1.0F, -11.0F, 4.0F, 2, 1, 3, 0.0F, false));

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, 12.0F, -2.0F);
		tail.addChild(cube_r4);
		setRotationAngle(cube_r4, -0.48F, 0.0F, 0.0F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 44, 41, -2.0F, -11.0F, 2.0F, 4, 1, 4, 0.0F, false));

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(0.0F, 11.0F, -3.0F);
		tail.addChild(cube_r5);
		setRotationAngle(cube_r5, -0.3927F, 0.0F, 0.0F);
		cube_r5.cubeList.add(new ModelBox(cube_r5, 42, 10, -3.0F, -11.0F, 2.0F, 6, 1, 3, 0.0F, false));

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(0.0F, 10.0F, -4.0F);
		tail.addChild(cube_r6);
		setRotationAngle(cube_r6, -0.3927F, 0.0F, 0.0F);
		cube_r6.cubeList.add(new ModelBox(cube_r6, 9, 36, -4.0F, -11.0F, 1.0F, 8, 1, 3, 0.0F, false));

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(0.0F, 9.0F, -5.0F);
		tail.addChild(cube_r7);
		setRotationAngle(cube_r7, -0.2182F, 0.0F, 0.0F);
		cube_r7.cubeList.add(new ModelBox(cube_r7, 31, 22, -4.0F, -10.0F, 3.0F, 8, 1, 2, 0.0F, false));

		wing1 = new ModelRenderer(this);
		wing1.setRotationPoint(5.0F, 14.0F, 0.0F);
		wing1.cubeList.add(new ModelBox(wing1, 0, 36, 0.0F, 1.0F, -4.0F, 1, 3, 7, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 0, 46, 1.0F, 1.0F, -3.0F, 1, 3, 5, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 50, 0, 0.0F, 4.0F, -3.0F, 1, 1, 5, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 0, 0, 1.0F, 2.0F, 2.0F, 1, 3, 1, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 0, 5, 1.0F, 4.0F, -2.0F, 1, 1, 4, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 6, 7, 1.0F, 4.0F, 3.0F, 1, 1, 1, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 6, 5, 1.0F, 5.0F, 2.0F, 1, 1, 1, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 0, 20, 1.0F, 5.0F, 0.0F, 1, 1, 2, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 0, 5, 1.0F, 2.0F, 3.0F, 1, 2, 1, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 0, 0, 0.0F, 0.0F, -2.0F, 1, 1, 4, 0.0F, false));

		wing2 = new ModelRenderer(this);
		wing2.setRotationPoint(-5.0F, 14.0F, 0.0F);
		wing2.cubeList.add(new ModelBox(wing2, 0, 36, -1.0F, 1.0F, -4.0F, 1, 3, 7, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 23, 45, -2.0F, 1.0F, -2.0F, 1, 3, 5, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 50, 0, -1.0F, 4.0F, -3.0F, 1, 1, 5, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 6, 0, -2.0F, 2.0F, -3.0F, 1, 2, 1, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 0, 5, -2.0F, 4.0F, -2.0F, 1, 1, 4, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 6, 7, -2.0F, 4.0F, 2.0F, 1, 1, 1, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 6, 5, -2.0F, 5.0F, 1.0F, 1, 1, 1, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 0, 20, -2.0F, 5.0F, -1.0F, 1, 1, 2, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 0, 5, -2.0F, 2.0F, 3.0F, 1, 2, 1, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 0, 0, -1.0F, 0.0F, -2.0F, 1, 1, 4, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		leftLeg.render(f5);
		rightLeg.render(f5);
		body.render(f5);
		head.render(f5);
		tail.render(f5);
		wing1.render(f5);
		wing2.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_,
			float partialTickTime) {
		EntityPigeon2 pigeon = (EntityPigeon2) entitylivingbaseIn;
		
        if(pigeon.hasMoved() && pigeon.onGround) {
        	rotateLeftLegWalk();
        	rotateRightLegWalk();
        }
        else {
        	interpolateLegsStop();
        }
	}
	
	@Override
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_,
			float p_78087_5_, float p_78087_6_, Entity entityIn) {
		EntityPigeon2 pigeon = (EntityPigeon2) entityIn;
		
		head.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
        head.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);
        head.rotateAngleX = MathHelper.clamp_float(head.rotateAngleX, (float)-Math.PI/2, (float)Math.PI/6);
        
	}
	
	
	void rotateLeftLegWalk() {
		if(Math.abs(leftLeg.rotateAngleX) < maxLegXRot) {
			leftLeg.rotateAngleX += legXRotIncrement*leftLegXDir;
		}
		else {
			leftLeg.rotateAngleX -= legXRotIncrement*leftLegXDir;
			leftLegXDir *= -1;
		}
	}
	
	void rotateRightLegWalk() {
		
	}

	void interpolateLegsStop() {
		leftLeg.rotateAngleX = 0;
		rightLeg.rotateAngleX = 0;
	}
}
