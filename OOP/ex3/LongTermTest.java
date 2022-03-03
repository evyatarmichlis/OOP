import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * this class is a test for the long term test , check every method in the long termStorageClass
 */
public class LongTermTest {

    /* magic numbers
    /* when action was completed successfully. */
    private static final int SUCCESS = 0;

    /* when failure of an action is return. */
    private static final int ERROR = -1;

    /* The total capacity units of every long time storage unit. */
    private static final int TOTAL_CAPACITY = 1000;



/* i create items and along term storage for the test */
    LongTermStorage longTermStorage1 = new LongTermStorage();
    /* item i create using the Item factory*/
    Item football = ItemFactory.createSingleItem("football");//size : 4
    Item helmet1 = ItemFactory.createSingleItem("helmet, size 1");//size 3
    Item helmet3 = ItemFactory.createSingleItem("helmet, size 3");//size 5
    Item baseballBat = ItemFactory.createSingleItem("baseball bat");//size 2
    Item sporesEngine = ItemFactory.createSingleItem("spores engine");// size 10

    /*i create this items for my test */
    Item book = new Item("book", 5);
    Item alien = new Item("alien", 10);
    Item theSun = new Item("the sun", 1000);


    /**
     * test for the addItem method
     * if we add item the method will return 0
     * if any problem will occur the method return -1
     */
    @Test
public void addItemTest(){
    /* add items */
    assertEquals(SUCCESS,longTermStorage1.addItem(football,50));
    assertEquals(SUCCESS,longTermStorage1.addItem(helmet1,35));

    /*add 0 items */
    assertEquals(SUCCESS,longTermStorage1.addItem(sporesEngine,0));
    /*add to much items */
    assertEquals(ERROR,longTermStorage1.addItem(helmet3,140));

    longTermStorage1.resetInventory();
    /*add a very big item*/
    assertEquals(SUCCESS,longTermStorage1.addItem(theSun,1));
    assertEquals(ERROR, longTermStorage1.addItem(baseballBat, 1)); // Checks the case that there isn't
        // available place in the storage.
    longTermStorage1.resetInventory();
    /*add negative number of items */
    assertEquals(ERROR,longTermStorage1.addItem(book,-3));




}

    /**
     *this method  test if the reserInventory restart the inventory
     */
    @Test
public void resetInventoryTest(){
     /* check if the empty inventory is equal to the empty map*/
    Map<String, Integer> assertMap = new HashMap<String, Integer>();
    assertEquals(assertMap,longTermStorage1.getInventory());
    /*add item before we clear */
    longTermStorage1.addItem(baseballBat,4);
    longTermStorage1.addItem(helmet1,12);
    longTermStorage1.resetInventory();

    assertEquals(assertMap,longTermStorage1.getInventory());

}

    /**
     * test if the getItemCount return the quantity of the item
     */
    @Test
public void getItemCountTest(){
    //check when the storage is empty
    assertEquals(longTermStorage1.getItemCount("football"),0);
    //check empty string
    assertEquals(longTermStorage1.getItemCount(""),0);

        /*add items before we check*/
    longTermStorage1.addItem(alien,15);
    longTermStorage1.addItem(book,8);
    assertEquals(longTermStorage1.getItemCount("alien"),15);
    assertEquals(longTermStorage1.getItemCount("book"),8);
    /*check the quantity if non exsit item */
    assertEquals(longTermStorage1.getItemCount("moon"),0);

}

    /**
     * test for the getInventoryTest, check if the we get the map we want
     */
    @Test
public void  getInventoryTest(){
     /*create new map for check*/
    Map<String, Integer> assertMap = new HashMap<String, Integer>();
    assertMap.put("football",15);
    assertMap.put("spores engine",8);
    /*add items for the storage*/
    longTermStorage1.addItem(football,15);
    longTermStorage1.addItem(sporesEngine,8);
    assertEquals(longTermStorage1.getInventory(),assertMap);

    /*create a diffrent map to check if they are not equal */
    Map<String, Integer> assertMap2 = new HashMap<String, Integer>();
    assertMap.put("football",16);
    assertMap.put("spores",8);
    assertNotEquals(longTermStorage1.getInventory(), assertMap2);

    /*create a diffrent map with the same number of items to check if they are not equal */
    Map<String, Integer> assertMap3 = new HashMap<String, Integer>();
        assertMap.put("football",10);
        assertMap.put("helmet, size 1",5);
        assertMap.put("spores",8);


}

    /**
     * test to the getCapacity method
     * check if the method return the right number of capacity
     */
    @Test
public void getCapacityTest(){

   /*check the capacity*/
    assertEquals(TOTAL_CAPACITY,longTermStorage1.getCapacity());
    /*check the capacity after we add items*/
    longTermStorage1.addItem(football,15);
    longTermStorage1.addItem(sporesEngine,8);
    longTermStorage1.addItem(baseballBat,3);
    longTermStorage1.addItem(alien,12);
    assertEquals(TOTAL_CAPACITY,longTermStorage1.getCapacity());
    /*check the capacity after we reset */
    longTermStorage1.resetInventory();
    assertEquals(TOTAL_CAPACITY,longTermStorage1.getCapacity());

}

    /**
     * test the getAvailableCapacity method if it really
     * return the available capacity
     */
    @Test

public void getAvailableCapacityTest(){
    /*check before we add items*/
    assertEquals(TOTAL_CAPACITY,longTermStorage1.getAvailableCapacity());
    longTermStorage1.addItem(book,90);
    longTermStorage1.addItem(alien,30);
    /*test after we add items*/
    assertEquals(250, longTermStorage1.getAvailableCapacity());

    /*check the capacity after i try to add a big item */
    longTermStorage1.addItem(theSun,1);
    assertEquals(250, longTermStorage1.getAvailableCapacity());


}


}
