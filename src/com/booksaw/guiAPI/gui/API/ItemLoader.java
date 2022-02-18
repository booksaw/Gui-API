/**
 * 
 */
package com.booksaw.guiAPI.gui.API;

import java.util.ArrayList;
import java.util.List;

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
		ItemStack is = new ItemStack(Material.getMaterial(section.getString("type")));

		ItemMeta meta = is.getItemMeta();

		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', section.getString("displayName")));

		List<String> lore = section.getStringList("lore");

		List<String> loreC = new ArrayList<>();

		for (String str : lore) {
			loreC.add(ChatColor.translateAlternateColorCodes('&', str));
		}
		meta.setLore(loreC);

		is.setItemMeta(meta);
		return is;
	}

}
