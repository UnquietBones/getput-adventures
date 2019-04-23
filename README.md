# GetPut Adventures - Chapter 1 (Java 11)

## This Branch

This branch is for adding testing out the chained actions in Room 2. Notes are under the testing log.

---
## Master ReadMe

As I learn (relearn) each language I'm creating a small text-based adventure game engine to hash out file I/O, user interfaces, and database queries. The first iterations will be horrible and clunky, but it should get better as I go on.

Feedback on better ways to do this is -as always- appreciated! :)

## Basic Game Information

This game engine is intended to let the player move through the world as built in the text files. Changing the text files will change the game world, allowing you to build whatever you like! One of the future enhancements will be to make a game designer so folks don't have to actually write the text files (and it will eliminate typos).

Currently the only way to 'win' the game is to enter the room designated as the win room. There is a loop counter build in to make testing easier, so in theory hitting that it the 'lose' condition. There is no way to die. ...yet. ;)

### Text Files

These will hold my story... once I finish writing it!
- actions.txt (Action ID/Action Name/Action Description/Action Command/Usage)
- items.txt (Item ID/Item Name/Item Description/Action ID/CanPickup)
- rooms.txt (Room ID/Room Name/Description/Items/Actions)

I built a set of four rooms to test every possible combination of the Actions that I could think of.
- testactions.txt
- testitems.txt
- testrooms.txt

### Basic Libraries (aka info from the text files)

- **GameMap** - string array of all the Rooms Information (and their Items/Actions)
- **ItemLibrary** - string array of all the Item Information
- **ActionLibrary** - string array of all the Action Information

### Player Inventory

- **Items[6]** - array of six possible Items (objects, not foreign keys)

### Rooms

- **ID** - short nickname/primary key
- **Name** - title of the room
- **Description** - short description of the room
- **Items[6]** - array of possible Items (objects, not foreign keys)
- **Actions[12]** - array of possible Actions (objects, not foreign keys)

### Items

- **ID** - short nickname/primary key
- **Name** - what it says on the tin
- **Description** - short description of the item
- **Action ID** - this is the nickname for the matching action/foreign key
- **Can Pickup** - flag for if something can be put into Player Inventory

### Actions

- **ID** - short nickname/primary key
- **Name** - what it says on the tin
- **Description** - short description of the player doing the action
- **Command** - a '~' delimited string of actions and targets
- **Type** - R - requires a Room, O - requires an Item, RO - requires a Room and an Item

### Command List

- **addRoomItem** - adds an item to the room
- **removeRoomItem** - removes an item from the room
- **addRoomAction** - adds an action to the room
- **removeRoomAction** - removes an action from the room
- **addPlayerItem** - adds an item to the player inventory
- **removePlayerItem** - removes an item from the player inventory
- **addItemAction** - adds an action to the item
- **removeItemAction** - removes an action from the item
- **movePlayer** - moves a player to a room
- **printDescription** - prints action description
