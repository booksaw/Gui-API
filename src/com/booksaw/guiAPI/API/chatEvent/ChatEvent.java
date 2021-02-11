package com.booksaw.guiAPI.API.chatEvent;

import java.util.HashMap;

import org.bukkit.entity.Player;

/**
 * The general chatEvent class - use either ChatEVentLong or ChatEventShort to
 * use.
 * 
 * @author booksaw
 *
 */
public abstract class ChatEvent {

	/**
	 * A list of all the active events (for back-end)
	 */
	private static HashMap<Player, ChatEvent> events = new HashMap<>();

	public static HashMap<Player, ChatEvent> getEvents() {
		return events;
	}

	protected Player p;
	protected ChatEventListener listener;
	boolean forceComplete;
	private String message;

	public ChatEvent(ChatEventListener listener, Player p) {
		this.listener = listener;
		this.p = p;
	}

	/**
	 * This is used to trigger the event (it sends the player the prompt message and
	 * the next chat message is returned to the listener.
	 * 
	 * @return - true - if the event is run, false - if the event cannot be run as
	 *         the player is associated with another event.
	 */
	public boolean runEvent() {
		if (events.containsKey(p)) {
			return false;
		}
		events.put(p, this);
		return true;
	}

	public void onChat(String message) {
		this.message = message;
		if (listener.runEvent(this)) {
			events.remove(p);
		} else if (!forceComplete) {
			cancel();
		} else {
			runEvent();
		}
	}

	/**
	 * Used to remove the exsistance of an event (stops the listener taking the next
	 * message) This is run when players quit while in an event.
	 */
	public void remove() {
		events.remove(p);
	}

	/**
	 * If the event is cancelled (mainly if they close the inventory for short chat
	 * event)
	 */
	public void cancel() {
		events.remove(p);
		listener.cancel(this);
	}

	public Player getPlayer() {
		return p;
	}

	public String getMessage() {
		return message;
	}

}
