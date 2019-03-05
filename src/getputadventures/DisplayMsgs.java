package getputadventures;

public class DisplayMsgs {

    public DisplayMsgs() {
    }

    public void displayMessage(String messageName, String thingName, boolean showMsg) {

        String thisMessage = "";

        if (showMsg) {
            switch (messageName) {
                case "PlayerInventoryFull":
                    thisMessage = "You have run out of pockets and can't carry anymore.";
                    break;
                case "RoomInventoryFull":
                    thisMessage = "There is no place to put " + thingName + " in the room.";
                    break;
                case "ActionListFull":
                    thisMessage = "The universe won't allow that.";
                    break;
                case "PlayerInventoryItemAdded":
                    thisMessage = thingName + " has been added to your inventory.";
                    break;
                case "RoomItemAdded":
                    thisMessage = "A " + thingName + " appears in the room.";
                    break;
                case "ActionAdded":
                    thisMessage = "You can now " + thingName + ".";
                    break;
                case "DuplicateItem":
                    thisMessage = thingName + " already exists and the universe hates a paradox.";
                    break;
                case "CantPickUp":
                    thisMessage = thingName + " is not an item that can be picked up.";
                    break;
                case "BadAction":
                    thisMessage = "The universe listens, but does not respond.";
                    break;
                case "GoHome":
                    thisMessage = "You click your ruby slippers together and the magic takes you home...";
                    break;
                case "LoadGame":
                    thisMessage = "Loading game...";
                    break;
                case "LoadItems":
                    thisMessage = "  Loading Items...";
                    break;
                case "LoadRooms":
                    thisMessage = "You click your ruby slippers together and the magic takes you home...";
                    break;
                case "LoadActions":
                    thisMessage = "  Loading Actions...";
                    break;
                case "LoadPlayerInventory":
                    thisMessage = "  Loading Player Inventory...";
                    break;
                case "LoadMap":
                    thisMessage = "  Loading Map...";
                    break;
                case "WinGame":
                    thisMessage = "You have won the game!";
                    break;
                case "OutOfTurns":
                    thisMessage = "You have run out of turns... DOOM BE UPON YOU! (aka You Lose)";
                    break;
            }
            System.out.println(thisMessage);
        }
    }
}
