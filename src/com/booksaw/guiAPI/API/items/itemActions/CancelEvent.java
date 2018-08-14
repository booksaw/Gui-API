package com.booksaw.guiAPI.API.items.itemActions;

/**
 * Cancels the event
 * 
 * @author booksaw
 *
 */
public class CancelEvent implements ItemAction {

	@Override
	public void onEvent(GuiEvent e) {
		e.e.setCancelled(true);
	}

}
