import oop.ex3.spaceship.Item;

import java.util.*;

/**
 * the Space Ship class is a class represent a spaceship,
 * the space ship have her crew members and each one of  can them have locker
 */
public class Spaceship {
 final String name ;

    private final int[] crewIDs;
    private final int numOfLockers;
    private final Item[][] constraints;
    private final LongTermStorage longTermStorage;
    private  int currNumberOfLockers;
    List<Locker> lockerArray = new ArrayList<Locker>();



    /*a list for all of the locker we have in the ship */
    private Locker[] lockerList ;


    /*magic number */
    private static final int ZERO = 0;
    private static final int SUCCESS = 0;
    private static final int INVALID_ID= -1;
    private static final int ILLEGAL_CAPACITY= -2;
    private static final int NO_MORE_LOCKERS= -3;

    /**
     * the constructor of the space ship
     * @param name the name of the space ship
     * @param crewIDs the crew ids
     * @param numOfLockers the number of locker the ship have on board
     * @param constraints the list of item that can't be in the same locker
     */
    public Spaceship(String name , int[] crewIDs, int numOfLockers, Item[][] constraints){
        this.name = name;
        this.crewIDs = crewIDs;
        this.numOfLockers = numOfLockers;
        this.constraints = constraints;
        this.longTermStorage = new LongTermStorage();
        this.currNumberOfLockers = ZERO;
        lockerList = new Locker[numOfLockers];//create a list of lockers


    }

    /**
     *
     * @return the long term storage for the ship
     */
    public LongTermStorage getLongTermStorage(){
        return this.longTermStorage;
    }

    /**
     *
     * @param crewID the id of the crew man how need a locker
     * @param capacity how mach place the locker need to have
     * @return -1 if the id is not valid
     * -2 if the capacity not valid
     * -3 if we have too much lockers
     */

    public int createLocker(int crewID, int capacity){
        if (!checkValidId(crewID) ){ // if the id not valid
            return INVALID_ID;
        }
        else if (capacity < ZERO ){ // if the capacity not valid
            return ILLEGAL_CAPACITY;
        }
        else if (currNumberOfLockers >= numOfLockers){ // if we have to much lockers
            return NO_MORE_LOCKERS;
        }
        else { // create locker for the crew man

            Locker locker = new Locker
            (this.longTermStorage,capacity,this.constraints);//create locker
            lockerArray.add(locker);

            }
            currNumberOfLockers +=1; // add one locker to the number of lockers
            return SUCCESS;
        }


    /*
    helper method, check if the id is valid
     */
    private boolean checkValidId(int Id){
        if (Id <ZERO ){
            return false;
        }
        for (int i:crewIDs) {
            if (Id == i){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return get the list og crew id
     */
    public int[] getCrewIDs(){
        return crewIDs;
    }
    /**
     *
     * @return the locker list
     */
    public Locker[] getLockers(){
        return lockerArray.toArray(lockerList);


    }




}
