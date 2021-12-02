import java.util.*;

class Nonperishable implements Item{
    String name;
    int quantity;
    Calendar exp_date;

    public Nonperishable(String n, int q){
        name = n;
        quantity = q;
        exp_date = calcExp();
    }

    // getters
    public String getName(){ return name; }
    public int getQuantity(){ return quantity; }
    public Calendar getExpDate(){ return exp_date; }
    
    // setters
    public void setName(String n){ name = n; }
    public void setQuanlity(int q){ quantity = q; }
    public void setExpire(Calendar d){ exp_date = d; }

    public Calendar calcExp(){
        Calendar ret = Calendar.getInstance();
        return ret;
    }

    public void edit(){
        return;
    }

    public String toString(){
        //String ret;
        //ret = name + " - quantity: " + quantity + ", experation date: " + exp_date.toString();

        String temp_ret;
        temp_ret = name + " - quantity: " + quantity;

        // add exp date

        return temp_ret;
    }
}
