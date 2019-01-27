
working on this will not compile


package getputadventures;

import java.io.File;
import java.util.Scanner;

public class ItemLibrary {

    // We will need to write out the Library modifications for the save game...

    int libraryCount = 3; // This is a count of the Items in the .txt file

    private Boolean showDebug;
    private String[] itemIDs = new String[libraryCount];
    private String[] itemNames = new String[libraryCount];
    private String[] itemDescriptions = new String[libraryCount];
    private String[] itemActions = new String[libraryCount];

    public ItemLibrary(Boolean mainDebug) {
        showDebug = mainDebug;
    }

    public void loadLibrary() throws Exception {

        if (showDebug) {
            System.out.println("-----------------------------------");
            System.out.println("Starting ItemLibrary loadLibrary...");
        }

        // This program will read in the Library from the text file

        int itemPos = 0;

        // pass the path to the file as a parameter
        String pathname = "C:\\getputadventures\\items.txt";
        File file = new File(pathname);
        Scanner scan = new Scanner(file);
        scan.useDelimiter("/|\\r\\n");

        if (showDebug) {
            System.out.println("Loading Action Library from " + pathname);
            System.out.println(" -----------------");
        }

        // throw out the first line, it's the field descriptions for the end user
        scan.nextLine();

        while (scan.hasNext()) {

            itemIDs[itemPos] = scan.next();
            if (showDebug) {
                System.out.println("Checking line " + itemPos);
            }

            if (!itemIDs[itemPos].isEmpty()) {
                itemNames[itemPos] = scan.next();
                itemDescriptions[itemPos] = scan.next();
                itemActions[itemPos] = scan.next();

                if (showDebug) {
                    System.out.println(" Item ID          " + itemIDs[itemPos]);
                    System.out.println(" Item Name        " + itemNames[itemPos]);
                    System.out.println(" Item Description " + itemDescriptions[itemPos]);
                    System.out.println(" Item Action      " + itemActions[itemPos]);
                    System.out.println(" -----------------");
                }
            } else {
                if (showDebug) {
                    System.out.println("All done!");
                }
            }
            itemPos += 1;
        }
        scan.close();
        System.out.println("-----------------------------------");
    }

    public ListThing readThing(String findThing) {

        // This will find the item in the Library and then create a new ListThing with
        // that information.


        if (showDebug) {
            System.out.println("---------------------------------");
            System.out.println("Starting ItemLibrary readThing...");
            System.out.println("Looking for Exit " + findThing);
        }

        ListThing thisItem = null;
        String thisType = "Item";

        String thisID;
        String thisName;
        String thisDescription;
        String thisActionID;

        String thisDestination = "";
        String thisCommand = "";

        // Need to check both the ID and the Name

        for (int libraryPos = 0; libraryPos < itemIDs.length; libraryPos++) {

            String checkID = itemIDs[libraryPos];
            String checkName = itemNames[libraryPos];

            if (showDebug) {
                System.out.println("Checking InventoryLibrary [" + libraryPos + "] " + checkID + " " + checkName);
            }

            if (checkID.equalsIgnoreCase(findThing) || checkName.equalsIgnoreCase(findThing)) {

                thisID = itemIDs[libraryPos];
                thisName = itemNames[libraryPos];
                thisDescription = itemDescriptions[libraryPos];
                thisActionID = itemActions[libraryPos];

                if (showDebug) {
                    System.out.println("Creating item...");
                    System.out.println("Item ID          " + thisID);
                    System.out.println("Item Name        " + thisName);
                    System.out.println("Item Description " + thisDescription);
                    System.out.println("Item Action      " + thisActionID);
                    System.out.println("---------------------------------");
                }

                thisItem = new ListThing(thisType, thisID, thisName, thisDescription, thisActionID, thisDestination, thisCommand, showDebug);
                return thisItem;
            }
        }

        // Still working under the 'any errors cause program to halt' idea

        System.out.println("ERMAHGERD! Unable to find Item " + findThing + ".");
        System.out.println("---------------------------------");
        return thisItem;
    }

    public int isInLibrary(String findThing) {
        // This will find the location the Library of the Item or it will return 999

        if (showDebug) {
            System.out.println("-----------------------------------");
            System.out.println("Starting ItemLibrary isInLibrary...");
            System.out.println("Trying to find " + findThing);
        }

        for (int itemPos = 0; itemPos < itemIDs.length; itemPos++) {

            if (showDebug) {
                System.out.println("Checking [" + itemPos + "] " + itemIDs[itemPos]);
            }

            if (itemIDs[itemPos].equalsIgnoreCase(findThing)) {

                if (showDebug) {
                    System.out.println("Item " + findThing + " was in the Item Library at [" + itemPos + "].");
                    System.out.println("-----------------------------------");
                }

                return itemPos;
            }
        }

        if (showDebug) {
            System.out.println("Item " + findThing + " was not in the Item Library.");
            System.out.println("-----------------------------------");
        }

        return 999;
    }

public boolean removeItemAction(String updateID) {
        if (showDebug) {
            System.out.println("------------------------------");
            System.out.println("Starting ItemLibrary removeItemAction...");
            System.out.println("Trying to update item " + updateID);
        }

        String oldValue;
        String newValue;

        foundPod = this.isInLibrary(updateID);
    
        if 
    
        for (int itemPos = 0; itemPos < roomIDs.length; itemPos++) {
            if (itemIDs[itemPos].equalsIgnoreCase(updateID)) {

                if (showDebug) {
                    System.out.println("Updating item name from " + roomNames[roomPos] + " to " + changedRoom.name);
                }
                roomNames[roomPos] = changedRoom.name;

}

}
