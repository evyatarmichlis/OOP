/**
 * the spcial ship is one of the ships in the game
 * my ship purpose is to be very annoying  and very hard to kill :
 * the ship teleport when she can. she fire and
 * activate her shield when there is a ship next to her
 * also she can reganrate health every 15 rounds of the game
 * also her energy regenrate faster.
 */
public class Special extends SpaceShip {
    private final double MIN_DISTANCE  ;
    private final int healthToAdd ;
    Special(){
        MIN_DISTANCE = 0.2;
         healthToAdd  = 1;
    }
    /**
     * we call this method in every round ,
     * here we do all the actions of the space ship
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        //telpeort when she can
        this.teleport();
        //the ship dont accelerate she just move in circles
        this.getPhysics().move(!accelerate, turnleft);
        SpaceShip closestShip = game.getClosestShipTo(this);
        double distance = getPhysics().distanceFrom(closestShip.getPhysics());
        //she shoot to the closest ship or once every 16 rounds
        if (getRoundCounter()% 16 == 0 || distance < MIN_DISTANCE){
                     this.fire(game);
                     this.shieldOn();
        }
        else {
            this.setCoolDown();
        }
        //add one life in every 15 rounds
        if (getRoundCounter()%15 == 0){
            setHealthLevel(healthToAdd);
        }
        //add energy
        this.setEnergy(2);
        //add to the cooldown counter

        //count round
        addRoundCounter();

    }
}
