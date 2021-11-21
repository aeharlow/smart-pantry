import java.util.*;

public class SmartPantry {
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {

        displayWelcome();

        String command = new String();
        Boolean cont = true;

        do{
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

                editItem();;

            } else if(command.equals("display pantry")){

               displayPantry();

            } else if(command.equals("help") || command.equals("h")){

                System.out.println();
                displayHelp();

            } else if(command.equals("quit") || command.equals("q")){

                cont = false;

            } else{

                System.out.println("Bad command!");

            }

            System.out.println("Please enter another command.");
        } while(cont);
        
    }
    
    public static void displayWelcome(){
        System.out.println("Welcome to SmartPantry!");
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
        System.out.println("adding perishable");
        return false;
    }

    public static boolean addNonPerishable(){
        System.out.println("adding perishable");
        return false;
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
        System.out.println("You have selected to display the pantry.");
        return false;
    }
}

// we want to sort the items in order from soonest exp date 