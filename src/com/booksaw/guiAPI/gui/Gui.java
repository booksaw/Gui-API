package com.booksaw.guiAPI.gui;

import org.bukkit.entity.Player;

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

	public PlayerGuiDisplay createPlayerInstance(Player player) {

		PlayerGuiDisplay guiDisplay;
		try {
			guiDisplay = new PlayerGuiDisplay(this, defaultMetadata.clone(), defaultItems.clone(), player);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}

		return guiDisplay;
	}
	
	/**
	 * Used to add the cancel listener to this Gui
	 */
	public void addCancelListener() {
		defaultMetadata.addListener(new CancelGuiListener());
	}

}
