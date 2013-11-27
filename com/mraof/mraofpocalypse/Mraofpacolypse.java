package com.mraof.mraofpocalypse;

import net.minecraft.entity.EntityList;

import com.mraof.mraofpocalypse.entity.EntityLifeDrainer;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;

@Mod(modid="Mraofpacolypse", name = "Mraofpacolypse", version = "0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {"Mraofpacolypse"})
public class Mraofpacolypse
{
	int entityIdStart = 3000;
	int currentEntityIdOffset = 0;
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		System.out.println("Loading");
		this.registerAndMapEntity(EntityLifeDrainer.class, "LifeDrainer", 0x888888, 0xAAAAAA);
	}
	
	//registers entity with forge and minecraft, and increases currentEntityIdOffset by one in order to prevent id collision
	public void registerAndMapEntity(Class entityClass, String name, int eggColor, int eggSpotColor)
	{
		this.registerAndMapEntity(entityClass, name, eggColor, eggSpotColor, 80, 3, true);
	}
	public void registerAndMapEntity(Class entityClass, String name, int eggColor, int eggSpotColor, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityList.addMapping(entityClass, name, entityIdStart + currentEntityIdOffset, eggColor, eggSpotColor);
		EntityRegistry.registerModEntity(entityClass, name, currentEntityIdOffset, this, trackingRange, updateFrequency, sendsVelocityUpdates);
		currentEntityIdOffset++;
	}
}
