/**
 * 
 */
package com.booksaw.guiAPI.gui.items.action;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.booksaw.guiAPI.gui.GuiDisplay;
import com.booksaw.guiAPI.gui.events.GuiEvent;
import com.booksaw.guiAPI.gui.items.ItemAction;
import com.booksaw.guiAPI.gui.items.action.command.ChatCommandSection;
import com.booksaw.guiAPI.gui.items.action.command.CommandSection;
import com.booksaw.guiAPI.gui.items.action.command.StringCommandSection;

/**
 * Action used to execute a command when the event is called
 * 
 * @author booksaw
 * 
 */
public class CommandBuilderAction implements ItemAction {

	private final JavaPlugin plugin;
	private List<CommandSection> command;

	/**
	 * @param command the command that the player will execute
	 */
	public CommandBuilderAction(JavaPlugin plugin) {
		this.plugin = plugin;
		command = new ArrayList<>();
	}

	public CommandBuilderAction addArg(CommandSection section) {

		if (section == null) {
			throw new IllegalArgumentException("Provided argument cannot be null");
		}

		command.add(section);
		return this;
	}

	/**
	 * Used to add static text to the command
	 * 
	 * @return This instance
	 */
	public CommandBuilderAction addArg(String text) {
		addArg(new StringCommandSection(text));
		return this;
	}

	/**
	 * Used to add a variable that the player can enter a value for =
	 * 
	 * @param prompt The prompt that the player will recieve in chat
	 * @param argMax The maximum number of arguments desired
	 * @return
	 */
	public CommandBuilderAction addVariable(String prompt) {
		addArg(new ChatCommandSection(plugin, prompt));

		return this;
	}

	@Override
	public void onEvent(GuiEvent event) {
		nextArg(event.getPlayer(), event.getGui());
	}

	public void nextArg(Player player, GuiDisplay gui) {
		StringBuilder commandStr = new StringBuilder();

		for (CommandSection section : command) {
			if (section.isComplete()) {
				commandStr.append(section.getArg()).append(" ");
			} else {
				section.execute(this, player, gui);
				return;
			}
		}
		new BukkitRunnable() {

			@Override
			public void run() {
				System.out.println("executing " + commandStr.toString().trim());
				player.performCommand(commandStr.toString().trim());
			}
		}.runTask(plugin);

	}

}
