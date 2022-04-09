/**
 * 
 */
package com.booksaw.guiAPI.gui.items.action;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.inventory.ClickType;

import com.booksaw.guiAPI.gui.events.GuiEvent;
import com.booksaw.guiAPI.gui.items.ItemAction;

/**
 * Used to wrap an item action in a click type selector
 * 
 * @author booksaw
 *
 */
public class ClickTypeWrapperAction implements ItemAction {

	private final ItemAction action;
	private final List<ClickType> types;

	/**
	 * Used to wrap an item action into a click type selector
	 * 
	 * @param action The item action to wrap
	 */
	public ClickTypeWrapperAction(ItemAction action) {
		this.action = action;
		this.types = new ArrayList<>();
	}

	/**
	 * Used to wrap an item action in a click type selector
	 * 
	 * @param action The item action to wrap
	 * @param type   The click type to wrap
	 */
	public ClickTypeWrapperAction(ItemAction action, ClickType... types) {
		this.action = action;

		this.types = new ArrayList<>();

		addClickTypes(types);

	}

	/**
	 * Add another set of click types to this action
	 * 
	 * @param types The new type
	 * @return This instance
	 */
	public ClickTypeWrapperAction addClickTypes(ClickType... types) {

		for (ClickType type : types) {
			this.types.add(type);
		}

		return this;
	}

	/**
	 * Add another click type to this action
	 * 
	 * @param type The new type
	 * @return This instance
	 */
	public ClickTypeWrapperAction addClickType(ClickType type) {
		types.add(type);

		return this;
	}

	@Override
	public void onEvent(GuiEvent event) {
		if (!types.contains(event.getEvent().getClick())) {
			return;
		}

		action.onEvent(event);

	}

}
