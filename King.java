public class King extends ConcretePiece{
    //data
    private static final String TYPE = "♔";
    //constructor
    public King(ConcretePlayer player, int id) {
        super(player, id);
    }
    @Override
    public String getType() {
        return TYPE;
    }
    //methods
    public String toString() {
        return "K7";
    }
}
