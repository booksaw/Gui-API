package com.booksaw.guiAPI.commands;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class GuiAPIHelp implements Sub {

	@Override
	public void command(CommandSender sender, String[] args, String label) {
		sender.sendMessage(ChatColor.AQUA + "/" + label + " save <item> " + ChatColor.GOLD
				+ "- saves the item you are holding examples of <item> are:");
		sender.sendMessage(ChatColor.BLUE + "blank " + ChatColor.GOLD + "- default blank item");
		sender.sendMessage(ChatColor.BLUE + "foward " + ChatColor.GOLD + "- default foward item");
		sender.sendMessage(ChatColor.BLUE + "back " + ChatColor.GOLD + "- default back item");

	}

}
