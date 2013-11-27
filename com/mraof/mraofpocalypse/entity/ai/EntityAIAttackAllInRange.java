package com.mraof.mraofpocalypse.entity.ai;

import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.mraof.mraofpocalypse.entity.EntityLifeDrainer;

public class EntityAIAttackAllInRange extends EntityAIBase 
{

	World worldObj;
	EntityLifeDrainer attacker;
	List<EntityLivingBase> entityTarget;

	/**
	 * An amount of decrementing ticks that allows the entity to attack once the tick reaches 0.
	 */
	int attackTick;
	float movementSpeed;
	boolean willSearch;

	/** The PathEntity of our entity. */
	PathEntity entityPathEntity;
	Class classTarget;
	private int movementTime;
	private int attackRate;
	private float distanceMultiplier;

	public EntityAIAttackAllInRange(EntityCreature par1EntityLiving, Class classTarget, float movementSpeed, int attackRate, boolean willSearch, int distanceMultiplier)
	{
		this(par1EntityLiving, movementSpeed, attackRate, willSearch, distanceMultiplier);
		this.classTarget = classTarget;
	}

	public EntityAIAttackAllInRange(EntityCreature par1EntityLiving, float movementSpeed, int attackRate, boolean willSearch, int distanceMultiplier)
	{
		this.attackTick = 0;
		this.attacker = (EntityLifeDrainer) par1EntityLiving;
		this.worldObj = par1EntityLiving.worldObj;
		this.movementSpeed = movementSpeed;
		this.attackRate = attackRate;
		this.willSearch = willSearch;
		this.distanceMultiplier = distanceMultiplier;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	 public boolean shouldExecute()
	 {
		 List<EntityLivingBase> entitylivings = this.attacker.getAttackTargets();

		 if (entitylivings == null)
		 {
			 return false;
		 }
		 else if (this.classTarget != null && !this.classTarget.isAssignableFrom(entitylivings.getClass()))
		 {
			 return false;
		 }
		 else
		 {
			 this.entityTarget = entitylivings;
			 this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(this.entityTarget.get(0));
			 return this.entityPathEntity != null;
		 }
	 }

	 /**
	  * Returns whether an in-progress EntityAIBase should continue executing
	  */
	 public boolean continueExecuting()
	 {
		 EntityLivingBase entityliving = this.attacker.getAttackTarget();
		 return entityliving == null ? false : ((this.entityTarget.size() == 0) ? false : (!this.willSearch ? !this.attacker.getNavigator().noPath() : this.attacker.func_110176_b(MathHelper.floor_double(this.entityTarget.get(0).posX), MathHelper.floor_double(this.entityTarget.get(0).posY), MathHelper.floor_double(this.entityTarget.get(0).posZ))));
	 }

	 /**
	  * Execute a one shot task or start executing a continuous task
	  */
	 public void startExecuting()
	 {
		 this.attacker.getNavigator().setPath(this.entityPathEntity, this.movementSpeed);
		 this.movementTime = 0;
	 }

	 /**
	  * Resets the task
	  */
	 public void resetTask()
	 {
		 this.entityTarget = null;
		 this.attacker.getNavigator().clearPathEntity();
	 }

	 /**
	  * Updates the task
	  */
	 public void updateTask()
	 {
		 this.attacker.getLookHelper().setLookPositionWithEntity(this.entityTarget.get(0), 30.0F, 30.0F);

		 if ((this.willSearch || this.attacker.getEntitySenses().canSee(this.entityTarget.get(0))) && --this.movementTime <= 0)
		 {
			 this.movementTime = 4 + this.attacker.getRNG().nextInt(7);
			 this.attacker.getNavigator().tryMoveToEntityLiving(this.entityTarget.get(0), this.movementSpeed);
		 }

		 this.attackTick = Math.max(this.attackTick - 1, 0);
		 double d0 = (double)(this.attacker.width * distanceMultiplier * this.attacker.width * distanceMultiplier);
		 for(EntityLivingBase target : entityTarget)
		 if (this.attacker.getDistanceSq(target.posX, target.boundingBox.minY, target.posZ) - (target.width / 2 ) <= d0)
		 {
			 if (this.attackTick <= 0)
			 {
				 this.attackTick = this.attackRate;

				 if (this.attacker.getHeldItem() != null)
				 {
					 this.attacker.swingItem();
				 }
				 
				 this.attacker.attackEntityAsMob(target);
			 }
		 }
	 }
	 public void setDistanceMultiplier(float distanceMultiplier) 
	 {
		 this.distanceMultiplier = distanceMultiplier;
	 }
}
