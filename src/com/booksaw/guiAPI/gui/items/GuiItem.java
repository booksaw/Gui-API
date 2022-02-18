package com.booksaw.guiAPI.gui.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.booksaw.guiAPI.gui.events.GuiEvent;

/**
 * @author booksaw
 *
 */
public class GuiItem implements Cloneable {

	/**
	 * The item
	 */
	private ItemStack is;

	/**
	 * Command that can be accessed in ItemActions
	 */
	String actionCommand;

	/**
	 * Creates a new item which can be used in GuiAPI
	 * 
	 * @param is the item it should be created with
	 */
	public GuiItem(ItemStack is) {
		this.is = is;

	}

	public GuiItem(ItemStack is, ItemAction... actions) {
		this(is);
		addActions(actions);
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
	 * @param is The item which should be set
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
	private void cloneInner() {
		is = is.clone();
		actions = new ArrayList<>(actions);
	}

	/**
	 * Sets the action command that will be accessible in ItemEvents
	 * 
	 * @param command The command
	 */
	public void setActionCommand(String command) {
		actionCommand = command;
	}

	/**
	 * 
	 * @return the action command
	 */
	public String getActionCommand() {
		return actionCommand;
	}

	/**
	 * All ItemActions associated with this GuiItem
	 */
	private List<ItemAction> actions = new ArrayList<>();

	/**
	 * Add an item action to the list
	 * 
	 * @param action The ItemAction to add
	 */
	public void addAction(ItemAction action) {

		if (action == null) {
			throw new IllegalArgumentException("Cannot provide a null action");
		}

		actions.add(action);
	}

	/**
	 * Used to add a set of item actions to this GuiItem
	 * 
	 * @param actions The actions to add
	 */
	public void addActions(ItemAction... actions) {
		for (ItemAction action : actions) {
			addAction(action);
		}
	}

	/**
	 * Remove an ItemAction from the list
	 * 
	 * @param action The ItemAction to remove
	 */
	public void removeAction(ItemAction action) {
		actions.remove(action);
	}

	/**
	 * Used to execute all ItemActions associated with this GuiItem
	 * 
	 * @param event the event that needs executing
	 */
	public void execute(GuiEvent event) {
		for (ItemAction action : actions) {
			action.onEvent(event);
		}
	}

}
