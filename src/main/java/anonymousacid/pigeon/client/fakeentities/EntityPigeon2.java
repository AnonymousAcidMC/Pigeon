package anonymousacid.pigeon.client.fakeentities;

import static anonymousacid.pigeon.McIf.player;
import static anonymousacid.pigeon.McIf.world;

import java.util.List;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import anonymousacid.pigeon.utils.RenderUtils;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityPigeon2 extends EntityMob implements IFakeEntity{
	
	public double gravity = -0.04;
	public Vector3d movementVelocity = new Vector3d(0, 0, 0);
	public Vector3d steeringForce = new Vector3d(0, 0, 0);
	private Vector3d pos = new Vector3d(0, 0, 0);
	public double maxSpeed;
	public double maxForce;
	
	public int poopTimer;
	public int soundTimer;
	
	/*these are used in the pigeon's Model class*/
	public Vector3f leftLegRot = new Vector3f();
	public Vector3f rightLegRot = new Vector3f();
	public Vector3f leftWingRot = new Vector3f();
	public Vector3f rightWingRot = new Vector3f();
	public Vector3f headRot = new Vector3f();
	public Vector3f bodyRot = new Vector3f();
	public Vector3f tailRot = new Vector3f();
	
	public boolean flapWings;
	public boolean isFlying;
	public boolean isScreenAsset;
	public boolean isPecking;
	public boolean allowedToPeckItem = true;
	private boolean flyDown;
	
	private EntityItem itemToPeck;
	private Entity entityToFollow;
	public Vector3d targetVector = new Vector3d(0, 0, 0);
	public Vector3d lookVector = new Vector3d(0, 0, 0);
	private TargetType targetType = TargetType.PLAYER;
	
	/*if the player is this distance far from the pigeon, the pigeon teleports to the player*/
	public int playerTeleportDistance = 15;
	/*the range that the pigeon checks for items to peck (rectangular. not a radius.)*/
	public int itemToPeckRange = 10;
	
	/*distance from these entities at which the pigeon stops moving*/
	public int itemStoppingDistance = 1;
	public int playerStoppingDistance = 3;
	
	private boolean atTarget = false;
	
	
	public EntityPigeon2(World worldIn, double maxSpeed, double maxForce) {
		super(worldIn);
		setSize(1, 1);
		
		//This lets the player place blocks on the entity
		preventEntitySpawning = false;
		
		this.maxSpeed = maxSpeed;
		this.maxForce = maxForce;
	}
	
	
	/**
	 * Where all the magic gets executed
	 */
	@Override
	public void onLivingUpdate() {
		if(isScreenAsset) {
			flapWings = false;
			isPecking = false;
			itemToPeck = null;
			entityToFollow = null;
			onGround = true;
			return;
		}
		
		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
		
		//Update pos vector
		pos.set(posX, posY, posZ);
		
		groundedCheck();
		
		handlePecking();
		
		doNullChecks();
		
		tryFindItemToPeck();
		tryTeleportToPlayer();
		
		if(entityToFollow == null) {
			entityToFollow = player();
		}
		
		setTargetPosition();
		
		lookAtTargetPosition();
		
		handleFlying();
		
		Vector3d vec = seekForce(
				targetVector.x,
				targetVector.y,
				targetVector.z,
				playerTeleportDistance,
				targetType == TargetType.ITEM ? itemStoppingDistance : playerStoppingDistance
				);
		
		steeringForce.add(vec);
		
		Move();
	}
	
	/**
	 * Sets onGround variable to appropriate type using aabb casting method
	 */
	void groundedCheck() {
		AxisAlignedBB aabb = Utils.boundingBoxAt(this, posX, posY-0.05, posZ);
		List<AxisAlignedBB> groundCollisions = world().func_147461_a(aabb);
		
		onGround = !groundCollisions.isEmpty(); 
	}
	
	
	
	/**
	 * Checks if target entities are either null, or do not exist
	 */
	void doNullChecks() {
		
		boolean setTargetToPlayer = false;
		
		switch(targetType) {
			case ENTITY:
				if(entityToFollow == null || entityToFollow.isDead) {
					entityToFollow = null;
					setTargetToPlayer = true;
				}
				break;
			case ITEM:
				if(itemToPeck == null || itemToPeck.isDead) {
					itemToPeck = null;
					setTargetToPlayer = true;
				}
				break;
			case PLAYER:
				break;
			case TARGET_VECTOR:
				break;
			case NONE:
				break;
		}
		
		if(setTargetToPlayer) {
			setTargetEntity(player());
		}
	}
	
	
	
	/**
	 * Sets isFlying and flyDown to the appropriate value.
	 */
	void handleFlying() {
		
		if(onGround) {
			flyDown = false;
			return;
		}
		
		//if target is grounded, then fly down to it.
		Vector3d targPosDown;
		if(targetType == TargetType.ENTITY || targetType == TargetType.PLAYER) {
			AxisAlignedBB aabb = Utils.boundingBoxAt(entityToFollow, entityToFollow.posX, entityToFollow.posY-0.1, entityToFollow.posZ);
			List<AxisAlignedBB> targetGroundCollisions = world().func_147461_a(aabb);
			
			aabb = Utils.boundingBoxAt(this, this.posX, entityToFollow.posY-0.1, this.posZ);
			List<AxisAlignedBB> pigeonGroundCollisionsAtTargetY = world().func_147461_a(aabb);
			
			flyDown = targetGroundCollisions.size() != 0 && pigeonGroundCollisionsAtTargetY.size() != 0;
		}
		else if (targetType == TargetType.TARGET_VECTOR){
			AxisAlignedBB aabb = Utils.boundingBoxAt(this, this.posX, targetVector.y-0.1, this.posZ);
			List<AxisAlignedBB> pigeonGroundCollisionsAtTargetY = world().func_147461_a(aabb);
			
			flyDown = pigeonGroundCollisionsAtTargetY.size() != 0;
		}
		else if(targetType == TargetType.ITEM) {//If at item
			flyDown = atTarget && (posY > itemToPeck.posY);//drop down to item's y level
		}
		else {
			flyDown = false;
			return;
		}
	}
	
	/**
	 * handle isPecking variable
	 */
	void handlePecking() {
		isPecking = (targetType == TargetType.ITEM) && allowedToPeckItem && onGround;
	}
	
	
	
	/**
	 * Try to teleport to the player if distance to player is more than playerTeleportDistance.
	 */
	void tryTeleportToPlayer() {
		Entity player = player();
		double dist = Math.sqrt(
				(player.posX - pos.x)*(player.posX - pos.x) +
				(player.posY - pos.y)*(player.posY - pos.y) +
				(player.posZ - pos.z)*(player.posZ - pos.z) 
				);
		
		if (dist > playerTeleportDistance) {
			setPosition(player.posX, player.posY, player.posZ);
		}
	}
	
	/**
	 * Look for items in aabb expanded by 10 blocks (11x11x11 box around pigeon)
	 */
	void tryFindItemToPeck() {
		
		if(!isPecking && allowedToPeckItem && targetType != TargetType.ITEM) {
			List<EntityItem> itemList = world().getEntitiesWithinAABB(
					EntityItem.class,
					this.getEntityBoundingBox().expand(itemToPeckRange, itemToPeckRange, itemToPeckRange));
			
			for(EntityItem itemEntity : itemList) {
				ItemStack itemStack = itemEntity.getEntityItem();
				int creativeTabIndex = itemStack.getItem().getCreativeTab().getTabIndex();
				String unlocalizedName = itemStack.getUnlocalizedName();
				
				boolean foundItem =
						/*if item is in "Foodstuffs" category*/ creativeTabIndex == 6 ||  
						/*or is a seed*/ unlocalizedName.contains("seed") || 
						/*or is Hypixel Skyblock "Tasty Cheese"*/ (unlocalizedName.equals("item.item.skull.char") && itemStack.getDisplayName().contains("Tasty Cheese")); 
				
				if(foundItem) {
					setItemToPeck(itemEntity);
					return;
				}
			}
		}
		else if(itemToPeck != null && targetType == TargetType.ITEM) {
			double dist = Math.sqrt(
					(itemToPeck.posX - posX)*(itemToPeck.posX - posX) +
					(itemToPeck.posY - posY)*(itemToPeck.posY - posY) +
					(itemToPeck.posZ - posZ)*(itemToPeck.posZ - posZ)
					);
			
			if(dist > itemToPeckRange) {
				setTargetEntity(player()); 
			}
		}
		
	}
		
	
	
	/**
	 * Sets targetVector to a position appropriate to the current targetType.
	 */
	void setTargetPosition() {
		
		switch(targetType) {
			case ENTITY:
				targetVector.x = entityToFollow.posX;
				targetVector.y = entityToFollow.posY;
				targetVector.z = entityToFollow.posZ; 
				break;
			case ITEM:
				targetVector.x = itemToPeck.posX;
				targetVector.y = itemToPeck.posY;
				targetVector.z = itemToPeck.posZ;
				break;
			case PLAYER://entityToFollow can be the player too.
				targetVector.x = entityToFollow.posX;
				targetVector.y = entityToFollow.posY;
				targetVector.z = entityToFollow.posZ;
				break;
				
			case TARGET_VECTOR://the target vector is already set if TARGET_VECTOR is the case.
				break;
			case NONE:
				return;
		}
	}
	
	/**
	 * Look at the targetVector position
	 */
	void lookAtTargetPosition() {
		//if targeting entity, look at it in the eyes.
		double yOffset = 
			(targetType == TargetType.ENTITY || targetType == TargetType.PLAYER) ? entityToFollow.getEyeHeight()/2 : 0;
		
		RenderUtils.facePosition(this, targetVector.x, targetVector.y + yOffset, targetVector.z, 360, 360);
	}
	
	
	
	/**
	 * Pigeon will move to inputted location.
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setTargetVector(double x, double y, double z) {
		targetType = TargetType.TARGET_VECTOR;
		targetVector.x = x;
		targetVector.y = y;
		targetVector.z = z;
	}
	
	/**
	 * Sets the entity that the pigeon wants to follow
	 * If the inputted entity happens to be the player, the pigeon will switch its movement type to PLAYER.
	 * Otherwise, it will be set to ENTITY.
	 * NOTE: if you put in an item, the pigeon will not peck it. Use setItemToPeck if you want the pigeon to peck.
	 * @param entityToFollow
	 */
	public void setTargetEntity(Entity entityToFollow) {
		targetType = entityToFollow == player() ? TargetType.PLAYER : TargetType.ENTITY;
		this.entityToFollow = entityToFollow;
	}
	
	/**
	 * Pigeon will peck this inputted item entity.
	 * @param item
	 */
	public void setItemToPeck(EntityItem item) {
		itemToPeck = item;
		targetType = TargetType.ITEM;
	}
	
	/**
	 * Makes the pigeon stay still
	 */
	public void setNoTarget() {
		targetType = TargetType.NONE;
	}
	
	
	
	
	/**
	 * targetType is private and should not be changed directly.
	 * Use the setTarget and setItemToPeck methods to manipulate this variable
	 * @return this.targetType 
	 */
	public TargetType getTargetType() {
		return this.targetType;
	}
	
	/**
	 * Enum that is used for manipulating responses to different types of targets
	 */
	public enum TargetType {
		TARGET_VECTOR,
		ITEM,
		ENTITY,
		PLAYER,
		NONE
	}
	
	/**
	 * Checks if there was a change in the xyz coordinate since the last tick
	 * @return true if the pigeon has moved since the last tick.
	 */
	public boolean hasMoved() {
		return 
			posX != prevPosX ||
            posY != prevPosY ||
            posZ != prevPosZ;
	}
	
	/**
	 * @return Distance moved since last tick 
	 */
	public double calculateMovedDistance() {
		return Math.sqrt(
				(posX-prevPosX)*(posX-prevPosX) +
				(posY-prevPosY)*(posY-prevPosY) +
				(posZ-prevPosZ)*(posZ-prevPosZ)
				);
	}
	
	
	/**
	 * Make a force vector that steers the pigeon towards the desired target coordinates
	 * @param xTarg
	 * @param yTarg
	 * @param zTarg
	 * @param slowingRadius When entering this radius, pigeon starts slowing down
	 * @param stoppingRadius When entering this radius, pigeon stops. This value MUST be smaller than slowingRadius.
	 * @return
	 */
	private Vector3d seekForce(double xTarg, double yTarg, double zTarg, double slowingRadius, double stoppingRadius) {
		Vector3d vec = new Vector3d(xTarg, yTarg, zTarg);
		atTarget = false;
		
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
		else {//if in stopping radius
			vec.set(0, 0, 0);
			atTarget = true;
		}
		
		//subtract to get the steering force
		vec.sub(movementVelocity);
		
		//limit steering force to max force
		if(vec.length() > maxForce) {
			vec.normalize();
			vec.scale(maxForce);
		}
		
		if(flyDown) {
			vec.y += gravity;
		}
		
		return vec;
	}
	
	/**
	 * Handles movementVelocity, movement, and block collisions.
	 */
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
		

		increment.x = MathHelper.clamp_double(increment.x, -0.01, 0.01);
		increment.y = MathHelper.clamp_double(increment.y, -0.01, 0.01);
		increment.z = MathHelper.clamp_double(increment.z, -0.01, 0.01);
		
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
	

	
	
	
	/**
	 * @Return false. This prevents the pigeon from blocking attacks from the user player.
	 */
	@Override
	public boolean canBeCollidedWith() {return false;}
}
