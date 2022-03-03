/  

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author evyatar
 * the Open hash set is a hash table that work on link list
 * every elemnt is the table is link list and if we want to add new
 * item we we check is hash code and 2 items with same hash code are add to the
 * same Link list
 * the table can resize when needed
 */
public class OpenHashSet  extends  SimpleHashSet {
    /**
     * a inner class that create a link list how extends the facade
     * every bucket in the open hashtable is from this class
     */
    private static class FacadeLinkList extends CollectionFacadeSet {
        /**
         * Constructor
         */
        public FacadeLinkList() {
            super(new LinkedList<String>());
        }

        /**
         * we need the option to iter the linklist
         */
        public Iterator<String> iterator(){
            return this.collection.iterator();
        }


    }

    /*create an array of the the class we just create this will be the
    base for the openHash table*/
    private FacadeLinkList[] openHash ;

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16)
     * @param upperLoadFactor The upper load factor of the hash table
     * @param lowerLoadFactor The lower load factor of the hash table
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        resetHash(INITIAL_CAPACITY);

    }

    /**
     * A default constructor. Constructs a new,
     * empty table with default initial capacity (16), upper load factor (0.75) and lower load factor (0.25).
     */

    public OpenHashSet() {
        super();
        resetHash(INITIAL_CAPACITY);
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored. The new table has the
     * default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25)
     * @param data Values to add to the set.
     */

    public OpenHashSet(java.lang.String[] data) {
        super();
        if (data != null) {
            resetHash(INITIAL_CAPACITY);
            for (String dat : data) {
                if (!this.contains(dat)) {
                    add(dat);

                }
            }
        }
    }

    /**
     *
     * @return The current capacity (number of cells) of the table
     */
    @Override
    public int capacity() {
        return this.tableSize;
    }


    /**
     *  resize the table if needed ( *2 / *0.5)
     * @param newSize the size of the new table
     */
    @Override
    protected void resizeTable(int newSize) {
        FacadeLinkList[] oldTable = this.openHash;
         this.resetHash(newSize);
         //add the elements from the old Table to the new one
        for (FacadeLinkList linkList : oldTable) {
            Iterator<String> it = linkList.iterator();
            for (Iterator<String> iter = it; iter.hasNext(); ) {
                String string = iter.next();
                int index = clamp​(string.hashCode());
                openHash[index].add(string);
                this.currCapacity++;

            }
            }
        }


    /**
     * Add a specified element to the set if it's not already in it
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        if (newValue!=null && !this.contains(newValue)) {
            checkAndResize(1);
            int index = clamp​(newValue.hashCode());
            openHash[index].add(newValue);
            this.currCapacity++;

            return true;
        }

        return false;
    }

    /**
     *Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        int index = clamp​(searchVal.hashCode());
        if ( openHash[index].contains(searchVal))return true;
        return false;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        if (toDelete!=null && this.contains(toDelete) ) {
            int index = clamp​(toDelete.hashCode());
                openHash[index].delete(toDelete);
            this.currCapacity--;
            checkAndResize(0);
            return true;
        }

        return false;
    }

    /**
     *
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return this.currCapacity;
    }


    /**
     *  build a new hash table with the given size
     * @param newSize the new size of the hash
     */
    public void resetHash(int newSize) {
        this.openHash = new FacadeLinkList[ newSize ];
        this.currCapacity = 0 ;
        for(int i = 0; i < newSize ; i++){
            this.openHash[i] = new FacadeLinkList();
        }
    }

}
