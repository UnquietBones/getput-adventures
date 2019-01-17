# getput-adventures

As I learn (relearn) each language I'm creating a small text-based adventure game to hash out file I/O, user interfaces, and database queries. The first iterations will be horrible and clunky, but it should get better as I go on.

Feedback on better ways to do this is (as always) appreciated! :)

It woooooooooooooooooooooooooooooorks! :D

You can
- Pick up an item from a room
- Put down an item in a room
- Do an action (if you have the item in inventory and the action exists in the room)
- Use an exit
- Look (reprints the room)
- Exit (quits the game)

Chapter 1 - Java 11 - This Old House (of Horrors?)  [in progress]

Basic Game Information
=======================

This game is intended to let the player move through the world as built in the text files. Changing the text files will change the game world, allowing you to build whatever you like! (As long as you follow the game rules.)

There is currently no way to 'win' the game, but I will be adding a 'Final Exit' room that will trigger a win condition. There is also no way to lose the game, since there are no monsters or ways to die. ...yet. ;)

Basic Libraries (aka info from the text files)
-------------------------------------------------
- GameMap - string array of all the rooms and their Items/Exits/Actions
- ItemLibrary - string array of all the items
- ExitLibrary - string array of all the exits
- ActionLibrary - string array of all the actions

Player Inventory
-------------------
- Items[6] - array of six possible Items (objects, not foreign keys)

Rooms
--------
- ID - short nickname/primary key
- Name - title of the room
- Description - short description of the room
- Items[6] - array of six possible Items (objects, not foreign keys)
- Exit[6] - array of six possible Exits (objects, not foreign keys)
- Actions[6] - array of six possible Actions (objects, not foreign keys)

Items
-------
- ID - short nickname/primary key
- Name - what it says on the tin
- Description - short description of the item
- Action ID - this is the nickname for the matching action/foreign key

Exits
-------
- ID - short nickname/primary key
- Name - what it says on the tin
- Description - short description of the player using the exit
- Destination Room ID - this is the nickname for the matching room/foreign key

Note: Exits are one-way things, since the description of going up the stairs and down the stairs are different, even though you move between the same two rooms.

Actions
---------
- ID - short nickname/primary key
- Name - what it says on the tin
- Description - short description of the player doing the action
- Command - a '~' delimiated string of actions and targets

Command List
-------------
- addRoomItem - adds an item to the room
- removeRoomItem - removes an item from the room
- addPlayerItem - adds an item to the player inventory
- removePlayerItem - removes an item from the player inventory
- addExit - adds an exit to the room
- removeExit - removes an exit from the room

Planned commands (aka to-do list)
----------------------------------
- addAction - adds an exit to the room
- removeAction - removes an exit from the room
- movePlayer - changes the current player location
- 'Remote' actions - changes a different room's Items/Exits/Actions

Currently action have to be tied to a room, am looking at ways of changing that...
