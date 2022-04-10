package com.booksaw.guiAPI.gui.items;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author booksaw
 *
 */
public class ItemCollection implements Cloneable {

	/**
	 * The items in this collection
	 */
	private HashMap<Integer, GuiItem> items = new HashMap<>();

	private boolean lock = false;

	/**
	 * Adds an item at the specified location
	 * 
	 * @param item     - the item to be added
	 * @param location - the location the item should be added
	 */
	public void addItem(GuiItem item, int location) {
		verifyLock();
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
		verifyLock();
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
		verifyLock();
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
		verifyLock();
		int location = 0;
		// first empty space after location.
		while (items.containsKey(location))
			location++;

		// placing the item
		items.put(location, item);

	}

	/**
	 * Removes the specified item from the collection
	 * 
	 * @param item - the item that should be removed
	 */
	public void removeItem(GuiItem item) {
		verifyLock();
		items.remove(getLocation(item));
	}

	/**
	 * Removes the item at the specified location
	 * 
	 * @param location - The location of the item that should be removed
	 */
	public void removeItem(int location) {
		verifyLock();
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
	@SuppressWarnings({ "unchecked" })
	private void cloned() {
		items = (HashMap<Integer, GuiItem>) items.clone();
	}

	/**
	 * Used to put the items in the Inventory
	 * 
	 * @param i - the inventory
	 * @return - the inventory with items
	 */
	public Inventory buildGui(Inventory i) {

		for (Entry<Integer, GuiItem> item : items.entrySet()) {
			if (item.getKey() < i.getSize()) {
				i.setItem(item.getKey(), item.getValue().getItem());
			}
		}

		if (emptyFillItem != null) {
			for (int j = 0; j < i.getSize(); j++) {
				if (!items.containsKey(j)) {
					i.setItem(j, emptyFillItem.getItem());
					items.put(j, emptyFillItem);
				}
			}
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
	 * This method is used to remove all items from this collection
	 */
	public void clearAll() {
		verifyLock();
		items.clear();
	}

	private void verifyLock() {
		if (isLocked()) {
			throw new IllegalStateException("The item collection is locked, it cannot be modified");
		}
	}

	public Map<Integer, GuiItem> getItems() {
		return items;
	}

	public boolean isLocked() {
		return lock;
	}

	public void setLocked(boolean lock) {
		this.lock = lock;
	}

	private GuiItem emptyFillItem = null;

	/**
	 * Sets the item used to be placed in any empty slot
	 * 
	 * @param emptyFillItem The guiItem to fill blank spaces
	 */
	public void setEmptyFillItem(GuiItem emptyFillItem) {
		verifyLock();
		this.emptyFillItem = emptyFillItem;
	}

	/**
	 * @return A clone of the empty fill item
	 */
	public GuiItem getEmptyFillItem() {
		return emptyFillItem.clone();
	}

}
