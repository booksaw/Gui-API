package com.booksaw.guiAPI.API.chatEvent;

import org.bukkit.entity.Player;

public interface ChatEventListener {

	/**
	 * When the player speaks in chat,
	 * 
	 * @return true - if the event successfully ran, false - if there was a problem running the event
	 */
	public boolean runEvent(ChatEvent e);
	
	public void cancel(ChatEvent e);
	
	/**
	 * Send the prompt message to the player
	 * @param p
	 */
	public void sendMessage(Player p);

}
