package com.booksaw.guiAPI;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.booksaw.guiAPI.API.DefaultItem;
import com.booksaw.guiAPI.API.GuiManager;
import com.booksaw.guiAPI.backend.ChatListener;
import com.booksaw.guiAPI.backend.InventoryListener;
import com.booksaw.guiAPI.commands.CommandGuiAPI;

/**
 * Main class nothing interesting here
 * 
 * @author booksaw
 *
 */
public class APIMain extends JavaPlugin {

	/**
	 * Used mainly to access config files etc. (no need for people using this API to
	 * touch)
	 */
	public static APIMain mainGuiAPI;

	/**
	 * Just enabling my listeners not interesting
	 */
	@Override
	public void onEnable() {
		// saving a static version
		mainGuiAPI = this;

		// saving config
		saveDefaultConfig();

		// loading default items
		DefaultItem.load();

		// setting up the /guiapi command
		getCommand("guiapi").setExecutor(new CommandGuiAPI());

		// registering the listener
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new InventoryListener(), this);
		pm.registerEvents(new ChatListener(), this);

	}

	/**
	 * Closing all associated GUIs on reload as details about them are not kept so
	 * could take items with no issues
	 */
	@Override
	public void onDisable() {
		GuiManager.stop();
	}

	// // TODO - build GUI and display to user (GuiManager)
	// // EVENT LISTENING
	// // PRE-BUILTS
	// // DEFULT ITEMS
	// // Layouts (border, row, column (quick ways of doing that) ENUM user
	// // specifies
	// // fill command (with minimum and max)
	// // Handle closing inventories
	// // items --> configs.

	// FUTURE:
	// easy folder system

	// item lists different event in item collection

	// ONGOING
	// full JavaDocs
}
