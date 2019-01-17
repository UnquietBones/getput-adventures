package getputadventures;

public class ListOfThings {

    // This can be a list of Items, Exits, or Actions

    // Currently Items can only be used once and only in one room
    //           Actions are used to link an Item to Room
    //           Exits can be used multiple times

    private boolean showDebug;
    private ItemLibrary gameItems;
    private ExitLibrary gameExits;
    private ActionLibrary gameActions;

    public String listType;          // RoomInv, PlayerInv, Exits, Actions
    public String listName;
    public int maxItems;
    public ListThing[] itemsList;

    public ListOfThings(String newListName, String newListType, int newMaxItems, ItemLibrary newgameItems, ExitLibrary newgameExits, ActionLibrary newgameActions, boolean mainDebug) {

        showDebug = mainDebug;
        listType = newListType;
        listName = newListName;
        maxItems = newMaxItems;
        itemsList = new ListThing[maxItems];
        gameItems = newgameItems;
        gameExits = newgameExits;
        gameActions = newgameActions;

        clearList();
    }

    public void clearList() {

        // Clear out the list, so we avoid nulls later

        if (showDebug) {
            System.out.println("----------------------------------");
            System.out.println("Starting ListOfThings clearList...");
        }

        if (showDebug) {
            System.out.println("Expecting " + maxItems + " maxItems in " + listName);
            System.out.println("Found " + itemsList.length + " items.");
        }

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {

            if (showDebug) {
                System.out.println("Set item [" + itemCounter + "] to empty strings.");
            }

            if (itemsList[itemCounter] == null) {

                itemsList[itemCounter] = new ListThing("", "", "", "",
                        "", "", "", showDebug);
            } else {
                itemsList[itemCounter].clearThing();
            }
        }
        if (showDebug) {
            System.out.println("----------------------------------");
        }
    }

    public boolean addItem(String newItem, boolean showMsg) {

        // This will find the first empty slot in the list, add the item, and
        // return true. If there are no empty slots it will return false.

        if (showDebug) {
            System.out.println("--------------------------------");
            System.out.println("Starting ListOfThings addItem...");
            System.out.println("Searching for item " + newItem + " in " + listName + " " + listType);
        }

        for (int itemCounter = 0; itemCounter <= maxItems; itemCounter++) {
            if (showDebug) {
                System.out.println("Checking for empty at [" + itemCounter + "] " + itemsList[itemCounter].name);
            }
            if (itemsList[itemCounter].name.isEmpty()) {

                switch (listType) {
                    case "RoomInv":
                        itemsList[itemCounter] = gameItems.readThing(newItem);
                        if (showMsg) {
                            System.out.println("A " + itemsList[itemCounter].name + " appears in the room.");
                        }
                        break;
                    case "PlayerInv":
                        itemsList[itemCounter] = gameItems.readThing(newItem);
                        if (showMsg) {
                            System.out.println("You have picked up the " + itemsList[itemCounter].name + ".");
                        }
                        break;
                    case "Exits":
                        itemsList[itemCounter] = gameExits.readItem(newItem);
                        if (showMsg) {
                            System.out.println("Exit " + itemsList[itemCounter].name + " is now available.");
                        }
                        break;
                    case "Actions":
                        itemsList[itemCounter] = gameActions.readThing(newItem);
                        if (showMsg) {
                            System.out.println("You can now " + itemsList[itemCounter].name + ".");
                        }
                        break;
                }
                if (showDebug) {
                    System.out.println("--------------------------------");
                }
                return true;
            }
        }
        if (showMsg) {
            System.out.println(listName + " is full!");
        }
        if (showDebug) {
            System.out.println("--------------------------------");
        }
        return false;
    }


    public void removeThing(String thisThing, boolean showMsg) {

        // This will remove the Thing from the list, if found.

        if (showDebug) {
            System.out.println("---------------------------------");
            System.out.println("Starting ListOfThings removeThing...");
            System.out.println("Trying to drop item " + thisThing + " from " + listType + " " + listType);
        }

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {

            if (showDebug) {
                System.out.println("Checking [" + itemCounter + "] " + itemsList[itemCounter].id + itemsList[itemCounter].name);
            }
            if (itemsList[itemCounter].id.equalsIgnoreCase(thisThing) || itemsList[itemCounter].name.equalsIgnoreCase(thisThing)) {
                itemsList[itemCounter].clearThing();
                if (showMsg) {
                    System.out.println(itemsList[itemCounter].name + " has been removed from " + listName + ".");
                }
                if (showDebug) {
                    System.out.println("---------------------------------");
                }
                return;
            }
        }

        if (showMsg) {
            System.out.println("Item " + thisThing + " was not in " + listName);
        }
        if (showDebug) {
            System.out.println("---------------------------------");
        }
    }

    public int freeSpot() {

        // This will check to see if there is an empty spot in the list

        if (showDebug) {
            System.out.println("---------------------------------");
            System.out.println("Starting ListOfThings freeSpot...");
            System.out.println("Looking for an empty slot in " + listType + " " + listName);
        }

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {
            if (itemsList[itemCounter] == null || itemsList[itemCounter].name.isEmpty()) {

                if (showDebug) {
                    System.out.println("Found empty spot at [" + itemCounter + "].");
                    System.out.println("---------------------------------");
                }
                return itemCounter;
            } else {

                if (showDebug) {
                    System.out.println("Checking [" + itemCounter + "] " + itemsList[itemCounter].id +
                            itemsList[itemCounter].name);
                }

            }
        }

        if (showDebug) {
            System.out.println("There are no empty spots in the list.");
            System.out.println("---------------------------------");
        }
        return 999;
    }

    public ListThing transferThing(String thisThing) {

        // This will remove the Thing from the list, to pass to a new list.

        ListThing tempThing;
        tempThing = null;

        if (showDebug) {
            System.out.println("---------------------------------");
            System.out.println("Starting ListOfThings transferThing...");
            System.out.println("Trying to remove item " + thisThing);
        }

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {

            if (itemsList[itemCounter] != null) {

                if (showDebug) {
                    System.out.println("Checking [" + itemCounter + "] " + itemsList[itemCounter].id +
                            itemsList[itemCounter].name);
                }
                if (itemsList[itemCounter].id.equalsIgnoreCase(thisThing) || itemsList[itemCounter].name.equalsIgnoreCase(thisThing)) {

                    // Move pointer into a temp variable to pass it out and clear it from the list
                    tempThing = itemsList[itemCounter];
                    itemsList[itemCounter] = null;

                    if (showDebug) {
                        System.out.println("Passing over " + thisThing + " from " + listName);
                        System.out.println("---------------------------------");
                    }
                    return tempThing;
                }
            }
        }

        if (showDebug) {
            System.out.println("Item " + thisThing + " was not in " + listName);
            System.out.println("---------------------------------");
        }
        return tempThing;
    }

    public boolean isInList(String findID) {

        // We're trying to match up a name or ID to a Thing in the list

        if (showDebug) {
            System.out.println("---------------------------------");
            System.out.println("Starting ListOfThings isInList...");
            System.out.println("Looking for " + findID + " in " + listType + " " + listName);
        }

        int itemCount = itemsList.length - 1;

        for (int itemCounter = 0; itemCounter <= (itemCount); itemCounter++) {

            if (itemsList[itemCounter] != null && !itemsList[itemCounter].name.isEmpty()) {

                if (showDebug) {
                    System.out.println("[" + itemCounter + "] " + itemsList[itemCounter].id + " " + itemsList[itemCounter].name);
                }

                if (itemsList[itemCounter].id.equalsIgnoreCase(findID) || itemsList[itemCounter].name.equalsIgnoreCase(findID)) {
                    return true;
                }
            } else {
                if (showDebug) {
                    System.out.println("[" + itemCounter + "] is empty.");
                }
            }
        }

        return false;
    }

    public int posInList(String findID) {

        // Find the position of the Thing in the List

        if (showDebug) {
            System.out.println("----------------------------------");
            System.out.println("Starting ListOfThings posInList...");
            System.out.println("Looking for " + findID + " in " + listType + " " + listName);
        }

        // Returns the position of the item in the list or 999

        int itemCount = itemsList.length - 1;

        for (int itemCounter = 0; itemCounter <= (itemCount); itemCounter++) {

            if (itemsList[itemCounter] != null) {

                if (showDebug) {
                    System.out.println("[" + itemCounter + "] " + itemsList[itemCounter].id + " " + itemsList[itemCounter].name);
                }

                if (itemsList[itemCounter].id.equalsIgnoreCase(findID) || itemsList[itemCounter].name.equalsIgnoreCase(findID)) {
                    if (showDebug) {
                        System.out.println("Found " + findID + " at " + itemCounter);
                        System.out.println("----------------------------------");
                    }
                    return itemCounter;
                }
            }
        }

        // Couldn't find it, so return default
        if (showDebug) {
            System.out.println("Didn't find " + findID);
            System.out.println("----------------------------------");
        }
        return 999;
    }

    public boolean actionInList(String findAction) {

        // Here we are checking a list of Items to see if any of them has the given Action

        if (showDebug) {
            System.out.println("-------------------------------------");
            System.out.println("Starting ListOfThings actionInList...");
            System.out.println("Looking for " + findAction + " in " + listType + " " + listName);
        }

        findAction = gameActions.getID(findAction);

        int itemCount = itemsList.length - 1;

        for (int itemCounter = 0; itemCounter <= (itemCount); itemCounter++) {
            if (itemsList[itemCounter] == null || itemsList[itemCounter].name.isEmpty()) {
                if (showDebug) {
                    System.out.println("[" + itemCounter + "] is null");
                }
            } else {

                if (showDebug) {
                    System.out.println("[" + itemCounter + "] " + itemsList[itemCounter].name + " " + itemsList[itemCounter].actionID);
                    System.out.println("-------------------------------------");
                }

                if (itemsList[itemCounter].actionID.equalsIgnoreCase(findAction)) {
                    return true;
                }
            }
        }

        if (showDebug) {
            System.out.println("No matching Action found.");
            System.out.println("-------------------------------------");
        }
        return false;
    }


    public void printListOfThings(String listType, ListOfThings playerInventory) {

        if (showDebug) {
            System.out.println("------------------------------------------");
            System.out.println("Starting ListOfThings printListOfThings...");
        }

        // This will print the list, skipping any blank entries
        // Actions will only print if they exist in the room and the playerInventory

        String displayList = "";
        String displayItem = "";
        int itemCount = itemsList.length;

        if (showDebug) {
            System.out.println("Expecting " + maxItems + " maxItems in " + listName + " of type " + listType);
            System.out.println("Found " + itemsList.length + " items.");
        }

        if (itemCount <= 0) {
            displayList = "None";
        } else {

            for (int itemCounter = 0; itemCounter < itemCount; itemCounter++) {

                if (showDebug) {
                    System.out.println("Checking [" + itemCounter + "]");
                }

                if (itemsList[itemCounter] != null && !itemsList[itemCounter].id.isEmpty()) {

                    if (showDebug) {
                        System.out.println("[" + itemCounter + "] " + itemsList[itemCounter].id + "  " +
                                itemsList[itemCounter].name);
                    }

                    // Check Action against player's inventory.

                    if (listType.equalsIgnoreCase("Do Action")) {

                        if (playerInventory.actionInList(itemsList[itemCounter].id)) {

                            if (showDebug) {
                                System.out.println("Action is in list.");
                            }

                            displayItem = itemsList[itemCounter].name;
                        } else {
                            if (showDebug) {
                                System.out.println("Action is not in list.");
                            }
                        }
                    } else {
                        displayItem = itemsList[itemCounter].name;
                    }

                    if (!displayList.isEmpty()) {
                        displayList += ", ";
                    }
                    displayList += displayItem;
                } else {
                    if (showDebug) {
                        System.out.println("[" + itemCounter + "] is empty.");
                    }
                }

            }
            if (displayList.isEmpty()) {
                displayList = "None";
            }
        }
        System.out.println(listName + ": " + displayList);

        if (showDebug) {
            System.out.println("------------------------------------------");
        }
    }

    public void printListDescriptions() {

        // This will print out the descriptions of all the items in the list

        if (showDebug) {
            System.out.println("----------------------------------------------");
            System.out.println("Starting ListOfThings printListDescriptions...");
        }

        String displayList = "";
        String displayItem = "";
        int itemCount = itemsList.length;

        if (showDebug) {
            System.out.println("Expecting " + maxItems + " maxItems in " + listName + " of type " + listType);
            System.out.println("Found " + itemsList.length + " items.");
        }

        if (itemCount <= 0) {
            displayList = "";
        } else {

            for (int itemCounter = 0; itemCounter < itemCount; itemCounter++) {

                if (showDebug) {
                    System.out.println("Checking [" + itemCounter + "]");
                }

                if (itemsList[itemCounter] != null && !itemsList[itemCounter].id.isEmpty()) {

                    if (showDebug) {
                        System.out.println("[" + itemCounter + "] " + itemsList[itemCounter].id + "  " +
                                itemsList[itemCounter].name);
                    }

                    displayItem = itemsList[itemCounter].description;

                    if (!displayList.isEmpty()) {
                        displayList += " ";
                    }
                    displayList += displayItem;
                } else {
                    if (showDebug) {
                        System.out.println("[" + itemCounter + "] is empty.");
                    }
                }

            }
            if (displayList.isEmpty()) {
                displayList = "";
            }
        }
        System.out.println(displayList);

        if (showDebug) {
            System.out.println("------------------------------------------");
        }
    }
}