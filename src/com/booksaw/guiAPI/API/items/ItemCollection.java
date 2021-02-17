package com.booksaw.guiAPI.API.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.booksaw.guiAPI.API.Gui;

/**
 * Used as the Inventory would be, main use is manipulating the items and their
 * locations so when the time comes to display the Gui to the player, the items
 * are already configured.
 * 
 * @author booksaw
 *
 */
public class ItemCollection implements Cloneable {

	/**
	 * The name of the gui
	 */
	private String name;

	/**
	 * The items in this collection
	 */
	private HashMap<Integer, GuiItem> items = new HashMap<>();;

	/**
	 * stores added item lists
	 */
	private List<ItemList> itemLists = new ArrayList<>();

	/**
	 * @param list - the list to be added
	 */
	public void addItemList(ItemList list) {
		// used for finding the selected item later on
		itemLists.add(list);

		// used for finding the first possible location for the item
		int location = list.getFirstLocation();

		// adding all the items
		for (GuiItem item : list.getItems()) {
			// first empty space after location.
			while (items.containsKey(location))
				location++;

			// if the item will be placed out of the gui, there is no point adding it.
			if (location >= Gui.MAXSIZE) {
				break;
			}

			// placing the item
			items.put(location, item);

			// moving on to the next location
			location++;
		}

	}

	/**
	 * Adds an item at the specified location
	 * 
	 * @param item     - the item to be added
	 * @param location - the location the item should be added
	 */
	public void addItem(GuiItem item, int location) {
		if (items.containsKey(location)) {
			items.remove(location);
		}
		items.put(location, item);
	}

	/**
	 * Used to add an item at the specified location
	 * 
	 * @param item the item to add to the gui
	 * @param x    the x location of the item (starting at 0, 0 being the top left)
	 * @param y    the y location of the item
	 */
	public void addItem(GuiItem item, int x, int y) {
		// item not in gui, no need in adding
		if (x > 8) {
			return;
		}
		addItem(item, (y * 9) + x);
	}

	/**
	 * Used to fill an area with a specified item
	 * 
	 * @param item   the item to fill the area with
	 * @param x      the x location of the item (starting at 0, 0 being the top
	 *               left)
	 * @param y      the y location of the item
	 * @param width  the width of the area to be filled
	 * @param height the height of the area to be filled
	 */
	public void fillItem(GuiItem item, int x, int y, int width, int height) {
		for (int i = x; i < width + x; i++) {
			for (int j = y; j < height + y; j++) {
				addItem(item, i, j);
			}
		}
	}

	/**
	 * Used to add an item in the first available slot
	 * 
	 * @param item - the item that should be added
	 */
	public void addItem(GuiItem item) {
		int location = 0;
		// first empty space after location.
		while (items.containsKey(location))
			location++;

		// if the item will be placed out of the gui, there is no point adding it.
		if (location >= Gui.MAXSIZE) {
			return;
		}

		// placing the item
		items.put(location, item);

	}

	/**
	 * Removes the specified item from the collection
	 * 
	 * @param item - the item that should be removed
	 */
	public void removeItem(GuiItem item) {
		items.remove(getLocation(item));
	}

	/**
	 * Removes the item at the specified location
	 * 
	 * @param location - The location of the item that should be removed
	 */
	public void removeItem(int location) {
		items.remove(location);
	}

	/**
	 * Returns the item which is in that location
	 * 
	 * @param location - the location to look for the item
	 * @return - item which is in the location
	 */
	public GuiItem getItem(int location) {
		return items.get(location);
	}

	/**
	 * Used to find the location an item is at (if the same item is used multiple
	 * times, it will return the first)
	 * 
	 * @param item - the item that that should be found
	 * @return - the location that that item is at, (-1) is returned if the item is
	 *         not found
	 */
	public int getLocation(GuiItem item) {
		// looping through all items
		for (Entry<Integer, GuiItem> tempItem : items.entrySet()) {
			// if it is the correct item
			if (item == tempItem.getValue()) {
				return tempItem.getKey();
			}
		}
		return -1;

	}

	/**
	 * DO NOT TOUCH -- used to create a clone of this class so it can be updated
	 * without affecting the other copy (so per player stuff can be done)
	 */
	@Override
	public ItemCollection clone() throws CloneNotSupportedException {
		ItemCollection m = (ItemCollection) super.clone();
		m.cloned();
		return m;
	}

	/**
	 * Used as part of the clone() method to clone all varibles
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void cloned() {
		items = (HashMap<Integer, GuiItem>) items.clone();
		itemLists = (List<ItemList>) ((ArrayList) itemLists).clone();
	}

	/**
	 * Used to put the items in the Inventory
	 * 
	 * @param i - the inventory
	 * @return - the inventory with items
	 */
	public Inventory buildGui(Inventory i) {

		for (Entry<Integer, GuiItem> item : items.entrySet()) {
			i.setItem(item.getKey(), item.getValue().getItem());
		}

		return i;
	}

	/**
	 * Finds the last item (for getting the size)
	 * 
	 * @return - returns the last item in the Gui
	 */
	public int getLastItem() {
		int last = 0;
		for (int location : items.keySet()) {
			if (last < location)
				last = location;
		}
		return last;
	}

	/**
	 * Gets the item from that itemStack
	 * 
	 * @param is - The itemstack for the item
	 * @return - the object of that Itemstack, null if itemstack does not exist in
	 *         collection
	 */
	public GuiItem getItem(ItemStack is) {
		for (Entry<Integer, GuiItem> temp : items.entrySet()) {
			if (temp.getValue().getItem().equals(is)) {
				return temp.getValue();
			}
		}
		return null;
	}

	/**
	 * An enum for directions for the fill() method
	 * 
	 * @author booksaw
	 *
	 */
	public enum Direction {
		DOWN,
		/**
		 * Will fill between the two columns downwards -- if they are not on same column
		 * will move onto next until it is found
		 */
		ACROSS/** Fills across (reaches end of row, moves to next row) */

	}

	/**
	 * Used to fill from the first location in the inventory to the specified
	 * location
	 * 
	 * @param is  - The item you want to fill it with
	 * @param end - The end location (this slot will be the final placement)
	 */
	public void fill(GuiItem is, int end) {
		fill(is, 0, end, Direction.ACROSS);
	}

	/**
	 * Used to fill an area from any place to any place in the inventory
	 * 
	 * @param is    - The item you want to fill it with
	 * @param start - The location of the first placement
	 * @param end   - The location of the final placement
	 */
	public void fill(GuiItem is, int start, int end, Direction d) {
		int add = (d == Direction.ACROSS) ? 1 : 9;

		for (int i = start; i <= end; i += add) {
			if (i >= Gui.MAXSIZE) {
				if (d == Direction.ACROSS) {
					i = 0;
				} else {
					i = 9 - (Gui.MAXSIZE - (i - 9));
					if (i > 9) {
						i -= 9;
					}
				}
			}

			addItem(is, i);
		}
	}

	/**
	 * This method is used to remove all items from this collection
	 */
	public void clearAll() {
		items.clear();
	}

	/**
	 * The name of the gui that is going to be created
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Used to change the name of the gui (only use in buildGui), this name is reset
	 * at the end of initalise
	 * 
	 * @param name The name of the gui relating to this player
	 */
	public void setName(String name) {
		this.name = name;
	}

	public HashMap<Integer, GuiItem> getItems() {
		return items;
	}

}
