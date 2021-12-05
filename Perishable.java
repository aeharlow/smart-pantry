import java.util.*;

class Perishable implements Item {
    private String name;
    private int quantity;
    private Calendar expDate;
    private boolean isFrozen;

    /**
     * Creates a Perishable object
     * @param n String representing the name of the item
     * @param q int representing the quantity of the item
     * @param f boolean representing if the user is freezing the item
     */
    public Perishable(String n, int q, boolean f) {
        name = n;
        quantity = q;
        isFrozen = f;
        expDate = calcExp();
    }

    /**
     * @return returns a String containing the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * @return returns an int containing the quantity of the item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return returns a Calendar representing the expiration 
     * date of the item
     */
    public Calendar getExpDate() {
        return expDate;
    }

    /**
     * @return returns a boolean representing if the item is frozen
     */
    public boolean getIsFrozen() {
        return isFrozen;
    }

    /**
     * @return returns a double containing the frozen multiplier
     * of the item
     */
    public double getMult() {
        String[] itemData = DBManager.getItem("perishable-database.txt", name);
        Double mult = Double.parseDouble(itemData[2]);
        return mult;
    }

    /**
     * Sets the name of an item
     * @param n New name of the item
     */
    public void setName(String n) {
        name = n;
    }

    /**
     * Sets the quantity of an item
     * @param q New quantity of the item
     */
    public void setQuantity(int q) {
        quantity = q;
    }

    /**
     * Sets the expiration date of an item
     * @param d New expiration of the item, Java Calendar
     */
    public void setExpire(Calendar d) {
        expDate = d;
    }

    /**
     * Sets the frozen flag of an item
     * @param n New frozen status of the item
     */
    public void setIsFrozen(boolean f) {
        isFrozen = f;
    }

    /**
     * Searches the perishable database for a specific item
     * @param name String representing the item to search the databases for.
     * @return returns true if the item exists in the database.
     */
    public static boolean searchDatabase(String name){
        boolean ret = false;
        String[] itemData = DBManager.getItem("perishable-database.txt", name);

        if(itemData != null){
            ret = true;
        }

        return ret;
    }

    /**
     * Calculates the expiration date of perishable items using the shelf 
     * life from the database. If the user indicates that they are freezing 
     * the item, the calulated expiration date will adjust according to how
     * much longer the item is expected to last for.
     * @return returns a Calender representing the expected expiration date
     * of the item
     */
    public Calendar calcExp() {
        Calendar expDate = Calendar.getInstance();

        String[] itemData = DBManager.getItem("perishable-database.txt", name);

        int sl = Integer.parseInt(itemData[1]);
        Double mul = 1.0;
        if (isFrozen) {
            mul = getMult();
        }

        sl = (int) (sl * mul);
        expDate.add(Calendar.DATE, sl);

        return expDate;
    }

    /**
     * Creates a string containing all relevant information about 
     * an item in the appropriate format to be written to the pantry file.
     * @return returns a String in the proper format for the txt files
     */
    public String toDBString() {
        String exp = calToStr(expDate);

        String f;
        if (isFrozen) {
            f = "y";
        } else {
            f = "n";
        }

        String ret = name + "," + quantity + "," + exp + "," + f;
        return ret;
    }

    /**
     * Creates a string of the format "MM/DD/YYYY" with the same 
     * date as the passed in Calendarfor the sake of writing the
     * date to the pantry files
     * @param c the Calendar object to be converted to a string
     * @return returns the String version of the Calendar
     */
    private static String calToStr(Calendar c) {
        String date;
        int month, day, year;

        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);
        year = c.get(Calendar.YEAR);

        date = month + "/" + day + "/" + year;

        return date;
    }

    /**
     * Prints out the information about a nonperishable item in a readable format
     */
    public String toString() {
        String f = new String();
        if (isFrozen)
            f = "yes";
        else
            f = "no";

        String ret = new String();
        ;
        ret = getName() + " - quantity: " + getQuantity() + ", expiration date: " +
                calToStr(expDate) + ", frozen: " + f;

        return ret;
    }
}

class sortByExpP implements Comparator<Perishable> {

    // Method
    // Sorting in ascending order of roll number
    
    @Override public int compare(Perishable a, Perishable b) {

        // let's sort the employee based on an id in ascending order
        // returns a negative integer, zero, or a positive integer as this perishable
        // exp date
        // is less than, equal to, or greater than the specified object.
        
    

        if (a.getExpDate().after(b.getExpDate())) {
            return 1;
        } else if (a.getExpDate().before(b.getExpDate())) {
            return -1;
        } else {
            return 0;
        }

    }
}
