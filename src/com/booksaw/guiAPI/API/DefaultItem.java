package com.booksaw.guiAPI.API;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.booksaw.guiAPI.APIMain;
import com.booksaw.guiAPI.API.items.GuiItem;

/**
 * Used to select default items of this plugin (for quick forward and back
 * buttons etc.)
 * These can also be configured in the config by the server owner so they can have consistency between plugins
 * DO NOT EDIT THESE ITEMS AS THEY COULD BREAK OTHER PLUGINS
 * 
 * @author booksaw
 */
public enum DefaultItem {
	BLANK/** default: black glass pane with no name*/, BACK/** default: red glass pane with "BACK" as name*/, FORWARD/** default: green glass pane with "FOWARD" as name*/;

	private GuiItem item;
	
	/**
	 * Used to load these items from the config (no need to touch)
	 */
	private void loadItem() {
		if(Bukkit.getVersion().contains("1.8.8")){
			item = new GuiItem(new ItemStack(Material.STONE));
			return; 
		}
		item = new GuiItem(APIMain.mainGuiAPI.getConfig().getItemStack("items."+ name()));
	}
	
	/**
	 * used to load these items from the config (do not touch)
	 */
	public static void load() {
		for(DefaultItem i : values()) {
			i.loadItem();
		}
	}
	
	/**
	 * Gets the item for that object in the enum (Creates a clone so does not change default)
	 * 
	 * @return - The item
	 */
	public GuiItem getItem() {
		return item.clone();
	}
	
}
