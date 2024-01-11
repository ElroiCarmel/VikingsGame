import java.util.Comparator;

public class CompByDist implements Comparator<ConcretePiece> {

    private ConcretePlayer winner;

    public CompByDist(ConcretePlayer winner) {
        this.winner = winner;
    }
    @Override
    public int compare(ConcretePiece o1, ConcretePiece o2) {
        int o1d = o1.getTotalDist(), o2d = o2.getTotalDist();
        if (o1d == o2d) {
            if (o1.getID() == o2.getID()) {
                if (o1.getOwner() == this.winner) return -1;
                return 1;
            }
            return o1.getID() - o2.getID();
        }
        return o2d - o1d;
    }
}
