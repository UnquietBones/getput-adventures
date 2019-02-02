package getputadventures;

import java.util.Scanner;

public class Room {

    // Changes Made
    //  Turned the debug dash-breaks into statics so they are consistent
    //  Changed all println with variables to printf's.
    //  Changed maxItems to a static
    //  Added a MAXACTIONS
    //  Tried to remove as many global variables as possible
    //  Added in handling for the RO Actions (that require a Room and an Item)

    public static int MAXITEMS = 6;
    public static int MAXACTIONS = 12;
    private static String LONGDASH = "--------------------------------";
    private static String SHORTDASH = "----------------";
    private static Scanner reader = new Scanner(System.in);
    public String ID;
    public String name;
    public String description;
    public ListOfThings items;
    public ListOfThings actions;
    private boolean showDebug;

    public Room(String roomID, String roomName, String roomDescription, String[] roomItems, String[] roomActions,
                ItemLibrary gameItems, ActionLibrary gameActions, boolean mainDebug) {

        showDebug = mainDebug;
        ID = roomID;
        name = roomName;
        description = roomDescription;

        // Right now we'll have a static default, not sure if I can  specified at time of room creation?
        // Still researching.

        items = new ListOfThings("Room Inventory", "RoomInv", MAXITEMS, gameItems, gameActions, showDebug);
        actions = new ListOfThings("Room Actions", "Actions", MAXACTIONS, gameItems, gameActions, showDebug);

        // Initialise the lists and create the items (if needed)

        items.clearList();
        actions.clearList();

        if (showDebug) {
            System.out.println(LONGDASH);
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
            System.out.println(SHORTDASH);
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
        if (showDebug) {
            System.out.println(LONGDASH);
        }
    }


    public void printRoom(ListOfThings playerInventory) {

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("Room printRoom");
        }

        System.out.println("  ");
        System.out.println("  ");
        System.out.println(name);
        System.out.println(SHORTDASH);
        System.out.print(description);
        items.printListDescriptions();
        System.out.println("");
        items.printListOfThings("Pickup item", playerInventory);
        actions.printListOfThings("Do Action", playerInventory);
        playerInventory.printListOfThings("Drop Inventory Item", playerInventory);

        if (showDebug) {
            System.out.println(LONGDASH);
        }
    }

    public boolean roomAction(ItemLibrary gameItems, ActionLibrary gameActions, ListOfThings playerInventory, RoomMap gameMap) {

        // Get input from the user, only return false if they want to exit the game

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("Room roomAction");
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
                System.out.println(LONGDASH);
            }
            return true;
        }

        if (showDebug) {
            System.out.println("Checking to see if they want to exit the game.");
        }

        if (userInput.equalsIgnoreCase("Exit")) {
            return false;
        }

        if (showDebug) {
            System.out.println("  Check to see if they want to reprint the room.");
        }

        if (userInput.equalsIgnoreCase("Look")) {
            this.printRoom(playerInventory);
        }

        if (showDebug) {
            System.out.println("  Checking to see if the Input is an Item.");
        }

        int itemPos = items.posInList(userInput);

        if (itemPos < 999) {

            // Check to see if it's an Item that can be picked up

            if (!items.itemsList[itemPos].canPickup.equalsIgnoreCase("Y")) {

                System.out.printf("%s is not an item that can be picked up.", items.itemsList[itemPos].name);
                return true;
            }

            // Check to see if the Player has room to pick it up

            getFreeSpot = playerInventory.freeSpot();

            if (getFreeSpot == 999) {
                System.out.println("You already have " + MAXITEMS + ", you cannot carry anymore.");
                if (showDebug) {
                    System.out.println(LONGDASH);
                }
                return true;
            } else {

                // Move it from the room inventory to the player inventory
                playerInventory.itemsList[getFreeSpot] = items.transferThing(userInput);
                System.out.println("You pickup the " + userInput + ".");

                if (showDebug) {
                    System.out.println(LONGDASH);
                }
                return true;
            }
        } else {

            if (showDebug) {
                System.out.println("Checking to see if the Input is an Action");
            }

            // If it's an Action, perform the Action

            if (actions.posInList(userInput) < 999) {
                if (showDebug) {
                    System.out.println("  This is a valid Action.");
                }

                // If this is a Room+Item action, check to make sure it's valid

                if ((actions.listType.equalsIgnoreCase("RO"))) {
                    if (playerInventory.actionInList(userInput) == 999) {
                        if (showDebug) {
                            System.out.println("  The matching Item for this Action is missing from Player Inventory.");
                        }

                        System.out.println("The universe listens, but does not respond.");
                        return true;
                    } else {
                        if (showDebug) {
                            System.out.println("  The matching Item for this Action is in Player Inventory.");
                        }
                    }
                }

                if (showDebug) {
                    System.out.println("  Attempting to do the Action");
                }

                ListThing thisAction = gameActions.readThing(userInput);
                thisAction.doAction(gameItems, gameActions, gameMap, playerInventory, this);
                return true;
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

                        System.out.println("The room already has " + MAXITEMS + ", you cannot drop this here.");

                        if (showDebug) {
                            System.out.println(LONGDASH);
                        }
                        return true;
                    } else {

                        // Move it from the room inventory to the player inventory
                        items.itemsList[getFreeSpot] = playerInventory.transferThing(userInput);

                        if (showDebug) {
                            System.out.println("Moved item from Player Inventory to the Room Inventory.");
                            System.out.println(LONGDASH);
                        }
                        return true;
                    }
                } else {

                    // If we've hit this point, assume no successful action was taken

                    System.out.println("The universe listens, but does not respond.");

                    if (showDebug) {
                        System.out.println(LONGDASH);
                    }
                    return true;
                }
            }
        }
    }
}