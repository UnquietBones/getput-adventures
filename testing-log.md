This log contains the testing for:
- Room 1: The first room has single actions, nothing is chained.
- Room 2: The second room has chained actions.
- Room 3: The third room has remote actions.
- Room 4: This room is where the remote actions from Room 3 happen.

---


# Room 1 Information

Room1/Testing Room 1/You are standing in an empty room. The walls are painted a calming light blue and you feel like you can take actions with infinate consquences./RIC/API,ARA,ARI,MPR

- Items: RIC, RIA, RIC
- Actions: API, ARA, ARI, MPR

## Items
- RIA/Random Item A/There is a small random item./None/Y
- RIB/Random Item B/There is a large random item./None/N
- RIC/Random Item C/There is a small random item that looks useless./None/Y

**Random Item A** is added to the player's inventory by action **Add Player Item**. It can be picked up and put down. It has no action associated with it.

**Random Item B** is added to the room by action **Add Room Item**. It cannot be picked up and has no action associated with it.

**Random Item C** in already in the room can be picked up and put down. It has no action associated with it.

## Actions
- API/Add Player Item/The Random Item A mysteriously appears in your pocket!/addPlayerItem RIA/R
- ARA/Add Room Action/The Room can now do A Cool Thing!/addRoomAction DCT Room1/R
- ARI/Add Room Item/The Random Item B mysteriously appears in the room!/addRoomItem RIB Room1/R
- MPR/Move Player to new Room/You have been moved suddenly, as if by a heartless computer.../movePlayer Room2/R
- DCT/Do a Cool Thing/You do a Cool Thing in the Room. It's extra awesome!/None/R

**Add Player Item** will create **Random Item A** in the player inventory.

**Add Room Action** will add action **Do a Cool Thing** to the room.

**Add Room Item** will add **Random Item B** to the room.

**Move Player to new Room** will move the player to Room #2 (thus ending our test!)

**Do a Cool Thing** will display a message.

## Things I Need to Test
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

## Testing First Round
- YES - Does it print the room title?
- YES - Does it print the Turns taken and Turns remaining?
- YES - Does it print the room description?
- YES - Does it print the room items?
- YES - Does it print the actions (do)?
- YES - Does it print the player inventory (drop) items?
- YES - Can you pickup the Random Item C?
- **YESish**, missing drop action dialog  - Can you drop the Random Item C? **Issue 001**
- **YESish**, duplicate dialog - Can you do action Add Player Item? **Issue 002**
- YES - Will it stop you from adding more than six items to the Player Inventory?
- YES - Can you do action Add Room Action?
- **NO**, no message prints - Can you do action Do a Cool Thing once it has been added? **Issue 003**
- YES - Can you do action Add Room Item?
- YES - Will it stop you from adding more than six items to the room?
- YES - Will it stop you from picking up Random Item B?
- YES - Can you do action Move Player to new Room?

## Issue 001
- YESish, missing drop action dialog  - Can you drop the Random Item C?

dropIt (Room) does this. The method was not setup to say anything when you dropped an item. Added a dropped message so the player can tell it worked.

Tested -- works!

added debugHeader (DebugMsgs) and swapped it over in Room just so things look nicer in the debug output

## Issue 002
- YESish, duplicate dialog - Can you do action Add Player Item?
- addItem (ListOfThings) displays "Random Item A has been added to your inventory."
- doAction (ListThing) displays "The Random Item A mysteriously appears in your pocket!"

There's no need for the message from addItem, removing it.

Tested -- works!

## Issue 002.a

Fixed a bug in addItem (ListOfThings) where it wasn't checking for a null, just isEmpty.

Tested -- works!

## Issue 002.b

Noticed that Add Player Item and picking up an item give different full inventory error messages

- Add Player Item == "Inventory is full!" from addItem (ListOfThings)
- Random Item C == "You already have 6 items, you cannot carry anymore." from pickItUp (Room)

Replacing both of these with "You have run out of pockets and can't carry anymore." and added non-Player Inventory messages to addItem (ListOfThings)

Tested -- works!

## Issue 002.c

Added findName to ItemLibrary so I can use the proper name of the item in the error for pickItUp (Room)

Tested -- works!

## Issue 002.d

The Add Room Item as the same issue as Add Player Item

- Add Room Item == "There is no place to put Random Item B in the room."  from addItem (ListOfThings)
- Random Item C == "The room already has 6 items, you cannot drop this here" from dropIt (Room)

Changed dropIt to match the message from addItem

Tested -- works!

## Issue 002.e

Added findName (ListOfThings) so I can use the proper name of the item in the error for dropIt (Room)

Tested -- works!

## Note to self
I really need to refactor this so everything is as broken down and single-action as it can be! The more I learn the more I realise how much better it can be. (woe!)

## Issue 003
- NO, no message prints - Can you do action Do a Cool Thing once it has been added?
- DCT/Do a Cool Thing/You do a Cool Thing in the Room. It's extra awesome!/None/R

doAction (ListThing) does seem to be firing, but the Action Command is "None" so it doesn't do anything. Working as written, but not as intended.

Added findDescription to ActionLibrary
Added a printDescription case option to doAction (ListThing)
Changed "None"s to "printDescription~**Action**" in the text file.

Tested -- works!

## Testing Second Round
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

## Issue 004
Picked up and put down the items until I hit a NullPointerException in transferThing (ListOfThings)

Looking at the code, I have no idea what I was smoking because the check for null only covers half what it needs to. *facepalm* Fixed.

Tested -- works!

## Issue 005
- NO - Does it print the descriptions of items in the room?

I had forgotten that printListDescriptions (ListOfThings) was supposed to be printing as well as the base room description. This is not working.

printListDescriptions (ListOfThings) had no print statement in it... which is why it didn't print. Set it up to return a string instead.

Added printing to printRoom (Room)

Tested -- works!

## Testing Third Round
I did all the actions multiple times and everything worked as expected. Going to merge this code back into Master and then check out a new branch for new functionality I want to add.

- Don't allow creation of duplicate items (Items or Actions)
- Refresh the available action lines when they change (Room items / Do Action / Drop Inventory Item)
- Add some whitespace and "-" to help the visuals for the room descriptions.
- Put a hard wrap on the room printouts so everything lines up nice and pretty


---
---

# Room 2 Information

Room2/Testing Room 2/You are standing in an empty room. The walls are a cheerful light yellow and you feel like actions have logical consequences./None/PAPI,PARI,PAIA,ARC,AAW

- Items: LAW, RIA, RIB
- Actions: AAW, ARC, OTW, PAIA, PRIA, PAPI, PRPI, PARI, PRRI 

## Items
- LAW/Window/There is a window here that leads back to the first room./OTW/N
- RIA/Random Item A/There is a small random item./None/Y
- RIB/Random Item B/There is a large random item./None/N

**Window** is added to the room by action **Add a Window**. It cannot be picked up and it has the action **Open The Window**.

**Random Item A** is added to the player's inventory by action **Properly Add Player Item** and removed by **Properly Remove Player Item**. It can be picked up and put down. It has no action associated with it until **Properly Add Item Action** is used, after which it can **Do a Thing Properly**. The action can be removed by **Properly Remove Item Action**.

**Random Item B** is added to the room by action **Properly Add Room Item** and removed by **Properly Remove Room Item**. It cannot be picked up and has no action associated with it.

## Actions
- AAW/Add a Window/You summon a window into existence. Properly!/addRoomItem LAW Room2 removeRoomAction AAW Room2/R
- ARC/Archway/There is an archway that leads into the wallpaper room./movePlayer Room3/R
- PAIA/Properly Add Item Action/Random Item A can now do a Thing Properly!/addItemAction DATP RIA removeRoomAction PAIA Room2 addRoomAction PRIA Room2/RO
- PAPI/Properly Add Player Item/The Random Item A mysteriously appears in your pocket! Properly!/addPlayerItem RIA removeRoomAction PAPI Room2 addRoomAction PRPI Room2/R
- PARI/Properly Add Room Item/The Random Item B mysteriously appears in the room! Properly!/addRoomItem RIB Room2 removeRoomAction PARI Room2 addRoomAction PRRI Room2/R

**Add a Window** will add **Window** to the room inventory and remove room action **Add a Window**.

**Archway** will move the player to Room 3.

**Properly Add Item Action** will add action **Do a Thing Properly** to item **Random Item A**, remove room action **Properly Add Item Action**, and add room action **Properly Remove Item Action**.

**Properly Add Player Item** will add **Random Item A** to the player's inventory, remove room action **Properly Add Player Item**, and add room action **Properly Remove Player Item**.

**Properly Add Room Item** will add **Random Item B** to the room inventory, remove room action **Properly Add Room Item**, and add room action **Properly Remove Room Item**. 

- DATP/Do a Thing Properly/You use the item to do a Thing. Properly!/printDescription DATP/O
- OTW/Open The Window/You open the window and crawl through it back to the blue room./movePlayer Room1/O
- PRIA/Properly Remove Item Action/Random Item A can no longer do a Thing Properly!/removeItemAction RIA removeRoomAction PRIA Room2 addRoomAction PAIA Room2/RO
- PRPI/Properly Remove Player Item/The Random Item A mysteriously vanished from your pocket! Properly!/removePlayerItem RIA removeRoomAction PRPI Room2 addRoomAction PAPI Room2/RO
- PRRI/Properly Remove Room Item/The Random Item B mysteriously vanished from the room! Properly!/removeRoomItem RIB Room2 removeRoomAction PRRI Room2 addRoomAction PARI Room2/RO

**Do a Thing Properly** will display a message.

**Open The Window** will move the player to Room #1.

**Properly Remove Item Action** will remove the action from item **Random Item A**, remove room action **Properly Remove Item Action**, and add room action **Properly Add Item Action**.

**Properly Remove Player Item** will remove item **Random Item A** from the player's inventory, remove room action **Properly Remove Player Item**, and add room action **Properly Add Player Item**.

**Properly Remove Room Item** will remove item **Random Item B** from the room inventory, remove room action **Properly Remove Room Item**, and add room action **Properly Add Room Item**.

## Things I Need to Test
- Does **Add a Window** add **Window** and remove **Add a Window**?
- Does **Archway** move the player to Room 3?
- Does **Properly Add Item Action** add action **Do a Thing Properly** to item **Random Item A**, remove room action **Properly Add Item Action**, and add room action **Properly Remove Item Action**?
- Does **Properly Add Player Item** add **Random Item A** to the player's inventory, remove room action **Properly Add Player Item**, and add room action **Properly Remove Player Item**?
- Does **Properly Add Room Item** add **Random Item B** to the room inventory, remove room action **Properly Add Room Item**, and add room action **Properly Remove Room Item**?
- Does **Do a Thing Properly** display a message?
- Does **Open The Window** move the player to Room #1?
- Does **Properly Remove Item Action** remove the action from item **Random Item A**, remove room action **Properly Remove Item Action**, and add room action **Properly Add Item Action**?
- Does **Properly Remove Player Item** remove item **Random Item A** from the player's inventory, remove room action **Properly Remove Player Item**, and add room action **Properly Add Player Item**?
- Does **Properly Remove Room Item** remove item **Random Item B** from the room inventory, remove room action **Properly Remove Room Item**, and add room action **Properly Add Room Item**?

## Issue 006

Using **Move Player to new Room** from Room 1, moves you to Room 2 but doesn't print the message.

MPR/Move Player to new Room/You have been moved suddenly, as if by a heartless computer.../movePlayer Room2/R

 - **ListThing** (doAction) just didn't have a display of the description, added
 - Swapped out all System.out.print to **displayOutput** (displayMsgs)
 - Combined **DebugMsgs** into **DisplayMsgs** since they were both about showing messages to the user
 - Swapped out all **DebugMsgs** for **DisplayMsgs**
 
Tested -- works!
 
## Issue 007
 
Does **Add a Window** add **Window** and remove **Add a Window**?

OTW/Open The Window/You open the window and crawl through it back to the blue room./movePlayer~Room1/O
 
- It does add the item **Window** and remove the room action **Add a Window** but the new action **Open The Window** doesn't show up.
- **Window** does have action **Open The Window**, but it's failing the check on 'Action OTW Open The Window is missing the required Item in Player Inventory.' because the action type is O
- So what we have here is bad planning on how to validate the actions!

Time to rethink how I want the actions to work...
 

