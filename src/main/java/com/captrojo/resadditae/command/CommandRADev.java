package com.captrojo.resadditae.command;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.captrojo.resadditae.extprop.DevData;
import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.item.devtool.ItemStructureWand;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.util.I18nHlpr;
import com.captrojo.resadditae.util.NBTHlpr;
import com.captrojo.resadditae.world.gen.structure.nbt.PaletteEntry;
import com.captrojo.resadditae.world.gen.structure.nbt.StructurePieceNBT;
import com.captrojo.resadditae.world.loot.LootGroup;
import com.captrojo.resadditae.world.loot.LootItem;
import com.captrojo.resadditae.world.loot.LootPool;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class CommandRADev extends CommandBase
{
	private static HashMap<String, NBTTagCompound> loot_memory = new HashMap<String, NBTTagCompound>();
	
	NBTTagCompound getHeldWandNBT(EntityPlayer player, int wand_id, String cmd_name)
	{
		ItemStack held = player.getHeldItem();
		if (held == null) {
			player.addChatMessage(I18nHlpr.chatf(String.format("commands.radev.%s.nowand", cmd_name)));
			return null;
		}
		if (held.getItem() != ModItems.structure_wand || held.getItemDamage() != wand_id) {
			player.addChatMessage(I18nHlpr.chatf(String.format("commands.radev.%s.nowand", cmd_name)));
			return null;
		}
		NBTTagCompound nbt = held.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			held.setTagCompound(nbt);
		}
		return nbt;
	}
	
	boolean saveStructureToDisk(EntityPlayer player, StructurePieceNBT structure, String name, String[] loots)
	{
		NBTTagCompound struct_nbt = structure.saveToNBT(new NBTTagCompound());
		
		if (loots != null) {
			Map<String, LootGroup> groups = RAPlayerProperties.get(player).getDevData().loot_groups;
			if (groups == null || groups.size() == 0) {
				player.addChatMessage(I18nHlpr.chat("commands.radev.struct.save.noloot"));
				return false;
			}
			
			NBTTagList loot_list = new NBTTagList();
			for (String loot_name : loots) {
				LootGroup lg = groups.get(loot_name);
				if (lg == null) {
					player.addChatMessage(I18nHlpr.chatf("commands.radev.struct.save.unknownloot", loot_name));
					return false;
				}
				loot_list.appendTag(lg.saveToNBT(new NBTTagCompound()));
			}
			
			struct_nbt.setTag("Loot", loot_list);
		}
		
		NBTHlpr.saveToDisk(ResAdditae.dir_structures + File.separator + name + ".nbt", struct_nbt);
		return true;
	}
	
	void cmdStruct(EntityPlayer player, DevData dev_data, String[] args)
	{
		if (args.length <= 1) {
			player.addChatMessage(I18nHlpr.chat("commands.radev.struct.usage"));
			return;
		}
		
		if (args[1].equals("save")) {
			NBTTagCompound hnbt = this.getHeldWandNBT(player, 0, "struct.save");
			if (hnbt == null) {
				return;
			}
			
			PaletteEntry foundation = null;
			
			String name = null;
			String[] loots = null;
			for (int i = 2; i < args.length; i++) {
				if (args[i].equals("-loot")) {
					i++;
					if (i >= args.length) {
						player.addChatMessage(I18nHlpr.chat("commands.radev.struct.loot.incomplete"));
						return;
					}
					loots = args[i].split(",");
					continue;
				} else if (args[i].equals("-foundation")) {
					i++;
					i++;
					if (i >= args.length) {
						player.addChatMessage(I18nHlpr.chat("commands.radev.struct.foundation.incomplete"));
						return;
					}
					Block block = getBlockByText(player, args[i - 1]);
					int meta;
					try {
						meta = Integer.parseInt(args[i]);
					} catch (NumberFormatException e) {
						player.addChatMessage(I18nHlpr.chatf("commands.radev.invalidnum", args[i]));
						return;
					}
					foundation = new PaletteEntry(block, meta);
					continue;
				}
				name = args[i];
			}
			if (name == null) {
				player.addChatMessage(I18nHlpr.chat("commands.radev.struct.noname"));
				return;
			}
			
			int x1a = hnbt.getInteger("x1");
			int y1a = hnbt.getInteger("y1");
			int z1a = hnbt.getInteger("z1");
			int x2a = hnbt.getInteger("x2");
			int y2a = hnbt.getInteger("y2");
			int z2a = hnbt.getInteger("z2");
			int x1 = (x1a < x2a) ? x1a : x2a;
			int y1 = (y1a < y2a) ? y1a : y2a;
			int z1 = (z1a < z2a) ? z1a : z2a;
			int x2 = (x1a >= x2a) ? x1a : x2a;
			int y2 = (y1a >= y2a) ? y1a : y2a;
			int z2 = (z1a >= z2a) ? z1a : z2a;
			int ox = hnbt.getInteger("xO");
			int oy = hnbt.getInteger("yO");
			int oz = hnbt.getInteger("zO");
			
			player.addChatMessage(I18nHlpr.chat("commands.radev.struct.save.begin"));
			
			StructurePieceNBT struct = new StructurePieceNBT(name, player.worldObj, x1, y1, z1, x2, y2, z2, ox, oy, oz);
			if (foundation != null) {
				struct.foundation = foundation;
			}
			
			if (this.saveStructureToDisk(player, struct, name, loots)) {
				player.addChatMessage(I18nHlpr.chatf("commands.radev.struct.save.success", name));
				return;
			}
			player.addChatMessage(I18nHlpr.chatf("commands.radev.struct.save.failure", name));
			return;
		} else if (args[1].equals("load")) {
			if (args.length <= 2) {
				player.addChatMessage(I18nHlpr.chat("commands.radev.struct.noname"));
				return;
			}
			String name = args[2];
			
			NBTTagCompound hnbt = this.getHeldWandNBT(player, 1, "struct.load");
			if (hnbt == null) {
				return;
			}
			
			NBTTagCompound struct_nbt = NBTHlpr.loadFromDisk(ResAdditae.dir_structures + File.separator + name + ".nbt");
			if (struct_nbt == null) {
				player.addChatMessage(I18nHlpr.chatf("commands.radev.struct.load.failure", name));
				return;
			}
			
			hnbt.setTag("Structure", struct_nbt);
			hnbt.setString("StructureName", name);
			player.addChatMessage(I18nHlpr.chatf("commands.radev.struct.load.success", name));
			return;
		}
		
		player.addChatMessage(I18nHlpr.chat("commands.radev.struct.usage"));
	}
	
	LootGroup getSelectedGroup(EntityPlayer player, DevData dev_data)
	{
		if (dev_data.selected_group == null) {
			player.addChatMessage(I18nHlpr.chat("commands.radev.loot.nogroupsel"));
			return null;
		}
		return dev_data.loot_groups.get(dev_data.selected_group);
	}
	
	LootPool getSelectedPool(EntityPlayer player, DevData dev_data)
	{
		LootGroup lg = this.getSelectedGroup(player, dev_data);
		if (lg == null) {
			return null;
		}
		if (dev_data.selected_pool == -1) {
			player.addChatMessage(I18nHlpr.chat("commands.radev.loot.nopoolsel"));
			return null;
		}
		return lg.pools.get(dev_data.selected_pool);
	}
	
	void cmdLoot(EntityPlayer player, DevData dev_data, String[] args)
	{
		if (args.length <= 1) {
			player.addChatMessage(I18nHlpr.chat("commands.radev.loot.usage"));
			return;
		}
		
		if (args[1].equals("create-group")) {
			if (args.length <= 2) {
				player.addChatMessage(I18nHlpr.chat("commands.radev.loot.create-group.usage"));
				return;
			}
			dev_data.loot_groups.put(args[2], new LootGroup());
			player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.create-group.success", args[2]));
			return;
		} else if (args[1].equals("select-group")) {
			if (args.length <= 2) {
				player.addChatMessage(I18nHlpr.chat("commands.radev.loot.select-group.usage"));
				return;
			}
			if (!dev_data.loot_groups.containsKey(args[2])) {
				player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.nogroup", args[2]));
				return;
			}
			dev_data.selected_group = args[2];
			dev_data.selected_pool = -1;
			player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.select-group.success", args[2]));
			return;
		} else if (args[1].equals("delete-group")) {
			if (args.length <= 2) {
				player.addChatMessage(I18nHlpr.chat("commands.radev.loot.delete-group.usage"));
				return;
			}
			if (!dev_data.loot_groups.containsKey(args[2])) {
				player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.nogroup", args[2]));
				return;
			}
			dev_data.loot_groups.remove(args[2]);
			if (dev_data.selected_group.equals(args[2])) {
				dev_data.selected_group = null;
			}
			player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.select-group.success", args[2]));
			return;
		} else if (args[1].equals("save-group")) {
			if (args.length <= 2) {
				player.addChatMessage(I18nHlpr.chat("commands.radev.loot.save-group.usage"));
				return;
			}
			
			LootGroup lg = dev_data.loot_groups.get(args[2]);
			if (lg == null) {
				player.addChatMessage(I18nHlpr.chat("commands.radev.loot.nogroup"));
				return;
			}
			NBTTagCompound nbt = lg.saveToNBT(new NBTTagCompound());
			
			String name = (args.length > 3) ? args[3] : args[2];
			String path = ResAdditae.dir_loots + File.separator + name + ".nbt";
			
			player.addChatMessage(I18nHlpr.chat("commands.radev.loot.save-group.begin"));
			if (!NBTHlpr.saveToDisk(path, nbt)) {
				player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.save-group.failure", name));
				return;
			}
			player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.save-group.success", name));
			return;
		} else if (args[1].equals("load-group")) {
			if (args.length <= 2) {
				player.addChatMessage(I18nHlpr.chat("commands.radev.loot.load-group.usage"));
				return;
			}
			
			String path = ResAdditae.dir_loots + File.separator + args[2] + ".nbt";
			NBTTagCompound nbt = NBTHlpr.loadFromDisk(path);
			if (nbt == null) {
				player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.load-group.failure", args[2]));
				return;
			}
			
			LootGroup lg = (new LootGroup()).loadFromNBT(nbt);
			String name = (args.length > 3) ? args[3] : args[2];
			dev_data.loot_groups.put(name, lg);
			player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.load-group.success", name));
			return;
		} else if (args[1].equals("create-pool")) {
			if (args.length <= 2) {
				player.addChatMessage(I18nHlpr.chat("commands.radev.loot.create-pool.usage"));
				return;
			}
			LootGroup lg = this.getSelectedGroup(player, dev_data);
			if (lg == null) {
				return;
			}
			
			int rounds;
			try {
				rounds = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				player.addChatMessage(I18nHlpr.chatf("commands.radev.invalidnum", args[2]));
				return;
			}
			
			int idx = lg.pools.size();
			lg.pools.add(new LootPool(rounds));
			player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.create-pool.success", rounds, idx));
			if (args[args.length - 1].equals("select")) {
				this.cmdLoot(player, dev_data, new String[] {"loot", "select-pool", Integer.toString(idx)});
			}
			return;
		} else if (args[1].equals("select-pool")) {
			if (args.length <= 2) {
				player.addChatMessage(I18nHlpr.chat("commands.radev.loot.create-pool.usage"));
				return;
			}
			LootGroup lg = this.getSelectedGroup(player, dev_data);
			if (lg == null) {
				return;
			}
			
			int idx;
			try {
				idx = Integer.valueOf(args[2]);
			} catch (NumberFormatException e) {
				player.addChatMessage(I18nHlpr.chatf("commands.radev.invalidnum", args[2]));
				return;
			}
			
			if (idx >= lg.pools.size()) {
				player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.abovemaxpool", idx));
				return;
			}
			dev_data.selected_pool = idx;
			player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.select-pool.success", idx));
			return;
		} else if (args[1].equals("delete-pool")) {
			if (args.length <= 2) {
				player.addChatMessage(I18nHlpr.chat("commands.radev.loot.delete-pool.usage"));
				return;
			}
			LootGroup lg = this.getSelectedGroup(player, dev_data);
			if (lg == null) {
				return;
			}
			
			int idx;
			try {
				idx = Integer.valueOf(args[2]);
			} catch (NumberFormatException e) {
				player.addChatMessage(I18nHlpr.chatf("commands.radev.invalidnum", args[2]));
				return;
			}

			if (idx >= lg.pools.size()) {
				player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.abovemaxpool", idx));
				return;
			}
			if (idx <= dev_data.selected_pool) {
				this.cmdLoot(
					player, dev_data,
					new String[] {"loot", "select-pool", Integer.toString(dev_data.selected_pool - 1)}
				);
			}
			lg.pools.remove(idx);
			player.addChatMessage(I18nHlpr.chat("commands.radev.loot.delete-pool.success"));
			return;
		} else if (args[1].equals("add-item")) {
			if (args.length <= 6) {
				player.addChatMessage(I18nHlpr.chat("commands.radev.loot.add-item.usage"));
				return;
			}
			/* args[2] = id
			 * args[3] = meta
			 * args[4] = weight
			 * args[5] = min size
			 * args[6] = max size
			 * args[7+] = optional stuff
			 */
			
			LootPool lp = this.getSelectedPool(player, dev_data);
			if (lp == null) {
				return;
			}
			
			Item item = getItemByText(player, args[2]);
			if (item == null) {
				player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.add-item.unknownitem", args[2]));
				return;
			}
			
			int meta, weight, min_size, max_size;
			int a = 3;
			try {
				meta = Integer.valueOf(args[3]);
				a++;
				weight = Integer.valueOf(args[4]);
				a++;
				min_size = Integer.valueOf(args[5]);
				a++;
				max_size = Integer.valueOf(args[6]);
			} catch (NumberFormatException e) {
				player.addChatMessage(I18nHlpr.chatf("commands.radev.invalidnum", args[a]));
				return;
			}
			
			ItemStack stack = new ItemStack(item, 1, meta);
			LootItem li = new LootItem(stack, weight, min_size, max_size);
			
			for (int i = 7; i < args.length; i++) {
				if (args[i].equals("-expn")) {
					i++;
					if (i == args.length) {
						player.addChatMessage(I18nHlpr.chat("commands.radev.loot.expn.incomplete"));
						return;
					}
					try {
						li.rand_exp = Double.parseDouble(args[i]);
					} catch (NumberFormatException e) {
						player.addChatMessage(I18nHlpr.chatf("commands.radev.invalidnum", args[i]));
						return;
					}
					continue;
				} else if (args[i].equals("-expire")) {
					li.expires = true;
					continue;
				} else if (args[i].equals("-ench")) {
					i++;
					if (i == args.length) {
						player.addChatMessage(I18nHlpr.chat("commands.radev.loot.ench.incomplete"));
						return;
					}
					try {
						li.is_enchanted = true;
						li.enchant_level = Byte.parseByte(args[i]);
					} catch (NumberFormatException e) {
						player.addChatMessage(I18nHlpr.chatf("commands.radev.invalidnum", args[i]));
						return;
					}
					continue;
				}
			}
			
			lp.items.add(li);
			player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.add-item.success", args[2], args[3]));
			return;
		} else if (args[1].equals("delete-item")) {
			LootPool lp = this.getSelectedPool(player, dev_data);
			if (lp == null) {
				return;
			}
			
			int idx;
			try {
				idx = Integer.valueOf(args[2]);
			} catch (NumberFormatException e) {
				player.addChatMessage(I18nHlpr.chatf("commands.radev.invalidnum", args[2]));
				return;
			}
			
			if (idx >= lp.items.size()) {
				player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.abovemaxitem", idx));
				return;
			}
			
			lp.items.remove(idx);
			player.addChatMessage(I18nHlpr.chat("commands.radev.loot.delete-item.success"));
			return;
		} else if (args[1].equals("list-items")) {
			LootPool lp = this.getSelectedPool(player, dev_data);
			if (lp == null) {
				return;
			}
			
			int size = lp.items.size();
			player.addChatMessage(I18nHlpr.chatf("commands.radev.loot.list-items.count", size, dev_data.selected_pool));
			
			for (int i = 0; i < size; i++) {
				LootItem li = lp.items.get(i);
				
				String key = "commands.radev.loot.list-items.item";
				Object[] av = {
					i,
					GameRegistry.findUniqueIdentifierFor(li.item.getItem()).toString(),
					li.item.getItemDamage(),
					li.weight,
					li.min_size,
					li.max_size,
					null,
					null,
					null
				};
				int a = 6;
				
				if (li.rand_exp != 1.0) {
					key += "_expn";
					av[a] = li.rand_exp;
					a++;
				}
				if (li.expires) {
					key += "_expr";
					av[a] = Boolean.toString(li.expires);
					a++;
				}
				if (li.is_enchanted) {
					key += "_ench";
					av[a] = li.enchant_level;
					a++;
				}
				
				player.addChatMessage(I18nHlpr.chatf(key, av));
			}
			return;
		}
		
		player.addChatMessage(I18nHlpr.chat("commands.radev.loot.usage"));
	}

	@Override
	public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	@Override
	public String getCommandName()
	{
		return "radev";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "commands.radev.usage";
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		ResAdditae.LOG.info("CommandRADev:");
		for (String s : args) {
			ResAdditae.LOG.info("> " + s);
		}
		
		if (args.length < 1) {
			sender.addChatMessage(I18nHlpr.chat("commands.radev.usage"));
			return;
		}
		if (!(sender instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer) sender;
		DevData dev_data = RAPlayerProperties.get(player).getDevData();
		
		if (args[0].equals("struct")) {
			this.cmdStruct(player, dev_data, args);
			return;
		} else if (args[0].equals("loot")) {
			this.cmdLoot(player, dev_data, args);
			return;
		} else if (args[0].equals("toggle-fill-wand-behavior")) {
			ItemStructureWand.fill_wand_void_behavior = !ItemStructureWand.fill_wand_void_behavior;
			if (ItemStructureWand.fill_wand_void_behavior) {
				player.addChatMessage(I18nHlpr.chat("commands.radev.toggle-fill-wand-behavior.true"));
			} else {
				player.addChatMessage(I18nHlpr.chat("commands.radev.toggle-fill-wand-behavior.false"));
			}
			return;
		}

		sender.addChatMessage(I18nHlpr.chat("commands.radev.usage"));
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		if (!(sender instanceof EntityPlayer)) {
			return null;
		}
		EntityPlayer player = (EntityPlayer) sender;
		DevData dev_data = RAPlayerProperties.get(player).getDevData();
		
		if (args.length == 0) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		
		if (args.length == 1) {
			list.add("struct");
			list.add("loot");
			list.add("toggle-fill-wand-behavior");
			return getListOfStringsFromIterableMatchingLastWord(args, list);
		}
		
		if (args[0].equals("struct")) {
			if (args.length == 2) {
				list.add("save");
				list.add("load");
				return getListOfStringsFromIterableMatchingLastWord(args, list);
			}
			
			if (args[1].equals("save")) {
				if (args[args.length - 2].equals("-loot")) {
					return getListOfStringsFromIterableMatchingLastWord(args, dev_data.loot_groups.keySet());
				}
				if (args[args.length - 2].equals("-foundation")) {
					return getListOfStringsFromIterableMatchingLastWord(args, Block.blockRegistry.getKeys());
				}
				
				boolean has_loot = false, has_foundation = false;
				for (String a : args) {
					if (a.equals("-loot")) {
						has_loot = true;
					} else if (a.equals("-foundation")) {
						has_foundation = true;
					}
				}
				if (!has_loot) {
					list.add("-loot");
				}
				if (!has_foundation) {
					list.add("-foundation");
				}
				return getListOfStringsFromIterableMatchingLastWord(args, list);
			}
		} else if (args[0].equals("loot")) {
			if (args.length == 2) {
				list.add("create-group");
				list.add("select-group");
				list.add("delete-group");
				list.add("save-group");
				list.add("load-group");
				list.add("create-pool");
				list.add("select-pool");
				list.add("delete-pool");
				list.add("add-item");
				list.add("delete-item");
				list.add("list-items");
				return getListOfStringsFromIterableMatchingLastWord(args, list);
			}
			
			if (args[1].equals("select-group") || args[1].equals("delete-group") || args[1].equals("save-group")) {
				if (args.length > 3) {
					return null;
				}
				for (String s : dev_data.loot_groups.keySet()) {
					list.add(s);
				}
				return getListOfStringsFromIterableMatchingLastWord(args, list);
			} else if (args[1].equals("add-item")) {
				if (args.length == 3) {
					return getListOfStringsFromIterableMatchingLastWord(args, Item.itemRegistry.getKeys());
				} else if (args.length >= 8)  {
					boolean expn = false, expire = false, ench = false;
					for (String s : args) {
						if (s.equals("-expn")) {
							expn = true;
						} else if (s.equals("-expire")) {
							expire = true;
						} else if (s.equals("-ench")) {
							ench = true;
						}
					}
					if (!expn) {
						list.add("-expn");
					}
					if (!expire) {
						list.add("-expire");
					}
					if (!ench) {
						list.add("-ench");
					}
					return getListOfStringsFromIterableMatchingLastWord(args, list);
				}
			}
		}
		
		return null;
	}
}
