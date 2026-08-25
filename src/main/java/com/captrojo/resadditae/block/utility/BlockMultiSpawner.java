package com.captrojo.resadditae.block.utility;

import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.block.IDumbMultiBlock;
import com.captrojo.resadditae.block.IStructureActor;
import com.captrojo.resadditae.tileentity.TEMultiSpawner;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMultiSpawner extends Block implements IDumbMultiBlock, ITileEntityProvider, IStructureActor
{
	private final String[] names = {"on", "off", "strong_on", "strong_off"};
	
	private Random rand = new Random();
	
	protected int meta_count = 4;
	
	public BlockMultiSpawner()
	{
		super(Material.iron);
		
		this.setBlockName("multi_spawner");
		this.setBlockTextureName("mob_spawner");
		this.setCreativeTab(null);
		this.setStepSound(soundTypeMetal);
		this.setHardness(5.0f);
		this.setResistance(5.0f);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz)
	{
		TEMultiSpawner te = (TEMultiSpawner) world.getTileEntity(x, y, z);
		
		if (player.isSneaking()) {
			if (world.isRemote) {
				return true;
			}
			te.entity_list.clear();
			return true;
		}
		
		ItemStack held = player.getHeldItem();
		if (held == null) {
			return false;
		}
		if (held.getItem() != Items.spawn_egg) {
			return false;
		}
		if (world.isRemote) {
			return true;
		}
		
		EntityEggInfo eei = (EntityEggInfo) EntityList.entityEggs.get(held.getItemDamage());
		String name = EntityList.getStringFromID(eei.spawnedID);
		te.addEntityToList(name);
		return true;
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune)
	{
		return null;
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
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public int getExpDrop(IBlockAccess world, int meta, int fortune)
	{
		return 15 + this.rand.nextInt(15) + this.rand.nextInt(15);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < this.meta_count; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String[] getNames()
	{
		return this.names;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TEMultiSpawner();
	}
	
	@Override
	public void onPlacedInStructure(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z) & 0xe;
		world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		((TEMultiSpawner) world.getTileEntity(x, y, z)).onPlacedInStructure();
	}
}
