public class Position {
    //data
    int _x;
    int _y;
    private ConcretePiece _cuPiece;
    //constructor
    public Position(int x, int y) {
        this._x = x;
        this._y = y;
    }
    //methods
    public ConcretePiece getPiece() {
        return this._cuPiece;
    }


}
