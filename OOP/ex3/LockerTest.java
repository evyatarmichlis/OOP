import oop.ex3.spaceship.Item;
import org.junit.*;
import  oop.ex3.spaceship.ItemFactory;
import java.util.HashMap;
import java.util.Map;

import static org . junit . Assert .*;
import static org.junit.Assert.assertEquals;


/**
 * test for the locker test
 */
public  class LockerTest   {
    /*Magic numbers */
    private static final int SUCCESS = 0;
    private static final int ERROR = -1;
    private static final int COLLISION = -2;
    private static final int MOVED_TO_STORAGE = 1;



    //new long term storage
    LongTermStorage longTermStorage = new LongTermStorage();
//@Before
//    public void  beforeTest() {
        /* item i create using the Item factory*/
        Item football = ItemFactory.createSingleItem("football");//size : 4
        Item helmet1 = ItemFactory.createSingleItem("helmet, size 1");//size 3
        Item helmet3 = ItemFactory.createSingleItem("helmet, size 3");//size 5
        Item baseballBat = ItemFactory.createSingleItem("baseball bat");//size 2
        Item sporesEngine = ItemFactory.createSingleItem("spores engine");// size 10


        /* item i create on my own */
        Item ball = new Item("ball", 3);
        Item fuel = new Item("fuel", 2);
        Item flamethrower = new Item("flamethrower", 4);
        Item cat = new Item("cat", 3);
        Item dog = new Item("dog", 5);
        Item tennisBall = new Item("tennisBall", 2);

        /*create constrainst for the test */
        Item[] constrains1 = new Item[]{cat, dog};
        Item[] constrains2 = new Item[]{flamethrower, fuel};
        Item[][] constraintsItems = new Item[][]{constrains1, constrains2};

        Item[][] createConstraints = ItemFactory.getConstraintPairs();//{["baseball bat";"football"]}

        /*create lockers for the tests*/
        Locker locker1 = new Locker(longTermStorage, 20, constraintsItems);
        Locker locker2 = new Locker(longTermStorage, 0, constraintsItems);
        Locker locker3 = new Locker(longTermStorage, 10, createConstraints);
        Locker locker4 = new Locker(longTermStorage, 100, constraintsItems);
        Locker bigLocker = new Locker(longTermStorage, 5000, constraintsItems);

    /**
     * test for the addItem method , i try too test all of the possible scenarios
     */
    @Test
    public void testAddItem(){
        //Simple test : check if the item add
        assertEquals (SUCCESS,locker1.addItem(football,1) ) ;
        assertEquals (SUCCESS , locker1.addItem(baseballBat,2)) ;

        // not enough space
        assertEquals (ERROR , locker1.addItem(sporesEngine,2)) ;

        //constrains check
        assertEquals ( SUCCESS,locker1.addItem(fuel,1)) ;
        assertEquals (COLLISION,locker1.addItem(flamethrower,1)) ;

        //another constrains check
        assertEquals ( SUCCESS,locker3.addItem(baseballBat,1)) ;
        assertEquals (COLLISION,locker3.addItem(football,1)) ;
        //add zero football still cause collision
        assertEquals (COLLISION,locker3.addItem(football,0)) ;

        //check the opposite direction
        locker3.removeItem(baseballBat,1);
        assertEquals ( SUCCESS,locker3.addItem(football,1)) ;
        assertEquals (COLLISION,locker3.addItem(baseballBat,1)) ;

        //try to add negative number
        assertEquals ( ERROR,locker4.addItem(football,-1)) ;

        //try to


        locker3.removeItem(football,1);
        /* Checks the case of locker without constraints when the parameter is null */
        Locker nullLocker = new Locker(new LongTermStorage(), 50, null);
        assertEquals(SUCCESS, nullLocker.addItem(baseballBat, 3));
        assertEquals(SUCCESS, nullLocker.addItem(football, 2)); // No collision should be expected.
        assertEquals(SUCCESS, nullLocker.addItem(baseballBat, 1)); // Checks from the other direction.

        //add more item then the locker can handle
        assertEquals (ERROR,locker1.addItem(football,10) ) ;

        //check if we add more then 50% of one item we get 1 from the add item
        //and if the item realy move to the long term storage.
        assertEquals(MOVED_TO_STORAGE,locker3.addItem(helmet1,2));//the volume
        // of the 2 helmets is 6 (is more the 50% of the capacity of
        // locker3 so he have now 1 helmet (3/10) of the capacity
        /* Checks after moving items to long term storage. */
        assertEquals(MOVED_TO_STORAGE, locker4.addItem(helmet1, 20));

        assertEquals(SUCCESS,locker3.addItem(helmet3,1)); // if the helmet
        // really moved the locker can add another1


        assertEquals(SUCCESS,locker4.addItem(helmet3,10));// 50% of the vloume

        assertEquals(MOVED_TO_STORAGE,locker4.addItem(helmet3,1));//

        // one more is > 50% so helmets need to move
        //after the move the helmets need to be 20% of the locker
        assertEquals(4,locker4.getItemCount("helmet, size 3"));
        // only 4 helmets need to be

        /*check if the long term Storage also don't have a place*/

        assertEquals( ERROR,bigLocker.addItem(sporesEngine,400));//here the
        // locker have place for the items but he need to move them to the long Term,
        // the Long term also dont have place for them so we need to get Error


    }

    /**
     *test the remove method of the locker and text all the problems can occur/
     */
    @Test
    public void testRemoveItem(){
        locker1.addItem(football,1);
        locker1.addItem(baseballBat,3);
        //remove  the items i put
        assertEquals(SUCCESS,locker1.removeItem(football,1));
        assertEquals(SUCCESS,locker1.removeItem(baseballBat,2));
        //try to remove 0 items
        assertEquals(SUCCESS,locker1.removeItem(baseballBat,0));
        //try to remove item i removed
        assertEquals(ERROR,locker1.removeItem(football,1));


        //try to remove a item that don't exist in the locker
        assertEquals(ERROR,locker1.removeItem(sporesEngine,1));

        //try to remove a negative number of items
        locker1.addItem(football,2);
        assertEquals(ERROR,  locker1.removeItem(football,-1));

        //try to remove more items then what we have
        assertEquals(ERROR,  locker1.removeItem(football,3));

    }

    /**
     * test the ItemCount method ; if enter name of item the method need
     * to return the number of items from this type
     */
    @Test
    public void testGetItemCount(){
    // add items and check if the method return the amount
    bigLocker.addItem(flamethrower,12);
    bigLocker.addItem(cat,7);
    bigLocker.addItem(ball,5);
    assertEquals(12,bigLocker.getItemCount("flamethrower"));
    assertEquals(7,bigLocker.getItemCount("cat"));
    assertEquals(5,bigLocker.getItemCount("ball"));

    // the amount of item that never were in the locker
    assertEquals(SUCCESS,bigLocker.getItemCount("dog"));

    }

    /**
     * the getInventory need to return a map of the inventory in the locker
     */
    @Test
    public void testGetInventory(){
        bigLocker.addItem(flamethrower,12);
        bigLocker.addItem(cat,7);
        bigLocker.addItem(ball,5);
        // i build a test map to check if they are equals
        Map<String, Integer> assertMap = new HashMap<String, Integer>();
        assertMap.put("flamethrower",12);
        assertMap.put("cat",7);
        assertMap.put("ball",5);
        assertEquals( assertMap,bigLocker.getInventory());

        // check if the inventory isn't equal to empty map
        Map<String, Integer> assertMap2 = new HashMap<String, Integer>();
        assertNotEquals(assertMap2,bigLocker.getInventory());

    }

    /**
     * the method of getCapacity return the total capacity
     */
    @Test
    public void testGetCapacity(){
        //check the return value of each locker i create
        assertEquals( 5000, bigLocker.getCapacity());
        assertEquals(20,locker1.getCapacity());
        assertEquals(10,locker3.getCapacity());
        //empty locker
        assertEquals(SUCCESS,locker2.getCapacity());



    }

    /**
     * check how much capacity we have left in the locker
     */
    @Test
    public void testGetAvailableCapacity(){
        //check the amount after i add items
        bigLocker.addItem(flamethrower,12);
        bigLocker.addItem(cat,7);
        bigLocker.addItem(ball,5);
        locker1.addItem(baseballBat,2);
        locker1.addItem(helmet1,1);
        assertEquals(4916,bigLocker.getAvailableCapacity());
        assertEquals(13,locker1.getAvailableCapacity());
        //check the amount before i add items
        assertEquals(SUCCESS,locker2.getAvailableCapacity());
        assertEquals(10,locker3.getAvailableCapacity());

    }

}
