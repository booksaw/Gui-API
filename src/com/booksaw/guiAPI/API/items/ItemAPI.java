package com.booksaw.guiAPI.API.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class ItemAPI {

	// SYNTAX:
	// base:
	// item: material:duribility:amount
	// name: hi
	// lore: []
	// enchantments:
	// - enchantment:level
	// itemFlags: []

	/**
	 * Used to save items to your config (must use the included load() function to
	 * retrieve the data
	 * 
	 * THIS DOES NOT SAVE THE CONFIG FOR YOU, SO YOU MUST STILL DO THAT WITH
	 * JavaPlugin.saveConfig()
	 * 
	 * @param is     - The item that should be saved
	 * @param config - The config that it should be saved to
	 * @param ref    - the location that it should be saved at (ie
	 *               "items.<itemName>")
	 */
	public static void save(ItemStack is, FileConfiguration config, String ref) {

		// baisc item details
		ItemMeta im = is.getItemMeta();
		config.set(ref + ".item", is.getType() + ":" + is.getDurability() + ":" + is.getAmount());

		// basic item meta
		config.set(ref + ".name", im.getDisplayName());
		config.set(ref + ".lore", im.getLore());

		// enchantments
		List<String> ench = new ArrayList<>();

		for (Entry<Enchantment, Integer> e : is.getEnchantments().entrySet()) {
			ench.add(e.getKey().getName() + ":" + e.getValue());
		}

		// item flags
		config.set(ref + ".itemFlags", im.getItemFlags());
	}

	/**
	 * Used to retrieve saved items which were saved with save()
	 * 
	 * @param f   - The config they were saved in
	 * @param ref - The location that they were saved in (must be the same as it was
	 *            in save())
	 * @return The Itemstack that was saved
	 */
	public static ItemStack load(FileConfiguration f, String ref) {
		String item = f.getString(ref + ".item");
		String s[] = item.split(":");

		// creating the item
		ItemStack is = new ItemStack(Material.getMaterial(s[0]), Integer.parseInt(s[2]), Short.parseShort(s[1]));
		ItemMeta im = is.getItemMeta();

		// setting basic meta data
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', f.getString(ref + ".name")));
		im.setLore(f.getStringList(ref + ".lore"));
		is.setItemMeta(im);
		// enchantments
		for (String tmp : f.getStringList(ref + ".enchantments")) {
			String ts[] = tmp.split(":");
			is.addEnchantment(Enchantment.getByName(ts[0]), Integer.parseInt(ts[1]));
		}

		// item flags
		for (String tmp : f.getStringList(ref + ".itemFlags")) {
			im.addItemFlags(ItemFlag.valueOf(tmp));
		}

		return is;
	}
}
