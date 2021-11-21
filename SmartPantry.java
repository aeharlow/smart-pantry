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

            if(command.equals("add item")){
                System.out.println("You have selected to add new item.");
            } else if (command.equals("remove item")){
                System.out.println("You have selected to remove item.");
            } else if (command.equals("edit item")){
                System.out.println("You have selected to edit an item.");
            } else if(command.equals("display pantry")){
                System.out.println("You have selected to display the pantry.");
            } else if(command.equals("help")){
                System.out.println();
                displayHelp();
            } else if(command.equals("quit")){
                cont = false;
            } else{
                System.out.println("Bad command! Please enter another command.");
            }
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
}

// add item, remove item, edit item, check inventory

// we want to sort the items in order from soonest exp date 