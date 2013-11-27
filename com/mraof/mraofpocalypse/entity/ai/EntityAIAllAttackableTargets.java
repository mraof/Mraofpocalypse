package com.mraof.mraofpocalypse.entity.ai;

import java.util.Collections;
import java.util.List;

import com.mraof.mraofpocalypse.entity.EntityLifeDrainer;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTargetSorter;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIAllAttackableTargets extends EntityAITarget 
{
	 private final Class targetClass;
	    private final int targetChance;

	    /** Instance of EntityAINearestAttackableTargetSorter. */
	    private final EntityAINearestAttackableTargetSorter theNearestAttackableTargetSorter;

	    /**
	     * This filter is applied to the Entity search.  Only matching entities will be targetted.  (null -> no
	     * restrictions)
	     */
	    private List<EntityLivingBase> targetEntity;

	    public EntityAIAllAttackableTargets(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4)
	    {
	        this(par1EntityCreature, par2Class, par3, par4, false);
	    }

	    public EntityAIAllAttackableTargets(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5)
	    {
	        this(par1EntityCreature, par2Class, par3, par4, par5, (IEntitySelector)null);
	    }

	    public EntityAIAllAttackableTargets(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5, IEntitySelector par6IEntitySelector)
	    {
	        super(par1EntityCreature, par4, par5);
	        this.targetClass = par2Class;
	        this.targetChance = par3;
	        this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTargetSorter(par1EntityCreature);
	        this.setMutexBits(1);
	    }

	    /**
	     * Returns whether the EntityAIBase should begin execution.
	     */
	    public boolean shouldExecute()
	    {
	        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0)
	        {
	            return false;
	        }
	        else
	        {
	            double d0 = this.getTargetDistance();
	            List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(d0, 4.0D, d0), null);
	            Collections.sort(list, this.theNearestAttackableTargetSorter);

	            if (list.isEmpty())
	            {
	                return false;
	            }
	            else
	            {
	                this.targetEntity = list;
	                return true;
	            }
	        }
	    }

	    /**
	     * Execute a one shot task or start executing a continuous task
	     */
	    public void startExecuting()
	    {
	        ((EntityLifeDrainer)this.taskOwner).setAttackTargets(this.targetEntity);
	        super.startExecuting();
	    }
}
