import java.io.*;
import java.util.LinkedList;

public class DBManager {

    // This function takes as input a string filename.
    // the function will go to the file and print out the data
    // in a User Friendly format, the format differs depening on what kind of
    // file is being read (perishable or nonPerishable have different formats)

    public static void printItem(String fileName) {

        File inputFile = new File(fileName);
        String[] retArray;
        String currLine;

        if (fileName.equals("nonperishable-database.txt")) { // the file contains nonP items and should be printed a
                                                             // specific way

            String frozenVar;
            System.out.println("Here's a list of your non perishable items:  ");
            try {
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                while ((currLine = reader.readLine()) != null) {
                    String trimmed = currLine.trim();
                    retArray = trimmed.split(",");
                    if (retArray[3].equals("no")) {
                        frozenVar = "Not Frozen.";
                    } else {
                        frozenVar = "Frozen.";
                    }

                    System.out.println(retArray[0] + " - Quantity: " + retArray[1] + ", Exp. Date: " + retArray[2]
                            + ", Days Left: ADD FUNC" + ", " + frozenVar);

                }
                reader.close();
            } catch (Exception e) {
                e.getStackTrace();
            }

        } else {
            System.out.println("Here's a list of your perishable items: ");
            try {
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                while ((currLine = reader.readLine()) != null) {
                    String trimmed = currLine.trim();
                    retArray = trimmed.split(",");

                    System.out.println(retArray[0] + " - Quantity: " + retArray[1] + ", Exp. Date: " + retArray[2]
                            + ", Days Left: ADD FUNC" + ".");

                }
                reader.close();
            } catch (Exception e) {
                e.getStackTrace();
            }

        }

    }

    public static String[] getItem(String fileName, String item) {
        File inputFile = new File(fileName);
        String[] returnArray;
        String currLine;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            while ((currLine = reader.readLine()) != null) {
                returnArray = currLine.split(",");
                if (returnArray[0].equals(item)) {
                    reader.close();
                    return returnArray;
                }
            }
            reader.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    public static boolean writeToFile(String fileName, String toWrite) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.write(toWrite);
            writer.newLine();
            writer.close();
            return true;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return false;
    }

    public static boolean removeFromFile(String fileName, String itemToRemove) {
        File inputFile = new File(fileName);
        File tempFile = new File("temp.txt");
        boolean foundFlag = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currLine;
            while ((currLine = reader.readLine()) != null) {
                String trimmed = currLine.trim();
                String currItem = trimmed.split(",")[0];
                if (currItem.equals(itemToRemove)) {
                    // This if statement makes sure that we only delete the first instance of the
                    // item.
                    if (foundFlag == false) {
                        foundFlag = true;
                        continue;
                    }
                    writer.write(currLine + System.getProperty("line.separator"));
                    continue;
                }
                writer.write(currLine + System.getProperty("line.separator"));
            }
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
        tempFile.renameTo(inputFile);
        if (foundFlag)
            return true;
        else
            return false;
    }

    public static boolean editItem(String fileName, String[] itemArray) {
        File inputFile = new File(fileName);
        File tempFile = new File("tempEdit.txt");
        boolean foundFlag = false;
        String[] currArray;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currLine;
            while ((currLine = reader.readLine()) != null) {
                String trimmed = currLine.trim();
                currArray = trimmed.split(",");
                if (currArray[0].equals(itemArray[0])) {
                    // This if statement makes sure that we only delete the first instance of the
                    // item.
                    if (foundFlag == false) {
                        foundFlag = true;
                        // If the edit item has 0 current item, remove the item.
                        if (itemArray[1] == null) {
                            continue;
                        }
                        if (fileName.equals("perishable.txt")) {
                            String curr = itemArray[0] + "," + itemArray[1] + "," + itemArray[2] + "," + itemArray[3];
                            writer.write(curr + System.getProperty("line.separator"));
                            continue;
                        } else {
                            String curr = itemArray[0] + "," + itemArray[1] + "," + itemArray[2];
                            writer.write(curr + System.getProperty("line.separator"));
                            continue;
                        }
                    }
                    writer.write(currLine + System.getProperty("line.separator"));
                    continue;
                }
                writer.write(currLine + System.getProperty("line.separator"));
            }
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
        tempFile.renameTo(inputFile);
        if (foundFlag)
            return true;
        else
            return false;
    }

    public static boolean clearDB(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
            writer.write("");
            writer.close();
            return true;
        } catch (IOException e) {
            e.getStackTrace();
        }
        return false;
    }

    public static LinkedList<Perishable> createPeriArray (){
        LinkedList<Perishable> creation = new LinkedList<Perishable>();
        try{
            BufferedReader reader = new BufferedReader (new FileReader("perishable.txt"));
            String currLine;
            try{
                while ((currLine = reader.readLine()) != null){
                    String[] itemArray = currLine.split(",");
                    String name = itemArray[0];
                    int num = Integer.parseInt(itemArray[1]);
                    Boolean isFrozen;
                    if (itemArray[3].equals("y")){
                        isFrozen = true;
                    }
                    else{
                        isFrozen = false;
                    }
                    Perishable itemToAdd = new Perishable (name,num,isFrozen);
                    creation.add(itemToAdd);
                }
                reader.close();
            }catch(IOException e){
                e.getStackTrace();
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found when trying to create instance array!");
        }
        return creation;
    }

    public static LinkedList<Nonperishable> createNPArray (){
        LinkedList<Nonperishable> creation = new LinkedList<Nonperishable>();
        try{
            BufferedReader reader = new BufferedReader (new FileReader("nonperishable.txt"));
            String currLine;
            try{
                while ((currLine = reader.readLine()) != null){
                    String[] itemArray = currLine.split(",");
                    String name = itemArray[0];
                    int num = Integer.parseInt(itemArray[1]);
                    Nonperishable itemToAdd = new Nonperishable (name,num);
                    creation.add(itemToAdd);
                }
                reader.close();
            }catch(IOException e){
                e.getStackTrace();
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found when trying to create instance array!");
        }
        return creation;
    }

    public static void arrayToDB (LinkedList<Perishable> periList,LinkedList<Nonperishable> npList ){
        Item currItem;
        if (periList != null){
            try{
                BufferedWriter writerP = new BufferedWriter(new FileWriter("perishable.txt", false));
                for(int i = 0; i<periList.size(); i++){
                    currItem = periList.pop();
                    String stringToWrite = currItem.toDBString();
                    writerP.write(stringToWrite + System.getProperty("line.separator"));

                }
                writerP.close();
            }
            catch(IOException e){
                e.getStackTrace();
            }
        }
        if(npList != null){
            try{
                BufferedWriter writerNP = new BufferedWriter(new FileWriter("nonperishable.txt", false));
                for(int i = 0; i<npList.size(); i++){
                    currItem = npList.pop();
                    String stringToWrite = currItem.toDBString();
                    writerNP.write(stringToWrite + System.getProperty("line.separator"));
                }
                writerNP.close();
            }
            catch(IOException e){
                e.getStackTrace();
            }
        }
    }

}
