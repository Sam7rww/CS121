import java.util.*;

abstract public class Piece {

    private Color pieceC;

    private static HashMap<Character,PieceFactory> factories = new HashMap<>();

    public Piece(Color c){
        pieceC = c;
    }

    public static void registerPiece(PieceFactory pf) {
        char symbol = pf.symbol();
        factories.put(symbol,pf);
    }

    public static Piece createPiece(String name) {
        char colorP = name.charAt(0);
        char typeP = name.charAt(1);
        Color piececolor;
        if(colorP == 'b'){
            piececolor = Color.BLACK;
        }else if(colorP == 'w'){
            piececolor = Color.WHITE;
        }else{
            throw new BadColor();
        }
        PieceFactory pieceFactory = factories.get(typeP);
        return pieceFactory.create(piececolor);
    }

    public Color color() {
	// You should write code here and just inherit it in
	// subclasses. For this to work, you should know
	// that subclasses can access superclass fields.
//	throw new UnsupportedOperationException();
        return pieceC;
    }

    abstract public String toString();

    abstract public List<String> moves(Board b, String loc);
}