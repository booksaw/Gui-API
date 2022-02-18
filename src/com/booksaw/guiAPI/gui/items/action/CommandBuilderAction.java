/**
 * 
 */
package com.booksaw.guiAPI.gui.items.action;

import com.booksaw.guiAPI.gui.events.GuiEvent;
import com.booksaw.guiAPI.gui.items.ItemAction;

/**
 * Action used to execute a command when the event is called
 * 
 * @author booksaw
 * 
 */
public class CommandBuilderAction implements ItemAction {

	private String command;

	/**
	 * @param command the command that the player will execute
	 */
	public CommandBuilderAction(String command) {
		this.command = command;
	}

	@Override
	public void onEvent(GuiEvent event) {
		event.getPlayer().performCommand(command);
	}

}
