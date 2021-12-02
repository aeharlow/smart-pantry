/**
 *  
 */

import java.util.*;

interface Item{
    // getters
    public String getName();
    public int getShelfLife();
    public int getQuantity();
    public Calendar getExpDate();

    // setters
    public void setName(String n);
    public void setShelfLife(int sl);
    public void setQuanlity(int q);

    public Calendar calcExp(int shelf_life);
    public void edit();
    public String toString();
}