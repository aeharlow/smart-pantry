import java.io.*;
import java.util.Calendar;
import java.util.LinkedList;

public class DBManager {

 
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
                    Calendar expDate = toCalendar(itemArray[2]);
                    Perishable itemToAdd = new Perishable (name,num,isFrozen);
                    itemToAdd.setExpire(expDate);
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
                    Calendar expDate = toCalendar(itemArray[2]);
                    itemToAdd.setExpire(expDate);
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
                    currItem = periList.get(i);
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
                    currItem = npList.get(i);
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
    private static Calendar toCalendar(String d) {
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


}
