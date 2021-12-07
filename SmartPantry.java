/**
 *  SmartPantry.java
 * 
 *  SmartPantry is designed to automatically keep track of the items 
 *  in the user's pantry and warn them when items have expired or if
 *  an item is about to expire. 
 * 
 *  Developers:
 *      Abdelazim Lokma - alokma@bu.edu
 *      Zhizhou Xing - xzzjoe@bu.edu
 *      Ashley Harlow - aeharlow@bu.edu
 */

import java.util.*;

public class SmartPantry {
    static Scanner scan;
    static Calendar currentDate;
    static LinkedList<Perishable> perishPantry;
    static LinkedList<Nonperishable> nonperishPantry;

    public static void main(String[] args) {
        scan = new Scanner(System.in);
        perishPantry = DBManager.createPeriArray();
        nonperishPantry = DBManager.createNPArray();

        //quickly sort both lists before continuing to ensure they are ordered
        sortList(true);
        sortList(false); 

        displayWelcome(); //Print the welcome string
        checkExpired(false); //check if any items are soon to expire

        String command = new String();

        while (true) {
            command = scan.nextLine();
            command = command.toLowerCase();

            if (command.equals("add item") || command.equals("a")) {

                System.out.println("Is your item perishable or nonperishable?");
                command = scan.nextLine();
                command = command.toLowerCase();
                if (command.equals("perishable") || command.equals("p")) {
                    addPerishable();
                    DBManager.arrayToDB(perishPantry, null);
                } else if (command.equals("nonperishable") || command.equals("n")) {
                    addNonPerishable();
                    DBManager.arrayToDB(null, nonperishPantry);
                } else {
                    System.out.println("Bad command!");
                }

            } else if (command.equals("remove item") || command.equals("r")) {

                removeItem();

            } else if (command.equals("edit item") || command.equals("e")) {

                editItem();
                DBManager.arrayToDB(perishPantry, nonperishPantry);

            } else if (command.equals("display pantry") || command.equals("d")) {

                displayPantry();

            } else if (command.equals("clear pantry")) {
                // completely wipe clean the pantry files
                // this is helper command for development, the user is not
                // expected to use it, so it is not mentioned in the help menu
                if (DBManager.clearDB("perishable.txt"))
                    perishPantry.clear();
                    System.out.println("Done!");
                if (DBManager.clearDB("nonperishable.txt"))
                    nonperishPantry.clear();
                    System.out.println("Done!");

            } else if (command.equals("check expired") || command.equals("c")) {

                checkExpired(true);

            } else if (command.equals("help") || command.equals("h")) {

                System.out.println();
                displayHelp();

            } else if (command.equals("quit") || command.equals("q")) {
                DBManager.arrayToDB(perishPantry, nonperishPantry);
                break;

            } else if(command.equals("list") || command.equals("l")){
                // list the contents of the pantry linked lists
                // this is helper command for development, the user is not
                // expected to use it, so it is not mentioned in the help menu
                for (int i = 0; i < perishPantry.size(); i++){
                    String temp = perishPantry.get(i).toString();
                    System.out.print(temp + ",");
                }
                for (int i = 0; i < nonperishPantry.size(); i++){
                    String temp = nonperishPantry.get(i).toString();
                    System.out.print(temp + ",");
                }
            }
            else {

                System.out.println("Unknown command");

            }
            System.out.println("\nPlease enter another command.");
        }
    }

    /**
     *  Displays a welcome message for the user upon launch
     */
    private static void displayWelcome() {
        System.out.println();
        System.out.println("----- Welcome to SmartPantry! -----");
        System.out.println();
        System.out.println("Please enter one of the following commands:");
        displayHelp();
        System.out.println();
        System.out.println("-----------------------------------");
        System.out.println();
    }

    /**
     *  Prints out all user commands
     */
    private static void displayHelp() {
        System.out.println("To add a new item, enter \"add item\"");
        System.out.println("To remove an item, enter \"remove item\"");
        System.out.println("To edit an item, enter \"edit item\"");
        System.out.println("To display your pantry, enter \"display pantry\"");
        System.out.println("To check for expired items, enter \"check expired\"");
        System.out.println("To see the commands again, enter \"help\"");
        System.out.println("To quit, enter \"quit\"");
    }

    /**
     * Prompts the user to enter appropriate data for adding a perishable
     * item to their pantry and creates a perishable object using that data.
     * The item is then added to the perishable pantry linked list and the 
     * list is sorted by expiration date.
     * @return returns true if the item was added successfully, returns 
     * false if any errors occur or an unknown command is entered.
     */
    private static boolean addPerishable() {
        System.out.println("What item are you adding?");
        String n = scan.nextLine();

        if(!Perishable.searchDatabase(n)){
            System.out.println("Sorry that item doesn't exist in our databases yet!");
            return false;
        }

        System.out.println("How many are you adding?");
        int q = scan.nextInt();
        scan.nextLine();

        System.out.println("Are you freezing this item?");
        String fs = scan.nextLine();
        fs = fs.toLowerCase();

        boolean f;

        if (fs.equals("yes") || fs.equals("y"))
            f = true;
        else if(fs.equals("no") || fs.equals("n"))
            f = false;
        else{
            System.out.println("Unknown command");
            return false;
        }
            
        Perishable temp = new Perishable(n, q, f);
        perishPantry.addLast(temp);

        System.out.print("You have just added: ");
        System.out.println(temp.toString());

        sortList(true);

        return true;
    }

     /**
      * Prompts the user to enter appropriate data for adding a nonperishable
     * item to their pantry and creates a nonperishable object using that data.
     * The item is then added to the nonperishable pantry linked list and the 
     * list is sorted by expiration date.
     * @return returns true if the item was added successfully, returns 
     * false if any errors occur or an unknown command is entered.
      */
    private static boolean addNonPerishable() {

        System.out.println("What item are you adding?");
        String n = scan.nextLine();

        if(!Nonperishable.searchDatabase(n)){
            System.out.println("Sorry that item doesn't exist in our databases yet!");
            return false;
        }

        System.out.println("How many are you adding?");
        int q = scan.nextInt();
        scan.nextLine();

        Nonperishable temp = new Nonperishable(n, q);
        nonperishPantry.addLast(temp);

        System.out.print("You have just added: ");
        System.out.println(temp.toString());

        sortList(false);

        return true;
    }
    
    /**
     * Prompts the user to enter the item they would like to remove from either
     * the perishable or nonperishable pantry. If there is more than one instance 
     * of an item in the pantry the oldest instance is removed.
     * @return returns true if the item is removed successfully, returns 
     * false if any errors occur or an unknown command is entered.
     */
    private static boolean removeItem() {
        System.out.println("Are you removing a perishable item or a nonperishable item?");
        String perish = scan.nextLine();

        if (perish.equals("perishable") || perish.equals("p")) {
            System.out.println("What item are you removing?");
            String item = scan.nextLine();

            Perishable temp = null;
            boolean found = false;

            // look for the item user wants to edit
            for (int i = 0; i < perishPantry.size(); i++) {
                temp = perishPantry.get(i);
                if (temp.getName().equals(item)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Sorry! We couldn't find that item in your pantry.");
                return false;
            }

            perishPantry.remove(temp);
        } else if (perish.equals("nonperishable") || perish.equals("n")) {
            System.out.println("What item are you removing?");
            String item = scan.nextLine();

            Nonperishable temp = null;
            boolean found = false;

            // look for the item user wants to edit
            for (int i = 0; i < nonperishPantry.size(); i++) {
                temp = nonperishPantry.get(i);
                if (temp.getName().equals(item)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Sorry! We couldn't find that item in your pantry.");
                return false;
            }

            nonperishPantry.remove(temp);
        } else {
            System.out.println("Unknown command");
            return false;
        }

        return true;
    }

     /**
      * Propmts the user to enter which item they would like to edit. Both perishable
      * and nonperishable objects give the user the option to edit the item's quantity.
      * If the user is editing a perishable object, the user will be given the option to 
      * change the object's frozen status. If the user changes the frozen status the object's
      * expiration date will change, so the pantry list will be sorted again.
      * @return returns true if the item was edited successfully, returns 
      * false if any errors occur or an unknown command is entered.
      */
    private static boolean editItem() {
        System.out.println("Would you like to edit a perishable item or a nonperishable item?");
        String perish = scan.nextLine();

        if (perish.equals("perishable") || perish.equals("p")) {
            System.out.println("What item would you like to edit?");
            String item = scan.nextLine();

            Perishable temp = null;
            boolean found = false;

            // look for the item user wants to edit
            for (int i = 0; i < perishPantry.size(); i++) {
                temp = perishPantry.get(i);
                if (temp.getName().equals(item)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Sorry! We couldn't find that item in your pantry.");
                return false;
            }

            System.out.println("You currently have " + temp.getQuantity() + " " +
                    temp.getName() + "\nWould you like to update this value?");
            String response = scan.nextLine();

            if (response.equals("yes") || response.equals("y")) {
                System.out.println("Please enter the new value");
                int val = scan.nextInt();
                scan.nextLine();
                if (val == 0) {
                    // they have finshed the item
                    perishPantry.remove(temp);
                    // if they finished the item skip asking about frozen
                    return true;
                } else {
                    temp.setQuantity(val);
                }
            } else if(response.equals("no") || response.equals("n"));
              else {
                System.out.println("Unknown command");
                return false;
              }

            // ask if the user wants to remove the item from the freezer or put it in
            if (temp.getIsFrozen()) {
                System.out.println("This item is currently in the freezer, would you like to remove it?");
                response = scan.nextLine();
                if (response.equals("yes") || response.equals("y")) {
                    // take it out of the frezzer
                    temp.setIsFrozen(false);

                    // change expiration date to tomorrow
                    Calendar tempDate = Calendar.getInstance();
                    tempDate.add(Calendar.DAY_OF_MONTH, 1);
                    temp.setExpire(tempDate);

                    sortList(true);
                } else if(response.equals("no") || response.equals("n"));
                else {
                  System.out.println("Unknown command");
                  return false;
                }
            } else {
                System.out.println("This item is currently not in the freezer," +
                        " would you like to put it into the freezer?");
                response = scan.nextLine();
                if (response.equals("yes") || response.equals("y")) {
                    temp.setIsFrozen(true);

                    // find the new experation date based on the remaining shelf life and
                    // the item's freezer multiplier
                    Calendar currExp = temp.getExpDate();
                    Calendar newExp = Calendar.getInstance();

                    long daysLeft = daysUntil(newExp, currExp);
                    double mult = temp.getMult();
                    daysLeft = (long) (daysLeft * mult);
                    newExp.add(Calendar.DAY_OF_MONTH, (int)daysLeft);

                    temp.setExpire(newExp);

                    sortList(true);
                } else if(response.equals("no") || response.equals("n"));
                else {
                  System.out.println("Unknown command");
                  return false;
                }
            }
        } else if (perish.equals("nonperishable") || perish.equals("n")) {
            System.out.println("What item would you like to edit?");
            String item = scan.nextLine();

            Nonperishable temp = null;
            boolean found = false;

            // look for the item user wants to edit
            for (int i = 0; i < nonperishPantry.size(); i++) {
                temp = nonperishPantry.get(i);
                if (temp.getName().equals(item)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Sorry! We couldn't find that item in your pantry.");
                return false;
            }

            System.out.println("You currently have " + temp.getQuantity() + " " +
                    temp.getName() + "\nWould you like to update this value?");
            String response = scan.nextLine();

            if (response.equals("yes") || response.equals("y")) {
                System.out.println("Please enter the new value");
                int val = scan.nextInt();
                scan.nextLine();
                if (val == 0) {
                    // they have finished the item
                    nonperishPantry.remove(temp);
                } else {
                    temp.setQuantity(val);
                }
            } else if(response.equals("no") || response.equals("n"));
            else {
              System.out.println("Unknown command");
              return false;
            }
        } else {
            System.out.println("Unknown command");
            return false;
        }

        return true;
    }

    /**
     * Sorts either the perishable or nonperishable pantry linked list
     * depending on the passed in boolean value.
     * @param IsPerishable If true, sort the perishable pantry linked list,
     * if false, sort the nonperishable pantry linked list
     */
    private static void sortList(boolean IsPerishable){

        if (IsPerishable == true) {
            perishPantry.sort(new sortByExpP());
        } else {
            nonperishPantry.sort(new sortByExpNP());
        }
    }

    /**
     * Displays the contents of the pantry to the user. The display is split
     * between perishable items and nonperisable items. The items are printed
     * in order of soonest to expire to last to expire.
     */
    private static void displayPantry() {
        
        //quickly sort both lists before printing
        sortList(true);
        sortList(false);

        // print out perishable items
        System.out.println();
        System.out.println("Perishable items:");
        System.out.println();      

        for (int i = 0; i < perishPantry.size(); i++) {
            System.out.println(perishPantry.get(i).toString());
        }
        System.out.println();

        System.out.println("-----------------------------------");

        // print mout nonperishable items
        System.out.println();
        System.out.println("Nonperishable items:");
        System.out.println();

        for (int i = 0; i < nonperishPantry.size(); i++) {
            System.out.println(nonperishPantry.get(i).toString());
        }
        System.out.println();
    }

    /**
     * Goes through the pantry files and checks to see if any
     * items have expired. If an item is expired it will print
     * a message stating so. If an item is nearing is expiration
     * date, it will print a message warning the user to use the
     * item soon before it expires. The threshold for the expiration
     * warning is three days.
     * @param userCall Upon start up we call this function to check for
     * expired items, but we do not want to print anything if nothing is
     * nearing expiration, but if the user requests to check for 
     * expired items and nothing is to expire soon, we must print a
     * statement saying so.
     */
    private static void checkExpired(boolean userCall){
        Calendar currentDate = Calendar.getInstance();
        Item nextItem;
        int periCount = 0;
        for (int i = 0; i < perishPantry.size(); i++) {
            nextItem = perishPantry.get(i);
            Calendar expiryDate = nextItem.getExpDate();
            long dayRemaining = daysUntil(currentDate, expiryDate);
            if (dayRemaining > 3) {
                break;
            }else if (dayRemaining < 0){
                if (nextItem.getQuantity() == 1){
                    String printString = nextItem.getQuantity() + " " +
                    nextItem.getName() + " has expired.";
                    System.out.println(printString);
                    periCount++;
                }
                else{
                    String printString = nextItem.getQuantity() + " " +
                    nextItem.getName() + " have expired.";
                    System.out.println(printString);
                    periCount++;
                }
            } else if (dayRemaining == 0) {
                String printString = nextItem.getQuantity() + " " +
                        nextItem.getName() + " expires today";
                System.out.println(printString);
                periCount++;
                continue;
            } else if (dayRemaining == 1) {
                periCount++;
                String printString = nextItem.getQuantity() + " " +
                        nextItem.getName() + " will expires in " + dayRemaining + " day";
                System.out.println(printString);
            } else {
                periCount++;
                String printString = nextItem.getQuantity() + " " +
                        nextItem.getName() + " will expire in " + dayRemaining + " days";
                System.out.println(printString);
            }
        }
        if (periCount == 0 && userCall) {
            System.out.println("No perishable items will expire in 3 days");
        }
        int nonperiCount = 0;
        for (int i = 0; i < nonperishPantry.size(); i++) {
            nextItem = nonperishPantry.get(i);
            Calendar expiryDate = nextItem.getExpDate();
            long dayRemaining = daysUntil(currentDate, expiryDate);
            if (dayRemaining > 3) {
                break;
            }else if (dayRemaining < 0){
                if (nextItem.getQuantity() == 1){
                    String printString = nextItem.getQuantity() + " " +
                    nextItem.getName() + " has expired.";
                    System.out.println(printString);
                    nonperiCount++;
                }
                else{
                    String printString = nextItem.getQuantity() + " " +
                    nextItem.getName() + " have expired.";
                    System.out.println(printString);
                    nonperiCount++;
                }
            }  
            else if (dayRemaining == 0) {
                nonperiCount++;
                String printString = nextItem.getQuantity() + " " +
                        nextItem.getName() + " expires today";
                System.out.println(printString);
                continue;
            } else if (dayRemaining == 1) {
                nonperiCount++;
                String printString = nextItem.getQuantity() + " " +
                        nextItem.getName() + "will expire in " + dayRemaining + " day";
                System.out.println(printString);
            } else {
                nonperiCount++;
                String printString = nextItem.getQuantity() + " " +
                        nextItem.getName() + " will expire in " + dayRemaining + " days";
                System.out.println(printString);
            }
        }
        if (nonperiCount == 0 && userCall) {
            System.out.println("No nonperishable items will expire in 3 days");
        }
    }
 
    /**
     * Calculates the number of days between two java Calendar dates
     * @param startDate the first date
     * @param endDate the second date
     * @return A long value representing the number of dats between startDate
     * and endDate. If endDate happens before startDate the returned value will be negative
     */
    private static long daysUntil(Calendar startDate, Calendar endDate) {
        long days;

        startDate.set(Calendar.HOUR_OF_DAY,0);
        endDate.set(Calendar.HOUR_OF_DAY,0);

        long startDateMilli = startDate.getTimeInMillis();
        long endDateMilli = endDate.getTimeInMillis();

        days =  (endDateMilli - startDateMilli);
        days = (days / (1000 * 60 * 60 * 24));

        return days+1;
    }
}