package getput.adventures;

public class Room {

    private String name;
    private String description;
    private String[] items;
    private String[] exits;
    private String[] actions;
    Boolean showDebug;

    public Room(String roomName, String roomDescription, String[] roomItems, String[] roomExits, String[] roomActions, Boolean mainDebug) {
        name = roomName;
        description = roomDescription;
        items = roomItems;
        exits = roomExits;
        actions = roomActions;
        showDebug = mainDebug;
    }

    public void printRoom() {

        ListOfThings listOfItems = new ListOfThings();

        System.out.println(name);
        System.out.println("---------------------------");
        System.out.println(description);
        System.out.println("");
        listOfItems.printListOfThings(items, "Items: ", showDebug);
        listOfItems.printListOfThings(exits, "Exits: ", showDebug);
        listOfItems.printListOfThings(actions, "Actions: ", showDebug);
        System.out.println("---------------------------");
    }
}