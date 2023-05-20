package anonymousacid.pigeon.client.fakeentities;

import static anonymousacid.pigeon.McIf.player;
import static anonymousacid.pigeon.McIf.world;

import java.util.List;

import javax.vecmath.Vector3d;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.AxisAlignedBB;
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
		
		//This lets the player place blocks on the entity
		preventEntitySpawning = false;
	}
	
	public EntityPigeon2(World worldIn, double maxSpeed, double maxForce) {
		super(worldIn);
		movementVelocity = new Vector3d(0, 0, 0);
		steeringForce = new Vector3d(0, 0, 0);
		pos = new Vector3d(0, 0, 0);
		
		//This lets the player place blocks on the entity
		preventEntitySpawning = false;
		
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
		
		Vector3d vec = seekForce(
				entityToFollow.posX,
				entityToFollow.posY,
				entityToFollow.posZ,
				10,
				3
				);
		
		steeringForce.add(vec);
		
		Move();
	}
	
	
	/**
	 * Make a force vector that steers the pigeon towards the desired targe coordinates
	 * @param xTarg
	 * @param yTarg
	 * @param zTarg
	 * @param slowingRadius When entering this radius, pigeon starts slowing down
	 * @param stoppingRadius When entering this radius, pigeon stops. This value MUST be smaller than slowingRadius.
	 * @return
	 */
	private Vector3d seekForce(double xTarg, double yTarg, double zTarg, double slowingRadius, double stoppingRadius) {
		Vector3d vec = new Vector3d(xTarg, yTarg, zTarg);
		
		if(vec.x == pos.x && vec.y == pos.y && vec.z == pos.z) {
			vec.set(0, 0, 0);
			return vec;
		}
		
		//Get vector pointing to target position
		vec.sub(pos);
		
		double dist = vec.length();
		if(dist > slowingRadius) {
			vec.normalize();
			vec.scale(maxSpeed);
		}
		else if (dist > stoppingRadius) {
			vec.normalize();
			vec.scale(maxSpeed * (dist/slowingRadius));
		}
		else {
			vec.x = 0;
			vec.y = 0;
			vec.z = 0;
		}
		
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
		
		doMovementAndBlockCollisions();
		
		
		steeringForce.x = 0;
		steeringForce.y = 0;
		steeringForce.z = 0;
	}
	
	void doMovementAndBlockCollisions() {
		
		Vector3d startPos = new Vector3d(posX, posY, posZ);
		
		Vector3d increment = new Vector3d(movementVelocity.x, movementVelocity.y, movementVelocity.z);
		increment.normalize();
		increment.scale(0.015);
		
		{//x collision test
			
			setPosition(posX+movementVelocity.x, posY, posZ);
			
			//apparently this is a collider-cast method that is used in pushOutOfBlocks, so I am using it here.
			List<AxisAlignedBB> xCollisions = world().func_147461_a(this.getEntityBoundingBox());
			
			if(xCollisions.size() > 0) {
				
				boolean gotHit = true;
				while(gotHit) {
					setPosition(posX-increment.x, posY, posZ);
					xCollisions = world().func_147461_a(this.getEntityBoundingBox());
					
					gotHit = xCollisions.size() > 0;
				}
				
			}
		}
		
		{//y collision test
			
			setPosition(posX, posY+movementVelocity.y, posZ);
			
			//apparently this is a collider-cast method that is used in pushOutOfBlocks, so I am using it here.
			List<AxisAlignedBB> yCollisions = world().func_147461_a(this.getEntityBoundingBox());
			
			if(yCollisions.size() > 0) {
				
				boolean gotHit = true;
				while(gotHit) {
					setPosition(posX, posY-increment.y, posZ);
					yCollisions = world().func_147461_a(this.getEntityBoundingBox());
					
					gotHit = yCollisions.size() > 0;
				}
				
			}
		}
		
		{//Z collision test
			
			setPosition(posX, posY, posZ+movementVelocity.z);
			
			//apparently this is a collider-cast method that is used in pushOutOfBlocks, so I am using it here.
			List<AxisAlignedBB> zCollisions = world().func_147461_a(this.getEntityBoundingBox());
			
			if(zCollisions.size() > 0) {
				
				boolean gotHit = true;
				while(gotHit) {
					setPosition(posX, posY, posZ-increment.z);
					zCollisions = world().func_147461_a(this.getEntityBoundingBox());
					
					gotHit = zCollisions.size() > 0;
				}
				
			}
		}
		
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}
}
