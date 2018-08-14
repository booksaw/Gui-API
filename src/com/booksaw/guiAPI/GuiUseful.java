package com.booksaw.guiAPI;

public class GuiUseful {
	/**
	 * used to get the smallest size of an inventory
	 * 
	 * @param count - this is how many Inventory slots will be filled (this assumes
	 *              that everything is one after the next)
	 * @return returns the size of inventory required, will return -1 if you require
	 *         more slots than a double chest
	 */
	public static int getSize(int count) {

		int size = 9;
		while (true) {

			if (size > 54) {
				return -1;
			}

			if (count <= size) {
				return size;
			}

			size += 9;
		}
	}
}
