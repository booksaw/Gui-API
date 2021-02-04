package com.booksaw.guiAPI.API.items;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

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
	
	/**
	 * Used to get a skull of the provided player
	 * 
	 * @param player The player to get the skull for
	 * @return That players skull
	 */
	public static ItemStack getSkull(Player player) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		// OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(head_uuid));
		sm.setOwningPlayer(player);
		skull.setItemMeta(sm);
		return skull;
	}

	/**
	 * Used to get a skull from a mojang texture reference
	 * 
	 * @param url The texture reference
	 * @return That players skull (never returns null, in the case of failure will
	 *         return alex head)
	 */
	public static ItemStack getSkull(String url) {

		ItemStack head = new ItemStack(Material.PLAYER_HEAD);
		if (url.isEmpty())
			return head;

		SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);

		profile.getProperties().put("textures", new Property("textures", url));

		try {
			Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
			mtd.setAccessible(true);
			mtd.invoke(skullMeta, profile);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
			ex.printStackTrace();
		}

		head.setItemMeta(skullMeta);
		return head;
	}
}
