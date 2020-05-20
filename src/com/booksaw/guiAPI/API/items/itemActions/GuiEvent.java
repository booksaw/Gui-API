package com.booksaw.guiAPI.API.items.itemActions;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.booksaw.guiAPI.API.Gui;

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
	public InventoryClickEvent e;

	/**
	 * The gui that was clicked (in case the plugin has multiple GUIs)
	 */
	private Gui gui;

	private String command;

	/**
	 * Made by GuiAPI, it is not expected that you will need to create a GuiEvent
	 * Object
	 * 
	 * @param e       - the Event
	 * @param gui     - The GUI which was clicked
	 * @param command - The action command associated with that item
	 */
	public GuiEvent(InventoryClickEvent e, Gui gui, String command) {
		this.gui = gui;
		this.e = e;
		this.command = command;
	}

	/**
	 * The GUI which was clicked
	 * 
	 * @return
	 */
	public Gui getGui() {
		return gui;
	}

	public String getActionCommand() {
		return command;
	}

	public HumanEntity getPlayer() {
		return e.getWhoClicked();
	}

}
