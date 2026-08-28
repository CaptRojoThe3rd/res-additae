package com.captrojo.resadditae.entity;

import java.util.HashMap;
import java.util.Map;

import com.captrojo.resadditae.entity.monster.EntitySnowEye;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModEntities
{
	static int egg_id_counter = 600;
	static Map<Class<? extends Entity>, Integer> entity_egg_map = new HashMap<Class<? extends Entity>, Integer>();
	
	public static ItemStack getEggFromEntity(Entity entity)
	{
		return new ItemStack(Items.spawn_egg, 1, ModEntities.entity_egg_map.get(entity.getClass()));
	}
	
	static void registerSpawnEgg(Class<? extends Entity> entity, int prim_color, int sec_color)
	{
		while (EntityList.getClassFromID(ModEntities.egg_id_counter) != null) {
			ModEntities.egg_id_counter++;
		}
		ModEntities.entity_egg_map.put(entity, ModEntities.egg_id_counter);
		
		EntityList.IDtoClassMapping.put(ModEntities.egg_id_counter, entity);
		EntityList.entityEggs.put(ModEntities.egg_id_counter, new EntityEggInfo(ModEntities.egg_id_counter, prim_color, sec_color));
	}
	
	public static void register()
	{
		EntityRegistry.registerModEntity(EntityThrownHalberd.class, "ThrownHalberd", 0, ResAdditae.instance, 128, 1, true);
		EntityRegistry.registerModEntity(EntitySnowEye.class, "SnowEye", 1, ResAdditae.instance, 128, 1, true);
		
		ModEntities.registerSpawnEgg(EntitySnowEye.class, 0x9dc6c1, 0x2bbfab);
	}
}
