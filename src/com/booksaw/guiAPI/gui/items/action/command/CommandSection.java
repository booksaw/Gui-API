/**
 * 
 */
package com.booksaw.guiAPI.gui.items.action.command;

import org.bukkit.entity.Player;

import com.booksaw.guiAPI.gui.GuiDisplay;
import com.booksaw.guiAPI.gui.items.action.CommandBuilderAction;

/**
 * @author booksaw
 *
 */
public interface CommandSection {

	/**
	 * Called when the command is being fully built.
	 * 
	 * @return The string part of the command that this command section contains
	 */
	public abstract String getArg();

	public abstract boolean isComplete();

	public abstract void execute(CommandBuilderAction notify, Player player, GuiDisplay gui);

}
