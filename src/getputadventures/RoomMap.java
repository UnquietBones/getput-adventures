package getputadventures;

import java.io.File;
import java.util.Scanner;

public class RoomMap {

    private static int MAXROOMS = 4;
    private static int MAXITEMS = 6;
    private static int MAXACTIONS = 12;
    private String[] roomIDs = new String[MAXROOMS];
    private String[] roomNames = new String[MAXROOMS];
    private String[] roomDescriptions = new String[MAXROOMS];
    private String[][] roomItems = new String[MAXROOMS][MAXITEMS];
    private String[][] roomActions = new String[MAXROOMS][MAXACTIONS];
    private String currentRoomID;
    private boolean showDebug;
    private DebugMsgs debugMessage;

    public RoomMap(boolean mainDebug) {
        showDebug = mainDebug;
        debugMessage = new DebugMsgs(showDebug);
        currentRoomID = "";
    }

    public void loadMap() throws Exception {

        debugMessage.debugLong();
        debugMessage.debugOutput("RoomMap loadMap");

        String allItems;
        String allActions;

        int roomCounter = 0;
        int itemCount = 0;
        int actionCount = 0;

        // pass the path to the file as a parameter

        //File file = new File("C:\\getputadventures\\rooms.txt");
        String pathname = "C:\\getputadventures\\testrooms.txt";
        File file = new File(pathname);
        Scanner scan = new Scanner(file);
        scan.useDelimiter("/|\\r\\n");

        debugMessage.debugOutput("  Loading Room Map from " + pathname);
        debugMessage.debugShort();

        // throw out the first line, it's the header
        scan.nextLine();

        while (scan.hasNext()) {

            roomIDs[roomCounter] = scan.next();
            debugMessage.debugOutput("Checking line " + roomCounter);

            if (!roomIDs[roomCounter].isEmpty()) {

                debugMessage.debugOutput("  Reading in room name.");
                roomNames[roomCounter] = scan.next();

                debugMessage.debugOutput("  Reading in room description.");
                roomDescriptions[roomCounter] = scan.next();

                // The next sections have comma delimited subvalues
                // -------------------------------------------------------------------------------------

                debugMessage.debugShort();
                debugMessage.debugOutput("  Reading in room items.");

                allItems = scan.next();
                debugMessage.debugOutput("    " + allItems);

                // First clear out all the values in the room's item list
                for (int itemPos = 0; itemPos < MAXITEMS; itemPos++) {
                    roomItems[roomCounter][itemPos] = "";
                }

                // Then copy in the items (if there are any)

                if (!allItems.equalsIgnoreCase("None")) {
                    String[] tempItems = allItems.split(",");
                    itemCount = tempItems.length;

                    debugMessage.debugOutput("    " + itemCount + " items found.");

                    for (int newItemPos = 0; newItemPos < itemCount; newItemPos++) {
                        roomItems[roomCounter][newItemPos] = tempItems[newItemPos];
                    }
                } else {
                    debugMessage.debugOutput("    No Items found.");
                }

                // -------------------------------------------------------------------------------------
                debugMessage.debugShort();
                debugMessage.debugOutput("Reading in room Actions.");

                allActions = scan.next();
                debugMessage.debugOutput("    " + allActions);

                // First clear out all the values in the room's actions list
                for (int actionPos = 0; actionPos < MAXACTIONS; actionPos++) {
                    roomActions[roomCounter][actionPos] = "";
                }

                // Then copy in the actions (if there are any)

                if (!allActions.equalsIgnoreCase("None")) {

                    String[] tempActions = allActions.split(",");
                    actionCount = tempActions.length;
                    debugMessage.debugOutput("    " + actionCount + " Actions found.");

                    for (int newActionPos = 0; newActionPos < actionCount; newActionPos++) {
                        roomActions[roomCounter][newActionPos] = tempActions[newActionPos];
                    }
                } else {
                    debugMessage.debugOutput("    No Actions found.");
                }

                // -------------------------------------------------------------------------------------

                debugMessage.debugShort();
                debugMessage.debugOutput("  Room ID          " + roomIDs[roomCounter]);
                debugMessage.debugOutput("  Room name        " + roomNames[roomCounter]);
                debugMessage.debugOutput("  Room Description " + roomDescriptions[roomCounter]);
                debugMessage.debugOutput("  Items            " + allItems);
                debugMessage.debugOutput("  Actions          " + allActions);
                debugMessage.debugShort();
            } else {
                debugMessage.debugOutput("All done!");
                debugMessage.debugShort();
            }
            roomCounter += 1;
        }
        debugMessage.debugLong();
        scan.close();
    }

    public Room readRoom(String findThisID, ItemLibrary gameItems, ActionLibrary gameActions, ListOfThings playerInventory) {

        debugMessage.debugLong();
        debugMessage.debugOutput("RoomMap readRoom");

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
                debugMessage.debugLong();
                return thisRoom;
            }
        }

        debugMessage.debugOutput("Unable to find room ID " + findThisID + ".");
        debugMessage.debugLong();
        return thisRoom;
    }

    public void updateRoom(Room changedRoom) {
        // Setting it up so everything can change except the room ID

        debugMessage.debugLong();
        debugMessage.debugOutput("RoomMap updateRoom");
        debugMessage.debugOutput("  Trying to update room " + changedRoom.ID + " " + changedRoom.name);

        int roomPos = isInMap(changedRoom.ID);

        if (roomPos < 999) {
            if (!roomNames[roomPos].equalsIgnoreCase(changedRoom.name)) {
                debugMessage.debugOutput("  Updating room name from " + roomNames[roomPos] + " to " + changedRoom.name);
                updateRoomName(changedRoom.ID, changedRoom.name);
            }

            if (!roomNames[roomPos].equalsIgnoreCase(changedRoom.name)) {
                debugMessage.debugOutput("  Updating room description from " + roomDescriptions[roomPos] + " to " + changedRoom.getDescription());
                updateRoomDescription(changedRoom.ID, changedRoom.getDescription());
            }

            updateRoomItems(roomPos, changedRoom.getItems());
            updateRoomActions(roomPos, changedRoom.getActions());

            debugMessage.debugOutput("Completed room update.");
            debugMessage.debugLong();
        } else {
            debugMessage.debugOutput("Unable to find Room " + changedRoom.name);
            debugMessage.debugLong();
        }
    }

    public void updateRoomItems(int roomPos, ListOfThings changedItems) {

        String oldValue;
        String newValue;

        debugMessage.debugLong();
        debugMessage.debugOutput("RoomMap updateRoomItems");

        for (int itemPos = 0; itemPos < MAXITEMS; itemPos++) {
            debugMessage.debugOutput("    Checking [" + itemPos + "]");

            if (roomItems[roomPos][itemPos] == null) {
                oldValue = "";
            } else {
                oldValue = roomItems[roomPos][itemPos];
            }

            if (changedItems.getListThing(itemPos) == null) {
                newValue = "";
            } else {
                newValue = changedItems.getListThing(itemPos).getId();
            }

            if (!newValue.equalsIgnoreCase(oldValue)) {
                debugMessage.debugOutput("      Changing [" + itemPos + "] from Old " + oldValue + " to New " + newValue);
            }

            roomItems[roomPos][itemPos] = newValue;
        }
        debugMessage.debugLong();
    }

    public void updateRoomActions(int roomPos, ListOfThings changedActions) {

        String oldValue;
        String newValue;

        debugMessage.debugLong();
        debugMessage.debugOutput("RoomMap updateRoomActions");

        for (int actionPos = 0; actionPos < MAXACTIONS; actionPos++) {

            if ((roomActions[roomPos][actionPos] == null) || (roomActions[roomPos][actionPos].isEmpty())) {
                oldValue = "";
            } else {
                oldValue = roomActions[roomPos][actionPos];
            }

            if ((changedActions.getListThing(actionPos) == null) || (changedActions.getListThing(actionPos).getId().isEmpty())) {
                newValue = "";
            } else {
                newValue = changedActions.getListThing(actionPos).getId();
            }

            if (!newValue.equalsIgnoreCase(oldValue)) {
                debugMessage.debugOutput("      Changing [" + actionPos + "] from Old " + oldValue + " to New " + newValue);
            }

            roomActions[roomPos][actionPos] = newValue;
        }
        debugMessage.debugLong();
    }

    public void updateRoomName(String roomID, String Name) {

        debugMessage.debugLong();
        debugMessage.debugOutput("RoomMap updateRoomName");

        int roomPos = isInMap(roomID);

        if (roomPos < 999) {
            roomNames[roomPos] = Name;
        } else {
            debugMessage.debugOutput("Unable to find Room " + roomID);
            debugMessage.debugLong();
        }
    }

    public void updateRoomDescription(String roomID, String Description) {

        debugMessage.debugLong();
        debugMessage.debugOutput("RoomMap updateRoomDescription");

        int roomPos = isInMap(roomID);

        if (roomPos < 999) {
            roomDescriptions[roomPos] = Description;
        } else {
            debugMessage.debugOutput("Unable to find Room " + roomID);
            debugMessage.debugLong();
        }
    }


    public int isInMap(String findRoom) {
        // This will find the location the Map of the Room or it will return 999
        // Checks for matches on ID or Name for simplicity's sake

        debugMessage.debugLong();
        debugMessage.debugOutput("RoomMap isInMap");
        debugMessage.debugOutput("  Trying to find " + findRoom);

        for (int roomPos = 0; roomPos < roomIDs.length; roomPos++) {
            debugMessage.debugOutput("  Checking [" + roomPos + "] " + roomIDs[roomPos] + " " + roomNames[roomPos]);

            if (roomIDs[roomPos].equalsIgnoreCase(findRoom) || roomNames[roomPos].equalsIgnoreCase(findRoom)) {
                debugMessage.debugOutput("  Room " + findRoom + " was in the Room Map at [ " + roomPos + " ].");
                debugMessage.debugLong();
                return roomPos;
            }
        }

        debugMessage.debugOutput("  Room " + findRoom + "was not in the Room Map.");
        debugMessage.debugLong();
        return 999;
    }

    public String getCurrentRoomID() {
        return currentRoomID;
    }

    public void setCurrentRoomID(String currentRoomID) {
        this.currentRoomID = currentRoomID;
    }
}
