package com.booksaw.guiAPI.backend;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.booksaw.guiAPI.API.Gui;
import com.booksaw.guiAPI.API.GuiManager;
import com.booksaw.guiAPI.API.chatEvent.ChatEvent;

/**
 * Listens to various events to manage GUIs - developers, no need to look here
 * 
 * @author booksaw
 *
 */
public class InventoryListener implements Listener {

	/**
	 * Used to register clicks and run the appropriate code, no need to touch
	 * 
	 * @param e - the event
	 */
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}

		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
			return;
		}
		Gui gui = GuiManager.getGui((Player) e.getWhoClicked());
		if (gui == null) {
			return;
		}
		gui.click((Player) e.getWhoClicked(), gui, e.getCurrentItem(), e);
	}

	/**
	 * When the player leaves, telling plugin that they are no longer around
	 * 
	 * @param e - the event
	 */
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Gui gui = GuiManager.getGui(e.getPlayer());

		if (gui != null) {
			GuiManager.removePlayer(e.getPlayer());
			gui.onLeave(e.getPlayer(), e);
		}

		ChatEvent ev = ChatEvent.getEvents().get(e.getPlayer());
		if (ev != null) {
			ev.remove();
		}
	}

	/**
	 * When the player leaves, telling the plugin that they are no longer around
	 * 
	 * @param e - the event
	 */
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (!(e.getPlayer() instanceof Player)) {
			return;
		}

		Gui gui = GuiManager.getGui((Player) e.getPlayer());
		if (Gui.getIgnoreClose().contains((Player) e.getPlayer())) {
			Gui.removeIgnoredPlayer((Player) e.getPlayer());
			return;
		}

		if (gui == null) {
			return;
		}

		GuiManager.removePlayer((Player) e.getPlayer());
		gui.onClose((Player) e.getPlayer(), e);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}

		Gui gui = GuiManager.getGui((Player) e.getEntity());
		if (Gui.getIgnoreClose().contains((Player) e.getEntity())) {
			Gui.removeIgnoredPlayer((Player) e.getEntity());
			return;
		}

		if (gui == null) {
			return;
		}

		GuiManager.removePlayer((Player) e.getEntity());
		gui.onDeath((Player) e.getEntity());
	}
}
