package getputadventures;

public class Main {

    public static void main(String[] args) throws Exception {

        boolean showDebug = false;
        boolean exitGame = false;
        DisplayMsgs displayMsgs = new DisplayMsgs(showDebug);

        // These are all hardcoded right now, will read from game file later

        String startRoom = "Room1";
        String winRoom = "Room5";
        int maxTurns = 50;

        // We need probably need to split these out to a method or a load data class or something

        ItemLibrary gameItems = new ItemLibrary(showDebug);
        ActionLibrary gameActions = new ActionLibrary(showDebug);

        displayMsgs.displayMessage("LoadGame", true);

        displayMsgs.displayMessage("LoadItems", true);
        gameItems.loadItemLibrary();

        displayMsgs.displayMessage("LoadActions", true);
        gameActions.loadActionLibrary();

        displayMsgs.displayMessage("LoadPlayerInventory", true);
        ListOfThings playerInventory = new ListOfThings("Inventory", "PlayerInv", 6, gameItems, gameActions, showDebug);

        displayMsgs.displayMessage("LoadMap",  true);
        RoomMap gameMap = new RoomMap(showDebug);
        gameMap.loadMap();
        gameMap.setCurrentRoomID(startRoom);

        Room currentRoom = gameMap.readRoom(gameMap.getCurrentRoomID(), gameItems, gameActions, playerInventory);

        // Begin the adventure!

        currentRoom.printRoom(playerInventory, 0, maxTurns);

        // Right now this is limited by the number of loops but could use the counter as a score instead of a timer

        int adventureLoop = 1;

        while (!exitGame) {
            if (currentRoom.roomAction(gameItems, gameActions, playerInventory, gameMap, adventureLoop, maxTurns)) {

                displayMsgs.debugOutput("Current Room is %" + currentRoom.ID + " room loaded is " + gameMap.getCurrentRoomID() + ".");

                if (!gameMap.getCurrentRoomID().equals(currentRoom.ID)) {

                    displayMsgs.debugOutput("Updating room " + currentRoom.ID + ".");
                    gameMap.updateRoom(currentRoom);

                    displayMsgs.debugOutput("Loading room " + gameMap.getCurrentRoomID() + ".");
                    currentRoom = gameMap.readRoom(gameMap.getCurrentRoomID(), gameItems, gameActions, playerInventory);
                    currentRoom.printRoom(playerInventory, adventureLoop, maxTurns);

                    if (gameMap.getCurrentRoomID().equalsIgnoreCase(winRoom)) {
                        displayMsgs.displayMessage("WonGame",  true);
                        exitGame = true;
                    }
                }
            } else {
                displayMsgs.displayMessage("GoHome", true);
                exitGame = true;
            }
            adventureLoop++;

            if (adventureLoop > maxTurns) {
                displayMsgs.displayMessage("OutOfTurns", true);
                exitGame = true;
            }
        }
    }
}