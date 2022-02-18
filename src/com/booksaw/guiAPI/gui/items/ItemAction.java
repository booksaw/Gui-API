/**
 * 
 */
package com.booksaw.guiAPI.gui.items;

import com.booksaw.guiAPI.gui.events.GuiEvent;

/**
 * @author booksaw Interface for an action that is called when a GuiItem is
 *         clicked
 */
public interface ItemAction {

	/**
	 * Called when a Gui
	 * 
	 * @param event The GUI event containing all information required
	 */
	public void onEvent(GuiEvent event);

}
