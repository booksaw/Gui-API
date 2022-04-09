/**
 * 
 */
package com.booksaw.guiAPI.gui.items.action;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.booksaw.guiAPI.gui.events.GuiEvent;
import com.booksaw.guiAPI.gui.items.ItemAction;

/**
 * @author booksaw
 *
 */
public class DelayAction implements ItemAction {

	private final JavaPlugin plugin;
	private final ItemAction action;
	private final int delay;

	public DelayAction(JavaPlugin plugin, ItemAction action, int delay) {
		this.plugin = plugin;
		this.action = action;
		this.delay = delay;
	}

	@Override
	public void onEvent(GuiEvent event) {
		new BukkitRunnable() {

			@Override
			public void run() {
				action.onEvent(event);
			}

		}.runTaskLater(plugin, delay);
	}

}
