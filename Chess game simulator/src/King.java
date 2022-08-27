import java.util.*;

public class King extends Piece {
    public King(Color c) {
        super(c);
//        throw new UnsupportedOperationException();
    }
    // implement appropriate methods

    PiecePosProcessor processor = new PiecePosProcessor();

    public String toString() {
        Color c = this.color();
        if(c == Color.BLACK) return "bk";
        else return "wk";
//	throw new UnsupportedOperationException();
    }

    public List<String> moves(Board b, String loc) {
        List<String> target = new ArrayList<>();
        char row = loc.charAt(1);
        char col = loc.charAt(0);
        char lower_row = (char)( row - 1);
        char lower_col = (char)( col - 1);
        for(char i = lower_row; i<(char)(lower_row+3);i++){
            for(char j = lower_col; j<(char)(lower_col+3);j++){
                if(processor.checkTargetPos(j+String.valueOf(i))){
                    //true,means the computed position is valid, next we should consider other chess
                    String temp = j+String.valueOf(i);
                    Piece temp_piece = b.getPiece(temp);
                    if(temp_piece == null){
                        target.add(temp);
                    }else if(temp_piece.color() != this.color()){
                        target.add(temp);
                    }
                }
            }

        }
        if(target.contains(loc)) target.remove(loc);
        return target;
    }

//    public boolean checkTargetPos(String pos){
//        char row = pos.charAt(1);
//        char col = pos.charAt(0);
//        if(row>'8' || row<'1') return false;
//        if(col<'a' || col>'h') return false;
//        return true;
//    }

}