package getput.adventures;

import java.util.Scanner;

public class Room {

    private boolean showDebug;
    private ItemLibrary gameItems;
    private ExitLibrary gameExits;
    private ActionLibrary gameActions;
    private ListOfThings playerInventory;
    private RoomMap gameMap;

    public String ID;
    public String name;
    public String description;
    public ListOfThings items;
    public ListOfThings exits;
    public ListOfThings actions;

    private static Scanner reader = new Scanner(System.in);

    public Room(String roomID, String roomName, String roomDescription, String[] roomItems, String[] roomExits,
                String[] roomActions, ItemLibrary maingameItems, ExitLibrary maingameExits,
                ActionLibrary maingameActions, ListOfThings mainplayerInventory, RoomMap maingameMap, boolean mainDebug) {

        showDebug = mainDebug;
        gameItems = maingameItems;
        gameExits = maingameExits;
        gameActions = maingameActions;
        playerInventory = mainplayerInventory;
        gameMap = maingameMap;

        ID = roomID;
        name = roomName;
        description = roomDescription;

        // Right now we'll have a default max of 6 for everything, not sure if I can
        // specified at time of room creation? Still researching.

        items = new ListOfThings("Room Inventory", "RoomInv", 6, gameItems, gameExits, gameActions, showDebug);
        exits = new ListOfThings("Room Exits", "Exits", 6, gameItems, gameExits, gameActions, showDebug);
        actions = new ListOfThings("Room Actions", "Actions", 6, gameItems, gameExits, gameActions, showDebug);

        // Initialise the lists and create the items (if needed)

        items.clearList();
        exits.clearList();
        actions.clearList();

        if (showDebug) {
            System.out.println("-------------------------");
            System.out.println("Starting Room creation...");
        }

        if (showDebug) {
            System.out.println("Creating Items...");
        }

        for (int itemPos = 0; itemPos < roomItems.length; itemPos++) {

            if (showDebug) {
                System.out.println("Checking [" + itemPos + "] " + roomItems[itemPos]);
            }

            if (roomItems[itemPos] != null && !roomItems[itemPos].isEmpty()) {
                items.addItem(roomItems[itemPos], false);
            }
        }

        if (showDebug) {
            System.out.println("-----------------");
            System.out.println("Creating Exits...");
        }

        for (int exitPos = 0; exitPos < roomExits.length; exitPos++) {

            if (showDebug) {
                System.out.println("Checking [" + exitPos + "] " + roomExits[exitPos]);
            }

            if (roomExits[exitPos] != null && !roomExits[exitPos].isEmpty()) {
                exits.addItem(roomExits[exitPos], false);
            }
        }

        if (showDebug) {
            System.out.println("-------------------");
            System.out.println("Creating Actions...");
        }

        for (int actionPos = 0; actionPos < roomActions.length; actionPos++) {

            if (showDebug) {
                System.out.println("Checking [" + actionPos + "] " + roomActions[actionPos]);
            }

            if (roomActions[actionPos] != null && !roomActions[actionPos].isEmpty()) {
                actions.addItem(roomActions[actionPos], false);
            }
        }
        if (showDebug) {
            System.out.println("-------------------------");
        }
    }


    public void printRoom() {

        if (showDebug) {
            System.out.println("Starting Room printRoom...");
        }

        System.out.println("  ");
        System.out.println("  ");
        System.out.println("Room> " + name);
        System.out.println("---------------------------");
        System.out.println(description);
        System.out.println("");
        items.printListOfThings("Pickup item", playerInventory);
        exits.printListOfThings("Use Exit", playerInventory);
        actions.printListOfThings("Do Action", playerInventory);
        playerInventory.printListOfThings("Drop Inventory Item", playerInventory);
        System.out.println("---------------------------");
    }

    public boolean roomAction() {

        if (showDebug) {
            System.out.println("==========================================");
            System.out.println("Starting Room roomAction...");
        }

        String userInput;
        int getFreeSpot;

        System.out.printf("> ");
        userInput = reader.nextLine();

        if (showDebug) {
            System.out.println("Checking to see if the Input null or blank");
        }

        if (userInput == null || userInput.isEmpty()) {
            System.out.println("The universe listens, but does not respond.");

            if (showDebug) {
                System.out.println("==========================================");
            }
            return false;
        }

        if (showDebug) {
            System.out.println("Checking to see if the Input is an Item");
        }

        if (items.isInList(userInput)) {

            getFreeSpot = playerInventory.freeSpot();

            // If it's an Item name, try and pick it up
            if (getFreeSpot == 999) {
                System.out.println("You already have " + items.maxItems + ", you cannot carry anymore.");
                if (showDebug) {
                    System.out.println("==========================================");
                }
                return false;
            } else {

                // Move it from the room inventory to the player inventory
                playerInventory.itemsList[getFreeSpot] = items.transferThing(userInput);
                System.out.println("You pickup the " + userInput + ".");

                if (showDebug) {
                    System.out.println("==========================================");
                }
                return true;
            }
        } else {

            // If it's an Exit, try and move there
            // using the exit will output the description and then change the current room

            if (showDebug) {
                System.out.println("Checking to see if the Input is an Exit");
            }

            int exitPos = exits.posInList(userInput);
            ListThing thisExit;

            if (exitPos < 999) {

                if (showDebug) {
                    System.out.println("Attempting to use the Exit");
                }

                thisExit = gameExits.readItem(userInput);
                thisExit.useRoomExit(gameMap);
                return true;

            } else {

                if (showDebug) {
                    System.out.println("Checking to see if the Input is an Action");
                }

                // If it's an Action, perform the Action
                if (actions.posInList(userInput) < 999) {

                    if (playerInventory.actionInList(userInput)) {

                        if (showDebug) {
                            System.out.println("Attempting to do the Action");
                        }

                        ListThing thisAction = gameActions.readThing(userInput);
                        thisAction.doAction(playerInventory, this, gameItems, gameExits, gameActions);
                        return true;
                    } else {

                        if (showDebug) {
                            System.out.println("Action is not in Player inventory.");
                        }
                        System.out.println("The universe listens, but does not respond.");
                        return false;
                    }

                } else {

                    // If it's an item in the player Inventory, try to drop it

                    if (showDebug) {
                        System.out.println("Trying to drop " + userInput);
                        System.out.println("Checking to see if the item is in the Player Inventory");
                    }

                    if (playerInventory.posInList(userInput) < 999) {

                        if (showDebug) {
                            System.out.println("Checking for a free spot in the Player Inventory");
                        }

                        getFreeSpot = items.freeSpot();

                        // If it's an Item name, try and pick it up
                        if (getFreeSpot == 999) {

                            System.out.println("The room already has " + items.maxItems + ", you cannot drop this here.");

                            if (showDebug) {
                                System.out.println("==========================================");
                            }
                            return false;
                        } else {

                            // Move it from the room inventory to the player inventory
                            items.itemsList[getFreeSpot] = playerInventory.transferThing(userInput);

                            if (showDebug) {
                                System.out.println("Moved item from Player Inventory to the Room Inventory.");
                                System.out.println("==========================================");
                            }
                            return true;
                        }
                    } else {

                        // If we've hit this point, assume no successful action was taken

                        System.out.println("The universe listens, but does not respond.");

                        if (showDebug) {
                            System.out.println("==========================================");
                        }
                        return false;
                    }
                }
            }
        }
    }
}
