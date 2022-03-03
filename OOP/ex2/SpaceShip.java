import oop.ex2.GameGUI;
import oop.ex2.SpaceShipPhysics;

import java.awt.*;


/**
 * The API spaceships need to implement for the SpaceWars game. 
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 *  a base class for the other spaceships or any other option you will choose.
 *  i choose to make the SpaceShip class to base class for all of my other ships
 *  because most of the attribute is usable in every ship in
 *  i think is much more easy for the user to work mainly on one class
 *  
 * @author oop ,
 */
public abstract class SpaceShip{
    /** The maximum energy level a ship starts with. */
    private static final int DEF_MAX_ENERGY = 210;

    /** The current energy level a ship starts with. */
    private static final int DEF_CUR_ENERGY = 190;

    /** The health level a ship starts with. */
    private static final int DEF_HEALTH = 22;



    /** The physic properties of this ship - location, velocity ect. */
    private SpaceShipPhysics spaceShipPhysics;

    /** The maximum level of energy this spaceship can has */
    private int maxEnergyLevel;

    /** The current energy level this spaceship has */
    private int curEnergyLevel;

    /** The health level of this spaceship, in the current moment */
    private int healthLevel;

    private boolean isShieldOn;
    /** counter the round of the game */
    private int roundCounter  ;
    /** the prameters i used in th movement of
     * all of the ships i use protected because
     * all of the sub classes can use the parameters*/
    /**#############################################*/
    private int coolDownCounter  ;// when the ship can shoot again
    protected int turnRight ; // turn right
    protected int turnleft ;// turn left
    protected int dontTurn ;// dont turn
    protected boolean accelerate;// boolean if the ship will accelrate
    /**
     *  Constructor - creates a new SpaceShip.
     */
    public SpaceShip() {
        accelerate = true;
        turnRight = -1;
        turnleft = 1 ;
        dontTurn = 0 ;
        roundCounter = 0 ;
        coolDownCounter = 7;
        resetPhyisc();
    }


    /**
     * Does the actions of this ship for this round. 
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public abstract void doAction(SpaceWars game) ;



    /**
     * This method is called every time a collision with this ship occurs 
     */
    public void collidedWithAnotherShip() {

        if (this.isShieldOn) {
            curEnergyLevel+=18;
            maxEnergyLevel+=18;


        }
        else {
            this.healthLevel -= 1;
            maxEnergyLevel-=10;
            if (curEnergyLevel > maxEnergyLevel){
                curEnergyLevel = maxEnergyLevel;
            }
        }
    }

    /** 
     * This method is called whenever a ship has died. It resets the ship's 
     * attributes, and starts it at a new random position.
     */
    public void reset(){
        if (this.healthLevel <= 0){
            resetPhyisc();
        }


    }

    /**
     * Checks if this ship is dead.
     * 
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return this.healthLevel <= 0;
    }


    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if (!isShieldOn) {
            this.healthLevel-=1;
            this.maxEnergyLevel = this.maxEnergyLevel - 10;
            if (this.curEnergyLevel > this.maxEnergyLevel) {
                this.curEnergyLevel = this.maxEnergyLevel;
            }
        }
    }
    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * 
     * @return the image of this ship.
     */
    public Image getImage() {
        if (this.getshield()) {
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;

        } else {
            return GameGUI.ENEMY_SPACESHIP_IMAGE;
        }
    }


    /**
     * Attempts to fire a shot.
     * 
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if (curEnergyLevel >= 19 && this.coolDownCounter >= 7) {

            curEnergyLevel -= 19;
            this.coolDownCounter = 0 ;
            game.addShot(spaceShipPhysics);

        }

    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (curEnergyLevel >= 3){
            this.curEnergyLevel -=3 ;
            isShieldOn = true;
        }
        else {
            isShieldOn = false;
        }
        
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
       if (curEnergyLevel>= 140){
           curEnergyLevel -=140 ;
           this.spaceShipPhysics = new SpaceShipPhysics();
           this.maxEnergyLevel = DEF_MAX_ENERGY;
           this.curEnergyLevel = getCurEnergyLevel();
           this.healthLevel = getHealthLevel();
           this.isShieldOn = false;

       }
    }

    /**
     * we call to this method  when the ship i
     */
    private void resetPhyisc(){

        this.spaceShipPhysics = new SpaceShipPhysics();
        this.maxEnergyLevel = DEF_MAX_ENERGY;
        this.curEnergyLevel = DEF_CUR_ENERGY;
        this.healthLevel = DEF_HEALTH;
        this.isShieldOn = false;

    }
    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.spaceShipPhysics;
    }

    /** mudify the cool down of the fire */
    protected void setCoolDown(){
        this.coolDownCounter ++ ;

    }


    /**
     *
     * @return a getter for the energy
     */
    protected int getCurEnergyLevel (){
        return curEnergyLevel;
    }


    /**
     *
     * @return the round counter
     */

    protected int getRoundCounter() {
        return roundCounter;
    }

    /**
     * add round
     */
    protected void addRoundCounter(){
        this.roundCounter ++ ;
    }

    /**
     *
     * @return the current health level
     */
    protected int getHealthLevel(){
        return healthLevel;
    }
    protected void setHealthLevel( int addHealth){
        healthLevel+=addHealth;
    }
    /**
     *
     * @return the current status of the shield
     */
    protected boolean getshield(){
        return isShieldOn;
    }


    /**
     * turn of the shield (make the flag of the shield to false)
     */
    protected void shieldOff(){
        this.isShieldOn = false;


    }

    /**
     * add energy to the ship
     * @param num = the energy we want to add in each round
     */
    protected void setEnergy(int num){
        if (curEnergyLevel < maxEnergyLevel) {
            curEnergyLevel += num;
        }

    }



}
