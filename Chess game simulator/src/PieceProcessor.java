public interface PieceProcessor {
    /*
    return the first dimention of the Board Array
     */
    public int first(String pos);

    /*
    return the second dimention of the Board Array
     */
    public int second(String pos);

    /*
    check the position is valid
     */
    public boolean check(String pos);

    /*
    the index of array to normal String of location
     */
    public String indexToStr(int f,int s);
}
