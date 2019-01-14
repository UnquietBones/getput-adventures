package getput.adventures;

import java.io.File;
import java.util.Scanner;

public class ActionLibrary {

    // Available actions are currently linked to a Room. There is no need to track changes as the game
    // progresses since the RoomMap will do so.

    int libraryCount = 3;

    private boolean showDebug;

    private String[] actionID = new String[libraryCount];
    private String[] actionName = new String[libraryCount];
    private String[] actionDescriptions = new String[libraryCount];
    private String[] actionCommands = new String[libraryCount];

    public ActionLibrary(boolean mainDebug) {
        showDebug = mainDebug;
    }

    public void loadLibrary() throws Exception {

        // This program will read in the Library from the text file

        if (showDebug) {
            System.out.println("-------------------------------------");
            System.out.println("Starting ActionLibrary loadLibrary...");
        }

        int itemPos = 0;

        // pass the path to the file as a parameter
        String pathname = "C:\\getputadventures\\actions.txt";
        File file = new File(pathname);
        Scanner scan = new Scanner(file);
        scan.useDelimiter("/|\\r\\n");

        if (showDebug) {
            System.out.println("Loading Action Library from " + pathname);
        }

        // throw out the first line, it's just the field descriptions for the developers
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
                    System.out.println("-------------------");
                }
            } else {
                if (showDebug) {
                    System.out.println("All done!");
                }
            }
            itemPos += 1;
        }
        scan.close();

        if (showDebug) {
            System.out.println("-------------------------------------");
        }
    }

    public ListThing readThing(String findThing) {

        // This will try and find (and return) the item either by Name or by ID from the Library.

        ListThing thisItem = null;
        String thisType = "Actions";

        String thisID;
        String thisName;
        String thisDescription;
        String thisCommand;

        String thisActionID = "";
        String thisDestination = "";

        if (showDebug) {
            System.out.println("----------------------------------");
            System.out.println("Starting ActionLibrary readThing...");
            System.out.println("Looking for Action " + findThing);
        }

        for (int InventoryItemPos = 0; InventoryItemPos < actionID.length; InventoryItemPos++) {
            if (actionID[InventoryItemPos].equals(findThing) || actionName[InventoryItemPos].equals(findThing)) {

                thisID = findThing;
                thisName = actionName[InventoryItemPos];
                thisDescription = actionDescriptions[InventoryItemPos];
                thisCommand = actionCommands[InventoryItemPos];

                if (showDebug) {
                    System.out.println("Creating item...");
                    System.out.println("Action ID          " + thisID);
                    System.out.println("Action Name        " + thisName);
                    System.out.println("Action Description " + thisDescription);
                    System.out.println("Action Command     " + thisCommand);
                    System.out.println("----------------------------------");
                }
                thisItem = new ListThing(thisType, thisID, thisName, thisDescription, thisActionID, thisDestination, thisCommand, showDebug);
                return thisItem;
            }
        }

        // Still working under the 'any errors cause program to halt' idea

        if (showDebug) {
            System.out.println("ERMAHGERD! Unable to find Action " + findThing + ".");
            System.out.println("----------------------------------");
        }

        return thisItem;
    }

    public boolean isInLibrary(String findThing) {

        // For simplicities sake, look for a match on the ID or the Name

        if (showDebug) {
            System.out.println("----------------------------------");
            System.out.println("Starting ActionLibrary isInLibrary...");
        }

        for (int itemPos = 0; itemPos < actionID.length; itemPos++) {
            if (actionID[itemPos].equals(findThing) || actionName[itemPos].equals(findThing)) {

                if (showDebug) {
                    System.out.println("Action " + findThing + " was in the Action Library at [" + itemPos + "].");
                    System.out.println("----------------------------------");
                }
                return true;
            }
        }

        if (showDebug) {
            System.out.println("Action " + findThing + " was not in the Action Library.");
            System.out.println("----------------------------------");
        }
        return false;
    }

    public String getID(String findThing) {

        // For this, we are providing the name and we need the ID

        if (showDebug) {
            System.out.println("----------------------------------");
            System.out.println("Starting ActionLibrary getID...");
        }

        for (int itemPos = 0; itemPos < actionID.length; itemPos++) {
            if (actionID[itemPos].equals(findThing) || actionName[itemPos].equals(findThing)) {

                if (showDebug) {
                    System.out.println("Action " + findThing + " was in the Action Library at [" + itemPos + "].");
                    System.out.println("----------------------------------");
                }
                return actionID[itemPos];
            }
        }

        if (showDebug) {
            System.out.println("Action " + findThing + " was not in the Action Library.");
            System.out.println("----------------------------------");
        }
        return findThing;
    }
}
