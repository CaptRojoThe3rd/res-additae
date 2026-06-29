package com.captrojo.resadditae.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.main.I18nHlpr;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.world.loot.LootGroup;
import com.captrojo.resadditae.world.loot.LootItem;
import com.captrojo.resadditae.world.loot.LootPool;
import com.captrojo.resadditae.world.structure.StructurePiece;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class CommandRAStruct extends CommandBase
{
	private static HashMap<String, NBTTagCompound> loot_memory = new HashMap<String, NBTTagCompound>();

	@Override
	public int getRequiredPermissionLevel()
	{
		return 1;
	}
	
	@Override
	public String getCommandName()
	{
		return "rastruct";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "commands.rastruct.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (args.length < 1) {
			throw new WrongUsageException(this.getCommandUsage(sender));
		}
		
		EntityPlayer player = (EntityPlayer) sender;
		
		if (args[0].equals("save-to-disk")) {
			if (args.length < 2) {
				throw new WrongUsageException("commands.rastruct.save-to-disk.usage");
			}
			
			ItemStack held = player.getHeldItem();
			if (held == null) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.save-to-disk.nowand"));
				return;
			}
			if (held.getItem() != ModItems.structure_wand || held.getItemDamage() != 0) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.save-to-disk.nowand"));
				return;
			}
			
			NBTTagCompound htag = held.getTagCompound();
			int x1 = htag.getInteger("x1");
			int y1 = htag.getInteger("y1");
			int z1 = htag.getInteger("z1");
			int x2 = htag.getInteger("x2");
			int y2 = htag.getInteger("y2");
			int z2 = htag.getInteger("z2");
			int ox = htag.getInteger("ox");
			int oy = htag.getInteger("oy");
			int oz = htag.getInteger("oz");
			StructurePiece struct = new StructurePiece(player.worldObj, x1, y1, z1, x2, y2, z2, ox, oy, oz);
			NBTTagCompound struct_nbt = struct.saveToNBT();
			
			if (args.length > 2) {
				if (!loot_memory.containsKey(args[2])) {
					sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.save-to-disk.noloot", args[2]));
					return;
				}
				NBTTagCompound loot = loot_memory.get(args[2]);
				struct_nbt.setTag("loot", loot);
			}
			
			sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.save-to-disk.saving"));
			File file = new File(ResAdditae.dir_structures + File.separator + args[1] + ".nbt");
			try {
				CompressedStreamTools.writeCompressed(struct_nbt, new FileOutputStream(file));
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.save-to-disk.save_success"));
			} catch (IOException e) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.save-to-disk.save_failure"));
			}
			return;
		}
		
		if (args[0].equals("load-from-disk")) {
			if (args.length < 2) {
				throw new WrongUsageException("commands.rastruct.load-from-disk.usage");
			}
			
			ItemStack held = player.getHeldItem();
			if (held == null) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-from-disk.nowand"));
				return;
			}
			if (held.getItem() != ModItems.structure_wand || held.getItemDamage() != 1) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-from-disk.nowand"));
				return;
			}
			NBTTagCompound htag = held.getTagCompound();
			if (htag == null) {
				htag = new NBTTagCompound();
			}
			
			File file = new File(ResAdditae.dir_structures + File.separator + args[1] + ".nbt");
			if (!file.exists()) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-from-disk.filenotfound", args[1]));
				return;
			}
			
			sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-from-disk.loading"));
			try {
				NBTTagCompound tag = CompressedStreamTools.readCompressed(new FileInputStream(file));
				htag.setTag("structure", tag);
				htag.setString("structure_name", args[1]);
				held.setTagCompound(htag);
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-from-disk.load_success"));
			} catch (IOException e) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-from-disk.load_failure"));
			}
			return;
		}
		
		if (args[0].equals("loot")) {
			if (args.length < 2) {
				throw new WrongUsageException("commands.rastruct.loot.usage");
			}
			
			ItemStack held = player.getHeldItem();
			if (held == null) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.nowand"));
				return;
			}
			if (held.getItem() != ModItems.structure_wand || held.getItemDamage() != 2) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.nowand"));
				return;
			}
			NBTTagCompound htag = held.getTagCompound();
			if (htag == null) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.noselection"));
				return;
			}
			
			byte idx = htag.getByte("idx");
			if (!htag.hasKey("groups")) {
				htag.setTag("groups", new NBTTagCompound());
			}
			NBTTagCompound groups = htag.getCompoundTag("groups");
			if (!groups.hasKey("idx_" + idx)) {
				groups.setTag("idx_" + idx, (new LootGroup()).saveToNBT());
			}
			NBTTagCompound group = groups.getCompoundTag("idx_" + idx);
			groups.setTag("idx_" + idx, group);
			htag.setTag("groups", groups);
			
			int current_pool = htag.getInteger("current_pool");
			int current_item = htag.getInteger("current_item");
			
			if (args[1].equals("list-pools")) {
				NBTTagList pools = group.getTagList("pools", NBT.TAG_COMPOUND);
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.list-pools.count", pools.tagCount(), idx));
				
				for (int i = 0; i < pools.tagCount(); i++) {
					NBTTagCompound pool = pools.getCompoundTagAt(i);
					int rounds = pool.getInteger("rounds");
					sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.list-pools.pool", i, rounds));
				}
				
				return;
			}
			
			if (args[1].equals("add-pool")) {
				if (args.length < 3) {
					throw new WrongUsageException("commands.rastruct.loot.add-pool.usage");
				}
				
				int rounds = Integer.valueOf(args[2]);
				NBTTagCompound pool = (new LootPool(rounds)).saveToNBT();
				
				NBTTagList pools = group.getTagList("pools", NBT.TAG_COMPOUND);
				int i = pools.tagCount();
				pools.appendTag(pool);
				
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.add-pool.added", rounds, i));
				return;
			}
			
			if (args[1].equals("set-pool-rounds")) {
				if (args.length < 3) {
					throw new WrongUsageException("commands.rastruct.loot.set-pool-rounds.usage");
				}
				
				int rounds = Integer.valueOf(args[2]);
				NBTTagList pools = group.getTagList("pools", NBT.TAG_COMPOUND);
				NBTTagCompound pool = pools.getCompoundTagAt(current_pool);
				pool.setInteger("rounds", rounds);
				
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.set-pool-rounds.set", current_pool, rounds));
				return;
			}
			
			if (args[1].equals("delete-pool")) {
				if (args.length < 3) {
					throw new WrongUsageException("commands.rastruct.loot.delete-pool.usage");
				}
				
				int p = Integer.valueOf(args[2]);
				NBTTagList pools = group.getTagList("pools", NBT.TAG_COMPOUND);
				if (p >= pools.tagCount()) {
					sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.delete-pool.nopool", p));
					return;
				}
				
				pools.removeTag(p);
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.delete-pool.deleted", p));
				
				if (current_pool >= p) {
					sender.addChatMessage(I18nHlpr.chat("commands.rastruct.loot.delete-pool.idxalert"));
				}
				return;
			}
			
			if (args[1].equals("switch-pool")) {
				if (args.length < 3) {
					throw new WrongUsageException("commands.rastruct.loot.switch-pool.usage");
				}
				
				int p = Integer.valueOf(args[2]);
				htag.setInteger("current_pool", p);
				htag.setInteger("current_item", 0);
				
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.switch-pool.set", p));
				return;
			}
			
			if (args[1].equals("list-items")) {
				NBTTagList pools = group.getTagList("pools", NBT.TAG_COMPOUND);
				NBTTagCompound pool = pools.getCompoundTagAt(current_pool);
				NBTTagList items = pool.getTagList("items", NBT.TAG_COMPOUND);
				
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.list-items.count", items.tagCount(), current_pool));
				
				for (int i = 0; i < items.tagCount(); i++) {
					LootItem item = new LootItem(items.getCompoundTagAt(i));
					sender.addChatMessage(I18nHlpr.chatf(
						"commands.rastruct.loot.list-items.item",
						i,
						GameRegistry.findUniqueIdentifierFor(item.item.getItem()).toString(),
						item.item.getItemDamage(),
						item.weight,
						item.min_size,
						item.max_size,
						item.rand_exp,
						item.expires
					));
				}
				
				return;
			}
			
			if (args[1].equals("add-item")) {
				if (args.length < 9) {
					throw new WrongUsageException("commands.rastruct.loot.add-item.usage");
				}
				
				UniqueIdentifier uidr = new UniqueIdentifier(args[2]);
				Item item = GameRegistry.findItem(uidr.modId, uidr.name);
				int meta = Integer.parseInt(args[3]);
				ItemStack stack = new ItemStack(item, 1, meta);
				int weight = Integer.parseInt(args[4]);
				int min_size = Integer.parseInt(args[5]);
				int max_size = Integer.parseInt(args[6]);
				double rand_exp = Double.parseDouble(args[7]);
				boolean expires = Boolean.parseBoolean(args[8]);
				
				NBTTagList pools = group.getTagList("pools", NBT.TAG_COMPOUND);
				NBTTagCompound pool = pools.getCompoundTagAt(current_pool);
				NBTTagList items = pool.getTagList("items", NBT.TAG_COMPOUND);
				
				LootItem l = new LootItem(stack, weight, min_size, max_size, rand_exp, expires);
				int i = items.tagCount();
				items.appendTag(l.saveToNBT());
				
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.add-item.added", uidr.toString(), meta, i));
				return;
			}
			
			if (args[1].equals("set-item-enchant-lvl")) {
				if (args.length < 4) {
					throw new WrongUsageException("commands.rastruct.loot-set-item-enchant-lvl");
				}
				
				NBTTagList pools = group.getTagList("pools", NBT.TAG_COMPOUND);
				NBTTagCompound pool = pools.getCompoundTagAt(current_pool);
				NBTTagList items = pool.getTagList("items", NBT.TAG_COMPOUND);
				
				int i = Integer.parseInt(args[2]);
				if (i >= items.tagCount()) {
					sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.set-item-enchant-lvl.noitem", i));
					return;
				}
				int lvl = Integer.parseInt(args[3]);
				if (lvl > 127) {
					sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.set-item-enchant-lvl.abovelimit", lvl));
					return;
				}
				
				NBTTagCompound item = items.getCompoundTagAt(i);
				item.setByte("enchant_level", (byte) lvl);

				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.set-item-enchant-lvl.set", i, lvl));
				return;
			}
			
			if (args[1].equals("delete-item")) {
				if (args.length < 3) {
					throw new WrongUsageException("commands.rastruct.loot.delete-item.usage");
				}
				
				NBTTagList pools = group.getTagList("pools", NBT.TAG_COMPOUND);
				NBTTagCompound pool = pools.getCompoundTagAt(current_pool);
				NBTTagList items = pool.getTagList("items", NBT.TAG_COMPOUND);
				
				int i = Integer.parseInt(args[2]);
				if (i >= items.tagCount()) {
					sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.delete-item.noitem", i));
					return;
				}
				
				items.removeTag(i);
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.loot.delete-item.deleted", i));
				return;
			}
			
			throw new WrongUsageException("commands.rastruct.loot.usage");
		}
		
		if (args[0].equals("save-loot-to-disk")) {
			if (args.length < 2) {
				throw new WrongUsageException("commands.rastruct.loot.save-loot-to-disk.usage");
			}
			
			ItemStack held = player.getHeldItem();
			if (held == null) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.save-loot-to-disk.nowand"));
				return;
			}
			if (held.getItem() != ModItems.structure_wand || held.getItemDamage() != 2) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.save-loot-to-disk.nowand"));
				return;
			}
			
			NBTTagCompound htag = held.getTagCompound();
			NBTTagCompound loot_groups = htag.getCompoundTag("groups");
			
			sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.save-loot-to-disk.saving"));
			File file = new File(ResAdditae.dir_structure_loots + File.separator + args[1] + ".nbt");
			try {
				CompressedStreamTools.writeCompressed(loot_groups, new FileOutputStream(file));
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.save-loot-to-disk.save_success"));
			} catch (IOException e) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.save-loot-to-disk.save_failure"));
			}
			return;
		}
		
		if (args[0].equals("load-loot-from-disk")) {
			if (args.length < 2) {
				throw new WrongUsageException("commands.rastruct.load-loot-from-disk.usage");
			}
			
			ItemStack held = player.getHeldItem();
			if (held == null) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-loot-from-disk.nowand"));
				return;
			}
			if (held.getItem() != ModItems.structure_wand || held.getItemDamage() != 2) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-loot-from-disk.nowand"));
				return;
			}
			NBTTagCompound htag = held.getTagCompound();
			if (htag == null) {
				htag = new NBTTagCompound();
			}
			
			File file = new File(ResAdditae.dir_structure_loots + File.separator + args[1] + ".nbt");
			if (!file.exists()) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-loot-from-disk.filenotfound", args[1]));
				return;
			}
			
			sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-loot-from-disk.loading"));
			try {
				NBTTagCompound tag = CompressedStreamTools.readCompressed(new FileInputStream(file));
				htag.setTag("groups", tag);
				held.setTagCompound(htag);
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-from-disk.load_success"));
			} catch (IOException e) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-from-disk.load_failure"));
			}
			return;
		}
		
		if (args[0].equals("save-loot-to-memory")) {
			if (args.length < 2) {
				throw new WrongUsageException("commands.rastruct.save-loot-to-memory.usage");
			}
			
			ItemStack held = player.getHeldItem();
			if (held == null) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.save-loot-to-memory.nowand"));
				return;
			}
			if (held.getItem() != ModItems.structure_wand || held.getItemDamage() != 2) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.save-loot-to-memory.nowand"));
				return;
			}
			
			NBTTagCompound htag = held.getTagCompound();
			NBTTagCompound loot_groups = htag.getCompoundTag("groups");
			
			loot_memory.put(args[1], loot_groups);
			sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.save-loot-to-memory.saved", args[1]));
			return;
		}
		
		if (args[0].equals("load-loot-from-memory")) {
			if (args.length < 2) {
				throw new WrongUsageException("commands.rastruct.load-loot-from-memory.usage");
			}
			
			ItemStack held = player.getHeldItem();
			if (held == null) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-loot-from-memory.nowand"));
				return;
			}
			if (held.getItem() != ModItems.structure_wand || held.getItemDamage() != 2) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-loot-from-memory.nowand"));
				return;
			}
			NBTTagCompound htag = held.getTagCompound();
			if (htag == null) {
				htag = new NBTTagCompound();
			}
			
			if (!loot_memory.containsKey(args[1])) {
				sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-loot-from-memory.noloot", args[1]));
				return;
			}
			NBTTagCompound groups = loot_memory.get(args[1]);
			
			htag.setTag("groups", groups);
			sender.addChatMessage(I18nHlpr.chatf("commands.rastruct.load-loot-from-memory.loaded", args[1]));
			return;
		}
		
		throw new WrongUsageException(this.getCommandUsage(sender));
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		List<String> list = null;
		if (args.length == 1) {
			list = new ArrayList<String>();
			list.add("load-from-disk");
			list.add("save-to-disk");
			list.add("loot");
			list.add("load-loot-from-disk");
			list.add("save-loot-to-disk");
			list.add("load-loot-from-memory");
			list.add("save-loot-to-memory");
		} else if (args.length == 2) {
			if (args[0].equals("loot")) {
				list = new ArrayList<String>();
				list.add("add-pool");
				list.add("set-pool-rounds");
				list.add("delete-pool");
				list.add("switch-pool");
				list.add("list-items");
				list.add("add-item");
				list.add("set-item-enchant-lvl");
				list.add("delete-item");
			} else if (args[0].equals("load-loot-from-memory")) {
				list = new ArrayList<String>();
				for (String key : loot_memory.keySet()) {
					list.add(key);
				}
			}
		}
		return list;
	}
}
