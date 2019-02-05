package getputadventures;

import java.util.Scanner;

public class Room {

    // List of methods so I can keep myself straight as I build this thing
    //	public void printRoom(ListOfThings playerInventory)
    //	public String getRoomActions(ListOfThings playerInventory)
    //	private String addNoDuplicates(String oldString, String newString)
    //	public boolean roomAction(ItemLibrary gameItems, ActionLibrary gameActions, ListOfThings playerInventory, RoomMap gameMap)
    //	private void pickItUp(String userInput, int itemPos, ListOfThings playerInventory)
    //	private void dropIt(String userInput, int itemPos, ListOfThings playerInventory)
    //	private void badRoomAction()
    //	private void debugLong()
    //	private void debugShort()
    //	private void debugOutput(String outputThis)

    private static int MAXITEMS = 6;
    private static int MAXACTIONS = 12;
    private static Scanner reader = new Scanner(System.in);
    public String ID;
    public String name;
    private String description;
    private ListOfThings items;
    private ListOfThings actions;
    private boolean showDebug;

    public Room(String roomID, String roomName, String roomDescription, String[] roomItems, String[] roomActions,
                ItemLibrary gameItems, ActionLibrary gameActions, boolean mainDebug) {

        showDebug = mainDebug;
        ID = roomID;
        name = roomName;
        description = roomDescription;

        /*
         * Right now we'll have a static default, not sure if I can  specified at time of room creation?
         * Still researching.
         */

        items = new ListOfThings("Room Inventory", "RoomInv", MAXITEMS, gameItems, gameActions, showDebug);
        actions = new ListOfThings("Room Actions", "Actions", MAXACTIONS, gameItems, gameActions, showDebug);

        // Initialise the lists and create the items (if needed)

        items.clearList();
        actions.clearList();

        if (showDebug) {
            debugLong();
            System.out.println("Room");
        }

        // Fetch the Items from the Library

        if (showDebug) {
            System.out.println("  Fetching Items...");
        }

        for (int itemPos = 0; itemPos < roomItems.length; itemPos++) {

            if (showDebug) {
                System.out.printf("    Checking [%d] %s %n", itemPos, roomItems[itemPos]);
            }

            if (roomItems[itemPos] != null && !roomItems[itemPos].isEmpty()) {
                items.addItem(roomItems[itemPos], false);
            }
        }

        // Fetch the Actions from the Library

        if (showDebug) {
            debugShort();
            System.out.println("  Fetching Actions...");
        }

        for (int actionPos = 0; actionPos < roomActions.length; actionPos++) {

            if (showDebug) {
                System.out.printf("    Checking [%d] %s %n", actionPos, roomActions[actionPos]);
            }

            if (roomActions[actionPos] != null && !roomActions[actionPos].isEmpty()) {
                actions.addItem(roomActions[actionPos], false);
            }
        }
        debugLong();
    }


    public void printRoom(ListOfThings playerInventory) {

        // This prints the room and items descriptions and then prints the available commands

        if (showDebug) {
            debugLong();
            System.out.println("Room printRoom");
        }

        System.out.println("  ");
        System.out.println("  ");
        System.out.println(name);
        debugShort();
        System.out.print(description);
        items.printListDescriptions();
        System.out.println("");
        items.printListOfThings("Pickup item", playerInventory);
        System.out.printf("  Do Action : %s %n", getRoomActions(playerInventory));
        playerInventory.printListOfThings("Drop Inventory Item", playerInventory);

        if (showDebug) {
            debugLong();
        }
    }

    public String getRoomActions(ListOfThings playerInventory) {

        // Available Actions can come from the Room, the Items in the Room, the Items in the Player Inventory
        // or a combination.

        debugLong();
        debugOutput("Room getRoomActions");

        String actionList = "";

        String roomItemActions = this.items.stringOfActions(playerInventory, this);
        String playerActions = playerInventory.stringOfActions(playerInventory, this);
        String roomActions = actions.stringOfActions(playerInventory, this);

        if (!roomItemActions.isEmpty()) {
            actionList = roomItemActions;
        }

        debugOutput("List with Room Item Actions");
        debugOutput(actionList);

        if (!playerActions.isEmpty()) {
            if (actionList.isEmpty()) {
                actionList = playerActions;
            } else {
                actionList = addNoDuplicates(actionList, playerActions);
            }
        }

        debugOutput("List with Room Item and Player Actions");
        debugOutput(actionList);

        if (!roomActions.isEmpty()) {
            if (actionList.isEmpty()) {
                actionList = roomActions;
            } else {
                actionList = addNoDuplicates(actionList, roomActions);
            }
        }

        debugOutput("List with Room Item and Player and Room Actions");
        debugOutput(actionList);
        return actionList;
    }

    private String addNoDuplicates(String oldString, String newString) {
        // Add the new comma separated values in newString to oldString, but only if they aren't already in the list

        String returnString;
        String testThis;
        returnString = oldString;

        String[] tempNew = newString.split(",");
        int tempCount = tempNew.length;

        for (int newStringPos = 0; newStringPos < tempCount; newStringPos++) {
            testThis = tempNew[newStringPos];
            if (!returnString.contains(testThis)) {
                returnString += ", " + testThis;
            }
        }
        return returnString;
    }

    public boolean roomAction(ItemLibrary gameItems, ActionLibrary gameActions, ListOfThings playerInventory, RoomMap gameMap) {

        // Get input from the user, only return false if they want to exit the game

        debugLong();
        debugOutput("Room roomAction");

        String userInput;
        String validActions = getRoomActions(playerInventory);

        System.out.printf("> ");
        userInput = reader.nextLine();

        switch (userInput) {
            case "":
                badRoomAction();
                return true;
            case "EXIT":
                return false;
            case "LOOK":
                this.printRoom(playerInventory);
                return true;
        }

        int itemPos = items.posInList(userInput);
        int actionPos = actions.posInList(userInput);
        int inventoryPos = playerInventory.posInList(userInput);
        int playerActionPos = playerInventory.actionInList(userInput);

        if (itemPos < 999) {
            pickItUp(userInput, itemPos, playerInventory);
        } else {
            if ((actionPos < 999) || (playerActionPos < 999)) {
                if (validActions.contains(userInput)) {
                    ListThing thisAction = gameActions.readThing(userInput);
                    thisAction.doAction(gameItems, gameActions, gameMap, playerInventory, this);
                    debugLong();
                } else {
                    badRoomAction();
                }
            } else {
                if (inventoryPos < 999) {
                    dropIt(userInput, itemPos, playerInventory);
                } else {
                    // If we've hit this point, assume no successful action was taken
                    badRoomAction();
                }
            }
        }
        debugLong();
        return true;
    }

    private void pickItUp(String userInput, int itemPos, ListOfThings playerInventory) {

        ListThing roomItem = items.getListThing(itemPos);

        if (!roomItem.getCanPickup().equalsIgnoreCase("Y")) {
            System.out.printf("%s is not an item that can be picked up. %n", roomItem.getName());
        } else {
            int getFreeSpot = playerInventory.freeSpot();
            if (getFreeSpot == 999) {
                System.out.printf("You already have %d items, you cannot carry anymore. %n", MAXITEMS);
            } else {
                // Move it from the room inventory to the player inventory
                playerInventory.setListThing(getFreeSpot, items.transferThing(userInput));
                System.out.printf("%s has been added to your inventory. %n", playerInventory.getListThing(getFreeSpot).getName());
            }
        }
    }

    private void dropIt(String userInput, int itemPos, ListOfThings playerInventory) {

        int getFreeSpot = items.freeSpot();

        if (getFreeSpot == 999) {
            System.out.printf("The room already has %d items, you cannot drop this here.", MAXITEMS);
        } else {
            // Move it from the room inventory to the player inventory
            items.setListThing(getFreeSpot, playerInventory.transferThing(userInput));
        }
    }

    public ListOfThings getActions() {
        return actions;
    }

    public ListOfThings getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    private void badRoomAction() {

        System.out.println("The universe listens, but does not respond.");
    }

    private void debugLong() {
        if (showDebug) {
            System.out.println("--------------------------------");
        }
    }

    private void debugShort() {
        if (showDebug) {
            System.out.println("----------------");
        }
    }

    private void debugOutput(String outputThis) {
        if (showDebug) {
            System.out.println(outputThis);
        }
    }
}