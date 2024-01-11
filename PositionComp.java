import java.util.Comparator;

public class PositionComp implements Comparator<Position> {
    @Override
    public int compare(Position o1, Position o2) {
        if (o2.getUniquePieces() == o1.getUniquePieces()) {
            if (o1._x == o2._x) {
                return o1._y - o2._y;
            }
            return o1._x - o2._x;
        }
        return o2.getUniquePieces() - o1.getUniquePieces();
    }
}
