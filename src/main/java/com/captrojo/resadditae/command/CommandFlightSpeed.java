package com.captrojo.resadditae.command;

import java.util.ArrayList;
import java.util.List;

import com.captrojo.resadditae.main.I18nHlpr;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.packet.toclient.PacketSetFlightSpeed;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommandFlightSpeed extends CommandBase
{
	@Override
	public int getRequiredPermissionLevel()
	{
		return 1;
	}
	
	@Override
	public String getCommandName()
	{
		return "flightspeed";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "commands.flightspeed.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (args.length < 1) {
			/* I hate that this is how Minecaft does this */
			throw new WrongUsageException(this.getCommandUsage(sender));
		}
		
		EntityPlayer player = (EntityPlayer) sender;
		float speed = 0.05f;
		try {
			speed = Float.parseFloat(args[0]);
		} catch (NumberFormatException e) {}
		ResAdditae.network.sendTo(new PacketSetFlightSpeed(speed), (EntityPlayerMP) sender);
		sender.addChatMessage(I18nHlpr.chatf("commands.flightspeed.set", speed));
		return;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		List<String> list = null;
		if (args.length == 1) {
			list = new ArrayList<String>();
			list.add("set");
			list.add("get");
		} else if (args.length == 2) {
			list = new ArrayList<String>();
			list.add("0.05");
		}
		return list;
	}
}
