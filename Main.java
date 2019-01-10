package getput.adventures;


public class Main {

    public static void main(String[] args) throws Exception {

        boolean showDebug = false;
        boolean exitGame = false;

        RoomMap gameMap = new RoomMap(showDebug);
        ItemLibrary gameItems = new ItemLibrary(showDebug);
        RoomExitLibrary gameExits = new RoomExitLibrary(showDebug);

        Room currentRoom;


        System.out.println("Loading game...");

        gameMap.loadMap();
        gameItems.loadLibrary();
        gameExits.loadLibrary();

        // New game defaults
        // We'll load the starting position from the file (eventually)

        String currentRoomID = "HOR1";
        ListOfThings playerInventory = new ListOfThings("Inventory", 5, showDebug);

        // Begin the adventure!

        currentRoom = gameMap.readRoom(currentRoomID, exitGame);
        currentRoom.printRoom(playerInventory, gameItems);

        // Adventure loop

        int adventureLoop = 1;

        while (!exitGame) {
            if (currentRoom.roomAction(playerInventory, gameItems)) {
                if (!currentRoomID.equals(currentRoom.ID)) {
                    gameMap.updateRoom(currentRoom, exitGame);
                    currentRoom = gameMap.readRoom(currentRoomID, exitGame);
                }

                // We only need to reprint the room if something has changed
                currentRoom.printRoom(playerInventory, gameItems);
            }
            adventureLoop++;

            if (adventureLoop > 3) {
                exitGame = true;
            }
        }
    }
}
