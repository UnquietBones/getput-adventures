package getput.adventures;

public class ListOfThings {

    Boolean showDebug;

    public void printListOfThings(String[] itemsList, String displayWord, Boolean mainDebug) {

        showDebug = mainDebug;

        String[] newItemsList = itemsList;

        int itemCount = newItemsList.length - 1;
        String displayList = "";

        if (showDebug) {
            System.out.println("---------------------------------------------------------------");
            System.out.println("There are " + itemCount + " items in the passed in list.");
        }

        for (int itemCounter = 0; itemCounter <= itemCount; itemCounter++) {
            displayList += itemsList[itemCounter];
            if (itemCounter < itemCount) {
                displayList += ", ";
            }
        }
        System.out.println(displayWord + displayList);
        if (showDebug) {
            System.out.println("---------------------------------------------------------------");
        }
    }

}