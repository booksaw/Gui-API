package com.booksaw.guiAPI.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

import com.booksaw.guiAPI.gui.items.ItemCollection;

/**
 * @author booksaw
 *
 *         This class is a specific display of the GUI.
 *
 */
public class GuiDisplay {

	protected final Gui gui;

	protected final GuiMetadata guiMetadata;

	protected final ItemCollection items;

	/**
	 * Used to create a new GuiDisplay
	 * 
	 * @param gui         The gui to create the display for
	 * @param guiMetadata The metadata for the GUI
	 * @param items       The items wihtin this GUI
	 */
	public GuiDisplay(Gui gui, GuiMetadata guiMetadata, ItemCollection items) {
		this.gui = gui;
		this.guiMetadata = guiMetadata;
		this.items = items;
	}

	public GuiMetadata getGuiMetadata() {
		return guiMetadata;
	}

	public ItemCollection getItems() {
		return items;
	}

	public Gui getGui() {
		return gui;
	}

	public void outside(InventoryClickEvent e) {
		guiMetadata.outside(e);
	}

}
