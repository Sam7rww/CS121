import java.util.*;

public class Queen extends Piece {
    public Queen(Color c) {
        super(c);
    }
    // implement appropriate methods

    PiecePosProcessor processor = new PiecePosProcessor();

    public String toString() {
        Color c = this.color();
        if(c == Color.BLACK) return "bq";
        else return "wq";
    }

    public List<String> moves(Board b, String loc) {
        List<String> target = new ArrayList<>();
        char row = loc.charAt(1);
        char col = loc.charAt(0);
        //up and down
        for(char i=(char)(row-1);i>='1';i--){
            String pos = col+String.valueOf(i);
            Piece temp = b.getPiece(pos);
            if(temp == null){
                target.add(pos);
            }else if(temp.color() != this.color()){
                target.add(pos);
                break;
            }else{
                break;
            }
        }

        for(char i=(char)(row+1);i<='8';i++){
            String pos = col+String.valueOf(i);
            Piece temp = b.getPiece(pos);
            if(temp == null){
                target.add(pos);
            }else if(temp.color() != this.color()){
                target.add(pos);
                break;
            }else{
                break;
            }
        }

        //left and right
        for(char i=(char)(col-1);i>='a';i--){
            String pos = i+String.valueOf(row);
            Piece temp = b.getPiece(pos);
            if(temp == null){
                target.add(pos);
            }else if(temp.color() != this.color()){
                target.add(pos);
                break;
            }else{
                break;
            }
        }

        for(char i=(char)(col+1);i<='h';i++){
            String pos = i+String.valueOf(row);
            Piece temp = b.getPiece(pos);
            if(temp == null){
                target.add(pos);
            }else if(temp.color() != this.color()){
                target.add(pos);
                break;
            }else{
                break;
            }
        }

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

        return target;
    }

}