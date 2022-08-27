import java.io.*;
import java.util.*;

public class Chess {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java Chess layout moves");
		}
		Piece.registerPiece(new KingFactory());
		Piece.registerPiece(new QueenFactory());
		Piece.registerPiece(new KnightFactory());
		Piece.registerPiece(new BishopFactory());
		Piece.registerPiece(new RookFactory());
		Piece.registerPiece(new PawnFactory());
		Board.theBoard().registerListener(new Logger());
		// args[0] is the layout file name
		// args[1] is the moves file name
		// Put your code to read the layout file and moves files
		// here.
		Board b = Board.theBoard();
		Chess chess = new Chess();
//		List<String> layouts = chess.readfile("D:\\Java Project\\ChessBorad_121\\layout");
//		List<String> moves = chess.readfile("D:\\Java Project\\ChessBorad_121\\moves");
		List<String> layouts = chess.readfile(args[0]);
		List<String> moves = chess.readfile(args[1]);
		try {
			chess.handleLayout(layouts,b);
			chess.handleMove(moves,b);
		}catch (Exception e){
			System.out.println("error in handle Piece");
			e.printStackTrace();
		}

		// Leave the following code at the end of the simulation:
		System.out.println("Final board:");
		Board.theBoard().iterate(new BoardPrinter());
	}

	private List<String> readfile(String filename){
		List<String> list = new ArrayList<String>();
		try {
			File file = new File(filename);
			if(file.exists() && file.isFile()){
//				BufferedReader in = new BufferedReader(new FileReader(filename));
//				String str;
//				while ((str = in.readLine()) != null) {
//					list.add(str);
//				}
//				in.close();
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;

				while ((lineTxt = bufferedReader.readLine()) != null)
				{
					list.add(lineTxt);
				}
				bufferedReader.close();
				read.close();

			}else {
				System.out.println("can't find file");
			}

		}catch (Exception e){
			System.out.println("error in reading Layout");
			e.printStackTrace();
		}
		return list;
	}

	private void handleLayout(List<String> lists,Board b) throws Exception {
		for(String str:lists){
			if(str.charAt(0) != '#'){
				String[] layout = str.split("="); //layout[0]:position layout[1]:type
				if(layout.length !=2) throw new Exception();
				b.addPiece(Piece.createPiece(layout[1].trim()),layout[0].trim());
			}
		}

	}

	private void handleMove(List<String> lists,Board b) throws Exception{
		for(String str:lists){
			if(str.charAt(0) != '#'){
				String[] move = str.split("-"); //move[0]:from move[1]:to
				if(move.length !=2) throw new Exception();
				b.movePiece(move[0].trim(),move[1].trim());
			}
		}

	}


}