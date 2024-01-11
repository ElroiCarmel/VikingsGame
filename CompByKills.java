import java.util.Comparator;

public class CompByKills implements Comparator<Pawn> {
    /**
     * Sort in descending order by killing number
     * if same, sort by ID in ascending order, and
     * if still the same, set winner's piece first
     */

    private ConcretePlayer winner;

    public CompByKills(ConcretePlayer winner) {
        this.winner = winner;
    }
    @Override
    public int compare(Pawn o1, Pawn o2) {
        int o1k = o1.getKills(), o2k = o2.getKills();
        if (o1k == o2k) {
            if (o1.getID() == o2.getID()) {
                if (o1.getOwner() == this.winner) return -1;
                return 1;
            }
            return o1.getID() - o2.getID();
        }
        return o2k - o1k;
    }


}
