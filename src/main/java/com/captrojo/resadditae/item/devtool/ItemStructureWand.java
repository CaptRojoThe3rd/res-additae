package com.captrojo.resadditae.item.devtool;

import java.util.List;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.main.I18nHlpr;
import com.captrojo.resadditae.main.ItemHlpr;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.tileentity.TEStructureBlock;
import com.captrojo.resadditae.world.structure.StructurePiece;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemStructureWand extends Item
{
	private static final String[] NAMES = new String[] {"save", "place", "loot"};
	
	private static void useSaveWand(ItemStack stack, NBTTagCompound tag, EntityPlayer player, World world, int x, int y, int z)
	{
		byte phase = tag.getByte("phase");
		
		if (phase == 0) {
			tag.setInteger("x1", x);
			tag.setInteger("y1", y);
			tag.setInteger("z1", z);
			tag.setByte("phase", (byte) 1);
			player.addChatMessage(I18nHlpr.chatf("item.structure_wand.save.chat.firstpos", x, y, z));
			return;
		}
		if (phase == 1) {
			tag.setInteger("x2", x);
			tag.setInteger("y2", y);
			tag.setInteger("z2", z);
			tag.setByte("phase", (byte) 2);
			player.addChatMessage(I18nHlpr.chatf("item.structure_wand.save.chat.secondpos", x, y, z));
			return;
		}
		if (phase == 2) {
			tag.setInteger("ox", x);
			tag.setInteger("oy", y);
			tag.setInteger("oz", z);
			tag.setByte("phase", (byte) 0);
			player.addChatMessage(I18nHlpr.chatf("item.structure_wand.save.chat.originpos", x, y, z));
			player.addChatMessage(I18nHlpr.chatf("item.structure_wand.save.chat.cmdhint"));
			return;
		}
	}
	
	private static void usePlaceWand(ItemStack stack, NBTTagCompound tag, EntityPlayer player, World world, int x, int y, int z)
	{
		if (!tag.hasKey("structure")) {
			player.addChatMessage(I18nHlpr.chatf("item.structure_wand.place.chat.notloaded"));
			return;
		}
		
		player.addChatMessage(I18nHlpr.chatf("item.structure_wand.place.chat.placing"));
		StructurePiece structure = new StructurePiece(tag.getCompoundTag("structure"));
		structure.placeInWorld(world, world.rand, x, y, z, 0l);
		player.addChatMessage(I18nHlpr.chatf("item.structure_wand.place.chat.placed"));
	}
	
	private static void useLootWand(ItemStack stack, NBTTagCompound tag, EntityPlayer player, World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		
		if (block != ModBlocks.structure_block || meta != 2) {
			player.addChatMessage(I18nHlpr.chatf("item.structure_wand.loot.chat.invalid_block"));
			return;
		}
		
		TEStructureBlock te = (TEStructureBlock) world.getTileEntity(x, y, z);
		tag.setByte("idx", (byte) te.idx);
		
		tag.setInteger("current_pool", 0);
		tag.setInteger("current_item", 0);
		
		tag.setInteger("x", x);
		tag.setInteger("y", y);
		tag.setInteger("z", z);
		player.addChatMessage(I18nHlpr.chatf("item.structure_wand.loot.chat.selected", x, y, z));
	}
	
	private IIcon[] textures;
	
	public ItemStructureWand()
	{
		this.setUnlocalizedName("structure_wand");
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hx, float hy, float hz)
	{
		if (world.isRemote) {
			return true;
		}
		
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		
		switch (stack.getItemDamage()) {
		case 0:
			useSaveWand(stack, tag, player, world, x, y, z);
			break;
		case 1:
			usePlaceWand(stack, tag, player, world, x, y, z);
			break;
		case 2:
			useLootWand(stack, tag, player, world, x, y, z);
			break;
		default:
			break;
		}
		
		stack.setTagCompound(tag);
		return true;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int meta = stack.getItemDamage();
		if (meta > NAMES.length) {
			return this.getUnlocalizedName();
		}
		return this.getUnlocalizedName() + "." + this.NAMES[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < this.textures.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
	{
		ItemHlpr.addItemDescription(stack, list);
		
		int meta = stack.getItemDamage();
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		
		switch (meta) {
		case 0:
			list.add(I18n.format("item.structure_wand.save.desc.firstpos", tag.getInteger("x1"), tag.getInteger("y1"), tag.getInteger("z1")));
			list.add(I18n.format("item.structure_wand.save.desc.secondpos", tag.getInteger("x2"), tag.getInteger("y2"), tag.getInteger("z2")));
			list.add(I18n.format("item.structure_wand.save.desc.originpos", tag.getInteger("ox"), tag.getInteger("oy"), tag.getInteger("oz")));
			break;
		case 1:
			list.add(I18n.format("item.structure_wand.place.desc.struct", tag.getString("structure_name")));
			break;
		case 2:
			list.add(I18n.format("item.structure_wand.loot.desc.pos", tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z")));
			list.add(I18n.format("item.structure_wand.loot.desc.idx", tag.getByte("idx")));
			break;
		default:
			break;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		this.textures = new IIcon[NAMES.length];
		this.textures[0] = reg.registerIcon(ResAdditae.ident("devtools/structure_wand_save"));
		this.textures[1] = reg.registerIcon(ResAdditae.ident("devtools/structure_wand_place"));
		this.textures[2] = reg.registerIcon(ResAdditae.ident("devtools/structure_wand_loot"));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta)
	{
		if (meta > this.textures.length) {
			meta = 0;
		}
		return this.textures[meta];
	}
}
