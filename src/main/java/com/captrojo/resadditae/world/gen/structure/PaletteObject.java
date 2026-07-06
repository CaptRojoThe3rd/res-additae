package com.captrojo.resadditae.world.gen.structure;

import java.util.Random;

import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockTorch;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PaletteObject
{
	private static int getPhase(Block block, int meta)
	{
		if (block instanceof BlockTorch) {
			return 1;
		}
		if (block instanceof BlockButton) {
			return 1;
		}
		if (block instanceof BlockLever) {
			return 1;
		}
		if (block instanceof BlockRail) {
			return 1;
		}
		return 0;
	}
	
	protected Block block;
	protected short meta;
	protected NBTTagCompound te_data;
	protected short ref_group;
	protected short ref_idx;
	
	public PaletteObject(Block block, short meta, NBTTagCompound te_data, short ref_group, short ref_idx)
	{
		this.block = block;
		this.meta = meta;
		this.te_data = te_data;
		this.ref_group = ref_group;
		this.ref_idx = ref_idx;
	}
	
	public PaletteObject(World world, int x, int y, int z)
	{
		this.block = world.getBlock(x, y, z);
		this.meta = (short) world.getBlockMetadata(x, y, z);
		
		TileEntity te = world.getTileEntity(x, y, z);
		NBTTagCompound te_data;
		if (te != null) {
			this.te_data = new NBTTagCompound();
			te.writeToNBT(this.te_data);
		} else {
			this.te_data = null;
		}
		
		this.ref_group = RefGroups.NONE;
		this.ref_idx = 0;
	}
	
	public PaletteObject(NBTTagCompound tag)
	{
		UniqueIdentifier uidr = new UniqueIdentifier(tag.getString("name"));
		this.block = GameRegistry.findBlock(uidr.modId, uidr.name);
		if (this.block == null) {
			this.block = Blocks.sponge;
		}
		
		this.meta = tag.getShort("meta");
		
		NBTTagCompound te_data = null;
		if (tag.hasKey("tile_entity")) {
			this.te_data = tag.getCompoundTag("tile_entity");
		}
		
		this.ref_group = 0;
		this.ref_idx = 0;
		if (tag.hasKey("ref_group")) {
			this.ref_group = tag.getShort("ref_group");
			this.ref_idx = tag.getShort("ref_idx");
		}
	}
	
	public NBTTagCompound saveToNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(this.block);
		if (id == null) {
			ResAdditae.LOG.error("Error saving block in structure");
			id = new UniqueIdentifier("minecraft:stone");
		}
		tag.setString("name", id.toString());
		tag.setShort("meta", this.meta);
		
		if (this.te_data != null) {
			tag.setTag("tile_entity", this.te_data);
		}
		
		if (this.ref_group != RefGroups.NONE) {
			tag.setShort("ref_group", this.ref_group);
			tag.setShort("ref_idx", this.ref_idx);
		}
		
		return tag;
	}
	
	public void placeAtPos(StructurePiece piece, int phase, Random rand, World world, int x, int y, int z, long optionals)
	{
		Block block = this.block;
		int meta = this.meta;
		NBTTagCompound te_data = this.te_data;
		long ref_bit = 1l << this.ref_idx;
		
		if (this.ref_group == RefGroups.OPTIONAL && (optionals & ref_bit) != 0) {
			block = Blocks.air;
			meta = 0;
			te_data = null;
		}
		
		if (phase != getPhase(block, meta)) {
			return;
		}
		if (block == null) {
			ResAdditae.LOG.error("Structure palette object: block is null");
			return;
		}
		
		world.setBlock(x, y, z, block, meta, 2);
		if (te_data != null) {
			TileEntity te = TileEntity.createAndLoadEntity(te_data);
			if (te != null) {
				te.xCoord = x;
				te.yCoord = y;
				te.zCoord = z;
				world.setTileEntity(x, y, z, te);
			}
		}
		
		if (this.ref_group == RefGroups.ENTITY) {
			PaletteEntity ep = piece.entities.get(this.ref_idx);
			Entity entity = EntityList.createEntityByName(ep.id, world);
			entity.setPosition(x + 0.5 + ep.x_offs, y + ep.y_offs, z + 0.5 + ep.z_offs);
			world.spawnEntityInWorld(entity);
		}

		if (block instanceof IStructureActor) {
			((IStructureActor) block).onPlacedInStructure(world, x, y, z);
		}
		if (block instanceof BlockChest) {
			/* BlockChest screws with the metadata so we have to change it back to what we want */
			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		}
		
		if (this.ref_group == RefGroups.LOOT && world.getTileEntity(x, y, z) instanceof IInventory) {
			IInventory inv = (IInventory) world.getTileEntity(x, y, z);
			piece.loot_groups.get(this.ref_idx).fillWithLoot(inv, rand);
		}
	}
	
	public boolean equals(Object obj)
	{
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PaletteObject)) {
			return false;
		}
		
		PaletteObject pal = (PaletteObject) obj;
		if (this.block != pal.block) {
			return false;
		}
		if (this.meta != pal.meta) {
			return false;
		}
		
		if (this.te_data != null || pal.te_data != null) {
			return false;
		}
		
		if (this.ref_group != pal.ref_group) {
			return false;
		}
		if (this.ref_idx != pal.ref_idx) {
			return false;
		}
		
		return true;
	}
}
