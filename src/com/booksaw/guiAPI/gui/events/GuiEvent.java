package com.booksaw.guiAPI.gui.events;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.booksaw.guiAPI.gui.GuiDisplay;
import com.booksaw.guiAPI.gui.items.GuiItem;

/**
 * Parsed by the listener when a GUI is clicked on
 * 
 * @author booksaw
 *
 */
public class GuiEvent {

	/**
	 * The event (remember it is not default cancelled so that is important
	 */
	private final InventoryClickEvent e;

	/**
	 * The gui that was clicked (in case the plugin has multiple GUIs)
	 */
	private final GuiDisplay gui;

	private final String command;

	private final GuiItem item;

	/**
	 * Made by GuiAPI, it is not expected that you will need to create a GuiEvent
	 * Object
	 * 
	 * @param e       - the Event
	 * @param gui     - The GUI which was clicked
	 * @param command - The action command associated with that item
	 * @param item    The item that was clicked on
	 */
	public GuiEvent(InventoryClickEvent e, GuiDisplay gui, String command, GuiItem item) {
		this.gui = gui;
		this.e = e;
		this.command = command;
		this.item = item;
	}

	/**
	 * The GUI which was clicked
	 * 
	 * @return
	 */
	public GuiDisplay getGui() {
		return gui;
	}

	public String getActionCommand() {
		return command;
	}

	public Player getPlayer() {
		return (Player) e.getWhoClicked();
	}

	public InventoryClickEvent getEvent() {
		return e;
	}

	/**
	 * @return The GUI item that was clicked on, if no GUI item was found will
	 *         return null
	 */
	public GuiItem getItem() {
		return item;
	}

}