package com.booksaw.guiAPI.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.booksaw.guiAPI.APIMain;
import com.booksaw.guiAPI.API.DefaultItem;

import net.md_5.bungee.api.ChatColor;

public class GuiApiSave implements Sub {

	@Override
	public void command(CommandSender sender, String[] args, String label) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to do that!");
			return;
		}

		if (args.length < 2) {
			sender.sendMessage(ChatColor.GRAY + "/" + label + " save <name> -- see below for possible names");
			sender.sendMessage(ChatColor.BLUE + "blank " + ChatColor.GOLD + "- default blank item");
			sender.sendMessage(ChatColor.BLUE + "forward " + ChatColor.GOLD + "- default forward item");
			sender.sendMessage(ChatColor.BLUE + "back " + ChatColor.GOLD + "- default back item");
			return;
		}

		DefaultItem type;

		switch (args[1].toUpperCase()) {
		case "BLANK":
			type = DefaultItem.BLANK;
			break;
		case "BACK":
			type = DefaultItem.BACK;
			break;
		case "FORWARD":
			type = DefaultItem.FORWARD;
			break;
		default:
			sender.sendMessage(ChatColor.GRAY + "/" + label + " save <name> -- see below for possible names");
			sender.sendMessage(ChatColor.BLUE + "blank " + ChatColor.GOLD + "- default blank item");
			sender.sendMessage(ChatColor.BLUE + "forward " + ChatColor.GOLD + "- default forward item");
			sender.sendMessage(ChatColor.BLUE + "back " + ChatColor.GOLD + "- default back item");
			return;
		}
		Player p = (Player) sender;
		ItemStack is = p.getEquipment().getItemInMainHand();

		if (is == null || is.getType() == Material.AIR) {
			sender.sendMessage(ChatColor.RED + "You must be holding an item to do that");
			return;
		}

		APIMain.mainGuiAPI.getConfig().set("items." + type, is);
		APIMain.mainGuiAPI.saveConfig();
		p.sendMessage(ChatColor.GOLD + "The item has been updated");

	}

}
