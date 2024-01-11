import java.util.Comparator;

public class CompBySteps implements Comparator<ConcretePiece> {

    @Override
    public int compare(ConcretePiece o1, ConcretePiece o2) {
        int o1s = o1.getMoves().size(), o2s = o2.getMoves().size();
        if (o1s == o2s) return o1.getID() - o2.getID();
        return o1s - o2s;
    }
}
