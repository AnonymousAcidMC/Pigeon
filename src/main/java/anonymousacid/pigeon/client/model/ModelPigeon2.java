package anonymousacid.pigeon.client.model;

import static anonymousacid.pigeon.McIf.mc;

import anonymousacid.pigeon.client.fakeentities.EntityPigeon2;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
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
	
	private final ModelRenderer leftWing;
	private final ModelRenderer rightWing;
	private byte leftFlapDir = 1;
	private byte rightFlapDir = 1;
	
	private static double maxLegXRot = Math.toRadians(70);
	private static double legXRotIncrement = Math.toRadians(10);
	
	private static double maxWingFlapRot = Math.toRadians(100);
	private static double minWingFlapRot = Math.toRadians(0);
	private static double wingFlapIncrement = Math.toRadians(25);

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

		leftWing = new ModelRenderer(this);
		leftWing.setRotationPoint(5.0F, 14.0F, 0.0F);
		leftWing.cubeList.add(new ModelBox(leftWing, 0, 36, 0.0F, 1.0F, -4.0F, 1, 3, 7, 0.0F, false));
		leftWing.cubeList.add(new ModelBox(leftWing, 0, 46, 1.0F, 1.0F, -3.0F, 1, 3, 5, 0.0F, false));
		leftWing.cubeList.add(new ModelBox(leftWing, 50, 0, 0.0F, 4.0F, -3.0F, 1, 1, 5, 0.0F, false));
		leftWing.cubeList.add(new ModelBox(leftWing, 0, 0, 1.0F, 2.0F, 2.0F, 1, 3, 1, 0.0F, false));
		leftWing.cubeList.add(new ModelBox(leftWing, 0, 5, 1.0F, 4.0F, -2.0F, 1, 1, 4, 0.0F, false));
		leftWing.cubeList.add(new ModelBox(leftWing, 6, 7, 1.0F, 4.0F, 3.0F, 1, 1, 1, 0.0F, false));
		leftWing.cubeList.add(new ModelBox(leftWing, 6, 5, 1.0F, 5.0F, 2.0F, 1, 1, 1, 0.0F, false));
		leftWing.cubeList.add(new ModelBox(leftWing, 0, 20, 1.0F, 5.0F, 0.0F, 1, 1, 2, 0.0F, false));
		leftWing.cubeList.add(new ModelBox(leftWing, 0, 5, 1.0F, 2.0F, 3.0F, 1, 2, 1, 0.0F, false));
		leftWing.cubeList.add(new ModelBox(leftWing, 0, 0, 0.0F, 0.0F, -2.0F, 1, 1, 4, 0.0F, false));

		rightWing = new ModelRenderer(this);
		rightWing.setRotationPoint(-5.0F, 14.0F, 0.0F);
		rightWing.cubeList.add(new ModelBox(rightWing, 0, 36, -1.0F, 1.0F, -4.0F, 1, 3, 7, 0.0F, false));
		rightWing.cubeList.add(new ModelBox(rightWing, 23, 45, -2.0F, 1.0F, -2.0F, 1, 3, 5, 0.0F, false));
		rightWing.cubeList.add(new ModelBox(rightWing, 50, 0, -1.0F, 4.0F, -3.0F, 1, 1, 5, 0.0F, false));
		rightWing.cubeList.add(new ModelBox(rightWing, 6, 0, -2.0F, 2.0F, -3.0F, 1, 2, 1, 0.0F, false));
		rightWing.cubeList.add(new ModelBox(rightWing, 0, 5, -2.0F, 4.0F, -2.0F, 1, 1, 4, 0.0F, false));
		rightWing.cubeList.add(new ModelBox(rightWing, 6, 7, -2.0F, 4.0F, 2.0F, 1, 1, 1, 0.0F, false));
		rightWing.cubeList.add(new ModelBox(rightWing, 6, 5, -2.0F, 5.0F, 1.0F, 1, 1, 1, 0.0F, false));
		rightWing.cubeList.add(new ModelBox(rightWing, 0, 20, -2.0F, 5.0F, -1.0F, 1, 1, 2, 0.0F, false));
		rightWing.cubeList.add(new ModelBox(rightWing, 0, 5, -2.0F, 2.0F, 3.0F, 1, 2, 1, 0.0F, false));
		rightWing.cubeList.add(new ModelBox(rightWing, 0, 0, -1.0F, 0.0F, -2.0F, 1, 1, 4, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		leftLeg.render(f5);
		rightLeg.render(f5);
		body.render(f5);
		head.render(f5);
		tail.render(f5);
		leftWing.render(f5);
		rightWing.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	
	/**
	 * Set the rotation angles according to data in the entity being rendered
	 */
	@Override
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_,
			float p_78087_5_, float p_78087_6_, Entity entityIn) {
		
		EntityPigeon2 pigeon = (EntityPigeon2) entityIn;
		getModelTransformations(pigeon);
		
		if(mc.isGamePaused())
			return;
		
		head.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
        head.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);
        head.rotateAngleX = MathHelper.clamp_float(head.rotateAngleX, (float)-Math.PI/2, (float)Math.PI/6);
        
        
        if(pigeon.hasMoved() && pigeon.onGround) {
        	rotateLeftLegWalk();
        	rotateRightLegWalk();
        }
        else {
        	interpolateLegsStop();
        }
        
        
        if(pigeon.flapWings) {
        	leftWingFlap();
        	rightWingFlap();
        }
        else {
        	leftWing.rotateAngleZ = 0;
        	rightWing.rotateAngleZ = 0;
        }
        
        setModelTransformations(pigeon);
	}
	
	
	
	void getModelTransformations(EntityPigeon2 pigeon) {
		head.rotateAngleX = pigeon.headRot.x;
		head.rotateAngleY = pigeon.headRot.y;
		head.rotateAngleZ = pigeon.headRot.z;
		

		body.rotateAngleX = pigeon.bodyRot.x;
		body.rotateAngleY = pigeon.bodyRot.y;
		body.rotateAngleZ = pigeon.bodyRot.z;
		

		tail.rotateAngleX = pigeon.tailRot.x;
		tail.rotateAngleY = pigeon.tailRot.y;
		tail.rotateAngleZ = pigeon.tailRot.z;
		

		leftLeg.rotateAngleX = pigeon.leftLegRot.x;
		leftLeg.rotateAngleY = pigeon.leftLegRot.y;
		leftLeg.rotateAngleZ = pigeon.leftLegRot.z;
		rightLeg.rotateAngleX = pigeon.rightLegRot.x;
		rightLeg.rotateAngleY = pigeon.rightLegRot.y;
		rightLeg.rotateAngleZ = pigeon.rightLegRot.z;
		

		leftWing.rotateAngleX = pigeon.leftWingRot.x;
		leftWing.rotateAngleY = pigeon.leftWingRot.y;
		leftWing.rotateAngleZ = pigeon.leftWingRot.z;
		rightWing.rotateAngleX = pigeon.rightWingRot.x;
		rightWing.rotateAngleY = pigeon.rightWingRot.y;
		rightWing.rotateAngleZ = pigeon.rightWingRot.z;
		
		leftLegXDir = pigeon.leftLegXDir;
		rightLegXDir = pigeon.rightLegXDir;
		leftFlapDir = pigeon.leftFlapDir;
		rightFlapDir = pigeon.rightFlapDir;
	}
	
	
	void setModelTransformations(EntityPigeon2 pigeon) {
		pigeon.headRot.x = head.rotateAngleX;
		pigeon.headRot.y = head.rotateAngleY;
		pigeon.headRot.z = head.rotateAngleZ;

		pigeon.bodyRot.x = body.rotateAngleX;
		pigeon.bodyRot.y = body.rotateAngleY;
		pigeon.bodyRot.z = body.rotateAngleZ;

		pigeon.tailRot.x = tail.rotateAngleX;
		pigeon.tailRot.y = tail.rotateAngleY;
		pigeon.tailRot.z = tail.rotateAngleZ;
		

		pigeon.leftLegRot.x = leftLeg.rotateAngleX;
		pigeon.leftLegRot.y = leftLeg.rotateAngleY;
		pigeon.leftLegRot.z = leftLeg.rotateAngleZ;
		pigeon.rightLegRot.x = rightLeg.rotateAngleX;
		pigeon.rightLegRot.y = rightLeg.rotateAngleY;
		pigeon.rightLegRot.z = rightLeg.rotateAngleZ;

		
		pigeon.leftWingRot.x = leftWing.rotateAngleX;
		pigeon.leftWingRot.y = leftWing.rotateAngleY;
		pigeon.leftWingRot.z = leftWing.rotateAngleZ;
		pigeon.rightWingRot.x = rightWing.rotateAngleX;
		pigeon.rightWingRot.y = rightWing.rotateAngleY;
		pigeon.rightWingRot.z = rightWing.rotateAngleZ;
		
		pigeon.leftLegXDir = leftLegXDir;
		pigeon.rightLegXDir = rightLegXDir;
		pigeon.leftFlapDir = leftFlapDir;
		pigeon.rightFlapDir = leftFlapDir;
	}
	
	
	
	
	void leftWingFlap() {
		if(leftFlapDir == 1 && leftWing.rotateAngleZ > -maxWingFlapRot) {
			leftWing.rotateAngleZ -= wingFlapIncrement;
		}
		else if(leftFlapDir == -1 && leftWing.rotateAngleZ < minWingFlapRot) {
			leftWing.rotateAngleZ += wingFlapIncrement;
		}
		else {
			leftFlapDir *= -1;
		}
	}
	
	
	void rightWingFlap() {
		if(rightFlapDir == 1 && rightWing.rotateAngleZ > -maxWingFlapRot) {
			rightWing.rotateAngleZ += wingFlapIncrement;
		}
		else if(rightFlapDir == -1 && rightWing.rotateAngleZ > minWingFlapRot) {
			rightWing.rotateAngleZ -= wingFlapIncrement;
		}
		else {
			rightFlapDir *= -1;
		}
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
		if(Math.abs(rightLeg.rotateAngleX) < maxLegXRot) {
			rightLeg.rotateAngleX += legXRotIncrement*rightLegXDir;
		}
		else {
			rightLeg.rotateAngleX -= legXRotIncrement*rightLegXDir;
			rightLegXDir *= -1;
		}
	}
	

	void interpolateLegsStop() {
		leftLeg.rotateAngleX = 0;
		rightLeg.rotateAngleX = 0;
	}
	
}
