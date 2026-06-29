package com.captrojo.resadditae.block.special;

import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.world.TeleporterDepths;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockDepthsPortal extends Block
{
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
		if (!(entity instanceof EntityPlayerMP)) {
			return;
		}
		EntityPlayerMP player = (EntityPlayerMP) entity;
		MinecraftServer server = MinecraftServer.getServer();
		
		int dest = CommonConfig.WorldGen.depths_dimension_id;
		if (player.dimension == dest) {
			dest = 0; /* Send them back to the overworld */
		}
		server.getConfigurationManager().transferPlayerToDimension(player, dest, new TeleporterDepths(server.worldServerForDimension(dest)));
	}
}
