package com.captrojo.resadditae.command;

import java.util.List;

import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CommandFill extends CommandBase
{
	@Override
	public int getRequiredPermissionLevel()
	{
		return 2;
	}

	@Override
	public String getCommandName()
	{
		return "fill";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "commands.fill.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (args.length < 7) {
			/* I hate that this is how Minecaft does this */
			throw new WrongUsageException(this.getCommandUsage(sender));
		}

		World world = sender.getEntityWorld();

		int px = sender.getPlayerCoordinates().posX;
		int py = sender.getPlayerCoordinates().posY;
		int pz = sender.getPlayerCoordinates().posZ;
		/* func_110666_a gets a coordinate, handling a '~' */
		int x1 = MathHelper.floor_double(func_110666_a(sender, (double) px, args[0]));
		int y1 = MathHelper.floor_double(func_110666_a(sender, (double) py, args[1]));
		int z1 = MathHelper.floor_double(func_110666_a(sender, (double) pz, args[2]));
		int x2 = MathHelper.floor_double(func_110666_a(sender, (double) px, args[3]));
		int y2 = MathHelper.floor_double(func_110666_a(sender, (double) py, args[4]));
		int z2 = MathHelper.floor_double(func_110666_a(sender, (double) pz, args[5]));
		Block block = getBlockByText(sender, args[6]);
		int arg_pos = 7;

		int meta = 0;
		if (args.length > arg_pos) {
			try {
				meta = Integer.parseInt(args[arg_pos]);
				arg_pos++;
			} catch (NumberFormatException e) {
			}
		}

		Block replaced_block = null;
		int replaced_meta = -1;
		if (args.length > arg_pos) {
			if (args[arg_pos].equals("replace")) {
				arg_pos++;
				if (args.length > arg_pos) {
					replaced_block = getBlockByText(sender, args[arg_pos]);
					arg_pos++;
				}
				if (args.length > arg_pos) {
					try {
						replaced_meta = Integer.parseInt(args[arg_pos]);
						arg_pos++;
					} catch (NumberFormatException e) {
					}
				}
			}
		}

		boolean cause_updates = false;
		if (args.length > arg_pos) {
			if (args[arg_pos].equals("update")) {
				cause_updates = true;
				arg_pos++;
			}
		}

		int ax = (x1 <= x2) ? 1 : -1;
		int ay = (y1 <= y2) ? 1 : -1;
		int az = (z1 <= z2) ? 1 : -1;
		if (x1 <= x2) {
			x2++;
		} else {
			x2--;
		}
		if (y1 <= y2) {
			y2++;
		} else {
			y2--;
		}
		if (z1 <= z2) {
			z2++;
		} else {
			z2--;
		}

		sender.addChatMessage(new ChatComponentText(String.format("Filling area from (%d, %d, %d) to (%d, %d, %d)",
			x1, y1, z1, x2, y2, z2)));

		int blocks_affected = 0;
		for (int x = x1; x != x2; x += ax) {
			for (int y = y1; y != y2; y += ay) {
				for (int z = z1; z != z2; z += az) {
					if (replaced_block != null) {
						Block existing_block = world.getBlock(x, y, z);
						if (existing_block != replaced_block) {
							continue;
						}
					}
					if (replaced_meta != -1) {
						int existing_meta = world.getBlockMetadata(x, y, z);
						if (existing_meta != replaced_meta) {
							continue;
						}
					}
					world.setBlock(x, y, z, block, meta, cause_updates ? 3 : 2);
					blocks_affected++;
				}
			}
		}

		sender.addChatMessage(new ChatComponentText(String.format("Modified %d blocks", blocks_affected)));
	}
}
