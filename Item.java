import java.util.*;

abstract class Item{
    private String name;
    private int shelf_life;
    private int quantity;
    private Date exp_date;
    private Date purch_date;

    public Item(String n, int sl, int q){
        name = n;
        shelf_life = sl;
        quantity = q;
        exp_date = calcExp(sl);
        // grab current date for purch_date
    }

    private Date calcExp(int shelf_life){
        // uhhhh
        return null;
    }

    // getters
    public String getName(){ return name; }
    public int getShelfLife(){ return shelf_life; }
    public int getQuantity(){ return quantity; }
    public Date getExpDate(){ return exp_date; }
    public Date getPurchDate(){ return purch_date; } 

    public void remind(){
        return;
    }

    
}