package com.booksaw.guiAPI.API.chatEvent;

import org.bukkit.entity.Player;

/**
 * This class is used to store the details about any players who are in long
 * chat events The most common way of using this class is through the
 * LongChatEvent class as that is run when a player interacts with an item.
 * 
 * @author booksaw
 *
 */
public class ChatEventLong extends ChatEvent {

	private String message;

	/**
	 * Used to create a new ChatEventLong class, this class is used to store the
	 * details of a player who is a in a PlayerChatEvent
	 * 
	 * @param p        - The player which is in the event
	 * @param message  - The message which is sent at the start of the event (prompt
	 *                 message)
	 * @param listener - The class which implements ChatEventListener, which will be
	 *                 notified when the player enters the response
	 */
	public ChatEventLong(Player p, String message, ChatEventListener listener) {
		this(p, message, listener, false);
	}

	/**
	 * Used to create a new ChatEventLong class, this class is used to store the
	 * details of a player who is in a PlayerChatEvent
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
	public ChatEventLong(Player p, String message, ChatEventListener listener, boolean forceComplete) {
		super(listener, p);
		this.message = message;
		this.forceComplete = forceComplete;
	}

	@Override
	public boolean runEvent() {
		boolean worked = super.runEvent();
		if (worked) {
			p.sendMessage(message);
			return true;
		}
		return false;
	}

}
