package getput.adventures;

import java.io.File;
import java.util.Scanner;

public class ActionLibrary {

    // Currently not intending to write a modified Library for the save game, but we'll see...
    // Should only need to load the Library and then create individual items as needed.

    int libraryCount = 3;

    private boolean showDebug;

    private String[] actionID           = new String[libraryCount];
    private String[] actionName         = new String[libraryCount];
    private String[] actionDescriptions = new String[libraryCount];
    private String[] actionCommands     = new String[libraryCount];

    public ActionLibrary(boolean mainDebug) {
        showDebug = mainDebug;
    }

    public void loadLibrary() throws Exception {

        // This program will read in the Library from the text file

        if (showDebug) {
            System.out.println("Starting ActionLibrary loadLibrary...");
        }

        int itemPos = 0;

        // pass the path to the file as a parameter
        File file = new File("C:\\getputadventures\\actions.txt");
        Scanner scan = new Scanner(file);
        scan.useDelimiter("/|\\r\\n");

        // throw out the first line, it's the field descriptions for the end user
        scan.nextLine();

        while (scan.hasNext()) {

            actionID[itemPos] = scan.next();
            if (showDebug) {
                System.out.println("Checking line " + itemPos);
            }

            if (!actionID[itemPos].isEmpty()) {
                actionName[itemPos] = scan.next();
                actionDescriptions[itemPos] = scan.next();
                actionCommands[itemPos] = scan.next();

                if (showDebug) {
                    System.out.println("Action ID          " + actionID[itemPos]);
                    System.out.println("Action Name        " + actionName[itemPos]);
                    System.out.println("Action Description " + actionDescriptions[itemPos]);
                    System.out.println("Action Command     " + actionCommands[itemPos]);
                    System.out.println("--------------------");
                }
            } else {
                if (showDebug) {
                    System.out.println("All done!");
                }
            }
            itemPos += 1;
        }
        scan.close();
    }

    public ListThing readItem(String findThisID) {

        // This will find the item in the Library and then create a new ActionItem with
        // that information.

        ListThing thisItem = null;
        String thisType = "Actions";

        String thisID;
        String thisName;
        String thisDescription;
        String thisCommand;

        String thisActionID = "";
        String thisDestination = "";

        if (showDebug) {
            System.out.println("Starting ActionLibrary readItem...");
        }

        for (int InventoryItemPos = 0; InventoryItemPos < actionID.length; InventoryItemPos++) {
            if (actionID[InventoryItemPos].equals(findThisID)) {

                thisID = findThisID;
                thisName = actionName[InventoryItemPos];
                thisDescription = actionDescriptions[InventoryItemPos];
                thisCommand = actionCommands[InventoryItemPos];

                if (showDebug) {
                    System.out.println("Creating item...");
                    System.out.println("Action ID          " +thisID);
                    System.out.println("Action Name        " + thisName);
                    System.out.println("Action Description " + thisDescription);
                    System.out.println("Action Command     " + thisCommand);
                }

                thisItem = new ListThing(thisType, thisID, thisName, thisDescription, thisActionID, thisDestination, thisCommand, showDebug);
                return thisItem;
            }
        }

        // Still working under the 'any errors cause program to halt' idea

        System.out.println("ERMAHGERD! Unable to find Inventory Item " + findThisID + ", aborting game.");
        return thisItem;
    }

    public boolean findItem(String findThisID){

        if (showDebug) {
            System.out.println("Starting ActionLibrary findItem...");
        }

        for (int itemPos = 0; itemPos < actionID.length; itemPos++) {
            if (actionID[itemPos].equals(findThisID)) {

                if (showDebug) {
                    System.out.println("Item "+findThisID+" was in the Item Library at ["+itemPos+"].");
                }

                return true;
            }
        }

        if (showDebug) {
            System.out.println("Item "+findThisID+" was not in the Item Library.");
        }

        return false;
    }
}
