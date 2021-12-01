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
 * are no lines in the pantry because if we start up with pantry txt
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
 * ---------------------------------------- R E A D  M E ! ! ! ------------------------------------------
 */




import java.io.*;
import java.util.*;

public class SmartPantry{
    static Scanner scan;
    static Calendar currentDate;

    public static void main(String[] args){
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

                System.out.println("Unknown command");

            }
            System.out.println("\nPlease enter another command.");
        }
    }
    
    /*
     *  displayWelcome() - displays a welcome message to the user upon launch 
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

    /**
     *  removeItem() - prompts the user to enter which item they would like to remove 
     *                 from the pantry, scans the pantry for the oldest instance of the
     *                 item and removes it from the pantry
     */
    private static boolean removeItem(){
        System.out.println("Are you removing a perishable item or a nonperishable item?");
        String perish = scan.nextLine();

        if(perish.equals("perishable") || perish.equals("p")){
            System.out.println("What item are you removing?");
            String item = scan.nextLine();

            if(!DBManager.removeFromFile("perishable.txt", item)){
                System.out.println("Sorry! We could not find that item!");
                return false;
            }
        } else if (perish.equals("nonperishable") || perish.equals("n")){
            System.out.println("What item are you removing?");
            String item = scan.nextLine();

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

    /**
     *  editItem() - asks the user which item they would like to edit, how they would
     *               like to edit it, and edits the pantry accordingly. Possible edits
     *               include: removing some of an item, taking a perishable item out of
     *               the freezer, and putting a perishable item in the freezer
     */
    private static boolean editItem(){
        System.out.println("Would you like to edit a perishable item or a nonperishable item?");
        String perish = scan.nextLine();

        if(perish.equals("perishable") || perish.equals("p")){
            System.out.println("editing perishable");
            System.out.println("What item would you like to edit?");
            String i = scan.nextLine();

            // look for the item
            String[] item = DBManager.getItem("perishable.txt", i);
            if(item == null){
                System.out.println("Sorry! We could not find that item!");
                return false;
            }

            System.out.println("You currently have " + item[1] + " " + item[0] + 
                "\nWould you like to update this value?");
            String response = scan.nextLine();

            if(response.equals("yes") || response.equals("y")){
                System.out.println("Please enter the new value");
                String val = scan.nextLine();
                if(val.equals("0")){
                    item[1] = null;

                    // if we get here the user just used the last of the item
                    // there is no need to ask if they want to put it in the freezer
                    DBManager.editItem("perishable.txt", item);
                    return true;
                } else{
                    item[1] = val;
                }
            }

            // ask if the user wants to remove the item from the freezer or put it in
            if(item[3].equals("y")){
                System.out.println("This item is currently in the freezer, would you like to remove it?");
                response = scan.nextLine();
                if(response.equals("yes") || response.equals("y")){
                    item[3] = "n";

                    // change expiration date to tomorrow
                    Calendar tempDate = Calendar.getInstance();
                    tempDate.add(Calendar.DAY_OF_MONTH,1);
                    item[2] = toString(tempDate);
                }
            } else{
                System.out.println("This item is currently not in the freezer," + 
                    " would you like to put it into the freezer?");
                response = scan.nextLine();
                if(response.equals("yes") || response.equals("y")){
                    item[3] = "y";

                    // find the new experation date based on the remaining shelf life and
                    // the item's freezer multiplier
                    Calendar expDate = toCalendar(item[2]);
                    currentDate = Calendar.getInstance();

                    int daysLeft = daysUntil(currentDate, expDate);
                    String[] itemData = DBManager.getItem("perishable-database.txt", item[0]);
                    double mul = Double.parseDouble(itemData[2]);
                    daysLeft = (int)(daysLeft * mul);

                    expDate.add(Calendar.DAY_OF_MONTH, daysLeft);
                    item[2] = toString(expDate);
                }
            }

            DBManager.editItem("perishable.txt", item);

        } else if(perish.equals("nonperishable") || perish.equals("n")){
            System.out.println("What item would you like to edit?");
            String i = scan.nextLine();

            // look for the item
            String[] item = DBManager.getItem("nonperishable.txt", i);
            if(item == null){
                System.out.println("Sorry! We could not find that item!");
                return false;
            }

            System.out.println("You currently have " + item[1] + " " + item[0] + 
                "\nWould you like to update this value?");
            String response = scan.nextLine();

            if(response.equals("yes") || response.equals("y")){
                System.out.println("Please enter the new value");
                String val = scan.nextLine();
                if(val.equals("0")){
                    item[1] = null;
                } else{
                    item[1] = val;
                }
            }

            DBManager.editItem("nonperishable.txt", item);

        } else{
            System.out.println("Unknown command");
            return false;
        }

        return true;
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
        
        expDate.add(Calendar.DAY_OF_MONTH, sl);

        int day, month, year;

        day = expDate.get(Calendar.DAY_OF_MONTH);
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
        try{
            BufferedReader readerP = new BufferedReader (new FileReader("perishable.txt"));
            BufferedReader readerNP = new BufferedReader (new FileReader("nonperishable.txt"));
            String currLine;
            try{
                while ((currLine = readerP.readLine()) != null){
                    String[] currArray = currLine.split(",");
                    Calendar date = toCalendar(currArray[1]);
                    int dayRemaining = daysUntil(currentDate, date);
                    if (dayRemaining < 0){
                        String printString = currArray[0] + " has expired.";
                        System.out.print(printString);
                    }
                    else if (dayRemaining ==0){
                        String printString = currArray[0] + " expires today.";
                        System.out.print(printString);
                    }
                    else if (dayRemaining <= 2){
                        String printString = currArray[1] + " " + currArray[0] + " has " + dayRemaining + " days left."; 
                        System.out.print(printString);
                    }
                }
                while ((currLine = readerNP.readLine())!= null){
                    String[] currArray = currLine.split(",");
                    Calendar date = toCalendar(currArray[1]);
                    int dayRemaining = daysUntil(currentDate, date);
                    if (dayRemaining < 0){
                        String printString = currArray[0] + " has expired.";
                        System.out.print(printString);
                    }
                    else if (dayRemaining ==0){
                        String printString = currArray[0] + " expires today.";
                        System.out.print(printString);
                    }
                    else if (dayRemaining <= 2){
                        String printString = currArray[1] + " " + currArray[0] + " has " + dayRemaining + " days left."; 
                        System.out.print(printString);
                    }
                }
            }
            catch(IOException e){
                e.getStackTrace();
            }
        }catch(FileNotFoundException e){
            e.getStackTrace();
        }
        // read each item in the pantry
        // grab the expiration date and compare it to the current date

        // if the expiration date has past, print a message to the user telling them to throw it away
        // if the item is not expired but the number of days left the item has is less than or equal to the number of days, warn the user
        // if nothing is expired or about to expire, print a message saying so
    }

    /**
     *  toCalender(d) - takes in a string d that holds a date in the format
     *                  "MM/DD/YYYY" and creates a Calender object with the
     *                  same date for the sake of doing date arithmetic with
     *                  the calender objects
     */

    private static Calendar toCalendar(String d){
        Calendar date = Calendar.getInstance();

        String[] dateInfo = d.split("/");

        int day, month, year;

        month = Integer.parseInt(dateInfo[0]) - 1;
        day = Integer.parseInt(dateInfo[1]);
        year = Integer.parseInt(dateInfo[2]);

        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
        date.set(Calendar.YEAR, year);

        return date;
    }

    /**
     *  toString(c) - takes in a calender object and creates a string of the
     *                format "MM/DD/YYYY" with the same date for the sake of 
     *                writing the date to the pantry files
     */
    private static String toString(Calendar c){
        String date;
        int month, day, year;

        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);
        year = c.get(Calendar.YEAR);

        date = month + "/" + day + "/" + year;

        return date;
    }

    /**
     *  daysUntil(startDate, endDate) - finds the number of days between the 
     *                                  Calenders startDate and endDate. If
     *                                  the end date occurs before the startDate
     *                                  the returned number of days will be negative
     */
    private static int daysUntil(Calendar startDate, Calendar endDate){
        int days;

        long startDateMilli = startDate.getTimeInMillis();
        long endDateMilli = endDate.getTimeInMillis();

        days = (int) (endDateMilli - startDateMilli);
        days =  (days / (1000*60*60*24));

        return days;
    }
}