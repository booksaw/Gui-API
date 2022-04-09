package com.booksaw.guiAPI.gui.backend;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import com.booksaw.guiAPI.gui.GuiDisplay;
import com.booksaw.guiAPI.gui.events.GuiEvent;
import com.booksaw.guiAPI.gui.items.GuiItem;

/**
 * @author booksaw Used to listen to events occuring in a GUI and handle them
 */
public class GuiEventListener implements Listener {

	private final GuiDisplay gui;
	private final Inventory inv;
	private final JavaPlugin plugin;
	private final Player player;

	/**
	 * Create a new event listener to manage the events for a specified GUI
	 * 
	 * @param gui    The gui to listen to events from
	 * @param inv    The inventory being opened
	 * @param plugin the JavaPlugin this API is a part of
	 */
	public GuiEventListener(GuiDisplay gui, Inventory inv, JavaPlugin plugin, Player player) {
		this.gui = gui;
		this.inv = inv;
		this.player = player;

		if (plugin == null) {
			throw new IllegalArgumentException("Provided JavaPlugin cannot be null");
		}
		this.plugin = plugin;
	}

	/**
	 * Used to register this listner so it is listening for events
	 */
	public void registerListener() {

		plugin.getServer().getPluginManager().registerEvents(this, plugin);

	}

	/**
	 * Used to unregister this listener
	 */
	public void unregisterListener() {

		HandlerList.unregisterAll(this);

	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		// checking if the inventory is the one we are looking for
		if (e.getInventory() != inv) {
			return;
		}

		// if the top inventory has been clicked
		if (e.getClickedInventory() == e.getInventory()) {
			GuiItem item = gui.getItems().getItem(e.getSlot());

			// no item clicked
			if (item == null) {
				gui.outside(e);
				return;
			}

			// building the event
			GuiEvent event = new GuiEvent(e, gui, item.getActionCommand(), item);
			item.execute(event);
		} else if (e.getWhoClicked() == player) {
			gui.outside(e);
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (e.getInventory() != inv && player != e.getPlayer()) {
			return;
		}

		unregisterListener();
	}

}
