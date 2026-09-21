public class OrderAndChaos extends Game {
    private Board board;

    public OrderAndChaos(Controller controller) {
        super(controller);
        board = new Board(6, 6);
    }

    @Override
    public void start() {
        controller.displayWelcomeOrderAndChaos();
    }

    @Override
    protected boolean checkWin() {
        return false;
    }
}
