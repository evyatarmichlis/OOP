public class SpaceShipFactory {
    /**
     *
     * @param args = the ship the player want to play in the game
     *  ( h = humen , r = runner , b = basher , a = aggressive , d = drunk , s = special )
     *  this method add every ship the player want to the game
     * @return a arry with all the ship we want to play with
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        int i = 0;
        SpaceShip[] createShips = new SpaceShip[args.length];
        for (String arg : args) {
            if (arg.equals("h")){
                SpaceShip humen = new HumenShip();
                createShips[i] = humen ;
            }
            if (arg.equals("r")){
                SpaceShip runner = new RunnerShip();
                createShips[i] = runner;
            }
            if (arg.equals("b")){
                SpaceShip basher = new BasherShip();
                createShips[i] = basher;
            }
            if (arg.equals("a")){
                SpaceShip aggresiveShip = new AggresiveShip();
                createShips[i] = aggresiveShip;
            }
            if (arg.equals("s")){
                SpaceShip special = new Special();
                createShips[i] = special;
            }
            if (arg.equals("d")){
                SpaceShip drunk = new DrunkardShip();
                createShips[i] = drunk;
            }
            i ++;

        }
        return createShips;
    }
}
