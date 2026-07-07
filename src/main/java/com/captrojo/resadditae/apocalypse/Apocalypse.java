package com.captrojo.resadditae.apocalypse;

import java.util.Set;
import java.util.TreeSet;

import com.captrojo.resadditae.block.ModBlocks;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class Apocalypse
{
	/*
	 * Even with my optimizations, this is still very laggy.
	 */
	public static void flashoverChunk(World world, Chunk chunk, int min_y, int max_y)
	{
		if (world.isRemote) {
			return;
		}
		
		Set update_hashset = (Set) ReflectionHelper.getPrivateValue(WorldServer.class, (WorldServer) world, "pendingTickListEntriesHashSet");
		TreeSet update_treeset = (TreeSet) ReflectionHelper.getPrivateValue(WorldServer.class, (WorldServer) world, "pendingTickListEntriesTreeSet");
		long time = world.getTotalWorldTime();
		
		int x0 = chunk.xPosition << 4;
		int z0 = chunk.zPosition << 4;
		
		for (int y = 0; y < 256; y += 16) {
			if (chunk.getBlock(0, y, 0) == Blocks.air) {
				chunk.func_150807_a(0, y, 0, ModBlocks.flashover_air, 0);
			}
		}
		
		/* Directly access arrays to speed things up */
		ExtendedBlockStorage[] ebs_arr = chunk.getBlockStorageArray();
		
		int fl_air_id = Block.getIdFromBlock(ModBlocks.flashover_air);
		byte fl_air_lsb = (byte) (fl_air_id & 0xff);
		byte fl_air_msb = (byte) ((fl_air_id >> 8) & 0xf);
		byte fire_lsb = (byte) (Block.getIdFromBlock(Blocks.fire) & 0xff);
		byte fire_msb = 0;
		byte[] set_arr_lsb = {fl_air_lsb, fire_lsb};
		byte[] set_arr_msb = {fl_air_msb, fire_msb};
		Block[] set_arr_blk = {ModBlocks.flashover_air, Blocks.fire};
		
		for (ExtendedBlockStorage ebs : ebs_arr) {
			int y0 = ebs.getYLocation();
			if (y0 < min_y || y0 >= max_y) {
				continue;
			}
			
			byte[] lsb_arr = ebs.getBlockLSBArray();
			NibbleArray msb_arr = ebs.getBlockMSBArray();
			if (msb_arr == null) {
				msb_arr = new NibbleArray(lsb_arr.length, 4);
			}
			
			for (int x = 0; x < 16; x++) {
				for (int y = 0; y < 16; y++) {
					for (int z = 0; z < 16; z++) {
						int idx = x | (z << 4) | (y << 8);
						int id = lsb_arr[idx] | msb_arr.get(x, y, z);
						if (id != 0) {
							continue;
						}
						int set = (x ^ y ^ z) & 0x1;
						lsb_arr[idx] = set_arr_lsb[set];
						msb_arr.set(x, y, z, set_arr_msb[set]);
						if (set != 0) {
							NextTickListEntry entry = new NextTickListEntry(x + x0, y + y0, z + z0, Blocks.fire);
							entry.setPriority(0);
							entry.setScheduledTime(time + (long) (30 + (x ^ z)));
							if (!update_hashset.contains(entry)) {
								update_hashset.add(entry);
								update_treeset.add(entry);
							}
						}
//						world.scheduleBlockUpdate(x + x0, y + y0, z + z0, set_arr_blk[set], 30 + (y & 0x7));
					}
				}
			}
			
			ebs.setBlockLSBArray(lsb_arr);
			ebs.setBlockMSBArray(msb_arr);
		}
		
		chunk.setChunkModified();
	}
}
