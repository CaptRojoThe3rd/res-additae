package com.captrojo.resadditae.item.equipment;

import java.util.ArrayList;
import java.util.List;

import com.captrojo.resadditae.main.ItemHlpr;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.material.ExtendedToolMaterial;
import com.google.common.collect.Sets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemScythe extends ItemTool
{
	private final ExtendedToolMaterial extmat;
	
	public ItemScythe(String name, String texture_name, ToolMaterial mat, 
			ExtendedToolMaterial extmat)
	{
		super(3f, mat, Sets.newHashSet(new Block[] {}));
		this.setUnlocalizedName(name);
		this.setTextureName(ResAdditae.ident(texture_name));
		this.setCreativeTab(null);
		
		this.extmat = extmat;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, 
			int x, int y, int z, EntityLivingBase entity)
	{
		if (world.isRemote) return false;
		
		if (!(block instanceof BlockCrops || block instanceof BlockTallGrass)) {
			stack.damageItem(2, entity);
			return false;
		}
		
		stack.damageItem(1, entity);
		
		int fortune = 0;
		NBTTagList tags = stack.getEnchantmentTagList();
		
		if (tags != null) {
			for (int i = 0; i < tags.tagCount(); i++) {
				short id = tags.getCompoundTagAt(i).getShort("id");
				short lvl = tags.getCompoundTagAt(i).getShort("lvl");
				
				if (id == 35) {
					fortune = lvl;
					break;
				}
			}
		}
		
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		int times = (int) this.extmat.crop_multiplier;
		float dec = this.extmat.crop_multiplier - ((int) this.extmat.crop_multiplier);
		if (world.rand.nextFloat() < dec) {
			times++;
		}
		for (int i = 0; i < times; i++) {
			ArrayList<ItemStack> drops0 = block.getDrops(world, x, y, z, 
				world.getBlockMetadata(x, y, z), fortune);
			for (ItemStack v : drops0) {
				drops.add(v);
			}
		}
		for (ItemStack v : drops) {
			double x0 = (double) (world.rand.nextFloat() * 0.7f) + 0.15d;
			double y0 = (double) (world.rand.nextFloat() * 0.7f) + 0.15d;
			double z0 = (double) (world.rand.nextFloat() * 0.7f) + 0.15d;
			EntityItem item = new EntityItem(world, 
				(double) x + x0, (double) y + y0, (double) z + z0, v);
			item.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(item);
		}
		
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
	{
		ItemHlpr.addItemDescription(stack, list);
	}
}
