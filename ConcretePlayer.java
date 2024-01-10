public class ConcretePlayer implements Player{

    private boolean playerOne;
    private int wins;

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
    public void addWin() {
        this.wins+=1;
    }
}
