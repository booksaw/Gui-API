package com.booksaw.guiAPI.API.items.itemActions;

import org.bukkit.entity.Player;

import com.booksaw.guiAPI.API.chatEvent.ChatEventListener;
import com.booksaw.guiAPI.API.chatEvent.ChatEventLong;

public class LongChatEvent implements ItemAction {

	private ChatEventListener listener;
	private boolean forceComplete;

	/**
	 * Used to add a LongChatEvent Action which gets chat response from the User.
	 * 
	 * 
	 * @param p        - The player which is in the event
	 * @param message  - The message which is sent at the start of the event (prompt
	 *                 message)
	 * @param listener - The class which implements ChatEventListener, which will be
	 *                 notified when the player enters the response
	 */
	public LongChatEvent(ChatEventListener listener) {
		this(listener, false);
	}

	/**
	 * Used to add a LongChatEvent Action which gets chat response from the User.
	 * 
	 * 
	 * @param p             - The player which is in the event
	 * @param message       - The message which is sent at the start of the event
	 *                      (prompt message)
	 * @param listener      - The class which implements ChatEventListener, which
	 *                      will be notified when the player enters the response
	 * @param forceComplete - If the user cannot quit (if the event is returned to
	 *                      be invalid, if they have to try again or if the event if
	 *                      cancelled) -- default false
	 */
	public LongChatEvent(ChatEventListener listener, boolean forceComplete) {
		this.listener = listener;
		this.forceComplete = forceComplete;
	}

	@Override
	public void onEvent(GuiEvent e) {
		ChatEventLong eLong = new ChatEventLong((Player) e.e.getWhoClicked(), listener, forceComplete);
		eLong.runEvent();
	}

}
