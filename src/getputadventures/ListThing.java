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

    public void doAction(ItemLibrary gameItems, ActionLibrary gameActions, RoomMap gameMap, ListOfThings playerInventory, Room currentRoom) {

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

        String[] itemCmds;
        String subAction1;
        String subAction2;
        int maxActions;

        debugMessage.debugLong();
        debugMessage.debugOutput("ListThing doAction");
        debugMessage.debugOutput("  Action Command: " + command);

        itemCmds = command.split("~");
        maxActions = itemCmds.length;

        // Time to step through the commands

        for (int actionPos = 0; actionPos < maxActions; actionPos++) {

            Room modifyRoom;

            switch (itemCmds[actionPos]) {

                case "addRoomItem":
                    // Add Item to Room                addRoomItem~[Item]~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    subAction2 = itemCmds[actionPos + 2];
                    debugMessage.debugOutput("      Attempting to addRoomItem " + subAction1 + " to Room " + subAction2 + ". We are currently in Room " + gameMap.getCurrentRoomID() + ".");

                    if (gameMap.getCurrentRoomID().equalsIgnoreCase(subAction2)) {
                        debugMessage.debugOutput("      We're already in the room, so update the current room first.");
                        currentRoom.getItems().addItem(subAction1, true);
                        debugMessage.debugOutput("      Now update the map.");
                        gameMap.updateRoom(currentRoom);
                    } else {
                        debugMessage.debugOutput("      We're updating a different room, so just update the map.");
                        modifyRoom = gameMap.readRoom(subAction2, gameItems, gameActions, playerInventory);
                        modifyRoom.getItems().addItem(subAction1, true);
                        gameMap.updateRoom(modifyRoom);
                    }
                    break;

                case "removeRoomItem":
                    // Remove Item from Room           removeRoomItem~[Item]~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    subAction2 = itemCmds[actionPos + 2];
                    debugMessage.debugOutput("      Attempting to removeRoomItem " + subAction1 + " from Room " + subAction2 + ". We are currently in Room " + gameMap.getCurrentRoomID() + ".");

                    if (gameMap.getCurrentRoomID().equalsIgnoreCase(subAction2)) {
                        debugMessage.debugOutput("      We're already in the room, so update the current room first.");
                        currentRoom.getItems().removeThing(subAction1, true);
                        debugMessage.debugOutput("      Now update the map.");
                        gameMap.updateRoom(currentRoom);
                    } else {
                        debugMessage.debugOutput("      We're updating a different room, so just update the map.");
                        modifyRoom = gameMap.readRoom(subAction2, gameItems, gameActions, playerInventory);
                        modifyRoom.getItems().removeThing(subAction1, true);
                        gameMap.updateRoom(modifyRoom);
                    }
                    break;

                case "addRoomAction":
                    // Add Action to Room              addRoomAction~[Action]~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    subAction2 = itemCmds[actionPos + 2];
                    debugMessage.debugOutput("      Attempting to addRoomAction " + subAction1 + " to Room " + subAction2 + ". We are currently in Room " + gameMap.getCurrentRoomID() + ".");

                    if (gameMap.getCurrentRoomID().equalsIgnoreCase(subAction2)) {
                        debugMessage.debugOutput("      We're already in the room, so update the current room first.");
                        currentRoom.getActions().addItem(subAction1, true);
                        debugMessage.debugOutput("      Now update the map.");
                        gameMap.updateRoom(currentRoom);
                    } else {
                        debugMessage.debugOutput("      We're updating a different room, so just update the map.");
                        modifyRoom = gameMap.readRoom(subAction2, gameItems, gameActions, playerInventory);
                        modifyRoom.getActions().addItem(subAction1, true);
                        gameMap.updateRoom(modifyRoom);
                    }
                    break;

                case "removeRoomAction":
                    // Remove Action from Room         removeRoomAction~[Action]~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    subAction2 = itemCmds[actionPos + 2];
                    debugMessage.debugOutput("      Attempting to removeRoomAction " + subAction1 + " from Room " + subAction2 + ". We are currently in Room " + gameMap.getCurrentRoomID() + ".");

                    if (gameMap.getCurrentRoomID().equalsIgnoreCase(subAction2)) {
                        debugMessage.debugOutput("      We're already in the room, so update the current room first.");
                        currentRoom.getActions().removeThing(subAction1, true);
                        debugMessage.debugOutput("      Now update the map.");
                        gameMap.updateRoom(currentRoom);
                    } else {
                        debugMessage.debugOutput("      We're updating a different room, so just update the map.");
                        modifyRoom = gameMap.readRoom(subAction2, gameItems, gameActions, playerInventory);
                        modifyRoom.getActions().removeThing(subAction1, true);
                        gameMap.updateRoom(modifyRoom);
                    }
                    break;

                case "addPlayerItem":
                    // Add Item to Inventory           addPlayerItem~[Item]
                    subAction1 = itemCmds[actionPos + 1];
                    debugMessage.debugOutput("      Attempting to addPlayerItem " + subAction1);
                    playerInventory.addItem(subAction1, true);
                    break;

                case "removePlayerItem":
                    // Remove Item from Inventory      removePlayerItem~[Item]
                    subAction1 = itemCmds[actionPos + 1];
                    debugMessage.debugOutput("      Attempting to removePlayerItem " + subAction1);
                    playerInventory.removeThing(subAction1, true);
                    break;

                case "addItemAction":
                    // Add Action to Item              addItemAction~[Item]~[Action]
                    subAction1 = itemCmds[actionPos + 1];
                    subAction2 = itemCmds[actionPos + 2];
                    debugMessage.debugOutput("      Attempting to addItemAction " + subAction2 + "to Item " + subAction1);
                    addItemAction(subAction1, subAction2, gameItems, gameActions, playerInventory, currentRoom);
                    break;

                case "removeItemAction":
                    // Remove Action from Item         removeItemAction~[Item]
                    subAction1 = itemCmds[actionPos + 1];
                    debugMessage.debugOutput("      Attempting to removeItemAction from Item " + subAction1);
                    removeItemAction(subAction1, gameItems, gameActions, playerInventory, currentRoom);
                    break;

                case "movePlayer":
                    // Move Player to Room             movePlayer~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    debugMessage.debugOutput("      Attempting to movePlayer to Room " + subAction1);
                    gameMap.setCurrentRoomID(subAction1);
                    break;
            }
        }
        System.out.println(description);
        debugMessage.debugLong();
    }

    public void addItemAction(String addItemID, String actionID, ItemLibrary gameItems, ActionLibrary gameActions, ListOfThings playerInventory, Room currentRoom) {

        // Adds the Action ID to the Item in the Library, Player Inventory, and current Room.

        debugMessage.debugLong();
        debugMessage.debugOutput("ListThing addItemAction");

        gameItems.addItemAction(addItemID, actionID, gameActions);
        playerInventory.addItemAction(addItemID, actionID);
        currentRoom.getItems().addItemAction(addItemID, actionID);

        debugMessage.debugLong();
    }

    public void removeItemAction(String removeItem, ItemLibrary gameItems, ActionLibrary gameActions, ListOfThings playerInventory, Room currentRoom) {

        // Removes the Action ID from the Item in the Library, Player Inventory, and current Room.

        debugMessage.debugLong();
        debugMessage.debugOutput("ListThing removeItemAction");

        gameItems.removeItemAction(removeItem);
        playerInventory.removeItemAction(removeItem);
        currentRoom.getItems().removeItemAction(removeItem);

        debugMessage.debugLong();
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
