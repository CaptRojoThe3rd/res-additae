package com.captrojo.resadditae.block.utility;

import com.captrojo.resadditae.block.IUnlockable;
import com.captrojo.resadditae.item.MultiItems;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.tileentity.TESnowDungeonSpawner;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSnowDungeonSpawner extends BlockMultiSpawner implements IUnlockable
{
	private final String[] names = {
		"lvl_1_on",
		"lvl_1_off",
		"lvl_2_on",
		"lvl_2_off",
		"lvl_3_on",
		"lvl_3_off",
		"lvl_4_on",
		"lvl_4_off",
		"lvl_5_on",
		"lvl_5_off",
		"lvl_6_on",
		"lvl_6_off",
		"lvl_7_on",
		"lvl_7_off",
		"lvl_8_on",
		"lvl_8_off"
	};
	
	private IIcon[] icons_inactive;
	private IIcon[] icons_active;
	private IIcon[] icons_spent;
	
	public BlockSnowDungeonSpawner()
	{
		super();
		this.meta_count = 16;
		
		this.setBlockName("snow_dungeon_spawner");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.icons_inactive = new IIcon[8];
		this.icons_active = new IIcon[8];
		this.icons_spent = new IIcon[8];
		
		for (int i = 1; i < 9; i++) {
			this.icons_inactive[i - 1] = reg.registerIcon(ResAdditae.ident("snow_dungeon/spawner_" + i + "_inactive"));
			this.icons_active[i - 1] = reg.registerIcon(ResAdditae.ident("snow_dungeon/spawner_" + i + "_active"));
			this.icons_spent[i - 1] = reg.registerIcon(ResAdditae.ident("snow_dungeon/spawner_" + i + "_spent"));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return this.icons_inactive[meta >> 1];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		int meta = world.getBlockMetadata(x, y, z) >> 1;
		TESnowDungeonSpawner te = (TESnowDungeonSpawner) world.getTileEntity(x, y, z);
		if (te.isSpent()) {
			return this.icons_spent[meta];
		}
		if (te.isActive()) {
			return this.icons_active[meta];
		}
		return this.icons_inactive[meta];
	}
	
	@Override
	public String[] getNames()
	{
		return this.names;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TESnowDungeonSpawner();
	}

	@Override
	public boolean isCorrectKey(World world, EntityPlayer player, int x, int y, int z, int key)
	{
		return key == 0 || key == 0x1f;
	}

	@Override
	public boolean consumesKey(World world, EntityPlayer player, int x, int y, int z, int key)
	{
		return key != 0;
	}

	@Override
	public boolean canBeUnlocked(World world, EntityPlayer player, int x, int y, int z, int key)
	{
		TESnowDungeonSpawner te = (TESnowDungeonSpawner) world.getTileEntity(x, y, z);
		return te.isSpent();
	}

	@Override
	public void unlockBlock(World world, EntityPlayer player, int x, int y, int z, int key)
	{
		TESnowDungeonSpawner te = (TESnowDungeonSpawner) world.getTileEntity(x, y, z);
		te.setState(TESnowDungeonSpawner.State.INACTIVE);
	}
}
