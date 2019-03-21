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
    private DisplayMsgs displayMsgs;

    public Room(String roomID, String roomName, String roomDescription, String[] roomItems, String[] roomActions,
                ItemLibrary gameItems, ActionLibrary gameActions, boolean mainDebug) {

        showDebug = mainDebug;
        ID = roomID;
        name = roomName;
        description = roomDescription;
        displayMsgs = new DisplayMsgs(showDebug);

        displayMsgs.debugHeader("Room");

        /*
         * Right now we'll have a static default for the size of the various arrays, not sure if this can be specified
         * at time of room creation?  ...Still researching.
         */

        items = new ListOfThings("Room Inventory", "RoomInv", MAXITEMS, gameItems, gameActions, showDebug);
        actions = new ListOfThings("Room Actions", "Actions", MAXACTIONS, gameItems, gameActions, showDebug);

        // Fetch the Items from the Library
        displayMsgs.debugOutput("  Fetching Items...");

        for (int itemPos = 0; itemPos < roomItems.length; itemPos++) {
            displayMsgs.debugOutput("    Checking [" + itemPos + "] " + roomItems[itemPos]);

            if (roomItems[itemPos] != null && !roomItems[itemPos].isEmpty()) {
                items.addItem(roomItems[itemPos], false);
            }
        }

        // Fetch the Actions from the Library
        displayMsgs.debugShort();
        displayMsgs.debugOutput("  Fetching Actions...");

        for (int actionPos = 0; actionPos < roomActions.length; actionPos++) {
            displayMsgs.debugOutput("    Checking [" + actionPos + "] " + roomActions[actionPos]);

            if (roomActions[actionPos] != null && !roomActions[actionPos].isEmpty()) {
                actions.addItem(roomActions[actionPos], false);
            }
        }
        displayMsgs.debugLong();
    }


    public void printRoom(ListOfThings playerInventory, int turnCounter, int maxTurns) {

        displayMsgs.debugHeader("Room printRoom");

        printRoomHeader(turnCounter, maxTurns);
        displayMsgs.displayOutput(description + " " + items.printListDescriptions());
        printAvailableActions(playerInventory);

        displayMsgs.debugLong();
    }

    private void printRoomHeader(int turnCounter, int maxTurns) {

        String headerText;
        String turnsDisplay;
        int lenSpaces;

        displayMsgs.debugHeader("Room printRoomHeader");

        turnsDisplay = "Turn " + turnCounter + "/" + maxTurns;
        headerText = name + turnsDisplay;
        lenSpaces = (displayMsgs.displayWidth - headerText.length());

        displayMsgs.displayOutput(" ");
        displayMsgs.displayOutput(name + " ".repeat(lenSpaces) + turnsDisplay);
        displayMsgs.displayOutput("-".repeat(displayMsgs.displayWidth));

        displayMsgs.debugLong();
    }

    private void printAvailableActions(ListOfThings playerInventory) {

        displayMsgs.debugHeader("Room printAvailableActions");

        displayMsgs.displayOutput(" ");
        displayMsgs.displayOutput("-".repeat(14));
        items.printListOfThings("Room items", playerInventory);
        displayMsgs.displayOutput("[Do Action ] : " + getRoomActions(playerInventory));
        playerInventory.printListOfThings("Drop Items", playerInventory);
        displayMsgs.displayOutput("-".repeat(displayMsgs.displayWidth));
        displayMsgs.debugLong();
    }


    public String getRoomActions(ListOfThings playerInventory) {

        // Available Actions can come from the Room, the Items in the Room, the Items in the Player Inventory
        // or a combination.

        displayMsgs.debugHeader("Room getRoomActions");

        String actionList = "";

        String roomItemActions = this.items.stringOfActions(playerInventory, this);
        String playerActions = playerInventory.stringOfActions(playerInventory, this);
        String roomActions = actions.stringOfActions(playerInventory, this);

        if (!roomItemActions.isEmpty()) {
            actionList = roomItemActions;
        }

        displayMsgs.debugOutput("  List with only Room Item Actions");
        displayMsgs.debugOutput("    " + actionList);

        if (!playerActions.isEmpty()) {
            if (actionList.isEmpty()) {
                actionList = playerActions;
            } else {
                actionList = addNoDuplicates(actionList, playerActions);
            }
        }

        displayMsgs.debugOutput("  List with Room and Player Item Actions");
        displayMsgs.debugOutput("    " + actionList);

        if (!roomActions.isEmpty()) {
            if (actionList.isEmpty()) {
                actionList = roomActions;
            } else {
                actionList = addNoDuplicates(actionList, roomActions);
            }
        }

        displayMsgs.debugOutput("  List with Room and Player Item Actions and Room Actions");
        displayMsgs.debugOutput("    " + actionList);
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

    public boolean roomAction(ItemLibrary gameItems, ActionLibrary gameActions, ListOfThings playerInventory, RoomMap gameMap, int adventureLoop, int maxTurns) {

        // Get input from the user, only return false if they want to exit the game

        displayMsgs.debugHeader("Room roomAction");

        String userInput;
        String checkAction;
        String validActions = getRoomActions(playerInventory);

        System.out.printf("> ");
        userInput = reader.nextLine();

        switch (userInput.toUpperCase()) {
            case "":
                displayMsgs.displayMessage("BadAction", true);
                return true;
            case "EXIT":
                return false;
            case "LOOK":
                this.printRoom(playerInventory, adventureLoop, maxTurns);
                return true;
        }

        int itemPos = items.posInList(userInput);
        int actionPos = actions.posInList(userInput);
        int inventoryPos = playerInventory.posInList(userInput);
        int playerActionPos = playerInventory.actionInList(userInput);

        displayMsgs.debugOutput("  Checking the input to see if it's valid.");

        if (itemPos < 999) {
            displayMsgs.debugOutput("    Trying to pick it up.");
            pickItUp(userInput, itemPos, playerInventory);
        } else {
            if ((actionPos < 999) || (playerActionPos < 999)) {
                displayMsgs.debugOutput("    It's an action, is it valid?");
                checkAction = gameActions.findName(userInput);
                displayMsgs.debugOutput("    Valid actions are: " + validActions);
                displayMsgs.debugOutput("    Checking: " + checkAction);
                if (validActions.contains(checkAction)) {
                    displayMsgs.debugOutput("      Yup, it's valid, try to do the thing!");
                    ListThing thisAction = gameActions.readAction(userInput);
                    if (thisAction.doAction(gameItems, gameActions, gameMap, playerInventory, this)) {
                        printAvailableActions(playerInventory);
                    }
                    displayMsgs.debugLong();
                } else {
                    displayMsgs.debugOutput("      Nope, not valid!");
                    displayMsgs.displayMessage("BadAction", true);
                }
            } else {
                if (inventoryPos < 999) {
                    displayMsgs.debugOutput("    Trying to drop the thing!");
                    dropIt(userInput, playerInventory);
                } else {
                    displayMsgs.debugOutput("      Input not recognized.");
                    displayMsgs.displayMessage("BadAction",  true);
                }
            }
        }
        displayMsgs.debugLong();
        return true;
    }

    private void pickItUp(String userInput, int itemPos, ListOfThings playerInventory) {

        int nextFreeSpot = playerInventory.freeSpot();
        ListThing roomItem = items.getListThing(itemPos);
        String itemName = roomItem.getName();

        displayMsgs.debugHeader("Room pickItUp");

        if (!roomItem.getCanPickup().equalsIgnoreCase("Y")) {
            displayMsgs.displayMessage("CantPickUp", itemName, true);
        } else {
            if (nextFreeSpot == 999) {
                displayMsgs.displayMessage("PlayerInventoryFull",  true);
            } else {
                playerInventory.setListThing(nextFreeSpot, items.transferThing(userInput));
                displayMsgs.displayMessage("PlayerInventoryItemAdded", itemName, true);
                printAvailableActions(playerInventory);
            }
        }
        displayMsgs.debugLong();
    }

    private void dropIt(String userInput, ListOfThings playerInventory) {

        displayMsgs.debugHeader("Room dropIt");

        int getFreeSpot = items.freeSpot();
        String itemName = playerInventory.findName(userInput);

        if (getFreeSpot == 999) {
            displayMsgs.displayMessage("CantDropThat", itemName, true);
        } else {
            items.setListThing(getFreeSpot, playerInventory.transferThing(userInput));
            displayMsgs.displayMessage("DroppedThat", itemName, true);
            printAvailableActions(playerInventory);
        }

        displayMsgs.debugLong();
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