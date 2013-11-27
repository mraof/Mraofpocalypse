package com.mraof.mraofpocalypse.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.mraof.mraofpocalypse.entity.ai.EntityAIAllAttackableTargets;
import com.mraof.mraofpocalypse.entity.ai.EntityAIAttackAllInRange;

public class EntityLifeDrainer extends EntityMraofpocalypse 
{
	private EntityAIAttackAllInRange entityAIAttackAllInRange = new EntityAIAttackAllInRange(this, EntityLiving.class, .4F, 20, false, 32);
	List<EntityLivingBase> attackTargets = new ArrayList<EntityLivingBase>();

	public EntityLifeDrainer(World par1World) 
	{
		super(par1World);
		this.targetTasks.addTask(2, new EntityAIAllAttackableTargets(this, EntityLiving.class, 2, true, false));
		this.tasks.addTask(4, entityAIAttackAllInRange);
	}

	@Override
	protected float getMaximumHealth() {
		return 20;
	}

	@Override
	public String getTexture() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}
	
	@Override
	public boolean attackEntityAsMob(Entity par1Entity) 
	{
		boolean flag = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3);
		return flag;
	}

	public void setAttackTargets(List<EntityLivingBase> targetEntities) 
	{
		System.out.println(targetEntities + " setting targets");
		attackTargets = targetEntities;
		
	}

	public List<EntityLivingBase> getAttackTargets() 
	{
		System.out.println("Getting targets");
		return attackTargets;
	}



}
