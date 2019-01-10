package getput.adventures;

import java.io.File;
import java.util.Scanner;

public class RoomExitLibrary {

    // Currently not intending to write a modified Library for the save game, but we'll see...
    // Should only need to load the Library and then create individual items as needed.

    int libraryCount = 1;

    private Boolean showDebug;
    private String[] exitID = new String[libraryCount];
    private String[] exitName = new String[libraryCount];
    private String[] exitDescription = new String[libraryCount];
    private String[] exitDestination = new String[libraryCount];
    private int[] exitVisible = new int[libraryCount];

    public RoomExitLibrary(Boolean mainDebug) {
        showDebug = mainDebug;
    }

    public void loadLibrary() throws Exception {

        // This program will read in the Library from the text file

        int exitCounter = 0;
        String visibleToggle;
        int intVisibleToggle;

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
                visibleToggle = scan.next();
                exitVisible[exitCounter] = Integer.valueOf(visibleToggle);

                if (showDebug) {
                    System.out.println("Exit ID          " + exitID[exitCounter]);
                    System.out.println("Exit Name        " + exitName[exitCounter]);
                    System.out.println("Exit Description " + exitDescription[exitCounter]);
                    System.out.println("Exit Destination " + exitDestination[exitCounter]);
                    System.out.println("Exit Visible     " + exitVisible[exitCounter]);
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

    public RoomExit readItem(String findThisID, boolean exitGame) {

        // This will find the item in the Library and then create a new exit with
        // that information.

        RoomExit thisExit = null;
        String thisID;
        String thisName;
        String thisDescription;
        String thisDestination;
        int thisVisible;
        
        for (int exitPos = 0; exitPos < exitID.length; exitPos++) {
            if (exitID[exitPos].equals(findThisID)) {

                thisID = findThisID;
                thisName = exitName[exitPos];
                thisDescription = exitDescription[exitPos];
                thisDestination = exitDestination[exitPos];
                thisVisible = exitVisible[exitPos];

                if (showDebug) {
                    System.out.println("Creating exit...");
                    System.out.println("Exit ID           " + thisID);
                    System.out.println("Exit Name         " + thisName);
                    System.out.println("Exit Description  " + thisDescription);
                    System.out.println("Exit Destination  " + thisDestination);
                    System.out.println("Exit Visible      " + thisVisible);
                }

                thisExit = new RoomExit(thisID, thisName, thisDescription, thisDestination, thisVisible, showDebug);
                return thisExit;
            }
        }

        // Still working under the 'any errors cause program to halt' idea

        System.out.println("ERMAHGERD! Unable to find Inventory Item " + findThisID + ", aborting game.");
        exitGame = true;
        return thisExit;
    }
}
