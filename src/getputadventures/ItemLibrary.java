package getputadventures;

import java.io.File;
import java.util.Scanner;

public class ItemLibrary {

    // We will need to write out the Library modifications for the save game...

    private int libraryCount = 7; // This is a count of the Items in the .txt file
    private String[] itemIDs = new String[libraryCount];
    private String[] itemNames = new String[libraryCount];
    private String[] itemDescriptions = new String[libraryCount];
    private String[] itemActions = new String[libraryCount];
    private String[] itemPickup = new String[libraryCount];
    private Boolean showDebug;

    public ItemLibrary(Boolean mainDebug) {
        showDebug = mainDebug;
    }

    public void loadLibrary() throws Exception {

        debugLong();
        debugOutput("ItemLibrary loadLibrary");

        // This program will read in the Library from the text file

        int itemPos = 0;

        String pathname = "C:\\getputadventures\\items.txt";
        File file = new File(pathname);
        Scanner scan = new Scanner(file);
        scan.useDelimiter("/|\\r\\n");

        debugOutput("  Loading Item Library from " + pathname);
        debugShort();

        // throw out the first line, it's the field descriptions for the end user
        scan.nextLine();

        while (scan.hasNext()) {
            itemIDs[itemPos] = scan.next();
            debugOutput("  Checking line " + itemPos);

            if (!itemIDs[itemPos].isEmpty()) {
                itemNames[itemPos] = scan.next();
                itemDescriptions[itemPos] = scan.next();
                itemActions[itemPos] = scan.next();
                itemPickup[itemPos] = scan.next();

                debugOutput("    Item ID          " + itemIDs[itemPos]);
                debugOutput("    Item Name        " + itemNames[itemPos]);
                debugOutput("    Item Description " + itemDescriptions[itemPos]);
                debugOutput("    Item Action      " + itemActions[itemPos]);
                debugOutput("    Item Pickup      " + itemPickup[itemPos]);
                debugShort();
            } else {
                debugOutput("  All done!");
            }
            itemPos += 1;
        }
        scan.close();
        debugLong();
    }

    public ListThing readThing(String findThing) {

        // This will find the item in the Library and then create a new ListThing with that information.

        debugLong();
        debugOutput("ItemLibrary readThing");
        debugOutput("  Looking for Exit " + findThing);

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

            debugOutput("  Creating item...");
            debugOutput("    Item ID          " + thisID);
            debugOutput("    Item Name        " + thisName);
            debugOutput("    Item Description " + thisDescription);
            debugOutput("    Item Action      " + thisActionID);
            debugOutput("    Item Pickup      " + thisPickup);
            debugLong();

            thisItem = new ListThing(thisType, thisID, thisName, thisDescription, thisActionID, thisCommand, thisActionType, thisPickup, showDebug);
            return thisItem;
        } else {
            debugOutput("Unable to find Item " + findThing);
            debugLong();
            return thisItem;
        }
    }

    public int isInLibrary(String findThing) {
        // This will find the location the Library of the Item or it will return 999
        // Checks for matches on ID or Name for simplicity's sake

        debugLong();
        debugOutput("ItemLibrary isInLibrary");
        debugOutput("  Trying to find " + findThing);

        for (int itemPos = 0; itemPos < itemIDs.length; itemPos++) {
            debugOutput("Checking [" + itemPos + "] " + itemIDs[itemPos] + " " + itemNames[itemPos] + ".");

            if (itemIDs[itemPos].equalsIgnoreCase(findThing) || itemNames[itemPos].equalsIgnoreCase(findThing)) {
                debugOutput("Item " + findThing + " was in the Item Library at [" + itemPos + "].");
                debugLong();
                return itemPos;
            }
        }

        debugOutput("Item " + findThing + " was not in the Item Library.");
        debugLong();
        return 999;
    }

    public String findID(String findThing) {
        // Given the Name or ID for the Item, it will return the ID from the Library

        debugLong();
        debugOutput("ItemLibrary findID");

        int itemPos = this.isInLibrary(findThing);

        if (itemPos < 999) {
            debugOutput("Found Item " + findThing + ".");
            debugLong();
            return itemIDs[itemPos];
        } else {
            debugOutput("Item " + findThing + " is not in the Library.");
            debugLong();
            return "";
        }
    }

    public void removeItemAction(String updateID) {

        // Items can only have one action at a time, so removing the action will just
        // clear the value.

        debugLong();
        debugOutput("ItemLibrary removeItemAction");
        debugOutput("  Trying to remove Action from Item " + updateID);

        int foundPos = this.isInLibrary(updateID);

        if (foundPos < 999) {
            this.itemActions[foundPos] = "";
            debugOutput("  Removed Action from Item " + updateID);
            debugLong();
        } else {
            debugOutput("  Unable to find Item " + updateID + " in Library.");
            debugLong();
        }
    }

    public void addItemAction(String updateID, String actionID, ActionLibrary gameActions) {
        // Items can only have one action at a time, so adding the action will just replace whatever value is there.

        debugLong();
        debugOutput("ItemLibrary removeItemAction");
        debugOutput("  Trying to update Item " + updateID + " with Action " + actionID);

        // Validate the Action ID
        actionID = gameActions.findID(actionID);

        if (!actionID.isEmpty()) {
            int foundPos = this.isInLibrary(updateID);

            if (foundPos < 999) {
                this.itemActions[foundPos] = actionID;
                debugOutput("  Action " + actionID + " was added to the Item " + updateID + ".");
                debugLong();
            } else {
                debugOutput("  Unable to find Item " + updateID + " in Library.");
                debugLong();
            }
        } else {
            debugOutput("  Action " + actionID + " is not in the Library, can't update Item " + updateID);
            debugLong();
        }
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