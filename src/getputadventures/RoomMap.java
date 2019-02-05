package getputadventures;

import java.io.File;
import java.util.Scanner;

public class RoomMap {

    // This information will change as they play the game, so it will need to be included in the save files

    // List of methods so I can keep myself straight as I build this thing
    //  public void printRoom(ListOfThings playerInventory)
    //  public String getRoomActions(ListOfThings playerInventory)
    //  public boolean roomAction(ItemLibrary gameItems, ActionLibrary gameActions, ListOfThings playerInventory, RoomMap gameMap)
    //  private void pickItUp(String userInput, int itemPos, ListOfThings playerInventory)
    //  private void dropIt(String userInput, int itemPos, ListOfThings playerInventory)
    //  private void badRoomAction()
    //  public String getCurrentRoomID()
    //  public void setCurrentRoomID(String currentRoomID)
    //  private void debugLong()
    //  private void debugShort()
    //  private void debugOutput(String outputThis)

    private static int MAXROOMS = 30;
    private static int MAXITEMS = 6;
    private static int MAXACTIONS = 6;
    private String[] roomIDs = new String[MAXROOMS];
    private String[] roomNames = new String[MAXROOMS];
    private String[] roomDescriptions = new String[MAXROOMS];
    private String[][] roomItems = new String[MAXROOMS][MAXITEMS];
    private String[][] roomActions = new String[MAXROOMS][MAXACTIONS];
    private String currentRoomID;
    private boolean showDebug;

    public RoomMap(boolean mainDebug) {
        showDebug = mainDebug;
        currentRoomID = "";
    }

    public void loadMap() throws Exception {

        debugLong();
        debugOutput("RoomMap loadMap");


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
            debugOutput("Checking line " + roomCounter);

            if (!roomIDs[roomCounter].isEmpty()) {

                debugOutput("  Reading in room name.");
                roomNames[roomCounter] = scan.next();

                debugOutput("  Reading in room description.");
                roomDescriptions[roomCounter] = scan.next();

                // The next sections have comma delimited subvalues
                // -------------------------------------------------------------------------------------

                debugShort();
                debugOutput("  Reading in room items.");

                allItems = scan.next();
                debugOutput("    " + allItems);

                // First clear out all the values in the room's item list
                for (int itemPos = 0; itemPos < MAXITEMS; itemPos++) {
                    roomItems[roomCounter][itemPos] = "";
                }

                // Then copy in the items (if there are any)

                if (!allItems.equalsIgnoreCase("None")) {
                    String[] tempItems = allItems.split(",");
                    itemCount = tempItems.length;

                    debugOutput("    " + itemCount + " items found.");

                    for (int newItemPos = 0; newItemPos < itemCount; newItemPos++) {
                        roomItems[roomCounter][newItemPos] = tempItems[newItemPos];
                    }
                } else {
                    debugOutput("    No Items found.");
                }

                // -------------------------------------------------------------------------------------
                debugShort();
                debugOutput("Reading in room Actions.");

                allActions = scan.next();
                debugOutput("    " + allActions);

                // First clear out all the values in the room's actions list
                for (int actionPos = 0; actionPos < MAXACTIONS; actionPos++) {
                    roomActions[roomCounter][actionPos] = "";
                }

                // Then copy in the actions (if there are any)

                if (!allActions.equalsIgnoreCase("None")) {

                    String[] tempActions = allActions.split(",");
                    actionCount = tempActions.length;
                    debugOutput("    " + actionCount + " Actions found.");

                    for (int newActionPos = 0; newActionPos < actionCount; newActionPos++) {
                        roomActions[roomCounter][newActionPos] = tempActions[newActionPos];
                    }
                } else {
                    debugOutput("    No Actions found.");
                }

                // -------------------------------------------------------------------------------------

                debugShort();
                debugOutput("  Room ID          " + roomIDs[roomCounter]);
                debugOutput("  Room name        " + roomNames[roomCounter]);
                debugOutput("  Room Description " + roomDescriptions[roomCounter]);
                debugOutput("  Items            " + allItems);
                debugOutput("  Actions          " + allActions);
                debugShort();
            } else {
                debugOutput("All done!");
                debugShort();
            }
            roomCounter += 1;
        }
        debugLong();
        scan.close();
    }

    public Room readRoom(String findThisID, ItemLibrary gameItems, ActionLibrary gameActions, ListOfThings playerInventory) {

        debugLong();
        debugOutput("RoomMap readRoom");

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
                debugLong();
                return thisRoom;
            }
        }

        debugOutput("Unable to find room ID " + findThisID + ".");
        debugLong();
        return thisRoom;
    }

    public void updateRoom(Room changedRoom) {
        // Setting it up so everything can change except the room ID

        debugLong();
        debugOutput("RoomMap updateRoom");
        debugOutput("  Trying to update room " + changedRoom.ID + " " + changedRoom.name);

        int roomPos = isInMap(changedRoom.ID);

        if (roomPos < 999) {
            if (!roomNames[roomPos].equalsIgnoreCase(changedRoom.name)) {
                debugOutput("  Updating room name from " + roomNames[roomPos] + " to " + changedRoom.name);
                updateRoomName(changedRoom.ID, changedRoom.name);
            }

            if (!roomNames[roomPos].equalsIgnoreCase(changedRoom.name)) {
                debugOutput("  Updating room description from " + roomDescriptions[roomPos] + " to " + changedRoom.getDescription());
                updateRoomDescription(changedRoom.ID, changedRoom.getDescription());
            }

            updateRoomItems(roomPos, changedRoom.getItems());
            updateRoomActions(roomPos, changedRoom.getActions());

            debugOutput("Completed room update.");
            debugLong();
        } else {
            debugOutput("Unable to find Room " + changedRoom.name);
            debugLong();
        }
    }

    public void updateRoomItems(int roomPos, ListOfThings changedItems) {

        debugLong();
        debugOutput("RoomMap updateRoomItems");

        String oldValue;
        String newValue;

        debugOutput("  Updating room Items");
        debugOutput("    Old: " + roomItems[roomPos]);
        debugOutput("    New: " + changedItems);
        debugOutput("    Expecting " + MAXITEMS + " in Old Room, found " + roomItems[roomPos].length);
        debugOutput("    Expecting " + MAXITEMS + " in New Room, found " + changedItems.getListLen());

        for (int itemPos = 0; itemPos < MAXITEMS; itemPos++) {
            debugOutput("    Checking [" + itemPos + "]");

            if (roomItems[roomPos][itemPos] == null) {
                oldValue = "";
                debugOutput("      The old Item was empty.");
            } else {
                oldValue = roomItems[roomPos][itemPos];
                debugOutput("      The old value was " + oldValue);
            }

            if (changedItems.getListThing(itemPos) == null) {
                debugOutput("      The new Item was empty.");
                newValue = "";
            } else {
                newValue = changedItems.getListThing(itemPos).getId();
                debugOutput("      The new value was " + newValue);
            }

            debugOutput("      Changing [" + itemPos + "] from Old " + oldValue + " to New " + newValue);
            roomItems[roomPos][itemPos] = newValue;
        }
        debugLong();
    }

    public void updateRoomActions(int roomPos, ListOfThings changedActions) {

        debugLong();
        debugOutput("RoomMap updateRoomActions");

        String oldValue;
        String newValue;

        debugOutput("  Updating room Actions");
        debugOutput("    Old: " + roomActions[roomPos]);
        debugOutput("    New: " + changedActions);
        debugOutput("    Expecting " + MAXACTIONS + " in Old Room, found " + roomActions[roomPos].length);
        debugOutput("    Expecting " + MAXACTIONS + " in New Room, found " + changedActions.getListLen());

        for (int actionPos = 0; actionPos < MAXACTIONS; actionPos++) {
            debugOutput("    Checking [" + actionPos + "]");

            if ((roomItems[roomPos][actionPos] == null) || (roomItems[roomPos][actionPos].isEmpty())) {
                debugOutput("      The old Action was empty.");
                oldValue = "";
            } else {
                oldValue = roomItems[roomPos][actionPos];
                debugOutput("      The old value was " + oldValue);
            }

            if ((changedActions.getListThing(actionPos) == null) || (changedActions.getListThing(actionPos).getId().isEmpty())) {
                debugOutput("      The new Action was empty.");
                newValue = "";
            } else {
                newValue = changedActions.getListThing(actionPos).getId();
                debugOutput("      The new value was " + newValue);
            }

            debugOutput("      Changing [" + actionPos + "] from Old " + oldValue + " to New " + newValue);
            roomItems[roomPos][actionPos] = newValue;
        }
        debugLong();
    }

    public void updateRoomName(String roomID, String Name) {

        debugLong();
        debugOutput("RoomMap updateRoomName");

        int roomPos = isInMap(roomID);

        if (roomPos < 999) {
            roomNames[roomPos] = Name;
        } else {
            debugOutput("Unable to find Room " + roomID);
            debugLong();
        }
    }

    public void updateRoomDescription(String roomID, String Description) {

        debugLong();
        debugOutput("RoomMap updateRoomDescription");

        int roomPos = isInMap(roomID);

        if (roomPos < 999) {
            roomDescriptions[roomPos] = Description;
        } else {
            debugOutput("Unable to find Room " + roomID);
            debugLong();
        }
    }


    public int isInMap(String findRoom) {
        // This will find the location the Map of the Room or it will return 999
        // Checks for matches on ID or Name for simplicity's sake

        debugLong();
        debugOutput("RoomMap isInMap");
        debugOutput("  Trying to find " + findRoom);

        for (int roomPos = 0; roomPos < roomIDs.length; roomPos++) {
            debugOutput("  Checking [" + roomPos + "] " + roomIDs[roomPos] + " " + roomNames[roomPos]);

            if (roomIDs[roomPos].equalsIgnoreCase(findRoom) || roomNames[roomPos].equalsIgnoreCase(findRoom)) {
                debugOutput("  Room " + findRoom + " was in the Room Map at [ " + roomPos + " ].");
                debugLong();
                return roomPos;
            }
        }

        debugOutput("  Room " + findRoom + "was not in the Room Map.");
        debugLong();
        return 999;
    }

    public String getCurrentRoomID() {
        return currentRoomID;
    }

    public void setCurrentRoomID(String currentRoomID) {
        this.currentRoomID = currentRoomID;
    }

    private void debugLong() {
        if (showDebug) {
            System.out.println("--------------------------------");
        }
    }

    private void debugShort() {
        if (showDebug) {
            System.out.println("----------------");
        }
    }

    private void debugOutput(String outputThis) {
        if (showDebug) {
            System.out.println(outputThis);
        }
    }
}
