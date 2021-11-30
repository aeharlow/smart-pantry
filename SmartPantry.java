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
import javax.swing.text.StyledEditorKit;

public class SmartPantry {
    static Scanner scan;
    static Calendar currentDate;
    public static void main(String[] args) {
        scan = new Scanner(System.in);

        displayWelcome();
        checkExpired();

        String command = new String();

        while(true){
            command = scan.nextLine();
            command = command.toLowerCase();

            if(command.equals("add item") || command.equals("a")){

                System.out.println("Is your item perishable or nonperishable?");
                command = scan.nextLine();
                command = command.toLowerCase();
                if(command.equals("perishable") || command.equals("p")){
                    addPerishable();
                } else if(command.equals("nonperishable") || command.equals("n")){
                    addNonPerishable();
                } else{
                    System.out.println("Bad command!");
                }

            } else if (command.equals("remove item") || command.equals("r")){

                removeItem();

            } else if (command.equals("edit item") || command.equals("e")){

                editItem();

            } else if(command.equals("display pantry") || command.equals("d")){

               displayPantry();

            } else if(command.equals("clear pantry")){

                // completely wipe clean the pantry files
                if(DBManager.clearDB("temp-perish-pantry.txt")) System.out.println("Done!");
                if(DBManager.clearDB("temp-nonperish-pantry.txt")) System.out.println("Done!");
                
            } else if(command.equals("check expired") || command.equals("c")){

                checkExpired();

            } else if(command.equals("help") || command.equals("h")){

                System.out.println();
                displayHelp();

            } else if(command.equals("quit") || command.equals("q")){

                break;

            } else{

                System.out.println("Bad command!");

            }
            System.out.println("\nPlease enter another command.");
        }
    }
    
    /*
     *  displayWelcome() - displays a welcome message to teh user upon launch 
     */

    private static void displayWelcome(){
        System.out.println();
        System.out.println("----- Welcome to SmartPantry! -----");
        System.out.println();
        System.out.println("Please enter one of the following commands:");
        displayHelp();
    }

    /**
     *  displayHelp() - prints all commands that the user can enter
     */
    private static void displayHelp(){
        System.out.println("To add a new item, enter \"add item\"");
        System.out.println("To remove an item, enter \"remove item\"");
        System.out.println("To edit an item, enter \"edit item\"");
        System.out.println("To display your pantry, enter \"display pantry\"");
        System.out.println("To check for expired items, enter \"check expired\"");
        System.out.println("To see the commands again, enter \"help\"");
        System.out.println("To quit, enter \"quit\"");
    }

    /**
     *  addPerishable() - prompts the user to input the appropriate data for a
     *                    perishable item, calculates the expiration date, and 
     *                    adds the item to the perishable pantry file
     */
    private static boolean addPerishable(){
        System.out.println("What item are you adding?");
        String n = scan.nextLine();

        System.out.println("How many are you adding?");
        int q = scan.nextInt();
        scan.nextLine();

        System.out.println("Are you freezing this item?");
        String fs = scan.nextLine();
        fs = fs.toLowerCase();
        
        boolean f = false;

        if(fs.equals("yes") || fs.equals("y")){
            fs = "y";
            f = true;
        } else if(fs.equals("no") || fs.equals("n")){
            fs = "n";
            f = false;
        }

        // look up shelf life in the data base
        String expD = calcExpire(n,f);

        String adding = n + "," + q + "," + expD + "," + fs;

        System.out.println("You have just added:");
        System.out.println(adding);
                
 

        DBManager.writeToFile("perishable.txt", adding);
        return true;
    }

    /**
     *  addNonPerishable() - prompts the user to input the appropriate data for a
     *                       nonperishable item, calculates the expiration date, and 
     *                       adds the item to the nonperishable pantry file
     */
    private static boolean addNonPerishable(){

        System.out.println("What item are you adding?");
        String n = scan.nextLine();

        System.out.println("How many are you adding?");
        String q = scan.nextLine();

        String expD = calcExpire(n);

        String adding = n + "," + q + "," + expD;
        DBManager.writeToFile("nonperishable.txt", adding);
 
        return true;
    }

    // ------------------------------------------------------------- REMOVE ITEM STILL ISNT DONE ---------------------------------------------------------------------------

    /**
     *  removeItem() - prompts the user to enter which item they would like to remove 
     *                 from the pantry, scans the pantry for the oldest instance of the
     *                 item and removes it from the pantry
     */
    private static boolean removeItem(){
        System.out.println("Are you removing a perishable item or a nonperishable item?");
        String perish = scan.nextLine();

        System.out.println("What item are you removing?");
        String item = scan.nextLine();

        if(perish.equals("perishable") || perish.equals("p")){
            if(!DBManager.removeFromFile("perishable.txt", item)){
                System.out.println("Sorry! We could not find that item!");
                return false;
            }
        } else if (perish.equals("perishable") || perish.equals("p")){
            if(!DBManager.removeFromFile("nonperishable.txt", item)){
                System.out.println("Sorry! We could not find that item!");
                return false;
            }
        } else{
            System.out.println("Unknown command");
            return false;
        }

        return true;
    }

    // ------------------------------------------------------------- EDIT ITEM STILL ISNT DONE ---------------------------------------------------------------------------

    /**
     *  editItem() - asks the user which item they would like to edit, how they would
     *               like to edit it, and edits the pantry accordingly. Possible edits
     *               include: removing some of an item, taking a perishable item out of
     *               the freezer, and putting a perishable item in the freezer
     */
    private static boolean editItem(){
        System.out.println("You have selected to remove item.");
        return false;
    }

    // ------------------------------------------------------------- DISPLAY PANTRY STILL ISNT DONE ---------------------------------------------------------------------------

    /**
     *  displayPantry() - displays the contents of the pantry to the user. The display is
     *                    seperated between the perishable items and nonperishable items.
     */
    private static boolean displayPantry(){
        System.out.println();
        System.out.println("Perishable items:");

        // print out perishable items

        System.out.println("Nonperishable items:");

        // print mout nonperishable items

        return true;
    }

    /**
     *  calcExpire(item, frozenFlag) - calculates the expiration date of a perishable item 
     *                                 using the shelf life from the database. If the user
     *                                 indicates that they are freezing the item, the  
     *                                 calulated expiration date will adjust according to 
     *                                 much longer the item is expected to last for. Returns
     *                                 a string representing the expiration date in a 
     *                                 MM/DD/YYYY format.
     */
    private static String calcExpire (String item, boolean frozenFlag){
        Calendar expDate = Calendar.getInstance();
        String expString = new String();

        currentDate = Calendar.getInstance();

        String[] itemData = DBManager.getItem("perishable-database.txt", item);
        int sl = Integer.parseInt(itemData[1]);
        Double mul = 1.0;
        if(frozenFlag){
            mul = Double.parseDouble(itemData[2]);
        }
        
        sl = (int)(sl * mul);
        expDate.add(Calendar.DATE, sl);

        int day, month, year;

        day = expDate.get(Calendar.DAY_OF_MONTH);
        month = expDate.get(Calendar.MONTH);
        month = expDate.get(Calendar.MONTH) + 1;
        year = expDate.get(Calendar.YEAR);

        expString = month + "/" + day + "/" + year;

        return expString;
    }

    /**
     *  calcExpire(item) - calculates the expiration of nonperishable items using
     *                     the shelf life from the database. Returns a String that
     *                     represents the expiration date in a MM/DD/YYYY format.
     */
    private static String calcExpire(String item){
        Calendar expDate = Calendar.getInstance(); 
        String expString = new String();

        // update currentDate incase the program hasn't been closed recently
        currentDate = Calendar.getInstance();

        String[] itemData = DBManager.getItem("nonperishable-database.txt", item);

        int sl = Integer.parseInt(itemData[1]);
        
        expDate.add(Calendar.DATE, sl);

        int day, month, year;

        day = expDate.get(Calendar.DAY_OF_MONTH);
        month = expDate.get(Calendar.MONTH);
        month = expDate.get(Calendar.MONTH) + 1;
        year = expDate.get(Calendar.YEAR);

        expString = month + "/" + day + "/" + year;

        return expString;
    }

    // ------------------------------------------------------------- CHECK EXPIRED STILL ISNT DONE ---------------------------------------------------------------------------

    /**
     *  checkExpired() - goes through the pantry files and checks to see if any
     *                   items have expired. If an item is expired it will print 
     *                   a message stating so. If an item is nearing is expiration
     *                   date, it will print a message warning the user to use the
     *                   item soon before it expires
     */
    private static void checkExpired(){
        currentDate = Calendar.getInstance();

        // read each item in the pantry
        // grab the expiration date and compare it to the current date

        // if the expiration date has past, print a message to the user telling them to throw it away
        // if the item is not expired but the number of days left the item has is less than or equal to the number of days, warn the user
        // if nothing is expired or about to expire, print a message saying so
    }
}