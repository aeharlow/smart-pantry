
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

/**
 * ---------------------------------------- R E A D  M E ! ! ! ------------------------------------------
 * 
 * we should add a feature that destroys the pantry file if it
 * becomes empty or we have to perform a check to see if there
 * are no lines in the pantry because if we start up with a pantry txt
 * that is empty but exists we get an index out of bounds error 
 * in check expired because it tries to read lines of a file thats empty
 * 
 * also the clear pantry command doesnt work, it doesnt delete the file 
 * or wipe it clean
 * 
 * check expired doesnt work but idk why really, im not sure how we are 
 * going to edit it though when it comes to the buffer reader because if 
 * we are going to rewrite it i dont want to spend a bunch of time debugging it
 * 
 * display pantry doesnt work but thats because Abdel's implementation of it
 * didnt push properly so if we can just grab that from somewhere and have 
 * someone else push it then that should work
 * 
 * also please dont forget to comment your code, the rubric specifies that
 * the prof wants fully commented code
 * 
 * one more thing, what do we want to do with the actual read me file? do we 
 * want to add like some quick documentation or something, maybe breifly touch
 * on some of the things we had considered about how we wanted to write the code
 * when we had our meetings? Maybe explain the assumptions we make about how the 
 * user interacts with the program itself?
 * 
 * ---------------------------------------- R E A D  M E ! ! ! ------------------------------------------
 */

import java.io.*;
import java.util.*;

public class SmartPantry {
    static Scanner scan;
    static Calendar currentDate;
    static LinkedList<Perishable> perishPantry;
    static LinkedList<Nonperishable> nonperishPantry;

    public static void main(String[] args) {
        scan = new Scanner(System.in);

        displayWelcome();
        checkExpired();

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
                } else if (command.equals("nonperishable") || command.equals("n")) {
                    addNonPerishable();
                } else {
                    System.out.println("Bad command!");
                }

            } else if (command.equals("remove item") || command.equals("r")) {

                removeItem();

            } else if (command.equals("edit item") || command.equals("e")) {

                editItem();

            } else if (command.equals("display pantry") || command.equals("d")) {

                displayPantry();

            } else if (command.equals("clear pantry")) {

                // completely wipe clean the pantry files
                if (DBManager.clearDB("temp-perish-pantry.txt"))
                    System.out.println("Done!");
                if (DBManager.clearDB("temp-nonperish-pantry.txt"))
                    System.out.println("Done!");

            } else if (command.equals("check expired") || command.equals("c")) {

                checkExpired();

            } else if (command.equals("help") || command.equals("h")) {

                System.out.println();
                displayHelp();

            } else if (command.equals("quit") || command.equals("q")) {

                break;

            } else {

                System.out.println("Unknown command");

            }
            System.out.println("\nPlease enter another command.");
        }
    }

    /*
     * displayWelcome() - displays a welcome message to the user upon launch
     */

    private static void displayWelcome() {
        System.out.println();
        System.out.println("----- Welcome to SmartPantry! -----");
        System.out.println();
        System.out.println("Please enter one of the following commands:");
        displayHelp();
    }

    /**
     * displayHelp() - prints all commands that the user can enter
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
     * addPerishable() - prompts the user to input the appropriate data for a
     * perishable item, calculates the expiration date, and
     * adds the item to the perishable pantry file
     */
    private static boolean addPerishable() {
        System.out.println("What item are you adding?");
        String n = scan.nextLine();

        System.out.println("How many are you adding?");
        int q = scan.nextInt();
        scan.nextLine();

        System.out.println("Are you freezing this item?");
        String fs = scan.nextLine();
        fs = fs.toLowerCase();

        boolean f = false;

        if (fs.equals("yes") || fs.equals("y"))
            f = true;
        else if (fs.equals("no") || fs.equals("n"))
            f = false;

        Perishable temp = new Perishable(n, q, f);
        perishPantry.add(temp);

        return true;
    }

    /**
     * addNonPerishable() - prompts the user to input the appropriate data for a
     * nonperishable item, calculates the expiration date, and
     * adds the item to the nonperishable pantry file
     */
    private static boolean addNonPerishable() {

        System.out.println("What item are you adding?");
        String n = scan.nextLine();

        System.out.println("How many are you adding?");
        int q = scan.nextInt();
        scan.nextLine();

        Nonperishable temp = new Nonperishable(n, q);
        nonperishPantry.add(temp);
        return true;
    }

    /**
     * removeItem() - prompts the user to enter which item they would like to remove
     * from the pantry, scans the pantry for the oldest instance of the
     * item and removes it from the pantry
     */
    private static boolean removeItem() {
        System.out.println("Are you removing a perishable item or a nonperishable item?");
        String perish = scan.nextLine();

        if (perish.equals("perishable") || perish.equals("p")) {
            System.out.println("What item are you removing?");
            String item = scan.nextLine();

            if (!DBManager.removeFromFile("perishable.txt", item)) {
                System.out.println("Sorry! We could not find that item!");
                return false;
            }
        } else if (perish.equals("nonperishable") || perish.equals("n")) {
            System.out.println("What item are you removing?");
            String item = scan.nextLine();

            if (!DBManager.removeFromFile("nonperishable.txt", item)) {
                System.out.println("Sorry! We could not find that item!");
                return false;
            }
        } else {
            System.out.println("Unknown command");
            return false;
        }

        return true;
    }

    /**
     * editItem() - asks the user which item they would like to edit, how they would
     * like to edit it, and edits the pantry accordingly. Possible edits
     * include: removing some of an item, taking a perishable item out of
     * the freezer, and putting a perishable item in the freezer
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
            for(int i = 0; i < perishPantry.size(); i++){
                temp = perishPantry.get(i);
                if(temp.getName().equals(item)){
                    found = true;
                    break;
                }
            }
            if(!found){
                System.out.println("Sorry! We couldn't find that item in your pantry.");
                return false;
            }

            System.out.println("You currently have " + temp.getQuantity() + " " + 
                temp.getName() + "\nWould you like to update this value?");
            String response = scan.nextLine();

            if (response.equals("yes") || response.equals("y")){
                System.out.println("Please enter the new value");
                int val = scan.nextInt();
                scan.nextLine();
                if (val == 0){
                    // they have finshed the item
                    perishPantry.remove(temp);
                    // if they finished the item skip asking about frozen
                    return true;
                } else {
                    temp.setQuantity(val);
                }
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
                    
                    int daysLeft = daysUntil(newExp, currExp);
                    double mult = temp.getMult();
                    daysLeft = (int) (daysLeft * mult);
                    newExp.add(Calendar.DAY_OF_MONTH, daysLeft);

                    temp.setExpire(newExp);
                }
            }
        } else if (perish.equals("nonperishable") || perish.equals("n")) {
            System.out.println("What item would you like to edit?");
            String item = scan.nextLine();

            Nonperishable temp = null;
            boolean found = false;

            // look for the item user wants to edit
            for(int i = 0; i < nonperishPantry.size(); i++){
                temp = nonperishPantry.get(i);
                if(temp.getName().equals(item)){
                    found = true;
                    break;
                }
            }
            if(!found){
                System.out.println("Sorry! We couldn't find that item in your pantry.");
                return false;
            }

            System.out.println("You currently have " + temp.getQuantity() + " " + 
                temp.getName() + "\nWould you like to update this value?");
            String response = scan.nextLine();

            if (response.equals("yes") || response.equals("y")){
                System.out.println("Please enter the new value");
                int val = scan.nextInt();
                scan.nextLine();
                if (val == 0){
                    // they have finished the item
                    nonperishPantry.remove(temp);
                } else{
                    temp.setQuantity(val);
                }
            }
        } else{
            System.out.println("Unknown command");
            return false;
        }

        return true;
    }

    // ------------------------------------------------------------- DISPLAY PANTRY
    // STILL ISNT DONE
    // ---------------------------------------------------------------------------

    /**
     * displayPantry() - displays the contents of the pantry to the user. The
     * display is
     * seperated between the perishable items and nonperishable items.
     */
    private static boolean displayPantry() {
        System.out.println();
        System.out.println("Perishable items:");
        System.out.println();

        // print out perishable items

        for (int i = 0; i < perishPantry.size(); i++) {
            System.out.println(perishPantry.get(i).toString());
        }

        System.out.println("-------------------------------------------");

        System.out.println();
        System.out.println("Nonperishable items:");
        System.out.println();

        for (int i = 0; i < nonperishPantry.size(); i++) {
            System.out.println(nonperishPantry.get(i).toString());
        }

        // print mout nonperishable items

        return true;
    }

    // ------------------------------------------------------------- CHECK EXPIRED
    // STILL ISNT DONE
    // ---------------------------------------------------------------------------

    /**
     * checkExpired() - goes through the pantry files and checks to see if any
     * items have expired. If an item is expired it will print
     * a message stating so. If an item is nearing is expiration
     * date, it will print a message warning the user to use the
     * item soon before it expires
     * 
     * xzz'notes: Do we need to make sure statement printing follow SVA
     * (subject-verb agreement)?
     * tbh i dont really care about the grammer at this point lol
     * What to print if no pantry are nearning expiry?
     * "Nothing is going to expire soon"? "Everything in your pantry is still
     * fresh"?
     * Can I assume there will be empty file existing in the system for the first
     * startup.
     * i dont think so because as of right now, on the very first start up the user
     * does not have either pantry file, they only have the data base files, so the
     * DBManager creates those files if they dont exist yet, i guess we could just
     * add an empty file from the very begining and then work with the empty files
     * if that makese sense
     * Should I have a segement for printing expiry?
     * wdym by a segment?
     */
    private static void checkExpired() {
        Calendar currentDate = Calendar.getInstance();
        Item nextItem;
        int periCount = 0;
        while ((nextItem = perishPantry.pop()) != null){
            Calendar expiryDate = nextItem.getExpDate();
            int dayRemaining = daysUntil(currentDate,expiryDate);
            if(dayRemaining > 3){
                break;
            }
            else if (dayRemaining == 0){
                String printString =  nextItem.getQuantity() + " " + 
                    nextItem.getName() + " expires today";
                System.out.println(printString);
                periCount ++;
                continue;
            }
            else if (dayRemaining == 1){
                periCount ++;
                if (nextItem.getQuantity() == 1){
                    String printString =  nextItem.getQuantity() + " " + 
                        nextItem.getName() + " expires in " + dayRemaining + " day";
                    System.out.println(printString);
                }
                else{
                    String printString =  nextItem.getQuantity() + " " + 
                        nextItem.getName() + " expire in " + dayRemaining + " day";
                    System.out.println(printString);
                }
            }
            else{
                periCount ++;
                if (nextItem.getQuantity() == 1){
                    String printString =  nextItem.getQuantity() + " " + 
                        nextItem.getName() + " expires in " + dayRemaining + " days";
                    System.out.println(printString);
                }
                else{
                    String printString =  nextItem.getQuantity() + " " + 
                        nextItem.getName() + " expire in " + dayRemaining + " days";
                    System.out.println(printString);
                } 
            }   
        }
        if (periCount == 0){
            System.out.print("No perishable items will expire in 3 days");
        }
        int nonperiCount = 0;
        while ((nextItem = nonperishPantry.pop()) != null){
            Calendar expiryDate = nextItem.getExpDate();
            int dayRemaining = daysUntil(currentDate,expiryDate);
            if(dayRemaining > 3){
                break;
            }
            else if (dayRemaining == 0){
                nonperiCount ++;
                String printString =  nextItem.getQuantity() + " " + 
                    nextItem.getName() + " expires today";
                System.out.println(printString);
                continue;
            }
            else if (dayRemaining == 1){
                nonperiCount ++;
                if (nextItem.getQuantity() == 1){
                    String printString =  nextItem.getQuantity() + " " + 
                        nextItem.getName() + " expires in " + dayRemaining + " day";
                    System.out.println(printString);
                }
                else{
                    String printString =  nextItem.getQuantity() + " " + 
                        nextItem.getName() + " expire in " + dayRemaining + " day";
                    System.out.println(printString);
                }
            }
            else{
                nonperiCount++;
                if (nextItem.getQuantity() == 1){
                    String printString =  nextItem.getQuantity() + " " + 
                        nextItem.getName() + " expires in " + dayRemaining + " days";
                    System.out.println(printString);
                }
                else{
                    String printString =  nextItem.getQuantity() + " " + 
                        nextItem.getName() + " expire in " + dayRemaining + " days";
                    System.out.println(printString);
                } 
            }  
        }
        if (nonperiCount == 0){
            System.out.print("No nonperishable items will expire in 3 days");
        }
    }

    /**
     * toCalender(d) - takes in a string d that holds a date in the format
     * "MM/DD/YYYY" and creates a Calender object with the
     * same date for the sake of doing date arithmetic with
     * the calender objects
     */
    
    // private static Calendar toCalendar(String d) {
    //     Calendar date = Calendar.getInstance();

    //     String[] dateInfo = d.split("/");

    //     int day, month, year;

    //     month = Integer.parseInt(dateInfo[0]) - 1;
    //     day = Integer.parseInt(dateInfo[1]);
    //     year = Integer.parseInt(dateInfo[2]);

    //     date.set(Calendar.MONTH, month);
    //     date.set(Calendar.DAY_OF_MONTH, day);
    //     date.set(Calendar.YEAR, year);

    //     return date;
    // }

    /**
     * daysUntil(startDate, endDate) - finds the number of days between the
     * Calenders startDate and endDate. If
     * the end date occurs before the startDate
     * the returned number of days will be negative
     */
    private static int daysUntil(Calendar startDate, Calendar endDate) {
        int days;

        long startDateMilli = startDate.getTimeInMillis();
        long endDateMilli = endDate.getTimeInMillis();

        days = (int) (endDateMilli - startDateMilli);
        days = (days / (1000 * 60 * 60 * 24));

        return days;
    }
}

/**
 * so pretty much what we should do is edit this to use the perishable
 * and nonperishable objects to act as a buffer between the text files
 * and smartPantry it self
 * 
 * "working directly with text files is unrealistic"
 * 
 * whenever we want to work with an item we will create an instance of
 * it using the information from the text files, whenever we update anything
 * using those objects we should rewrite it to the txt files
 * 
 * i think we also need to make perishable and nonperishable pantry objects
 * that can act as an interface between the pantry text files and SmartPantry
 * This should probably include a pantry interface
 * 
 * we should also change item into an interface i think
 */