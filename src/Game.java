/* Common structure for the games */

public abstract class Game {
    protected Controller controller;
    protected int turn;

    public Game(Controller controller) {
        this.controller = controller;
    }

    public Game() { this.controller = new Controller(); }

    public abstract void start();

    protected abstract boolean endGame(String message);

    protected abstract boolean checkWin();

}