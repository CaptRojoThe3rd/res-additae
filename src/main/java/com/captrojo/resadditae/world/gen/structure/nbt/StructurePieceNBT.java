package com.captrojo.resadditae.world.gen.structure.nbt;

import java.util.ArrayList;
import java.util.Random;

import com.captrojo.resadditae.util.NBTHlpr;
import com.captrojo.resadditae.world.loot.LootGroup;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class StructurePieceNBT
{	
	public String name;
	
	public int origin_x;
	public int origin_y;
	public int origin_z;
	public int size_x;
	public int size_y;
	public int size_z;
	
	public Palette palette;
	public PaletteIndex[] pal_indices;
	
	public PaletteEntry foundation;
	
	public LootGroup[] loot_groups;
	public String[] entity_names;
	
	public StructurePieceNBT()
	{
	}
	
	public StructurePieceNBT(ResourceLocation rl)
	{
		NBTTagCompound nbt = NBTHlpr.loadNBTFromResource(rl);
		this.loadFromNBT(nbt);
	}
	
	public StructurePieceNBT(String name, World world, int x1, int y1, int z1, int x2, int y2, int z2, int xo, int yo, int zo)
	{
		this.name = name;
		
		this.origin_x = xo - x1;
		this.origin_y = yo - y1;
		this.origin_z = zo - z1;
		this.size_x = x2 - x1 + 1;
		this.size_y = y2 - y1 + 1;
		this.size_z = z2 - z1 + 1;
		
		this.palette = new Palette();
		ArrayList<PaletteIndex> pal_indices_list = new ArrayList<PaletteIndex>();
		
		for (int xb = 0; xb < this.size_x; xb++) {
			int x = xb + x1;
			for (int yb = 0; yb < this.size_y; yb++) {
				int y = yb + y1;
				for (int zb = 0; zb < this.size_z; zb++) {
					int z = zb + z1;
					if (world.getBlock(x, y, z) == Blocks.air) {
						continue;
					}
					int idx = this.palette.getPaletteEntryIdxFor(world, x, y, z);
					pal_indices_list.add(new PaletteIndex(idx, xb, yb, zb));
				}
			}
		}
		
		this.pal_indices = pal_indices_list.toArray(new PaletteIndex[pal_indices_list.size()]);
	}
	
	public NBTTagCompound saveToNBT(NBTTagCompound nbt)
	{
		nbt.setString("Name", this.name);
		
		nbt.setTag("Origin", NBTHlpr.savePosB(new byte[] {(byte) this.origin_x, (byte) this.origin_y, (byte) this.origin_z}));
		nbt.setTag("Size", NBTHlpr.savePosB(new byte[] {(byte) this.size_x, (byte) this.size_y, (byte) this.size_z}));
		
		nbt.setTag("Palette", this.palette.saveToNBT(new NBTTagList()));
		
		NBTTagList block_list = new NBTTagList();
		for (PaletteIndex palidx : this.pal_indices) {
			block_list.appendTag(palidx.saveToNBT(new NBTTagCompound()));
		}
		nbt.setTag("Blocks", block_list);
		
		if (this.foundation != null) {
			nbt.setTag("Foundation", this.foundation.saveToNBT(new NBTTagCompound()));
		}
		
		if (this.loot_groups != null) {
			NBTTagList loot_list = new NBTTagList();
			for (LootGroup loot : this.loot_groups) {
				loot_list.appendTag(loot.saveToNBT(new NBTTagCompound()));
			}
			nbt.setTag("Loot", loot_list);
		}
		
		if (this.entity_names != null) {
			NBTTagList entity_list = new NBTTagList();
			for (String e : this.entity_names) {
				entity_list.appendTag(new NBTTagString(e));
			}
			nbt.setTag("Entities", entity_list);
		}

		return nbt;
	}
	
	public StructurePieceNBT loadFromNBT(NBTTagCompound nbt)
	{
		this.name = nbt.getString("Name");
		
		byte[] origin = NBTHlpr.loadPosB(nbt.getCompoundTag("Origin"));
		this.origin_x = origin[0];
		this.origin_y = origin[1];
		this.origin_z = origin[2];
		byte[] size = NBTHlpr.loadPosB(nbt.getCompoundTag("Size"));
		this.size_x = size[0];
		this.size_y = size[1];
		this.size_z = size[2];
		
		this.palette = new Palette().loadFromNBT(nbt.getTagList("Palette", NBT.TAG_COMPOUND));
		
		NBTTagList block_list = nbt.getTagList("Blocks", NBT.TAG_COMPOUND);
		this.pal_indices = new PaletteIndex[block_list.tagCount()];
		for (int i = 0; i < block_list.tagCount(); i++) {
			this.pal_indices[i] = new PaletteIndex().loadFromNBT(block_list.getCompoundTagAt(i));
		}
		
		if (nbt.hasKey("Foundation")) {
			this.foundation = (new PaletteEntry()).loadFromNBT(nbt.getCompoundTag("Foundation"));
		}
		
		if (nbt.hasKey("Loot")) {
			NBTTagList loot_list = nbt.getTagList("Loot", NBT.TAG_COMPOUND);
			this.loot_groups = new LootGroup[loot_list.tagCount()];
			for (int i = 0; i < loot_list.tagCount(); i++) {
				this.loot_groups[i] = new LootGroup(loot_list.getCompoundTagAt(i)); 
			}
		}
		
		if (nbt.hasKey("Entities")) {
			NBTTagList entity_list = nbt.getTagList("Entities", NBT.TAG_STRING);
			this.entity_names = new String[entity_list.tagCount()];
			for (int i = 0; i < entity_list.tagCount(); i++) {
				this.entity_names[i] = entity_list.getStringTagAt(i);
			}
		}
		
		return this;
	}
	
	/**
	 * For use by the structure wand. NOT for use in world gen.
	 */
	public void placeInWorld(World world, Random rand, int x0, int y0, int z0)
	{
		int x1 = x0 - this.origin_x;
		int y1 = y0 - this.origin_y;
		int z1 = z0 - this.origin_z;
		
		for (int phase = 0; phase <= 1; phase++) {
			for (PaletteIndex index : this.pal_indices) {
				int x = index.x + x1;
				int y = index.y + y1;
				int z = index.z + z1;
				this.palette.getEntry(index.idx).place(world, rand, x, y, z, this, 2, phase, -1);
			}
		}
	}
}
