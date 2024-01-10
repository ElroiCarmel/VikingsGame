import java.util.LinkedList;
import java.util.List;

public abstract class ConcretePiece implements Piece {
    //data
    private final Player owner;
    private final int id;

    private List<Position> moves = new LinkedList<>();
    //methods
    public ConcretePiece(ConcretePlayer player, int id) {
        this.id = id;
        this.owner = player;
    }
    @Override
    public Player getOwner() {

        return this.owner;
    }

    private void addMove(Position p) {
        this.moves.add(p);
    }

}
