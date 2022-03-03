import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * the LongTermStorage represent a storage in a space ship ,each space ship have one storage
 * if the locker of one crew man cant contain some items he can move them to the lts.
 */

public class LongTermStorage {
    /* how much space is currently use*/
    private int currCapacity;
    /*the total space we have in the lTS*/
    private final int storageLimit;

    private final Map<String, Integer> ltmInventory = new HashMap<String, Integer>();
    //MESSAGE I PRINT WHEN A PROBLEM IS OCCUR


    private final String GENERAL_ERROR = "Error: Your request cannot be completed at this time";
    //general integers , use for the messages
    private int n;
    private String type;
    private String NO_ROOM;
    private String DO_NOT_CONTAIN;

    private String NEGATIVE_NUMBER;


    /*magic number */
    private static final int CAPACITY = 1000;
    private static final int NO_ITEMS = 0;
    private static final int SUCCESS = 0;
    private static final int FAIL = -1;

    /**
     * the Constarcor of the long term storage
     */
    public LongTermStorage() {
        storageLimit = CAPACITY;// the capacity of  the lts
        currCapacity = NO_ITEMS;// the lts start with 0 use capacity
        /*i use map for the long term storage */


    }

    /**
     * add item to the lts
     *
     * @param item the item type we want to add to the lts
     * @param n    the number of items
     * @return 0 if we enter the item
     * 01 if we did't
     */
    public int addItem(Item item, int n) {
        this.n = n;
        this.type = item.getType();
        updateMessage();
        int itemCapacity = n * item.getVolume();
        if (n < 0) {// if the n is negative number
            System.out.println(GENERAL_ERROR + " " + NEGATIVE_NUMBER);
            return FAIL;
        }
        if (itemCapacity > getAvailableCapacity()) { // if we don't have place
            System.out.println(GENERAL_ERROR + " " + NO_ROOM);
            return FAIL;
        } else if (ltmInventory.containsKey(item.getType())) {// if we have this item
            ltmInventory.put(item.getType(), ltmInventory.get(item.getType()) + n);
            currCapacity += itemCapacity;
            return SUCCESS;
        } else { // new item
            ltmInventory.put(item.getType(), n);
            currCapacity += itemCapacity;
            return SUCCESS;
        }

    }

    /**
     * reset the inventory of the lts , also reset the capacity
     */
    public void resetInventory() {
        this.currCapacity = NO_ITEMS;
        ltmInventory.clear();
    }

    /**
     * @param type = the type of th item
     * @return the number of item we have from this item
     */
    public int getItemCount(String type) {
        if (!ltmInventory.containsKey(type)) {
            return SUCCESS;
        } else {
            return ltmInventory.get(type);
        }
    }

    /**
     * @return the inventory map
     */
    public Map<String, Integer> getInventory() {
        return ltmInventory;
    }

    /**
     * @return the storage limit
     */
    public int getCapacity() {
        return storageLimit;
    }

    /**
     * @return how much capacity have left
     */
    public int getAvailableCapacity() {
        return storageLimit - currCapacity;
    }


    public void updateMessage() {
        NO_ROOM = "Problem : no room for " + n + " items of type " + type;
        DO_NOT_CONTAIN = " Problem: the locker does not " +
                "contain " + n + " items of type " + type;
        NEGATIVE_NUMBER = "Problem: cannot add" +
                " a negative number of items of type " + type;


    }
}



