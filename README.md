# Gui-API

This API is used to create GUIs quickly in Minecraft, spigot. 

## Getting Started

Download GUI-API from spigot: and install on a Minecraft 1.12 server.
Next you can start coding with it by building a path to the jar file, just like you do with spigot-(version).jar

### Prerequisites

Only requirements are spigot, tested versions include: 1.12.

### Creating a GUI

#### The GUI class

Extend the GUI class

```java
public class foo extends GUI {

    // required methods for creating a GUI:
    
    @Override
	protected String getName() {
		// Return the name of the GUI (what will be used as the title of the GUI)
		return "foo";
	}

	@Override
	protected String getReference() {
		/* The unique reference used for backend processing. It is recommended that this is the package 
        and class that extends GUI so it is unique. */
		return "com.booksaw.guiExample.Foo";
	}

	@Override
	protected void initialise(ItemCollection items) {
		/* Use the ItemCollection to add items which do not change per player,
        This is also a good time to setup items which will vary slightly per player and store them yourself.
        for the item collection to add an item use items.addItem(GuiItem[see below]); - see documentation for methods.*/
		
	}

	@Override
	protected void buildGui(Player p, ItemCollection items) {
		/* This is run directly before the player has the GUI opened, so make any finalising changes here
        which are required to be run at the time the GUI is opened. */
		
	}


}
```

#### Informing the manager about the GUI

Next, at some point in your code, it is recommended to be run in your main class in the onEnable() method. Add the following code to register the GUI.

```java
GuiManager.registerGui(new Foo());
```

#### Opening the GUI

Weather this is run from another GUI, or this is run on a command, to open a GUI use this code: 
```java
// A method of getting the GUI if you don't have it stored locally
Gui gui = GuiManager.getGui(reference);

// used to display the GUI to the player
gui.displayGui(player);
```

### Using Items

For an item to be added to a GUI it must be included in a GuiItem Object, this is done simply by doing 

```java
GuiItem item = new GuiItem(ItemStack);
```

#### Using actions

Actions are a system of making events which run when the item is clicked upon, to make a new action implement the ItemAction class:
There are some template actions which can be used to quickly setup some items.

```java
public class FooAction implements ItemAction {

    @Override
	public void onEvent(GuiEvent e) {
		// Run when the item is clicked on.
		
	}
}
```

#### Adding actions to items

To add an action to an item simply do

```java
item(GuiItem).addAction(new FooAction());
```

#### Saving and Loading items from the config

In case items are required to be saved to the config, the API makes this easy as it can manage the loading and saving of items from the config: 

To load do

```java
ItemAPI.load(FileConfiguration config, String reference);
```

Where reference is the base location that the item is stored in the config, for example: "items.back"
To save items do:

```java
ItemAPI.save(ItemStack item, FileConfiguration config, String reference);
```


### Chat Events

There is a single chat event setup, this is used to get a text response from the user.
There are 2 ways of using a chat Event: 

#### Adding a chat event to an item

There is a pre-made Action called LongChatEvent which can be added to items, so it is triggered when that item is clicked:
To add this event, do:

```java
item(GuiItem).addAction(new LongChatEvent(String message, ChatEventListener listener[see below]));
```
Where message is the message prompt that will be sent to the user at the start of the Chat Event.
And the listener is explained below.

#### Creating a custom chat event

This is if you want to run the chat event, but not having the trigger as the user clicking an item, IE the user running a command.

```java
ChatEventLong chatEventLong= new ChatEventLong(Player, String message, ChatEventListener listener);
chatEventLong.runEvent();
```
Where message is the message prompt that will be sent to the user at the start of the Chat Event.
And the listener is explained below

#### ChatEventListener

To create a listener, implement the ChatEventListener class

```java
public class chatListener implements ChatEventListener{

    @Override
	public boolean runEvent(ChatEvent e) {
        // This is run if the player speaks in the chat
		// If the event is run, use documentation to see more about ChatEvent class
        /*
        Return true if the entered message is valid
        return false if the entered message is invalid.
        */
        
		return false;
	}

	@Override
	public void cancel(ChatEvent e) {
		/* 
        This is run if the player cancels the chat event
        This will be more useful in future. 
        */
		
	}

}
```

### Pre-Built GUIs

There are several pre-built GUIs, (see the package "com.booksaw.guiAPI.preBuilt)
The way to use them is to extend the pre-built GUI class instead if the GUI class and they will change the layout before Gui.initalise() is run.

#### The classes

At present there are 2 prebuilt layouts: 
* BorderLayout which adds a border of the blank item from the GUI API config around the edge of the border
* PageLayout which adds a forward and back button at the bottom of the GUI.

### pre-existing actions

To make item creation much quicker, there is some default actions which can be added to items:
* CancelEvent - sets the InventoryInteractEvent to be cancelled
* CloseEvent - Closes the users Inventory
* MessageEvent - sends the user a message
