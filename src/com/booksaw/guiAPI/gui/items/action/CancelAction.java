/**
 * 
 */
package com.booksaw.guiAPI.gui.items.action;

import com.booksaw.guiAPI.gui.events.GuiEvent;
import com.booksaw.guiAPI.gui.items.ItemAction;

/**
 * @author booksaw
 * Cancels the GuiEvent
 */
public class CancelAction implements ItemAction {

	@Override
	public void onEvent(GuiEvent event) {
		event.getEvent().setCancelled(true);
	}

	
	
}
