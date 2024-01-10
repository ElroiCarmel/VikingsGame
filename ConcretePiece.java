import java.util.LinkedList;
import java.util.List;

public abstract class ConcretePiece implements Piece {
    //data
    private final Player owner;
    private final int id;

    private int stepsCount = 0;

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

    public void addMove(Position p) {
        this.moves.add(p);
    }
    public int getID(){
        return this.id;
    }
    public void addSteps(int num) {
        this.stepsCount = this.stepsCount + num;
    }

    public int getStepsCount(){
        return this.stepsCount;
    }

    public Position getLastPosition() {
        return moves.getLast();
    }

    public List<Position> getMoves() {
        return moves;
    }
}
