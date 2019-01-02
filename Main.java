package getput.adventures;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.FALSE;

public class Main {

    public static void main(String[] args) throws Exception {

        Boolean showDebug = FALSE;
        RoomMap currentMap = new RoomMap(showDebug);
        Room currentRoom;

        System.out.println("Loading game...");

        currentMap.loadFile(showDebug);

        // We'll load the starting position from the file (eventually)
        String currentRoomID = "HOR1";
        currentRoom = currentMap.findRoom(currentRoomID);
        currentRoom.printRoom();
    }
}