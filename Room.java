package getput.adventures;

import java.util.Scanner;

public class Room {

    boolean showDebug;
    boolean exitGame;

    public String ID;
    public String name;
    public String description;

    public ListOfThings items;
    public ListOfThings exits;
    public ListOfThings actions;

    private static Scanner reader = new Scanner(System.in);

    // Right now we'll have a default max of 6 for everything, not sure if I can
    // specified at time of room creation? Still researching.

    public Room(String roomID, String roomName, String roomDescription, String[] roomItems, String[] roomExits, String[] roomActions, boolean mainDebug, boolean mainExit) {

        showDebug = mainDebug;
        exitGame = mainExit;
        ID = roomID;
        name = roomName;
        description = roomDescription;

        items = new ListOfThings("Items", 6, showDebug);
        exits = new ListOfThings("Exits", 6, showDebug);
        actions = new ListOfThings("Actions", 6, showDebug);

        items.itemsList = roomItems;
        exits.itemsList = roomExits;
        actions.itemsList = roomActions;
    }

    public void printRoom(ListOfThings playerInventory, ItemLibrary gameItems) {

        System.out.println(name);
        System.out.println("---------------------------");
        System.out.println(description);
        System.out.println("");
        items.printListOfThings();
        exits.printListOfThings();
        actions.printListOfThings();
        playerInventory.printListOfThings();
        System.out.println("---------------------------");
    }

    public boolean roomAction(ListOfThings playerInventory, ItemLibrary gameItems) {

        String userInput;

        System.out.printf("> ");
        userInput = reader.nextLine();

        if (items.isInList(userInput)) {

            //If it's an Item name, try and pick it up
            if (playerInventory.addItem(userInput)) {
                items.dropItem(userInput);
                return true;
            }
        } else {

            // If it's an Exit, try and move there
            if (exits.isInList(userInput)) {
                // No idea how to do this yet
            } else {

                // If it's an Action, perform the Action
                if (actions.isInList(userInput)) {

                    gameItems.readItem(userInput, exitGame);

                // If it's not an Item, Exit, or Action... nothing happens.
                } else {
                    System.out.println("The universe listens, but does not respond.");
                }

            }
        }

        // If we've hit this point, assume no successful action was taken
        return false;
    }
}
