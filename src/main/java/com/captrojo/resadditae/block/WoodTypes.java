package com.captrojo.resadditae.block;

import com.captrojo.resadditae.block.generic.BlockDoor;
import com.captrojo.resadditae.block.generic.BlockMulti;
import com.captrojo.resadditae.block.generic.BlockMultiButton;
import com.captrojo.resadditae.block.generic.BlockMultiFence;
import com.captrojo.resadditae.block.generic.BlockFenceGate;
import com.captrojo.resadditae.block.generic.BlockMultiLeaves;
import com.captrojo.resadditae.block.generic.BlockMultiLog;
import com.captrojo.resadditae.block.generic.BlockMultiPressurePlate;
import com.captrojo.resadditae.block.generic.BlockMultiSapling;
import com.captrojo.resadditae.block.generic.BlockMultiSlab;
import com.captrojo.resadditae.block.generic.BlockMultiStair;
import com.captrojo.resadditae.block.generic.BlockMultiWithDoubleSlab;
import com.captrojo.resadditae.block.generic.BlockTrapdoor;
import com.captrojo.resadditae.item.block.ItemBlockDoor;
import com.captrojo.resadditae.item.block.ItemBlockMulti;
import com.captrojo.resadditae.item.block.ItemBlockMultiSlab;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.render.block.BlockTexture;
import com.captrojo.resadditae.render.block.BlockTexture.Type;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * Whole buncha garbage to automate efficient block ID usage
 */
public enum WoodTypes
{
	VIOLET(0, "violet", 0, false),
	CHESTNUT(1, "chestnut", 0, false),
	NETHER_PALM(2, "nether_palm", 0xffffff, true),
	THERMARBOL(3, "thermarbol", 0, false),
	ENCHANTED_ASH(4, "enchanted_ash", 0xffffff, true),
	DEEPWOOD(5, "deepwood", 0, false);
	
	private static BlockMultiLog[] log_blocks;
	private static BlockMultiWithDoubleSlab[] plank_blocks;
	private static BlockMultiSlab[] slab_blocks;
	private static BlockMultiStair[] stair_blocks;
	private static BlockMultiFence[] fence_blocks;
	private static BlockFenceGate[] fence_gate_blocks;
	private static BlockMultiPressurePlate[] pressure_plate_blocks;
	private static BlockMultiButton[] button_blocks;
	private static BlockDoor[] door_blocks;
	private static BlockTrapdoor[] trapdoor_blocks;
	private static BlockMultiLeaves[] leaf_blocks;
	private static BlockMultiSapling[] sapling_blocks;
	
	protected static void initBlocks()
	{
		int max_id = WoodTypes.values()[WoodTypes.values().length - 1].id;
		
		log_blocks = new BlockMultiLog[max_id / 2 + 1];
		plank_blocks = new BlockMultiWithDoubleSlab[max_id / 8 + 1];
		slab_blocks = new BlockMultiSlab[max_id / 8 + 1];
		stair_blocks = new BlockMultiStair[max_id / 2 + 1];
		fence_blocks = new BlockMultiFence[max_id / 16 + 1];
		fence_gate_blocks = new BlockFenceGate[max_id + 1];
		pressure_plate_blocks = new BlockMultiPressurePlate[max_id / 8 + 1];
		button_blocks = new BlockMultiButton[max_id + 1];
		door_blocks = new BlockDoor[max_id + 1];
		trapdoor_blocks = new BlockTrapdoor[max_id + 1];
		
		leaf_blocks = new BlockMultiLeaves[max_id / 4 + 1];
		sapling_blocks = new BlockMultiSapling[max_id / 8 + 1];
		
		BasicBlockData single_data = new BasicBlockData(Material.wood, Block.soundTypeWood, 3f, 3f, "axe", 0);
		
		GenericMultiBlockData[] log_data = new GenericMultiBlockData[log_blocks.length];
		GenericMultiBlockData[] plank_data = new GenericMultiBlockData[plank_blocks.length];
		GenericMultiBlockData[] fence_data = new GenericMultiBlockData[fence_blocks.length];
		for (int i = 0; i < log_data.length; i++) {
			log_data[i] = new GenericMultiBlockData(Material.wood, Block.soundTypeWood);
		}
		for (int i = 0; i < plank_data.length; i++) {
			plank_data[i] = new GenericMultiBlockData(Material.wood, Block.soundTypeWood);
		}
		for (int i = 0; i < fence_data.length; i++) {
			fence_data[i] = new GenericMultiBlockData(Material.wood, Block.soundTypeWood);
		}
		GenericMultiBlockData[] leaf_data = new GenericMultiBlockData[leaf_blocks.length];
		for (int i = 0; i < leaf_data.length; i++) {
			leaf_data[i] = new GenericMultiBlockData(Material.leaves, Block.soundTypeGrass);
		}
		int[][] leaf_colors = new int[leaf_blocks.length][4];
		boolean[][] leaf_color_flags = new boolean[leaf_blocks.length][4];
		GenericMultiBlockData[] sapling_data = new GenericMultiBlockData[sapling_blocks.length];
		for (int i = 0; i < sapling_data.length; i++) {
			sapling_data[i] = new GenericMultiBlockData(Material.plants, Block.soundTypeGrass);
		}
		
		float log_hardness = 2.0f;
		float log_resistance = 2.0f;
		float wood_hardness = 2.0f;
		float wood_resistance = 3.0f;
		float leaf_hardness = 0.2f;
		float leaf_resistance = 0.2f;
		
		for (WoodTypes type : WoodTypes.values()) {
			int log_stair_idx = type.id / 2;
			int log_stair_meta = type.id & 0x1;
			int plank_slab_idx = type.id / 8;
			int plank_slab_meta = type.id & 0x7;
			int fence_idx = type.id / 16;
			int fence_meta = type.id & 0xf;
			int leaf_idx = type.id / 4;
			int leaf_meta = type.id & 0x3;
			GenericMultiBlockData data;
			
			data = log_data[log_stair_idx];
			data.metas_list.add(log_stair_meta);
			/* LogMultiBlockData handles log names and texture names */
			data.names_map.put(log_stair_meta, type.name);
			data.names_map.put(log_stair_meta | 0xc, type.name + "_wood");
			data.names_map.put(log_stair_meta | 0x2, type.name + "_stripped");
			data.names_map.put(log_stair_meta | 0xe, type.name + "_wood_stripped");
			data.texture_map.put(log_stair_meta, new BlockTexture(
				Type.LOG,
				"trees/" + type.name + "_log_end",
				"trees/" + type.name + "_log_side",
				"trees/" + type.name + "_stripped_log_end",
				"trees/" + type.name + "_stripped_log_side"
			));
			data.hardnesses_map.put(log_stair_meta, log_hardness);
			data.resistances_map.put(log_stair_meta, log_resistance);
			data.harvest_tools_map.put(log_stair_meta, "axe");
			data.harvest_levels_map.put(log_stair_meta, 0);
			data.fire_spread_speed_map.put(log_stair_meta, 5);
			data.flammability_map.put(log_stair_meta, 20);
			
			data = plank_data[plank_slab_idx];
			data.metas_list.add(plank_slab_meta);
			data.names_map.put(plank_slab_meta, type.name);
			data.texture_map.put(plank_slab_meta, new BlockTexture(Type.STANDARD, "trees/" + type.name + "_planks"));
			data.hardnesses_map.put(plank_slab_meta, wood_hardness);
			data.resistances_map.put(plank_slab_meta, wood_resistance);
			data.harvest_tools_map.put(plank_slab_meta, "axe");
			data.harvest_levels_map.put(plank_slab_meta, 0);
			data.fire_spread_speed_map.put(plank_slab_meta, 5);
			data.flammability_map.put(plank_slab_meta, 20);
			
			data = fence_data[fence_idx];
			data.metas_list.add(fence_meta);
			data.names_map.put(fence_meta, type.name);
			data.texture_map.put(fence_meta, new BlockTexture(Type.STANDARD, "trees/" + type.name + "_planks"));
			data.hardnesses_map.put(fence_meta, wood_hardness);
			data.resistances_map.put(fence_meta, wood_resistance);
			data.harvest_tools_map.put(fence_meta, "axe");
			data.harvest_levels_map.put(fence_meta, 0);
			data.fire_spread_speed_map.put(fence_meta, 5);
			data.flammability_map.put(fence_meta, 20);
			
			data = leaf_data[leaf_idx];
			data.metas_list.add(leaf_meta);
			data.names_map.put(leaf_meta, type.name);
			data.texture_map.put(leaf_meta, new BlockTexture(
				Type.LEAF,
				"trees/" + type.name + "_leaves",
				"trees/" + type.name + "_leaves_opaque"
			));
			data.hardnesses_map.put(leaf_meta, leaf_hardness);
			data.resistances_map.put(leaf_meta, leaf_resistance);
			data.fire_spread_speed_map.put(leaf_meta, 30);
			data.flammability_map.put(leaf_meta, 60);
			leaf_colors[leaf_idx][leaf_meta] = type.leaf_color;
			leaf_color_flags[leaf_idx][leaf_meta] = type.leaf_color_flag;
			
			data = sapling_data[plank_slab_idx];
			data.metas_list.add(plank_slab_meta);
			data.names_map.put(plank_slab_meta, type.name);
			data.texture_map.put(plank_slab_meta, new BlockTexture(Type.STANDARD, "trees/" + type.name + "_sapling"));
		}
		
		for (GenericMultiBlockData data : log_data) {
			data.finalizeMaps();
		}
		for (GenericMultiBlockData data : plank_data) {
			data.finalizeMaps();
		}
		for (GenericMultiBlockData data : fence_data) {
			data.finalizeMaps();
		}
		for (GenericMultiBlockData data : leaf_data) {
			data.finalizeMaps();
		}
		for (GenericMultiBlockData data : sapling_data) {
			data.finalizeMaps();
		}
		
		for (int i = 0; i < log_blocks.length; i++) {	
			log_blocks[i] = new BlockMultiLog("wood_logs_" + i, log_data[i]);
		}
		for (int i = 0; i < plank_blocks.length; i++) {
			plank_blocks[i] = new BlockMultiWithDoubleSlab("wood_planks_" + i, plank_data[i]);
		}
		for (int i = 0; i < slab_blocks.length; i++) {
			slab_blocks[i] = new BlockMultiSlab("wood_slabs_" + i, plank_data[i]);
		}
		for (int i = 0; i < plank_blocks.length; i++) {
			BlockMulti plank = plank_blocks[i];
			GenericMultiBlockData data = (GenericMultiBlockData) plank.data;
			for (int j = 0; j < 8; j += 2) {
				int m0 = j;
				int m1 = j + 1;
				boolean m0f = data.metas_list.contains(m0);
				boolean m1f = data.metas_list.contains(m1);
				BlockMultiStair stair;
				if (m0f && m1f) {
					stair = new BlockMultiStair(
						"wood_stairs_" + stairId(i, j),
						plank, m0, m1, false
					);
				} else if (m0f && !m1f) {
					stair = new BlockMultiStair(
						"wood_stairs_" + stairId(i, j),
						plank, m0, -1, false
					);
				} else if (!m0f && m1f) {
					stair = new BlockMultiStair(
						"wood_stairs_" + stairId(i, j),
						plank, -1, m1, true
					);
				} else {
					continue;
				}
				stair_blocks[stairId(i, j)] = stair;
			}
		}
		for (int i = 0; i < fence_blocks.length; i++) {
			fence_blocks[i] = new BlockMultiFence("wood_fences_" + i, fence_data[i]);
		}
		for (int i = 0; i < fence_gate_blocks.length; i++) {
			int plank_idx = i >> 3;
			int plank_meta = i & 0x7;
			String name = plank_blocks[plank_idx].data.getName(plank_meta);
			fence_gate_blocks[i] = new BlockFenceGate("wood_fence_gate." + name, "trees/" + name + "_planks", single_data);
		}
		for (int i = 0; i < pressure_plate_blocks.length; i++) {
			pressure_plate_blocks[i] = new BlockMultiPressurePlate("wood_pressure_plates_" + i, plank_data[i]);
		}
		for (int i = 0; i < button_blocks.length; i++) {
			int plank_idx = i >> 3;
			int plank_meta = i & 0x7;
			String name = plank_blocks[plank_idx].data.getName(plank_meta);
			button_blocks[i] = new BlockMultiButton("wood_button." + name, plank_data[plank_idx], plank_meta, false);
		}
		for (int i = 0; i < door_blocks.length; i++) {
			int plank_idx = i >> 3;
			int plank_meta = i & 0x7;
			String name = plank_blocks[plank_idx].data.getName(plank_meta);
			door_blocks[i] = new BlockDoor("wood_door." + name, single_data, "trees/" + name + "_door");
			trapdoor_blocks[i] = new BlockTrapdoor("wood_trapdoor." + name, single_data, "trees/" + name + "_trapdoor");
		}
		for (int i = 0; i < leaf_blocks.length; i++) {
			leaf_blocks[i] = new BlockMultiLeaves("leaves_" + i, leaf_data[i], leaf_colors[i], leaf_color_flags[i]);
		}
		for (int i = 0; i < sapling_blocks.length; i++) {
			sapling_blocks[i] = new BlockMultiSapling("saplings_" + i, sapling_data[i]);
		}
	}
	
	protected static void registerBlocks()
	{
		for (BlockMultiLog block : log_blocks) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		for (BlockMulti block : plank_blocks) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		for (BlockMultiSlab block : slab_blocks) {
			GameRegistry.registerBlock(block, ItemBlockMultiSlab.class, block.getUnlocalizedName());
		}
		for (BlockMultiStair block : stair_blocks) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		for (BlockMultiFence block : fence_blocks) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		for (BlockFenceGate block : fence_gate_blocks) {
			GameRegistry.registerBlock(block, block.getUnlocalizedName());
		}
		for (BlockMultiPressurePlate block : pressure_plate_blocks) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		for (BlockMultiButton block : button_blocks) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		for (BlockDoor block : door_blocks) {
			GameRegistry.registerBlock(block, ItemBlockDoor.class, block.getUnlocalizedName(), (Object) block.name);
		}
		for (BlockTrapdoor block : trapdoor_blocks) {
			GameRegistry.registerBlock(block, block.getUnlocalizedName());
		}
		for (BlockMultiLeaves block : leaf_blocks) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		for (BlockMultiSapling block : sapling_blocks) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
	}
	
	private static int stairId(int i, int j) {
		return (i * 4) + (j / 2);
	}
	
	protected static Block getBlockFromSlab(Block block)
	{
		for (int i = 0; i < slab_blocks.length; i++) {
			if (block == slab_blocks[i]) {
				return plank_blocks[i];
			}
		}
		return null;
	}
	
	protected static Block getSlabFromBlock(Block block)
	{
		for (int i = 0; i < plank_blocks.length; i++) {
			if (block == plank_blocks[i]) {
				return slab_blocks[i];
			}
		}
		return null;
	}
	
	public static WoodTypes getWoodFromLeaf(Block block, int meta)
	{
		WoodTypes type = null;
		meta &= 0x3;
		
		for (WoodTypes t : WoodTypes.values()) {
			if (t.getLeaves().block != block) {
				continue;
			}
			if (t.getLeaves().meta != meta) {
				continue;
			}
			type = t;
			break;
		}
		
		return type;
	}
	
	public static WoodTypes getWoodFromSapling(Block block, int meta)
	{
		WoodTypes type = null;
		meta &= 0x7;
		
		for (WoodTypes t : WoodTypes.values()) {
			if (t.getSapling().block != block) {
				continue;
			}
			if (t.getSapling().meta != meta) {
				continue;
			}
			type = t;
			break;
		}
		
		return type;
	}
	
	public final int id;
	public final String name;
	
	public final int leaf_color;
	public final boolean leaf_color_flag;
	
	private IIcon log_side_texture;
	private IIcon log_end_texture;
	private IIcon log_side_texture_stripped;
	private IIcon log_end_texture_stripped;
	private IIcon planks_texture;
	
	private WoodTypes(int id, String name, int leaf_color, boolean leaf_color_flag)
	{
		this.id = id;
		this.name = name;
		
		this.leaf_color = leaf_color;
		this.leaf_color_flag = leaf_color_flag;
	}
	
	public BlockMeta getLog()
	{
		Block block = log_blocks[this.id >> 1];
		int meta = this.id & 0x1;
		return new BlockMeta(block, meta);
	}
	
	public BlockMeta getStrippedLog()
	{
		BlockMeta log = this.getLog();
		return new BlockMeta(log.block, log.meta | 0x2);
	}
	
	public BlockMeta getWood()
	{
		BlockMeta log = this.getLog();
		return new BlockMeta(log.block, log.meta | 0xc);
	}
	
	public BlockMeta getStrippedWood()
	{
		BlockMeta log = this.getLog();
		return new BlockMeta(log.block, log.meta | 0xe);
	}
	
	public BlockMeta getPlanks()
	{
		Block block = plank_blocks[this.id >> 3];
		int meta = this.id & 0x7;
		return new BlockMeta(block, meta);
	}
	
	public BlockMeta getSlab()
	{
		Block block = slab_blocks[this.id >> 3];
		int meta = this.id & 0x7;
		return new BlockMeta(block, meta);
	}
	
	public BlockMeta getStair()
	{
		Block block = stair_blocks[this.id >> 1];
		int meta = (this.id & 0x1) << 3;
		return new BlockMeta(block, meta);
	}
	
	public BlockMeta getFence()
	{
		Block block = fence_blocks[this.id >> 4];
		int meta = this.id & 0xf;
		return new BlockMeta(block, meta);
	}
	
	public BlockMeta getFenceGate()
	{
		return new BlockMeta(fence_gate_blocks[this.id], 0);
	}
	
	public BlockMeta getPressurePlate()
	{
		Block block = pressure_plate_blocks[this.id >> 3];
		int meta = (this.id & 0x7) << 1;
		return new BlockMeta(block, meta);
	}
	
	public BlockMeta getButton()
	{
		return new BlockMeta(button_blocks[this.id], 0);
	}
	
	public BlockMeta getDoor()
	{
		return new BlockMeta(door_blocks[this.id], 0);
	}
	
	public BlockMeta getTrapdoor()
	{
		return new BlockMeta(trapdoor_blocks[this.id], 0);
	}
	
	public BlockMeta[] getAllWoodBlocks()
	{
		return new BlockMeta[] {
			this.getLog(),
			this.getWood(),
			this.getStrippedLog(),
			this.getStrippedWood(),
			this.getPlanks(),
			this.getSlab(),
			this.getStair(),
			this.getFence(),
			this.getFenceGate(),
			this.getPressurePlate(),
			this.getButton(),
			this.getDoor(),
			this.getTrapdoor()
		};
	}
	
	public BlockMeta getLeaves()
	{
		Block block = leaf_blocks[this.id >> 2];
		int meta = this.id & 0x3;
		return new BlockMeta(block, meta);
	}
	
	public BlockMeta getLeavesPersistent()
	{
		BlockMeta leaves = this.getLeaves();
		return new BlockMeta(leaves.block, leaves.meta | 0x4);
	}
	
	public BlockMeta getSapling()
	{
		Block block = sapling_blocks[this.id >> 3];
		int meta = this.id & 0x7;
		return new BlockMeta(block, meta);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		String path = ResAdditae.ident("trees/" + this.name + "_");
		this.log_side_texture = reg.registerIcon(path + "log_side");
		this.log_end_texture = reg.registerIcon(path + "log_end");
		this.log_side_texture_stripped = reg.registerIcon(path + "stripped_log_side");
		this.log_end_texture_stripped = reg.registerIcon(path + "stripped_log_end");
		this.planks_texture = reg.registerIcon(path + "planks");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getLogSideTexture(int meta)
	{
		if ((meta & 0x2) != 0) {
			return this.log_side_texture_stripped;
		}
		return this.log_side_texture;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getLogEndTexture(int meta)
	{
		if ((meta & 0x2) != 0) {
			return this.log_end_texture_stripped;
		}
		return this.log_end_texture;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getPlanksTexture(int meta)
	{
		return this.planks_texture;
	}
}
