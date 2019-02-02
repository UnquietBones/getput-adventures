package getputadventures;

import java.io.File;
import java.util.Scanner;

public class RoomMap {

    private static String LONGDASH = "--------------------------------";
    private static String SHORTDASH = "----------------";
    private static int MAXROOMS = 30;
    private static int MAXITEMS = 6;
    public String[] roomIDs = new String[MAXROOMS];
    public String[] roomNames = new String[MAXROOMS];
    public String[] roomDescriptions = new String[MAXROOMS];
    public String[][] roomItems = new String[MAXROOMS][MAXITEMS];
    public String[][] roomActions = new String[MAXROOMS][MAXITEMS];
    String currentRoomID;
    private boolean showDebug;

    public RoomMap(boolean mainDebug) {
        showDebug = mainDebug;
        currentRoomID = "";
    }

    public void loadMap() throws Exception {

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("RoomMap loadMap");
        }

        String allItems;
        String allActions;

        int roomCounter = 0;
        int itemCount = 0;
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
                System.out.printf("Checking line %d %n", roomCounter);
            }

            if (!roomIDs[roomCounter].isEmpty()) {

                if (showDebug) {
                    System.out.println("  Reading in room name.");
                }
                roomNames[roomCounter] = scan.next();


                if (showDebug) {
                    System.out.println("  Reading in room description.");
                }
                roomDescriptions[roomCounter] = scan.next();

                // The next sections have comma delimited subvalues

                // -------------------------------------------------------------------------------------
                if (showDebug) {
                    System.out.println(SHORTDASH);
                    System.out.println("  Reading in room items.");
                }
                allItems = scan.next();
                if (showDebug) {
                    System.out.printf("    $s $n", allItems);
                }

                // First clear out all the values in the room's item list
                for (int itemPos = 0; itemPos < MAXITEMS; itemPos++) {
                    roomItems[roomCounter][itemPos] = "";
                }

                // Then copy in the items (if there are any)

                if (!allItems.equalsIgnoreCase("None")) {
                    String[] tempItems = allItems.split(",");
                    itemCount = tempItems.length;

                    if (showDebug) {
                        System.out.printf("    %d items found. %n", itemCount);
                    }

                    for (int newItemPos = 0; newItemPos < itemCount; newItemPos++) {
                        roomItems[roomCounter][newItemPos] = tempItems[newItemPos];
                    }
                } else {
                    if (showDebug) {
                        System.out.println("    No Items found.");
                    }
                }

                // -------------------------------------------------------------------------------------
                if (showDebug) {
                    System.out.println(SHORTDASH);
                    System.out.println("Reading in room Actions.");
                }

                allActions = scan.next();
                if (showDebug) {
                    System.out.printf("    %s %n", allActions);
                }

                // First clear out all the values in the room's actions list
                for (int actionPos = 0; actionPos < MAXITEMS; actionPos++) {
                    roomActions[roomCounter][actionPos] = "";
                }

                // Then copy in the actions (if there are any)

                if (!allActions.equalsIgnoreCase("None")) {

                    String[] tempActions = allActions.split(",");
                    actionCount = tempActions.length;

                    if (showDebug) {
                        System.out.printf("    %d Actions found.", actionCount);
                    }

                    for (int newActionPos = 0; newActionPos < actionCount; newActionPos++) {
                        roomActions[roomCounter][newActionPos] = tempActions[newActionPos];
                    }
                } else {
                    if (showDebug) {
                        System.out.println("    No Actions found.");
                    }
                }

                // -------------------------------------------------------------------------------------

                if (showDebug) {
                    System.out.printf("  Room ID          %s %n", roomIDs[roomCounter]);
                    System.out.printf("  Room name        %s %n", roomNames[roomCounter]);
                    System.out.printf("  Room Description %s %n", roomDescriptions[roomCounter]);
                    System.out.printf("  Items            %s %n", allItems);
                    System.out.printf("  Actions          %s %n", allActions);
                    System.out.println(SHORTDASH);
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

    public Room readRoom(String findThisID, ItemLibrary gameItems, ActionLibrary gameActions, ListOfThings playerInventory) {

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("RoomMap readRoom");
        }

        Room thisRoom = null;
        String thisRoomName;
        String thisRoomDescription;
        String[] thisRoomItems;
        String[] thisRoomActions;

        for (int roomPos = 0; roomPos < roomIDs.length; roomPos++) {
            if (roomIDs[roomPos].equalsIgnoreCase(findThisID) || roomNames[roomPos].equalsIgnoreCase(findThisID)) {

                thisRoomName = roomNames[roomPos];
                thisRoomDescription = roomDescriptions[roomPos];
                thisRoomItems = roomItems[roomPos];
                thisRoomActions = roomActions[roomPos];
                thisRoom = new Room(findThisID, thisRoomName, thisRoomDescription, thisRoomItems, thisRoomActions, gameItems, gameActions, showDebug);
                return thisRoom;
            }
        }

        if (showDebug) {
            System.out.println("Unable to find room ID " + findThisID + ".");
            System.out.println(SHORTDASH);
        }
        return thisRoom;
    }

    public void updateRoom(Room changedRoom) {
        // Setting it up so everything can change except the room ID

        if (showDebug) {
            System.out.println(SHORTDASH);
            System.out.println("RoomMap updateRoom");
            System.out.printf("  Trying to update room %s  %s %n", changedRoom.ID, changedRoom.name);
        }

        String oldValue;
        String newValue;

        int roomPos = isInMap(changedRoom.ID);

        if (roomPos < 999) {

            if (showDebug) {
                System.out.printf("  Updating room name from %s to %s %n", roomNames[roomPos], changedRoom.name);
            }
            roomNames[roomPos] = changedRoom.name;

            if (showDebug) {
                System.out.printf("  Updating room description from %s to %s %n", roomDescriptions[roomPos], changedRoom.description);
            }
            roomDescriptions[roomPos] = changedRoom.description;

            // Time to translate the ListOfThings back into Strings

            if (showDebug) {
                System.out.println("  Updating room Items");
                System.out.printf("    %s %n", roomItems[roomPos]);
                System.out.printf("    %s %n", changedRoom.items);
                System.out.printf("    Expecting %d in Old Room, found %d %n", MAXITEMS, roomItems[roomPos].length);
                System.out.printf("    Expecting %d in New Room, found %d %n", MAXITEMS, changedRoom.items.itemsList.length);
            }

            for (int itemPos = 0; itemPos < MAXITEMS; itemPos++) {

                if (showDebug) {
                    System.out.printf("    Checking [%d] %n", itemPos);
                }

                if (roomItems[roomPos][itemPos] == null) {

                    if (showDebug) {
                        System.out.println("      The old item was empty.");
                    }
                    oldValue = "";
                } else {
                    oldValue = roomItems[roomPos][itemPos];
                    if (showDebug) {
                        System.out.printf("      The old value was %s %n", oldValue);
                    }
                }

                if (changedRoom.items.itemsList[itemPos] == null) {
                    if (showDebug) {
                        System.out.println("      The new item was empty.");
                    }
                    newValue = "";
                } else {
                    newValue = changedRoom.items.itemsList[itemPos].id;
                    if (showDebug) {
                        System.out.printf("      The new value was %s %n", newValue);
                    }
                }

                if (showDebug) {
                    System.out.printf("      Changing [%d] from Old %s to New %s %n", itemPos, oldValue, newValue);
                }
                roomItems[roomPos][itemPos] = newValue;
            }

            if (showDebug) {
                System.out.println("  Updating room Actions");
            }

            for (int itemPos = 0; itemPos < MAXITEMS; itemPos++) {

                oldValue = roomActions[roomPos][itemPos];
                newValue = changedRoom.actions.itemsList[itemPos].id;

                if (showDebug) {
                    System.out.printf("    Changing [%d] from Old %s to New %s %n", itemPos, oldValue, newValue);
                }
                roomActions[roomPos][itemPos] = newValue;
            }

            if (showDebug) {
                System.out.println("Completed room update.");
                System.out.println(LONGDASH);
            }
            return;
        } else {
            System.out.printf("Unable to update room %s %n", changedRoom.name);
            System.out.println(LONGDASH);
        }
    }


    public int isInMap(String findRoom) {
        // This will find the location the Map of the Room or it will return 999
        // Checks for matches on ID or Name for simplicity's sake

        if (showDebug) {
            System.out.println(LONGDASH);
            System.out.println("RoomMap isInMap");
            System.out.printf("  Trying to find %s %n", findRoom);
        }

        for (int roomPos = 0; roomPos < roomIDs.length; roomPos++) {

            if (showDebug) {
                System.out.printf("  Checking [%d] %s, %s %n", roomPos, roomIDs[roomPos], roomNames[roomPos]);
            }

            if (roomIDs[roomPos].equalsIgnoreCase(findRoom) || roomNames[roomPos].equalsIgnoreCase(findRoom)) {

                if (showDebug) {
                    System.out.printf("  Room %s was in the Room Map at [%d]. %n", findRoom, roomPos);
                    System.out.println(LONGDASH);
                }
                return roomPos;
            }
        }

        if (showDebug) {
            System.out.printf("  Room %s was not in the Room Map. %n", findRoom);
            System.out.println(LONGDASH);
        }
        return 999;
    }
}
