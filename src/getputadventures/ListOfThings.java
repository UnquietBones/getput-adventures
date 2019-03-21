package getputadventures;

public class ListOfThings {

    // This can be a list of Items (Room Inventory or Player Inventory) or Actions.

    private String listType;          // RoomInv, PlayerInv, Actions
    private String listName;
    private ListThing[] itemsList;
    private ItemLibrary gameItems;
    private ActionLibrary gameActions;
    private int maxItems;
    private boolean showDebug;
    private DisplayMsgs displayMsgs;

    public ListOfThings(String newListName, String newListType, int newMaxItems, ItemLibrary newgameItems, ActionLibrary newgameActions, boolean mainDebug) {

        listType = newListType;
        listName = newListName;
        maxItems = newMaxItems;
        itemsList = new ListThing[maxItems];
        gameItems = newgameItems;
        gameActions = newgameActions;
        showDebug = mainDebug;

        displayMsgs = new DisplayMsgs(showDebug);
    }

    public boolean addItem(String newItem, boolean showMsg) {

        displayMsgs.debugHeader("ListOfThings addItem");
        displayMsgs.debugOutput("Attempting to add " + newItem + " to " + listName + " " + listType);

        int checkDuplicate = posInList(newItem);
        int emptyPos = freeSpot();
        String itemName = gameItems.findName(newItem);

        if (checkDuplicate < 999) {
            displayMsgs.displayMessage("DuplicateItem", itemName, showMsg);
            return false;
        } else {
            if (emptyPos < 999) {
                switch (listType) {
                    case "RoomInv":
                        itemsList[emptyPos] = gameItems.readItem(newItem);
                        displayMsgs.displayMessage("RoomItemAdded", itemName, showMsg);
                        break;
                    case "PlayerInv":
                        itemsList[emptyPos] = gameItems.readItem(newItem);
                        break;
                    case "Actions":
                        itemsList[emptyPos] = gameActions.readAction(newItem);
                        displayMsgs.displayMessage("RoomItemAdded", itemsList[emptyPos].getName(), showMsg);
                        break;
                }
                displayMsgs.debugLong();
                return true;
            } else {
                switch (listType) {
                    case "RoomInv":
                        displayMsgs.displayMessage("RoomInventoryFull", itemName, showMsg);
                        break;
                    case "PlayerInv":
                        displayMsgs.displayMessage("PlayerInventoryFull", showMsg);
                        break;
                    case "Actions":
                        displayMsgs.displayMessage("ActionListFull",  showMsg);
                        break;
                }
            }
        }
        displayMsgs.debugLong();
        return false;
    }

    public boolean removeThing(String thisThing, boolean showMsg) {

        // This will remove the Thing from the List, if found.

        displayMsgs.debugHeader("ListOfThings removeThing");
        displayMsgs.debugOutput("  Trying to remove " + thisThing + " from " + listType + ", " + listType);

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {
            //displayMsgs.debugOutput("    Checking [" + itemCounter + "] " + itemsList[itemCounter].getId() + " " + itemsList[itemCounter].getName());

            if (itemsList[itemCounter].getId().equalsIgnoreCase(thisThing) || itemsList[itemCounter].getName().equalsIgnoreCase(thisThing)) {
                displayMsgs.displayMessage("RemovedFromList", itemsList[itemCounter].getName(), listName, showMsg);
                itemsList[itemCounter].clearThing();
                displayMsgs.debugLong();
                return true;
            }
        }

        displayMsgs.displayMessage("WasNotInList", thisThing, listName, showMsg);
        displayMsgs.debugLong();
        return false;
    }

    public int freeSpot() {

        // This will return the position of the first empty slot in the list or 999

        displayMsgs.debugHeader("ListOfThings freeSpot");
        displayMsgs.debugOutput("  Looking for an empty slot in " + listType + " " + listName);

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {
            if (itemsList[itemCounter] == null || itemsList[itemCounter].getName().isEmpty()) {
                displayMsgs.debugOutput("  Found empty spot at [" + itemCounter + "].");
                displayMsgs.debugLong();
                return itemCounter;
            } else {
                displayMsgs.debugOutput("  Checking [" + itemCounter + "] " + itemsList[itemCounter].getId() + " " + itemsList[itemCounter].getName());
            }
        }
        displayMsgs.debugOutput("  There are no empty spots in the list.");
        displayMsgs.debugLong();
        return 999;
    }

    public ListThing transferThing(String thisThing) {

        ListThing tempThing = null;
        int itemPos = posInList(thisThing);

        displayMsgs.debugHeader("ListOfThings transferThing");
        displayMsgs.debugOutput("Trying to get reference for item " + thisThing + " and remove it from the list.");

        if (itemPos < 999) {
            tempThing = itemsList[itemPos];
            itemsList[itemPos] = null;

            displayMsgs.debugOutput("Passing over " + thisThing + " from " + listName);
            displayMsgs.debugLong();
            return tempThing;
        }

        displayMsgs.debugOutput("Item " + thisThing + " was not in " + listName);
        displayMsgs.debugLong();
        return tempThing;
    }

    public int posInList(String findID) {

        displayMsgs.debugHeader("ListOfThings posInList");
        displayMsgs.debugOutput("  Looking for " + findID + " in " + listType + " " + listName);

        for (int itemCounter = 0; itemCounter < itemsList.length; itemCounter++) {
            if (itemsList[itemCounter] != null) {
                if (itemsList[itemCounter].getId().equalsIgnoreCase(findID) || itemsList[itemCounter].getName().equalsIgnoreCase(findID)) {
                    displayMsgs.debugOutput("  Found " + findID + " at " + itemCounter);
                    displayMsgs.debugLong();
                    return itemCounter;
                }
            }
        }
        displayMsgs.debugOutput("Didn't find " + findID);
        displayMsgs.debugLong();
        return 999;
    }

    public int actionInList(String findAction) {

        displayMsgs.debugHeader("ListOfThings actionInList");
        displayMsgs.debugOutput("  Looking for " + findAction + " in " + listType + " " + listName);

        findAction = gameActions.findID(findAction);

        int itemCount = itemsList.length - 1;

        for (int itemCounter = 0; itemCounter <= (itemCount); itemCounter++) {
            //displayMsgs.debugOutput("    Checking item " + itemCounter);

            if (itemsList[itemCounter] == null || itemsList[itemCounter].getName().isEmpty()) {
                //displayMsgs.debugOutput("    [" + itemCounter + "] is null");
            } else {
                //displayMsgs.debugOutput("    [" + itemCounter + "] " + itemsList[itemCounter].getName() + " " + itemsList[itemCounter].getActionID());
                //displayMsgs.debugLong();

                if (itemsList[itemCounter].getActionID().equalsIgnoreCase(findAction)) {
                    displayMsgs.debugOutput("  Found action " + findAction + " on item " + itemsList[itemCounter].getName());
                    displayMsgs.debugLong();
                    return itemCounter;
                }
            }
        }

        displayMsgs.debugOutput("No matching Action found.");
        displayMsgs.debugLong();
        return 999;
    }

    public boolean removeItemAction(String itemID) {

        // Items can only have one action at a time, so removing the action will just
        // clear the value.

        displayMsgs.debugHeader("ItemLibrary removeItemsAction");
        displayMsgs.debugOutput("  Trying to update item " + itemID);

        int foundPos = this.posInList(itemID);

        if (foundPos < 999) {
            this.itemsList[foundPos].setActionID("");
            displayMsgs.debugOutput("  Removed Action from Item");
            displayMsgs.debugLong();
            return true;
        } else {
            displayMsgs.debugOutput("  Unable to find Item in Library.");
            displayMsgs.debugLong();
            return false;
        }
    }

    public boolean addItemAction(String updateID, String actionID) {

        // Items can only have one action at a time, so adding the action will just
        // replace whatever value is there.

        displayMsgs.debugHeader("ItemLibrary addItemsAction");
        displayMsgs.debugOutput("  Trying to update item " + updateID + " with " + actionID);

        int foundPos = this.posInList(updateID);

        if (foundPos < 999) {
            this.itemsList[foundPos].setActionID(actionID);
            displayMsgs.debugOutput("  Action " + actionID + " was added to Item " + updateID);
            displayMsgs.debugLong();
            return true;
        }

        return false;
    }

    public void printListOfThings(String listType, ListOfThings playerInventory) {

        // This will print the list, skipping any blank entries

        String displayList = "";
        String displayItem;
        int itemCount = itemsList.length;

        displayMsgs.debugHeader("ListOfThings printListOfThings");
        displayMsgs.debugOutput("  Expecting " + maxItems + " maxItems in " + listName + " of type " + listType);
        displayMsgs.debugOutput("  Found " + itemsList.length + " items.");

        if (itemCount <= 0) {
            displayList = "None";
        } else {
            for (int itemCounter = 0; itemCounter < itemCount; itemCounter++) {
                displayItem = "";

                if (itemsList[itemCounter] != null && !itemsList[itemCounter].getId().isEmpty()) {
                    displayMsgs.debugOutput("  [" + itemCounter + "] " + itemsList[itemCounter].getId() + " " + itemsList[itemCounter].getName());

                    // Check to see if this is an Action

                    if (listType.equalsIgnoreCase("Do Action")) {
                        displayMsgs.debugOutput("    This is an Action not an Item.");

                        // Check Action against player's inventory, if needed

                        if (itemsList[itemCounter].getActionType().equalsIgnoreCase("RO")) {
                            displayMsgs.debugOutput("  This is an RO item, checking Player Inventory");

                            if (playerInventory.actionInList(itemsList[itemCounter].getId()) < 999) {
                                displayMsgs.debugOutput("    Action is in Player Inventory.");
                                displayItem = itemsList[itemCounter].getName();
                            } else {
                                displayMsgs.debugOutput("    Action is not in Player Inventory.");
                            }
                        } else {
                            displayMsgs.debugOutput("    Action is not an RO Item.");
                            displayItem = itemsList[itemCounter].getName();
                        }
                    } else {
                        displayMsgs.debugOutput("    This is an Item not an Action.");
                        displayItem = itemsList[itemCounter].getName();
                    }

                    if (!displayItem.isEmpty()) {

                        if (!displayList.isEmpty()) {
                            displayList += ", ";
                        }
                        displayList += displayItem;
                    }
                } else {
                    displayMsgs.debugOutput("  [" + itemCounter + "] is empty");
                }
            }

            if (displayList.isEmpty()) {
                displayList = "None";
            }
        }
        displayMsgs.displayOutput("[" + listType + "] : " + displayList);
        displayMsgs.debugLong();
    }

    public String printListDescriptions() {

        String displayList = "";
        String displayItem = "";
        int itemCount = itemsList.length;

        displayMsgs.debugHeader("ListOfThings printListDescriptions");
        displayMsgs.debugOutput("There are " + itemCount + " items in the list.");

        if (itemCount <= 0) {
            displayList = "";
        } else {

            for (int itemCounter = 0; itemCounter < itemCount; itemCounter++) {
                if (itemsList[itemCounter] != null && !itemsList[itemCounter].getId().isEmpty()) {
                    displayMsgs.debugOutput("  [" + itemCounter + "] " + itemsList[itemCounter].getId() + " " + itemsList[itemCounter].getName());
                    displayItem = itemsList[itemCounter].getDescription();

                    if (!displayList.isEmpty()) {
                        displayList += " ";
                    }
                    displayList += displayItem;
                } else {
                    displayMsgs.debugOutput("  [" + itemCounter + "] is empty.");
                }
            }

            if (displayList.isEmpty()) {
                displayList = "";
            }
        }
        displayMsgs.debugOutput(" " + displayList);
        displayMsgs.debugLong();

        return displayList;
    }


    public String stringOfActions(ListOfThings playerInventory, Room currentRoom) {

        // This will return all actions associated with the List, if they are valid

        String thisActionID;
        String thisActionName;
        String thisActionType;
        String actionList = "";

        displayMsgs.debugHeader("ListOfThings stringOfActions");
        displayMsgs.debugOutput("  Returning action list for " + listType + ", " + listName);

        int listLength = itemsList.length - 1;

        for (int listPos = 0; listPos <= (listLength); listPos++) {

            if (itemsList[listPos] == null || itemsList[listPos].getName().isEmpty()) {
                //displayMsgs.debugOutput("    [" + listPos + "] is null, so no action to add.");
            } else {

                // If this is a list of Actions, get the name. If this is a list of Items (RoomInv, PlayerInv), return
                // the action name

                if (listType.equalsIgnoreCase("Actions")) {
                    thisActionID = itemsList[listPos].getId();
                    thisActionName = itemsList[listPos].getName();
                    thisActionType = itemsList[listPos].getActionType();
                    displayMsgs.debugOutput("    [" + listPos + "] is action " + thisActionID + " " + thisActionName);
                } else {
                    thisActionID = itemsList[listPos].getActionID();
                    thisActionName = gameActions.findName(thisActionID);
                    thisActionType = gameActions.findType(thisActionID);
                    displayMsgs.debugOutput("  [" + listPos + "] " + itemsList[listPos].getName() + " has action " + thisActionID + " " + thisActionName);
                }

                // If this is from an Action list and an RO item, we don't need to return it since it will
                // have been returned in the with with PlayerInventory

                if (thisActionType.equalsIgnoreCase("RO") && listType.equalsIgnoreCase("Actions")) {
                    continue;
                }

                // If the Type contains "R" make sure were in the right Room

                if (thisActionType.contains("R")) {
                    if (currentRoom.getActions().posInList(thisActionID) == 999) {
                        displayMsgs.debugOutput("  Action " + thisActionID + " " + thisActionName + " is missing the required Room");
                        continue;
                    }
                }

                // If the Type contains "O" make sure the Item is in the Player Inventory

                if (thisActionType.contains("O")) {
                    if (playerInventory.actionInList(thisActionID) == 999) {
                        displayMsgs.debugOutput("  Action " + thisActionID + " " + thisActionName + " is missing the required Item in Player Inventory.");
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
            displayMsgs.debugOutput("No actions were found.");
        } else {
            displayMsgs.debugOutput("Returning action list: " + actionList);
        }

        displayMsgs.debugLong();
        return actionList;
    }

    public String findName(String findThing) {

        int itemPos = this.posInList(findThing);

        if (itemPos < 999) {
            displayMsgs.debugOutput("  Found Item " + findThing + ", returning Name " + itemsList[itemPos].getName());
            return itemsList[itemPos].getName();
        } else {
            displayMsgs.debugOutput("  Item " + findThing + " is not in the Library.");
            return "";
        }
    }

    public ListThing getListThing(int thisPos) {
        return this.itemsList[thisPos];
    }

    public void setListThing(int thisPos, ListThing thisThing) {
        this.itemsList[thisPos] = thisThing;
    }
}