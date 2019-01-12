package getput.adventures;


public class Main {

    public static void main(String[] args) throws Exception {

        boolean showDebug = true;
        boolean exitGame = false;

        ItemLibrary gameItems = new ItemLibrary(showDebug);
        ExitLibrary gameExits = new ExitLibrary(showDebug);
        ActionLibrary gameActions = new ActionLibrary(showDebug);

        System.out.println("Loading game...");

        gameItems.loadLibrary();
        gameExits.loadLibrary();
        gameActions.loadLibrary();
        ListOfThings playerInventory = new ListOfThings("Inventory", "PlayerInv", 6, gameItems, gameExits, gameActions, showDebug);

        RoomMap gameMap = new RoomMap(gameItems, gameExits, gameActions, playerInventory, showDebug);
        gameMap.loadMap();

        // New game defaults
        // We'll load the starting position from the file (eventually)

        String currentRoomID = "HOR1";
        Room currentRoom = gameMap.readRoom(currentRoomID);

        // Begin the adventure!

        currentRoom.printRoom();

        // Adventure loop

        int adventureLoop = 1;

        while (!exitGame) {
            if (currentRoom.roomAction()) {
                if (!currentRoomID.equals(currentRoom.ID)) {
                    gameMap.updateRoom(currentRoom);
                    currentRoom = gameMap.readRoom(currentRoomID);
                }

                // We only need to reprint the room if something has changed
                currentRoom.printRoom();
            }
            adventureLoop++;

            if (adventureLoop > 3) {
                exitGame = true;
            }
        }
    }
}
