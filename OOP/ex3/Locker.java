import oop.ex3.spaceship.Item;


import java.util.HashMap;
import java.util.Map;


/**
 * the locker class , represent a locker in a space ship, every crew man can have his locker
 * he can move items up to certin amount , the user to the class can move items to the long term storage
 */
public class Locker {
    /*a long tern storage for the locker*/
    private final LongTermStorage longTermStorage;
    /*the capacity of the locker */
    private final int capacity ;

    /*magic numbers */
    private static final int FAIL = -1;
    private static final int SUCCESS = 0;
    private static final int MOVED_TO_LTS = 1;
    private static final int CONTRADICTION = -2;
    private static final double MIN_CAPACITY = 0.2;




    /*create map for all of the inventory */
    private final Map<String,Integer> inventory = new HashMap<String, Integer>() ;
    /* how much space is currently use*/
    private int currCapacity;

    private final Item[][] constraints ;




    //MESSAGE I PRINT WHEN A PROBLEM IS OCCUR
    private final  String GENERAL_ERROR= "Error: Your request cannot be completed at this time" ;
    private  final  String MOVED_TO_LTS_WARNING ="Warning: Action successful, but has caused items to be moved to storage";
    //general integers , use for the messages


    private int n ;
    private static  String type ;

    private   String NO_ROOM;
    private   String DO_NOT_CONTAIN;

    private    String NEGATIVE_NUMBER;

    private    String CONSTRAINS_ERROR;





    /**
     *
     * @param lts the long term storgae of the ship
     * @param capacity how mach the locker can contain
     * @param constraints what cant be togherther
     */
    public Locker(LongTermStorage lts, int capacity, Item[][] constraints){
        longTermStorage = lts ;
        this.capacity = capacity;
        this.constraints = constraints ;
      }

    /**
     * if an item pass al of the test is
     * needed he will be added to the locker
     *
     * @param item = the item  type we want to add
     * @param n = the number of items from this type
     * @return if we add the item - return 0
     *
     * if we didnt add - if this is because the item is collid with other item we retuen -2
     * if this is because we dont have space for the item or the n is a negative number we return -1
     * if we need to move the item to the long term we return 1
     */
    public int addItem(Item item, int n) {
        this.n = n;
        this.type = item.getType();
        updateMessage();
        int capacityINeed = item.getVolume() * n;
        //check collision - if the item is in the contraint list
        if (constraints!= null &&!checkConstrains(item)) {
                System.out.println(GENERAL_ERROR + " " + CONSTRAINS_ERROR);
                return CONTRADICTION;
            }

        // if we can't add as much items
        if (capacityINeed > this.getAvailableCapacity()) {
            System.out.println(GENERAL_ERROR + " " + NO_ROOM);
            return FAIL;
        }
        if (moveToLongTerm(item,n)==1){
            System.out.println(MOVED_TO_LTS_WARNING);
            return MOVED_TO_LTS;
        }
        else if (moveToLongTerm(item,n)==-1){
            System.out.println(GENERAL_ERROR + " " + NO_ROOM);
            return FAIL;
        }

        // check if the integer is negative
        if (n < 0) {
            System.out.println(GENERAL_ERROR + " " + NEGATIVE_NUMBER);
            return FAIL;
        }

            // if we already have the item
        if (inventory.containsKey(item.getType())) {
            inventory.put(item.getType(), n + inventory.get(item.getType()));
            currCapacity += capacityINeed;
            return SUCCESS;
            } else
                { // if this is a new item
                inventory.put(item.getType(), n);
                    currCapacity += capacityINeed;
                    return SUCCESS;
            }
        }

        /*an helper method for the addItem function , check constrains  */
        private boolean checkConstrains(Item item){
            for (Item[] constrain:this.constraints) {
                if((constrain[0].getType().equals(item.getType())&& inventory.containsKey(constrain[1].getType()))
                        ||constrain[1].getType().equals( item.getType()) &&inventory.containsKey(constrain[0].getType())){
                    return false;
                }
            }
            return true;
        }


    /**
     * check if i need to move items to the long term and if so ,move them
     */
    private int moveToLongTerm(Item item,int n) {

        int itemVolume = 0;

        int maxCapcity = (int) (this.capacity * MIN_CAPACITY); // the amount need to stay
        int reaminigCapicity = (int) (maxCapcity / item.getVolume());//the amount if items need to stay
        int movingCapicity = n - reaminigCapicity;
        if (this.inventory.get(item.getType()) != null) {
            itemVolume = this.inventory.get(item.getType()) * item.getVolume();
            movingCapicity = (int) this.inventory.get(item.getType()) + n - reaminigCapicity;
        }

        if (itemVolume + item.getVolume() * n > capacity / 2 && longTermStorage.getAvailableCapacity() >= movingCapicity*item.getVolume()) {//check if the volume is more then 50% of the volume
                longTermStorage.addItem(item, movingCapicity);
                inventory.put(item.getType(), reaminigCapicity);
                currCapacity = currCapacity + n * item.getVolume() - movingCapicity* item.getVolume();
                return MOVED_TO_LTS;
            }
            else if (longTermStorage.getAvailableCapacity() < movingCapicity
                || longTermStorage.getAvailableCapacity() < movingCapicity*item.getVolume()){
                return FAIL;
            }
            else {
            return SUCCESS;
        }

    }

    /**
     * remove an item from the locker
     * @param item the item we want to remove
     * @param n the number of each item we want to remove
     * @return return the -1 if we didnt remove the item
     * return 0 if we did
     */
    public int removeItem(Item item, int n){
        this.n = n ;
        this.type = item.getType();
        updateMessage();
        if(n<0){
            System.out.println(GENERAL_ERROR+" "+ NEGATIVE_NUMBER);
            return FAIL;
        }
        if (inventory.get(item.getType())==null||inventory.get(item.getType()) < n){
            System.out.println(GENERAL_ERROR +" "+ DO_NOT_CONTAIN);
            return FAIL;
        }
        else if (inventory.get(item.getType()) - n == 0) {
            inventory.remove(item.getType());
            currCapacity -= n;
            return SUCCESS;
        }
        else {
            inventory.put(item.getType(),inventory.get(item.getType()) - n );
            currCapacity -=n;
            return SUCCESS;
        }

    }

    /**
     * get how much items we have from each item
     * @param type the type of the number we want to count
     * @return how mucj from this item
     */
    public int getItemCount(String type){
        if (inventory.get(type) == null)return 0;
        return inventory.get(type);




    }

    /**
     *  this function return the inventory represent
     *  by a map of the type(string) of th items and the count of them
     * @return a map
     */

    public Map<String, Integer> getInventory(){

        return inventory;
    }

    /**
     *
     * @return the capacity of the locker
     */
    public int getCapacity(){
        return this.capacity;
    }

    /**
     *
     * @return how mach room left
     */
    public int getAvailableCapacity(){
        return this.capacity - this.currCapacity;
    }
    private   void updateMessage(){
        NO_ROOM = "Problem : no room for " + n + " items of type " + type ;
        DO_NOT_CONTAIN = " Problem: the locker does not " +
                "contain "+ n + " items of type " + type  ;
        NEGATIVE_NUMBER =  "Problem: cannot remove" +
                " a negative number of items of type " + type ;
        CONSTRAINS_ERROR =  "Problem: the " +
                "locker cannot contain items of type " + type +
                " , as it contains a contradicting item";

    }
}


