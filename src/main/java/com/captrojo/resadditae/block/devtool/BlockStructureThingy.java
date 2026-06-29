package com.captrojo.resadditae.block.devtool;

import java.util.List;

import com.captrojo.resadditae.block.IDumbMultiBlock;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.tileentity.TEStructureBlock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockStructureThingy extends Block implements IDumbMultiBlock, ITileEntityProvider
{
	private static final String[] NAMES = {
		"void",
		"optional",
		"loot",
		"entity"
	};
	
	public IIcon[] textures;
	public IIcon[] number_textures;
	
	public BlockStructureThingy()
	{
		super(Material.plants);
		
		this.setBlockName("structure_block");
		this.setBlockUnbreakable();
		this.useNeighborBrightness = true;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz)
	{
		ItemStack held = player.getHeldItem();
		int meta = world.getBlockMetadata(x, y, z);
		TEStructureBlock te = (TEStructureBlock) world.getTileEntity(x, y, z);
		
		if (meta == 1 || meta == 2) {
			if (held == null) {
				if (world.isRemote) {
					return true;
				}
				if (player.isSneaking()) {
					te.reset();
				} else {
					te.incIdx();
				}
				return true;
			}
			
			if (player.isSneaking()) {
				return false;
			}
			
			Item item = held.getItem();
			if (!(item instanceof ItemBlock)) {
				return false;
			}
			Block block = Block.getBlockFromItem(item);
			if (block == this) {
				return false;
			}
			if (meta == 2 && !(block instanceof BlockContainer)) {
				return false;
			}
			
			if (!world.isRemote) {
				te.setStoredBlock(held, meta, side);
			}
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
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
		this.textures = new IIcon[NAMES.length];
		this.textures[0] = reg.registerIcon(ResAdditae.ident("structure_block/void"));
		this.textures[1] = reg.registerIcon(ResAdditae.ident("structure_block/optional"));
		this.textures[2] = reg.registerIcon(ResAdditae.ident("structure_block/loot"));
		this.textures[3] = reg.registerIcon(ResAdditae.ident("structure_block/entity"));
		
		this.number_textures = new IIcon[10];
		for (int i = 0; i < 10; i++) {
			this.number_textures[i] = reg.registerIcon(ResAdditae.ident("structure_block/" + i));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (meta > this.textures.length) {
			meta = 0;
		}
		return this.textures[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < NAMES.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String[] getNames()
	{
		return NAMES;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TEStructureBlock();
	}
}
