package com.booksaw.guiAPI.API.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.booksaw.guiAPI.API.items.itemActions.GuiEvent;
import com.booksaw.guiAPI.API.items.itemActions.ItemAction;

import net.md_5.bungee.api.ChatColor;

/**
 * Used to store items for the plugin
 * 
 * @author booksaw
 *
 */
public class GuiItem implements Cloneable {

	/**
	 * The item
	 */
	private ItemStack is;

	/**
	 * The Action manager (manages what happens when the item is clicked)
	 */
	private List<ItemAction> actions;

	/**
	 * Command that can be accessed in ItemActions
	 */
	String actionCommand;

	/**
	 * Creates a new item which can be used in GuiAPI
	 * 
	 * @param is - the item it should be created with
	 */
	public GuiItem(ItemStack is) {
		this.is = is;
		actions = new ArrayList<>();

	}

	/**
	 * Creates an item that can be used in this plugin
	 * 
	 * @param is     - The itemStack that this will be created around
	 * @param action - the ItemAction that will be run when the player selects the
	 *               item.
	 */
	public GuiItem(ItemStack is, ItemAction action) {
		this.is = is;

		actions = new ArrayList<>();
		actions.add(action);

	}

	/**
	 * Creates an item that can be used in this plugin
	 * 
	 * @param is      - the itemStack that this will be created around
	 * @param actions - the ItemAction that will be run when the player selects the
	 *                item.
	 */
	public GuiItem(ItemStack is, List<ItemAction> actions) {
		this.is = is;

		this.actions = actions;

	}

	/**
	 * The ItemStack
	 * 
	 * @return the item which this object is associated with
	 */
	public ItemStack getItem() {
		return is;
	}

	/**
	 * Sets the associated item
	 * 
	 * @param is - The item which should be set
	 */
	public void setItem(ItemStack is) {
		this.is = is;
	}

	/**
	 * used to create a clone of this class so it can be updated without affecting
	 * the other copy (so per player stuff can be done)
	 */
	@Override
	public GuiItem clone() {
		GuiItem item;
		try {
			item = (GuiItem) super.clone();

			item.cloneInner();
			return item;
		} catch (CloneNotSupportedException e) {
			System.err.println(ChatColor.RED
					+ "COULD NOT CREATE CLONE OF THE GUIITEM, SO WILL RETURN DEFAULT (CAN HAVE PROBLEMS ON OTHER PLUGINS)");
			e.printStackTrace();
			return this;
		}
	}

	/**
	 * Clones all variables (so they are also different in the next version)
	 */
	@SuppressWarnings("unchecked")
	private void cloneInner() {
		is = is.clone();
		actions = (ArrayList<ItemAction>) ((ArrayList<ItemAction>) actions).clone();
	}

	/**
	 * Runs all the listeners
	 * 
	 * @param e - the event to tell the listeners
	 */
	public void runAction(GuiEvent e) {
		for (ItemAction atc : actions) {
			atc.onEvent(e);
		}
	}

	/**
	 * Adds an action that will be run on click
	 * 
	 * @param action - the action to be run
	 */
	public void addAction(ItemAction action) {
		actions.add(action);
	}

	/**
	 * Removes an action so it will no longer be run on click
	 * 
	 * @param action - the action
	 */
	public void removeAction(ItemAction action) {
		actions.remove(action);
	}

	/**
	 * Returns a list of actions
	 * 
	 * @return - List of all actions
	 */
	public List<ItemAction> getActions() {
		return actions;
	}

	/**
	 * Sets the list of actions
	 * 
	 * @param actions - List of all actions
	 */
	public void setActions(List<ItemAction> actions) {
		this.actions = actions;
	}

	/**
	 * Sets the action command that will be accessible in ItemEvents
	 * 
	 * @param command - The command
	 */
	public void setActionCommand(String command) {
		actionCommand = command;
	}

	/**
	 * 
	 * @return - the action command
	 */
	public String getActionCommand() {
		return actionCommand;
	}
}
