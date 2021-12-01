import java.io.*;

public class DBManager {

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
    
    public static String[] getItem (String fileName, String item){
        File inputFile = new File (fileName);
        String[] returnArray;
        String currLine;
        try{
            BufferedReader reader = new BufferedReader (new FileReader(inputFile));
            while ((currLine = reader.readLine())!= null){
                String trimmed = currLine.trim();
                returnArray = trimmed.split(",");
                if (returnArray[0].equals(item)){
                    reader.close();
                    return returnArray;
                } 
            }
            reader.close();
        }
        catch(Exception e){
            e.getStackTrace();
        }
        return null;
    }

    public static boolean writeToFile (String fileName, String toWrite){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true));
            writer.write(toWrite);
            writer.newLine();
            writer.close();
            return true;
        }
        catch(Exception e){
            e.getStackTrace();
        }
        return false;
    }

    public static boolean removeFromFile (String fileName, String itemToRemove){
        File inputFile = new File(fileName);
        File tempFile = new File ("temp.txt");
        boolean foundFlag = false;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currLine;
            while ((currLine = reader.readLine()) != null){
                String trimmed = currLine.trim();
                String currItem = trimmed.split(",")[0];
                if (currItem.equals(itemToRemove)) {
                    //This if statement makes sure that we only delete the first instance of the item.
                    if(foundFlag == false){
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
        }
        catch(Exception e){
            e.getStackTrace();
        }
        tempFile.renameTo(inputFile);
        if (foundFlag) return true;
        else return false;
    }

    public static boolean editItem (String fileName, String[] itemArray){
        File inputFile = new File (fileName);
        File tempFile = new File ("tempEdit.txt");
        boolean foundFlag = false;
        String[] currArray;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currLine;
            while ((currLine = reader.readLine()) != null){
                String trimmed = currLine.trim();
                currArray = trimmed.split(",");
                if (currArray[0].equals(itemArray[0])) {
                    //This if statement makes sure that we only delete the first instance of the item.
                    if(foundFlag == false){
                        foundFlag = true;
                        //If the edit item has 0 current item, remove the item.
                        if (itemArray[1] == null){
                            continue;
                        }
                        if (fileName.equals("perishable.txt")){
                            String curr = itemArray[0] + "," + itemArray[1] + "," + itemArray[2] + "," + itemArray[3];
                            writer.write(curr + System.getProperty("line.separator"));
                            continue;
                        }else{
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
        }
        catch(Exception e){
            e.getStackTrace();
        }
        tempFile.renameTo(inputFile);
        if (foundFlag) return true;
        else return false;
    }

    public static boolean clearDB (String fileName){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
            writer.write("");
            writer.close();
            return true;
        }
        catch(IOException e){
            e.getStackTrace();
        }
        return false;
    } 

}
