/**
 * the runner ship is one of the ships we have in the game ,
 * the runner ship try to escape from the other ships
 */
public class RunnerShip extends SpaceShip {
    private final double MIN_DISTANCE ;
    private final double MIN_ANGLE;

    /**
     * the Constructor of the Runnership
     * it inherit from the Space ship class
     */
    RunnerShip(){
        super();
        MIN_DISTANCE = 0.25 ;
        MIN_ANGLE =  Math.abs(Math.toRadians(0.23));

    }
    /**
     * we call this method in every round ,
     * here we do all the actions of the space ship
     * @param game the game object to which this ship belongs.
     * the runner ship do this actions:

     *

     *
     */
    @Override
    public void doAction(SpaceWars game) {

        int turn = dontTurn ;
        SpaceShip closestShip = game.getClosestShipTo(this);
        double distance = getPhysics().distanceFrom(closestShip.getPhysics());
        double diffAngle = getPhysics().angleTo(closestShip.getPhysics());
        // * 1.if the distance from the
        //closest is < min_distance and the angle from the
        // other ship is < from the minimum angle the ship will teleport

        if (distance < MIN_DISTANCE && diffAngle < MIN_ANGLE ){
            this.teleport();
        }
        // 2. if the angle to the closest ship is
        //     less then then pi the runner will turn right

        if (diffAngle >= 0 && diffAngle <= Math.PI  ){
            turn = turnRight;
        }
        //      if the angle to the closest ship is less
        //      the 0 and more than -Pi the ship will turn left
        else  if (diffAngle < 0 && diffAngle >= -Math.PI  ){
            turn = turnleft;
        }
        this.getPhysics().move(accelerate,turn);






    }
}
