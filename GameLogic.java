public class GameLogic implements PlayableLogic{
    //data
    private static final int BOARD_SIZE = 11;
    private ConcretePlayer playerOne = new ConcretePlayer(true);
    private ConcretePlayer playerTwo = new ConcretePlayer(false);

    private ConcretePiece[] pieces = new ConcretePiece[32];

    //private ConcretePlayer currentPlayer;

    private boolean secondPlayerTurn;
    private ConcretePiece[][] board = new ConcretePiece[BOARD_SIZE][BOARD_SIZE];
    //Constructor
    public GameLogic() {
        for (int i = 0; i<19; i++) {
            pieces[i] = new Pawn(playerTwo, i+1);
        }
        for (int i = 19; i<31; i++) {
            pieces[i] = new Pawn(playerOne, i-18);
        }
        pieces[31] = new King(playerOne, 7);
        // Initate player two pieces on board
        board[3][0] = pieces[0];
        board[4][0] = pieces[1];
        board[5][0] = pieces[2];
        board[6][0] = pieces[3];
        board[7][0] = pieces[4];
        board[5][1] = pieces[5];
        board[0][3] = pieces[6];
        board[10][3] = pieces[7];
        board[0][4] = pieces[8];
        board[10][4] = pieces[9];

//        for (int j : new int[]{0, 10}) {
//        for (int i = 3; i<=7; i++) {
//                this.board[i][j] = new Pawn(playerTwo);
//            }
//        }
//        for (int j : new int[]{0, 10}) {
//            for (int i = 3; i <= 7; i++) {
//                this.board[j][i] = new Pawn(playerTwo);
//            }
//        }
//        this.board[5][1] = new Pawn(playerTwo);
//        this.board[1][5] = new Pawn(playerTwo);
//        this.board[9][5] = new Pawn(playerTwo);
//        this.board[5][9] = new Pawn(playerTwo);
//        // Initiate player one pieces
//        for (int i = 3; i<=7; i++) {
//            if (i==5) continue;
//            this.board[i][5] = new Pawn(playerOne);
//        }
//        for (int i : new  int[] {4, 6}) {
//            for (int j : new int[] {4,5,6}) {
//                this.board[j][i] = new Pawn(playerOne);
//            }
//        }
//        this.board[5][3] = new Pawn(playerOne);
//        this.board[5][7] = new Pawn(playerOne);
//        this.board[5][5] = new King(playerOne);

        secondPlayerTurn = true;
        //this.currentPlayer = playerTwo;
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
            min  = Math.min(a._y, b._y);
            max = Math.max(a._y, b._y);
            for (int s = min+1; s<max; s++) {
                if (this.board[a._x][s] != null) return false;
            }
        }
        if (a._y == b._y) {
            min  = Math.min(a._x, b._x);
            max = Math.max(a._x, b._x);
            for (int s = min+1; s<max; s++) {
                if (this.board[s][a._y] != null) return false;
            }
        }
        // check for pawn attempts to get to corner
        if (!(this.board[a._x][a._y] instanceof King) && isCorner(b)) return false;
        //make the move
        this.board[b._x][b._y] = poa;
        if (!(poa instanceof King)) {
            updateKills(b);
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
        check = new Position(x, y-1);
        if (inRange(check) && getPieceAtPosition(check) != null && getPieceAtPosition(check).getOwner() != currentPlayer) {
            if (y-1 == 0 || (this.board[x][y-2]!= null && this.board[x][y-2].getOwner() == currentPlayer)) {
                this.board[check._x][check._y] = null;
                ans+=1;
            }
        }
        // Down
        check = new Position(x, y+1);
        if (inRange(check) && getPieceAtPosition(check) != null && getPieceAtPosition(check).getOwner() != currentPlayer) {
            if (y+1 == 10 || (this.board[x][y+2]!= null && this.board[x][y+2].getOwner() == currentPlayer)) {
                this.board[check._x][check._y] = null;
                ans+=1;
            }
        }
        // Right
        check = new Position(x+1, y);
        if (inRange(check) && getPieceAtPosition(check) != null && getPieceAtPosition(check).getOwner() != currentPlayer) {
            if (x+1 == 10 || (this.board[x+2][y] != null && this.board[x+2][y].getOwner() == currentPlayer)) {
                this.board[check._x][check._y] = null;
                ans += 1;
            }
        }
        // Left
        check = new Position(x-1, y);
        if (inRange(check) && getPieceAtPosition(check) != null && getPieceAtPosition(check).getOwner() != currentPlayer) {
            if (x-1 == 0 || (this.board[x-2][y] != null && this.board[x-2][y].getOwner() == currentPlayer)) {
                this.board[check._x][check._y] = null;
                ans += 1;
            }
        }
       // ConcretePiece neighbour = this.board[x+1]
//        Position[] neigh = new Position[4];
//        neigh[0]  = new Position(x+1, y);
//        neigh[1]  = new Position(x-1, y);
//        neigh[2]  = new Position(x, y+1);
//        neigh[3]  = new Position(x, y-1);
//        for (Position cuPo : neigh) {
//            if (cuPo)
//        }
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
        //
        return false;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return secondPlayerTurn;
    }

    @Override
    public void reset() {

    }

    @Override
    public void undoLastMove() {

    }

    @Override
    public int getBoardSize() {
        return this.BOARD_SIZE;
    }
    private boolean isCorner(Position p) {
        for (int i : new int[] {0, GameLogic.BOARD_SIZE-1}) {
            for (int j : new int[] {0, GameLogic.BOARD_SIZE-1}) {
                if (p._x == i && p._y == j) return true;
            }
        }
        return false;
    }
}
