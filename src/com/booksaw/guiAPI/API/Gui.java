package com.booksaw.guiAPI.API;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
	 * The largest an inventory can be (the size of a double chest inventory)
	 */
	public static final int MAXSIZE = 54;

	/**
	 * The generic item collection (not specific for the player)
	 */
	protected ItemCollection items;

	/**
	 * The name of the inventory
	 */
	public String name;

	/**
	 * The unique reference for this GUI (so it does not conflict with other
	 * plugins)
	 */
	public String REFERENCE;

	/**
	 * A variable of the enum SizeType, which is used to specify how the size is
	 * calculated, default is SizeType.SMALLEST
	 */
	public SizeType sizeType;

	/**
	 * Chance this variable if SizeType is set to custom
	 */
	public int size = -1;

	/**
	 * No point extending - only done to setup some GuiAPI stuff before initialise()
	 * is run.
	 */
	public void enable() {
		items = new ItemCollection();
		sizeType = SizeType.SMALLEST;

		name = getName();
		REFERENCE = getReference();
		layout(items);
		initialise(items);

	}

	/**
	 * Use to display the GUI to the player
	 * 
	 * @param p - the player which the gui should be displayed to
	 * @return result - false if the clone does not work - true if the gui was
	 *         displayed
	 */
	public boolean displayGui(Player p) {
		ItemCollection itemsClone;

		try {
			itemsClone = items.clone();
		} catch (CloneNotSupportedException e) {
			return false;
		}

		buildGui(p, itemsClone);

		Inventory i = Bukkit.createInventory(null, sizeType.getSize(items.getLastItem(), size), name);
		itemsClone.buildGui(i);

		GuiManager.addPlayer(p, itemsClone, this);

		p.openInventory(i);
		return true;
	}

	/**
	 * Mainly used back-end when the event is called but could be used to force a
	 * player to click on an item
	 * 
	 * @param p   - the player that ran the event
	 * @param gui - the GUI that they had open (use GuiManager to get)
	 * @param is  - the Item that they clicked on
	 * @param e   - The event (so plugin developers can get all the details they
	 *            want / cancel it)
	 */
	public void click(Player p, Gui gui, ItemStack is, InventoryClickEvent e) {
		try {
			GuiPlayer guip = GuiManager.players.get(p);
			if (guip == null) {
				Bukkit.getLogger().warning(ChatColor.RED + "COULD NOT FIND PLAYER, ALERT BOOKSAW");
				return;
			}
			GuiItem item = guip.items.getItem(is);
			if (item == null) {
				System.out.println("Item is not recognised");
				e.setCancelled(true);
				return;
			}
			item.runAction(new GuiEvent(e, gui, item.getActionCommand()));
		} catch (Exception ex) {
			e.setCancelled(true);
			ex.printStackTrace();
		}
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
	 * @param p     - the player who is going to open the inventory
	 * @param items - the ItemCollection of the inventory
	 * @return the item collection
	 */
	protected abstract void buildGui(Player p, ItemCollection items);

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