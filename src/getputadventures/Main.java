package getputadventures;


public class Main {

    public static void main(String[] args) throws Exception {

        boolean showDebug = false;
        boolean exitGame = false;

        ItemLibrary gameItems = new ItemLibrary(showDebug);
        ExitLibrary gameExits = new ExitLibrary(showDebug);
        ActionLibrary gameActions = new ActionLibrary(showDebug);

        System.out.println("Loading game...");


        System.out.println("Loading Items...");
        gameItems.loadLibrary();

        System.out.println("Loading Exits...");
        gameExits.loadLibrary();

        System.out.println("Loading Actions...");
        gameActions.loadLibrary();

        System.out.println("Setting player inventory...");
        ListOfThings playerInventory = new ListOfThings("Inventory", "PlayerInv", 6, gameItems, gameExits, gameActions, showDebug);

        System.out.println("Loading Map...");
        RoomMap gameMap = new RoomMap(gameItems, gameExits, gameActions, playerInventory, showDebug);
        gameMap.loadMap();

        // New game defaults
        // We'll load the starting position from the file (eventually)

        System.out.println("Loading Room...");
        gameMap.currentRoomID = "HOR1";
        Room currentRoom = gameMap.readRoom(gameMap.currentRoomID);

        // Begin the adventure!

        currentRoom.printRoom();

        // Adventure loop

        int adventureLoop = 1;
        int maxLoops = 8;

        while (!exitGame) {
            if (currentRoom.roomAction()) {

                if (showDebug) {
                    System.out.println("Current Room is " + currentRoom.ID + " room loaded is " + gameMap.currentRoomID);
                }
                if (!gameMap.currentRoomID.equals(currentRoom.ID)) {

                    if (showDebug) {
                        System.out.println("Updating room "+currentRoom.ID);
                    }
                    gameMap.updateRoom(currentRoom);


                    if (showDebug) {
                        System.out.println("Loading  room "+gameMap.currentRoomID);
                    }

                    currentRoom = gameMap.readRoom(gameMap.currentRoomID);
                }

                // We only need to reprint the room if something has changed
                currentRoom.printRoom();
            }
            adventureLoop++;

            if (adventureLoop > maxLoops) {
                exitGame = true;
            }
        }
    }
}
