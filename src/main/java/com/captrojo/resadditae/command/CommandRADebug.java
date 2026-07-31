package com.captrojo.resadditae.command;

import java.util.ArrayList;
import java.util.List;

import com.captrojo.resadditae.apocalypse.Apocalypse;
import com.captrojo.resadditae.entity.properties.RAPlayerProperties;
import com.captrojo.resadditae.magic.LearnedSpell;
import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.magic.spell.Spells;
import com.captrojo.resadditae.main.I18nHlpr;
import com.captrojo.resadditae.main.PerformanceInfo;
import com.captrojo.resadditae.tileentity.TEMossLayer;
import com.captrojo.resadditae.world.gen.feature.WorldGenChasm;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class CommandRADebug extends CommandBase
{
	@Override
	public int getRequiredPermissionLevel()
	{
		return 4;
	}
	
	@Override
	public String getCommandName()
	{
		return "radebug";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "commands.radebug.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (args.length < 1) {
			throw new WrongUsageException("commands.radebug.usage");
		}
		
		if (args[0].equals("echo")) {
			if (args.length < 2) {
				sender.addChatMessage(new ChatComponentText("Echo!"));
				return;
			}
			sender.addChatMessage(new ChatComponentText(args[1]));
			return;
		}
		
		if (args[0].equals("reset-rpp")) {
			EntityPlayerMP player = args.length > 1 ? getPlayer(sender, args[1]) : getCommandSenderAsPlayer(sender);
			RAPlayerProperties.get(player).reset();
			sender.addChatMessage(I18nHlpr.chatf("commands.radebug.reset-rpp.done", player.getCommandSenderName()));
			return;
		}
		
		if (args[0].equals("learn-all-spells")) {
			EntityPlayerMP player = args.length > 1 ? getPlayer(sender, args[1]) : getCommandSenderAsPlayer(sender);
			RAPlayerProperties rpp = RAPlayerProperties.get(player);
			rpp.learned_spells.clear();
			for (Spell spell : Spells.SPELL_LIST) {
				LearnedSpell ls = new LearnedSpell(spell);
				rpp.learned_spells.add(ls);
			}
			sender.addChatMessage(I18nHlpr.chatf("commands.radebug.learn-all-spells.done", player.getCommandSenderName()));
			return;
		}
		
		if (args[0].equals("get-block-data")) {
			if (args.length < 4) {
				throw new WrongUsageException("commands.radebug.get-block-data.usage");
			}
			
			int px = sender.getPlayerCoordinates().posX;
			int py = sender.getPlayerCoordinates().posY;
			int pz = sender.getPlayerCoordinates().posZ;
			/* func_110666_a gets a coordinate, handling a '~' */
			int x = MathHelper.floor_double(func_110666_a(sender, (double) px, args[1]));
			int y = MathHelper.floor_double(func_110666_a(sender, (double) py, args[2]));
			int z = MathHelper.floor_double(func_110666_a(sender, (double) pz, args[3]));
			
			World world = sender.getEntityWorld();
			Block block = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			sender.addChatMessage(new ChatComponentText(block.getUnlocalizedName() + " # " + meta));
			if (world.getTileEntity(x, y, z) instanceof TEMossLayer) {
				int[] layers = ((TEMossLayer) world.getTileEntity(x, y, z)).layer_counts;
				sender.addChatMessage(new ChatComponentText(
					layers[0] + ", " +
					layers[1] + ", " +
					layers[2] + ", " +
					layers[3] + ", " +
					layers[4] + ", " +
					layers[5]
				));
			}
			
			return;
		}
		
		if (args[0].equals("respawn-chasms")) {
			if (args.length < 2) {
				throw new WrongUsageException("commands.radebug.respawn-chasms.usage");
			}
			int cr = Integer.valueOf(args[1]);
			
			World world = sender.getEntityWorld();
			ChunkCoordinates coords = sender.getPlayerCoordinates();
			int cx0 = coords.posX >> 4;
			int cz0 = coords.posZ >> 4;
			
			for (int cx1 = -cr; cx1 <= cr; cx1++) {
				int cx = cx0 + cx1;
				for (int cz1 = -cr; cz1 <= cr; cz1++) {
					int cz = cz0 + cz1;
					if (!world.getChunkProvider().chunkExists(cx, cz)) {
						continue;
					}
					if (!WorldGenChasm.PLACEMENT_CHK.canPlaceAt(world, cx, cz)) {
						continue;
					}
					(new WorldGenChasm()).generateRespawned(world.rand, cx, cz);
				}
			}
			
			sender.addChatMessage(I18nHlpr.chat("commands.radebug.respawn-chasms.complete"));
			return;
		}
		
		if (args[0].equals("enable-performance-info")) {
			EntityPlayerMP player = args.length > 1 ? getPlayer(sender, args[1]) : getCommandSenderAsPlayer(sender);
			PerformanceInfo.addListener(player);
			return;
		}
		
		if (args[0].equals("disable-performance-info")) {
			EntityPlayerMP player = args.length > 1 ? getPlayer(sender, args[1]) : getCommandSenderAsPlayer(sender);
			PerformanceInfo.removeListener(player);
			return;
		}
		
		if (args[0].equals("reset-performance-info")) {
			PerformanceInfo.resetInfo();
			return;
		}
		
		if (args[0].equals("test0")) {
			sender.addChatMessage(I18nHlpr.chat("start"));
			
			World world = sender.getEntityWorld();
			ChunkCoordinates coords = sender.getPlayerCoordinates();
			int cx0 = coords.posX >> 4;
			int cz0 = coords.posZ >> 4;
			
			for (int cx1 = -4; cx1 < 4; cx1++) {
				int cx = cx0 + cx1;
				for (int cz1 = -4; cz1 < 4; cz1++) {
					int cz = cz0 + cz1;
					Chunk chunk = world.getChunkFromChunkCoords(cx, cz);
					Apocalypse.flashoverChunk(world, chunk, 64, 128);
				}
			}
			
			sender.addChatMessage(I18nHlpr.chat("end"));
			return;
		}
	}
	
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		List<String> list = null;
		if (args.length == 1) {
			list = new ArrayList<String>();
			list.add("echo");
			list.add("reset-rpp");
			list.add("learn-all-spells");
			list.add("get-block-data");
			list.add("respawn-chasms");
			list.add("enable-performance-info");
			list.add("disable-performance-info");
			list.add("reset-performance-info");
		}
		return list;
	}
}
