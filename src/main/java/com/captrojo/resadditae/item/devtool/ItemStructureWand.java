package com.captrojo.resadditae.item.devtool;

import java.util.List;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.util.Consts;
import com.captrojo.resadditae.util.CoordHlpr;
import com.captrojo.resadditae.util.I18nHlpr;
import com.captrojo.resadditae.util.ItemHlpr;
import com.captrojo.resadditae.world.gen.structure.nbt.StructureComponentNBT;
import com.captrojo.resadditae.world.gen.structure.nbt.StructurePieceNBT;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemStructureWand extends Item
{
	private static final String[] NAMES = new String[] {
		"save",
		"place",
		"fill",
		"clone"
	};
	
	public static boolean fill_wand_void_behavior = true;
	
	private static void useSaveWand(ItemStack stack, NBTTagCompound nbt, EntityPlayer player, World world, int x, int y, int z)
	{
		byte phase = nbt.getByte("Phase");
		
		if (phase == 0) {
			nbt.setInteger("x1", x);
			nbt.setInteger("y1", y);
			nbt.setInteger("z1", z);
			nbt.setByte("Phase", (byte) 1);
			player.addChatMessage(I18nHlpr.chatf("item.structure_wand.chat.firstpos", x, y, z));
			return;
		}
		if (phase == 1) {
			nbt.setInteger("x2", x);
			nbt.setInteger("y2", y);
			nbt.setInteger("z2", z);
			nbt.setByte("Phase", (byte) 2);
			player.addChatMessage(I18nHlpr.chatf("item.structure_wand.chat.secondpos", x, y, z));
			return;
		}
		if (phase == 2) {
			nbt.setInteger("xO", x);
			nbt.setInteger("yO", y);
			nbt.setInteger("zO", z);
			nbt.setByte("Phase", (byte) 0);
			player.addChatMessage(I18nHlpr.chatf("item.structure_wand.save.chat.originpos", x, y, z));
			player.addChatMessage(I18nHlpr.chatf("item.structure_wand.save.chat.cmdhint"));
			return;
		}
	}
	
	private static void usePlaceWand(ItemStack stack, NBTTagCompound nbt, EntityPlayer player, World world, int x, int y, int z)
	{
		if (!nbt.hasKey("Structure")) {
			player.addChatMessage(I18nHlpr.chatf("item.structure_wand.place.chat.notloaded"));
			return;
		}
		
		player.addChatMessage(I18nHlpr.chatf("item.structure_wand.place.chat.placing"));
		StructurePieceNBT struct = new StructurePieceNBT().loadFromNBT(nbt.getCompoundTag("Structure"));
		StructureComponentNBT sc = new StructureComponentNBT(struct, x, y, z, Consts.NORTH) {};
		sc.addComponentParts(world, world.rand, Consts.SBB_MINMAX);
		player.addChatMessage(I18nHlpr.chatf("item.structure_wand.place.chat.placed"));
	}
	
	private static void useFillWand(ItemStack stack, NBTTagCompound nbt, EntityPlayer player, World world, int x, int y, int z)
	{
		if (player.isSneaking()) {
			Block block = world.getBlock(x, y, z);
			UniqueIdentifier uidr = GameRegistry.findUniqueIdentifierFor(block);
			nbt.setString("Block", uidr.toString());
			
			int meta = world.getBlockMetadata(x, y, z);
			nbt.setShort("Meta", (short) meta);
			
			player.addChatMessage(I18nHlpr.chatf("item.structure_wand.fill.chat.selblock", uidr.toString(), meta));
			return;
		}
		
		if (nbt.hasKey("x1")) {
			Block block = Blocks.air;
			int meta = 0;
			
			if (!nbt.hasKey("Block")) {
//				player.addChatMessage(I18nHlpr.chat("item.structure_wand.fill.chat.noselblock"));
//				return;
			} else {
				UniqueIdentifier uidr = new UniqueIdentifier(nbt.getString("Block"));
				block = GameRegistry.findBlock(uidr.modId, uidr.name);
				meta = nbt.getShort("meta");
			}
			
			/* Make placing structure voids easier */
			boolean air_only = (
				ItemStructureWand.fill_wand_void_behavior &&
				(block == ModBlocks.structure_block && meta == 0)
			);
			
			int x1 = nbt.getInteger("x1");
			int y1 = nbt.getInteger("y1");
			int z1 = nbt.getInteger("z1");
			nbt.removeTag("x1");
			nbt.removeTag("y1");
			nbt.removeTag("z1");
			int[] arr = CoordHlpr.fixCorners(x1, y1, z1, x, y, z);
			x1 = arr[0];
			y1 = arr[1];
			z1 = arr[2];
			int x2 = arr[3], y2 = arr[4], z2 = arr[5];
			
			for (int xa = x1; xa <= x2; xa++) {
				for (int ya = y1; ya <= y2; ya++) {
					for (int za = z1; za <= z2; za++) {
						if (air_only && !world.isAirBlock(xa, ya, za)) {
							continue;
						}
						world.setBlock(xa, ya, za, block, meta, 2);
					}
				}
			}
			
			player.addChatMessage(I18nHlpr.chatf("item.structure_wand.fill.chat.filled", x1, y1, z1, x2, y2, z2));
			return;
		}
		
		nbt.setInteger("x1", x);
		nbt.setInteger("y1", y);
		nbt.setInteger("z1", z);
		player.addChatMessage(I18nHlpr.chatf("item.structure_wand.chat.firstpos", x, y, z));
	}
	
	private static void useCloneWand(ItemStack stack, NBTTagCompound nbt, EntityPlayer player, World world, int x, int y, int z)
	{
		if (player.isSneaking()) {
			int phase = nbt.getByte("P");
			nbt.setByte("P", (byte) (phase ^ 0x1));
			if (phase == 0) {
				nbt.setInteger("x1", x);
				nbt.setInteger("y1", y);
				nbt.setInteger("z1", z);
				player.addChatMessage(I18nHlpr.chatf("item.structure_wand.chat.firstpos", x, y, z));
			} else {
				nbt.setInteger("x2", x);
				nbt.setInteger("y2", y);
				nbt.setInteger("z2", z);
				player.addChatMessage(I18nHlpr.chatf("item.structure_wand.chat.secondpos", x, y, z));
			}
			return;
		}
		
		if (!nbt.hasKey("x1") || !nbt.hasKey("x2")) {
			player.addChatMessage(I18nHlpr.chat("item.structure_wand.clone.chat.noregion"));
			return;
		}
		
		int[] arr = CoordHlpr.fixCorners(
			nbt.getInteger("x1"), nbt.getInteger("y1"), nbt.getInteger("z1"),
			nbt.getInteger("x2"), nbt.getInteger("y2"), nbt.getInteger("z2")
		);
		int x1 = arr[0], y1 = arr[1], z1 = arr[2], x2 = arr[3], y2 = arr[4], z2 = arr[5];
		int xs = x2 - x1, ys = y2 - y1, zs = z2 - z1;
		
		for (int xo = 0; xo <= xs; xo++) {
			int xa = x1 + xo;
			int xb = x + xo;
			for (int yo = 0; yo <= ys; yo++) {
				int ya = y1 + yo;
				int yb = y + yo;
				for (int zo = 0; zo <= zs; zo++) {
					int za = z1 + zo;
					int zb = z + zo;
					
					Block block = world.getBlock(xa, ya, za);
					int meta = world.getBlockMetadata(xa, ya, za);
					TileEntity te = world.getTileEntity(xa, ya, za);
					
					world.setBlock(xb, yb, zb, block, meta, 2);
					if (te != null) {
						world.setTileEntity(xb, yb, zb, te);
					}
				}
			}
		}
		
		player.addChatMessage(I18nHlpr.chat("item.structure_wand.clone.chat.cloned"));
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
		
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}
		
		switch (stack.getItemDamage()) {
		case 0:
			ItemStructureWand.useSaveWand(stack, nbt, player, world, x, y, z);
			break;
		case 1:
			ItemStructureWand.usePlaceWand(stack, nbt, player, world, x, y, z);
			break;
		case 2:
			ItemStructureWand.useFillWand(stack, nbt, player, world, x, y, z);
			break;
		case 3:
			ItemStructureWand.useCloneWand(stack, nbt, player, world, x, y, z);
			break;
		default:
			break;
		}
		
		stack.setTagCompound(nbt);
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
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}
		
		switch (meta) {
		case 0:
			list.add(I18nHlpr.getf(
				"item.structure_wand.desc.firstpos",
				nbt.getInteger("x1"), nbt.getInteger("y1"), nbt.getInteger("z1")
			));
			list.add(I18nHlpr.getf(
				"item.structure_wand.desc.secondpos",
				nbt.getInteger("x2"), nbt.getInteger("y2"), nbt.getInteger("z2")
			));
			list.add(I18nHlpr.getf(
				"item.structure_wand.save.desc.originpos",
				nbt.getInteger("xO"), nbt.getInteger("yO"), nbt.getInteger("zO")
			));
			break;
		case 1:
			list.add(I18nHlpr.getf("item.structure_wand.place.desc.struct", nbt.getString("StructureName")));
			break;
		case 2:
			list.add(I18nHlpr.getf(
				"item.structure_wand.fill.desc.block",
				nbt.getString("Block"), nbt.getShort("Meta")
			));
			list.add(I18nHlpr.getf(
				"item.structure_wand.desc.firstpos",
				nbt.getInteger("x1"), nbt.getInteger("y1"), nbt.getInteger("z1")
			));
			break;
		case 3:
			list.add(I18nHlpr.getf(
				"item.structure_wand.desc.firstpos",
				nbt.getInteger("x1"), nbt.getInteger("y1"), nbt.getInteger("z1")
			));
			list.add(I18nHlpr.getf(
				"item.structure_wand.desc.secondpos",
				nbt.getInteger("x2"), nbt.getInteger("y2"), nbt.getInteger("z2")
			));
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
		this.textures[2] = reg.registerIcon(ResAdditae.ident("devtools/structure_wand_fill"));
		this.textures[3] = reg.registerIcon(ResAdditae.ident("devtools/structure_wand_clone"));
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
