import java.util.Random;

/**
 * the drunk ship is a space ship that act like the driver drunk to much alcohol,
 * i make the druk ship drive in an unstable way ( the turns are random)
 * also the drunk ship fire and activate his shield in a random way
 */

public class DrunkardShip extends SpaceShip {
    int[] randomTurnArry ;// the right left and non directions
    private final Random rand = new Random();

    /**
     * the Constructor of the drunk ship , he get all of his method from SpaceShip
     * also he create a random arry .
     */
    DrunkardShip(){
        super();
        randomTurnArry = new int[]{turnRight,dontTurn,turnleft};
    }

    /**
     * we call this method in every round ,
     * here we do all the actions of the space ship
     * @param game the game object to which this ship belongs.
     * the drunk ship do this actions:
     * 1.turn to a random direction
     * 2.randomly activate the shield
     * 3. randomly shoot
     * 4. check if the ship died
     * 5. add one energy
     */
    @Override
    public void doAction(SpaceWars game) {
        SpaceShip closestShip = game.getClosestShipTo(this);
        // * 1.turn to a random direction
        int turn = RandomTurn();
        this.getPhysics().move(accelerate, turn);
        int ranodmInt = rand.nextInt(10);
        double distance = getPhysics().distanceFrom(closestShip.getPhysics());
        // 2.randomly activate the shield
        if (this.getRoundCounter() % (ranodmInt+1) == 0){
            shieldOn();
        }
        //randomly shoot and turn off the ship
        if(  distance  > (ranodmInt)  ){
            shieldOff();
            fire(game);
        }
        else {
            this.setCoolDown();
        }

        //add one energy
        this.setEnergy(1);

        this.addRoundCounter();


    }

    /**
     *
     * @return a random turn from the arry.
     */
    public int RandomTurn(){
        Random rand = new Random();
        return randomTurnArry[rand.nextInt(3)];

    }
}
