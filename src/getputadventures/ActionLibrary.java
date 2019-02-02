package getputadventures;

import java.io.File;
import java.util.Scanner;

public class ActionLibrary {

    // Changes made
    //  Had getID blank out the findThing if the lookup fails
    //  Added placeholders for new actions to doAction
    //  Turned the debug dash-breaks into statics so they are consistent
    //  Changed all println with variables to printf's.
    //  Rewrote getID to use isInLibrary
    //  Tried to remove as many global variables as possible

    // Available actions are currently linked to a Room. There is no need to track changes as the game
    // progresses since the RoomMap will do so.

    private static String LONGDASH = "--------------------------------";
    private static String SHORTDASH = "----------------";
    private static int LIBRARYCOUNT = 30;
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

        // This program will read in the Library from the text file

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ActionLibrary loadLibrary");
        }

        int actionPos = 0;

        // pass the path to the file as a parameter
        String pathname = "C:\\getputadventures\\actions.txt";
        File file = new File(pathname);
        Scanner scan = new Scanner(file);
        scan.useDelimiter("/|\\r\\n");

        if (showDebug) {
            System.out.printf("  Loading Action Library from %s %n", pathname);
        }

        // throw out the first line, it's just the field descriptions for the developers
        scan.nextLine();

        while (scan.hasNext()) {

            actionID[actionPos] = scan.next();
            if (showDebug) {
                System.out.printf("  Checking line %d %n", actionPos);
            }

            if (!(actionID[actionPos] == null)) {
                actionName[actionPos] = scan.next();
                actionDescriptions[actionPos] = scan.next();
                actionCommands[actionPos] = scan.next();
                actionType[actionPos] = scan.next();

                if (showDebug) {
                    System.out.printf("    Action ID          %s %n", actionID[actionPos]);
                    System.out.printf("    Action Name        %s %n", actionName[actionPos]);
                    System.out.printf("    Action Description %s %n", actionDescriptions[actionPos]);
                    System.out.printf("    Action Command     %s %n", actionCommands[actionPos]);
                    System.out.printf("    Action Type        %s %n", actionType[actionPos]);
                    System.out.println(SHORTDASH);
                }
            } else {
                if (showDebug) {
                    System.out.println("All done!");
                }
            }
            actionPos += 1;
        }
        scan.close();

        if (showDebug) {
            System.out.println(LONGDASH);
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
        String thisActionType;

        // These are unused fields for Actions
        String thisActionID = "";

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ActionLibrary readThing");
            System.out.println("  Looking for Action " + findThing);
        }

        int inventoryItemPos = this.isInLibrary(findThing);

        if (inventoryItemPos < 999) {

            thisID = findThing;
            thisName = actionName[inventoryItemPos];
            thisDescription = actionDescriptions[inventoryItemPos];
            thisCommand = actionCommands[inventoryItemPos];
            thisActionType = actionType[inventoryItemPos];

            if (showDebug) {
                System.out.println("  Creating item...");
                System.out.println("  Action ID          " + thisID);
                System.out.println("  Action Name        " + thisName);
                System.out.println("  Action Description " + thisDescription);
                System.out.println("  Action Command     " + thisCommand);
                System.out.println(LONGDASH);
            }
            thisItem = new ListThing(thisType, thisID, thisName, thisDescription, thisActionID, thisCommand, thisActionType, "", showDebug);
            return thisItem;
        }

        if (showDebug) {
            System.out.println(LONGDASH);
        }
        return thisItem;
    }

    public int isInLibrary(String findThing) {
        // This will find the location the Library of the Item or it will return 999
        // Checks for matches on ID or Name for simplicity's sake

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ActionLibrary isInLibrary");
            System.out.printf("  Trying to find %s %n", findThing);
        }

        for (int actionPos = 0; actionPos < actionID.length; actionPos++) {

            if (showDebug) {
                System.out.printf("  Checking [%d] %s, %s %n", actionPos, actionID[actionPos], actionName[actionPos]);
            }

            if (actionID[actionPos].equalsIgnoreCase(findThing) || actionName[actionPos].equalsIgnoreCase(findThing)) {

                if (showDebug) {
                    System.out.printf("  Action %s was in the Item Library at [%d]. %n", findThing, actionPos);
                    System.out.println(LONGDASH);
                }
                return actionPos;
            }
        }

        if (showDebug) {
            System.out.printf("  Action %s was not in the Action Library. %n", findThing);
            System.out.println(LONGDASH);
        }
        return 999;
    }

    public String getID(String findThing) {

        // Given the Name or ID for the Action, it will return the ID from the Library

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("ActionLibrary getID");
        }

        int actionPos = this.isInLibrary(findThing);

        if (actionPos < 999) {
            return actionID[actionPos];
        } else {
            findThing = "";
            return findThing;
        }
    }
}
