public abstract class ConcretePiece implements Piece {
    //data
    Player owner;
    //methods
    public ConcretePiece(ConcretePlayer player) {
        this.owner = player;
    }
    @Override
    public Player getOwner() {
        return this.owner;
    }

}
