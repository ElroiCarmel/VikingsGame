import java.util.*;

public class GameLogic implements PlayableLogic {
    //data
    private static final int BOARD_SIZE = 11;
    private ConcretePlayer playerOne = new ConcretePlayer(true);
    private ConcretePlayer playerTwo = new ConcretePlayer(false);

    private ConcretePiece[] pieces;

    //private ConcretePlayer currentPlayer;

    private boolean secondPlayerTurn;
    private ConcretePiece[][] board;

    //Constructor
    public GameLogic() {
        reset();
    }

    //methods
    @Override
    public boolean move(Position a, Position b) {
        ConcretePiece poa = board[a._x][a._y];
        ConcretePiece pob = this.board[b._x][b._y];
        if (poa == null || pob != null) return false; // If a is empty or b already has a piece
        Player cuPl;
        if (this.isSecondPlayerTurn()) cuPl = playerTwo;
        else cuPl = playerOne;
        Player aOwner = poa.getOwner();
        if (cuPl != aOwner) return false; // If it's not the piece owner turn
        //check for attempting moving diagonally
        if (a._x != b._x && a._y != b._y) return false;
        //check for clear path, without pieces in between
        int min, max;
        if (a._x == b._x) {
            min = Math.min(a._y, b._y);
            max = Math.max(a._y, b._y);
            for (int s = min + 1; s < max; s++) {
                if (this.board[a._x][s] != null) return false;
            }
        }
        if (a._y == b._y) {
            min = Math.min(a._x, b._x);
            max = Math.max(a._x, b._x);
            for (int s = min + 1; s < max; s++) {
                if (this.board[s][a._y] != null) return false;
            }
        }
        // check for pawn attempts to get to corner
        if (!(this.board[a._x][a._y] instanceof King) && isCorner(b)) return false;
        //make the move
        this.board[b._x][b._y] = poa;
        // Adding information for Statistics
        // Steps taken
        int dist = Math.abs(a._x - b._x) + Math.abs(a._y - b._y);
        poa.addDist(dist);
        // Add position for moves history
        poa.addMove(b);
        if (!(poa instanceof King)) {
            ((Pawn) poa).addKills(updateKills(b));
        }
        this.board[a._x][a._y] = null;
        secondPlayerTurn = !secondPlayerTurn; // switch turns
        return true;
    }

    private int updateKills(Position p) {
        int ans = 0;
        int x = p._x, y = p._y;
        ConcretePlayer currentPlayer = (this.isSecondPlayerTurn()) ? this.playerTwo : this.playerOne;
        Position check;
        // Up
        check = new Position(x, y - 1);
        if (inRange(check) && getPieceAtPosition(check) != null && !(getPieceAtPosition(check) instanceof King) && getPieceAtPosition(check).getOwner() != currentPlayer) {
            if (y - 1 == 0 || (this.board[x][y - 2] != null && this.board[x][y - 2].getOwner() == currentPlayer && (this.board[x][y - 2] instanceof Pawn))) {
                this.board[check._x][check._y] = null;
                ans += 1;
            }
        }
        // Down
        check = new Position(x, y + 1);
        if (inRange(check) && getPieceAtPosition(check) != null && !(getPieceAtPosition(check) instanceof King) && getPieceAtPosition(check).getOwner() != currentPlayer) {
            if (y + 1 == 10 || (this.board[x][y + 2] != null && this.board[x][y + 2].getOwner() == currentPlayer && (this.board[x][y + 2] instanceof Pawn))) {
                this.board[check._x][check._y] = null;
                ans += 1;
            }
        }
        // Right
        check = new Position(x + 1, y);
        if (inRange(check) && getPieceAtPosition(check) != null && !(getPieceAtPosition(check) instanceof King) && getPieceAtPosition(check).getOwner() != currentPlayer) {
            if (x + 1 == 10 || (this.board[x + 2][y] != null && this.board[x + 2][y].getOwner() == currentPlayer && (this.board[x + 2][y] instanceof Pawn))) {
                this.board[check._x][check._y] = null;
                ans += 1;
            }
        }
        // Left
        check = new Position(x - 1, y);
        if (inRange(check) && getPieceAtPosition(check) != null && !(getPieceAtPosition(check) instanceof King) && getPieceAtPosition(check).getOwner() != currentPlayer) {
            if (x - 1 == 0 || (this.board[x - 2][y] != null && this.board[x - 2][y].getOwner() == currentPlayer && (this.board[x - 2][y] instanceof Pawn))) {
                this.board[check._x][check._y] = null;
                ans += 1;
            }
        }

        return ans;

        // x+1,y+0
        // x-1, y+0
        //x+0, y-1
        //x+0, y+1
    }

    private boolean inRange(Position p) {
        return inRange(p._x, p._y);
    }

    private boolean inRange(int x, int y) {
        return x >= 0 && x < GameLogic.BOARD_SIZE && y >= 0 && y < GameLogic.BOARD_SIZE;
    }

    @Override
    public Piece getPieceAtPosition(Position position) {
        int x = position._x, y = position._y;
        return this.board[x][y];
    }

    @Override
    public Player getFirstPlayer() {
        return this.playerOne;
    }

    @Override
    public Player getSecondPlayer() {
        return this.playerTwo;
    }

    @Override
    public boolean isGameFinished() {
        // Player 1 wins
        if (isCorner(pieces[30].getLastPosition())) {
            this.playerOne.addWin();
            printStat(playerOne);
            return true;
        }
        // Check if king is captured
        Position kp = pieces[30].getLastPosition();
        Position up = new Position(kp._x, kp._y - 1);
        Position down = new Position(kp._x, kp._y + 1);
        Position right = new Position(kp._x + 1, kp._y);
        Position left = new Position(kp._x - 1, kp._y);
        Position[] posArr = new Position[]{up, down, right, left};
        boolean kingCapture = true;
        for (Position p : posArr) {
//            if (inRange(p)) return
            boolean temp = !inRange(p) || (getPieceAtPosition(p) != null && !getPieceAtPosition(p).getOwner().isPlayerOne());
            kingCapture = kingCapture && temp;
        }
        if (kingCapture) {
            this.playerTwo.addWin();
            printStat(playerTwo);
            return true;
        }
        return false;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return secondPlayerTurn;
    }

    @Override
    public void reset() {
        this.board = new ConcretePiece[BOARD_SIZE][BOARD_SIZE];
        this.pieces = new ConcretePiece[37];
        for (int i = 0; i < 24; i++) {
            pieces[i] = new Pawn(playerTwo, i + 1);
        }
        for (int i = 24; i < 37; i++) {
            if (i - 23 == 7) continue;
            pieces[i] = new Pawn(playerOne, i - 23);
        }
        pieces[30] = new King(playerOne, 7);
        pieces[0].addMove(new Position(3, 0));
        pieces[1].addMove(new Position(4, 0));
        pieces[2].addMove(new Position(5, 0));
        pieces[3].addMove(new Position(6, 0));
        pieces[4].addMove(new Position(7, 0));
        pieces[5].addMove(new Position(5, 1));
        pieces[6].addMove(new Position(0, 3));
        pieces[7].addMove(new Position(10, 3));
        pieces[8].addMove(new Position(0, 4));
        pieces[9].addMove(new Position(10, 4));
        pieces[10].addMove(new Position(0, 5));
        pieces[11].addMove(new Position(1, 5));
        pieces[12].addMove(new Position(9, 5));
        pieces[13].addMove(new Position(10, 5));
        pieces[14].addMove(new Position(0, 6));
        pieces[15].addMove(new Position(10, 6));
        pieces[16].addMove(new Position(0, 7));
        pieces[17].addMove(new Position(10, 7));
        pieces[18].addMove(new Position(5, 9));
        for (int i = 19, po = 3; i < 24; i++, po++) {
            pieces[i].addMove(new Position(po, 10));
        }
        pieces[24].addMove(new Position(5, 3));
        pieces[25].addMove(new Position(4, 4));
        pieces[26].addMove(new Position(5, 4));
        pieces[27].addMove(new Position(6, 4));
        for (int i = 28, po = 3; i < 33; i++, po++) {
            if (i == 30) continue;
            pieces[i].addMove(new Position(po, 5));
        }
        pieces[30].addMove(new Position(5, 5));
        pieces[33].addMove(new Position(4, 6));
        pieces[34].addMove(new Position(5, 6));
        pieces[35].addMove(new Position(6, 6));
        pieces[36].addMove(new Position(5, 7));
        for (ConcretePiece p : pieces) {
            Position lp = p.getLastPosition();
            int x = lp._x, y = lp._y;
            board[x][y] = p;
        }
        secondPlayerTurn = true;
    }

    @Override
    public void undoLastMove() {

    }

    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }

    private boolean isCorner(Position p) {
        for (int i : new int[]{0, GameLogic.BOARD_SIZE - 1}) {
            for (int j : new int[]{0, GameLogic.BOARD_SIZE - 1}) {
                if (p._x == i && p._y == j) return true;
            }
        }
        return false;
    }

    private void printStat(ConcretePlayer whoWon) {

        Arrays.sort(pieces, 0, 24, new CompBySteps());
        Arrays.sort(pieces, 24, pieces.length, new CompBySteps());
        if (whoWon == playerTwo) {
            printWithMoves(this.pieces, 0, 24);
            printWithMoves(this.pieces, 24, pieces.length);
        } else {
            printWithMoves(this.pieces, 24, pieces.length);
            printWithMoves(this.pieces, 0, 24);
        }
        printAst();
        ArrayList<Pawn> temp = new ArrayList<>();
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] instanceof Pawn) {
                Pawn pawn = (Pawn) pieces[i];
                if (pawn.getKills() > 0) temp.add(pawn);
            }
        }
        Collections.sort(temp, new CompByKills(whoWon));
        printWithKills(temp);
        printAst();
        Arrays.sort(this.pieces, new CompByDist(whoWon));
        printWithDist();
        printAst();
        //System.out.println(Arrays.toString(pieces));
        HashMap<String, HashSet<String>> posHelper = new HashMap<>();
        for (ConcretePiece cp : pieces) {
            for (Position p : cp.getMoves()) {
                if (posHelper.get(p.toString()) == null) {
                    HashSet<String> hs = new HashSet<>();
                    hs.add(cp.toString());
                    posHelper.put(p.toString(), hs);
                } else {
                    posHelper.get(p.toString()).add(cp.toString());
                }
            }
        }
        List<Position> positions = new ArrayList<>();
        for (String s : posHelper.keySet()) {
            int nop = posHelper.get(s).size();
            if (nop > 1) {
                Position p = Position.stringToPosition(s);
                p.setUniquePieces(nop);
                positions.add(p);
            }
        }
        Collections.sort(positions, new PositionComp());
        printPositions(positions);
    }

    private void printPositions(List<Position> lop) {
        for (Position p : lop) {
            System.out.println(p.toString() + p.getUniquePieces() + " pieces");
        }
    }

    private void printWithDist() {
        for (ConcretePiece cp : this.pieces) {
            if (cp.getTotalDist() > 0) {
                System.out.println(cp + ": " + cp.getTotalDist() + " squares");
            }
        }
    }

    private void printWithKills(ArrayList<Pawn> arr) {
        for (Pawn p : arr) {
            System.out.println(p + ": " + p.getKills() + " kills");
        }
    }

    private void printWithMoves(ConcretePiece[] arr, int s, int e) {
        for (int i = s; i < e; i++) {
            if (pieces[i].getMoves().size() > 1) {
                System.out.println(pieces[i] + " : " + pieces[i].getMoves());
            }
        }
    }

    private void printAst() {
        for (int i = 0; i < 74; i++) System.out.print("*");
        System.out.println("*");
    }
}
