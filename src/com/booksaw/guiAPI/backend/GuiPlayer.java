package com.booksaw.guiAPI.backend;

import org.bukkit.entity.Player;

import com.booksaw.guiAPI.API.items.ItemCollection;

/**
 * Used to store details about players using GUIs
 * 
 * @author booksaw
 *
 */
public class GuiPlayer {

	/**
	 * The player
	 */
	public Player p;
	
	/**
	 * The unique item collection for that player
	 */
	public ItemCollection items;
	
	/**
	 * The reference for the GUI so it can be accessed later
	 */
	public String ref;

	public GuiPlayer(Player p, ItemCollection i, String ref) {
		this.p = p;
		items = i;
		this.ref = ref;
	}
}
