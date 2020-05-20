package com.booksaw.guiAPI.API.items;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

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
	 * <p>
	 * There are methods within a file configuration to do this
	 * (FileConfiguration.set) Use that instead
	 * </p>
	 * 
	 * 
	 * @param is     - The item that should be saved
	 * @param config - The config that it should be saved to
	 * @param ref    - the location that it should be saved at (ie
	 *               "items.<itemName>")
	 */
	@Deprecated
	public static void save(ItemStack is, FileConfiguration config, String ref) {
		config.set(ref, is);
	}

	/**
	 * Used to retrieve saved items which were saved with save()
	 * <p>
	 * There are methods within a file configuration to do this
	 * (FileConfiguration.getItemStack) Use that instead
	 * </p>
	 * 
	 * @param f   - The config they were saved in
	 * @param ref - The location that they were saved in (must be the same as it was
	 *            in save())
	 * @return The Itemstack that was saved
	 */
	@Deprecated
	public static ItemStack load(FileConfiguration f, String ref) {
		return f.getItemStack(ref);
	}
}
