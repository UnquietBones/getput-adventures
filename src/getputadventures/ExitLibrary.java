package getputadventures;

import java.io.File;
import java.util.Scanner;

public class ExitLibrary {

    // Available exits are currently linked to a Room. There is no need to track changes as the game
    // progresses since the RoomMap will do so.

    int libraryCount = 11;

    private Boolean showDebug;
    private String[] exitID = new String[libraryCount];
    private String[] exitName = new String[libraryCount];
    private String[] exitDescription = new String[libraryCount];
    private String[] exitDestination = new String[libraryCount];

    public ExitLibrary(Boolean mainDebug) {
        showDebug = mainDebug;
    }

    public void loadLibrary() throws Exception {

        if (showDebug) {
            System.out.println("-----------------------------------");
            System.out.println("Starting ExitLibrary loadLibrary...");
        }

        // This program will read in the Library from the text file

        int exitCounter = 0;

        // pass the path to the file as a parameter
        String pathname = "C:\\getputadventures\\exits.txt";
        File file = new File(pathname);
        Scanner scan = new Scanner(file);
        scan.useDelimiter("/|\\r\\n");

        if (showDebug) {
            System.out.println("Loading Exit Library from " + pathname);
        }

        // throw out the first line, it's the field descriptions for the end user
        scan.nextLine();

        while (scan.hasNext()) {

            exitID[exitCounter] = scan.next();
            if (showDebug) {
                System.out.println("Checking line " + exitCounter);
            }

            if (!exitID[exitCounter].isEmpty()) {
                exitName[exitCounter] = scan.next();
                exitDescription[exitCounter] = scan.next();
                exitDestination[exitCounter] = scan.next();

                if (showDebug) {
                    System.out.println("Exit ID          " + exitID[exitCounter]);
                    System.out.println("Exit Name        " + exitName[exitCounter]);
                    System.out.println("Exit Description " + exitDescription[exitCounter]);
                    System.out.println("Exit Destination " + exitDestination[exitCounter]);
                    System.out.println("-----------------");
                }
            } else {
                if (showDebug) {
                    System.out.println("All done!");
                }
            }
            exitCounter += 1;
        }
        scan.close();

        if (showDebug) {
            System.out.println("-----------------------------------");
        }
    }

    public ListThing readItem(String findThing) {

        // This will try and find (and return) the item either by Name or by ID from the Library.

        if (showDebug) {
            System.out.println("--------------------------------");
            System.out.println("Starting ExitLibrary readThing...");
            System.out.println("Looking for Exit " + findThing);
        }

        ListThing thisExit = null;
        String thisType = "Exit";

        String thisID;
        String thisName;
        String thisDescription;
        String thisDestination;

        String thisActionID = "";
        String thisCommand = "";

        for (int exitPos = 0; exitPos < exitID.length; exitPos++) {
            if (exitID[exitPos].equalsIgnoreCase(findThing) || exitName[exitPos].equalsIgnoreCase(findThing)) {

                thisID = findThing;
                thisName = exitName[exitPos];
                thisDescription = exitDescription[exitPos];
                thisDestination = exitDestination[exitPos];

                if (showDebug) {
                    System.out.println("Creating exit...");
                    System.out.println("Exit ID           " + thisID);
                    System.out.println("Exit Name         " + thisName);
                    System.out.println("Exit Description  " + thisDescription);
                    System.out.println("Exit Destination  " + thisDestination);
                    System.out.println("--------------------------------");
                }

                thisExit = new ListThing(thisType, thisID, thisName, thisDescription, thisActionID, thisDestination, thisCommand, showDebug);
                return thisExit;
            }
        }

        // Still working under the 'any errors cause program to halt' idea

        if (showDebug) {
            System.out.println("ERMAHGERD! Unable to find Exit " + findThing + ".");
            System.out.println("--------------------------------");
        }
        return thisExit;
    }
}
