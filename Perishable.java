class Perishable extends Item{
    boolean is_frozen;
    
    public Perishable(String n, String t, int sl, int q, boolean f){
        super(n, t, sl, q);
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

        String ret = new String();;
        ret = getName() + " - quantity: " + getQuantity() + ", experation date: " + 
            getExpDate().toString() + ", frozen: " + f;
        return ret;
    }
}
