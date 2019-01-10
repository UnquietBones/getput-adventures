package getput.adventures;

import java.io.File;
import java.util.Scanner;

public class ItemLibrary {

    // Currently not intending to write a modified Library for the save game, but we'll see...
    // Should only need to load the Library and then create individual items as needed.

    int libraryCount = 1;

    private Boolean showDebug;
    private String[] inventoryItemNames = new String[libraryCount];
    private String[] inventoryItemDescriptions = new String[libraryCount];
    private String[] inventoryItemActions = new String[libraryCount];
    private String[] inventoryItemActionDescriptions = new String[libraryCount];
    private String[] inventoryItemActionCommands = new String[libraryCount];

    public ItemLibrary(Boolean mainDebug) {
        showDebug = mainDebug;
    }

    public void loadLibrary() throws Exception {

        // This program will read in the Library from the text file

        int InventoryItemCounter = 0;

        // pass the path to the file as a parameter
        File file = new File("C:\\getputadventures\\Items.txt");
        Scanner scan = new Scanner(file);
        scan.useDelimiter("/|\\r\\n");

        // throw out the first line, it's the field descriptions for the end user
        scan.nextLine();

        while (scan.hasNext()) {

            inventoryItemNames[InventoryItemCounter] = scan.next();
            if (showDebug) {
                System.out.println("Checking line " + InventoryItemCounter);
            }

            if (!inventoryItemNames[InventoryItemCounter].isEmpty()) {
                inventoryItemDescriptions[InventoryItemCounter] = scan.next();
                inventoryItemActions[InventoryItemCounter] = scan.next();
                inventoryItemActionDescriptions[InventoryItemCounter] = scan.next();
                inventoryItemActionCommands[InventoryItemCounter] = scan.next();

                if (showDebug) {
                    System.out.println("InventoryItem Name               " + inventoryItemNames[InventoryItemCounter]);
                    System.out.println("InventoryItem Description        " + inventoryItemDescriptions[InventoryItemCounter]);
                    System.out.println("InventoryItem Action             " + inventoryItemActions[InventoryItemCounter]);
                    System.out.println("InventoryItem Action Description " + inventoryItemActionDescriptions[InventoryItemCounter]);
                    System.out.println("InventoryItem Action Command     " + inventoryItemActionCommands[InventoryItemCounter]);
                    System.out.println("--------------------");
                }
            } else {
                if (showDebug) {
                    System.out.println("All done!");
                }
            }
            InventoryItemCounter += 1;
        }
        scan.close();
    }

    public InventoryItem readItem(String findThisID, boolean exitGame) {

        // This will find the item in the Library and then create a new InventoryItem with
        // that information.

        InventoryItem thisItem = null;
        String thisName;
        String thisDescription;
        String thisAction;
        String thisActionDescription;
        String thisActionCommand;

        for (int InventoryItemPos = 0; InventoryItemPos < inventoryItemNames.length; InventoryItemPos++) {
            if (inventoryItemNames[InventoryItemPos].equals(findThisID)) {

                thisName = findThisID;
                thisDescription = inventoryItemDescriptions[InventoryItemPos];
                thisAction = inventoryItemActions[InventoryItemPos];
                thisActionDescription = inventoryItemActionDescriptions[InventoryItemPos];
                thisActionCommand = inventoryItemActionCommands[InventoryItemPos];

                if (showDebug) {
                    System.out.println("Creating item...");
                    System.out.println("InventoryItem Name               " + thisName);
                    System.out.println("InventoryItem Description        " + thisDescription);
                    System.out.println("InventoryItem Action             " + thisAction);
                    System.out.println("InventoryItem Action Description " + thisActionDescription);
                    System.out.println("InventoryItem Action Command     " + thisActionCommand);
                }

                thisItem = new InventoryItem(thisName, thisDescription, thisAction, thisActionDescription, thisActionCommand, showDebug);
                return thisItem;
            }
        }

        // Still working under the 'any errors cause program to halt' idea

        System.out.println("ERMAHGERD! Unable to find Inventory Item " + findThisID + ", aborting game.");
        exitGame = true;
        return thisItem;
    }
}
