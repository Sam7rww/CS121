public class Adapter implements PieceProcessor{

    PiecePosProcessor processor = new PiecePosProcessor();

    @Override
    public int first(String pos) {
        return processor.getfirstDimention(pos);
    }

    @Override
    public int second(String pos) {
        return processor.getSecondDimention(pos);
    }

    @Override
    public boolean check(String pos) {
        return processor.checkPos(pos);
    }

    @Override
    public String indexToStr(int f, int s) {
        return processor.indexTo(f,s);
    }
}
