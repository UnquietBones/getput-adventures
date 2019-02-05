package getputadventures;

import java.io.File;
import java.util.Scanner;

public class ActionLibrary {

    /*
     * Available actions are linked to a Room or an Item. There is no need to track this Library as the game
     * progresses since the values will not change.
     */

    // List of methods so I can keep myself straight as I build this thing
    //  public void loadLibrary()
    //  public ListThing readThing(String findThing)
    //  public int isInLibrary(String findThing)
    //  public String findID(String findThing)
    //  public String findName(String findThing)
    //  public String findType(String findThing)
    //  private void debugLong()
    //  private void debugShort()
    //  private void debugOutput(String outputThis)

    private static int LIBRARYCOUNT = 18;
    private boolean showDebug;
    private String[] actionID = new String[LIBRARYCOUNT];
    private String[] actionName = new String[LIBRARYCOUNT];
    private String[] actionDescriptions = new String[LIBRARYCOUNT];
    private String[] actionCommands = new String[LIBRARYCOUNT];
    private String[] actionType = new String[LIBRARYCOUNT];

    public ActionLibrary(boolean mainDebug) {
        showDebug = mainDebug;
    }

    public void loadLibrary() throws Exception {

        /*
         * This method reads in the Library from the text file. Right now it's expecting a specific number
         * of Actions in the file and a specific path. Someday I need to change it so that both don't have
         * to be hardcoded.
         */

        debugLong();
        debugOutput("ActionLibrary loadLibrary");

        int actionPos = 0;

        String pathname = "C:\\getputadventures\\actions.txt";
        File file = new File(pathname);
        Scanner scan = new Scanner(file);
        scan.useDelimiter("/|\\r\\n");

        debugOutput("  Loading Action Library from " + pathname);

        // We can throw out the first line, it's just the field descriptions for the game designers.
        scan.nextLine();

        while (scan.hasNext()) {
            actionID[actionPos] = scan.next();
            debugOutput("  Checking line " + actionPos);

            if (!(actionID[actionPos] == null)) {
                actionName[actionPos] = scan.next();
                actionDescriptions[actionPos] = scan.next();
                actionCommands[actionPos] = scan.next();
                actionType[actionPos] = scan.next();

                debugOutput("    Action ID          " + actionID[actionPos]);
                debugOutput("    Action Name        " + actionName[actionPos]);
                debugOutput("    Action Description " + actionDescriptions[actionPos]);
                debugOutput("    Action Command     " + actionCommands[actionPos]);
                debugOutput("    Action Type        " + actionType[actionPos]);
                debugShort();
            } else {
                debugOutput("All done!");
            }
            actionPos += 1;
        }
        scan.close();
        debugLong();
    }

    public ListThing readThing(String findThing) {
        // This will try and find (and return) the item either by Name or by ID from the Library.

        ListThing thisItem = null;
        String thisType = "Actions";

        String thisID;
        String thisName;
        String thisDescription;
        String thisCommand;
        String thisActionType;

        // These are unused fields for Actions
        String thisActionID = "";
        String thisPickup = "";

        debugLong();
        debugOutput("ActionLibrary readThing");
        debugOutput("  Looking for Action " + findThing);

        int inventoryItemPos = this.isInLibrary(findThing);

        if (inventoryItemPos < 999) {

            thisID = findThing;
            thisName = actionName[inventoryItemPos];
            thisDescription = actionDescriptions[inventoryItemPos];
            thisCommand = actionCommands[inventoryItemPos];
            thisActionType = actionType[inventoryItemPos];

            debugOutput("  Creating item...");
            debugOutput("  Action ID          " + thisID);
            debugOutput("  Action Name        " + thisName);
            debugOutput("  Action Description " + thisDescription);
            debugOutput("  Action Command     " + thisCommand);
            debugLong();

            thisItem = new ListThing(thisType, thisID, thisName, thisDescription, thisActionID, thisCommand,
                    thisActionType, thisPickup, showDebug);

            return thisItem;
        }

        debugLong();
        return thisItem;
    }

    public int isInLibrary(String findThing) {
        /*
         * This returns the location the Library of the Item or 999. It checks for matches on ID or Name for
         * simplicity's sake.
         */

        debugLong();
        debugOutput("ActionLibrary isInLibrary");
        debugOutput("  Trying to find " + findThing);

        for (int actionPos = 0; actionPos < actionID.length; actionPos++) {
            debugOutput("  Checking [" + actionPos + "] " + actionID[actionPos] + " " + actionName[actionPos]);

            if (actionID[actionPos].equalsIgnoreCase(findThing) || actionName[actionPos].equalsIgnoreCase(findThing)) {
                debugOutput("  Action " + findThing + " was in the Item Library at [" + actionPos + "].");
                debugLong();
                return actionPos;
            }
        }

        debugOutput("  Action " + findThing + " is not in the Library.");
        debugLong();
        return 999;
    }

    public String findID(String findThing) {
        // Given the Name or ID for the Action, it will return the ID from the Library

        debugLong();
        debugOutput("ActionLibrary findID");

        int actionPos = this.isInLibrary(findThing);

        if (actionPos < 999) {
            debugOutput("  Found Action " + findThing + ", returning ID " + actionID[actionPos]);
            debugLong();
            return actionID[actionPos];
        } else {
            debugOutput("  Action " + findThing + " is not in the Library.");
            debugLong();
            return "";
        }
    }

    public String findName(String findThing) {
        // Given the Name or ID for the Action, it will return the Name from the Library


        debugLong();
        debugOutput("ActionLibrary findName");

        int actionPos = this.isInLibrary(findThing);

        if (actionPos < 999) {
            debugOutput("  Found Action " + findThing + ", returning Name " + actionName[actionPos]);
            debugLong();
            return actionName[actionPos];
        } else {
            debugOutput("  Action " + findThing + " is not in the Library.");
            debugLong();
            return "";
        }
    }

    public String findType(String findThing) {
        // Given the Name or ID for the Action, it will return the Type from the Library

        debugLong();
        debugOutput("ActionLibrary findType");

        int actionPos = this.isInLibrary(findThing);

        if (actionPos < 999) {
            debugOutput("  Found Action " + findThing + ", returning Type " + actionType[actionPos]);
            debugLong();
            return actionType[actionPos];
        } else {
            debugOutput("  Action " + findThing + " is not in the Library.");
            debugLong();
            return "";
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
