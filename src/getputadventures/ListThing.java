working on this will not compile

package getputadventures;

public class ListThing {

    // All Things have:
    //    String ID;                  - Primary Key
    //    String Name;                - Name of Thing
    //    String Description;         - Description of Thing or description of action
    
    // Items have:
    //    String itemActionID;        - Shows what actions this Item can take

    // Actions have:
    //    String actionType;          - Does the action require a Room, and Object, or both
    //    String actionCommand;       - What happens when the action is taken

    boolean showDebug;

    public String thingType;
    public String id;
    public String name;
    public String description;
    public String actionID;
    public String command;
    public String actionType;

    public ListThing(String newType, String newID, String newName, String newDescription, String newActionID, String newCommand, boolean mainDebug, String newActionType) {

        showDebug = mainDebug;

        thingType = newType;
        id = newID;
        name = newName;
        description = newDescription;
        actionID = newActionID;
        command = newCommand;
        actionType = newActionType;
    }

    public void clearThing(){
        // Used to clear the slot in the array to signal it can be used
        if (showDebug) {
            System.out.println("Running ListThing clearThing - set all to empty strings...");
        }

        thingType    = "";
        id           = "";
        name         = "";
        description  = "";
        actionID     = "";
        command = "";
        actionType = "";
    }
        
    public void doAction(ListOfThings playerInventory, Room currentRoom, ItemLibrary gameItems, ActionLibrary gameActions) {

        if (showDebug) {
            System.out.println("Starting ListThing doAction...");
        }

        // Basic possible commands
        // -----------------------------
        // Add Item to Room                addRoomItem~[Item]~[Room]
        // Remove Item from Room           removeRoomItem~[Item]~[Room]
        // Add Action to Room              addRoomAction~[Action]~[Room]
        // Remove Action from Room         removeRoomAction~[Action]~[Room]
        
        // Add Item to Inventory           addPlayerItem~[Item]
        // Remove Item from Inventory      removePlayerItem~[Item]
        
        // Add Action to Item              addItemAction~[Item]
        // Remove Action from Item         removeItemAction~[Item]
        
        // Move Player to Room             movePlayer~[Room]

        // Chained commands
        // -----------------------------
        // Consume an Item to add an Exit to a room                      removePlayerItem~FDK~addExit~FD~HOR1
        // Create Item in inventory and remove action from first item    addPlayerItem~JM~removeItemAction~SL~OL

        String[] itemCmds;
        int maxActions;

        if (showDebug) {
            System.out.println("  Action Command: " + thingCommand);
        }

        itemCmds = thingCommand.split("~");
        maxActions = itemCmds.length;

        // Time to step through the commands

        System.out.println(description);

        for (int actionPos = 0; actionPos < maxActions; actionPos++) {

            if (showDebug) {
                if ((actionPos + 1) >= maxActions) {
                    System.out.println("    Parsing action "+itemCmds[actionPos]);
                } else {
                    System.out.println("    Parsing action "+itemCmds[actionPos] + "    " + itemCmds[actionPos + 1]);
                }
            }
            
            subAction1 = ""
            subAction2 = ""

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
                    // Add Action to Item              addItemAction~[Item]
                    subAction1 = itemCmds[actionPos + 1];
                    //need to update library, then refresh room or player inventory
                    break;
                    
                case "removeItemAction":
                    // Remove Action from Item         removeItemAction~[Item]
                    subAction1 = itemCmds[actionPos + 1];
                    //need to update library, then refresh room or player inventory
                    break;
                    
                case "movePlayer":
                    // Move Player to Room             movePlayer~[Room]
                    subAction1 = itemCmds[actionPos + 1];
                    System.out.println(description);
                    gameMap.currentRoomID = subAction1;
                    break;
            }
        }
    }


    // Exits are now actions, so taking this out
//    public void useRoomExit(RoomMap gameMap) {
        // using the exit will output the description and then change the current room
    
    public void removeItemAction() {
        // Removes an action from the Item and removes it from the Item in the Item Inventory
        if (showDebug) {
            System.out.println("Running ListThing removeItemAction...");
        }

       // update the item in the Library
       // update the item if it's in the current Room's inventory or in the Player's inventory
    }
}
