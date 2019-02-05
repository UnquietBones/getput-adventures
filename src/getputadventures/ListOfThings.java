package getputadventures;

// Changes made
//	Created stringOfActions
//  Updated more println to printfs


public class ListOfThings {

    // This can be a list of Items (Room Inventory or Player Inventory) or Actions.

    private String listType;          // RoomInv, PlayerInv, Actions
    private String listName;
    private ListThing[] itemsList;
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

    // List of methods so I can keep myself straight as I build this thing

    // void clearList()
    // boolean addItem(String newItem, boolean showMsg)
    // void removeThing(String thisThing, boolean showMsg)
    // int freeSpot()
    // ListThing transferThing(String thisThing)
    // int posInList(String findID)
    // int actionInList(String findAction)
    // void removeItemAction(String itemID)
    // void addItemAction(String updateID, String actionID)
    // void printListOfThings(String listType, ListOfThings playerInventory)
    // void printListDescriptions()
    // String stringOfActions()


    public void clearList() {

        // Clear out the list, so we avoid nulls later

        if (showDebug) {
            debugLong();
            System.out.println("ListOfThings clearList");
        }

        if (showDebug) {
            System.out.printf("  Expecting %d maxItems in %s", maxItems, listName);
            System.out.printf("  Found %d items.", itemsList.length);
        }

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {

            if (showDebug) {
                System.out.printf("  Set item [%d] to empty object.", itemCounter);
            }

            if (itemsList[itemCounter] == null) {

                itemsList[itemCounter] = new ListThing("", "", "", "",
                        "", "", "", "", showDebug);
            } else {
                itemsList[itemCounter].clearThing();
            }
        }
        if (showDebug) {
            debugLong();
        }
    }

    public boolean addItem(String newItem, boolean showMsg) {

        // This will find the first empty slot in the list, add the item, and
        // return true. If there are no empty slots it will return false.

        if (showDebug) {
            debugLong();
            System.out.println("ListOfThings addItem");
            System.out.println("Searching for item " + newItem + " in " + listName + " " + listType);
        }

        for (int itemCounter = 0; itemCounter <= maxItems; itemCounter++) {
            if (showDebug) {
                System.out.println("Checking for empty at [" + itemCounter + "] " + itemsList[itemCounter].getName());
            }
            if (itemsList[itemCounter].getName().isEmpty()) {

                switch (listType) {
                    case "RoomInv":
                        itemsList[itemCounter] = gameItems.readThing(newItem);
                        if (showMsg) {
                            System.out.printf("A %s appears in the room. %n", itemsList[itemCounter].getName());
                        }
                        break;
                    case "PlayerInv":
                        itemsList[itemCounter] = gameItems.readThing(newItem);
                        if (showMsg) {
                            System.out.printf("%s has been added to your inventory. %n", itemsList[itemCounter].getName());
                        }
                        break;
                    case "Actions":
                        itemsList[itemCounter] = gameActions.readThing(newItem);
                        if (showMsg) {
                            System.out.printf("You can now %s. %n", itemsList[itemCounter].getName());
                        }
                        break;
                }
                if (showDebug) {
                    debugLong();
                }
                return true;
            }
        }
        if (showMsg) {
            System.out.println(listName + " is full!");
        }
        if (showDebug) {
            debugLong();
        }
        return false;
    }

    public void removeThing(String thisThing, boolean showMsg) {

        // This will remove the Thing from the List, if found.

        if (showDebug) {
            debugLong();
            System.out.println("ListOfThings removeThing");
            System.out.printf("  Trying to remove %s from %s %s %n", thisThing, listType, listType);
        }

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {

            if (showDebug) {
                System.out.printf("    Checking [%d] %s %s %n", itemCounter, itemsList[itemCounter].getId(), itemsList[itemCounter].getName());
            }
            if (itemsList[itemCounter].getId().equalsIgnoreCase(thisThing) || itemsList[itemCounter].getName().equalsIgnoreCase(thisThing)) {
                if (showMsg) {
                    System.out.printf("%s has been removed from %s. %n", itemsList[itemCounter].getName(), listName);
                }
                itemsList[itemCounter].clearThing();
                if (showDebug) {
                    debugLong();
                }
                return;
            }
        }

        if (showMsg) {
            System.out.printf("%s was not in %s. %n", thisThing, listName);
        }
        if (showDebug) {
            debugLong();
        }
    }

    public int freeSpot() {

        // This will return the position of the first empty slot in the list or 999

        if (showDebug) {
            debugLong();
            System.out.println("ListOfThings freeSpot");
            System.out.println("  Looking for an empty slot in " + listType + " " + listName);
        }

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {
            if (itemsList[itemCounter] == null || itemsList[itemCounter].getName().isEmpty()) {

                if (showDebug) {
                    System.out.printf("  Found empty spot at [%d]. %n", itemCounter);
                    debugLong();
                }
                return itemCounter;
            } else {

                if (showDebug) {
                    System.out.printf("  Checking [%d] %s %s %n", itemCounter, itemsList[itemCounter].getId(), itemsList[itemCounter].getName());
                }
            }
        }

        if (showDebug) {
            System.out.println("  There are no empty spots in the list.");
            debugLong();
        }
        return 999;
    }

    public ListThing transferThing(String thisThing) {

        // This will remove the Thing from the list, to pass to a new list.

        ListThing tempThing;
        tempThing = null;

        if (showDebug) {
            debugLong();
            System.out.println("Starting ListOfThings transferThing...");
            System.out.println("Trying to remove item " + thisThing);
        }

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {

            if (itemsList[itemCounter] != null) {

                if (showDebug) {
                    System.out.println("Checking [" + itemCounter + "] " + itemsList[itemCounter].getId() +
                            itemsList[itemCounter].getName());
                }
                if (itemsList[itemCounter].getId().equalsIgnoreCase(thisThing) || itemsList[itemCounter].getName().equalsIgnoreCase(thisThing)) {

                    // Move pointer into a temp variable to pass it out and clear it from the list
                    tempThing = itemsList[itemCounter];
                    itemsList[itemCounter] = null;

                    if (showDebug) {
                        System.out.println("Passing over " + thisThing + " from " + listName);
                        debugLong();
                    }
                    return tempThing;
                }
            }
        }

        if (showDebug) {
            System.out.println("Item " + thisThing + " was not in " + listName);
            debugLong();
        }
        return tempThing;
    }

    public int posInList(String findID) {

        // Find the position of the Thing in the List

        if (showDebug) {
            debugLong();
            System.out.println("ListOfThings posInList");
            System.out.printf("  Looking for %s in %s %s %n", findID, listType, listName);
        }

        // Returns the position of the item in the list or 999

        int itemCount = itemsList.length - 1;

        for (int itemCounter = 0; itemCounter <= (itemCount); itemCounter++) {

            if (itemsList[itemCounter] != null) {

                if (showDebug) {
                    System.out.printf("  [%d]  %s %s %n", itemCounter, itemsList[itemCounter].getId(), itemsList[itemCounter].getName());
                }

                if (itemsList[itemCounter].getId().equalsIgnoreCase(findID) || itemsList[itemCounter].getName().equalsIgnoreCase(findID)) {
                    if (showDebug) {
                        System.out.printf("  Found %s at %d %n", findID, itemCounter);
                        debugLong();
                    }
                    return itemCounter;
                }
            }
        }

        // Couldn't find it, so return default
        if (showDebug) {
            System.out.printf("Didn't find %s %n", findID);
            debugLong();
        }
        return 999;
    }

    public int actionInList(String findAction) {

        // Check the List to see if any of the Items have the given action

        if (showDebug) {
            debugLong();
            System.out.println("ListOfThings actionInList");
            System.out.printf("  Looking for %s in %s %s %n", findAction, listType, listName);
        }

        findAction = gameActions.findID(findAction);

        int itemCount = itemsList.length - 1;

        for (int itemCounter = 0; itemCounter <= (itemCount); itemCounter++) {

            if (showDebug) {
                System.out.printf("    Checking item %d %n", itemCounter);
            }


            if (itemsList[itemCounter] == null || itemsList[itemCounter].getName().isEmpty()) {
                if (showDebug) {
                    System.out.printf("    [%d] is null", itemCounter);
                }
            } else {

                if (showDebug) {
                    System.out.printf("    [%d] %s %s %n", itemCounter, itemsList[itemCounter].getName(), itemsList[itemCounter].getActionID());
                    debugLong();
                }

                if (itemsList[itemCounter].getActionID().equalsIgnoreCase(findAction)) {
                    return itemCounter;
                }
            }
        }

        if (showDebug) {
            System.out.println("No matching Action found.");
            debugLong();
        }
        return 999;
    }

    public void removeItemAction(String itemID) {

        // Items can only have one action at a time, so removing the action will just
        // clear the value.

        if (showDebug) {
            debugLong();
            System.out.println("ItemLibrary removeItemAction");
            System.out.printf("  Trying to update item %s %n", itemID);
        }

        int foundPos = this.posInList(itemID);

        if (foundPos < 999) {

            this.itemsList[foundPos].setActionID("");

            if (showDebug) {
                System.out.println("  Removed Action from Item");
                debugLong();
            }
        } else {
            if (showDebug) {
                System.out.println("  Unable to find Item in Library.");
                debugLong();
            }
        }
    }

    public void addItemAction(String updateID, String actionID) {

        // Items can only have one action at a time, so adding the action will just
        // replace whatever value is there.

        if (showDebug) {
            debugLong();
            System.out.println("ItemLibrary removeItemAction");
            System.out.printf("  Trying to update item %s with %s %n", updateID, actionID);
        }

        int foundPos = this.posInList(updateID);

        if (foundPos < 999) {

            this.itemsList[foundPos].setActionID(actionID);

            if (showDebug) {
                System.out.printf("  Action %s was added to the Item %n", actionID);
                debugLong();
            }
        }
    }

    public void printListOfThings(String listType, ListOfThings playerInventory) {

        if (showDebug) {
            debugLong();
            System.out.println("ListOfThings printListOfThings");
        }

        // This will print the list, skipping any blank entries

        String displayList = "";
        String displayItem;
        int itemCount = itemsList.length;

        if (showDebug) {
            System.out.printf("  Expecting %d maxItems in %s of type %s %n" + listType, maxItems, listName, listType);
            System.out.printf("  Found %d items. %n", itemsList.length);
        }

        if (itemCount <= 0) {
            displayList = "None";
        } else {

            for (int itemCounter = 0; itemCounter < itemCount; itemCounter++) {

                displayItem = "";

                if (showDebug) {
                    System.out.printf("  Checking [%d] %n", itemCounter);
                }

                if (itemsList[itemCounter] != null && !itemsList[itemCounter].getId().isEmpty()) {

                    if (showDebug) {
                        System.out.printf("  [%d] %s %s %n", itemCounter, itemsList[itemCounter].getId(), itemsList[itemCounter].getName());
                    }

                    // Check to see if this is an Action

                    if (listType.equalsIgnoreCase("Do Action")) {

                        if (showDebug) {
                            System.out.println("    This is an Action not an Item.");
                        }

                        // Check Action against player's inventory, if needed

                        if (itemsList[itemCounter].getActionType().equalsIgnoreCase("RO")) {

                            if (showDebug) {
                                System.out.println("  This is an RO item, checking Player Inventory");
                            }

                            if (playerInventory.actionInList(itemsList[itemCounter].getId()) < 999) {

                                if (showDebug) {
                                    System.out.println("    Action is in Player Inventory.");
                                }

                                displayItem = itemsList[itemCounter].getName();
                            } else {
                                if (showDebug) {
                                    System.out.println("    Action is not in Player Inventory.");
                                }
                            }
                        } else {
                            if (showDebug) {
                                System.out.println("    Action is not an RO Item.");
                            }
                            displayItem = itemsList[itemCounter].getName();
                        }
                    } else {
                        if (showDebug) {
                            System.out.println("    This is an Item not an Action.");
                        }

                        displayItem = itemsList[itemCounter].getName();
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
            debugLong();
        }
    }

    public void printListDescriptions() {

        // This will print out the combined descriptions of all the items in the list

        if (showDebug) {
            debugLong();
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

                if (itemsList[itemCounter] != null && !itemsList[itemCounter].getId().isEmpty()) {

                    if (showDebug) {
                        System.out.printf("  [%d] %s %s %n", itemCounter, itemsList[itemCounter].getId(), itemsList[itemCounter].getName());
                    }

                    displayItem = itemsList[itemCounter].getDescription();

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
            debugLong();
        }
    }


    public String stringOfActions(ListOfThings playerInventory, Room currentRoom) {

        // This will return all actions associated with the List, if they are valid

        if (showDebug) {
            debugLong();
            System.out.println("ListOfThings stringOfActions");
            System.out.printf("  Returning action list for %s %s %n", listType, listName);
        }

        String actionList = "";

        int listLength = itemsList.length - 1;

        for (int listPos = 0; listPos <= (listLength); listPos++) {

            String thisActionName;
            String thisActionID;
            String thisActionType;

            if (itemsList[listPos] == null || itemsList[listPos].getName().isEmpty()) {
                if (showDebug) {
                    System.out.printf("    [%d] is null, so no action to add. %n", listPos);
                }
            } else {

                // If this is a list of Actions, get the name

                if (listType.equalsIgnoreCase("Actions")) {

                    thisActionID = itemsList[listPos].getId();
                    thisActionName = itemsList[listPos].getName();
                    thisActionType = itemsList[listPos].getActionType();

                    if (showDebug) {
                        System.out.printf("    [%d] is action %s %s %n", listPos, thisActionID, thisActionName);
                    }
                } else {

                    // If this is a list of Items (RoomInv, PlayerInv), return the action name

                    thisActionID = itemsList[listPos].getActionID();
                    thisActionName = gameActions.findName(thisActionID);
                    thisActionType = gameActions.findType(thisActionID);

                    if (showDebug) {
                        System.out.printf("  [%d] %s has action %s %s %n", listPos, itemsList[listPos].getName(), thisActionID, thisActionName);
                    }
                }

                // If this is from an Action list and an RO item, we don't need to return it since it will
                // have been returned in the with with PlayerInventory

                if (thisActionType.equalsIgnoreCase("RO") && listType.equalsIgnoreCase("Actions")) {
                    continue;
                }

                // If the Type contains "R" make sure were in the right Room

                if (thisActionType.contains("R")) {
                    if (currentRoom.getActions().posInList(thisActionID) == 999) {
                        if (showDebug) {
                            System.out.printf("  Action %s %s is missing the required Room. %n", thisActionID, thisActionName);
                        }
                        continue;
                    }
                }

                // If the Type contains "O" make sure the Item is in the Player Inventory

                if (thisActionType.contains("O")) {
                    if (playerInventory.actionInList(thisActionID) == 999) {
                        if (showDebug) {
                            System.out.printf("  Action %s %s is missing the required Item in Player Inventory. %n", thisActionID, thisActionName);
                        }
                        continue;
                    }
                }

                if (actionList.isEmpty()) {
                    actionList = thisActionName;
                } else {
                    actionList += ", " + thisActionName;
                }
            }
        }

        if (actionList.isEmpty()) {
            if (showDebug) {
                System.out.println("No actions were found.");
                debugLong();
            }
        } else {
            if (showDebug) {
                System.out.printf("Returning action list: %s %n", actionList);
                debugLong();
            }
        }
        return actionList;
    }

    public int getListLen() {
        return itemsList.length;
    }

    public ListThing getListThing(int thisPos) {
        return this.itemsList[thisPos];
    }

    public void setListThing(int thisPos, ListThing thisThing) {
        this.itemsList[thisPos] = thisThing;
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