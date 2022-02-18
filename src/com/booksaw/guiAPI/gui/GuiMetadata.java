/**
 * 
 */
package com.booksaw.guiAPI.gui;

import org.bukkit.event.inventory.InventoryType;

/**
 * @author booksaw
 *
 *         Used to store all metadata about a GUI
 */
public class GuiMetadata {

	/**
	 * The name of the GUI
	 */
	private String guiName;

	/**
	 * The size of the GUI
	 */
	private int size;
	
	private InventoryType invType;

	/**
	 * Used to create a new GuiMetadata with all metadata
	 * 
	 * @param guiName The name of the GUI 
	 * @param size The size of the GUI
	 * @param type The inventory type of the GUI
	 */
	public GuiMetadata(String guiName, int size, InventoryType type) {
		this.guiName = guiName;
		setSize(size);
	}
	
	public GuiMetadata(String guiNane, int size) {
		this(guiNane, size, InventoryType.CHEST);
	}

	/**
	 * Used to create a new GuiMetadata with the specified name
	 * 
	 * @param guiName The name of the GUI
	 */
	public GuiMetadata(String guiName) {
		this(guiName, 9);
	}
	
	/**
	 * Used to create a new blank GuiMetadata 
	 */
	public GuiMetadata() {
		this("");
	}

	public String getGuiName() {
		return guiName;
	}

	public void setGuiName(String guiName) {
		this.guiName = guiName;
	}

	public int getSize() {
		return size;
	}

	/**
	 * Used to set the number of slots within the GUI. Must be a multiple of 9
	 * 
	 * @param size the number of slots within the GUI
	 */
	public void setSize(int size) {

		if (size % 9 != 0) {
			throw new IllegalArgumentException("The size of a GUI must be a multiple of 9");
		}

		this.size = size;
	}

	public InventoryType getInvType() {
		return invType;
	}

	public void setInvType(InventoryType invType) {
		this.invType = invType;
	}
	
	

}
