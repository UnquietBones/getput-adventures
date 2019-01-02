package getput.adventures;

import java.io.File;
import java.util.Scanner;

public class RoomMap {

    Boolean showDebug;
    private String[] roomIDs = {"", "", "", "", ""};
    private String[] roomNames = {"", "", "", "", ""};
    private String[] roomDescriptions = {"", "", "", "", ""};
    private String[][] roomItems = {{""}, {""}};
    private String[][] roomExits = {{""}, {""}};
    private String[][] roomActions = {{""}, {""}};

    public RoomMap(Boolean mainDebug) {
        showDebug = mainDebug;
    }

    public void loadFile(Boolean mainDebug) throws Exception {

        showDebug = mainDebug;
        ListOfThings listOfItems = new ListOfThings();

        String allItems;
        String allExits;
        String allActions;

        int roomCounter = 0;
        int itemCount = 0;
        int exitCount = 0;
        int actionCount = 0;

        // pass the path to the file as a parameter
        File file = new File("C:\\getputadventures\\rooms.txt");
        Scanner scan = new Scanner(file);
        scan.useDelimiter("/|\\r\\n");

        // throw out the first line, it's the header
        scan.nextLine();

        while (scan.hasNext()) {

            roomIDs[roomCounter] = scan.next();
            if (showDebug) {
                System.out.println("Checking line " + roomCounter);
            }

            if (!roomIDs[roomCounter].isEmpty()) {
                roomNames[roomCounter] = scan.next();
                roomDescriptions[roomCounter] = scan.next();

                // The next sections have comma delimitated subvalues

                allItems = scan.next();
                if (showDebug) {
                    System.out.println(allItems);
                }

                // If there is more than one, split it out
                if (allItems.contains(",")) {
                    if (showDebug) {
                        System.out.println("Splitting out items...");
                    }
                    roomItems[roomCounter] = allItems.split(",");
                    itemCount = roomItems[roomCounter].length;
                    if (showDebug) {
                        System.out.println(itemCount + " items found.");
                    }
                    itemCount -= 1;
                } else {
                    roomItems[roomCounter][0] = allItems;
                }

                allExits = scan.next();
                if (showDebug) {
                    System.out.println(allExits);
                }

                // If there is more than one, split it out
                if (allExits.contains(",")) {
                    if (showDebug) {
                        System.out.println("Splitting out Exits...");
                    }
                    roomExits[roomCounter] = allExits.split(",");
                    exitCount = roomExits[roomCounter].length;
                    if (showDebug) {
                        System.out.println(exitCount + " Exits found.");
                    }
                    exitCount -= 1;
                } else {
                    roomExits[roomCounter][0] = allExits;
                }

                // The next sections have comma delimitated subvalues
                allActions = scan.next();
                if (showDebug) {
                    System.out.println(allActions);
                }

                // If there is more than one, split it out
                if (allActions.contains(",")) {
                    if (showDebug) {
                        System.out.println("Splitting out Actions...");
                    }
                    roomActions[roomCounter] = allActions.split(",");
                    actionCount = roomActions[roomCounter].length;
                    if (showDebug) {
                        System.out.println(actionCount + " Actions found.");
                    }
                    actionCount -= 1;
                } else {
                    roomActions[roomCounter][0] = allActions;
                }

                if (showDebug) {
                    System.out.println("Room ID          " + roomIDs[roomCounter]);
                    System.out.println("Room name        " + roomNames[roomCounter]);
                    System.out.println("Room Description " + roomDescriptions[roomCounter]);
                    listOfItems.printListOfThings(roomItems[roomCounter], "Items: ", showDebug);
                    listOfItems.printListOfThings(roomExits[roomCounter], "Exits: ", showDebug);
                    listOfItems.printListOfThings(roomActions[roomCounter], "Actions: ", showDebug);
                    System.out.println("--------------------");
                }
            } else {
                if (showDebug) {
                    System.out.println("All done!");
                }
            }
            roomCounter += 1;
        }
        scan.close();

        System.out.println();
        System.out.println("=========================================================");
        System.out.println("===================|||||||||||===========================");
        System.out.println("===================|||||||||||===========================");
        System.out.println("===================|||||||||||===========================");
        System.out.println("===================|||||||||||===========================");
        System.out.println("=================|||||||||||||||=========================");
        System.out.println("==================|||||||||||||==========================");
        System.out.println("===================|||||||||||===========================");
        System.out.println("====================|||||||||============================");
        System.out.println("=====================|||||||=============================");
        System.out.println("======================|||||==============================");
        System.out.println("=======================|||===============================");
        System.out.println("========================|================================");
        System.out.println();
    }

    public Room findRoom(String findThisID) {

        Room thisRoom = null;
        String thisRoomName;
        String thisRoomDescription;
        String[] thisRoomItems;
        String[] thisRoomExits;
        String[] thisRoomActions;

        for (int roomPos = 0; roomPos < roomIDs.length; roomPos++) {
            if (roomIDs[roomPos].equals(findThisID)) {

                thisRoomName = roomNames[roomPos];
                thisRoomDescription = roomDescriptions[roomPos];
                thisRoomItems = roomItems[roomPos];
                thisRoomExits = roomExits[roomPos];
                thisRoomActions = roomActions[roomPos];
                thisRoom = new Room(thisRoomName, thisRoomDescription, thisRoomItems, thisRoomExits, thisRoomActions, showDebug);
            }
        }
        return thisRoom;
    }
}
