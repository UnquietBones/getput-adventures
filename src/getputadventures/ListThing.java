package getputadventures;

public class ListThing {

    // All Things have:
    //    String ID;                  - Primary Key
    //    String Name;                - Name of Thing
    //    String Description;         - Description of the thing or description of the action taken

    // Items have:
    //    String itemActionID;        - Shows what actions this Item can take
    //    String canPickup;           - Can the item be moved to Player's Inventory (Y/N)

    // Actions have:
    //    String actionType;          - Does the action require a Room, and Object, or both
    //    String actionCommand;       - What happens when the action is taken

    private static String LONGDASH = "--------------------------------";
    private static String SHORTDASH = "----------------";
    public String thingType;
    public String id;
    public String name;
    public String description;
    public String actionID;
    public String command;
    public String actionType;
    public String canPickup;
    private boolean showDebug;

    public ListThing(String newType, String newID, String newName, String newDescription, String newActionID, String newCommand, String newActionType, String newPickup, boolean mainDebug) {

        thingType = newType;
        id = newID;
        name = newName;
        description = newDescription;
        actionID = newActionID;
        command = newCommand;
        actionType = newActionType;
        canPickup = newPickup;

        showDebug = mainDebug;
    }

    public void clearThing() {

        // Used to clear the slot in the array to signal it can be used/reused

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ListThing clearThing");
        }

        thingType = "";
        id = "";
        name = "";
        description = "";
        actionID = "";
        command = "";
        actionType = "";
        canPickup = "";

        if (showDebug) {
            System.out.println("  All values set to empty string.");
            System.out.println(LONGDASH);
        }
    }

    public void doAction(ItemLibrary newItems, ActionLibrary newActions, RoomMap newMap, ListOfThings newInventory, Room newRoom) {

        ItemLibrary gameItems = newItems;
        ActionLibrary gameActions = newActions;
        RoomMap gameMap = newMap;
        ListOfThings playerInventory = newInventory;
        Room currentRoom = newRoom;

        String[] itemCmds;
        String subAction1;
        String subAction2;
        int maxActions;

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ListThing doAction");
        }

        // Basic possible commands
        // -----------------------------
        // Add Item to Room                addRoomItem~[Item]~[Room]
        // Remove Item from Room           removeRoomItem~[Item]~[Room]
        // Add Action to Room              addRoomAction~[Action]~[Room]
        // Remove Action from Room         removeRoomAction~[Action]~[Room]

        // Add Item to Inventory           addPlayerItem~[Item]
        // Remove Item from Inventory      removePlayerItem~[Item]

        // Add Action to Item              addItemAction~[Item]~[Action]
        // Remove Action from Item         removeItemAction~[Item]

        // Move Player to Room             movePlayer~[Room]

        // Chained commands
        // -----------------------------
        // Consume an Item to add an Exit to a room                      removePlayerItem~FDK~addExit~FD~HOR1
        // Create Item in inventory and remove action from first item    addPlayerItem~JM~removeItemAction~SL

        if (showDebug) {
            System.out.printf("  Action Command: %s %n", command);
        }

        itemCmds = command.split("~");
        maxActions = itemCmds.length;

        // Time to step through the commands

        System.out.println(description);

        for (int actionPos = 0; actionPos < maxActions; actionPos++) {

            if (showDebug) {
                if ((actionPos + 1) >= maxActions) {
                    System.out.printf("    Parsing action %s %n", itemCmds[actionPos]);
                } else {
                    System.out.printf("    Parsing action %s   %s %n", itemCmds[actionPos], itemCmds[actionPos + 1]);
                }
            }

            subAction1 = "";
            subAction2 = "";

            switch (itemCmds[actionPos]) {

                case "addRoomItem":
                    // Add Item to Room                addRoomItem~[Item]~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    subAction2 = itemCmds[actionPos + 2];
                    //need to update map, then refresh room
                    //currentRoom.items.addItem(itemCmds[actionPos + 1], true);
                    break;

                case "removeRoomItem":
                    // Remove Item from Room           removeRoomItem~[Item]~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    subAction2 = itemCmds[actionPos + 2];
                    //need to update map, then refresh room
                    //currentRoom.items.removeThing(itemCmds[actionPos + 1],true);
                    break;

                case "addRoomAction":
                    // Add Action to Room              addRoomAction~[Action]~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    subAction2 = itemCmds[actionPos + 2];
                    //need to update map, then refresh room
                    break;

                case "removeRoomAction":
                    // Remove Action from Room         removeRoomAction~[Action]~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    subAction2 = itemCmds[actionPos + 2];
                    //need to update map, then refresh room
                    break;

                case "addPlayerItem":
                    // Add Item to Inventory           addPlayerItem~[Item]
                    subAction1 = itemCmds[actionPos + 1];
                    playerInventory.addItem(subAction1, true);
                    break;

                case "removePlayerItem":
                    // Remove Item from Inventory      removePlayerItem~[Item]
                    subAction1 = itemCmds[actionPos + 1];
                    playerInventory.removeThing(subAction1, true);
                    break;

                case "addItemAction":
                    // Add Action to Item              addItemAction~[Item]~[Action]
                    subAction1 = itemCmds[actionPos + 1];
                    subAction2 = itemCmds[actionPos + 2];
                    addItemAction(subAction1, subAction2, gameItems, playerInventory, currentRoom);
                    break;

                case "removeItemAction":
                    // Remove Action from Item         removeItemAction~[Item]
                    subAction1 = itemCmds[actionPos + 1];
                    removeItemAction(subAction1, gameItems, playerInventory, currentRoom);
                    break;

                case "movePlayer":
                    // Move Player to Room             movePlayer~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    System.out.println(description);
                    gameMap.currentRoomID = subAction1;
                    break;
            }
        }

        if (showDebug) {
            System.out.println(LONGDASH);
        }
    }

    public void addItemAction(String addItemID, String actionID, ItemLibrary gameItems, ListOfThings playerInventory, Room currentRoom) {

        // Adds the Action ID to the Item in the Library, Player Inventory, and current Room.

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ListThing addItemAction");
        }

            gameItems.addItemAction(addItemID, actionID);
            playerInventory.addItemAction(addItemID, actionID);
            currentRoom.items.addItemAction(addItemID, actionID);

        if (showDebug) {
            System.out.println(LONGDASH);
        }
    }

    public void removeItemAction(String removeItem, ItemLibrary gameItems, ListOfThings playerInventory, Room currentRoom) {

        // Removes the Action ID from the Item in the Library, Player Inventory, and current Room.

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ListThing removeItemAction");
        }

        gameItems.removeItemAction(removeItem);
        playerInventory.removeItemAction(removeItem);
        currentRoom.items.removeItemAction(removeItem);

        if (showDebug) {
            System.out.println(LONGDASH);
        }
    }
}
