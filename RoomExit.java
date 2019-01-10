package getput.adventures;

public class RoomExit {

    // Currently the exits only exist in one room

    boolean showDebug;
    String roomExitID;
    String roomExitName;
    String roomExitDescription;
    String roomExitDestination;
    int roomExitVisible;

    public RoomExit(String iID, String iName, String iDesc, String iDest, int iVisible, boolean mainDebug) {

        showDebug = mainDebug;
        roomExitID = iID;
        roomExitName = iName;
        roomExitDescription = iDesc;
        roomExitDestination = iDest;
        roomExitVisible = iVisible;
    }

    public void toggleVisible() {

        if (showDebug) {
            System.out.println("Visible is currently set to "+roomExitVisible);
        }

        if (roomExitVisible == 1) {
            roomExitVisible = 0;
        } else {
            roomExitVisible = 1;
        }
    }

    public void useRoomExit(RoomMap gameMap) {
        // using the exit will output the description and then change the current room

        System.out.println(roomExitDescription);

        if (showDebug) {
            System.out.println("Moving to room " + roomExitDestination);
        }

        gameMap.currentRoomID = roomExitDestination;
    }
}
