import java.time.Period;
import java.util.*;

class Perishable implements Item {
    private String name;
    private int quantity;
    private Calendar expDate;
    private boolean isFrozen;

    public Perishable(String n, int q, boolean f) {
        name = n;
        quantity = q;
        expDate = calcExp();
        isFrozen = f;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Calendar getExpDate() {
        return expDate;
    }

    public boolean getIsFrozen() {
        return isFrozen;
    }

    public double getMult() {
        String[] itemData = DBManager.getItem("perishable-database.txt", name);
        Double mult = Double.parseDouble(itemData[2]);
        return mult;
    }

    // setters
    public void setName(String n) {
        name = n;
    }

    public void setQuantity(int q) {
        quantity = q;
    }

    public void setExpire(Calendar d) {
        expDate = d;
    }

    public void setIsFrozen(boolean f) {
        isFrozen = f;
    }

    /**
     * calcExpire() - calculates the expiration date of a perishable item
     * using the shelf life from the database. If the user
     * indicates that they are freezing the item, the
     * calulated expiration date will adjust according to
     * much longer the item is expected to last for.
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
     *  toDBString() - creates a string containing information about the
     *                 object in the correct format for the pantry txt files
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
     * toString(c) - takes in a calender object and creates a string of the
     * format "MM/DD/YYYY" with the same date for the sake of
     * writing the date to the pantry files
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

    public String toString() {
        String f = new String();
        if (isFrozen)
            f = "yes";
        else
            f = "no";

        String ret = new String();
        ;
        ret = getName() + " - quantity: " + getQuantity() + ", experation date: " +
                calToStr(expDate) + ", frozen: " + f;

        return ret;
    }
}

class sortByExpP implements Comparator<Perishable> {

    // Method
    // Sorting in ascending order of roll number
    public int compare(Perishable a, Perishable b) {

        // let's sort the employee based on an id in ascending order
        // returns a negative integer, zero, or a positive integer as this perishable
        // exp date
        // is less than, equal to, or greater than the specified object.
        if (a.getExpDate().after(b)) {
            return 1;
        } else if (a.getExpDate().before(b)) {
            return -1;
        } else {
            return 0;
        }

    }
}
