import java.util.*;

class Nonperishable implements Item {
    private String name;
    private int quantity;
    private Calendar expDate;

    public Nonperishable(String n, int q) {
        name = n;
        quantity = q;
        expDate = calcExp();
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

    public int compareTo(Item perish) {
        // let's sort the employee based on an id in ascending order
        // returns a negative integer, zero, or a positive integer as this perishable
        // exp date
        // is less than, equal to, or greater than the specified object.
        if (this.expDate.after(perish)) {
            return 1;
        } else if (this.expDate.before(perish)) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * calcExpire(item) - calculates the expiration of nonperishable items
     * using the shelf life from the database.
     */
    public Calendar calcExp() {
        Calendar expDate = Calendar.getInstance();

        String[] itemData = DBManager.getItem("nonperishable-database.txt", name);
        int sl = Integer.parseInt(itemData[1]);

        expDate.add(Calendar.DAY_OF_MONTH, sl);
        return expDate;
    }

    public String toDBString() {
        String exp = calToStr(expDate);
        String ret = name + "," + quantity + "," + exp;
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
        String ret;
        ret = name + " - quantity: " + quantity + ", experation date: " + calToStr(expDate);
        return ret;
    }
}

class sortByExpNP implements Comparator<Nonperishable> {

    // Method
    // Sorting in ascending order of roll number
    public int compare(Nonperishable a, Nonperishable b) {

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
