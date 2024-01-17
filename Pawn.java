public class Pawn extends ConcretePiece{
    //data
    private static final String PLAYER_ONE_TYPE = "♙";
    private static final String PLAYER_TWO_TYPE = "♟";
    private int kills;
    //constructor
    public Pawn(ConcretePlayer player, int id) {
        super(player, id);
        this.kills = 0;
    }
    @Override
    public String getType() {
        if (this.getOwner().isPlayerOne()) return PLAYER_ONE_TYPE;
        return PLAYER_TWO_TYPE;
    }
    //methods
    public int getKills() {
        return this.kills;
    }
    public void addKills(int num) {
        this.kills = this.kills + num;
    }
    public void substractKills(int n) {
        this.kills = this.kills - n;
    }
    public String toString() {
        if (this.getOwner().isPlayerOne()) return "D" + this.getID();
        return "A" + this.getID();
    }
}
