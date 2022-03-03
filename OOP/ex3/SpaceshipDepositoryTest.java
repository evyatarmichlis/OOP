import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


/**
 * this test test the relation between the classes
 */

public class SpaceshipDepositoryTest {


    /* Magic numbers */
    private static final int SUCCESS = 0;
    private static final int ERROR = -1;
    private static final int COLLISION = -2;
    private static final int MOVED_TO_STORAGE = 1;

    // create the object we want to test
    private static Spaceship spaceShip;
    private static LongTermStorage longTermStorage;
    private static Locker locker;
    // the object items
    private static final int SPACESHIP_LOCKERS = 4;
    private static final int LOCKER_DEFAULT_CAPACITY = 25;
    private static Item[][] constraints;
    private static int[] crewId;


    /*
    create item for the tests
     */
    Item football = ItemFactory.createSingleItem("football");//size : 4
    Item helmet3 = ItemFactory.createSingleItem("helmet, size 3");//size 5
    Item baseballBat = ItemFactory.createSingleItem("baseball bat");//size 2


    @Before
    public void initialObjects(){
        crewId = new int[]{1,2,3,4,17};
        constraints = ItemFactory.getConstraintPairs();
        spaceShip = new Spaceship("Millennium Falcon",crewId,SPACESHIP_LOCKERS,constraints);
        longTermStorage = new LongTermStorage();
        locker = new Locker(longTermStorage,LOCKER_DEFAULT_CAPACITY,constraints);

    }
    /**
     *  test the action of the locker when he create by a ship
     **/
    @Test
    public void ShipAndHerLocker(){
        // create lockers from the space ship
        assertEquals(SUCCESS,spaceShip.createLocker(crewId[1],50));
        //get a locker from the space ship and use him
        Locker lockerFromShip = spaceShip.getLockers()[0];
        //check if the capacity is the same as we put
        assertEquals(50,lockerFromShip.getCapacity());
        //check if we can put items in the locker
        assertEquals(SUCCESS,lockerFromShip.addItem(baseballBat,2));
        // dont have space
        assertEquals(ERROR,lockerFromShip.addItem(baseballBat,100));
        //see if the locker inherit the constraints from his ship
        assertEquals(COLLISION,lockerFromShip.addItem(football,1));


    }

    /**
     * test the how the lts work with the lockers connect to him from the ship
     */

    @Test
    public void ShipLockerLtm(){
        //the ship and the locker need to have the same Ltm
        //so i will create space ship ,ltm amd try to fill the ltm
        longTermStorage = spaceShip.getLongTermStorage();
        //fill the ltm
        longTermStorage.addItem(helmet3,200);
        //create locker with the ship
        spaceShip.createLocker(crewId[0],50);
        locker = spaceShip.getLockers()[0];
        // the space ship locker need to be full
        assertEquals(ERROR,locker.addItem(helmet3,6));
        // see if another locker of the ship is full
        spaceShip.createLocker(crewId[1],40);
        locker = spaceShip.getLockers()[1];
        assertEquals(ERROR,locker.addItem(helmet3,5));

        // clear the lts and check
        longTermStorage.resetInventory();
        assertEquals(MOVED_TO_STORAGE,locker.addItem(football,8));
        //see if the amount of items the move to the lts is what we want
        assertEquals(6,longTermStorage.getItemCount("football"));



    }




}
