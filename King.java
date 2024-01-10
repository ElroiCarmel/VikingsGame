public class King extends ConcretePiece{
    //data
    private static final String TYPE = "♔";

    //constructor


    public King(ConcretePlayer player) {
        super(player);
    }

    @Override
    public static String getType() {
        return this.TYPE;
    }
    //methods
}
