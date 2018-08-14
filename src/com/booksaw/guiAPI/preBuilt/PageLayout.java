package com.booksaw.guiAPI.preBuilt;

import com.booksaw.guiAPI.API.DefaultItem;
import com.booksaw.guiAPI.API.Gui;
import com.booksaw.guiAPI.API.items.ItemCollection;

/**
 * Basic double chest layout with back and forward button at the bottom. TO USE
 * ADD AN ItemList which starts at 0 and stops at 44.
 * 
 * @author booksaw
 *
 */
public abstract class PageLayout extends Gui {

	@Override
	protected void layout(ItemCollection items) {

		items.addItem(DefaultItem.BACK.getItem(), 48);
		items.addItem(DefaultItem.FOWARD.getItem(), 50);

	}

}
