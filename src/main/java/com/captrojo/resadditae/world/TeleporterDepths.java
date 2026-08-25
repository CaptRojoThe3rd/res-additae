package com.captrojo.resadditae.world;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.config.common.WorldGenConfig;

import codechicken.lib.math.MathHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TeleporterDepths extends Teleporter
{
	public final WorldServer server;
	
	public TeleporterDepths(WorldServer server)
	{
		super(server);
		this.server = server;
	}
	
	private boolean isValidFloorBlock(IBlockAccess world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		if (block.isAir(world, x, y, z)) {
			return false;
		}
		if (!block.isOpaqueCube()) {
			return false;
		}
		if (block == ModBlocks.depths_portal) {
			return false;
		}
		return true;
	}
	
	private boolean hasEnoughSpace(IBlockAccess world, int x, int y, int z, boolean check_floor)
	{
		if (y < 0 || y > 255) {
			return false;
		}
		if (!this.isValidFloorBlock(world, x, y - 1, z) && check_floor) {
			return false;
		}
		if (!world.getBlock(x, y, z).isAir(world, x, y, z)) {
			return false;
		}
		if (!world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z)) {
			return false;
		}
		if (!world.getBlock(x, y + 2, z).isAir(world, x, y + 2, z)) {
			return false;
		}
		return true;
	}
	
	private int[] runThroughSpaceChkLoops(IBlockAccess world, int x, int y, int z, int d)
	{
		/* Scans the edges of a diamond at the specified Y level. */
		for (int a = 0; a <= d; a++) {
			/* East Corner → South Corner */
			if (this.hasEnoughSpace(world, x + d - a, y, z + a, true)) {
				return new int[] {x + d - a, y, z + a};
			}
			/* South Corner → West Corner */
			if (this.hasEnoughSpace(world, x - a, y, z + d - a, true)) {
				return new int[] {x - a, y, z + d - a};
			}
			/* West Corner → North Corner */
			if (this.hasEnoughSpace(world, x - d + a, y, z - a, true)) {
				return new int[] {x - d + a, y, z - a};
			}
			/* North Corner → East Corner */
			if (this.hasEnoughSpace(world, x + a, y, z - d + a, true)) {
				return new int[] {x + a, y, z - d + a};
			}
		}
		return null;
	}
	
	@Override
	public void placeInPortal(Entity entity, double xd, double yd, double zd, float f)
	{
		World world = this.server;
		int x = MathHelper.floor_double(entity.posX);
		int y = MathHelper.floor_double(entity.posY);
		int z = MathHelper.floor_double(entity.posZ);
		
		if (world.provider.dimensionId != WorldGenConfig.chasm_dimension) {
			for (y = 240; y > 0; y--) {
				if (this.hasEnoughSpace(world, x, y, z, false)) {
					break;
				}
			}
			if (y == 0) {
				/* They will be in a block, but at least they will not be in an unbreakable block. */
				y = 128;
			}
		} else {
			y = 4;
		cLoop:
			for (int c = 1; c < 64; c++) {
				for (int y1 = 0; y1 < c; y1++) {
					int[] pos = this.runThroughSpaceChkLoops(world, x, y + y1, z, c - y1);
					if (pos != null) {
						x = pos[0];
						y = pos[1];
						z = pos[2];
						break cLoop;
					}
				}
			}
		}
		
		entity.setLocationAndAngles(x + 0.5, y, z + 0.5, entity.rotationYaw, entity.rotationPitch);
	}
}
