package com.captrojo.resadditae.block.utility;

import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.block.IDumbMultiBlock;
import com.captrojo.resadditae.block.IUnlockable;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.tileentity.TEVault;
import com.captrojo.resadditae.world.loot.LootGroup;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockVaultBase extends Block implements IDumbMultiBlock, ITileEntityProvider, IUnlockable
{
	private final String[] texture_names;
	private IIcon[] filled_side_textures;
	private IIcon[] filled_end_textures;
	private IIcon[] empty_side_textures;
	private IIcon[] empty_end_textures;
	
	protected int meta_count = 1;
	
	public BlockVaultBase(String name, String[] texture_names)
	{
		super(Material.iron);
		this.texture_names = texture_names;
		
		this.setBlockName(name);
		this.setStepSound(soundTypeMetal);
		this.setHardness(5.0f);
		this.setResistance(5.0f);
		this.useNeighborBrightness = true;
	}
	
	protected abstract LootGroup getLoot(World world, int meta);
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random rand)
	{
		return 0;
	}
	
	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.filled_side_textures = new IIcon[this.meta_count];
		this.filled_end_textures = new IIcon[this.meta_count];
		this.empty_side_textures = new IIcon[this.meta_count];
		this.empty_end_textures = new IIcon[this.meta_count];
		
		for (int i = 0; i < this.meta_count; i++) {
			String name = ResAdditae.ident(this.texture_names[i]);
			this.filled_side_textures[i] = reg.registerIcon(name + "_filled_side");
			this.filled_end_textures[i] = reg.registerIcon(name + "_filled_end");
			this.empty_side_textures[i] = reg.registerIcon(name + "_empty_side");
			this.empty_end_textures[i] = reg.registerIcon(name + "_empty_end");
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		int meta = world.getBlockMetadata(x, y, z);
		TEVault te = (TEVault) world.getTileEntity(x, y, z);
		if (te.hasLoot()) {
			return this.getIcon(side, meta);
		}
		if (meta >= this.meta_count) {
			meta = 0;
		}
		if (side < 2) {
			return this.empty_end_textures[meta];
		}
		return this.empty_side_textures[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (meta >= this.meta_count) {
			meta = 0;
		}
		if (side < 2) {
			return this.filled_end_textures[meta];
		}
		return this.filled_side_textures[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int m = 0; m < this.meta_count; m++) {
			list.add(new ItemStack(item, 1, m));
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TEVault(this.getLoot(world, meta));
	}
	
	@Override
	public boolean consumesKey(World world, EntityPlayer player, int x, int y, int z, int key)
	{
		return key != 0;
	}
	
	@Override
	public boolean canBeUnlocked(World world, EntityPlayer player, int x, int y, int z, int key)
	{
		return ((TEVault) world.getTileEntity(x, y, z)).hasLoot();
	}
	
	@Override
	public void unlockBlock(World world, EntityPlayer player, int x, int y, int z, int key)
	{
		((TEVault) world.getTileEntity(x, y, z)).dispenseItems();
	}
}
