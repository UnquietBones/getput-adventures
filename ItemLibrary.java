package getput.adventures;

import java.io.File;
import java.util.Scanner;

public class ItemLibrary {

    // Currently not intending to write a modified Library for the save game, but we'll see...
    // Should only need to load the Library and then create individual items as needed.

    int libraryCount = 3;

    private Boolean showDebug;
    private String[] inventoryItemIDs = new String[libraryCount];
    private String[] inventoryItemNames = new String[libraryCount];
    private String[] inventoryItemDescriptions = new String[libraryCount];
    private String[] inventoryItemActions = new String[libraryCount];

    public ItemLibrary(Boolean mainDebug) {
        showDebug = mainDebug;
    }

    public void loadLibrary() throws Exception {

        if (showDebug) {
            System.out.println("----------------------------------------");
            System.out.println("Starting InventoryLibrary loadLibrary...");
        }

        // This program will read in the Library from the text file

        int itemPos = 0;

        // pass the path to the file as a parameter
        File file = new File("C:\\getputadventures\\items.txt");
        Scanner scan = new Scanner(file);
        scan.useDelimiter("/|\\r\\n");

        // throw out the first line, it's the field descriptions for the end user
        scan.nextLine();

        while (scan.hasNext()) {

            inventoryItemIDs[itemPos] = scan.next();
            if (showDebug) {
                System.out.println("Checking line " + itemPos);
            }

            if (!inventoryItemIDs[itemPos].isEmpty()) {
                inventoryItemNames[itemPos] = scan.next();
                inventoryItemDescriptions[itemPos] = scan.next();
                inventoryItemActions[itemPos] = scan.next();

                if (showDebug) {
                    System.out.println("InventoryItem ID                 " + inventoryItemIDs[itemPos]);
                    System.out.println("InventoryItem Name               " + inventoryItemNames[itemPos]);
                    System.out.println("InventoryItem Description        " + inventoryItemDescriptions[itemPos]);
                    System.out.println("InventoryItem Action             " + inventoryItemActions[itemPos]);
                    System.out.println("--------------");
                }
            } else {
                if (showDebug) {
                    System.out.println("All done!");
                }
            }
            itemPos += 1;
        }
        scan.close();
        System.out.println("----------------------------------------");
    }

    public ListThing readItem(String findThisID) {

        // This will find the item in the Library and then create a new ListThing with
        // that information.


        if (showDebug) {
            System.out.println("-------------------------------------");
            System.out.println("Starting InventoryLibrary readItem...");
            System.out.println("Attempting to read Inventory item " + findThisID);
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

        for (int libraryPos = 0; libraryPos < inventoryItemIDs.length; libraryPos++) {

            String checkID = inventoryItemIDs[libraryPos];
            String checkName = inventoryItemNames[libraryPos];

            if (showDebug) {
                System.out.println("Checking InventoryLibrary [" + libraryPos + "] " + checkID + " " + checkName);
            }

            if (checkID.equals(findThisID) || checkName.equals(findThisID)) {

                thisID = inventoryItemIDs[libraryPos];
                thisName = inventoryItemNames[libraryPos];
                thisDescription = inventoryItemDescriptions[libraryPos];
                thisActionID = inventoryItemActions[libraryPos];

                if (showDebug) {
                    System.out.println("Creating item...");
                    System.out.println("InventoryItem ID                 " + thisID);
                    System.out.println("InventoryItem Name               " + thisName);
                    System.out.println("InventoryItem Description        " + thisDescription);
                    System.out.println("InventoryItem Action             " + thisActionID);
                }

                thisItem = new ListThing(thisType, thisID, thisName, thisDescription, thisActionID, thisDestination, thisCommand, showDebug);
                System.out.println("-------------------------------------");
                return thisItem;
            }
        }

        // Still working under the 'any errors cause program to halt' idea

        System.out.println("ERMAHGERD! Unable to find Inventory Item " + findThisID + ", aborting game.");
        System.out.println("-------------------------------------");
        return thisItem;
    }

    public boolean findItem(String findThisID) {

        if (showDebug) {
            System.out.println("-------------------------------------");
            System.out.println("Starting InventoryLibrary findItem...");
            System.out.println("Trying to find " + findThisID);
        }

        for (int itemPos = 0; itemPos < inventoryItemIDs.length; itemPos++) {

            if (showDebug) {
                System.out.println("Checking [" + itemPos + "] " + inventoryItemIDs[itemPos]);
            }

            if (inventoryItemIDs[itemPos].equals(findThisID)) {

                if (showDebug) {
                    System.out.println("Item " + findThisID + " was in the Item Library at [" + itemPos + "].");
                    System.out.println("-------------------------------------");
                }

                return true;
            }
        }

        if (showDebug) {
            System.out.println("Item " + findThisID + " was not in the Item Library.");
            System.out.println("-------------------------------------");
        }

        return false;
    }
}
