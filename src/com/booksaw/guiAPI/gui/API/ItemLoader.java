/**
 * 
 */
package com.booksaw.guiAPI.gui.API;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

/**
 * @author booksaw
 *
 */
public class ItemLoader {

	private ItemLoader() {
		// class should not be instantiated
	}

	/**
	 * Loads a basic item stack and returns it. BasicItemStack storage is used to
	 * create a more readable / modifiable item so the file is readable. Less
	 * options are available as a result
	 * 
	 * @param section The ConfigurationSection that the item stack exists in
	 * @return The loaded item stack
	 */
	public static ItemStack loadBasicItemStack(ConfigurationSection section) {

		Material mat = Material.getMaterial(section.getString("type").toUpperCase());

		if (mat == null) {
			Bukkit.getLogger()
					.warning("Provided item type " + section.getString("type").toUpperCase() + " is not a valid item");

			mat = Material.STONE;
		}

		ItemStack is = new ItemStack(mat);

		ItemMeta meta = is.getItemMeta();

		String name = section.getString("name");

		if (name == null) {
			name = "";
		}

		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

		List<String> lore = section.getStringList("lore");

		List<String> loreC = new ArrayList<>();

		for (String str : lore) {
			if (str != null)
				loreC.add(ChatColor.translateAlternateColorCodes('&', str));
		}
		meta.setLore(loreC);

		is.setItemMeta(meta);
		return is;
	}

}
