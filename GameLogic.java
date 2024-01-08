public class GameLogic implements PlayableLogic{
    //data
    private static final int BOARD_SIZE = 11;
    private ConcretePlayer playerOne = new ConcretePlayer(true);
    private ConcretePlayer playerTwo = new ConcretePlayer(false);

    //private ConcretePlayer currentPlayer;

    boolean secondPlayerTurn;
    private ConcretePiece[][] board = new ConcretePiece[BOARD_SIZE][BOARD_SIZE];
    //Constructor
    public GameLogic() {
        // Initate player two pieces on board
        for (int j : new int[]{0, 10}) {
        for (int i = 3; i<=7; i++) {
                this.board[i][j] = new Pawn(playerTwo);
            }
        }
        for (int j : new int[]{0, 10}) {
            for (int i = 3; i <= 7; i++) {
                this.board[j][i] = new Pawn(playerTwo);
            }
        }
        this.board[5][1] = new Pawn(playerTwo);
        this.board[1][5] = new Pawn(playerTwo);
        this.board[9][5] = new Pawn(playerTwo);
        this.board[5][9] = new Pawn(playerTwo);
        // Initate player one pieces
        for (int i = 3; i<=7; i++) {
            if (i==5) continue;
            this.board[i][5] = new Pawn(playerOne);
        }
        for (int i : new  int[] {4, 6}) {
            for (int j : new int[] {4,5,6}) {
                this.board[j][i] = new Pawn(playerOne);
            }
        }
        this.board[5][3] = new Pawn(playerOne);
        this.board[5][7] = new Pawn(playerOne);
        this.board[5][5] = new King(playerOne);

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
        this.board[b._x][b._y] = poa;
        this.board[a._x][a._y] = null;
        secondPlayerTurn = !secondPlayerTurn; // switch turns
        return true;
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
}
