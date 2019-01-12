package getput.adventures;

import java.io.File;
import java.util.Scanner;

public class RoomMap {

    int maxRooms = 2;
    int maxItems = 6;

    private boolean showDebug;
    private ItemLibrary gameItems;
    private ExitLibrary gameExits;
    private ActionLibrary gameActions;
    private ListOfThings playerInventory;

    public String[] roomIDs = new String[2];
    public String[] roomNames = new String[2];
    public String[] roomDescriptions = new String[2];
    public String[][] roomItems = new String[maxRooms][maxItems];
    public String[][] roomExits = new String[maxRooms][maxItems];
    public String[][] roomActions = new String[maxRooms][maxItems];


    String currentRoomID;

    public RoomMap(ItemLibrary maingameItems, ExitLibrary maingameExits, ActionLibrary maingameActions, ListOfThings mainplayerInventory, boolean mainDebug) {
        showDebug = mainDebug;
        currentRoomID = "HOR1";

        gameItems = maingameItems;
        gameExits = maingameExits;
        gameActions = maingameActions;
        playerInventory = mainplayerInventory;
    }

    public void loadMap() throws Exception {

        if (showDebug) {
            System.out.println("Starting RoomMap loadMap...");
        }

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
                if (!allItems.equals("None")) {
                    roomItems[roomCounter] = allItems.split(",");
                    itemCount = roomItems[roomCounter].length;
                    if (showDebug) {
                        System.out.println(itemCount + " items found.");
                    }
                }

                allExits = scan.next();
                if (!allExits.equals("None")) {
                    if (showDebug) {
                        System.out.println(allExits);
                    }
                    roomExits[roomCounter] = allExits.split(",");
                    exitCount = roomExits[roomCounter].length;
                    if (showDebug) {
                        System.out.println(exitCount + " Exits found.");
                    }
                }

                allActions = scan.next();
                if (!allActions.equals("None")) {
                    if (showDebug) {
                        System.out.println(allActions);
                    }
                    roomActions[roomCounter] = allActions.split(",");
                    actionCount = roomActions[roomCounter].length;
                    if (showDebug) {
                        System.out.println(actionCount + " Actions found.");
                    }
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

    public Room readRoom(String findThisID) {

        if (showDebug) {
            System.out.println("Starting RoomMap readRoom...");
        }

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
                thisRoom = new Room(findThisID, thisRoomName, thisRoomDescription, thisRoomItems, thisRoomExits, thisRoomActions, gameItems, gameExits, gameActions, playerInventory, this, showDebug);
                return thisRoom;
            }
        }
        System.out.println("ERMAHGERD! Unable to find room ID " + findThisID + ", aborting game.");
        return thisRoom;
    }

    public void updateRoom(Room changedRoom) {
        // Setting it up so everything can change except the room ID
        // Returns 1 if it updates the room, 0 if it can't find the room

        if (showDebug) {
            System.out.println("Starting RoomMap updateRoom...");
        }

        String oldValue;
        String newValue;

        for (int roomPos = 0; roomPos < roomIDs.length; roomPos++) {
            if (roomIDs[roomPos].equals(changedRoom.ID)) {
                roomNames[roomPos] = changedRoom.name;
                roomDescriptions[roomPos] = changedRoom.description;

                // Time to translate the ListOfThings back into Strings

                for (int itemPos = 0; itemPos < 6; itemPos++) {

                    oldValue = roomItems[roomPos][itemPos];
                    newValue = changedRoom.items.itemsList[itemPos].id;

                    if (showDebug) {
                        System.out.println("Checking [" + itemPos + "] of Old " + oldValue + " and New " + newValue);
                    }

                    if (newValue != null && !newValue.isEmpty()) {
                        roomItems[roomPos][itemPos] = newValue;
                    }
                }


                for (int itemPos = 0; itemPos < 6; itemPos++) {

                    oldValue = roomExits[roomPos][itemPos];
                    newValue = changedRoom.exits.itemsList[itemPos].id;

                    if (showDebug) {
                        System.out.println("Checking [" + itemPos + "] of Old " + oldValue + " and New " + newValue);
                    }

                    if (newValue != null && !newValue.isEmpty()) {
                        roomExits[roomPos][itemPos] = newValue;
                    }
                }

                for (int itemPos = 0; itemPos < 6; itemPos++) {

                    oldValue = roomActions[roomPos][itemPos];
                    newValue = changedRoom.actions.itemsList[itemPos].id;

                    if (showDebug) {
                        System.out.println("Checking [" + itemPos + "] of Old " + oldValue + " and New " + newValue);
                    }

                    if (newValue != null && !newValue.isEmpty()) {
                        roomActions[roomPos][itemPos] = newValue;
                    }
                }
            }
        }
        System.out.println("ERMAHGERD! Unable to update room " + changedRoom.name + ", aborting game.");
    }
}
