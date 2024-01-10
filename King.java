public class King extends ConcretePiece{
    //data
    private static final String TYPE = "♔";

    //constructor


    public King(ConcretePlayer player) {
        super(player);
    }

    @Override
    public String getType() {
        return TYPE;
    }
    //methods
}
