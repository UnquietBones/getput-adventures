package getput.adventures;

import jdk.swing.interop.SwingInterOpUtils;

import java.io.File;
import java.sql.SQLOutput;
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

                if (showDebug) {
                    System.out.println("Reading in room name.");
                }
                roomNames[roomCounter] = scan.next();


                if (showDebug) {
                    System.out.println("Reading in room description.");
                }
                roomDescriptions[roomCounter] = scan.next();

                // The next sections have comma delimitated subvalues

                // -------------------------------------------------------------------------------------
                if (showDebug) {
                    System.out.println("--------------");
                    System.out.println("Reading in room items.");
                }
                allItems = scan.next();
                if (showDebug) {
                    System.out.println(allItems);
                }

                // First clear out all the values in the room's item list
                for (int itemPos = 0; itemPos < maxItems; itemPos++) {
                    roomItems[roomCounter][itemPos] = "";
                }

                // Then copy in the items (if there are any)

                if (!allItems.equals("None")) {
                    String[] tempItems = allItems.split(",");
                    itemCount = tempItems.length;

                    if (showDebug) {
                        System.out.println(itemCount + " items found.");
                    }

                    for (int newItemPos = 0; newItemPos < itemCount; newItemPos++) {
                        roomItems[roomCounter][newItemPos] = tempItems[newItemPos];
                    }
                } else {
                    if (showDebug) {
                        System.out.println("No Items found.");
                    }
                }

                // -------------------------------------------------------------------------------------
                if (showDebug) {
                    System.out.println("-----------");
                    System.out.println("Reading in room exits.");
                }
                allExits = scan.next();
                if (showDebug) {
                    System.out.println(allExits);
                }

                // First clear out all the values in the room's exit list
                for (int exitPos = 0; exitPos < maxItems; exitPos++) {
                    roomExits[roomCounter][exitPos] = "";
                }

                // Then copy in the exits (if there are any)

                if (!allExits.equals("None")) {

                    String[] tempExits = allExits.split(",");
                    exitCount = tempExits.length;

                    if (showDebug) {
                        System.out.println(exitCount + " Exits found.");
                    }

                    for (int newExitPos = 0; newExitPos < exitCount; newExitPos++) {
                        roomExits[roomCounter][newExitPos] = tempExits[newExitPos];
                    }
                } else {
                    if (showDebug) {
                        System.out.println("No Exits found.");
                    }
                }

                // -------------------------------------------------------------------------------------
                if (showDebug) {
                    System.out.println("-----------");
                    System.out.println("Reading in room actions.");
                }

                allActions = scan.next();
                if (showDebug) {
                    System.out.println(allActions);
                }

                // First clear out all the values in the room's exit list
                for (int actionPos = 0; actionPos < maxItems; actionPos++) {
                    roomActions[roomCounter][actionPos] = "";
                }

                // Then copy in the actions (if there are any)

                if (!allActions.equals("None")) {

                    String[] tempActions = allActions.split(",");
                    actionCount = tempActions.length;

                    if (showDebug) {
                        System.out.println(actionCount + " Actions found.");
                    }

                    for (int newActionPos = 0; newActionPos < actionCount; newActionPos++) {
                        roomActions[roomCounter][newActionPos] = tempActions[newActionPos];
                    }
                } else {
                    if (showDebug) {
                        System.out.println("No Actions found.");
                    }
                }

                // -------------------------------------------------------------------------------------

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
    }

    public Room readRoom(String findThisID) {

        if (showDebug) {
            System.out.println("----------------------------");
            System.out.println("Starting RoomMap readRoom...");
        }

        Room thisRoom = null;
        String thisRoomName;
        String thisRoomDescription;
        String[] thisRoomItems;
        String[] thisRoomExits;
        String[] thisRoomActions;

        for (int roomPos = 0; roomPos < roomIDs.length; roomPos++) {
            if (roomIDs[roomPos].equals(findThisID) || roomNames[roomPos].equals(findThisID)) {

                thisRoomName = roomNames[roomPos];
                thisRoomDescription = roomDescriptions[roomPos];
                thisRoomItems = roomItems[roomPos];
                thisRoomExits = roomExits[roomPos];
                thisRoomActions = roomActions[roomPos];
                thisRoom = new Room(findThisID, thisRoomName, thisRoomDescription, thisRoomItems, thisRoomExits, thisRoomActions, gameItems, gameExits, gameActions, playerInventory, this, showDebug);
                return thisRoom;
            }
        }

        if (showDebug) {
            System.out.println("ERMAHGERD! Unable to find room ID " + findThisID + ".");
            System.out.println("----------------------------");
        }
        return thisRoom;
    }

    public void updateRoom(Room changedRoom) {
        // Setting it up so everything can change except the room ID

        if (showDebug) {
            System.out.println("------------------------------");
            System.out.println("Starting RoomMap updateRoom...");
            System.out.println("Trying to update room " + changedRoom.ID + " " + changedRoom.name);
        }

        String oldValue;
        String newValue;

        for (int roomPos = 0; roomPos < roomIDs.length; roomPos++) {
            if (roomIDs[roomPos].equals(changedRoom.ID)) {

                if (showDebug) {
                    System.out.println("Updating room name from " + roomNames[roomPos] + " to " + changedRoom.name);
                }
                roomNames[roomPos] = changedRoom.name;

                if (showDebug) {
                    System.out.println("Updating room description from " + roomDescriptions[roomPos] + " to " + changedRoom.description);
                }
                roomDescriptions[roomPos] = changedRoom.description;

                // Time to translate the ListOfThings back into Strings

                if (showDebug) {
                    System.out.println("Updating room Items");
                    System.out.println(roomItems[roomPos]);
                    System.out.println(changedRoom.items);
                    System.out.println("Expecting " + maxItems + " in Old Room, found " + roomItems[roomPos].length);
                    System.out.println("Expecting " + maxItems + " in New Room, found " + changedRoom.items.itemsList.length);
                }

                for (int itemPos = 0; itemPos < maxItems; itemPos++) {

                    if (showDebug) {
                        System.out.println("Checking [" + itemPos + "]");
                    }

                    if (roomItems[roomPos][itemPos] == null) {

                        if (showDebug) {
                            System.out.println("The old item was empty.");
                        }
                        oldValue = "";
                    } else {
                        oldValue = roomItems[roomPos][itemPos];
                        if (showDebug) {
                            System.out.println("The old value was " + oldValue);
                        }
                    }

                    if (changedRoom.items.itemsList[itemPos] == null) {
                        if (showDebug) {
                            System.out.println("The new item was empty.");
                        }
                        newValue = "";
                    } else {
                        newValue = changedRoom.items.itemsList[itemPos].id;
                        if (showDebug) {
                            System.out.println("The new value was " + newValue);
                        }
                    }


                    if (showDebug) {
                        System.out.println("Checking [" + itemPos + "] of Old " + oldValue + " and New " + newValue);
                    }
                    roomItems[roomPos][itemPos] = newValue;
                }

                if (showDebug) {
                    System.out.println("Updating room Exits");
                    System.out.println(roomExits[roomPos]);
                    System.out.println(changedRoom.exits);
                }

                for (int itemPos = 0; itemPos < maxItems; itemPos++) {

                    if (showDebug) {
                        System.out.println("Checking [" + itemPos + "]");
                    }

                    oldValue = roomExits[roomPos][itemPos];
                    newValue = changedRoom.exits.itemsList[itemPos].id;

                    if (showDebug) {
                        System.out.println("Checking [" + itemPos + "] of Old " + oldValue + " and New " + newValue);
                    }

                    roomExits[roomPos][itemPos] = newValue;
                }

                if (showDebug) {
                    System.out.println("Updating room Actions");
                }

                for (int itemPos = 0; itemPos < maxItems; itemPos++) {

                    oldValue = roomActions[roomPos][itemPos];
                    newValue = changedRoom.actions.itemsList[itemPos].id;

                    if (showDebug) {
                        System.out.println("Checking [" + itemPos + "] of Old " + oldValue + " and New " + newValue);
                    }

                    roomActions[roomPos][itemPos] = newValue;
                }

                if (showDebug) {
                    System.out.println("Completed room update.");
                }
                return;
            }
        }
        System.out.println("ERMAHGERD! Unable to update room " + changedRoom.name + ".");
    }
}
