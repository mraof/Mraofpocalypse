package com.mraof.mraofpocalypse.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class EntityMraofpocalypse extends EntityCreature {
	protected ResourceLocation textureResource;
	public EntityMraofpocalypse(World par1World, Object... objects) 
	{
		super(par1World);
		
		setCustomStartingVariables(objects);
		
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute((double)(this.getMaximumHealth()));
		this.setHealth(this.getMaximumHealth());
		
		this.textureResource = this.getTexture() != null?new ResourceLocation("minestuck", this.getTexture()):null;
	}

	protected void setCustomStartingVariables(Object[] objects)
	{
		
	}
	protected abstract float getMaximumHealth();
	
	/**
	 * Returns null if it handles the texture itself.
	 * @return
	 */
	public abstract String getTexture();

	public ResourceLocation getTextureResource() 
	{
		return textureResource;
	}
	
}
