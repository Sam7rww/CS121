import java.util.*;

public class Rook extends Piece {
    public Rook(Color c) {
        super(c);
    }
    // implement appropriate methods

    PiecePosProcessor processor = new PiecePosProcessor();

    public String toString() {
        Color c = this.color();
        if(c == Color.BLACK) return "br";
        else return "wr";
    }

    public List<String> moves(Board b, String loc) {
        List<String> target = new ArrayList<>();
        char row = loc.charAt(1);
        char col = loc.charAt(0);
        //down and up
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

        return target;
    }

}