package io.github.anonymousacid.pigeon.client.model;

import org.lwjgl.input.Mouse;

import io.github.anonymousacid.pigeon.client.fakeentities.EntityPigeon;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelPigeon extends ModelBase {
	
	private final ModelRenderer leg1;
	private final ModelRenderer leg2;
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
	private double neckMoved = 0;
	
	protected double distanceMovedTotal = 0.0d;
	protected static final double CYCLES_PER_BLOCK = 3.0D;
	protected int cycleIndex = 0;
	protected int peckingTimer = 4;
	protected boolean invertCycle = false;
	protected boolean invertFlap = false;
	protected boolean swim = false;
	
	protected float[][] undulationCycle = new float[][]
	{
		{ 0F, -45F, -45F, 0F, 45F, 45F, 0F, -45F, 0f },//leg1
		{ -45F, 0F, 45F, 0F, -45F, 0F, 45F, 0F, -45f }//leg2
	};
	
	public ModelPigeon() {
		textureWidth = 64;
		textureHeight = 64;
		

		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(2.5F, 20.0F, -0.5F);
		leg1.cubeList.add(new ModelBox(leg1, 0, 0, -0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F, false));
		leg1.cubeList.add(new ModelBox(leg1, 0, 0, -0.5F, 3.0F, -1.5F, 1, 1, 1, 0.0F, false));
		leg1.cubeList.add(new ModelBox(leg1, 0, 0, -1.5F, 3.0F, -0.5F, 1, 1, 1, 0.0F, false));
		leg1.cubeList.add(new ModelBox(leg1, 0, 0, 0.5F, 3.0F, -0.5F, 1, 1, 1, 0.0F, false));

		leg2 = new ModelRenderer(this);
		leg2.setRotationPoint(-2.5F, 20.0F, 0.0F);
		leg2.cubeList.add(new ModelBox(leg2, 0, 0, -0.5F, 0.0F, -1.0F, 1, 4, 1, 0.0F, false));
		leg2.cubeList.add(new ModelBox(leg2, 0, 0, -0.5F, 3.0F, -2.0F, 1, 1, 1, 0.0F, false));
		leg2.cubeList.add(new ModelBox(leg2, 0, 0, -1.5F, 3.0F, -1.0F, 1, 1, 1, 0.0F, false));
		leg2.cubeList.add(new ModelBox(leg2, 0, 0, 0.5F, 3.0F, -1.0F, 1, 1, 1, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 16, 0, -3.0F, -5.0F, -4.0F, 6, 1, 7, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 14, 3, -5.0F, -6.0F, -5.0F, 10, 1, 9, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 10, 15, -5.0F, -9.0F, -6.0F, 10, 3, 11, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 24, 0, -5.0F, -10.0F, -7.0F, 10, 1, 11, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 50, -4.0F, -11.0F, -7.0F, 8, 1, 8, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 35, 52, -3.0F, -12.0F, -7.0F, 6, 1, 7, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 24, 0, -5.0F, -9.0F, -7.0F, 10, 1, 1, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 12.0F, -3.5F);
		head.cubeList.add(new ModelBox(head, 17, 44, -3.0F, -1.0F, -2.5F, 6, 1, 5, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 12, 45, -2.0F, -2.0F, -2.5F, 4, 1, 5, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 16, 45, -2.0F, -3.0F, -2.5F, 4, 1, 4, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 15, 45, -2.0F, -4.0F, -2.5F, 4, 1, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 25, 58, -1.0F, -3.0F, -3.5F, 2, 1, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 1, 16, -3.0F, -3.0F, -1.5F, 1, 1, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 1, 16, 2.0F, -3.0F, -1.5F, 1, 1, 1, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 15.0F, 4.0F);
		

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 10.0F, -5.0F);
		tail.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.48F, 0.0F, 0.0F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 44, 29, -3.0F, -11.0F, 2.0F, 6, 1, 4, 0.0F, false));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, 11.0F, -4.0F);
		tail.addChild(cube_r2);
		setRotationAngle(cube_r2, -0.48F, 0.0F, 0.0F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 44, 29, -2.0F, -11.0F, 2.0F, 4, 1, 4, 0.0F, false));

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, 13.0F, -1.0F);
		tail.addChild(cube_r3);
		setRotationAngle(cube_r3, -0.48F, 0.0F, 0.0F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 2, 19, -1.0F, -11.0F, 4.0F, 2, 1, 3, 0.0F, false));

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, 12.0F, -2.0F);
		tail.addChild(cube_r4);
		setRotationAngle(cube_r4, -0.48F, 0.0F, 0.0F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 44, 29, -2.0F, -11.0F, 2.0F, 4, 1, 4, 0.0F, false));

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(0.0F, 11.0F, -3.0F);
		tail.addChild(cube_r5);
		setRotationAngle(cube_r5, -0.3927F, 0.0F, 0.0F);
		cube_r5.cubeList.add(new ModelBox(cube_r5, 44, 29, -3.0F, -11.0F, 2.0F, 6, 1, 3, 0.0F, false));

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(0.0F, 10.0F, -4.0F);
		tail.addChild(cube_r6);
		setRotationAngle(cube_r6, -0.3927F, 0.0F, 0.0F);
		cube_r6.cubeList.add(new ModelBox(cube_r6, 44, 29, -4.0F, -11.0F, 1.0F, 8, 1, 3, 0.0F, false));

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(0.0F, 9.0F, -5.0F);
		tail.addChild(cube_r7);
		setRotationAngle(cube_r7, -0.2182F, 0.0F, 0.0F);
		cube_r7.cubeList.add(new ModelBox(cube_r7, 34, 29, -4.0F, -10.0F, 3.0F, 8, 1, 2, 0.0F, false));

		wing1 = new ModelRenderer(this);
		wing1.setRotationPoint(5.0F, 14.0F, 0.0F);
		wing1.cubeList.add(new ModelBox(wing1, 33, 1, 0.0F, 1.0F, -4.0F, 1, 3, 7, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 25, 24, 1.0F, 1.0F, -3.0F, 1, 3, 5, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 43, 3, 0.0F, 4.0F, -3.0F, 1, 1, 5, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 43, 1, 1.0F, 2.0F, 2.0F, 1, 3, 1, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 27, 7, 1.0F, 4.0F, -2.0F, 1, 1, 4, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 42, 8, 1.0F, 4.0F, 3.0F, 1, 1, 1, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 30, 7, 1.0F, 5.0F, 2.0F, 1, 1, 1, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 32, 6, 1.0F, 5.0F, 0.0F, 1, 1, 2, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 35, 6, 1.0F, 2.0F, 3.0F, 1, 2, 1, 0.0F, false));
		wing1.cubeList.add(new ModelBox(wing1, 38, 3, 0.0F, 0.0F, -2.0F, 1, 1, 4, 0.0F, false));

		wing2 = new ModelRenderer(this);
		wing2.setRotationPoint(-5.0F, 14.0F, 0.0F);
		wing2.cubeList.add(new ModelBox(wing2, 33, 1, -1.0F, 1.0F, -4.0F, 1, 3, 7, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 30, 24, -2.0F, 1.0F, -2.0F, 1, 3, 5, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 43, 3, -1.0F, 4.0F, -3.0F, 1, 1, 5, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 43, 1, -2.0F, 2.0F, -3.0F, 1, 2, 1, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 27, 7, -2.0F, 4.0F, -2.0F, 1, 1, 4, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 42, 8, -2.0F, 4.0F, 2.0F, 1, 1, 1, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 30, 7, -2.0F, 5.0F, 1.0F, 1, 1, 1, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 32, 6, -2.0F, 5.0F, -1.0F, 1, 1, 2, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 35, 6, -2.0F, 2.0F, 3.0F, 1, 2, 1, 0.0F, false));
		wing2.cubeList.add(new ModelBox(wing2, 38, 3, -1.0F, 0.0F, -2.0F, 1, 1, 4, 0.0F, false));
	}

	@Override
	public void render(Entity parEntity, float parTime, float parSwingSuppress, float par4, float parHeadAngleY, float parHeadAngleX, float par7) {
		leg1.render(par7);
		leg2.render(par7);
		body.render(par7);
		head.render(par7);
		tail.render(par7);
		wing1.render(par7);
		wing2.render(par7);
		if(leg1.rotateAngleX >= 45/20) {
			invertCycle = false;
			leg1.rotateAngleX = 45/20;
		} else if(leg1.rotateAngleX <= -45/20) {
			invertCycle = true;
			leg1.rotateAngleX = -45/20;
		}
		if(leg2.rotateAngleX >= 45/20) {
			invertCycle = false;
			leg2.rotateAngleX = 45/20;
		} else if(leg2.rotateAngleX <= -45/20) {
			invertCycle = true;
			leg2.rotateAngleX = -45/20;
		}
		if(wing1.rotateAngleZ <= -75/20) {
			wing1.rotateAngleZ = -75/20;
			invertFlap = true;
		} else if(wing1.rotateAngleZ >= 0) {
			wing1.rotateAngleZ = 0;
			invertFlap = false;
		}
		if(wing2.rotateAngleZ >= 75/20) {
			wing2.rotateAngleZ = 75/20;
			invertFlap = true;
		} else if(wing2.rotateAngleZ <= 0) {
			wing2.rotateAngleZ = 0;
			invertFlap = false;
		}
		renderPigeon((EntityPigeon) parEntity, parTime, parSwingSuppress, par4, parHeadAngleY, parHeadAngleX, par7);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	public void renderPigeon(EntityPigeon parEntity, float parTime, float parSwingSuppress, float par4, float parHeadAngleY, float parHeadAngleX, float par7) {
		setRotationAngles(parTime, parSwingSuppress, par4, parHeadAngleY, parHeadAngleX, par7, parEntity);
	}
    
    protected void setRotation(ModelRenderer model, float rotX, float rotY, float rotZ) {
        model.rotateAngleX = degToRad(rotX);
        model.rotateAngleY = degToRad(rotY);
        model.rotateAngleZ = degToRad(rotZ);        
    }
    
  	@Override
  	public void setRotationAngles(float parTime, float parSwingSuppress, float par3, float parHeadAngleY, float parHeadAngleX, float par6, Entity parEntity) {
  	
  	}

    //ANIMATIONS
  	@Override
  	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_,
  			float partialTickTime) {
  		EntityPigeon entity = (EntityPigeon)entitylivingbaseIn;
  		if(entity.isInventoryAsset) {
			if(Mouse.getEventButtonState()) {head.offsetZ = -0.1f;}
			else head.offsetZ = 0;
			return;
		}
  		super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
  		double traveledDistance = entity.getDistance(entity.prevPosX, entity.prevPosY, entity.prevPosZ);
  		if(traveledDistance != 0) {
  			if(traveledDistance > 0.2) {
	  			if(!invertCycle) {
	  				leg1.rotateAngleX += 0.4f;
	  				leg2.rotateAngleX -= 0.4f;
	  			} else {
	  				leg1.rotateAngleX -= 0.4f;
	  				leg2.rotateAngleX += 0.4f;
	  			}
  			} else {
  				leg1.rotateAngleX = 0f;
  	  			leg2.rotateAngleX = 0f;
  			}
  		} else {
  			leg1.rotateAngleX = 0f;
  			leg2.rotateAngleX = 0f;
  		}
  		
		if(entity.flapWings) {
			if(!invertFlap) {
  				wing1.rotateAngleZ -= 0.6f;
  				wing2.rotateAngleZ += 0.6f;
  			} else {
  				wing1.rotateAngleZ += 0.6f;
  				wing2.rotateAngleZ -= 0.6f;
  			}
		} else {
			wing1.rotateAngleZ = 0;
  			wing2.rotateAngleZ = 0;
		}
  		if(entity.isMoving) {
  			if(neckMoved != 0.1) {
	  			head.offsetZ += 0.1;
	  			neckMoved += 0.1;	
  			} else {
  				if(neckMoved != -0.1) {
  					head.offsetZ -= 0.1;
  					neckMoved -= 0.1;
  				} else {
  					head.offsetZ = 0;
  					neckMoved = 0;
  				}
  			}
  		} else {
  			head.offsetZ = 0f;
  			neckMoved = 0.1;
  		}
  		if(entity.startPecking) {
  			if(peckingTimer == 0) {
	  			head.rotateAngleX = 0.3f;
	  			head.offsetY = 0.4f;
	  			body.offsetY = 0.4f;
	  			tail.offsetY = 0.4f;
	  			wing1.offsetY = 0.4f;
	  			wing2.offsetY = 0.4f;
	  			if(head.offsetZ != 0f) {
	  				head.offsetZ += 0.1f;
	  			} else if(head.offsetZ != -0.1f){
	  				head.offsetZ += -0.1f;
	  			}
	  			peckingTimer = 4;
  			} else {
  				peckingTimer--;
  			}
  		} else {
  			head.rotateAngleX = 0f;
  			head.offsetZ = 0f;
  			head.offsetY = 0f;
  			body.offsetY = 0f;    
  			tail.offsetY = 0f;       
  			wing1.offsetY = 0f;  
  			wing2.offsetY = 0f;  
  		}
  	}
  	
	protected float degToRad(float degrees) {
	    return degrees * (float)Math.PI / 180 ;
	}
    
    // spin methods are good for testing and debug rotation points and offsets in the model
    protected void spinX(ModelRenderer model) {
        model.rotateAngleX += degToRad(0.5F);
    }
    
    protected void spinY(ModelRenderer model) {
        model.rotateAngleY += degToRad(0.5F);
    }
    
    protected void spinZ(ModelRenderer model) {
        model.rotateAngleZ += degToRad(0.5F);
    }
    
    protected void updateDistanceMovedTotal(Entity entity) {
    	distanceMovedTotal = entity.getDistance(entity.lastTickPosX, entity.lastTickPosX, entity.lastTickPosX);
    }
    
    protected double getDistanceMovedTotal(Entity parEntity) 
    {
        return (distanceMovedTotal);
    }
}
