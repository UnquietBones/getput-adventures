package getputadventures;

public class Main {

    public static void main(String[] args) throws Exception {

        boolean showDebug = false;
        boolean exitGame = false;
        String winRoom = "HIR1";  // Hardcoded win position, will read from game file later
        int maxLoops = 50;  // Hardcoded timer, will read from game file later

        ItemLibrary gameItems = new ItemLibrary(showDebug);
        ActionLibrary gameActions = new ActionLibrary(showDebug);

        System.out.println("Loading game...");

        System.out.println("  Loading Items...");
        gameItems.loadLibrary();

        System.out.println("  Loading Actions...");
        gameActions.loadLibrary();

        System.out.println("  Setting player inventory...");
        ListOfThings playerInventory = new ListOfThings("Inventory", "PlayerInv", 6, gameItems, gameActions, showDebug);

        System.out.println("  Loading Map...");
        RoomMap gameMap = new RoomMap(showDebug);
        gameMap.loadMap();

        // New game defaults
        // We'll load the starting position from the file (eventually)

        System.out.println("Loading Room...");
        gameMap.setCurrentRoomID("HOR1");
        Room currentRoom = gameMap.readRoom(gameMap.getCurrentRoomID(), gameItems, gameActions, playerInventory);

        // Begin the adventure!

        currentRoom.printRoom(playerInventory);

        // Adventure loop
        // Right now this is limited by the number of loops, eventually it will be limited by the win condition

        int adventureLoop = 1;

        while (!exitGame) {
            if (currentRoom.roomAction(gameItems, gameActions, playerInventory, gameMap)) {

                if (showDebug) {
                    System.out.printf("Current Room is %s room loaded is %s %n", currentRoom.ID, gameMap.getCurrentRoomID());
                }
                if (!gameMap.getCurrentRoomID().equals(currentRoom.ID)) {

                    if (showDebug) {
                        System.out.printf("Updating room %s %n", currentRoom.ID);
                    }
                    gameMap.updateRoom(currentRoom);


                    if (showDebug) {
                        System.out.printf("Loading  room %s %n", gameMap.getCurrentRoomID());
                    }

                    currentRoom = gameMap.readRoom(gameMap.getCurrentRoomID(), gameItems, gameActions, playerInventory);

                    // We only reprint the room if they have moved, otherwise they need to use Look
                    currentRoom.printRoom(playerInventory);

                    if (gameMap.getCurrentRoomID().equalsIgnoreCase(winRoom)) {
                        System.out.println("You have won the game!");
                        exitGame = true;
                    }
                }
            } else {
                System.out.println("You click your ruby slippers together and the magic takes you home...");
                exitGame = true;
            }
            adventureLoop++;

            if (adventureLoop > maxLoops) {
                exitGame = true;
            }
        }
    }
}