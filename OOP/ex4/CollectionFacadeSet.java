/**
 * class of the facade set
 * we can use this class to do action on any collection with string type
 *
 */
public class CollectionFacadeSet implements SimpleSet  {
    /* the data type of this class is this */
    protected java.util.Collection<java.lang.String> collection;


    /**
     *  Constructor
     * @param collection the data structor type we enter to the facade
     */
    public CollectionFacadeSet(java.util.Collection<java.lang.String> collection) {
        if (collection != null) {
            this.collection = collection;
        }
    }
    /**
     * add new item to the collection
     */
    @Override
    public boolean add(String newValue) {
        if (newValue != null){
            if (this.collection.contains(newValue))return false;
            else this.collection.add(newValue);
            return true;
        }
        return false;
    }
    /**
     * check if the string is contain in the collection
     * return true or false
     */
    @Override
    public boolean contains(String searchVal) {
        if (searchVal !=null) return this.collection.contains(searchVal);
        return false;
    }


    /**
     *  delete a value from the collection
     * @param toDelete Value to delete
     * @return ture if deleted
     */
    @Override
    public boolean delete(String toDelete) {
        if (toDelete !=null && contains(toDelete)) {
            this.collection.remove(toDelete);
            return true;
        }
        return false;
    }

    /**
     * @return the size of the collection
     */
    @Override
    public int size() {
        return this.collection.size();
    }
}
