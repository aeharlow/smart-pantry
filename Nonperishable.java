abstract class Nonperishable extends Item{

    public Nonperishable(String n, String t, int sl, int q){
        super(n, t, sl, q);
    }

    /***************************************************
     * do we really need all these classes?
     * Why cant we just add a string attribute 
     * that signals the type of item ansd have 
     * perishable and non-perishable be regular classes?
     ***************************************************/

}
