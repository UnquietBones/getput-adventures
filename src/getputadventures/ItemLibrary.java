package getputadventures;

import java.io.File;
import java.util.Scanner;

public class ItemLibrary {

    // We will need to write out the Library modifications for the save game...

    private int libraryCount = 5; // This is a count of the Items in the .txt file
    private String[] itemIDs = new String[libraryCount];
    private String[] itemNames = new String[libraryCount];
    private String[] itemDescriptions = new String[libraryCount];
    private String[] itemActions = new String[libraryCount];
    private String[] itemPickup = new String[libraryCount];
    private Boolean showDebug;
    private DebugMsgs debugMessage;

    public ItemLibrary(Boolean mainDebug) {
        showDebug = mainDebug;
        debugMessage = new DebugMsgs(showDebug);
    }

    public void loadItemLibrary() throws Exception {

        // This program will read in the Library from the text file

        debugMessage.debugLong();
        debugMessage.debugOutput("ItemLibrary loadLibrary");

        int itemPos = 0;

        //String pathname = "C:\\getputadventures\\items.txt";
        String pathname = "C:\\getputadventures\\testitems.txt";
        File file = new File(pathname);
        Scanner scan = new Scanner(file);
        scan.useDelimiter("/|\\r\\n");

        debugMessage.debugOutput("  Loading Item Library from " + pathname);
        debugMessage.debugShort();

        // throw out the first line, it's the field descriptions for the end user
        scan.nextLine();

        while (scan.hasNext()) {
            itemIDs[itemPos] = scan.next();
            debugMessage.debugOutput("  Checking line " + itemPos);

            if (!itemIDs[itemPos].isEmpty()) {
                itemNames[itemPos] = scan.next();
                itemDescriptions[itemPos] = scan.next();
                itemActions[itemPos] = scan.next();
                itemPickup[itemPos] = scan.next();

                debugMessage.debugOutput("    Item ID          " + itemIDs[itemPos]);
                debugMessage.debugOutput("    Item Name        " + itemNames[itemPos]);
                debugMessage.debugOutput("    Item Description " + itemDescriptions[itemPos]);
                debugMessage.debugOutput("    Item Action      " + itemActions[itemPos]);
                debugMessage.debugOutput("    Item Pickup      " + itemPickup[itemPos]);
                debugMessage.debugShort();
            } else {
                debugMessage.debugOutput("  All done!");
            }
            itemPos += 1;
        }
        scan.close();
        debugMessage.debugLong();
    }

    public ListThing readItem(String findThing) {

        // This will find the item in the Library and then create a new ListThing with that information.

        debugMessage.debugLong();
        debugMessage.debugOutput("ItemLibrary readItem");
        debugMessage.debugOutput("  Looking for Item " + findThing);

        ListThing thisItem = new ListThing("Item", "", "", "", "",
                "", "", "", showDebug);
        int libraryPos;

        String thisType = "Item";
        String thisID;
        String thisName;
        String thisDescription;
        String thisActionID;
        String thisPickup;

        // These are unused fields for Items
        String thisCommand = "";
        String thisActionType = "";

        libraryPos = isInLibrary(findThing);

        if (libraryPos < 999) {

            thisID = itemIDs[libraryPos];
            thisName = itemNames[libraryPos];
            thisDescription = itemDescriptions[libraryPos];
            thisActionID = itemActions[libraryPos];
            thisPickup = itemPickup[libraryPos];

            debugMessage.debugOutput("  Creating item...");
            debugMessage.debugOutput("    Item ID          " + thisID);
            debugMessage.debugOutput("    Item Name        " + thisName);
            debugMessage.debugOutput("    Item Description " + thisDescription);
            debugMessage.debugOutput("    Item Action      " + thisActionID);
            debugMessage.debugOutput("    Item Pickup      " + thisPickup);
            debugMessage.debugLong();

            thisItem = new ListThing(thisType, thisID, thisName, thisDescription, thisActionID, thisCommand, thisActionType, thisPickup, showDebug);
            return thisItem;
        } else {
            debugMessage.debugOutput("Unable to find Item " + findThing);
            debugMessage.debugLong();
            return thisItem;
        }
    }

    public int isInLibrary(String findThing) {
        // This will find the location the Library of the Item or it will return 999
        // Checks for matches on ID or Name for simplicity's sake

        debugMessage.debugLong();
        debugMessage.debugOutput("ItemLibrary isInLibrary");

        for (int itemPos = 0; itemPos < itemIDs.length; itemPos++) {
            // debugMessage.debugOutput("Checking [" + itemPos + "] " + itemIDs[itemPos] + " " + itemNames[itemPos] + ".");

            if (itemIDs[itemPos].equalsIgnoreCase(findThing) || itemNames[itemPos].equalsIgnoreCase(findThing)) {
                debugMessage.debugOutput("Item " + findThing + " was in the Item Library at [" + itemPos + "] " + itemNames[itemPos]);
                debugMessage.debugLong();
                return itemPos;
            }
        }

        debugMessage.debugOutput("Item " + findThing + " was not in the Item Library.");
        debugMessage.debugLong();
        return 999;
    }

    public String findID(String findThing) {
        // Given the Name or ID for the Item, it will return the ID from the Library

        int itemPos = this.isInLibrary(findThing);

        if (itemPos < 999) {
            return itemIDs[itemPos];
        } else {
            return "";
        }
    }

    public boolean removeItemAction(String updateID) {

        // Items can only have one action at a time, so removing the action will just
        // clear the value.

        debugMessage.debugLong();
        debugMessage.debugOutput("ItemLibrary removeItemAction");
        debugMessage.debugOutput("  Trying to remove Action from Item " + updateID);

        int foundPos = this.isInLibrary(updateID);

        if (foundPos < 999) {
            this.itemActions[foundPos] = "";
            debugMessage.debugOutput("  Removed Action from Item " + updateID);
            debugMessage.debugLong();
            return true;
        } else {
            debugMessage.debugOutput("  Unable to find Item " + updateID + " in Library.");
            debugMessage.debugLong();
            return false;
        }
    }

    public boolean addItemAction(String updateID, String actionID, ActionLibrary gameActions) {
        // Items can only have one action at a time, so adding the action will just replace whatever value is there.

        debugMessage.debugLong();
        debugMessage.debugOutput("ItemLibrary removeItemAction");
        debugMessage.debugOutput("  Trying to update Item " + updateID + " with Action " + actionID);

        // Validate the Action ID
        actionID = gameActions.findID(actionID);

        if (!actionID.isEmpty()) {
            int foundPos = this.isInLibrary(updateID);

            if (foundPos < 999) {
                this.itemActions[foundPos] = actionID;
                debugMessage.debugOutput("  Action " + actionID + " was added to the Item " + updateID + ".");
                debugMessage.debugLong();
                return true;
            } else {
                debugMessage.debugOutput("  Unable to find Item " + updateID + " in Library.");
                debugMessage.debugLong();
                return false;
            }
        } else {
            debugMessage.debugOutput("  Action " + actionID + " is not in the Library, can't update Item " + updateID);
            debugMessage.debugLong();
            return false;
        }
    }
}