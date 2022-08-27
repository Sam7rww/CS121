import java.util.*;

public class Pawn extends Piece {
    public Pawn(Color c) {
        super(c);
    }
    // implement appropriate methods

    PiecePosProcessor processor = new PiecePosProcessor();

    public String toString() {
        Color c = this.color();
        if(c == Color.BLACK) return "bp";
        else return "wp";
    }

    public List<String> moves(Board b, String loc) {
        List<String> target = new ArrayList<>();
        char row = loc.charAt(1);
        char col = loc.charAt(0);
	    if(this.color() == Color.BLACK){//black
	        //move to row1
            if(row=='7'){
                //can move two step
                String go1 = col+String.valueOf((char) (row-1));
                Piece go1_piece = b.getPiece(go1);
                String go2 = col+String.valueOf((char) (row-2));
                Piece go2_piece = b.getPiece(go2);
                if(go1_piece == null) target.add(go1);
                if(go2_piece == null) target.add(go2);

            }else{
                //can only move one step
                String go1 = col+String.valueOf((char) (row-1));
                if(processor.checkTargetPos(go1)){
                    Piece go1_piece = b.getPiece(go1);
                    if(go1_piece == null) target.add(go1);
                }
            }
            //capture move
            String oppo1 = (char)(col-1)+String.valueOf((char) (row-1));
            if(processor.checkTargetPos(oppo1)){
                Piece oppo1_piece = b.getPiece(oppo1);
                if(oppo1_piece!=null && oppo1_piece.color()==Color.WHITE) target.add(oppo1);
            }
            String oppo2 = (char)(col+1)+String.valueOf((char) (row-1));
            if(processor.checkTargetPos(oppo2)){
                Piece oppo2_piece = b.getPiece(oppo2);
                if(oppo2_piece!=null && oppo2_piece.color()==Color.WHITE) target.add(oppo2);
            }

        }else{//white
	        //move to row8
            if(row=='2'){
                //can move two step
                String go1 = col+String.valueOf((char) (row+1));
                Piece go1_piece = b.getPiece(go1);
                String go2 = col+String.valueOf((char) (row+2));
                Piece go2_piece = b.getPiece(go2);
                if(go1_piece == null) target.add(go1);
                if(go2_piece == null) target.add(go2);

            }else{
                //can only move one step
                String go1 = col+String.valueOf((char) (row+1));
                if(processor.checkTargetPos(go1)){
                    Piece go1_piece = b.getPiece(go1);
                    if(go1_piece == null) target.add(go1);
                }
            }
            //capture move
            String oppo1 = (char)(col-1)+String.valueOf((char) (row+1));
            if(processor.checkTargetPos(oppo1)){
                Piece oppo1_piece = b.getPiece(oppo1);
                if(oppo1_piece!=null && oppo1_piece.color()==Color.BLACK) target.add(oppo1);
            }
            String oppo2 = (char)(col+1)+String.valueOf((char) (row+1));
            if(processor.checkTargetPos(oppo2)){
                Piece oppo2_piece = b.getPiece(oppo2);
                if(oppo2_piece!=null && oppo2_piece.color()==Color.BLACK) target.add(oppo2);
            }
        }

	    return target;
    }

}