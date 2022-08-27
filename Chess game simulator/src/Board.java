import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<BoardListener> listeners = new ArrayList<>();

    private PieceProcessor processor = new Adapter();

    private static Board instance = null;

    private Piece[][] pieces = new Piece[8][8];

    private Board() { }
    
    public static Board theBoard() {
        if(instance == null) instance = new Board();
	    return instance; // implement this
    }

    // Returns piece at given loc or null if no such piece
    // exists
    public Piece getPiece(String loc) {
        if(!processor.check(loc)){
            throw new BadLocation();
        }
        Piece p = pieces[processor.first(loc)][processor.second(loc)];
        return p;
    }

    public void addPiece(Piece p, String loc) {
        if(!processor.check(loc)){
            throw new BadLocation();
        }
        int f = processor.first(loc);
        int s = processor.second(loc);
        Piece temploc = pieces[f][s];
        if(temploc!=null){
            throw new BadLocation();
        }
        pieces[f][s] = p;
    }

    public void movePiece(String from, String to) {
        if(!processor.check(from)){  //check from
            throw new BadLocation();
        }
        int from_f = processor.first(from);
        int from_s = processor.second(from);
        if(!processor.check(to)){  //check to
            throw new BadLocation();
        }
        int to_f = processor.first(to);
        int to_s = processor.second(to);

        Piece fromPiece = pieces[from_f][from_s];
        if(fromPiece == null) throw new BadMove();  //check piece is exist

        List<String> movepos = fromPiece.moves(theBoard(),from);
        if(movepos.contains(to)){//move is legal
            this.move(from,to,fromPiece);
            Piece target = pieces[to_f][to_s];
            if(target!=null){
                this.capture(fromPiece,target);
            }
            pieces[to_f][to_s] = pieces[from_f][from_s];
            pieces[from_f][from_s] = null;

        }else{
            throw new BadMove();
        }

    }

    public void clear() {
        for(int i =0;i<8;i++){
            for(int j=0;j<8;j++){
                pieces[i][j] = null;
            }
        }
    }

    public void registerListener(BoardListener bl) {
        listeners.add(bl);
    }

    public void removeListener(BoardListener bl) {
        listeners.remove(bl);

    }

    public void removeAllListeners() {
        listeners.clear();
    }

    private void move(String from, String to, Piece p){
        for(BoardListener lis:listeners){
            lis.onMove(from,to,p);
        }
    }

    private void capture(Piece attacker, Piece captured){
        for(BoardListener lis:listeners){
            lis.onCapture(attacker,captured);
        }
    }

    public void iterate(BoardExternalIterator bi) {
	    for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                Piece p = pieces[i][j];
                String pos = processor.indexToStr(i,j);
                bi.visit(pos,p);
            }
        }
    }
}