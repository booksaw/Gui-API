package com.booksaw.guiAPI;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class GuiUseful {
	/**
	 * used to get the smallest size of an inventory
	 * 
	 * @param count - this is how many Inventory slots will be filled (this assumes
	 *              that everything is one after the next)
	 * @return returns the size of inventory required, will return -1 if you require
	 *         more slots than a double chest
	 */
	public static int getSize(int count) {

		int size = 9;
		while (true) {

			if (size > 54) {
				return -1;
			}

			if (count <= size) {
				return size;
			}

			size += 9;
		}
	}

	public static ItemStack getHead(OfflinePlayer player) {

		ItemStack is = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) is.getItemMeta();
		meta.setOwningPlayer(player);
		is.setItemMeta(meta);
		return is;

	}
}
