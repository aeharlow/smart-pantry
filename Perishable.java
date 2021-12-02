import java.util.*;

class Perishable implements Item{
    String name;
    int shelf_life;
    int quantity;
    Calendar exp_date;
    Calendar purch_date;
    boolean is_frozen;
    
    public Perishable(String n, int sl, int q, boolean f){
        name = n;
        shelf_life = sl;
        quantity = q;
        exp_date = calcExp(sl);
        is_frozen = f;
    }

    // getters
    public String getName(){ return name; }
    public int getShelfLife(){ return shelf_life; }
    public int getQuantity(){ return quantity; }
    public Calendar getExpDate(){ return exp_date; }
    public Calendar getPurchDate(){ return purch_date; } 
    public boolean getIsFrozen(){ return is_frozen; }

    // setters
    public void setName(String n){ name = n; }
    public void setShelfLife(int sl){ shelf_life = sl; }
    public void setQuanlity(int q){ quantity = q; }
    public void setExpDate(Calendar d){ exp_date = d; }
    public void setPurchDate(Calendar d){ purch_date = d; }
    public void getIsFrozen(boolean f){ is_frozen = f;}

    public Calendar calcExp(int sl){
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
        temp_ret = getName() + " - quantity: " + getQuantity() + ", shelf life: " + 
            getShelfLife() + "days, frozen: " + f;

        return temp_ret;
    }
}
