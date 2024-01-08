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
        if (poa == null || pob != null) return false;
        Player cuPl;
        if (this.isSecondPlayerTurn()) cuPl = playerTwo;
         else cuPl = playerOne;
        Player aOwner = poa.getOwner();
        if (cuPl != aOwner) return false;
        this.board[b._x][b._y] = poa;
        this.board[a._x][a._y] = null;
        secondPlayerTurn = !secondPlayerTurn;
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
