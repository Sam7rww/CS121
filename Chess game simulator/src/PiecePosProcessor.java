public class PiecePosProcessor {
    public int getfirstDimention(String pos){
        char first = pos.charAt(1);
        int res = Character.getNumericValue(first);
        return --res;
    }

    public int getSecondDimention(String pos){
        char sec = pos.charAt(0);
        char temp = (char) (sec-48);
        int res = Character.getNumericValue(temp);
        return --res;
    }

    public boolean checkPos(String pos){
        char fir = pos.charAt(0);
        char sec = pos.charAt(1);
        return (fir >= 'a' && fir <= 'h') && (sec>='1' && sec<='8');
    }

    public String indexTo(int f,int s){
        char a = (char) ((char) s+97);
        char b = (char) ((char) f+49);
        return a+String.valueOf(b);
    }

    /*
    in piece moving, check whether the computed position is valid. e.g a1 -> a0 is not valid
     */
    public boolean checkTargetPos(String pos){
        char row = pos.charAt(1);
        char col = pos.charAt(0);
        if(row>'8' || row<'1') return false;
        if(col<'a' || col>'h') return false;
        return true;
    }
}
