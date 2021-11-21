import java.util.*;

abstract class Item{
    private String name;
    private String type;
    private int shelf_life;
    private int quantity;
    private Date exp_date;
    private Date purch_date;

    public Item(String n, String t, int sl, int q){
        name = n;
        type = t;
        shelf_life = sl;
        quantity = q;
        exp_date = calcExp(sl);
        purch_date = new Date(); // according to stackOverflow this initalizes with current date
    }

    private Date calcExp(int shelf_life){
        // we add the shelf life to the purch_date to get expected exp date
        return null;
    }

    // getters
    public String getName(){ return name; }
    public String getType(){ return type; }
    public int getShelfLife(){ return shelf_life; }
    public int getQuantity(){ return quantity; }
    public Date getExpDate(){ return exp_date; }
    public Date getPurchDate(){ return purch_date; } 

    // setters
    public void setName(String n){ name = n; }
    public void setType(String c){ type = c; }
    public void setShelfLife(int sl){ shelf_life = sl; }
    public void setQuanlity(int q){ quantity = q; }
    public void setExpDate(Date d){ exp_date = d; }
    public void setPurchDate(Date d){ purch_date = d; }

    public void remind(){
        return;
    }

    public String toString(){
        //String ret;
        //ret = name + " - quantity: " + quantity + ", experation date: " + exp_date.toString();

        String temp_ret;
        temp_ret = name + " - quantity: " + quantity + ", shelf life: " + shelf_life + " days";

        return temp_ret;
    }
}