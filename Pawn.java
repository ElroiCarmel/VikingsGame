public class Pawn extends ConcretePiece{
    //data
    private static final String PLAYER_ONE_TYPE = "♙";
    private static final String PLAYER_TWO_TYPE = "♟";
    //constructor


    public Pawn(ConcretePlayer player) {
        super(player);
    }

    @Override
    public String getType() {
        if (this.getOwner().isPlayerOne()) return this.PLAYER_ONE_TYPE;
        return this.PLAYER_TWO_TYPE;
    }
    //methods

}
