/**
 * 
 */
package com.booksaw.guiAPI.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Cancellss anything clicked outside of the tracked items
 * 
 * @author booksaw
 *
 */
public class CancelGuiListener implements GuiListener {

	@Override
	public void outside(InventoryClickEvent e) {
		e.setCancelled(true);
	}

}
