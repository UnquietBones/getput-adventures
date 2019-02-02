package getputadventures;

public class ListOfThings {

    // This can be a list of Items or Actions

    // Changes Made
    //    Tried to remove as many global variables as possible
    //    Turned the debug dash-breaks into statics so they are consistent
    //    Changed all println with variables to printf's.

    private static String LONGDASH = "--------------------------------";
    private static String SHORTDASH = "----------------";
    public String listType;          // RoomInv, PlayerInv, Actions
    public String listName;
    public ListThing[] itemsList;
    private boolean showDebug;
    private ItemLibrary gameItems;
    private ActionLibrary gameActions;
    private int maxItems;

    public ListOfThings(String newListName, String newListType, int newMaxItems, ItemLibrary newgameItems, ActionLibrary newgameActions, boolean mainDebug) {

        showDebug = mainDebug;
        listType = newListType;
        listName = newListName;
        maxItems = newMaxItems;
        itemsList = new ListThing[maxItems];
        gameItems = newgameItems;
        gameActions = newgameActions;

        clearList();
    }

    public void clearList() {

        // Clear out the list, so we avoid nulls later

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ListOfThings clearList");
        }

        if (showDebug) {
            System.out.printf("  Expecting %d maxItems in %s", maxItems, listName);
            System.out.printf("  Found %d items.", itemsList.length);
        }

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {

            if (showDebug) {
                System.out.println("  Set item [" + itemCounter + "] to empty string.");
            }

            if (itemsList[itemCounter] == null) {

                itemsList[itemCounter] = new ListThing("", "", "", "",
                        "", "", "", "", showDebug);
            } else {
                itemsList[itemCounter].clearThing();
            }
        }
        if (showDebug) {
            System.out.println(LONGDASH);
        }
    }

    public boolean addItem(String newItem, boolean showMsg) {

        // This will find the first empty slot in the list, add the item, and
        // return true. If there are no empty slots it will return false.

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ListOfThings addItem");
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
                    case "Actions":
                        itemsList[itemCounter] = gameActions.readThing(newItem);
                        if (showMsg) {
                            System.out.println("You can now " + itemsList[itemCounter].name + ".");
                        }
                        break;
                }
                if (showDebug) {
                    System.out.println(LONGDASH);
                }
                return true;
            }
        }
        if (showMsg) {
            System.out.println(listName + " is full!");
        }
        if (showDebug) {
            System.out.println(LONGDASH);
        }
        return false;
    }


    public void removeThing(String thisThing, boolean showMsg) {

        // This will remove the Thing from the list, if found.

        if (showDebug) {
            System.out.println(LONGDASH);
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
                    System.out.println(LONGDASH);
                }
                return;
            }
        }

        if (showMsg) {
            System.out.println("Item " + thisThing + " was not in " + listName);
        }
        if (showDebug) {
            System.out.println(LONGDASH);
        }
    }

    public int freeSpot() {

        // This will return the position of the first empty slot in the list or 999

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ListOfThings freeSpot");
            System.out.println("  Looking for an empty slot in " + listType + " " + listName);
        }

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {
            if (itemsList[itemCounter] == null || itemsList[itemCounter].name.isEmpty()) {

                if (showDebug) {
                    System.out.printf("  Found empty spot at [%d]. %n", itemCounter);
                    System.out.println(LONGDASH);
                }
                return itemCounter;
            } else {

                if (showDebug) {
                    System.out.printf("  Checking [%d] %s %s %n", itemCounter, itemsList[itemCounter].id, itemsList[itemCounter].name);
                }
            }
        }

        if (showDebug) {
            System.out.println("  There are no empty spots in the list.");
            System.out.println(LONGDASH);
        }
        return 999;
    }

    public ListThing transferThing(String thisThing) {

        // This will remove the Thing from the list, to pass to a new list.

        ListThing tempThing;
        tempThing = null;

        if (showDebug) {
            System.out.println(LONGDASH);
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
                        System.out.println(LONGDASH);
                    }
                    return tempThing;
                }
            }
        }

        if (showDebug) {
            System.out.println("Item " + thisThing + " was not in " + listName);
            System.out.println(LONGDASH);
        }
        return tempThing;
    }

    public int posInList(String findID) {

        // Find the position of the Thing in the List

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ListOfThings posInList");
            System.out.printf("  Looking for %s in %s %s %n", findID, listType, listName);
        }

        // Returns the position of the item in the list or 999

        int itemCount = itemsList.length - 1;

        for (int itemCounter = 0; itemCounter <= (itemCount); itemCounter++) {

            if (itemsList[itemCounter] != null) {

                if (showDebug) {
                    System.out.printf("  [%d]  %s %s %n", itemCounter, itemsList[itemCounter].id, itemsList[itemCounter].name);
                }

                if (itemsList[itemCounter].id.equalsIgnoreCase(findID) || itemsList[itemCounter].name.equalsIgnoreCase(findID)) {
                    if (showDebug) {
                        System.out.printf("  Found %s at %d %n", findID, itemCounter);
                        System.out.println(LONGDASH);
                    }
                    return itemCounter;
                }
            }
        }

        // Couldn't find it, so return default
        if (showDebug) {
            System.out.printf("Didn't find %s %n", findID);
            System.out.println(LONGDASH);
        }
        return 999;
    }

    public int actionInList(String findAction) {

        // Here we are checking a list of Items to see if any of them has the given Action

        if (showDebug) {
            System.out.println(LONGDASH);
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
                    System.out.println(LONGDASH);
                }

                if (itemsList[itemCounter].actionID.equalsIgnoreCase(findAction)) {
                    return itemCounter;
                }
            }
        }

        if (showDebug) {
            System.out.println("No matching Action found.");
            System.out.println(LONGDASH);
        }
        return 999;
    }

    public void removeItemAction(String itemID) {

        // Items can only have one action at a time, so removing the action will just
        // clear the value.

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ItemLibrary removeItemAction");
            System.out.printf("  Trying to update item %s %n", itemID);
        }

        int foundPos = this.posInList(itemID);

        if (foundPos < 999) {

            this.itemsList[foundPos].actionID = "";

            if (showDebug) {
                System.out.println("  Removed Action from Item");
                System.out.println(LONGDASH);
            }
        } else {
            if (showDebug) {
                System.out.println("  Unable to find Item in Library.");
                System.out.println(LONGDASH);
            }
        }
    }

    public void addItemAction(String updateID, String actionID) {

        // Items can only have one action at a time, so adding the action will just
        // replace whatever value is there.

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ItemLibrary removeItemAction");
            System.out.printf("  Trying to update item %s with %s %n", updateID, actionID);
        }

        int foundPos = this.posInList(updateID);

        if (foundPos < 999) {

            this.itemsList[foundPos].actionID = actionID;

            if (showDebug) {
                System.out.printf("  Action %s was added to the Item %n", actionID);
                System.out.println(LONGDASH);
            }
        }
    }


    public void printListOfThings(String listType, ListOfThings playerInventory) {

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ListOfThings printListOfThings");
        }

        // This will print the list, skipping any blank entries
        // If an Action is an RO type, it will only print if the Room and Item are both there

        String displayList = "";
        String displayItem;
        int itemCount = itemsList.length;

        if (showDebug) {
            System.out.printf("  Expecting %d maxItems in %s of type %s %n" + listType, maxItems, listName, listType);
            System.out.printf("  Found %d items.", itemsList.length);
        }

        if (itemCount <= 0) {
            displayList = "None";
        } else {

            for (int itemCounter = 0; itemCounter < itemCount; itemCounter++) {

                displayItem = "";

                if (showDebug) {
                    System.out.printf("  Checking [%d]", itemCounter);
                }

                if (itemsList[itemCounter] != null && !itemsList[itemCounter].id.isEmpty()) {

                    if (showDebug) {
                        System.out.printf("  [%d] %s %s %n", itemCounter, itemsList[itemCounter].id, itemsList[itemCounter].name);
                    }

                    // Check to see if this is an Action

                    if (listType.equalsIgnoreCase("Do Action")) {

                        if (showDebug) {
                            System.out.println("    This is an Action not an Item.");
                        }

                        // Check Action against player's inventory, if needed

                        if (itemsList[itemCounter].actionType.equalsIgnoreCase("RO")) {

                            if (showDebug) {
                                System.out.println("  This is an RO item, checking Player Inventory");
                            }

                            if (playerInventory.actionInList(itemsList[itemCounter].id) < 999) {

                                if (showDebug) {
                                    System.out.println("    Action is in Player Inventory.");
                                }

                                displayItem = itemsList[itemCounter].name;
                            } else {
                                if (showDebug) {
                                    System.out.println("    Action is not in Player Inventory.");
                                }
                            }
                        } else {
                            if (showDebug) {
                                System.out.println("    Action is not an RO Item.");
                            }
                            displayItem = itemsList[itemCounter].name;
                        }
                    } else {
                        if (showDebug) {
                            System.out.println("    This is an Item not an Action.");
                        }

                        displayItem = itemsList[itemCounter].name;
                    }

                    if (!displayItem.isEmpty()) {

                        if (!displayList.isEmpty()) {
                            displayList += ", ";
                        }
                        displayList += displayItem;
                    }
                } else {
                    if (showDebug) {
                        System.out.printf("  [%d] is empty. %n", itemCounter);
                    }
                }

            }
            if (displayList.isEmpty()) {
                displayList = "None";
            }
        }
        System.out.printf("  %s : %s %n", listType, displayList);

        if (showDebug) {
            System.out.println(LONGDASH);
        }
    }

    public void printListDescriptions() {

        // This will print out the combined descriptions of all the items in the list

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ListOfThings printListDescriptions");
        }

        String displayList = "";
        String displayItem = "";
        int itemCount = itemsList.length;

        if (showDebug) {
            System.out.printf("  Expecting %d maxItems in %s of typ %s %n", maxItems, listName, listType);
            System.out.printf("  Found %d items %n", itemsList.length);
        }

        if (itemCount <= 0) {
            displayList = "";
        } else {

            for (int itemCounter = 0; itemCounter < itemCount; itemCounter++) {

                if (showDebug) {
                    System.out.printf("  Checking [%d] %n", itemCounter);
                }

                if (itemsList[itemCounter] != null && !itemsList[itemCounter].id.isEmpty()) {

                    if (showDebug) {
                        System.out.printf("  [%d] %s %s %n", itemCounter, itemsList[itemCounter].id, itemsList[itemCounter].name);
                    }

                    displayItem = itemsList[itemCounter].description;

                    if (!displayList.isEmpty()) {
                        displayList += " ";
                    }
                    displayList += displayItem;
                } else {
                    if (showDebug) {
                        System.out.printf("  [%d] is empty. %n", itemCounter);
                    }
                }

            }
            if (displayList.isEmpty()) {
                displayList = "";
            }
        }
        System.out.println(" " + displayList);

        if (showDebug) {
            System.out.println(LONGDASH);
        }
    }
}