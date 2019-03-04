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
            }
            System.out.println(thisMessage);
        }
    }
}
