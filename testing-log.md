This branch is for testing all the possible actions in Room #1. The first room has single actions, nothing is chained.

Room 1 Information
===================
- Items: RIC, RIA, RIC
- Actions: API,ARA,ARI,MPR

Items
--------------
- RIA/Random Item A/There is a small random item./None/Y
- RIB/Random Item B/There is a large random item./None/N
- RIC/Random Item C/There is a small random item that looks useless./None/Y

[Random Item A] is added to the player's inventory by action [Add Player Item]. It can be picked up and put down. It has no action associated with it.

[Random Item B] is added to the room by action [Add Room Item]. It cannot be picked up and has no action associated with it.

[Random Item C] in already in the room can be picked up and put down. It has no action associated with it.

Actions
-------------
- API/Add Player Item/The Random Item A mysteriously appears in your pocket!/addPlayerItem RIA/R
- ARA/Add Room Action/The Room can now do A Cool Thing!/addRoomAction DCT Room1/R
- ARI/Add Room Item/The Random Item B mysteriously appears in the room!/addRoomItem RIB Room1/R
- MPR/Move Player to new Room/You have been moved suddenly, as if by a heartless computer.../movePlayer Room2/R
- DCT/Do a Cool Thing/You do a Cool Thing in the Room. It's extra awesome!/None/R

[Add Player Item] will create [Random Item A] in the player inventory.

[Add Room Action] will add action [Do a Cool Thing] to the room.

[Add Room Item] will add [Random Item B] to the room.

[Move Player to new Room] will move the player to Room #2 (thus ending our test!)

[Do a Cool Thing] will display a message.

Things I Need to Test
===================
- Does it print the room title?
- Does it print the Turns taken and Turns remaining?
- Does it print the room description?
- Does it print the room items?
- Does it print the actions (do)?
- Does it print the player inventory (drop) items?
- Can you pickup the Random Item C?
- Can you drop the Random Item C?
- Can you do action Add Player Item?
- Will it stop you from adding more than six items to the Player Inventory?
- Can you do action Add Room Action?
- Can you do action Do a Cool Thing once it has been added?
- Can you do action Add Room Item?
- Will it stop you from adding more than six items to the room?
- Will it stop you from picking up Random Item B?
- Can you do action Move Player to new Room?

Testing First Round
----------------------
- YES - Does it print the room title?
- YES - Does it print the Turns taken and Turns remaining?
- YES - Does it print the room description?
- YES - Does it print the room items?
- YES - Does it print the actions (do)?
- YES - Does it print the player inventory (drop) items?
- YES - Can you pickup the Random Item C?
- YESish, missing drop action dialog  - Can you drop the Random Item C? [Issue 001]
- YESish, duplicate dialog - Can you do action Add Player Item? [Issue 002]
- YES - Will it stop you from adding more than six items to the Player Inventory?
- YES - Can you do action Add Room Action?
- NO, no message prints - Can you do action Do a Cool Thing once it has been added? [Issue 003]
- YES - Can you do action Add Room Item?
- YES - Will it stop you from adding more than six items to the room?
- YES - Will it stop you from picking up Random Item B?
- YES - Can you do action Move Player to new Room?

Issue 001
-----------
- YESish, missing drop action dialog  - Can you drop the Random Item C?

dropIt (Room) does this. The method was not setup to say anything when you dropped an item. Added a dropped message so the player can tell it worked.

Tested -- works!

added debugHeader (DebugMsgs) and swapped it over in Room just so things look nicer in the debug output

Issue 002
------------
- YESish, duplicate dialog - Can you do action Add Player Item?
- addItem (ListOfThings) displays "Random Item A has been added to your inventory."
- doAction (ListThing) displays "The Random Item A mysteriously appears in your pocket!"

There's no need for the message from addItem, removing it.

Tested -- works!

Issue 002.a
--------------

Fixed a bug in addItem (ListOfThings) where it wasn't checking for a null, just isEmpty.

Tested -- works!

Issue 002.b
--------------

Noticed that Add Player Item and picking up an item give different full inventory error messages

- > Add Player Item == "Inventory is full!" from addItem (ListOfThings)
- > Random Item C == "You already have 6 items, you cannot carry anymore." from pickItUp (Room)

Replacing both of these with "You have run out of pockets and can't carry anymore." and added non-Player Inventory messages to addItem (ListOfThings)

Tested -- works!

Issue 002.c
--------------

Added findName to ItemLibrary so I can use the proper name of the item in the error for pickItUp (Room)

Tested -- works!

Issue 002.d
--------------

The Add Room Item as the same issue as Add Player Item

- Add Room Item == "There is no place to put Random Item B in the room."  from addItem (ListOfThings)
- Random Item C == "The room already has 6 items, you cannot drop this here" from dropIt (Room)

Changed dropIt to match the message from addItem

Tested -- works!

Issue 002.e
--------------

Added findName (ListOfThings) so I can use the proper name of the item in the error for dropIt (Room)

Tested -- works!

Note to self
-------------
I really need to refactor this so everything is as broken down and single-action as it can be! The more I learn the more I realise how much better it can be. (woe!)

Issue 003
------------
- NO, no message prints - Can you do action Do a Cool Thing once it has been added?
- DCT/Do a Cool Thing/You do a Cool Thing in the Room. It's extra awesome!/None/R

doAction (ListThing) does seem to be firing, but the Action Command is "None" so it doesn't do anything. Working as written, but not as intended.

Added findDescription to ActionLibrary
Added a printDescription case option to doAction (ListThing)
Changed "None"s to "printDescription~[Action]" in the text file.

Tested -- works!

Testing Second Round
----------------------
- YES - Does it print the room title?
- YES - Does it print the Turns taken and Turns remaining?
- YES - Does it print the room description?
- YES - Does it print the room items?
- YES - Does it print the actions (do)?
- YES - Does it print the player inventory (drop) items?
- YES - Can you pickup the Random Item C?
- YES - Can you drop the Random Item C?
- YES - Can you do action Add Player Item?
- YES - Will it stop you from adding more than six items to the Player Inventory?
- YES - Can you do action Add Room Action?
- YES - Can you do action Do a Cool Thing once it has been added?
- YES - Can you do action Add Room Item?
- YES - Will it stop you from adding more than six items to the room?
- YES - Will it stop you from picking up Random Item B?
- YES - Can you do action Move Player to new Room?

Issue 004
------------
Picked up and put down the items until I hit a NullPointerException in transferThing (ListOfThings)

Looking at the code, I have no idea what I was smoking because the check for null only covers half what it needs to. *facepalm* Fixed.

Tested -- works!

Issue 005
------------
- NO - Does it print the descriptions of items in the room?

I had forgotten that printListDescriptions (ListOfThings) was supposed to be printing as well as the base room description. This is not working.

printListDescriptions (ListOfThings) had no print statement in it... which is why it didn't print. Set it up to return a string instead.

Added printing to printRoom (Room)

Tested -- works!

Testing Third Round
----------------------
I did all the actions multiple times and everything worked as expected. Going to merge this code back into Master and then check out a new branch for new functionality I want to add.

- Don't allow creation of duplicate items (Items or Actions)
- Refresh the available action lines when they change (Room items / Do Action / Drop Inventory Item)
- Add some whitespace and "-" to help the visuals for the room descriptions.
- Put a hard wrap on the room printouts so everything lines up nice and pretty