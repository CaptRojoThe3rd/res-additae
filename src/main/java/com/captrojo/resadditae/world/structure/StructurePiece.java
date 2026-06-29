package com.captrojo.resadditae.world.structure;

import java.util.ArrayList;
import java.util.Random;

import com.captrojo.resadditae.world.loot.LootGroup;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockVine;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class StructurePiece
{
	private static int rotateBlock(Block block, int meta)
	{
		if (block instanceof BlockStairs || block instanceof BlockFurnace) {
			int u = meta & 12;
			switch (meta & 3) {
			case 0:
				return 2 + u;
			case 1:
				return 3 + u;
			case 2:
				return 1 + u;
			case 3:
				return 0 + u;
			}
		}
		if (block instanceof BlockRotatedPillar) {
			int u = meta & 3;
			switch (meta & 12) {
			case 4:
				return 8 + u;
			case 8:
				return 4 + u;
			default:
				return meta;
			}
		}
		if (block == Blocks.torch || block == Blocks.redstone_torch || block instanceof BlockButton) {
			int u = meta & 8;
			switch (meta & 7) {
			case 1:
				return 3 + u;
			case 2:
				return 4 + u;
			case 3:
				return 2 + u;
			case 4:
				return 1 + u;
			default:
				return u;
			}
		}
		if (block == Blocks.ladder || block instanceof BlockChest) {
			switch (meta) {
			case 2:
				return 5;
			case 3:
				return 4;
			case 4:
				return 2;
			case 5:
				return 3;
			default:
				return meta;
			}
		}
		if (block instanceof BlockDoor) {
			int u = meta & 8;
			switch (meta & 7) {
			case 0:
				return 1 + u;
			case 1:
				return 2 + u;
			case 2:
				return 3 + u;
			case 3:
				return 0 + u;
			case 4:
				return 7 + u;
			case 5:
				return 4 + u;
			case 6:
				return 5 + u;
			case 7:
				return 6 + u;
			}
		}
		if (block instanceof BlockTrapDoor) {
			int u = meta & 12;
			switch (meta & 3) {
			case 0:
				return 3 + u;
			case 1:
				return 2 + u;
			case 2:
				return 0 + u;
			case 3:
				return 1 + u;
			}
		}
		if (block instanceof BlockBed) {
			int u = meta & 12;
			return ((meta + 1) & 3) + u;
		}
		if (block instanceof BlockSkull) {
			switch (meta) {
			case 2:
				return 5;
			case 3:
				return 4;
			case 4:
				return 2;
			case 5:
				return 3;
			default:
				return meta;
			}
		}
		if (block instanceof BlockVine) {
			meta <<= 1;
			if ((meta & 16) == 16) {
				meta &= 15;
				meta |= 1;
			}
		}
		return meta;
	}
	
	public final ArrayList<PaletteObject> palette;
	public final ArrayList<PaletteIndex> blocks;
	public final ArrayList<LootGroup> loot_groups;
	public final ArrayList<PaletteEntity> entities;
	
	public final int origin_x;
	public final int origin_y;
	public final int origin_z;
	
	public final int size_x;
	public final int size_y;
	public final int size_z;
	
	public StructurePiece(int ox, int oy, int oz, int sx, int sy, int sz)
	{
		this.palette = new ArrayList<PaletteObject>();
		this.blocks = new ArrayList<PaletteIndex>();
		this.loot_groups = new ArrayList<LootGroup>();
		this.entities = new ArrayList<PaletteEntity>();
		
		this.origin_x = ox;
		this.origin_y = oy;
		this.origin_z = oz;
		
		this.size_x = sx;
		this.size_y = sy;
		this.size_z = sz;
	}
	
	public StructurePiece(World world, int x1, int y1, int z1, int x2, int y2, int z2, int ox, int oy, int oz)
	{
		int bx = (x1 < x2) ? x1 : x2;
		int by = (y1 < y2) ? y1 : y2;
		int bz = (z1 < z2) ? z1 : z2;
		int ex = ((x1 < x2) ? x2 : x1) + 1;
		int ey = ((y1 < y2) ? y2 : y1) + 1;
		int ez = ((z1 < z2) ? z2 : z1) + 1;
		
		this.origin_x = ox - bx;
		this.origin_y = oy - by;
		this.origin_z = oz - bz;
		
		this.size_x = ex - bx;
		this.size_y = ey - by;
		this.size_z = ez - bz;
		
		this.palette = new ArrayList<PaletteObject>();
		this.blocks = new ArrayList<PaletteIndex>();
		
		for (int x = bx; x < ex; x++) {
			for (int y = by; y < ey; y++) {
				for (int z = bz; z < ez; z++) {
					Block block = world.getBlock(x, y, z);
					if (block == Blocks.air) {
						continue;
					}
					PaletteIndex idx = new PaletteIndex(palette, world, x, y, z, bx, by, bz);
					blocks.add(idx);
				}
			}
		}
		
		this.loot_groups = new ArrayList<LootGroup>();
		this.entities = new ArrayList<PaletteEntity>();
	}
	
	public StructurePiece(NBTTagCompound tag)
	{
		this.origin_x = tag.getShort("origin_x");
		this.origin_y = tag.getShort("origin_y");
		this.origin_z = tag.getShort("origin_z");

		this.size_x = tag.getShort("size_x");
		this.size_y = tag.getShort("size_y");
		this.size_z = tag.getShort("size_z");
		
		this.palette = new ArrayList<PaletteObject>();
		NBTTagList palette_list = tag.getTagList("palette", NBT.TAG_COMPOUND);
		for (int i = 0; i < palette_list.tagCount(); i++) {
			this.palette.add(new PaletteObject(palette_list.getCompoundTagAt(i)));
		}
		
		this.blocks = new ArrayList<PaletteIndex>();
		NBTTagList block_list = tag.getTagList("blocks", NBT.TAG_COMPOUND);
		for (int i = 0; i < block_list.tagCount(); i++) {
			this.blocks.add(new PaletteIndex(block_list.getCompoundTagAt(i)));
		}
		
		this.loot_groups = new ArrayList<LootGroup>();
		NBTTagCompound groups = tag.getCompoundTag("loot");
		for (int i = 0; i < 64; i++) {
			if (!groups.hasKey("idx_" + i)) {
				this.loot_groups.add(null);
				continue;
			}
			this.loot_groups.add(new LootGroup(groups.getCompoundTag("idx_" + i)));
		}

		this.entities = new ArrayList<PaletteEntity>();
		NBTTagList entities = tag.getTagList("entities", NBT.TAG_COMPOUND);
		for (int i = 0; i < entities.tagCount(); i++) {
			this.entities.add(new PaletteEntity(entities.getCompoundTagAt(i)));
		}
	}
	
	public NBTTagCompound saveToNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		NBTTagList palette_list = new NBTTagList();
		for (PaletteObject pal : this.palette) {
			palette_list.appendTag(pal.saveToNBT());
		}
		tag.setTag("palette", palette_list);
		
		NBTTagList block_list = new NBTTagList();
		for (PaletteIndex blk : this.blocks) {
			block_list.appendTag(blk.saveToNBT());
		}
		tag.setTag("blocks", block_list);
		
		tag.setShort("origin_x", (short) this.origin_x);
		tag.setShort("origin_y", (short) this.origin_y);
		tag.setShort("origin_z", (short) this.origin_z);
		
		tag.setShort("size_x", (short) this.size_x);
		tag.setShort("size_y", (short) this.size_y);
		tag.setShort("size_z", (short) this.size_z);
		
		return tag;
	}
	
	public void placeInWorld(World world, Random rand, int x, int y, int z, long optionals)
	{
		int start_x = x - this.origin_x;
		int start_y = y - this.origin_y;
		int start_z = z - this.origin_z;
		
		for (PaletteIndex blk : this.blocks) {
			blk.placeWithStartPos(this, 0, rand, world, start_x, start_y, start_z, optionals);
		}
		for (PaletteIndex blk : this.blocks) {
			blk.placeWithStartPos(this, 1, rand, world, start_x, start_y, start_z, optionals);
		}
	}
	
	public StructurePiece getRotated90()
	{
		int ox = this.size_z - this.origin_z - 1;
		int oz = this.origin_x;
		int sx = this.size_z;
		int sz = this.size_x;
		StructurePiece sp = new StructurePiece(ox, this.origin_y, oz, sx, this.size_y, sz);
		
		for (LootGroup l : this.loot_groups) {
			sp.loot_groups.add(l);
		}
		for (PaletteEntity e : this.entities) {
			sp.entities.add(e);
		}
		
		for (PaletteObject p : this.palette) {
			short meta = (short) rotateBlock(p.block, p.meta);
			PaletteObject pn = new PaletteObject(p.block, meta, p.te_data, p.ref_group, p.ref_idx);
			sp.palette.add(pn);
		}
		for (PaletteIndex b : this.blocks) {
			int x = this.size_z - b.z;
			int z = b.x;
			sp.blocks.add(new PaletteIndex(b.idx, x, b.y, z));
		}
		
		return sp;
	}
}
