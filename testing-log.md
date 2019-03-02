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
- Does it print the Turns taken and Turns remaning?
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
YES - Does it print the room title?
YES - Does it print the Turns taken and Turns remaning?
YES - Does it print the room description?
YES - Does it print the room items?
YES - Does it print the actions (do)?
YES - Does it print the player inventory (drop) items?
YES - Can you pickup the Random Item C?
YESish, missing drop action dialog  - Can you drop the Random Item C?
YESish, duplicate dialog - Can you do action Add Player Item?
YES - Will it stop you from adding more than six items to the Player Inventory?
YES - Can you do action Add Room Action?
NO, no message prints - Can you do action Do a Cool Thing once it has been added?
YES - Can you do action Add Room Item?
YES - Will it stop you from adding more than six items to the room?
YES - Will it stop you from picking up Random Item B?
