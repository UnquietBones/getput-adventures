package getput.adventures;

import java.io.File;
import java.util.Scanner;

public class ExitLibrary {

    // Currently not intending to write a modified Library for the save game, but we'll see...
    // Should only need to load the Library and then create individual items as needed.

    int libraryCount = 1;

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
            System.out.println("Starting ExitLibrary loadLibrary...");
        }

        // This program will read in the Library from the text file

        int exitCounter = 0;

        // pass the path to the file as a parameter
        File file = new File("C:\\getputadventures\\exits.txt");
        Scanner scan = new Scanner(file);
        scan.useDelimiter("/|\\r\\n");

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
                    System.out.println("--------------------");
                }
            } else {
                if (showDebug) {
                    System.out.println("All done!");
                }
            }
            exitCounter += 1;
        }
        scan.close();
    }

    public ListThing readItem(String findThisID) {

        if (showDebug) {
            System.out.println("Starting ExitLibrary readItem...");
        }

        // This will find the item in the Library and then create a new exit with
        // that information.

        ListThing thisExit = null;
        String thisType = "Exit";

        String thisID;
        String thisName;
        String thisDescription;
        String thisDestination;

        String thisActionID = "";
        String thisCommand = "";
        
        for (int exitPos = 0; exitPos < exitID.length; exitPos++) {
            if (exitID[exitPos].equals(findThisID)) {

                thisID = findThisID;
                thisName = exitName[exitPos];
                thisDescription = exitDescription[exitPos];
                thisDestination = exitDestination[exitPos];

                if (showDebug) {
                    System.out.println("Creating exit...");
                    System.out.println("Exit ID           " + thisID);
                    System.out.println("Exit Name         " + thisName);
                    System.out.println("Exit Description  " + thisDescription);
                    System.out.println("Exit Destination  " + thisDestination);
                }

                thisExit = new ListThing(thisType, thisID, thisName, thisDescription, thisActionID, thisDestination, thisCommand, showDebug);
                return thisExit;
            }
        }

        // Still working under the 'any errors cause program to halt' idea

        System.out.println("ERMAHGERD! Unable to find Inventory Item " + findThisID + ", aborting game.");
        return thisExit;
    }
}
