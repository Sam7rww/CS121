import java.util.*;

public class Bishop extends Piece {
    public Bishop(Color c) {
        super(c);
    }
    // implement appropriate methods

    PiecePosProcessor processor = new PiecePosProcessor();

    public String toString() {
        Color c = this.color();
        if(c == Color.BLACK) return "bb";
        else return "wb";
    }

    public List<String> moves(Board b, String loc) {
        List<String> target = new ArrayList<>();
        char row = loc.charAt(1);
        char col = loc.charAt(0);

        //diagnol
        int step = 1;
        while(step<=7){
            String lu = ((char)(col - step)) + String.valueOf((char)(row+step));
            if(processor.checkTargetPos(lu)){
                //true,means the computed position is valid, next we should consider other chess
                Piece temp_piece = b.getPiece(lu);
                if(temp_piece == null){
                    target.add(lu);
                }else if(temp_piece.color() != this.color()){
                    target.add(lu);
                    break;
                }else{
                    break;
                }
            }
            step++;
        }

        step = 1;
        while(step<=7){
            String ld = ((char)(col - step)) + String.valueOf((char)(row-step));
            if(processor.checkTargetPos(ld)){
                //true,means the computed position is valid, next we should consider other chess
                Piece temp_piece = b.getPiece(ld);
                if(temp_piece == null){
                    target.add(ld);
                }else if(temp_piece.color() != this.color()){
                    target.add(ld);
                    break;
                }else{
                    break;
                }
            }
            step++;
        }

        step = 1;
        while(step<=7){
            String ru = ((char)(col + step)) + String.valueOf((char)(row+step));
            if(processor.checkTargetPos(ru)){
                //true,means the computed position is valid, next we should consider other chess
                Piece temp_piece = b.getPiece(ru);
                if(temp_piece == null){
                    target.add(ru);
                }else if(temp_piece.color() != this.color()){
                    target.add(ru);
                    break;
                }else{
                    break;
                }
            }
            step++;
        }

        step = 1;
        while(step<=7){
            String rd = ((char)(col + step)) + String.valueOf((char)(row-step));
            if(processor.checkTargetPos(rd)){
                //true,means the computed position is valid, next we should consider other chess
                Piece temp_piece = b.getPiece(rd);
                if(temp_piece == null){
                    target.add(rd);
                }else if(temp_piece.color() != this.color()){
                    target.add(rd);
                    break;
                }else{
                    break;
                }
            }
            step++;
        }

        target.remove(loc);
        return target;
    }

}