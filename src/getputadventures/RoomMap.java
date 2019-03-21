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
    private DisplayMsgs displayMsgs;

    public RoomMap(boolean mainDebug) {
        showDebug = mainDebug;
        displayMsgs = new DisplayMsgs(showDebug);
        currentRoomID = "";
    }

    public void loadMap() throws Exception {

        displayMsgs.debugHeader("RoomMap loadMap");

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

        displayMsgs.debugOutput("  Loading Room Map from " + pathname);
        displayMsgs.debugShort();

        // throw out the first line, it's the header
        scan.nextLine();

        while (scan.hasNext()) {

            roomIDs[roomCounter] = scan.next();
            displayMsgs.debugOutput("Checking line " + roomCounter);

            if (!roomIDs[roomCounter].isEmpty()) {

                displayMsgs.debugOutput("  Reading in room name.");
                roomNames[roomCounter] = scan.next();

                displayMsgs.debugOutput("  Reading in room description.");
                roomDescriptions[roomCounter] = scan.next();

                // The next sections have comma delimited subvalues
                // -------------------------------------------------------------------------------------

                displayMsgs.debugShort();
                displayMsgs.debugOutput("  Reading in room items.");

                allItems = scan.next();
                displayMsgs.debugOutput("    " + allItems);

                // First clear out all the values in the room's item list
                for (int itemPos = 0; itemPos < MAXITEMS; itemPos++) {
                    roomItems[roomCounter][itemPos] = "";
                }

                // Then copy in the items (if there are any)

                if (!allItems.equalsIgnoreCase("None")) {
                    String[] tempItems = allItems.split(",");
                    itemCount = tempItems.length;

                    displayMsgs.debugOutput("    " + itemCount + " items found.");

                    for (int newItemPos = 0; newItemPos < itemCount; newItemPos++) {
                        roomItems[roomCounter][newItemPos] = tempItems[newItemPos];
                    }
                } else {
                    displayMsgs.debugOutput("    No Items found.");
                }

                // -------------------------------------------------------------------------------------
                displayMsgs.debugShort();
                displayMsgs.debugOutput("Reading in room Actions.");

                allActions = scan.next();
                displayMsgs.debugOutput("    " + allActions);

                // First clear out all the values in the room's actions list
                for (int actionPos = 0; actionPos < MAXACTIONS; actionPos++) {
                    roomActions[roomCounter][actionPos] = "";
                }

                // Then copy in the actions (if there are any)

                if (!allActions.equalsIgnoreCase("None")) {

                    String[] tempActions = allActions.split(",");
                    actionCount = tempActions.length;
                    displayMsgs.debugOutput("    " + actionCount + " Actions found.");

                    for (int newActionPos = 0; newActionPos < actionCount; newActionPos++) {
                        roomActions[roomCounter][newActionPos] = tempActions[newActionPos];
                    }
                } else {
                    displayMsgs.debugOutput("    No Actions found.");
                }

                // -------------------------------------------------------------------------------------

                displayMsgs.debugShort();
                displayMsgs.debugOutput("  Room ID          " + roomIDs[roomCounter]);
                displayMsgs.debugOutput("  Room name        " + roomNames[roomCounter]);
                displayMsgs.debugOutput("  Room Description " + roomDescriptions[roomCounter]);
                displayMsgs.debugOutput("  Items            " + allItems);
                displayMsgs.debugOutput("  Actions          " + allActions);
                displayMsgs.debugShort();
            } else {
                displayMsgs.debugOutput("All done!");
                displayMsgs.debugShort();
            }
            roomCounter += 1;
        }
        displayMsgs.debugLong();
        scan.close();
    }

    public Room readRoom(String findThisID, ItemLibrary gameItems, ActionLibrary gameActions, ListOfThings playerInventory) {

        displayMsgs.debugLong();
        displayMsgs.debugOutput("RoomMap readRoom");

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
                displayMsgs.debugLong();
                return thisRoom;
            }
        }

        displayMsgs.debugOutput("Unable to find room ID " + findThisID + ".");
        displayMsgs.debugLong();
        return thisRoom;
    }

    public void updateRoom(Room changedRoom) {
        // Setting it up so everything can change except the room ID

        displayMsgs.debugLong();
        displayMsgs.debugOutput("RoomMap updateRoom");
        displayMsgs.debugOutput("  Trying to update room " + changedRoom.ID + " " + changedRoom.name);

        int roomPos = isInMap(changedRoom.ID);

        if (roomPos < 999) {
            if (!roomNames[roomPos].equalsIgnoreCase(changedRoom.name)) {
                displayMsgs.debugOutput("  Updating room name from " + roomNames[roomPos] + " to " + changedRoom.name);
                updateRoomName(changedRoom.ID, changedRoom.name);
            }

            if (!roomNames[roomPos].equalsIgnoreCase(changedRoom.name)) {
                displayMsgs.debugOutput("  Updating room description from " + roomDescriptions[roomPos] + " to " + changedRoom.getDescription());
                updateRoomDescription(changedRoom.ID, changedRoom.getDescription());
            }

            updateRoomItems(roomPos, changedRoom.getItems());
            updateRoomActions(roomPos, changedRoom.getActions());

            displayMsgs.debugOutput("Completed room update.");
            displayMsgs.debugLong();
        } else {
            displayMsgs.debugOutput("Unable to find Room " + changedRoom.name);
            displayMsgs.debugLong();
        }
    }

    public void updateRoomItems(int roomPos, ListOfThings changedItems) {

        String oldValue;
        String newValue;

        displayMsgs.debugLong();
        displayMsgs.debugOutput("RoomMap updateRoomItems");

        for (int itemPos = 0; itemPos < MAXITEMS; itemPos++) {
            displayMsgs.debugOutput("    Checking [" + itemPos + "]");

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
                displayMsgs.debugOutput("      Changing [" + itemPos + "] from Old " + oldValue + " to New " + newValue);
            }

            roomItems[roomPos][itemPos] = newValue;
        }
        displayMsgs.debugLong();
    }

    public void updateRoomActions(int roomPos, ListOfThings changedActions) {

        String oldValue;
        String newValue;

        displayMsgs.debugLong();
        displayMsgs.debugOutput("RoomMap updateRoomActions");

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
                displayMsgs.debugOutput("      Changing [" + actionPos + "] from Old " + oldValue + " to New " + newValue);
            }

            roomActions[roomPos][actionPos] = newValue;
        }
        displayMsgs.debugLong();
    }

    public void updateRoomName(String roomID, String Name) {

        displayMsgs.debugLong();
        displayMsgs.debugOutput("RoomMap updateRoomName");

        int roomPos = isInMap(roomID);

        if (roomPos < 999) {
            roomNames[roomPos] = Name;
        } else {
            displayMsgs.debugOutput("Unable to find Room " + roomID);
            displayMsgs.debugLong();
        }
    }

    public void updateRoomDescription(String roomID, String Description) {

        displayMsgs.debugLong();
        displayMsgs.debugOutput("RoomMap updateRoomDescription");

        int roomPos = isInMap(roomID);

        if (roomPos < 999) {
            roomDescriptions[roomPos] = Description;
        } else {
            displayMsgs.debugOutput("Unable to find Room " + roomID);
            displayMsgs.debugLong();
        }
    }


    public int isInMap(String findRoom) {
        // This will find the location the Map of the Room or it will return 999
        // Checks for matches on ID or Name for simplicity's sake

        displayMsgs.debugLong();
        displayMsgs.debugOutput("RoomMap isInMap");
        displayMsgs.debugOutput("  Trying to find " + findRoom);

        for (int roomPos = 0; roomPos < roomIDs.length; roomPos++) {
            displayMsgs.debugOutput("  Checking [" + roomPos + "] " + roomIDs[roomPos] + " " + roomNames[roomPos]);

            if (roomIDs[roomPos].equalsIgnoreCase(findRoom) || roomNames[roomPos].equalsIgnoreCase(findRoom)) {
                displayMsgs.debugOutput("  Room " + findRoom + " was in the Room Map at [ " + roomPos + " ].");
                displayMsgs.debugLong();
                return roomPos;
            }
        }

        displayMsgs.debugOutput("  Room " + findRoom + "was not in the Room Map.");
        displayMsgs.debugLong();
        return 999;
    }

    public String getCurrentRoomID() {
        return currentRoomID;
    }

    public void setCurrentRoomID(String currentRoomID) {
        this.currentRoomID = currentRoomID;
    }
}
