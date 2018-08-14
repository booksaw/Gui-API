package com.booksaw.guiAPI.backend;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.booksaw.guiAPI.API.chatEvent.ChatEvent;

public class ChatListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		
		if(ChatEvent.getEvents().containsKey(e.getPlayer())) {
			e.setCancelled(true);
			ChatEvent.getEvents().get(e.getPlayer()).onChat(e.getMessage());
		}
	
	}
}
