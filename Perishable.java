import java.util.*;

class Perishable implements Item{
    String name;
    int quantity;
    Calendar exp_date;
    boolean is_frozen;
    
    public Perishable(String n, int q, boolean f){
        name = n;
        quantity = q;
        exp_date = calcExp();
        is_frozen = f;
    }

    // getters
    public String getName(){ return name; }
    public int getQuantity(){ return quantity; }
    public Calendar getExpDate(){ return exp_date; }
    public boolean getIsFrozen(){ return is_frozen; }

    // setters
    public void setName(String n){ name = n; }
    public void setQuanlity(int q){ quantity = q; }
    public void setExpire(Calendar d){ exp_date = d; }
    public void getIsFrozen(boolean f){ is_frozen = f;}

     /**
     * calcExpire() - calculates the expiration date of a perishable item
     *                using the shelf life from the database. If the user
     *                indicates that they are freezing the item, the
     *                calulated expiration date will adjust according to
     *                much longer the item is expected to last for. 
     */
    public Calendar calcExp(){
        Calendar expDate = Calendar.getInstance();

        String[] itemData = DBManager.getItem("perishable-database.txt", name);
        int sl = Integer.parseInt(itemData[1]);
        Double mul = 1.0;
        if(is_frozen) {
            mul = Double.parseDouble(itemData[2]);
        }

        sl = (int) (sl * mul);
        expDate.add(Calendar.DATE, sl);

        return expDate;
    }

    public void edit(){
        return;
    }

    public String toString(){
        String f = new String();
        if(is_frozen)
            f = "yes";
        else
            f = "no";

        // String ret = new String();;
        // ret = getName() + " - quantity: " + getQuantity() + ", experation date: " + 
        //     getExpDate().toString() + ", frozen: " + f;

        String temp_ret = new String();;
        temp_ret = getName() + " - quantity: " + getQuantity() + ", frozen: " + f;

        //add exp date

        return temp_ret;
    }
}
