package getput.adventures;

public class InventoryItem {

    // Currently Items can only be used once and only in one room

    private boolean showDebug;
    private String itemName;
    private String itemDescription;
    private String itemAction;
    private String itemActionDescription;
    private String itemActionCommand;

    public InventoryItem(String iName, String iAction, String iDesc, String iActionDesc, String iActionCmd, boolean mainDebug) {

        showDebug = mainDebug;
        itemName = iName;
        itemDescription = iDesc;
        itemAction = iAction;
        itemActionDescription = iActionDesc;
        itemActionCommand = iActionCmd;
    }

    public void useItem(ListOfThings playerInventory, Room currentRoom, Boolean mainDebug) {
        // currently using the Item will consume the item and drop it from inventory

        showDebug = mainDebug;
        String[] itemCmds;

        System.out.println(itemActionDescription);
        playerInventory.dropItem(itemName);

        if (showDebug) {
            System.out.println("Item Action Command: " + itemActionCommand);
        }

        itemCmds = itemActionCommand.split("+");

        if (itemCmds[1].equals("spawnItem")) {

            if (showDebug) {
                System.out.println("Spawning item " + itemCmds[2] + " in room " + currentRoom.ID + " " + currentRoom.name);
            }

            currentRoom.items.addItem(itemCmds[2]);
        } else if (itemCmds[1].equals("toggleExit")) {

            if (showDebug) {
                System.out.println("Toggling exit " + itemCmds[2] + " in room " + currentRoom.ID + " " + currentRoom.name);
            }

            // toggle goes here

        } else if (itemCmds[1].equals("Drop")) {

            if (showDebug) {
                System.out.println("Dropping item " + itemCmds[2]);
            }

            currentRoom.items.addItem(itemCmds[2]);
            playerInventory.dropItem(itemCmds[2]);

        } else {
            System.out.println("Unknown item action command!");
        }
    }
}
