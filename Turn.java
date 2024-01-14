import java.util.LinkedList;

public class Turn {
    //data
    private ConcretePiece pieceMoved;
    private LinkedList<ConcretePiece> piecesKilled;
    //constructor
    public Turn(ConcretePiece cp) {
        this.pieceMoved = cp;
        this.piecesKilled = new LinkedList<>();
    }
    public Turn(ConcretePiece cp, LinkedList<ConcretePiece> pk) {
        this.pieceMoved = cp;
        this.piecesKilled = pk;
    }
    //method
    public ConcretePiece getPieceMoved() {
        return pieceMoved;
    }

    public LinkedList<ConcretePiece> getPiecesKilled() {
        return piecesKilled;
    }
}
