public class Pawn extends ConcretePiece{
    //data
    private static final String PLAYER_ONE_TYPE = "♙";
    private static final String PLAYER_TWO_TYPE = "♟";

    private int kills;
    //constructor


    public Pawn(ConcretePlayer player, int id) {
        super(player, id);
       // super(player);
        kills = 0;
    }

    @Override
    public String getType() {
        if (this.getOwner().isPlayerOne()) return this.PLAYER_ONE_TYPE;
        return this.PLAYER_TWO_TYPE;
    }
    //methods
    public int getKills() {
        return this.kills;
    }
}
