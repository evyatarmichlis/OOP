/**
 * @author  evyatar
 * This class is a close hash set - this set is built as one long array of words
 *  ,if we want to add new item we add him to the closer
 * (by the equation we have been given) place of his
 * hash code(with clamp)
 */
public class ClosedHashSet extends SimpleHashSet {
    /**
     * a inner class that build the stractur we need for the close hash set.
     * we need a array of string and each string need flag to show if he s deleted
     */
    private static class StringAndFlag{
        private String string;
        private Boolean flag;

        /**
         * Contractor
         * @param string the string we get from the user/data
         */
        private StringAndFlag(String string){
            this.string = string;
            this.flag = false;

        }

        /**
         * @return the string
         */
        private String getString(){
            return string;
        }

        /**
         * @return show if the value is delete or not
         */
        private Boolean deleted(){
            return flag;

        }
        /**
         * delete the value ( by chang the flag)
         */
        private void deleteValue(){
            this.flag = true;
        }

    }

    /* the array of the close hash set */
    private StringAndFlag[] closeHash;

    /**
     * Constructs a new,
     * empty table with the specified load factors, and the default initial capacity (16)
     * @param upperLoadFactor The upper load factor of the hash table
     * @param lowerLoadFactor  The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        closeHash = new StringAndFlag[INITIAL_CAPACITY];


    }

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16)
     * , upper load factor (0.75) and lower load factor (0.25).
     */

    public ClosedHashSet() {
        super();
        closeHash = new StringAndFlag[INITIAL_CAPACITY];

    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16),
     * upper load factor (0.75), and lower load factor (0.25)
     * @param data - Values to add to the set.
     */
    public ClosedHashSet(java.lang.String[] data) {
        super();
        closeHash = new StringAndFlag[INITIAL_CAPACITY];
        for (String str : data) {
            if (!this.contains(str)) {
                this.add(str);
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
        StringAndFlag[] oldHash = this.closeHash;
        StringAndFlag[] newHash = new StringAndFlag[newSize];
        this.closeHash = newHash;
        this.currCapacity = 0;
        this.tableSize = newSize;
        //add all the elements to a new and resize table
        for (StringAndFlag oldStr : oldHash) {
            if (oldStr!=null&& !oldStr.deleted())  {
                copyOrAdd(oldStr.getString());

            }
        }
    }
    /**
     * Add a specified element to the set if it's not already in it
     * @param newValue New value to add to the set
     * @return False if newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {

        if (newValue != null && !this.contains(newValue)) {
            //check if we need to resize to table
            checkAndResize(1);
            copyOrAdd(newValue);
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
    public boolean contains(String searchVal){
        int oldIndex = searchVal.hashCode();
        int i = 0;
        while (i < capacity()){
            int index = (int) (oldIndex + (i + (i*i)) / 2);
            if (closeHash[clamp​(index)] == null) return false;
            if (((closeHash[clamp​(index)].getString()).equals(searchVal))&&(!closeHash[clamp​(index)].deleted()))
                return true;
            i++;

        }
        return false;

    }
    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        if (toDelete != null && this.contains(toDelete)) {
            int oldIndex = toDelete.hashCode();
            int i = 0;
            while (i < capacity()) {
                int index = (int) (oldIndex + (i + (i*i)) / 2);
                int clampIndex = clamp​(index);
                if (this.closeHash[clampIndex] == null) return false;
                if (((this.closeHash[clampIndex].getString()).equals(toDelete))
                        && (!this.closeHash[clampIndex].deleted())) {
                    this.closeHash[clampIndex].deleteValue();
                    this.currCapacity--;
                    checkAndResize(0);
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return this.currCapacity;
    }

    /**
     * get the index we can put / delete the string we want
     * @param oldIndex the index we get
     * @return the correct index
     */
    private int getIndex(int oldIndex){
        int i = 0;
        while (true){
          int index = (int) (oldIndex + (i + (i*i)) / 2);
            if (closeHash[clamp​(index)] == null || closeHash[clamp​(index)].deleted()){
                return clamp​(index);
            }else i++;
        }

    }

    /**
     * helper method for the add/resize method
     * add the element to the table
     * @param newVal
     */
    private void copyOrAdd(String newVal){
        int index = getIndex(newVal.hashCode());
        this.closeHash[index] = new StringAndFlag(newVal) ;
        this.currCapacity ++;

    }


}
