package com.booksaw.guiAPI.API.items;

import java.util.ArrayList;
import java.util.List;

import com.booksaw.guiAPI.API.Gui;

/**
 * Used to add items to an Item Collection without giving them locations IE to
 * have a list (for admin work)
 * 
 * @author booksaw
 *
 */
public class ItemList {

	/**
	 * the start location of the list
	 */
	private int startLocation;
	
	/**
	 * The last location that an item can be placed
	 */
	private int finalLocation;

	/**
	 * A list of the items
	 */
	private List<GuiItem> items;

	/**
	 * Creates a new item list where it will start placing items from the first
	 * potential spot
	 */
	public ItemList() {
		startLocation = 0;
		items = new ArrayList<>();
		finalLocation = Gui.MAXSIZE;
	}

	/**
	 * Creates a new item list where it will start placing items at the specified
	 * location
	 * 
	 * @param firstLocation - The location where items will start to be placed.
	 */
	public ItemList(int firstLocation) {
		startLocation = firstLocation;
		items = new ArrayList<>();
		finalLocation = Gui.MAXSIZE;
	}

	/**
	 * Creates a new item list where it will start placing items at the specified
	 * location and stop placing items at the specified location
	 * 
	 * @param firstLocation - the location where items will start to be placed
	 * @param finalLocation - the final possible location for an item to be placed
	 */
	public ItemList(int firstLocation, int finalLocation) {
		startLocation = firstLocation;
		this.finalLocation = finalLocation;
		items = new ArrayList<>();
	}

	/**
	 * Adds the specified item to the item list
	 * 
	 * @param item - the item to be added
	 */
	public void addItem(GuiItem item) {
		items.add(item);
	}

	/**
	 * Used to add a list of items to the already existent item list
	 * 
	 * @param items - The items to be added
	 */
	public void addItems(List<GuiItem> itemList) {
		for (GuiItem item : itemList) {
			items.add(item);
		}
	}

	/**
	 * Removes the specified item from the item list
	 * 
	 * @param item - the item to be removed
	 */
	public void removeItem(GuiItem item) {
		items.remove(item);
	}

	/**
	 * 
	 * @return - The list of items
	 */
	public List<GuiItem> getItems() {
		return items;
	}

	/**
	 * To set what the items are in the item list
	 * 
	 * @param items - The items for this item list
	 */
	public void setItems(List<GuiItem> items) {
		this.items = items;
	}

	/**
	 * 
	 * @return - the first location items will be placed at
	 */
	public int getFirstLocation() {
		return startLocation;
	}

	/**
	 * 
	 * @param firstLocation - the first location items should be placed at
	 */
	public void setFirstLocation(int firstLocation) {
		startLocation = firstLocation;
	}

	/**
	 * 
	 * @return - the last location an item will be placed at
	 */
	public int getFinalLocation() {
		return finalLocation;
	}

	/**
	 * 
	 * @param finalLocation - the last possible location where an Item can be placed
	 */
	public void setFinalLocation(int finalLocation) {
		this.finalLocation = finalLocation;
	}
}
