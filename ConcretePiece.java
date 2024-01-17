import java.util.Iterator;
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
    public void addMove(Position p) {
        this.moves.add(p);
    }
    public int getID(){
        return this.id;
    }
    public int getTotalDist(){
        if (moves.size() < 2) return 0;
        int ans = 0;
        Iterator<Position> it = this.moves.iterator();
        Position first = it.next();
        Position second = it.next();
        int delta = Math.abs(second._x - first._x) + Math.abs(second._y - first._y);
        ans+=delta;
        while (it.hasNext()) {
            first = second;
            second = it.next();
            delta = Math.abs(second._x - first._x) + Math.abs(second._y - first._y);
            ans+=delta;
        }
        return ans;
    }
    public Position getLastPosition() {
        return moves.getLast();
    }
    /**
     * Removes the last position of this piece
     * @return Last position if the move history is not empty else returns null
     */
    public Position removeLstPos() {
        if (!moves.isEmpty()) {
            return this.moves.removeLast();
        }
        return null;
    }
    public List<Position> getMoves() {
        return moves;
    }
}
