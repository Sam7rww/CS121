import java.util.List;

public class Test {

    // Run "java -ea Test" to run with assertions enabled (If you run
    // with assertions disabled, the default, then assert statements
    // will not execute!)

    public static void test1() {
	Board b = Board.theBoard();
	Piece.registerPiece(new PawnFactory());
	Piece p = Piece.createPiece("bp");
	b.addPiece(p, "a3");
	assert b.getPiece("a3") == p;
    }
    
    public static void main(String[] args) {
		tests();
//		tests2();
    }

	public static void tests2(){
		Piece.registerPiece(new KingFactory());
		Piece.registerPiece(new QueenFactory());
		Piece.registerPiece(new KnightFactory());
		Piece.registerPiece(new BishopFactory());
		Piece.registerPiece(new RookFactory());
		Piece.registerPiece(new PawnFactory());
		Board.theBoard().registerListener(new Logger());

		Board b = Board.theBoard();
		b.addPiece(Piece.createPiece("bk"),"f5");
		List<String> kingmoves = b.getPiece("f5").moves(b,"f5");
		for(String a:kingmoves){
			System.out.println("king can move to:"+a);
		}

	}

    public static void tests(){
		Piece.registerPiece(new KingFactory());
		Piece.registerPiece(new QueenFactory());
		Piece.registerPiece(new KnightFactory());
		Piece.registerPiece(new BishopFactory());
		Piece.registerPiece(new RookFactory());
		Piece.registerPiece(new PawnFactory());
		Board.theBoard().registerListener(new Logger());

		Board b = Board.theBoard();
		//white
		b.addPiece(Piece.createPiece("wk"),"e1");
		b.addPiece(Piece.createPiece("wq"),"d1");
		b.addPiece(Piece.createPiece("wb"),"c1");
		b.addPiece(Piece.createPiece("wn"),"b1");
		b.addPiece(Piece.createPiece("wr"),"a1");
		b.addPiece(Piece.createPiece("wp"),"h2");
		b.addPiece(Piece.createPiece("wp"),"f2");

		//black
		b.addPiece(Piece.createPiece("bk"),"e8");
		b.addPiece(Piece.createPiece("bq"),"d8");
		b.addPiece(Piece.createPiece("bb"),"f8");
		b.addPiece(Piece.createPiece("bn"),"b8");
		b.addPiece(Piece.createPiece("br"),"a8");
		b.addPiece(Piece.createPiece("br"),"h8");
		b.addPiece(Piece.createPiece("bp"),"c7");
		b.addPiece(Piece.createPiece("bp"),"d7");
		b.addPiece(Piece.createPiece("bp"),"f7");
		b.addPiece(Piece.createPiece("bp"),"h7");

		//move king
		try{
			b.movePiece("e1","d1");
		}catch (BadMove e){
			System.out.println("this is bad king move!");
			e.printStackTrace();
		}
		b.movePiece("e1","e2");
		assert b.getPiece("e1")==null;
		assert b.getPiece("e2").toString().equals("wk");

		//move queen
		b.movePiece("d1","b3");
		assert b.getPiece("d1")==null;
		assert b.getPiece("b3").toString().equals("wq");
		b.movePiece("b3","f7");
		assert b.getPiece("b3")==null;
		assert b.getPiece("f7").toString().equals("wq");

		//move Bishop
		b.movePiece("f8","c5");
		assert b.getPiece("f8")==null;
		assert b.getPiece("c5").toString().equals("bb");

		//move Knight
		b.movePiece("b8","c6");
		assert b.getPiece("b8")==null;
		assert b.getPiece("c6").toString().equals("bn");

		//move Rock
		b.movePiece("a1","a8");
		assert b.getPiece("a1")==null;
		assert b.getPiece("a8").toString().equals("wr");

		//move Pawn
		b.movePiece("f7","e6");
		b.movePiece("d7","e6");
		assert b.getPiece("d7")==null;
		assert b.getPiece("e6").toString().equals("bp");
		b.movePiece("h7","h5");
		assert b.getPiece("h7")==null;
		assert b.getPiece("h5").toString().equals("bp");
		try{
			b.movePiece("h5","h3");
		}catch (BadMove e){
			System.out.println("this is bad pawn move!");
			e.printStackTrace();
		}
		b.movePiece("h5","h4");
		assert b.getPiece("h5")==null;
		assert b.getPiece("h4").toString().equals("bp");

		System.out.println("Final board:");
		Board.theBoard().iterate(new BoardPrinter());

		b.clear();
		System.out.println("Final board:");
		Board.theBoard().iterate(new BoardPrinter());

	}

}