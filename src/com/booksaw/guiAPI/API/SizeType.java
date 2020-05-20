package com.booksaw.guiAPI.API;

/**
 * Used to determine how the size is calculated
 * 
 * @author booksaw
 *
 */
public enum SizeType {
	/**
	 * Used to make the gui as small as possible
	 */
	SMALLEST,

	/**
	 * makes the gui size = Gui.MAXSIZE
	 */
	LARGEST,

	/**
	 * Same as smallest but leaves a blank row at the bottom
	 */
	BLANKROW,

	/**
	 * Allows the dev to specify a custom size for the gui (Gui.size)
	 */
	CUSTOM;

	/**
	 * Uses the last item to calculate the size of the inventory
	 * 
	 * @param lastItem - the location of the final item in the GUI
	 * @return - the size of the GUI
	 */
	public int getSize(int lastItem, int size) {
		if (this == SizeType.CUSTOM) {
			return size;
		} else if (this == SizeType.LARGEST) {
			return Gui.MAXSIZE;
		} else {
			size = 9;
			while (true) {

				if (size > Gui.MAXSIZE) {
					size = Gui.MAXSIZE;
					break;
				}

				if (lastItem <= size) {
					break;
				}

				size += 9;
			}

			if (this == SizeType.BLANKROW) {
				size = (size + 9 > Gui.MAXSIZE) ? size : size + 9;
			}

			return size;

		}
	}

}
