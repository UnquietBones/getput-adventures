package getput.adventures;

import java.io.File;
import java.util.Scanner;

public class RoomMap {

    private boolean showDebug;
    private String[] roomIDs = new String[2];
    private String[] roomNames = new String[2];
    private String[] roomDescriptions = new String[2];
    private String[][] roomItems = new String[2][6];
    private String[][] roomExits = new String[2][6];
    private String[][] roomActions = new String[2][6];

    String currentRoomID;

    public RoomMap(boolean mainDebug) {
        showDebug = mainDebug;
        currentRoomID = "HOR1";
    }

    public void loadMap() throws Exception {

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
                roomItems[roomCounter] = allItems.split(",");
                itemCount = roomItems[roomCounter].length;
                if (showDebug) {
                    System.out.println(itemCount + " items found.");
                }

                allExits = scan.next();
                if (showDebug) {
                    System.out.println(allExits);
                }
                roomExits[roomCounter] = allExits.split(",");
                exitCount = roomExits[roomCounter].length;
                if (showDebug) {
                    System.out.println(exitCount + " Exits found.");
                }

                allActions = scan.next();
                if (showDebug) {
                    System.out.println(allActions);
                }
                roomActions[roomCounter] = allActions.split(",");
                actionCount = roomActions[roomCounter].length;
                if (showDebug) {
                    System.out.println(actionCount + " Actions found.");
                }

                if (showDebug) {
                    System.out.println("Room ID          " + roomIDs[roomCounter]);
                    System.out.println("Room name        " + roomNames[roomCounter]);
                    System.out.println("Room Description " + roomDescriptions[roomCounter]);
                    System.out.println("Items            " + allItems);
                    System.out.println("Exits            " + allExits);
                    System.out.println("Actions          " + allActions);
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

        // Silly graphics just for fun!

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

    public Room readRoom(String findThisID, boolean exitGame) {

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
                thisRoom = new Room(findThisID, thisRoomName, thisRoomDescription, thisRoomItems, thisRoomExits, thisRoomActions, showDebug, exitGame);
                return thisRoom;
            }
        }
        System.out.println("ERMAHGERD! Unable to find room ID " + findThisID + ", aborting game.");
        exitGame = true;
        return thisRoom;
    }

    public void updateRoom(Room changedRoom, boolean exitGame) {
        // Setting it up so everything can change except the room ID
        // Returns 1 if it updates the room, 0 if it can't find the room

        for (int roomPos = 0; roomPos < roomIDs.length; roomPos++) {
            if (roomIDs[roomPos].equals(changedRoom.ID)) {
                roomNames[roomPos] = changedRoom.name;
                roomDescriptions[roomPos] = changedRoom.description;
                roomItems[roomPos] = changedRoom.items.itemsList;
                roomExits[roomPos] = changedRoom.exits.itemsList;
                roomActions[roomPos] = changedRoom.actions.itemsList;
            }
        }
        System.out.println("ERMAHGERD! Unable to update room " + changedRoom.name + ", aborting game.");
        exitGame = true;
    }
}
