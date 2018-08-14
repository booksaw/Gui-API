package com.booksaw.guiAPI.API.items.itemActions;

public class CloseEvent implements ItemAction {

	@Override
	public void onEvent(GuiEvent e) {
		e.e.getWhoClicked().closeInventory();
	}

}
