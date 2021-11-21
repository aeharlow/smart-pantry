class Perishable extends Item{
    boolean is_frozen;
    
    public Perishable(String n, int sl, int q, boolean f){
        super(n, sl, q);
        is_frozen = f;
    }

    public boolean getIsFrozen(){ return is_frozen; }

    public void getIsFrozen(boolean f){ is_frozen = f;}

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
