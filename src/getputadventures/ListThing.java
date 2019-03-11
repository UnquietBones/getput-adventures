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

    private String thingType;
    private String id;
    private String name;
    private String description;
    private String actionID;
    private String command;
    private String actionType;
    private String canPickup;
    private boolean showDebug;
    private DebugMsgs debugMessage;

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
        debugMessage = new DebugMsgs(showDebug);
    }

    public void clearThing() {
        // Used to clear the slot in the array to signal it can be used/reused

        thingType = "";
        id = "";
        name = "";
        description = "";
        actionID = "";
        command = "";
        actionType = "";
        canPickup = "";
    }

    public boolean doAction(ItemLibrary gameItems, ActionLibrary gameActions, RoomMap gameMap, ListOfThings playerInventory, Room currentRoom) {

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

        // Print Action Description        printDescription~[Action]

        // Chained commands
        // -----------------------------
        // Consume an Item to add an Exit to a room                      removePlayerItem~FDK~addExit~FD~HOR1
        // Create Item in inventory and remove action from first item    addPlayerItem~JM~removeItemAction~SL

        String[] itemCmds;
        String subAction1;
        String subAction2;
        int maxActions;
        boolean didActionsChange = false;

        debugMessage.debugLong();
        debugMessage.debugOutput("ListThing doAction");
        debugMessage.debugOutput("  Action Command: " + command);

        itemCmds = command.split("~");
        maxActions = itemCmds.length;

        // Time to step through the commands

        for (int actionPos = 0; actionPos < maxActions; actionPos++) {

            Room modifyRoom;

            switch (itemCmds[actionPos]) {

                case "printDescription":
                    // printDescription~[Action]
                    subAction1 = itemCmds[actionPos + 1];
                    System.out.println(gameActions.findDescription(subAction1));
                    break;

                case "addRoomItem":
                    // addRoomItem~[Item]~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    subAction2 = itemCmds[actionPos + 2];
                    debugMessage.debugOutput("      Attempting to addRoomItem " + subAction1 + " to Room " + subAction2 + ". We are currently in Room " + gameMap.getCurrentRoomID() + ".");

                    if (gameMap.getCurrentRoomID().equalsIgnoreCase(subAction2)) {
                        debugMessage.debugOutput("      We're already in the room, so update the current room first.");
                        currentRoom.getItems().addItem(subAction1, true);
                        debugMessage.debugOutput("      Now update the map.");
                        gameMap.updateRoom(currentRoom);
                        didActionsChange = true;
                    } else {
                        debugMessage.debugOutput("      We're updating a different room, so just update the map.");
                        modifyRoom = gameMap.readRoom(subAction2, gameItems, gameActions, playerInventory);
                        modifyRoom.getItems().addItem(subAction1, true);
                        gameMap.updateRoom(modifyRoom);
                    }
                    break;

                case "removeRoomItem":
                    // removeRoomItem~[Item]~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    subAction2 = itemCmds[actionPos + 2];
                    debugMessage.debugOutput("      Attempting to removeRoomItem " + subAction1 + " from Room " + subAction2 + ". We are currently in Room " + gameMap.getCurrentRoomID() + ".");

                    if (gameMap.getCurrentRoomID().equalsIgnoreCase(subAction2)) {
                        debugMessage.debugOutput("      We're already in the room, so update the current room first.");
                        currentRoom.getItems().removeThing(subAction1, true);
                        debugMessage.debugOutput("      Now update the map.");
                        gameMap.updateRoom(currentRoom);
                        didActionsChange = true;
                    } else {
                        debugMessage.debugOutput("      We're updating a different room, so just update the map.");
                        modifyRoom = gameMap.readRoom(subAction2, gameItems, gameActions, playerInventory);
                        modifyRoom.getItems().removeThing(subAction1, true);
                        gameMap.updateRoom(modifyRoom);
                    }
                    break;

                case "addRoomAction":
                    // addRoomAction~[Action]~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    subAction2 = itemCmds[actionPos + 2];
                    debugMessage.debugOutput("      Attempting to addRoomAction " + subAction1 + " to Room " + subAction2 + ". We are currently in Room " + gameMap.getCurrentRoomID() + ".");

                    if (gameMap.getCurrentRoomID().equalsIgnoreCase(subAction2)) {
                        debugMessage.debugOutput("      We're already in the room, so update the current room first.");
                        currentRoom.getActions().addItem(subAction1, true);
                        debugMessage.debugOutput("      Now update the map.");
                        gameMap.updateRoom(currentRoom);
                        didActionsChange = true;
                    } else {
                        debugMessage.debugOutput("      We're updating a different room, so just update the map.");
                        modifyRoom = gameMap.readRoom(subAction2, gameItems, gameActions, playerInventory);
                        modifyRoom.getActions().addItem(subAction1, true);
                        gameMap.updateRoom(modifyRoom);
                    }
                    break;

                case "removeRoomAction":
                    // removeRoomAction~[Action]~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    subAction2 = itemCmds[actionPos + 2];
                    debugMessage.debugOutput("      Attempting to removeRoomAction " + subAction1 + " from Room " + subAction2 + ". We are currently in Room " + gameMap.getCurrentRoomID() + ".");

                    if (gameMap.getCurrentRoomID().equalsIgnoreCase(subAction2)) {
                        debugMessage.debugOutput("      We're already in the room, so update the current room first.");
                        currentRoom.getActions().removeThing(subAction1, true);
                        debugMessage.debugOutput("      Now update the map.");
                        gameMap.updateRoom(currentRoom);
                        didActionsChange = true;
                    } else {
                        debugMessage.debugOutput("      We're updating a different room, so just update the map.");
                        modifyRoom = gameMap.readRoom(subAction2, gameItems, gameActions, playerInventory);
                        modifyRoom.getActions().removeThing(subAction1, true);
                        gameMap.updateRoom(modifyRoom);
                    }
                    break;

                case "addPlayerItem":
                    // addPlayerItem~[Item]
                    subAction1 = itemCmds[actionPos + 1];
                    debugMessage.debugOutput("      Attempting to addPlayerItem " + subAction1);
                    if (playerInventory.addItem(subAction1, true)) {
                        System.out.println(description);
                        didActionsChange = true;
                    }
                    break;

                case "removePlayerItem":
                    // removePlayerItem~[Item]
                    subAction1 = itemCmds[actionPos + 1];
                    debugMessage.debugOutput("      Attempting to removePlayerItem " + subAction1);
                    if (playerInventory.removeThing(subAction1, true)) {
                        System.out.println(description);
                        didActionsChange = true;
                    }
                    break;

                case "addItemAction":
                    // addItemAction~[Item]~[Action]
                    subAction1 = itemCmds[actionPos + 1];
                    subAction2 = itemCmds[actionPos + 2];
                    debugMessage.debugOutput("      Attempting to addItemAction " + subAction2 + "to Item " + subAction1);
                    if (addItemsAction(subAction1, subAction2, gameItems, gameActions, playerInventory, currentRoom)) {
                        System.out.println(description);
                        didActionsChange = true;
                    }
                    break;

                case "removeItemAction":
                    // removeItemAction~[Item]
                    subAction1 = itemCmds[actionPos + 1];
                    debugMessage.debugOutput("      Attempting to removeItemAction from Item " + subAction1);
                    if (removeItemsAction(subAction1, gameItems, gameActions, playerInventory, currentRoom)) {
                        System.out.println(description);
                        didActionsChange = true;
                    }
                    break;

                case "movePlayer":
                    // movePlayer~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    debugMessage.debugOutput("      Attempting to movePlayer to Room " + subAction1);
                    gameMap.setCurrentRoomID(subAction1);
                    break;
            }
        }

        debugMessage.debugLong();
        return didActionsChange;
    }

    public boolean addItemsAction(String addItemID, String actionID, ItemLibrary gameItems, ActionLibrary gameActions, ListOfThings playerInventory, Room currentRoom) {

        // Adds the Action ID to the Item in the Library, Player Inventory, and current Room.

        debugMessage.debugLong();
        debugMessage.debugOutput("ListThing addItemAction");

        if (gameItems.addItemAction(addItemID, actionID, gameActions)) {
            return true;
        }
        if (playerInventory.addItemAction(addItemID, actionID)) {
            return true;
        }
        if (currentRoom.getItems().addItemAction(addItemID, actionID)) {
            return true;
        }

        debugMessage.debugLong();
        return false;
    }

    public boolean removeItemsAction(String removeItem, ItemLibrary gameItems, ActionLibrary gameActions, ListOfThings playerInventory, Room currentRoom) {

        // Removes the Action ID from the Item in the Library, Player Inventory, and current Room.

        debugMessage.debugLong();
        debugMessage.debugOutput("ListThing removeItemsAction");

        if (gameItems.removeItemAction(removeItem)) {
            return true;
        }
        if (playerInventory.removeItemAction(removeItem)) {
            return true;
        }
        if (currentRoom.getItems().removeItemAction(removeItem)) {
            return true;
        }

        debugMessage.debugLong();
        return false;
    }

    public String getThingType() {
        return thingType;
    }

    public void setThingType(String thingType) {
        this.thingType = thingType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActionID() {
        return actionID;
    }

    public void setActionID(String actionID) {
        this.actionID = actionID;
    }

    public String getCommand() {
        return command;
    }

    public String getActionType() {
        return actionType;
    }

    public String getCanPickup() {
        return canPickup;
    }
}
