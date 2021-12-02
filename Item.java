/**
 *  
 */

import java.util.*;

interface Item{
    // getters
    public String getName();
    public int getQuantity();
    public Calendar getExpDate();

    // setters
    public void setName(String n);
    public void setQuanlity(int q);
    public void setExpire(Calendar e);

    public Calendar calcExp();
    public void edit();
    public String toString();
}