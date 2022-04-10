/**
 * 
 */
package com.booksaw.guiAPI.gui.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author booksaw
 *
 */
public class ItemAPI {

	/**
	 * Used to replace the internal placeholders on an item stack
	 * 
	 * @param is           The itemstack to replace the placeholders of
	 * @param replacements The replacements, each Entry<String, String> is of the
	 *                     format <placeholder, replacement>
	 */
	public static void replaceInternalPlaceholders(ItemStack is, HashMap<String, String> replacements) {

		ItemMeta im = is.getItemMeta();
		im.setDisplayName(replacePlaceholders(im.getDisplayName(), replacements));

		List<String> lore = new ArrayList<>();
		;
		for (String str : im.getLore()) {
			lore.add(replacePlaceholders(str, replacements));
		}
		im.setLore(lore);
		is.setItemMeta(im);

	}

	private static String replacePlaceholders(String str, HashMap<String, String> replacements) {
		for (Entry<String, String> entry : replacements.entrySet()) {
			str = str.replace(entry.getKey(), entry.getValue());
		}
		return str;
	}

	private ItemAPI() {

	}

}
