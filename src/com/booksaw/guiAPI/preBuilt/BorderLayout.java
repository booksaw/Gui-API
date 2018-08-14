package com.booksaw.guiAPI.preBuilt;

import com.booksaw.guiAPI.API.Gui;
import com.booksaw.guiAPI.API.SizeType;
import com.booksaw.guiAPI.API.items.GuiItem;
import com.booksaw.guiAPI.API.items.ItemCollection;
import com.booksaw.guiAPI.API.items.ItemCollection.Direction;

/**
 * Creates a border around a double chest sized layout with a border around the
 * side
 * 
 * @author James
 *
 */
public abstract class BorderLayout extends Gui {

	/**
	 * The item to fill it with
	 */
	GuiItem borderItem;
	/**
	 * booleans to store which parts of the border are being used
	 */
	boolean topAndBottom, sides;

	/**
	 * Simple border around the entire thing
	 * 
	 * @param borderItem - The item the border should be made of
	 */
	public BorderLayout(GuiItem borderItem) {
		this.borderItem = borderItem;
		topAndBottom = true;
		sides = true;
		size = MAXSIZE;
	}

	/**
	 * Border but each segment can be determined if it is displayed
	 * 
	 * @param borderItem   - the item to be used for the border
	 * @param topAndBottom - If the top and bottom of the border display
	 * @param sides        - If the sides of the border display
	 */
	public BorderLayout(GuiItem borderItem, boolean topAndBottom, boolean sides) {
		this.borderItem = borderItem;
		this.topAndBottom = topAndBottom;
		this.sides = sides;
		size = MAXSIZE;
	}

	/**
	 * Border but each segment can be determined if it is displayed and the size of
	 * the gui can be specified (instead of just being max size)
	 * 
	 * @param borderItem   - The itme to be used for the border
	 * @param topAndBottom - if the top and bottom of the border display
	 * @param sides        - if the sides of the border display
	 * @param size         - the size of the gui (must be multiple of 9)
	 */
	public BorderLayout(GuiItem borderItem, boolean topAndBottom, boolean sides, int size) {
		this.borderItem = borderItem;
		this.topAndBottom = topAndBottom;
		this.sides = sides;
		this.size = size;
		sizeType = SizeType.CUSTOM;
	}

	@Override
	protected void layout(ItemCollection items) {
		if (sides) {
			items.fill(borderItem, 0, 45, Direction.DOWN);
			items.fill(borderItem, 8, 53, Direction.DOWN);
		}
		if (topAndBottom) {
			items.fill(borderItem, 0, 9, Direction.ACROSS);
			items.fill(borderItem, 45, 53, Direction.ACROSS);
		}
	}

}
