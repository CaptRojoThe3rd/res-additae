package com.captrojo.resadditae.block.ore;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.block.IDumbMultiBlock;
import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public abstract class BlockOreBase extends Block implements IDumbMultiBlock
{
//	private static final String[] NAMES = {
//		"regular",
//		"nether",
//		"end",
//		"depths",
//		"depths_dirty",
//		"depths_aqua"
//	};
	
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	private final String name;
	private final OreStones[] stones;
	
	private HashMap<BlockMeta, BlockMeta> tgt_ore_map;
	
	public BlockOreBase(String name, OreStones[] stones)
	{
		super(Material.rock);
		
		this.name = name;
		this.stones = stones;
		
		this.setBlockName("ore_" + name);
	}
	
	public abstract ItemStack itemDropped(int meta);
	
	public HashMap<BlockMeta, BlockMeta> getTgtOreMap()
	{
		if (this.tgt_ore_map == null) {
			this.tgt_ore_map = new HashMap<BlockMeta, BlockMeta>();
			this.tgt_ore_map.put(new BlockMeta(Blocks.stone, 0), new BlockMeta(this, OreStones.STONE.ordinal()));
			this.tgt_ore_map.put(new BlockMeta(Blocks.netherrack, 0), new BlockMeta(this, OreStones.NETHER.ordinal()));
			this.tgt_ore_map.put(new BlockMeta(Blocks.end_stone, 0), new BlockMeta(this, OreStones.END.ordinal()));
			this.tgt_ore_map.put(new BlockMeta(ModBlocks.depth_stones, 0), new BlockMeta(this, OreStones.DEPTHS.ordinal()));
			this.tgt_ore_map.put(new BlockMeta(ModBlocks.depth_stones, 1), new BlockMeta(this, OreStones.DEPTHS_AMBER.ordinal()));
			this.tgt_ore_map.put(new BlockMeta(ModBlocks.depth_stones, 2), new BlockMeta(this, OreStones.DEPTHS_JADE.ordinal()));
			this.tgt_ore_map.put(new BlockMeta(ModBlocks.depth_stones, 3), new BlockMeta(this, OreStones.DEPTHS_RUBY.ordinal()));
			this.tgt_ore_map.put(new BlockMeta(ModBlocks.depth_stones, 4), new BlockMeta(this, OreStones.DEPTHS_SAPPHIRE.ordinal()));
			this.tgt_ore_map.put(new BlockMeta(ModBlocks.depth_stones, 5), new BlockMeta(this, OreStones.DEPTHS_TOPAZ.ordinal()));
		}
		return this.tgt_ore_map;
	}
	
	public int fixMeta(int meta)
	{
		if (meta >= OreStones.values().length) {
			meta = this.stones[0].ordinal();
		}
		return meta;
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune)
	{
		return this.itemDropped(meta).getItem();
	}
	
	@Override
	public int damageDropped(int meta)
	{
		return this.itemDropped(meta).getItemDamage();
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random rand)
	{
		if (this.getItemDropped(meta, rand, fortune) == Item.getItemFromBlock(this)) {
			return 1;
		}
		return this.quantityDropped(rand) * (1 + rand.nextInt(fortune + 1));
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		int meta = this.fixMeta(world.getBlockMetadata(x, y, z));
		return OreStones.values()[meta].hardness;
	}
	
	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double ex, double ey, double ez)
	{
		int meta = this.fixMeta(world.getBlockMetadata(x, y, z));
		return OreStones.values()[meta].resistance;
	}
	
	@Override
	public int getDamageValue(World world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.icons = new IIcon[OreStones.values().length];
		for (OreStones stone : this.stones) {
			String texture = ResAdditae.ident(this.name + "/ore" + stone.texture_suffix);
			this.icons[stone.ordinal()] = reg.registerIcon(texture);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return this.icons[this.fixMeta(meta)];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (OreStones stone : this.stones) {
			list.add(new ItemStack(this, 1, stone.ordinal()));
		}
	}
	
	@Override
	public String[] getNames()
	{
//		return NAMES;
		return null;
	}
}
