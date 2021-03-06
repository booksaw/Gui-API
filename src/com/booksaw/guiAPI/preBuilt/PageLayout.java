package com.booksaw.guiAPI.preBuilt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.booksaw.guiAPI.GuiUseful;
import com.booksaw.guiAPI.API.DefaultItem;
import com.booksaw.guiAPI.API.Gui;
import com.booksaw.guiAPI.API.SizeType;
import com.booksaw.guiAPI.API.items.GuiItem;
import com.booksaw.guiAPI.API.items.ItemCollection;
import com.booksaw.guiAPI.API.items.itemActions.GuiEvent;
import com.booksaw.guiAPI.API.items.itemActions.ItemAction;

/**
 * Basic double chest layout with back and forward button at the bottom. TO USE
 * ADD AN ItemList which starts at 0 and stops at 44.
 * 
 * @author booksaw
 *
 */
public abstract class PageLayout extends Gui {

	/**
	 * Used to track what page the player is viewing
	 */
	private HashMap<Player, Integer> pages;

	List<GuiItem> contents;
	protected GuiItem[] bottomBar = null;
	private GuiItem foward, back;

	@Override
	protected final void layout(ItemCollection items) {
		pages = new HashMap<>();
		sizeType = SizeType.CUSTOM;
		contents = new ArrayList<>();
		bottomBar = new GuiItem[9];

		foward = getForward();
		back = getBack();

		configureBottomBar(bottomBar);
		addItems(contents);
	}

	@Override
	protected final void buildGui(Player p, ItemCollection items, String[] details) {
		List<GuiItem> contents = new ArrayList<>(this.contents);
		addItems(p, contents, details);
		GuiItem[] bottomBar = this.bottomBar.clone();
		configureBottomBar(p, bottomBar, details);
		size = GuiUseful.getSize(contents.size());
		if (size < 0 || size >= 54) {
			size = 54;
		} else {
			size += 9;
		}
		int contentSize = size - 9;
		Integer pageInt = pages.get(p);

		int page;
		if (pageInt == null || pageInt < 0) {
			page = 0;
		} else {
			page = pageInt;
		}
		if (page > 0) {
			GuiItem back = this.back.clone();
			back.addAction(new EventHandle(details));
			back.setActionCommand("back");
			bottomBar[3] = back;
		}
		if (contents.size() > (page + 1) * contentSize) {
			GuiItem foward = this.foward.clone();
			foward.addAction(new EventHandle(details));
			foward.setActionCommand("forward");
			bottomBar[5] = foward;
		}

		// adding the bottom bar
		for (int i = 0; i < 9; i++) {
			if (bottomBar[i] != null) {
				items.addItem(bottomBar[i], contentSize + i);
			}
		}

		// getting the page that the player is viewing

		// checking the page is not out of the bounds
		if (page * contentSize > contents.size()) {
			// moving player to the last page using integer division
			page = contents.size() / contentSize;
		}

		// adding the correct page to the display
		for (int i = page * contentSize; i < (page + 1) * contentSize && i < contents.size(); i++) {
			items.addItem(contents.get(i), i % contentSize);
		}
		pages.put(p, page);

	}

	/**
	 * Used to add any items which will not change over time, this is run when the
	 * GUI is first created
	 * 
	 * @param contents
	 */
	public abstract void addItems(List<GuiItem> contents);

	/**
	 * This method is used to add all items to the list of items which can be
	 * displayed at run time.
	 * 
	 * @param p        the player who is opening the GUI
	 * @param contents the contents of the GUI
	 * @param details  the details provided about the GUI
	 */
	public abstract void addItems(Player p, List<GuiItem> contents, String[] details);

	/**
	 * This is run when the GUI is first made and can be used to change the bottom
	 * bar however you want (if changing slots 3 and 5 the back and foward button
	 * will be removed)
	 * 
	 * @param bottomBar the bottom bar of the page
	 */
	public abstract void configureBottomBar(GuiItem[] bottomBar);

	/**
	 * This is run when the GUI is about to be displayed and can be used to change
	 * the bottom bar however you want (if changing slots 3 and 5 the back and
	 * Forward button will be removed)
	 * 
	 * @param bottomBar the bottom bar of the page
	 * @param player    the player the GUI is about to be displayed to
	 * @param details   the details about this gui
	 */
	public abstract void configureBottomBar(Player player, GuiItem[] bottomBar, String[] details);

	@Override
	protected void initialise(ItemCollection items) {
	}

	@Override
	public void onClose(Player player, InventoryCloseEvent e) {
		pages.remove(player);
	}

	@Override
	public void onLeave(Player player, PlayerQuitEvent e) {
		pages.remove(player);
	}

	@Override
	public void onDeath(Player player) {
		pages.remove(player);
	}

	/**
	 * This is used to handle the foward and back buttons without pageLayout
	 * extending Item action
	 * 
	 * @author nfgg2
	 *
	 */
	private class EventHandle implements ItemAction {

		private final String[] details;

		public EventHandle(String[] details) {
			this.details = details;
		}

		@Override
		public void onEvent(GuiEvent e) {
			int page;
			Integer pageInt;
			e.e.setCancelled(true);
			switch (e.getActionCommand()) {
			case "forward":
				// getting the page that the player is viewing
				pageInt = pages.get((Player) e.getPlayer()) + 1;
				if (pageInt == null || pageInt < 0) {
					page = 0;
				} else {
					page = pageInt;
				}

				pages.put((Player) e.getPlayer(), page);
				displayGui((Player) e.getPlayer(), details);
				break;
			case "back":
				// getting the page that the player is viewing
				pageInt = pages.get((Player) e.getPlayer()) - 1;

				if (pageInt == null || pageInt < 0) {
					page = 0;
				} else {
					page = pageInt;
				}

				pages.put((Player) e.getPlayer(), page);
				displayGui((Player) e.getPlayer(), details);
				break;
			}

		}

	}

	public GuiItem getBack() {
		return DefaultItem.BACK.getItem();
	}

	public GuiItem getForward() {
		return DefaultItem.FORWARD.getItem();
	}
}
