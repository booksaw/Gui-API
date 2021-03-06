package com.booksaw.guiAPI.API;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.booksaw.guiAPI.APIMain;
import com.booksaw.guiAPI.API.items.GuiItem;
import com.booksaw.guiAPI.API.items.ItemCollection;
import com.booksaw.guiAPI.API.items.itemActions.GuiEvent;
import com.booksaw.guiAPI.backend.GuiPlayer;

import net.md_5.bungee.api.ChatColor;

/**
 * This class must be extended to create a new GUI (There are some defaults
 * included in the GUI pack which can be used)
 * 
 * @author booksaw
 */
public abstract class Gui {

	/**
	 * This list is used to track any players which are being transferred between
	 * inventories instead of closing an inventory, this is used to ensure
	 * onClose(Player) is only run when the player closes the GUI manually
	 */
	private static List<Player> ignoreClose = new ArrayList<>();

	/**
	 * @return a list of players who are being transferred between inventories
	 */
	public static List<Player> getIgnoreClose() {
		return ignoreClose;
	}

	/**
	 * This method should be used once a the close event for the inventory transfer
	 * is detected
	 * 
	 * @param player the player to remove from the ignore list
	 */
	public static void removeIgnoredPlayer(Player player) {
		ignoreClose.remove(player);
	}

	/**
	 * The largest an inventory can be (the size of a double chest inventory)
	 */
	public static final int MAXSIZE = 54;

	/**
	 * The generic item collection (not specific for the player)
	 */
	protected ItemCollection items;

	/**
	 * The unique reference for this GUI (so it does not conflict with other
	 * plugins)
	 */
	public String REFERENCE;

	/**
	 * A variable of the enum SizeType, which is used to specify how the size is
	 * calculated, default is SizeType.SMALLEST
	 */
	public SizeType sizeType = SizeType.SMALLEST;

	/**
	 * Chance this variable if SizeType is set to custom
	 */
	public int size = -1;

	/**
	 * No point extending only done to setup some GuiAPI stuff before initialise()
	 * is run.
	 */
	public final void enable() {
		items = new ItemCollection();

		REFERENCE = getReference();
		layout(items);
		initialise(items);

	}

	/**
	 * Used to rebuild the item collection if your plugin wants to make changes to
	 * it
	 */
	public void reinitGui() {
		items = new ItemCollection();
		layout(items);
		initialise(items);
	}

	/**
	 * Use to display the GUI to the player
	 * 
	 * @param p the player which the gui should be displayed to
	 * @return result false if the clone does not work - true if the gui was
	 *         displayed
	 */
	public boolean displayGui(Player p) {
		return displayGui(p, null);
	}

	/**
	 * Use to display the GUI to the player
	 * 
	 * @param p       the player which the gui should be displayed to
	 * @param details details to pass onto the gui
	 * @return result false if the clone does not work - true if the gui was
	 *         displayed
	 */
	public boolean displayGui(Player p, String[] details) {
		ItemCollection itemsClone;

		try {
			itemsClone = items.clone();
		} catch (CloneNotSupportedException e) {
			return false;
		}

		if (details == null) {
			details = new String[0];
		}

		itemsClone.setName(getName());
		buildGui(p, itemsClone, details);

		if (itemsClone.getName() == null) {
			itemsClone.setName("");
		}
		Inventory i = Bukkit.createInventory(null, sizeType.getSize(itemsClone.getLastItem(), size),
				itemsClone.getName());
		itemsClone.buildGui(i);
		ignoreClose.add(p);
		openInventory(p, i);
		ignoreClose.remove(p);
		// a way to have unique names for each player
		GuiPlayer player = new GuiPlayer(p, itemsClone, REFERENCE);
		GuiManager.addPlayer(player, itemsClone, this);
		return true;
	}

	/**
	 * Use to display the GUI to the player
	 * 
	 * @param p the player which the gui should be displayed to
	 * @return result false if the clone does not work - true if the gui was
	 *         displayed
	 */
	public boolean displayGui(GuiPlayer p) {
		return displayGui(p, null);
	}

	/**
	 * Use to display the GUI to the player
	 * 
	 * @param p       the player which the gui should be displayed to
	 * @param details details to pass onto the gui
	 * @return result false if the clone does not work - true if the gui was
	 *         displayed
	 */
	public boolean displayGui(GuiPlayer p, String details[]) {
		ItemCollection itemsClone;

		try {
			itemsClone = items.clone();
		} catch (CloneNotSupportedException e) {
			return false;
		}

		if (details == null) {
			details = new String[0];
		}

		itemsClone.setName(getName());
		buildGui(p.getPlayer(), itemsClone, details);

		Inventory i = Bukkit.createInventory(null, sizeType.getSize(items.getLastItem(), size), itemsClone.getName());
		itemsClone.buildGui(i);

		ignoreClose.add(p.getPlayer());
		openInventory(p.getPlayer(), i);
		ignoreClose.remove(p.getPlayer());
		GuiManager.addPlayer(p, itemsClone, this);
		return true;
	}

	/**
	 * Used to open the provided inventory (ensures that this is run sychronously)
	 * 
	 * @param p The player to open the inventory for
	 * @param i The inventory to open
	 */
	private void openInventory(Player p, Inventory i) {

		if (Bukkit.isPrimaryThread()) {
			p.openInventory(i);
		} else {
			Bukkit.getScheduler().runTask(APIMain.mainGuiAPI, new Runnable() {

				@Override
				public void run() {
					p.openInventory(i);
				}
			});
		}

	}

	/**
	 * Mainly used back-end when the event is called but could be used to force a
	 * player to click on an item
	 * 
	 * @param p   the player that ran the event
	 * @param gui the GUI that they had open (use GuiManager to get)
	 * @param is  the Item that they clicked on
	 * @param e   The event (so plugin developers can get all the details they want
	 *            / cancel it)
	 */
	public void click(Player p, Gui gui, ItemStack is, InventoryClickEvent e) {
		// checking if it is the players inventory
		if (p.getInventory() == e.getClickedInventory()) {
			clickedMainInventory(p, is, e);
			return;
		}

		try {
			GuiPlayer guip = GuiManager.players.get(p);
			if (guip == null) {
				Bukkit.getLogger().warning(
						ChatColor.RED + "Could not find player, this should never happen. Please make a bug report.");
				return;
			}
			GuiItem item = guip.items.getItem(e.getSlot());
			if (item == null) {
				unrecognisedItem(p, e.getCurrentItem(), e);
				return;
			}
			item.runAction(new GuiEvent(e, gui, item.getActionCommand()));
		} catch (Exception ex) {
			e.setCancelled(true);
			ex.printStackTrace();
		}
	}

	/**
	 * this method can be overwritten if you do not want the event to be cancelled
	 * if the user selects an item in their own inventory (not the chest GUI)
	 * 
	 * @param p  the player that ran the event
	 * @param is the Item that they clicked on
	 * @param e  The event (so plugin developers can get all the details they want /
	 *           cancel it)
	 */
	public void clickedMainInventory(Player p, ItemStack is, InventoryClickEvent event) {
		event.setCancelled(true);
	}

	/**
	 * This method is called if an item is found in the GUI portion of the
	 * 
	 * @param p     The player that ran the event
	 * @param is    The item they selected
	 * @param event The event
	 */
	public void unrecognisedItem(Player p, ItemStack is, InventoryClickEvent event) {
		Bukkit.getLogger().warning("Item is not recognised");
		event.setCancelled(true);
	}

	/**
	 * This method can be used if your GUI needs to detect when a player leaves the
	 * server while interacting with your GUI
	 * 
	 * @param player the player which has left
	 */
	public void onLeave(Player player, PlayerQuitEvent e) {
	}

	/**
	 * this method can be used if your GUI needs to detect when a player closes the
	 * GUI
	 * 
	 * @param player the player which closed the GUI
	 */
	public void onClose(Player player, InventoryCloseEvent e) {
	}

	/**
	 * this method can be used if your GUI needs to detect when a player dies while
	 * in the GUI
	 * 
	 * @param player the player which closed the GUI
	 */
	public void onDeath(Player player) {
	}

	/**
	 * Returns the name of the GUI
	 * 
	 * @return - the name of the GUI
	 */
	protected abstract String getName();

	/**
	 * Must be unique, use package rules to ensure ie (return
	 * "com.booksaw.guiAPI.maingui") To ensure there is no conflicts with other
	 * plugins CaSe SeNsItIve
	 * 
	 * @return - A unique reference
	 */
	protected abstract String getReference();

	/**
	 * This is used to setup as much of the GUI as possible (runs on server load)
	 * This includes setting up the layout and adding any items which do not change
	 * per user
	 * 
	 * Examples of things which may not be added here are: - Items which are
	 * permission specific - Items which require to be created at the time (say they
	 * display if a player is online)
	 * 
	 * @param items -- this is where items are put -- examples of how to use this
	 *              class is all Bukkit.Inventory commands for adding items The
	 *              order things are added is important. and there is a risk of
	 *              ItemCollection.setItem overriding items
	 */
	protected abstract void initialise(ItemCollection items);

	/**
	 * In this method, put everything which is run right at the end (Everything
	 * which requires the player to run
	 * 
	 * @param p       the player who is going to open the inventory
	 * @param items   the ItemCollection of the inventory
	 * @param details any provided details
	 * @return the item collection
	 */
	protected abstract void buildGui(Player p, ItemCollection items, String[] details);

	/**
	 * Run before initialise() Only used if you want to create a extendable class
	 * for a GUI (in terms of JFrames an example would be boxLayout / flowLayout)
	 * 
	 * @param items - The items for the inventory
	 */
	protected void layout(ItemCollection items) {

	}
}
// TODO STUFF LIKE setName()