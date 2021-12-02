import java.util.*;

class Nonperishable implements Item{
    String name;
    int shelf_life;
    int quantity;
    Calendar exp_date;
    Calendar purch_date;

    public Nonperishable(String n, int sl, int q){
        name = n;
        shelf_life = sl;
        quantity = q;
        exp_date = calcExp(sl);
    }

    // getters
    public String getName(){ return name; }
    public int getShelfLife(){ return shelf_life; }
    public int getQuantity(){ return quantity; }
    public Calendar getExpDate(){ return exp_date; }
    public Calendar getPurchDate(){ return purch_date; } 
    
    // setters
    public void setName(String n){ name = n; }
    public void setShelfLife(int sl){ shelf_life = sl; }
    public void setQuanlity(int q){ quantity = q; }
    public void setExpDate(Calendar d){ exp_date = d; }
    public void setPurchDate(Calendar d){ purch_date = d; }

    private static Calendar calcExp(int sl){
        Calendar ret = Calendar.getInstance();
        return ret;
    }


    public String toString(){
        //String ret;
        //ret = name + " - quantity: " + quantity + ", experation date: " + exp_date.toString();

        String temp_ret;
        temp_ret = name + " - quantity: " + quantity + ", shelf life: " + shelf_life + " days";

        return temp_ret;
    }
}
