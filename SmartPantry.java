import java.util.*;
import java.util.spi.CalendarDataProvider;
import java.io.*;

public class SmartPantry {
    static Scanner scan;
    static Perishable[] perishItems;
    static Nonperishable[] nonperishItems;
    static int numPerish;
    static int numNonperish;
    static Calendar currentDate;
    public static void main(String[] args) {
        
        scan = new Scanner(System.in);
        perishItems = new Perishable[15];
        nonperishItems = new Nonperishable[15];
        numPerish = 0;
        numNonperish = 0;
        currentDate = Calendar.getInstance();

        // check the current date upon start up and run a check 
        // to see if anything has expired since the last boot up

        // if anything is expired display that it has exipred

        // if anytihng is close to expiring
        // print a warning for the user letting them know
        // we have to decide on the threshold for when to warn the user

        displayWelcome();

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
    
    public static void displayWelcome(){
        System.out.println();
        System.out.println("----- Welcome to SmartPantry! -----");
        System.out.println();
        System.out.println("Please enter one of the following commands:");
        displayHelp();
    }

    public static void displayHelp(){
        System.out.println("To add a new item, enter \"add item\"");
        System.out.println("To remove an item, enter \"remove item\"");
        System.out.println("To edit an item, enter \"edit item\"");
        System.out.println("To display your pantry, enter \"display pantry\"");
        System.out.println("To see the commands again, enter \"help\"");
        System.out.println("To quit, enter \"quit\"");
    }

    public static boolean addPerishable(){
        if(numPerish >= 15){
            System.out.println("Not enough room!");
            return false;
        }

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
            f = true;
        } else if(fs.equals("no") || fs.equals("n")){
            f = false;
        }

        // look up shelf life in the data base
        int sl = 5;

        Perishable p = new Perishable(n, sl, q, f);

        System.out.println("You have just added:");
        System.out.println(p.toString());
                
 
        String adding = n + "," + sl + "," + q + "," + f;
        DBManager.writeToFile("perishable.txt", adding);

        perishItems[numPerish] = p;
        numPerish++;  
        return true;
    }

    public static boolean addNonPerishable(){

        if(numNonperish >= 15){
            System.out.println("Not enough room!");
            return false;
        }

        System.out.println("What item are you adding?");
        String n = scan.nextLine();

        System.out.println("How many are you adding?");
        int q = scan.nextInt();
        scan.nextLine();

        // look up shelf life in the data base

        int sl = 5;

        Nonperishable np = new Nonperishable(n, sl, q);

        System.out.println("You have just added:");

        System.out.println(np.toString());

        nonperishItems[numNonperish] = np;
        numNonperish++;  
        return true;
    }

    public static boolean removeItem(){
        System.out.println("You have selected to remove item.");
        return false;
    }

    public static boolean editItem(){
        System.out.println("You have selected to remove item.");
        return false;
    }

    public static boolean displayPantry(){
        System.out.println();
        System.out.println("Perishable items:");

        for(int i = 0; i < numPerish; i++){
            System.out.println(perishItems[i].toString());
        }
        System.out.println();

        System.out.println("Nonperishable items:");

        for(int i = 0; i < numNonperish; i++){
            System.out.println(nonperishItems[i].toString());
        }

        return true;
    }
}

/*********************************************************************
    
    reference for working with calender

        currentDate = Calendar.getInstance();

        int day, month, year;

        day = currentDate.get(Calendar.DAY_OF_MONTH);
        month = currentDate.get(Calendar.MONTH);
        year = currentDate.get(Calendar.YEAR);

        System.out.println();
        //System.out.println(currentDate.toString());
        System.out.println("day: " + day);
        System.out.println("month: " + month);
        System.out.println("year: " + year);


        Calendar temp = Calendar.getInstance();

        temp.set(2001, 5, 1);

        day = temp.get(Calendar.DAY_OF_MONTH);
        month = temp.get(Calendar.MONTH);
        year = temp.get(Calendar.YEAR);

        System.out.println();
        //System.out.println(currentDate.toString());
        System.out.println("day: " + day);
        System.out.println("month: " + month);
        System.out.println("year: " + year);


        temp.add(Calendar.DATE, -15); 

        day = temp.get(Calendar.DAY_OF_MONTH);
        month = temp.get(Calendar.MONTH);
        year = temp.get(Calendar.YEAR);

        System.out.println();
        //System.out.println(currentDate.toString());
        System.out.println("day: " + day);
        System.out.println("month: " + month);
        System.out.println("year: " + year);

        temp.add(Calendar.DATE, 30); 

        day = temp.get(Calendar.DAY_OF_MONTH);
        month = temp.get(Calendar.MONTH);
        year = temp.get(Calendar.YEAR);

        System.out.println();
        //System.out.println(currentDate.toString());
        System.out.println("day: " + day);
        System.out.println("month: " + month);
        System.out.println("year: " + year);

        temp.add(Calendar.DATE, 365); 

        day = temp.get(Calendar.DAY_OF_MONTH);
        month = temp.get(Calendar.MONTH);
        year = temp.get(Calendar.YEAR);

        System.out.println();
        //System.out.println(currentDate.toString());
        System.out.println("day: " + day);
        System.out.println("month: " + month);
        System.out.println("year: " + year);    


 *********************************************************************/