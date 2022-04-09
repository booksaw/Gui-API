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
public class StringCommandSection implements CommandSection {

	private final String text;

	public StringCommandSection(String text) {
		this.text = text;
	}

	@Override
	public String getArg() {
		return text;
	}

	@Override
	public boolean isComplete() {
		return true;
	}

	@Override
	public void execute(CommandBuilderAction notify, Player player, GuiDisplay gui) {
		// nothing to do here as isComplete always returns true
	}

}
