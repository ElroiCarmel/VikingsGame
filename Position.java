public class Position {
    //data
    int _x;
    int _y;
    private int uniquePieces;


    //constructor
    public Position(int x, int y) {
        this._x = x;
        this._y = y;
    }

    //methods
    @Override
    public String toString() {
        return "(" + this._x + ", " + this._y + ")";
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (o == this) return true;
//
//        if (!(o instanceof Position)) return false;
//
//        Position p = (Position) o;
//        return Integer.compare(this._x, p._x)==0 && Integer.compare(this._y, p._y)==0;
//
//    }
    public int getUniquePieces() {
        return uniquePieces;
    }

    public void setUniquePieces(int uniquePieces) {
        this.uniquePieces = uniquePieces;
    }

    public static Position stringToPosition(String s) {
        String[] xy = s.split(",");
        int x = Integer.parseInt(xy[0], 1, xy[0].length(), 10);
        int y = Integer.parseInt(xy[1], 1, xy[1].length() - 1, 10);
        return new Position(x, y);
    }


}
