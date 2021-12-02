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

    public Calendar calcExp(){
        Calendar ret = Calendar.getInstance();
        return ret;
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
