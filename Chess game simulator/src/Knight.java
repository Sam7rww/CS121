import java.util.*;

public class Knight extends Piece {
    public Knight(Color c) {
        super(c);
//        throw new UnsupportedOperationException();
    }
    // implement appropriate methods

    PiecePosProcessor processor = new PiecePosProcessor();

    public String toString() {
        Color c = this.color();
        if(c == Color.BLACK) return "bn";
        else return "wn";
//	throw new UnsupportedOperationException();
    }

    public List<String> moves(Board b, String loc) {
        List<String> target = new ArrayList<>();
        char row = loc.charAt(1);
        char col = loc.charAt(0);
        //there are total 8 point knight can go
        String lu1 = ((char)(col - 1)) + String.valueOf((char)(row+2));
        addIntoList(target,lu1,b);
        String lu2 = ((char)(col - 2)) + String.valueOf((char)(row+1));
        addIntoList(target,lu2,b);

        String ld1 = ((char)(col - 1)) + String.valueOf((char)(row-2));
        addIntoList(target,ld1,b);
        String ld2 = ((char)(col - 2)) + String.valueOf((char)(row-1));
        addIntoList(target,ld2,b);

        String ru1 = ((char)(col + 1)) + String.valueOf((char)(row+2));
        addIntoList(target,ru1,b);
        String ru2 = ((char)(col + 2)) + String.valueOf((char)(row+1));
        addIntoList(target,ru2,b);

        String rd1 = ((char)(col + 1)) + String.valueOf((char)(row-2));
        addIntoList(target,rd1,b);
        String rd2 = ((char)(col + 2)) + String.valueOf((char)(row-1));
        addIntoList(target,rd2,b);

        return target;
    }

    public void addIntoList(List<String> list, String pos,Board b){
        if(processor.checkTargetPos(pos)){
            //true,means the computed position is valid, next we should consider other chess
            Piece temp_piece = b.getPiece(pos);
            if(temp_piece == null){
                list.add(pos);
            }else if(temp_piece.color() != this.color()){
                list.add(pos);
            }else{
                return;
            }
        }
    }

}