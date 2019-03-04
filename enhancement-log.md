This is the log for the enhancements so I can track what I added (and in what order) for my own sanity.

# Room 1 Enhancements

1. Don't allow creation of duplicate items (Items or Actions)
2. Refresh the available action lines when they change (Room items / Do Action / Drop Inventory Item)
3. Add some whitespace and "-" to help the visuals for the room descriptions.
4. Put a hard wrap on the room printouts so everything lines up nice and pretty

## 001 Don't Allow Creation of Duplicates

1. Don't allow creation of duplicate items (Items or Actions)

Items are created by addRoomItem and addPlayerItem.

- **addRoomItem** uses currentRoom.getItems().addItem(subAction1, true)
- **addPlayerItem** uses playerInventory.addItem(subAction1, true)

I should just be able to add an 'am I already here' check to addItem (ListOfThings)

### Changes to addItem (ListOfThings)

- Took out unneeded comments
- Swapped out the code checking for an open slot to use **freeSpot** instead
- Swapped out the prints for **displayMessage**

### Created DisplayMessages

To try and make the messages displayed to the player a little more consistent, I made a new class. I am thinking of adding a UI later, so this should help simplify that as well.

### Changes to posInList (ListOfThings)

- Took out unneeded comments
- Changed to use debugHeader

### Changed to clearList (ListOfThings)

- We don't really need this, so deleted it 
- Removed it from the **ListOfThings** constructor
- Removed it from the **Room** constructor

### Testing for [001 Don't Allow Creation of Duplicates]

- Could not use **Add Player Item** to add more than one to the Player Inventory
- Could not use **Add Room Item** to add more than one to the Room Inventory
- If I dropped the **Random Item A**, I could create another using **Add Player Item**. This seems like it would be useful for game design, so I'm keeping it for now.
- If I had dropped **Random Item A** I could then create another one in the Player Inventory and then pick up the item from the Room. I want to stop it from doing that!

## 001a Don't Allow Pick up of Duplicates

Since all I'm doing is moving a reference from one list to another, I'm using **transferThing** (ListOfThings) instead of **addItem** and **removeThing**. That save us creating a new item and needing the old one cleaned up.

I probably need to go back and look at the actions and see if I need to have a transfer option there as well...

### Changes to pickItUp (Room)

- Swapped it over to using **displayMessage**

### Changes to transferThing (ListOfThings) 

- Took out the search code and used **posInList** instead

### Changes to badRoomAction (Room)

- Now that we have **displayMessage** there's no need for this. Swapped out all calls to it for **displayMessage**

### Testing for [001a Don't Allow Pick up of Duplicates]

- Could not pick up a second **Random Item A**

## 002 Refresh Available Actions

2. Refresh the available action lines when they change (Room items / Do Action / Drop Inventory Item)

[pending...]

## 003 Tidy Up Room Display

3. Add some whitespace and "-" to help the visuals for the room descriptions.

[pending...]

## 004 Line Wrap!

4. Put a hard wrap on the room printouts so everything lines up nice and pretty

[pending...]