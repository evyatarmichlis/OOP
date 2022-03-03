/**
 * one of the ship in the game . this ship try to bash the other ships
 */
public class BasherShip extends SpaceShip {
    private final double MIN_DISTANCE ;
    BasherShip(){
        super();
        MIN_DISTANCE = 0.19;
    }

    /**
     * we call this method in every round ,
     * here we do all the actions of the space ship
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {

        int turn = dontTurn;
        SpaceShip closestShip = game.getClosestShipTo(this);
        double distance = getPhysics().distanceFrom(closestShip.getPhysics());
        double diffAngle = getPhysics().angleTo(closestShip.getPhysics());
        //ActivateShield
        //   1.if the distance from the closest ship is
        //  less then the minimal distance the ship will activate the shield
        if (distance < MIN_DISTANCE) {
            this.shieldOn();
        }
        else {
            this.shieldOff();
        }
        //chase the close ship
        //      3. if the angle to the closest ship is
        //      less then then pi the runner will turn left

        if (diffAngle >= 0 && diffAngle <= Math.PI) {
            turn = turnleft;
        //     if the angle to the closest ship is less
        //      the 0 and more than -Pi the ship will turn right
        } else if (diffAngle < 0 && diffAngle >= -Math.PI) {
            turn = turnRight;

        }
        this.getPhysics().move(accelerate, turn);


        //add one energy
        this.setEnergy(1);

    }
}
