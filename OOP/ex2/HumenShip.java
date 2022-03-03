import oop.ex2.GameGUI;

import java.awt.*;

/**
 * one of the space ship we have in the game
 * this space ship is controlled by the player
 *
 */
public class HumenShip extends SpaceShip {

    /**
     *the main method for this class do
     *  all the action the player can do i a single round
     * @param game the game object to which this ship belongs.
     * this method do the action of the humen spaceship need to do every round of the game
     */
    public void doAction(SpaceWars game) {
       boolean speedUp = !accelerate ;
       int turn = dontTurn;

    // if the t key is pressed the ship teleport
    if (game.getGUI().isTeleportPressed()) {
        this.teleport();
    }
    // if the up key is pressed the ship speed up
    if (game.getGUI().isUpPressed()){
        speedUp = accelerate;
    }
    // if the right key is pressed the ship turn right
    if (game.getGUI().isRightPressed()){
        turn = turnRight;
    }
    // if the  left key is pressed the ship turn left
    if (game.getGUI().isLeftPressed()){

        turn = turnleft;
    }
    // if the player press left and right together the ship dont turn
    if (game.getGUI().isRightPressed()&&game.getGUI().isLeftPressed()){
        turn = dontTurn;
    }
    // move the ship
    this.getPhysics().move(speedUp,turn);
    // if the player  press d key the shield turn on
    if(game.getGUI().isShieldsPressed()) {
        this.shieldOn();
    }
    else {
        this.shieldOff();
    }
    // if the player press the s key the ship shoot
    if (game.getGUI().isShotPressed()) {
        this.fire(game);
    }
    else {
        this.setCoolDown();
    }

    //add one energy and add one round to the cool down counter
    this.setEnergy(1);




}

    /**
     *  the humen ship have a diffrent image in the
     *  game this method set the image of the humen ship
     * @return the image we need
     */
    @Override
    public Image getImage() {
        if (this.getshield()) {
            return GameGUI.SPACESHIP_IMAGE_SHIELD;

        } else {
            return GameGUI.SPACESHIP_IMAGE;
        }
    }
}
