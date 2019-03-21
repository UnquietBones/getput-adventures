package getputadventures;

import java.io.File;
import java.util.Scanner;

public class ActionLibrary {

    /*
     * Available actions are linked to a Room or an Item. There is no need to track this Library as the game
     * progresses since the values will not change.
     */

    private static int LIBRARYCOUNT = 31;
    private boolean showDebug;
    private String[] actionID = new String[LIBRARYCOUNT];
    private String[] actionName = new String[LIBRARYCOUNT];
    private String[] actionDescriptions = new String[LIBRARYCOUNT];
    private String[] actionCommands = new String[LIBRARYCOUNT];
    private String[] actionType = new String[LIBRARYCOUNT];
    private DisplayMsgs debugMessage;

    public ActionLibrary(boolean mainDebug) {
        showDebug = mainDebug;
        debugMessage = new DisplayMsgs(showDebug);

        System.out.println();
    }

    public void loadActionLibrary() throws Exception {

        /*
         * This method reads in the Library from the text file. Right now it's expecting a specific number
         * of Actions in the file and a specific path. Someday I need to change it so that both don't have
         * to be hardcoded.
         */

        debugMessage.debugLong();
        debugMessage.debugOutput("ActionLibrary loadLibrary");

        int actionPos = 0;

        //String pathname = "C:\\getputadventures\\actions.txt";
        String pathname = "C:\\getputadventures\\testactions.txt";
        File file = new File(pathname);
        Scanner scan = new Scanner(file);
        scan.useDelimiter("/|\\r\\n");

        debugMessage.debugOutput("  Loading Action Library from " + pathname);
        debugMessage.debugShort();

        // We can throw out the first line, it's just the field descriptions for the game designers.
        scan.nextLine();

        while (scan.hasNext()) {
            actionID[actionPos] = scan.next();
            debugMessage.debugOutput("  Checking line " + actionPos);

            if (!(actionID[actionPos] == null)) {
                actionName[actionPos] = scan.next();
                actionDescriptions[actionPos] = scan.next();
                actionCommands[actionPos] = scan.next();
                actionType[actionPos] = scan.next();

                debugMessage.debugOutput("    Action ID          " + actionID[actionPos]);
                debugMessage.debugOutput("    Action Name        " + actionName[actionPos]);
                debugMessage.debugOutput("    Action Description " + actionDescriptions[actionPos]);
                debugMessage.debugOutput("    Action Command     " + actionCommands[actionPos]);
                debugMessage.debugOutput("    Action Type        " + actionType[actionPos]);
                debugMessage.debugShort();
            } else {
                debugMessage.debugOutput("All done!");
            }
            actionPos += 1;
        }
        scan.close();
        debugMessage.debugLong();
    }

    public ListThing readAction(String findThing) {
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

        debugMessage.debugLong();
        debugMessage.debugOutput("ActionLibrary readThing");
        debugMessage.debugOutput("  Looking for Action " + findThing);

        int inventoryItemPos = this.isInLibrary(findThing);

        if (inventoryItemPos < 999) {

            thisID = findThing;
            thisName = actionName[inventoryItemPos];
            thisDescription = actionDescriptions[inventoryItemPos];
            thisCommand = actionCommands[inventoryItemPos];
            thisActionType = actionType[inventoryItemPos];

            debugMessage.debugOutput("  Creating item...");
            debugMessage.debugOutput("  Action ID          " + thisID);
            debugMessage.debugOutput("  Action Name        " + thisName);
            debugMessage.debugOutput("  Action Description " + thisDescription);
            debugMessage.debugOutput("  Action Command     " + thisCommand);
            debugMessage.debugLong();

            thisItem = new ListThing(thisType, thisID, thisName, thisDescription, thisActionID, thisCommand,
                    thisActionType, thisPickup, showDebug);

            return thisItem;
        }

        debugMessage.debugLong();
        return thisItem;
    }

    public int isInLibrary(String findThing) {
        /*
         * This returns the location the Library of the Item or 999. It checks for matches on ID or Name for
         * simplicity's sake.
         */

        debugMessage.debugLong();
        debugMessage.debugOutput("ActionLibrary isInLibrary");

        for (int actionPos = 0; actionPos < actionID.length; actionPos++) {
            //debugMessage.debugOutput("    Checking [" + actionPos + "] " + actionID[actionPos] + " " + actionName[actionPos]);

            if (actionID[actionPos].equalsIgnoreCase(findThing) || actionName[actionPos].equalsIgnoreCase(findThing)) {
                debugMessage.debugOutput("  Action " + findThing + " was in the Library at [" + actionPos + "] " + actionName[actionPos]);
                debugMessage.debugLong();
                return actionPos;
            }
        }

        debugMessage.debugOutput("  Action " + findThing + " is not in the Library.");
        debugMessage.debugLong();
        return 999;
    }

    public String findID(String findThing) {
        // Given the Name or ID for the Action, it will return the ID from the Library

        int actionPos = this.isInLibrary(findThing);

        if (actionPos < 999) {
            debugMessage.debugOutput("  Found Action " + findThing + ", returning ID " + actionID[actionPos]);
            return actionID[actionPos];
        } else {
            debugMessage.debugOutput("  Action " + findThing + " is not in the Library.");
            return "";
        }
    }

    public String findName(String findThing) {

        int actionPos = this.isInLibrary(findThing);

        if (actionPos < 999) {
            debugMessage.debugOutput("  Found Action " + findThing + ", returning Name " + actionName[actionPos]);
            return actionName[actionPos];
        } else {
            debugMessage.debugOutput("  Action " + findThing + " is not in the Library.");
            return "";
        }
    }

    public String findType(String findThing) {

        int actionPos = this.isInLibrary(findThing);

        if (actionPos < 999) {
            debugMessage.debugOutput("  Found Action " + findThing + ", returning Type " + actionType[actionPos]);
            return actionType[actionPos];
        } else {
            debugMessage.debugOutput("  Action " + findThing + " is not in the Library.");
            return "";
        }
    }

    public String findDescription(String findThing) {

        int actionPos = this.isInLibrary(findThing);

        if (actionPos < 999) {
            debugMessage.debugOutput("  Found Action " + findThing + ", returning Description " + actionName[actionPos]);
            return actionDescriptions[actionPos];
        } else {
            debugMessage.debugOutput("  Action " + findThing + " is not in the Library.");
            return "";
        }
    }
}
