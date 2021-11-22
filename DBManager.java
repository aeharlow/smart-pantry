import java.util.*;
import java.io.*;

public class DBManager {
    
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
        File inputFile = new File (toWrite);
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
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
        File inputFile = new File (fileName);
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
}
