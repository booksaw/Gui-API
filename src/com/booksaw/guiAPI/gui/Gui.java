package com.booksaw.guiAPI.gui;

import com.booksaw.guiAPI.gui.items.ItemCollection;

/**
 * The class which manages all information relating to a GUI.
 * 
 * @author booksaw
 */
public class Gui {

	private GuiMetadata defaultMetadata;
	private ItemCollection defaultItems;

	/**
	 * Used to create a new GUI
	 * 
	 * @param defaultMetadata The metadata for the GUI
	 * @param defaultItems    The default items in the GUI
	 */
	public Gui(GuiMetadata defaultMetadata, ItemCollection defaultItems) {
		this.defaultMetadata = defaultMetadata;
		this.defaultItems = defaultItems;
	}

	public Gui() {
		this(new GuiMetadata(), new ItemCollection());
	}
	
	public Gui(String name) {
		this(new GuiMetadata(name), new ItemCollection());
	}

	public GuiMetadata getDefaultMetadata() {
		return defaultMetadata;
	}

	public void setDefaultMetadata(GuiMetadata defaultMetadata) {
		this.defaultMetadata = defaultMetadata;
	}

	public ItemCollection getDefaultItems() {
		return defaultItems;
	}

	public void setDefaultItems(ItemCollection defaultItems) {
		this.defaultItems = defaultItems;
	}

	
	
}
