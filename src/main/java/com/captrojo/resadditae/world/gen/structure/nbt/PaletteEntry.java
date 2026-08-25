package com.captrojo.resadditae.world.gen.structure.nbt;

import java.util.Random;

import com.captrojo.resadditae.block.IStructureActor;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.tileentity.TEStructureBlock;
import com.captrojo.resadditae.util.Consts;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockVine;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

/* An entry into a structure's palette of blocks.
 * Contains all sorts of information about the block that it will place.
 */
public class PaletteEntry
{
	public static final int F_OPTIONAL = 0x0001;
	public static final int F_LOOT = 0x0002;
	public static final int F_ENTITY = 0x0004;

	public static Block fallback_block = Blocks.sponge;

	private static int rotateBlock90(Block block, int meta)
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

	private static int rotateMeta(Block block, int meta, int rotation)
	{
		for (int i = 0; i < rotation; i++) {
			meta = rotateBlock90(block, meta);
		}
		return meta;
	}

	private static int getMetaWithOffset(Block block, int meta, int dir)
	{
		if (block instanceof BlockRailBase) {
			int a = meta & ~0x1;
			int n = meta & 0x1;
			if (dir == Consts.WEST || dir == Consts.EAST) {
				if (n == 1) {
					n = 0;
				} else {
					n = 1;
				}
			}
			return a | n;
		}
		
		if (block instanceof BlockTorch) {
			int a = meta & ~0x7;
			int n = meta & 0x7;
			
			if (dir == Consts.SOUTH) {
				n = Consts.TORCH_ROT_180[n];
			} else if (dir == Consts.WEST) {
				n = Consts.TORCH_ROT_CCW[n];
			} else if (dir == Consts.EAST) {
				n = Consts.TORCH_ROT_CW[n];
			}
			
			return a | n;
		}
		
		if (block instanceof BlockLadder || block instanceof BlockButton) {
			int a = meta & ~0x7;
			int n = meta & 0x7;
			
			if (dir == Consts.SOUTH) {
				n = Consts.LADDER_BTN_ROT_180[n];
			} else if (dir == Consts.WEST) {
				n = Consts.LADDER_BTN_ROT_CCW[n];
			} else if (dir == Consts.EAST) {
				n = Consts.LADDER_BTN_ROT_CW[n];
			}
			
			return a | n;
		}
		
		if (block == Blocks.piston || block == Blocks.sticky_piston || block == Blocks.lever || block == Blocks.dispenser) {
			int a = meta & ~0x7;
			int n = meta & 0x7;
			
			if (dir == Consts.SOUTH) {
				n = Consts.LADDER_BTN_ROT_180[n];
			} else if (dir == Consts.WEST) {
				n = Consts.LADDER_BTN_ROT_CCW[n];
			} else if (dir == Consts.EAST) {
				n = Consts.LADDER_BTN_ROT_CW[n];
			}
			
			return a | n;
		}
		
		if (block == Blocks.tripwire_hook || block instanceof BlockDirectional) {
			int a = meta & ~0x3;
			int n = meta & 0x3;
			
			if (dir == Consts.SOUTH) {
				n = Direction.rotateOpposite[n];
			} else if (dir == Consts.WEST) {
				n = Direction.rotateLeft[n];
			} else if (dir == Consts.EAST) {
				n = Direction.rotateRight[n];
			}
			
			return a | n;
		}
		
		if (block instanceof BlockStairs) {
			int a = meta & ~0x3;
			int n = meta & 0x3;
			
			if (dir == Consts.SOUTH) {
				n = Consts.STAIR_ROT_180[n];
			} else if (dir == Consts.WEST) {
				n = Consts.STAIR_ROT_CCW[n];
			} else if (dir == Consts.EAST) {
				n = Consts.STAIR_ROT_CW[n];
			}
			
			return a | n;
		}
		
		if (block instanceof BlockDoor) {
			int a = meta & ~0x3;
			int n = meta & 0x3;
			
			if (dir == Consts.SOUTH) {
				if (n == 0) {
					n = 2;
				} else if (n == 2) {
					n = 0;
				}
			} else if (dir == Consts.WEST) {
				n = n + 1 & 3;
			} else if (dir == Consts.EAST) {
				n = n + 3 & 3;
			}
			
			return a | n;
		}
		
		if (block instanceof BlockTrapDoor) {
			int a = meta & ~0x3;
			int n = meta & 0x3;
			
			if (dir == Consts.SOUTH) {
				n = Consts.TRAPDOOR_ROT_180[n];
			} else if (dir == Consts.WEST) {
				n = Consts.TRAPDOOR_ROT_CCW[n];
			} else if (dir == Consts.EAST) {
				n = Consts.TRAPDOOR_ROT_CW[n];
			}
			
			return a | n;
		}

		return meta;
	}

	private static int getPhase(Block block, int meta)
	{
		/* Support blocks needed */
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
		
		/* Will set their metadata based on surrounding blocks */
		if (block instanceof BlockChest || block instanceof BlockFurnace) {
			return 1;
		}
		
		return 0;
	}

	public Block block;
	public int meta;
	public NBTTagCompound te_nbt;

	public int flags;

	public int optional_id;
	public int loot_id;

	public int entity_id;
	public double entity_offs_x;
	public double entity_offs_y;
	public double entity_offs_z;

	public PaletteEntry()
	{
	}
	
	public PaletteEntry(Block block, int meta)
	{
		this.block = block;
		this.meta = meta;
		this.te_nbt = null;
		
		this.flags = 0;
	}

	public PaletteEntry(World world, int x, int y, int z, boolean modify)
	{
		this.block = world.getBlock(x, y, z);
		this.meta = world.getBlockMetadata(x, y, z);

		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null) {
			this.te_nbt = new NBTTagCompound();
			world.getTileEntity(x, y, z).writeToNBT(this.te_nbt);
			this.te_nbt.removeTag("x");
			this.te_nbt.removeTag("y");
			this.te_nbt.removeTag("z");
		}

		if (te instanceof TEStructureBlock && modify) {
			TEStructureBlock tesb = (TEStructureBlock) te;

			switch (this.meta) {
			case 0:
				this.block = Blocks.air;
				this.meta = 0;
				this.te_nbt = null;
				break;
			case 1:
				this.block = tesb.stored_block;
				this.meta = tesb.stored_block_meta;
				this.optional_id = tesb.idx;
				this.flags |= F_OPTIONAL;
				this.te_nbt = null;
				break;
			case 2:
				this.block = tesb.stored_block;
				this.meta = tesb.stored_block_meta;
				this.loot_id = tesb.idx;
				this.flags |= F_LOOT;
				this.te_nbt = null;
				break;
			case 3:
				this.block = tesb.stored_block;
				this.meta = tesb.stored_block_meta;
				this.entity_id = tesb.idx;
				this.flags |= F_ENTITY;
				this.te_nbt = null;
				break;
			default:
				break;
			}
		}
	}

	public boolean shouldPlace(int phase, int... opt)
	{
		if (phase != getPhase(this.block, this.meta)) {
			return false;
		}
		if ((this.flags & F_OPTIONAL) != 0 && opt != null) {
			for (int o : opt) {
				if (o == this.optional_id) {
					return false;
				}
			}
		}
		return true;
	}

	public void place(World world, Random rand, int x, int y, int z, StructurePieceNBT piece, int dir, int phase, int... opt)
	{
		if (!this.shouldPlace(phase, opt)) {
			return;
		}

		int meta = getMetaWithOffset(this.block, this.meta, dir);
		world.setBlock(x, y, z, this.block, meta, 2);

		if (this.te_nbt != null) {
			TileEntity te = TileEntity.createAndLoadEntity(this.te_nbt);
			if (te != null) {
				te.xCoord = x;
				te.yCoord = y;
				te.zCoord = z;
				world.setTileEntity(x, y, z, te);
			} else {
				ResAdditae.LOG.warn("Skipped tile entity during structure placement");
			}
		}

		if ((this.flags & F_ENTITY) != 0 && piece.entity_names != null) {
			String entity_name = piece.entity_names[this.entity_id];
			Entity entity = EntityList.createEntityByName(entity_name, world);
			if (entity != null) {
				entity.setPosition(x + this.entity_offs_x, y + this.entity_offs_y, z + this.entity_offs_z);
				world.spawnEntityInWorld(entity);
			} else {
				ResAdditae.LOG.warn("Skipped entity " + this.entity_id + " during structure placement");
			}
		}

		TileEntity te = world.getTileEntity(x, y, z);

		if ((this.flags & F_LOOT) != 0 && te instanceof IInventory && piece.loot_groups != null) {
			IInventory inv = (IInventory) te;
			piece.loot_groups[this.loot_id].fillWithLoot(inv, rand);
		}
		
		if (this.block instanceof IStructureActor) {
			((IStructureActor) this.block).onPlacedInStructure(world, x, y, z);
		}
	}

	public NBTTagCompound saveToNBT(NBTTagCompound nbt)
	{
		UniqueIdentifier uidr = GameRegistry.findUniqueIdentifierFor(this.block);
		if (uidr == null) {
			uidr = new UniqueIdentifier("minecraft:air");
		}
		nbt.setString("B", uidr.toString());
		nbt.setShort("M", (short) this.meta);
		if (this.te_nbt != null) {
			nbt.setTag("TE", this.te_nbt);
		}

		nbt.setShort("F", (short) this.flags);

		if ((this.flags & F_OPTIONAL) != 0) {
			nbt.setByte("Opt", (byte) this.optional_id);
		}
		if ((this.flags & F_LOOT) != 0) {
			nbt.setByte("Loot", (byte) this.loot_id);
		}
		if ((this.flags & F_ENTITY) != 0 && this.entity_id != -1) {
			nbt.setByte("E", (byte) this.entity_id);
			nbt.setFloat("Ex", (float) this.entity_offs_x);
			nbt.setFloat("Ey", (float) this.entity_offs_y);
			nbt.setFloat("Ez", (float) this.entity_offs_z);
		}

		return nbt;
	}

	public PaletteEntry loadFromNBT(NBTTagCompound nbt)
	{
		UniqueIdentifier uidr = new UniqueIdentifier(nbt.getString("B"));
		this.block = GameRegistry.findBlock(uidr.modId, uidr.name);
		if (this.block == null) {
			this.block = PaletteEntry.fallback_block;
		}
		this.meta = nbt.getShort("M");
		if (nbt.hasKey("TE")) {
			this.te_nbt = nbt.getCompoundTag("TE");
		}

		this.flags = nbt.getShort("F");

		if ((this.flags & F_OPTIONAL) != 0) {
			this.optional_id = nbt.getByte("Opt");
		}
		if ((this.flags & F_LOOT) != 0) {
			this.loot_id = nbt.getByte("Loot");
		}
		if ((this.flags & F_ENTITY) != 0) {
			this.entity_id = nbt.getByte("E");
			this.entity_offs_x = nbt.getFloat("Ex");
			this.entity_offs_y = nbt.getFloat("Ey");
			this.entity_offs_z = nbt.getFloat("Ez");
		}

		return this;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this.te_nbt != null) {
			return false;
		}

		if (!(o instanceof PaletteEntry)) {
			return false;
		}
		PaletteEntry that = (PaletteEntry) o;

		if (that.te_nbt != null) {
			return false;
		}

		if (this.flags != that.flags) {
			return false;
		}
		if ((this.flags & F_OPTIONAL) != 0 && this.optional_id != that.optional_id) {
			return false;
		}
		if ((this.flags & F_LOOT) != 0 && this.loot_id != that.loot_id) {
			return false;
		}
		if ((this.flags & F_ENTITY) != 0 && (this.entity_id != that.entity_id ||
			this.entity_offs_x != that.entity_offs_x ||
			this.entity_offs_y != that.entity_offs_y ||
			this.entity_offs_z != that.entity_offs_z)) {
			return false;
		}

		return this.block == that.block && this.meta == that.meta;
	}
}
