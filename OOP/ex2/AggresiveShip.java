/**
 * one of the space ship we have in the game ,
 * this ship try to follow and fire at the closest ship to her
 */
public class AggresiveShip extends SpaceShip {
    private final double MIN_DISTANCE  ;
    AggresiveShip(){
        super();
        MIN_DISTANCE = 0.21;
    }
    /**
     * we call this method in every round ,
     * here we do all the actions of the space ship
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {

        int turn = dontTurn;
        // get the closest ship
        SpaceShip closestShip = game.getClosestShipTo(this);
        double distance = getPhysics().distanceFrom(closestShip.getPhysics());
        double diffAngle = getPhysics().angleTo(closestShip.getPhysics());
        // turn to the close ship
        if (diffAngle >= 0 && diffAngle <= Math.PI) {
            turn = turnleft;
        } else if (diffAngle < 0 && diffAngle >= -Math.PI) {
            turn = turnRight;
        }

        this.getPhysics().move(accelerate, turn);
        //Fire if the distance is less then the MIN_DISTANCE
        if (distance < MIN_DISTANCE) {
            this.fire(game);
        }
        else {
            this.setCoolDown();
        }

        // add one energy point
        this.setEnergy(1);


    }
}
