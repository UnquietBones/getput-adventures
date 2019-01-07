package getput.adventures;

import java.io.File;
import java.util.Scanner;

public class ItemLibrary {

    // Currently not intending to write a modified Library for the save game, but we'll see...
    // Should only need to load the Library and then create individual items as needed.

    private Boolean showDebug;
    private String[] InventoryItemNames = new String[1];
    private String[] InventoryItemDescriptions = new String[1];
    private String[] InventoryItemActions = new String[1];
    private String[] InventoryItemActionDescriptions = new String[1];

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

            InventoryItemNames[InventoryItemCounter] = scan.next();
            if (showDebug) {
                System.out.println("Checking line " + InventoryItemCounter);
            }

            if (!InventoryItemNames[InventoryItemCounter].isEmpty()) {
                InventoryItemDescriptions[InventoryItemCounter] = scan.next();
                InventoryItemActions[InventoryItemCounter] = scan.next();
                InventoryItemActionDescriptions[InventoryItemCounter] = scan.next();

                if (showDebug) {
                    System.out.println("InventoryItem Name               " + InventoryItemNames[InventoryItemCounter]);
                    System.out.println("InventoryItem Description        " + InventoryItemDescriptions[InventoryItemCounter]);
                    System.out.println("InventoryItem Action             " + InventoryItemActions[InventoryItemCounter]);
                    System.out.println("InventoryItem Action Description " + InventoryItemActionDescriptions[InventoryItemCounter]);
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

        for (int InventoryItemPos = 0; InventoryItemPos < InventoryItemNames.length; InventoryItemPos++) {
            if (InventoryItemNames[InventoryItemPos].equals(findThisID)) {

                thisName = findThisID;
                thisDescription = InventoryItemDescriptions[InventoryItemPos];
                thisAction = InventoryItemActions[InventoryItemPos];
                thisActionDescription = InventoryItemActionDescriptions[InventoryItemPos];

                thisItem = new InventoryItem(thisName, thisDescription, thisAction, thisActionDescription, showDebug);
                return thisItem;
            }
        }

        // Still working under the 'any errors cause program to halt' idea

        System.out.println("ERMAHGERD! Unable to find Inventory Item " + findThisID + ", aborting game.");
        exitGame = true;
        return thisItem;
    }
}
