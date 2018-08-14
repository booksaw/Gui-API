package com.booksaw.guiAPI.API.items.itemActions;

/**
 * Sends a message to the player
 * 
 * @author booksaw
 *
 */
public class MessageEvent implements ItemAction {

	/**
	 * The message
	 */
	public String message;

	/**
	 * Sends the allotted message to the player
	 * 
	 * @param message - The message to send to the player - %PLAYER% - replaced with
	 *                the player name - %DISPLAY% - replaced with the player display
	 *                name
	 */
	public MessageEvent(String message) {
		this.message = message;
	}

	@Override
	public void onEvent(GuiEvent e) {
		String tmpMessage = message.replaceAll("%PLAYER%", e.e.getWhoClicked().getName());
		tmpMessage = tmpMessage.replaceAll("%DISPLAY%", e.e.getWhoClicked().getCustomName());

		e.e.getWhoClicked().sendMessage(tmpMessage);
	}

}
