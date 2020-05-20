package com.booksaw.guiAPI.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

/**
 * This class controls the command /guiapi name <name>
 * 
 * @author booksaw
 *
 */
public class GuiApiName implements Sub {

	@Override
	public void command(CommandSender sender, String[] args, String label) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to do that!");
			return;
		}

		Player p = (Player) sender;
		ItemStack is = p.getEquipment().getItemInMainHand();

		if (is == null) {
			sender.sendMessage(ChatColor.RED + "You must be holding an item to do that");
		}

		String name = "";
		if (args.length > 1) {
			for (int i = 1; i < args.length; i++) {
				name = name + args[i] + " ";
			}
			name = ChatColor.translateAlternateColorCodes('&', name.substring(0, name.length() - 1));
		} else {
			name = " ";
		}

		// removing the final space

		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(name);
		is.setItemMeta(meta);
		sender.sendMessage(ChatColor.GOLD + "That item has been renamed");
	}

}
