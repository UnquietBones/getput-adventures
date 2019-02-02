package getputadventures;

import java.io.File;
import java.util.Scanner;

public class ItemLibrary {

    // Changes made
    //  Added removeItemAction
    //  Added addItemAction
    //  Changed all println with variables to printf's.
    //  Turned the debug dash-breaks into statics so they are consistent.
    //  Removed Destination
    //  Added actionType
    //  Added GetID
    //  Tried to remove as many global variables as possible
    //  Rewrote getID to use isInLibrary
    //  Added itemPickup


    // We will need to write out the Library modifications for the save game...

    private static String LONGDASH = "--------------------------------";
    private static String SHORTDASH = "----------------";
    private int libraryCount = 9; // This is a count of the Items in the .txt file
    public String[] itemIDs = new String[libraryCount];
    public String[] itemNames = new String[libraryCount];
    public String[] itemDescriptions = new String[libraryCount];
    public String[] itemActions = new String[libraryCount];
    public String[] itemPickup = new String[libraryCount];
    private Boolean showDebug;

    public ItemLibrary(Boolean mainDebug) {
        showDebug = mainDebug;
    }

    public void loadLibrary() throws Exception {

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ItemLibrary loadLibrary");
        }

        // This program will read in the Library from the text file

        int itemPos = 0;

        // pass the path to the file as a parameter
        String pathname = "C:\\getputadventures\\items.txt";
        File file = new File(pathname);
        Scanner scan = new Scanner(file);
        scan.useDelimiter("/|\\r\\n");

        if (showDebug) {
            System.out.printf("  Loading Item Library from %s %n", pathname);
            System.out.println(SHORTDASH);
        }

        // throw out the first line, it's the field descriptions for the end user
        scan.nextLine();

        while (scan.hasNext()) {

            itemIDs[itemPos] = scan.next();
            if (showDebug) {
                System.out.printf("  Checking line %d %n", itemPos);
            }

            if (!itemIDs[itemPos].isEmpty()) {
                itemNames[itemPos] = scan.next();
                itemDescriptions[itemPos] = scan.next();
                itemActions[itemPos] = scan.next();
                itemPickup[itemPos] = scan.next();

                if (showDebug) {
                    System.out.printf("    Item ID          %s %n", itemIDs[itemPos]);
                    System.out.printf("    Item Name        %s %n", itemNames[itemPos]);
                    System.out.printf("    Item Description %s %n", itemDescriptions[itemPos]);
                    System.out.printf("    Item Action      %s %n", itemActions[itemPos]);
                    System.out.printf("    Item Pickup      $s %n", itemPickup[itemPos]);
                    System.out.println("    " + SHORTDASH);
                }
            } else {
                if (showDebug) {
                    System.out.println("  All done!");
                }
            }
            itemPos += 1;
        }
        scan.close();
        System.out.println(LONGDASH);
    }

    public ListThing readThing(String findThing) {

        // This will find the item in the Library and then create a new ListThing with
        // that information.

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ItemLibrary readThing");
            System.out.printf("  Looking for Exit %s %n", findThing);
        }

        ListThing thisItem = new ListThing("Item", "", "", "", "", "", "", "", showDebug);
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

            if (showDebug) {
                System.out.println("  Creating item...");
                System.out.printf("    Item ID          %s %n", thisID);
                System.out.printf("    Item Name        %s %n", thisName);
                System.out.printf("    Item Description %s %n", thisDescription);
                System.out.printf("    Item Action      %s %n", thisActionID);
                System.out.printf("    Item Pickup      %s %n", thisPickup);
                System.out.println(LONGDASH);
            }

            thisItem = new ListThing(thisType, thisID, thisName, thisDescription, thisActionID, thisCommand, thisActionType, thisPickup, showDebug);
            return thisItem;
        } else {
            System.out.printf("Unable to find Item %s %n", findThing);
            System.out.println(LONGDASH);
            return thisItem;
        }
    }

    public int isInLibrary(String findThing) {
        // This will find the location the Library of the Item or it will return 999
        // Checks for matches on ID or Name for simplicity's sake

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ItemLibrary isInLibrary");
            System.out.printf("  Trying to find %s %n", findThing);
        }

        for (int itemPos = 0; itemPos < itemIDs.length; itemPos++) {

            if (showDebug) {
                System.out.printf("Checking [%d] %s, %s %n", itemPos, itemIDs[itemPos], itemNames[itemPos]);
            }

            if (itemIDs[itemPos].equalsIgnoreCase(findThing) || itemNames[itemPos].equalsIgnoreCase(findThing)) {

                if (showDebug) {
                    System.out.printf("Item %s was in the Item Library at [%d]. %n", findThing, itemPos);
                    System.out.println(LONGDASH);
                }
                return itemPos;
            }
        }

        if (showDebug) {
            System.out.printf("Item %s was not in the Item Library. %n", findThing);
            System.out.println(LONGDASH);
        }
        return 999;
    }

    public String getID(String findThing) {

        // Given the Name or ID for the Item, it will return the ID from the Library

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ItemLibrary getID");
        }

        int itemPos = this.isInLibrary(findThing);

        if (itemPos < 999) {
            return itemIDs[itemPos];
        } else {
            findThing = "";
            return findThing;
        }
    }

    public void removeItemAction(String updateID) {

        // Items can only have one action at a time, so removing the action will just
        // clear the value.

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ItemLibrary removeItemAction");
            System.out.printf("  Trying to update item %s %n", updateID);
        }

        int foundPos = this.isInLibrary(updateID);

        if (foundPos < 999) {

            this.itemActions[foundPos] = "";

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

        int foundPos = this.isInLibrary(updateID);

        if (foundPos < 999) {

            this.itemActions[foundPos] = actionID;

            if (showDebug) {
                System.out.printf("  Action %s was added to the Item %n", actionID);
                System.out.println(LONGDASH);
            }
        }
    }
}