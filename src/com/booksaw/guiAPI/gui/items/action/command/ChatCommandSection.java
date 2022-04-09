/**
 * 
 */
package com.booksaw.guiAPI.gui.items.action.command;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.booksaw.guiAPI.gui.GuiDisplay;
import com.booksaw.guiAPI.gui.items.action.CommandBuilderAction;

/**
 * @author booksaw
 *
 */
public class ChatCommandSection implements CommandSection {

	private final String prompt;
	private final JavaPlugin plugin;

	private String result = null;

	/**
	 * 
	 */
	public ChatCommandSection(JavaPlugin plugin, String prompt) {
		this.prompt = prompt;
		this.plugin = plugin;
	}

	@Override
	public String getArg() {
		return result;
	}

	@Override
	public boolean isComplete() {
		return result != null;
	}

	@Override
	public void execute(CommandBuilderAction notify, Player player, GuiDisplay gui) {
		player.sendMessage(prompt);
		player.closeInventory();

		new ChatListenerSection(plugin, player, notify, gui);

	}

	private class ChatListenerSection implements Listener {

		private final Player player;
		private final CommandBuilderAction notify;
		private final GuiDisplay gui;

		public ChatListenerSection(JavaPlugin plugin, Player player, CommandBuilderAction notify, GuiDisplay gui) {
			this.player = player;
			this.notify = notify;
			this.gui = gui;

			plugin.getServer().getPluginManager().registerEvents(this, plugin);

		}

		@EventHandler
		public void onChat(AsyncPlayerChatEvent e) {
			if (e.getPlayer() != player) {
				return;
			}
			e.setCancelled(true);
			// event has been called, no longer needed
			HandlerList.unregisterAll(this);

			String msg = e.getMessage();
			result = msg;
			notify.nextArg(player, gui);

		}
	}

}
