/**
 * 
 */
package com.booksaw.guiAPI.gui.items.action;

import com.booksaw.guiAPI.gui.events.GuiEvent;
import com.booksaw.guiAPI.gui.items.ItemAction;

/**
 * @author booksaw
 * Action used to close the players GUI
 */
public class CloseAction implements ItemAction {

	@Override
	public void onEvent(GuiEvent event) {
		event.getPlayer().closeInventory();
	}

}
