import oop.ex3.spaceship.Item;
import  oop.ex3.spaceship.ItemFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * this class is a test for the SpaceShip class
 */
public class SpaceshipTest {
    // create magic numbers
    private static final int SUCCESS = 0;
    private static final int INVALID_ID= -1;
    private static final int ILLEGAL_CAPACITY= -2;
    private static final int NO_MORE_LOCKERS= -3;



    //create crew ids for the test's
    private final int[] crewId = new int[]{1,2,3,4,17};
    private final int[] fakeCrewId = new int[]{2,3,0,4,17};
    private final int[] fakeCrewId2 = new int[]{0};

//    /*create long tern storage for the tests.*/
    LongTermStorage longTermStorage = new LongTermStorage();
//    LongTermStorage fakeLongTermStorage = new LongTermStorage();
    /* item i create using the Item factory*/
    Item football = ItemFactory.createSingleItem("football");//size : 4
    Item baseballBat = ItemFactory.createSingleItem("baseball bat");//size 2


    Item[][] createConstraints = ItemFactory.getConstraintPairs();//{["baseball bat";"football"]}

    Spaceship spaceShip = new Spaceship("Millennium Falcon",crewId,3,createConstraints);


    /**
     * test the getLongTermStorage method
     */
    @Test

    public void getLongTermStorageTest(){
        //check if we get an long term storage from the method
        assertEquals(spaceShip.getLongTermStorage().getClass(),longTermStorage.getClass());
    }

    /**
     * test the CreateLocker method.
     */
    @Test
    public void createLockerTest(){
        //check if the id is valid
        assertEquals(spaceShip.createLocker(-5,3),INVALID_ID);// negative id
        assertEquals(spaceShip.createLocker(15,3),INVALID_ID);//the id don't in the ship
        assertEquals(spaceShip.createLocker(1002,3),INVALID_ID);
        // the id and the capacity not valid
        assertEquals(spaceShip.createLocker(-4,-3),INVALID_ID);
        //the capacity not valid
        assertEquals(spaceShip.createLocker(4,-3),ILLEGAL_CAPACITY);
        //add lockers
        assertEquals(spaceShip.createLocker(1,2),SUCCESS);
        assertEquals(spaceShip.createLocker(2,2),SUCCESS);
        assertEquals(spaceShip.createLocker(3,2),SUCCESS);
        // we add toם much lockers, we cant add another one
        assertEquals(spaceShip.createLocker(4,2),NO_MORE_LOCKERS);
        //create new space ship with 0 capacity of lockers
        Spaceship spaceShip2 = new Spaceship("Mili",fakeCrewId,0,createConstraints);
        assertEquals(spaceShip2.createLocker(17,2),NO_MORE_LOCKERS);
    }

    /**
     * test the getCrewId method
     */
    @Test
    public void getCrewIDsTest(){
        //check if the arrays are equals
        assertArrayEquals(crewId,spaceShip.getCrewIDs());
        //i created 2 fake id list : check if false
        assertNotEquals(fakeCrewId,spaceShip.getCrewIDs());
        assertNotEquals(fakeCrewId2,spaceShip.getCrewIDs());
    }
@Test
    public void getLockersTest(){
        spaceShip.createLocker(1,1);
        spaceShip.createLocker(1,2);
        spaceShip.createLocker(2,3);
        // create too much lockers
        spaceShip.createLocker(3,4);
        spaceShip.createLocker(4,5);
        // check the get lockers , see if the size as we put in the ship
        // see if the lockers are lockers with valid capacity and storage is empty
        Locker[] lockers  = spaceShip.getLockers();
        Map<String, Integer> assertMap = new HashMap<String, Integer>();
        assertEquals(3, lockers.length);
        for (Locker locker:lockers) {
            assertTrue(locker != null && locker.getCapacity()>= 0 && locker.getInventory().equals(assertMap));
        }

    }

}
