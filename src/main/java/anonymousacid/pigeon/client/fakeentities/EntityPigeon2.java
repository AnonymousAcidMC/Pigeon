package anonymousacid.pigeon.client.fakeentities;

import static anonymousacid.pigeon.McIf.player;

import javax.vecmath.Vector3d;

import anonymousacid.pigeon.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityPigeon2 extends EntityMob implements IFakeEntity{
	
	public float gravity;
	public Vector3d movementVelocity;
	public Vector3d steeringForce;
	private Vector3d pos;
	public double maxSpeed;
	public double maxForce;
	
	public int poopTimer;
	public int soundTimer;
	
	public boolean flapWings;
	public boolean isScreenAsset;
	public boolean isPecking;
	
	public EntityItem itemToPeck;
	public Entity entityAttractedTo;
	
	public EntityPigeon2(World worldIn) {
		super(worldIn);
		movementVelocity = new Vector3d(0, 0, 0);
		steeringForce = new Vector3d(0, 0, 0);
		pos = new Vector3d(0, 0, 0);
		gravity = -9.81f;
	}
	
	public EntityPigeon2(World worldIn, double maxSpeed, double maxForce) {
		super(worldIn);
		movementVelocity = new Vector3d(0, 0, 0);
		steeringForce = new Vector3d(0, 0, 0);
		pos = new Vector3d(0, 0, 0);
		
		gravity = -9.81f;
		
		this.maxSpeed = maxSpeed;
		this.maxForce = maxForce;
	}
	
	
	
	@Override
	public void onLivingUpdate() {
		if(isScreenAsset) {
			flapWings = false;
			isPecking = false;
			itemToPeck = null;
			entityAttractedTo = null;
			return;
		}
		
		//Update pos vector
		pos.x = posX;
		pos.y = posY;
		pos.z = posZ;
		
		Entity entityToFollow = entityAttractedTo != null ? entityAttractedTo : player();
		setPosition(
				entityToFollow.posX,
				entityToFollow.posY,
				entityToFollow.posZ
				);
		
		Vector3d vec = seekForce(
				entityToFollow.posX,
				entityToFollow.posY,
				entityToFollow.posZ
				);
		steeringForce.add(vec);
		
		Move();
	}
	
	private Vector3d seekForce(double xTarg, double yTarg, double zTarg) {
		Vector3d vec = new Vector3d(xTarg, yTarg, zTarg);
		
		//Get vector pointing to target position
		vec.sub(pos);
		
		if(vec.x == 0 || vec.y == 0 || vec.z == 0) 
			return vec;
		
		//Set magnitude to max speed
		vec.normalize();
		vec.scale(maxSpeed);
		
		//subtract to get the steering force
		vec.sub(movementVelocity);
		
		//limit steering force to max force
		if(vec.length() > maxForce) {
			vec.normalize();
			vec.scale(maxForce);
		}
		
		return vec;
	}
	
	private void Move() {
		
		movementVelocity.add(steeringForce);
		
		if(movementVelocity.length() > maxSpeed) {
			movementVelocity.normalize();
			movementVelocity.scale(maxSpeed);
		}
		
		pos.x += movementVelocity.x;
		pos.y += movementVelocity.y;
		pos.z += movementVelocity.z;
		
		setPosition(pos.x, pos.y, pos.z);
		
		steeringForce.x = 0;
		steeringForce.y = 0;
		steeringForce.z = 0;
	}
	
	
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

}
