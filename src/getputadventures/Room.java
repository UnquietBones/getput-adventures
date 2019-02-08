package getputadventures;

import java.util.Scanner;

public class Room {

    private static int MAXITEMS = 6;
    private static int MAXACTIONS = 12;
    private static Scanner reader = new Scanner(System.in);
    public String ID;
    public String name;
    private String description;
    private ListOfThings items;
    private ListOfThings actions;
    private boolean showDebug;
    private DebugMsgs debugMessage;

    public Room(String roomID, String roomName, String roomDescription, String[] roomItems, String[] roomActions,
                ItemLibrary gameItems, ActionLibrary gameActions, boolean mainDebug) {

        showDebug = mainDebug;
        ID = roomID;
        name = roomName;
        description = roomDescription;
        debugMessage = new DebugMsgs(showDebug);

        debugMessage.debugLong();
        debugMessage.debugOutput("Room");

        /*
         * Right now we'll have a static default for the size of the various arrays, not sure if this can be specified
         * at time of room creation?  ...Still researching.
         */

        items = new ListOfThings("Room Inventory", "RoomInv", MAXITEMS, gameItems, gameActions, showDebug);
        actions = new ListOfThings("Room Actions", "Actions", MAXACTIONS, gameItems, gameActions, showDebug);

        // Initialise the lists since most rooms won't have all the slots filled

        debugMessage.debugOutput("  Clearing the Items list");
        items.clearList();

        debugMessage.debugOutput("  Clearing the Actions list");
        actions.clearList();

        // Fetch the Items from the Library
        debugMessage.debugOutput("  Fetching Items...");

        for (int itemPos = 0; itemPos < roomItems.length; itemPos++) {
            debugMessage.debugOutput("    Checking [" + itemPos + "] " + roomItems[itemPos]);

            if (roomItems[itemPos] != null && !roomItems[itemPos].isEmpty()) {
                items.addItem(roomItems[itemPos], false);
            }
        }

        // Fetch the Actions from the Library
        debugMessage.debugShort();
        debugMessage.debugOutput("  Fetching Actions...");

        for (int actionPos = 0; actionPos < roomActions.length; actionPos++) {
            debugMessage.debugOutput("    Checking [" + actionPos + "] " + roomActions[actionPos]);

            if (roomActions[actionPos] != null && !roomActions[actionPos].isEmpty()) {
                actions.addItem(roomActions[actionPos], false);
            }
        }
        debugMessage.debugLong();
    }


    public void printRoom(ListOfThings playerInventory) {

        // This prints the room and items descriptions and then prints the available commands

        if (showDebug) {
            debugMessage.debugLong();
            System.out.println("Room printRoom");
        }

        System.out.println("  ");
        System.out.println("  ");
        System.out.println(name);
        debugMessage.debugShort();
        System.out.print(description);
        items.printListDescriptions();
        System.out.println("");
        items.printListOfThings("Room items", playerInventory);
        System.out.printf("  Do Action : %s %n", getRoomActions(playerInventory));
        playerInventory.printListOfThings("Drop Inventory Item", playerInventory);

        if (showDebug) {
            debugMessage.debugLong();
        }
    }

    public String getRoomActions(ListOfThings playerInventory) {

        // Available Actions can come from the Room, the Items in the Room, the Items in the Player Inventory
        // or a combination.

        debugMessage.debugLong();
        debugMessage.debugOutput("Room getRoomActions");

        String actionList = "";

        String roomItemActions = this.items.stringOfActions(playerInventory, this);
        String playerActions = playerInventory.stringOfActions(playerInventory, this);
        String roomActions = actions.stringOfActions(playerInventory, this);

        if (!roomItemActions.isEmpty()) {
            actionList = roomItemActions;
        }

        debugMessage.debugOutput("  List with only Room Item Actions");
        debugMessage.debugOutput("    "+actionList);

        if (!playerActions.isEmpty()) {
            if (actionList.isEmpty()) {
                actionList = playerActions;
            } else {
                actionList = addNoDuplicates(actionList, playerActions);
            }
        }

        debugMessage.debugOutput("  List with Room and Player Item Actions");
        debugMessage.debugOutput("    "+actionList);

        if (!roomActions.isEmpty()) {
            if (actionList.isEmpty()) {
                actionList = roomActions;
            } else {
                actionList = addNoDuplicates(actionList, roomActions);
            }
        }

        debugMessage.debugOutput("  List with Room and Player Item Actions and Room Actions");
        debugMessage.debugOutput("    "+actionList);
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

        debugMessage.debugLong();
        debugMessage.debugOutput("Room roomAction");

        String userInput;
        String checkAction;
        String validActions = getRoomActions(playerInventory);

        System.out.printf("> ");
        userInput = reader.nextLine();

        switch (userInput.toUpperCase()) {
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

        debugMessage.debugOutput("  Checking the input to see if it's valid.");

        if (itemPos < 999) {
            debugMessage.debugOutput("    Trying to pick it up.");
            pickItUp(userInput, itemPos, playerInventory);
        } else {
            if ((actionPos < 999) || (playerActionPos < 999)) {
                debugMessage.debugOutput("    It's an action, is it valid?");
                checkAction = gameActions.findName(userInput);
                debugMessage.debugOutput("    Valid actions are: " + validActions);
                debugMessage.debugOutput("    Checking: " + checkAction);
                if (validActions.contains(checkAction)) {
                    debugMessage.debugOutput("      Yup, it's valid, try to do the thing!");
                    ListThing thisAction = gameActions.readAction(userInput);
                    thisAction.doAction(gameItems, gameActions, gameMap, playerInventory, this);
                    debugMessage.debugLong();
                } else {
                    debugMessage.debugOutput("      Nope, not valid!");
                    badRoomAction();
                }
            } else {
                if (inventoryPos < 999) {
                    debugMessage.debugOutput("    Trying to drop the thing!");
                    dropIt(userInput, playerInventory);
                } else {
                    // If we've hit this point, assume no successful action was taken
                    debugMessage.debugOutput("      Input not recognized.");
                    badRoomAction();
                }
            }
        }
        debugMessage.debugLong();
        return true;
    }

    private void badRoomAction() {

        System.out.println("The universe listens, but does not respond.");
    }

    private void pickItUp(String userInput, int itemPos, ListOfThings playerInventory) {

        /*
         * This looks at the item in the given position in the list of Room Items and checks to see if it can be
         * picked up. If it can, then it will try to add the item to the Player's Inventory.
         */

        debugMessage.debugLong();
        debugMessage.debugOutput("Room pickItUp");

        ListThing roomItem = items.getListThing(itemPos);

        if (!roomItem.getCanPickup().equalsIgnoreCase("Y")) {
            System.out.printf("%s is not an item that can be picked up. %n", roomItem.getName());
        } else {
            int getFreeSpot = playerInventory.freeSpot();

            if (getFreeSpot == 999) {
                System.out.printf("You already have %d items, you cannot carry anymore. %n", MAXITEMS);
            } else {
                playerInventory.setListThing(getFreeSpot, items.transferThing(userInput));
                System.out.printf("%s has been added to your inventory. %n", playerInventory.getListThing(getFreeSpot).getName());
            }
        }

        debugMessage.debugLong();
    }

    private void dropIt(String userInput, ListOfThings playerInventory) {

        /*
         * This will attempt to move the item from the Player Inventory to the Room Inventory. If the room has no open
         * slots then the drop will fail.
         */

        debugMessage.debugLong();
        debugMessage.debugOutput("Room dropIt");

        int getFreeSpot = items.freeSpot();

        if (getFreeSpot == 999) {
            System.out.printf("The room already has %d items, you cannot drop this here.", MAXITEMS);
        } else {
            items.setListThing(getFreeSpot, playerInventory.transferThing(userInput));
        }

        debugMessage.debugLong();
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
}