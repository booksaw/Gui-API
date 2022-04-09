/**
 * 
 */
package com.booksaw.guiAPI.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author booksaw
 *
 */
public interface GuiListener {

	/**
	 * Called when the user clicks on something not tracked by guiAPI
	 * 
	 * @param e The inventory click event for the item
	 */
	public void outside(InventoryClickEvent e);

}
