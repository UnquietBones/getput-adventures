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
    private DebugMsgs debugMessage;
    private DisplayMsgs displayMsgs = new DisplayMsgs();


    public ListOfThings(String newListName, String newListType, int newMaxItems, ItemLibrary newgameItems, ActionLibrary newgameActions, boolean mainDebug) {

        listType = newListType;
        listName = newListName;
        maxItems = newMaxItems;
        itemsList = new ListThing[maxItems];
        gameItems = newgameItems;
        gameActions = newgameActions;
        showDebug = mainDebug;

        debugMessage = new DebugMsgs(showDebug);
    }

    public boolean addItem(String newItem, boolean showMsg) {

        debugMessage.debugHeader("ListOfThings addItem");
        debugMessage.debugOutput("Attempting to add " + newItem + " to " + listName + " " + listType);

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
                debugMessage.debugLong();
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
        debugMessage.debugLong();
        return false;
    }

    public boolean removeThing(String thisThing, boolean showMsg) {

        // This will remove the Thing from the List, if found.

        debugMessage.debugHeader("ListOfThings removeThing");
        debugMessage.debugOutput("  Trying to remove " + thisThing + " from " + listType + ", " + listType);

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {
            //debugMessage.debugOutput("    Checking [" + itemCounter + "] " + itemsList[itemCounter].getId() + " " + itemsList[itemCounter].getName());

            if (itemsList[itemCounter].getId().equalsIgnoreCase(thisThing) || itemsList[itemCounter].getName().equalsIgnoreCase(thisThing)) {
                displayMsgs.displayMessage("RemovedFromList", itemsList[itemCounter].getName(), listName, showMsg);
                itemsList[itemCounter].clearThing();
                debugMessage.debugLong();
                return true;
            }
        }

        displayMsgs.displayMessage("WasNotInList", thisThing, listName, showMsg);
        debugMessage.debugLong();
        return false;
    }

    public int freeSpot() {

        // This will return the position of the first empty slot in the list or 999

        debugMessage.debugHeader("ListOfThings freeSpot");
        debugMessage.debugOutput("  Looking for an empty slot in " + listType + " " + listName);

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {
            if (itemsList[itemCounter] == null || itemsList[itemCounter].getName().isEmpty()) {
                debugMessage.debugOutput("  Found empty spot at [" + itemCounter + "].");
                debugMessage.debugLong();
                return itemCounter;
            } else {
                debugMessage.debugOutput("  Checking [" + itemCounter + "] " + itemsList[itemCounter].getId() + " " + itemsList[itemCounter].getName());
            }
        }
        debugMessage.debugOutput("  There are no empty spots in the list.");
        debugMessage.debugLong();
        return 999;
    }

    public ListThing transferThing(String thisThing) {

        ListThing tempThing = null;
        int itemPos = posInList(thisThing);

        debugMessage.debugHeader("ListOfThings transferThing");
        debugMessage.debugOutput("Trying to get reference for item " + thisThing + " and remove it from the list.");

        if (itemPos < 999) {
            tempThing = itemsList[itemPos];
            itemsList[itemPos] = null;

            debugMessage.debugOutput("Passing over " + thisThing + " from " + listName);
            debugMessage.debugLong();
            return tempThing;
        }

        debugMessage.debugOutput("Item " + thisThing + " was not in " + listName);
        debugMessage.debugLong();
        return tempThing;
    }

    public int posInList(String findID) {

        debugMessage.debugHeader("ListOfThings posInList");
        debugMessage.debugOutput("  Looking for " + findID + " in " + listType + " " + listName);

        for (int itemCounter = 0; itemCounter < itemsList.length; itemCounter++) {
            if (itemsList[itemCounter] != null) {
                if (itemsList[itemCounter].getId().equalsIgnoreCase(findID) || itemsList[itemCounter].getName().equalsIgnoreCase(findID)) {
                    debugMessage.debugOutput("  Found " + findID + " at " + itemCounter);
                    debugMessage.debugLong();
                    return itemCounter;
                }
            }
        }
        debugMessage.debugOutput("Didn't find " + findID);
        debugMessage.debugLong();
        return 999;
    }

    public int actionInList(String findAction) {

        debugMessage.debugHeader("ListOfThings actionInList");
        debugMessage.debugOutput("  Looking for " + findAction + " in " + listType + " " + listName);

        findAction = gameActions.findID(findAction);

        int itemCount = itemsList.length - 1;

        for (int itemCounter = 0; itemCounter <= (itemCount); itemCounter++) {
            //debugMessage.debugOutput("    Checking item " + itemCounter);

            if (itemsList[itemCounter] == null || itemsList[itemCounter].getName().isEmpty()) {
                //debugMessage.debugOutput("    [" + itemCounter + "] is null");
            } else {
                //debugMessage.debugOutput("    [" + itemCounter + "] " + itemsList[itemCounter].getName() + " " + itemsList[itemCounter].getActionID());
                //debugMessage.debugLong();

                if (itemsList[itemCounter].getActionID().equalsIgnoreCase(findAction)) {
                    debugMessage.debugOutput("  Found action " + findAction + " on item " + itemsList[itemCounter].getName());
                    debugMessage.debugLong();
                    return itemCounter;
                }
            }
        }

        debugMessage.debugOutput("No matching Action found.");
        debugMessage.debugLong();
        return 999;
    }

    public boolean removeItemAction(String itemID) {

        // Items can only have one action at a time, so removing the action will just
        // clear the value.

        debugMessage.debugHeader("ItemLibrary removeItemsAction");
        debugMessage.debugOutput("  Trying to update item " + itemID);

        int foundPos = this.posInList(itemID);

        if (foundPos < 999) {
            this.itemsList[foundPos].setActionID("");
            debugMessage.debugOutput("  Removed Action from Item");
            debugMessage.debugLong();
            return true;
        } else {
            debugMessage.debugOutput("  Unable to find Item in Library.");
            debugMessage.debugLong();
            return false;
        }
    }

    public boolean addItemAction(String updateID, String actionID) {

        // Items can only have one action at a time, so adding the action will just
        // replace whatever value is there.

        debugMessage.debugHeader("ItemLibrary addItemsAction");
        debugMessage.debugOutput("  Trying to update item " + updateID + " with " + actionID);

        int foundPos = this.posInList(updateID);

        if (foundPos < 999) {
            this.itemsList[foundPos].setActionID(actionID);
            debugMessage.debugOutput("  Action " + actionID + " was added to Item " + updateID);
            debugMessage.debugLong();
            return true;
        }

        return false;
    }

    public void printListOfThings(String listType, ListOfThings playerInventory) {

        // This will print the list, skipping any blank entries

        String displayList = "";
        String displayItem;
        int itemCount = itemsList.length;

        debugMessage.debugHeader("ListOfThings printListOfThings");
        debugMessage.debugOutput("  Expecting " + maxItems + " maxItems in " + listName + " of type " + listType);
        debugMessage.debugOutput("  Found " + itemsList.length + " items.");

        if (itemCount <= 0) {
            displayList = "None";
        } else {
            for (int itemCounter = 0; itemCounter < itemCount; itemCounter++) {
                displayItem = "";

                if (itemsList[itemCounter] != null && !itemsList[itemCounter].getId().isEmpty()) {
                    debugMessage.debugOutput("  [" + itemCounter + "] " + itemsList[itemCounter].getId() + " " + itemsList[itemCounter].getName());

                    // Check to see if this is an Action

                    if (listType.equalsIgnoreCase("Do Action")) {
                        debugMessage.debugOutput("    This is an Action not an Item.");

                        // Check Action against player's inventory, if needed

                        if (itemsList[itemCounter].getActionType().equalsIgnoreCase("RO")) {
                            debugMessage.debugOutput("  This is an RO item, checking Player Inventory");

                            if (playerInventory.actionInList(itemsList[itemCounter].getId()) < 999) {
                                debugMessage.debugOutput("    Action is in Player Inventory.");
                                displayItem = itemsList[itemCounter].getName();
                            } else {
                                debugMessage.debugOutput("    Action is not in Player Inventory.");
                            }
                        } else {
                            debugMessage.debugOutput("    Action is not an RO Item.");
                            displayItem = itemsList[itemCounter].getName();
                        }
                    } else {
                        debugMessage.debugOutput("    This is an Item not an Action.");
                        displayItem = itemsList[itemCounter].getName();
                    }

                    if (!displayItem.isEmpty()) {

                        if (!displayList.isEmpty()) {
                            displayList += ", ";
                        }
                        displayList += displayItem;
                    }
                } else {
                    debugMessage.debugOutput("  [" + itemCounter + "] is empty");
                }
            }

            if (displayList.isEmpty()) {
                displayList = "None";
            }
        }
        displayMsgs.displayOutput("[" + listType + "] : " + displayList);
        debugMessage.debugLong();
    }

    public String printListDescriptions() {

        String displayList = "";
        String displayItem = "";
        int itemCount = itemsList.length;

        debugMessage.debugHeader("ListOfThings printListDescriptions");
        debugMessage.debugOutput("There are " + itemCount + " items in the list.");

        if (itemCount <= 0) {
            displayList = "";
        } else {

            for (int itemCounter = 0; itemCounter < itemCount; itemCounter++) {
                if (itemsList[itemCounter] != null && !itemsList[itemCounter].getId().isEmpty()) {
                    debugMessage.debugOutput("  [" + itemCounter + "] " + itemsList[itemCounter].getId() + " " + itemsList[itemCounter].getName());
                    displayItem = itemsList[itemCounter].getDescription();

                    if (!displayList.isEmpty()) {
                        displayList += " ";
                    }
                    displayList += displayItem;
                } else {
                    debugMessage.debugOutput("  [" + itemCounter + "] is empty.");
                }
            }

            if (displayList.isEmpty()) {
                displayList = "";
            }
        }
        debugMessage.debugOutput(" " + displayList);
        debugMessage.debugLong();

        return displayList;
    }


    public String stringOfActions(ListOfThings playerInventory, Room currentRoom) {

        // This will return all actions associated with the List, if they are valid

        String thisActionID;
        String thisActionName;
        String thisActionType;
        String actionList = "";

        debugMessage.debugHeader("ListOfThings stringOfActions");
        debugMessage.debugOutput("  Returning action list for " + listType + ", " + listName);

        int listLength = itemsList.length - 1;

        for (int listPos = 0; listPos <= (listLength); listPos++) {

            if (itemsList[listPos] == null || itemsList[listPos].getName().isEmpty()) {
                //debugMessage.debugOutput("    [" + listPos + "] is null, so no action to add.");
            } else {

                // If this is a list of Actions, get the name. If this is a list of Items (RoomInv, PlayerInv), return
                // the action name

                if (listType.equalsIgnoreCase("Actions")) {
                    thisActionID = itemsList[listPos].getId();
                    thisActionName = itemsList[listPos].getName();
                    thisActionType = itemsList[listPos].getActionType();
                    debugMessage.debugOutput("    [" + listPos + "] is action " + thisActionID + " " + thisActionName);
                } else {
                    thisActionID = itemsList[listPos].getActionID();
                    thisActionName = gameActions.findName(thisActionID);
                    thisActionType = gameActions.findType(thisActionID);
                    debugMessage.debugOutput("  [" + listPos + "] " + itemsList[listPos].getName() + " has action " + thisActionID + " " + thisActionName);
                }

                // If this is from an Action list and an RO item, we don't need to return it since it will
                // have been returned in the with with PlayerInventory

                if (thisActionType.equalsIgnoreCase("RO") && listType.equalsIgnoreCase("Actions")) {
                    continue;
                }

                // If the Type contains "R" make sure were in the right Room

                if (thisActionType.contains("R")) {
                    if (currentRoom.getActions().posInList(thisActionID) == 999) {
                        debugMessage.debugOutput("  Action " + thisActionID + " " + thisActionName + " is missing the required Room");
                        continue;
                    }
                }

                // If the Type contains "O" make sure the Item is in the Player Inventory

                if (thisActionType.contains("O")) {
                    if (playerInventory.actionInList(thisActionID) == 999) {
                        debugMessage.debugOutput("  Action " + thisActionID + " " + thisActionName + " is missing the required Item in Player Inventory.");
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
            debugMessage.debugOutput("No actions were found.");
        } else {
            debugMessage.debugOutput("Returning action list: " + actionList);
        }

        debugMessage.debugLong();
        return actionList;
    }

    public String findName(String findThing) {

        int itemPos = this.posInList(findThing);

        if (itemPos < 999) {
            debugMessage.debugOutput("  Found Item " + findThing + ", returning Name " + itemsList[itemPos].getName());
            return itemsList[itemPos].getName();
        } else {
            debugMessage.debugOutput("  Item " + findThing + " is not in the Library.");
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