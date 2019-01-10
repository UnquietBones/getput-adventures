package getput.adventures;

public class ListOfThings {

    // This can be a list of Items, Exits, or Actions

    // Currently Items can only be used once and only in one room
    //           Actions are used to link an Item to Room
    //           Exits can be used multiple times

    private boolean showDebug;

    public String listName;
    private int maxItems;
    public String[] itemsList;

    public ListOfThings(String newListName, int newMaxItems, boolean mainDebug) {

        showDebug = mainDebug;
        listName = newListName;
        maxItems = newMaxItems;
        itemsList = new String[maxItems];
        clearList();
    }

    private void clearList() {

        // Clear out the list, so we avoid nulls

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {
            itemsList[itemCounter] = "";

            if (showDebug) {
                System.out.println("Set item [" + itemCounter + "] to empty string.");
            }
        }

    }

    public boolean addItem(String newItem) {

        // This will find the first empty slot in the list, add the item, and
        // return true. If there are no empty slots it will return false.

        for (int itemCounter = 0; itemCounter <= maxItems; itemCounter++) {
            if (showDebug) {
                System.out.println("[" + itemCounter + "] " + itemsList[itemCounter]);
            }
            if (itemsList[itemCounter].isEmpty()) {
                itemsList[itemCounter] = newItem;
                System.out.println("Added " + newItem + " to " + listName + ".");
                return true;
            }
        }
        System.out.println(listName + " is full!");
        return false;
    }


    public void dropItem(String dropItemName) {

        // This will remove the item from the list, if found.

        for (int itemCounter = 0; itemCounter < maxItems; itemCounter++) {
            if (showDebug) {
                System.out.println("[" + itemCounter + "] " + itemsList[itemCounter]);
            }
            if (itemsList[itemCounter].equals(dropItemName)) {
                itemsList[itemCounter] = "";
                System.out.println("Item " + dropItemName + " is removed from " + listName);
                return;
            }
        }

        System.out.println("Item " + dropItemName + " was not in " + listName);
    }

    public boolean isInList(String findWord) {

        int itemCount = itemsList.length - 1;

        for (int itemCounter = 0; itemCounter <= (itemCount); itemCounter++) {

            if (showDebug) {
                System.out.println("[" + itemCounter + "] " + itemsList[itemCounter]);
            }

            if (itemsList[itemCounter].equals(findWord)) {
                return true;
            }
        }

        return false;
    }

    public void printListOfThings() {

        // It's possible for some array positions to be empty so we
        // need to skip over those.

        String displayList = "";
        int itemCount = itemsList.length;

        if (showDebug) {
            System.out.println("Expecting " + maxItems + " maxItems in " + listName);
            System.out.println("Found " + itemsList.length + " items.");
        }

        if (itemCount <= 0) {
            displayList = "None";
        } else {

            for (int itemCounter = 0; itemCounter < itemCount; itemCounter++) {

                if (showDebug) {
                    System.out.println("Checking [" + itemCounter + "] " + itemsList[itemCounter]);
                }

                if (itemsList[itemCounter] != null && !itemsList[itemCounter].isEmpty()) {

                    if (!displayList.isEmpty()) {
                        displayList += ", ";
                    }
                    displayList += itemsList[itemCounter];
                }
            }
            if (displayList.isEmpty()) {
                displayList = "None";
            }
        }
        System.out.println(listName + ": " + displayList);
    }

}
