package com.captrojo.resadditae.block.special;

import com.captrojo.resadditae.config.common.WorldGenConfig;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.world.TeleporterDepths;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockDepthsPortal extends Block
{
	public static void sendEntityToDimension(WorldServer worldorigin, WorldServer worlddest, Entity entity)
	{
		if (entity.worldObj.isRemote || entity.isDead) {
			return;
		}
		
		entity.worldObj.theProfiler.startSection("changeDimension");
		MinecraftServer server = MinecraftServer.getServer();
		int cur_dim_id = entity.dimension;
		
		/* Ensure the chunk is loaded so entities can actually spawn on the other side */
		worlddest.getBlock((int) entity.posX, (int) entity.posY, (int) entity.posZ);

		entity.worldObj.removeEntity(entity);
		entity.isDead = false;
		entity.worldObj.theProfiler.startSection("reposition");
		server.getConfigurationManager().transferEntityToWorld(entity, cur_dim_id, worldorigin, worlddest, new TeleporterDepths(worlddest));
		entity.worldObj.theProfiler.endStartSection("reloading");
		Entity entity2 = EntityList.createEntityByName(EntityList.getEntityString(entity), worlddest);

		if (entity2 != null) {
			entity2.copyDataFrom(entity, true);
			worlddest.spawnEntityInWorld(entity2);
		}

		entity.isDead = true;
		entity.worldObj.theProfiler.endSection();
		worldorigin.resetUpdateEntityTick();
		worlddest.resetUpdateEntityTick();
		entity.worldObj.theProfiler.endSection();
	}

	public BlockDepthsPortal()
	{
		super(Material.portal);

		this.setBlockName("depths_portal");
		this.setBlockTextureName(ResAdditae.ident("depths/portal"));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if (world.isRemote) {
			return;
		}
		if (entity.ridingEntity != null || entity.riddenByEntity != null) {
			return;
		}

		int origin, dest;
		if (entity.dimension == WorldGenConfig.depths_dimension_id) {
			origin = WorldGenConfig.depths_dimension_id;
			dest = 0;
		} else {
			origin = 0;
			dest = WorldGenConfig.depths_dimension_id;
		}

		MinecraftServer server = MinecraftServer.getServer();
		WorldServer worldorigin = server.worldServerForDimension(origin);
		WorldServer worlddest = server.worldServerForDimension(dest);

		if (entity instanceof EntityPlayerMP) {
			server.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) entity, dest, new TeleporterDepths(worlddest));
		} else {
			sendEntityToDimension(worldorigin, worlddest, entity);
		}
	}
}
