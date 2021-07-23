package com.booksaw.guiAPI.API.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

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
//	public static ItemStack getSkull(String url) {
//
//		ItemStack head = new ItemStack(Material.PLAYER_HEAD);
//		if (url.isEmpty())
//			return head;
//
//		SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
//		try {
//            Object skullOwner = createNode(itemStack, "SkullOwner");
//            setMeta(skullOwner, "Id", id.toString());
//
//            Object properties = createNode(skullOwner, "Properties");
//
//            Object listMeta = class_NBTTagList_constructor.newInstance();
//            Object textureNode = class_NBTTagCompound_constructor.newInstance();
//
//            String textureJSON = "{textures:{SKIN:{url:\"" + url + "\"}}}";
//            String encoded = Base64Coder.encodeString(textureJSON);
//
//            setMeta(textureNode, "Value", encoded);
//            addToList(listMeta, textureNode);
//            class_NBTTagCompound_setMethod.invoke(properties, "textures", listMeta);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//	}

	/**
	 * Used to replace placeholders when an item is loaded from a config These
	 * placeholders should be surrounded in { }
	 * 
	 * @param is           The itemstack to replace the placeholders for
	 * @param placeholders The placeholders that need replacing
	 * @param values       The new values of those placeholders
	 */
	public static void replaceInternalPlaceholders(ItemStack is, String[] placeholders, String[] values) {

		if (placeholders.length != values.length) {
			throw new IllegalArgumentException("Number of values and placeholders provided does not match" + "");
		}

		ItemMeta meta = is.getItemMeta();

		meta.setDisplayName(internalPlaceholders(meta.getDisplayName(), placeholders, values));

		List<String> lore = meta.getLore();

		if (lore != null) {

			List<String> newLore = new ArrayList<>();

			for (String str : lore) {
				newLore.add(internalPlaceholders(str, placeholders, values));
			}
			meta.setLore(newLore);
		}

		is.setItemMeta(meta);

	}

	private static String internalPlaceholders(String str, String[] placeholders, String[] values) {

		for (int i = 0; i < placeholders.length; i++) {
			String toreplace = placeholders[i];
			if (!toreplace.startsWith("{")) {
				toreplace = "\\{" + toreplace + "\\}";
			}
			str = str.replaceAll(toreplace, values[i]);
		}

		return str;

	}
}
