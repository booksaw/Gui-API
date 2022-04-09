/**
 * 
 */
package com.booksaw.guiAPI.gui.items.action;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.booksaw.guiAPI.gui.events.GuiEvent;
import com.booksaw.guiAPI.gui.items.ItemAction;

/**
 * ItemAction used to reload the current GUI
 * 
 * @author booksaw
 *
 */
public class ReloadAction implements ItemAction {

	private final JavaPlugin plugin;

	/**
	 * Create a new ReloadAction
	 * 
	 * @param plugin The JavaPlugin that governs the GUI
	 */
	public ReloadAction(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onEvent(final GuiEvent event) {
		event.getPlayer().closeInventory();

		// adding a delay otherwise spigot gets confused
		new BukkitRunnable() {

			@Override
			public void run() {
				event.getGui().getGui().createPlayerInstance(event.getPlayer()).displayGui(plugin);
			}
		}.runTaskLater(plugin, 1);

	}

}
