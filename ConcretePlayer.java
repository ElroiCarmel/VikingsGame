public class ConcretePlayer implements Player{

    boolean playerOne;
    int wins;

    public ConcretePlayer(boolean one) {
        this.playerOne = one;
        this.wins = 0;
    }
    @Override
    public boolean isPlayerOne() {
        return this.playerOne;
    }

    @Override
    public int getWins() {
        return this.wins;
    }
}
