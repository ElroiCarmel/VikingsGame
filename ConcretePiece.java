public abstract class ConcretePiece implements Piece {
    //data
    Player _owner;
    //methods
    public ConcretePiece(ConcretePlayer player) {
        this._owner = player;
    }
    @Override
    public Player getOwner() {
        return this._owner;
    }

}
