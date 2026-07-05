package com.captrojo.resadditae.command;

import java.util.ArrayList;
import java.util.List;

import com.captrojo.resadditae.block.special.BlockDepthsPortal;
import com.captrojo.resadditae.main.I18nHlpr;
import com.captrojo.resadditae.world.gen.WorldGenChasm;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

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
	}
	
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		List<String> list = null;
		if (args.length == 1) {
			list = new ArrayList<String>();
			list.add("respawn-chasms");
		}
		return list;
	}
}
