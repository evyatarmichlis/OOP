/**
 * an abstract class that represent a simpleHash set
 * this class will be the base of the other classes i built
 */
public abstract class SimpleHashSet implements SimpleSet  {

    /* default Constants*/
    //the initial  capacity of the hash table
    protected static final int INITIAL_CAPACITY = 16;
    // default of the lower capacity of the table
    protected static final float DEFAULT_LOWER_CAPACITY = 0.25f;
    // default of the higher capacity of the table
    protected static final float DEFAULT_HIGHER_CAPACITY=0.75f;
    /*field constants*/
    // the max percent of load factor
    private final float upperLoadFactor ;
    //the min percent of the load factor
    private final float lowerLoadFactor ;
    //the size of the hash table
    protected int tableSize ;
    //num of items / the place in the table
    protected double loadFactor;
    // the current capacity if the hush table
    protected  int currCapacity ;

    /**
     * Constructor
     * Constructs a new, empty table with the specified load factors,
     * and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table
     */
    protected SimpleHashSet(float upperLoadFactor,float lowerLoadFactor){
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
        this.tableSize = INITIAL_CAPACITY;
        this.loadFactor = 0 ;

    }

    /**
     * A default constructor. Constructs a new,
     * empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    protected SimpleHashSet(){
        this.upperLoadFactor =DEFAULT_HIGHER_CAPACITY;
        this.lowerLoadFactor = DEFAULT_LOWER_CAPACITY;
        this.tableSize = INITIAL_CAPACITY;
        this.loadFactor = 0 ;


    }

    /**
     *
     * @return The current capacity (number of cells) of the table
     */
    public abstract int capacity();

    /**
     *
     * @return  The lower load factor of the table.
     */
    protected float getLowerLoadFactor(){
        return this.lowerLoadFactor;
    }

    /**
     *
     * @return The higher load factor of the table
     */
    protected float getUpperLoadFactor(){

        return this.upperLoadFactor;
    }

    /**
     *Clamps hashing indices to fit within the
     * current table capacity
     * @param index the index before clamping
     * @return an index properly clamped
     */

    protected int clamp​(int index){
        return index & (this.tableSize - 1);
    }


    /**
     * use when we want to increase the size of the table
     */
    protected void increaseTableSize(){
        resizeTable(this.tableSize*=2);

    }

    /**
     * use when we want to decrease the size of the table
     */

    protected void decreaseTableSize(){
        resizeTable(this.tableSize/=2);
    }

    /**
     * change the size of the table
     * it is abstract because every hash table implement this diffrent
     * @param newSize the size of the new table
     */
    protected abstract void resizeTable(int newSize);

    /**
     * check and resize the table if the load factor is bigger of
     * smaller than the lower/upper loadFactor
     * @param addOrDelete int that help us to know if we add item or delete item
     */
    protected void  checkAndResize(int addOrDelete){
        this.loadFactor  = (float) (size()+addOrDelete) / capacity();
            if (loadFactor < lowerLoadFactor && (addOrDelete == 0)){
                decreaseTableSize();
            }
            else if (loadFactor >upperLoadFactor&& addOrDelete == 1){
                increaseTableSize();
            }

    }

    

}

