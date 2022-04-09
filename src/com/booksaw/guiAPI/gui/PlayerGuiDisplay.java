package com.booksaw.guiAPI.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import com.booksaw.guiAPI.gui.backend.GuiEventListener;
import com.booksaw.guiAPI.gui.items.ItemCollection;

/**
 * @author booksaw
 *
 *         A instance of a GuiDisplay tended for a specific player
 */
/**
 * @author booksaw
 *
 */
public class PlayerGuiDisplay extends GuiDisplay {

	private Player player = null;
	private GuiEventListener listener;

	/**
	 * @param gui
	 */
	public PlayerGuiDisplay(Gui gui, GuiMetadata meta, ItemCollection items) {
		super(gui, meta, items);
	}

	public PlayerGuiDisplay(Gui gui, GuiMetadata meta, ItemCollection items, Player player) {
		this(gui, meta, items);
		setPlayer(player);
	}

	/**
	 * This can only be called a single time per PlayerGuiDisplay else will raise an
	 * IllegalStateException
	 * 
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {

		if (this.player != null) {
			throw new IllegalStateException("The player for this PlayerGuiDisplay has already been set");
		}

		this.player = player;
	}

	/**
	 * Used to display the GUI and set the player at the same time
	 * 
	 * @param player the player to display the GUI to
	 * @param plugin The JavaPlugin that is controlling this GUI
	 */
	public void displayGui(Player player, JavaPlugin plugin) {
		setPlayer(player);
		displayGui(plugin);
	}

	/**
	 * Used to display the GUI to the set player
	 * 
	 * @param plugin The JavaPlugin that is creating this GUI
	 */
	public void displayGui(JavaPlugin plugin) {

		if (player == null) {
			throw new IllegalArgumentException("The player must be set before displaying the GUI");
		}

		Inventory gui = createInventory();

		items.buildGui(gui);

		listener = new GuiEventListener(this, gui, plugin, player);
		listener.registerListener();

		player.openInventory(gui);

	}

	/**
	 * Used to create a Bukkit inventory with the given data
	 * 
	 * @return The created inventory
	 */
	private Inventory createInventory() {

		Inventory inv;

		if (guiMetadata.getInvType() == InventoryType.CHEST) {
			inv = Bukkit.createInventory(null, guiMetadata.getSize(), guiMetadata.getGuiName());
		} else {
			inv = Bukkit.createInventory(null, guiMetadata.getInvType(), guiMetadata.getGuiName());
		}

		return inv;
	}
	
	
	
}
