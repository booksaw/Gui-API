package com.booksaw.guiAPI.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import com.booksaw.guiAPI.API.items.ItemCollection;
import com.booksaw.guiAPI.backend.GuiPlayer;

/**
 * MUST ADD YOUR GUI TO THIS CLASS (GuiManager.registerGui(Gui)) This class is
 * used to manage GUIs most is automated back-end management, methods of use may
 * be GuiManager.getGui(Player / String reference)
 */
public class GuiManager {

	/**
	 * List of all GUIs
	 */
	public static List<Gui> guis = new ArrayList<>();

	/**
	 * List of players accessing Guis along with some data about them
	 */
	protected static HashMap<Player, GuiPlayer> players = new HashMap<>();

	/**
	 * THIS IS IMPORTANT, YOU MUST REGISTER ALL GUIs (Mainly recommended to do on
	 * load)
	 * 
	 * @param gui - create a new Gui object of the gui you want to register (must
	 *            only be done once per GUI)
	 */
	public static void registerGui(Gui gui) {
		gui.enable();
		guis.add(gui);
	}

	/**
	 * Don't need a GUI any more? Use this to remove it
	 * 
	 * @param gui - The GUI that should be removed
	 */
	public static void removeGui(Gui gui) {
		guis.remove(gui);
	}

	/**
	 * Returns the GUI with the specified reference
	 * 
	 * @param refrence - The reference for that GUI
	 * @return the gui with that reference
	 */
	public static Gui getGui(String reference) {
		for (Gui gui : guis) {
			if (gui.REFERENCE.equals(reference)) {
				return gui;
			}
		}
		return null;
	}

	/**
	 * Used to get the gui a player is viewing
	 * 
	 * @param p - the player to find the gui for
	 * @return - the gui that the player is viewing
	 */
	public static Gui getGui(Player p) {
		GuiPlayer gp = players.get(p);
		if (gp == null) {
			return null;
		}
		return getGui(gp.ref);
	}

	/**
	 * USE REFERENCE WHENEVER POSSIBLE Used to get the inventory from the name not
	 * preferred as could have conflicts
	 * 
	 * 
	 * @param name - the name of the gui
	 * @return
	 */
	@Deprecated
	public static Gui getGuiByName(String name) {
		for (Gui gui : guis) {
			if (gui.name.equals(name)) {
				return gui;
			}
		}
		return null;
	}

	/**
	 * Tells the plugin that a user is viewing that Gui. Mainly used by the API
	 * can't think of any other use
	 * 
	 * @param p   - the player who is being added to the GUI
	 * @param i   - the item collection for the player
	 * @param gui - the GUI they are viewing
	 */
	public static void addPlayer(Player p, ItemCollection i, Gui gui) {
		if (players.containsKey(p)) {
			players.remove(p);
		}
		players.put(p, new GuiPlayer(p, i, gui.REFERENCE));
	}

	/**
	 * Removes a player from a gui (when they close it etc.) generally not used
	 * 
	 * @param p - The player that should be removed
	 */
	public static void removePlayer(Player p) {

		players.remove(p);

	}

	public static void stop() {
		for (Player p : players.keySet()) {
			p.closeInventory();
		}
	}

}
