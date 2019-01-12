package getput.adventures;

public class ListThing {

    // Trying to make the Room, Exit, and Items all one sort of thing

    // Items have:
    //    String itemID;
    //    String itemName;
    //    String itemDescription;
    //    String itemActionID;

    // Exits have:
    //    String roomExitID;
    //    String roomExitName;
    //    String roomExitDescription;
    //    String roomExitDestination;

    // Actions have:
    //    String actionID;
    //    String actionName;
    //    String actionDescription;
    //    String actionCommand;

    boolean showDebug;
    boolean exitGame;

    String thingType;
    String id;
    String name;
    String description;
    String actionID;
    String destination;
    String thingCommand;

    public ListThing(String newType, String newID, String newName, String newDescription, String newActionID, String newDestination, String newCommand, boolean mainDebug) {

        showDebug = mainDebug;

        thingType = newType;
        id = newID;
        name = newName;
        description = newDescription;
        actionID = newActionID;
        destination = newDestination;
        thingCommand = newCommand;
    }

    public void clearThing(){

        if (showDebug) {
            System.out.println("Starting ListThing clearThing...");
        }

        thingType    = "";
        id           = "";
        name         = "";
        description  = "";
        actionID     = "";
        destination  = "";
        thingCommand = "";
    }

    public void doAction(ListOfThings playerInventory, Room currentRoom, ItemLibrary gameItems, ExitLibrary gameExits, ActionLibrary gameActions) {

        if (showDebug) {
            System.out.println("Starting ListThing doAction...");
        }

        // Basic possible commands
        // -----------------------------
        // Add Item to current room        addRoomItem~JM
        // Remove Item from current room   removeRoomItem~JM
        // Add Item to inventory           addPlayerItem~JM
        // Remove Item from inventory      removePlayerItem~JM
        // Add Exit to current room        addExit~FD
        // Remove Exit to current room     removeExit~FD

        // may want to add in the ability to change other rooms via the Map?

        // Chained commands
        // -----------------------------
        // Use an Item to add an Exit  removePlayerItem~FDK~addExit~FD
        // Change Item in inventory    removePlayerItem~SL~addPlayerItem~JM

        // currently idea is that using the Item will consume the item and drop it from inventory

        String[] itemCmds;
        int maxActions;

        if (showDebug) {
            System.out.println("Action Command: " + thingCommand);
        }

        itemCmds = thingCommand.split("~");
        maxActions = itemCmds.length;

        // Time to step through the commands

        System.out.println(description);

        for (int actionPos = 0; actionPos < maxActions; actionPos++) {

            if (showDebug) {
                System.out.println(itemCmds[actionPos] + "    " + itemCmds[actionPos + 1]);
            }

            switch (itemCmds[actionPos]) {

                case "addRoomItem":
                    currentRoom.items.addItem(itemCmds[actionPos + 1]);
                    break;

                case "removeRoomItem":
                    currentRoom.items.removeThing(itemCmds[actionPos + 1]);
                    break;

                case "addPlayerItem":
                    playerInventory.addItem(itemCmds[actionPos + 1]);
                    break;

                case "removePlayerItem":
                    playerInventory.removeThing(itemCmds[actionPos + 1]);
                    break;

                case "addExit":
                    currentRoom.exits.addItem(itemCmds[actionPos + 1]);
                    break;

                case "removeExit":
                    currentRoom.exits.removeThing(itemCmds[actionPos + 1]);
                    break;
            }

        }
    }


    public void useRoomExit(RoomMap gameMap) {
        // using the exit will output the description and then change the current room

        if (showDebug) {
            System.out.println("Starting ListThing useRoomExit...");
        }

        System.out.println(description);

        if (showDebug) {
            System.out.println("Moving to room " + destination);
        }

        gameMap.currentRoomID = destination;
    }

}
