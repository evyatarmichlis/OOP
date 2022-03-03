import java.util.LinkedList;

public class WrapperLinkList implements SimpleSet {

    private CollectionFacadeSet FacadelinkedList;

    private java.util.LinkedList<String> linkedStringList;

    public WrapperLinkList(LinkedList<String> collection) {
        this.linkedStringList = collection;
        this.FacadelinkedList = new CollectionFacadeSet(collection);

    }


    @Override
    public boolean add(String newValue) {
        return this.FacadelinkedList.add(newValue);
    }

    @Override
    public boolean contains(String searchVal) {
        return this.FacadelinkedList.contains(searchVal);

    }


    @Override
    public boolean delete(String toDelete) {
        return this.FacadelinkedList.contains(toDelete);
    }

    @Override
    public int size() {
        return this.FacadelinkedList.size();

    }

    public LinkedList<String> getLinkList(){
        return linkedStringList;
    }
}
