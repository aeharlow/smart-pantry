abstract class Perishable extends Item{
    boolean is_frozen;
    
    public Perishable(String n, String t, int sl, int q, boolean f){
        super(n, t, sl, q);
        is_frozen = f;
    }

    public boolean getIsFrozen(){ return is_frozen; }

    public void getIsFrozen(boolean f){ is_frozen = f;}
}
