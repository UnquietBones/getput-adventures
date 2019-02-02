# getput-adventures

As I learn (relearn) each language I'm creating a small text-based adventure game to hash out file I/O, user interfaces, and database queries. The first iterations will be horrible and clunky, but it should get better as I go on.

Feedback on better ways to do this is -as always- appreciated! :)

Chapter 1 - Java 11 - This Old House (of Horrors?)
=======================

This is the first part of the adventure game. The player is exploring a small wooden house deep in the woods. For... reasons.

Basic Game Information
-------------------------------------------------

This game is intended to let the player move through the world as built in the text files. Changing the text files will change the game world, allowing you to build whatever you like! (As long as you follow the game rules.)

There is currently no way to 'win' the game, but I will be adding a 'Final Exit' room that will trigger a win condition. There is also no way to lose the game, since there are no monsters or ways to die. ...yet. ;)

Basic Libraries (aka info from the text files)
-------------------------------------------------
- GameMap - string array of all the Rooms Information (and their Items/Actions)
- ItemLibrary - string array of all the Item Information
- ActionLibrary - string array of all the Action Information

Player Inventory
-------------------
- Items[6] - array of six possible Items (objects, not foreign keys)

Rooms
--------
- ID - short nickname/primary key
- Name - title of the room
- Description - short description of the room
- Items[6] - array of possible Items (objects, not foreign keys)
- Actions[12] - array of possible Actions (objects, not foreign keys)

Items
-------
- ID - short nickname/primary key
- Name - what it says on the tin
- Description - short description of the item
- Action ID - this is the nickname for the matching action/foreign key
- Can Pickup - flag for if something can be put into Player Inventory

Actions
---------
- ID - short nickname/primary key
- Name - what it says on the tin
- Description - short description of the player doing the action
- Command - a '~' delimiated string of actions and targets
- Type - R - requires a Room, O - requires an Item, RO - requires a Room and an Item

Command List
-------------
- addRoomItem - adds an item to the room
- removeRoomItem - removes an item from the room
- addRoomAction - adds an action to the room
- removeRoomAction - removes an action from the room
- addPlayerItem - adds an item to the player inventory
- removePlayerItem - removes an item from the player inventory
- addItemAction - adds an action to the item
- removeItemAction - removes an action from the item
- movePlayer - moves a player to a room
