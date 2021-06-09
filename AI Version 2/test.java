package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;

public class test {
	public static void main(String[] args) {
		Board practice = new Board();
		System.out.println(practice.toString());

		//practice.setTile(4,5,2);
		//practice.setTileFromCharIndex(57, 1);
//		Board ver2 = new Board(practice);
//		System.out.println(ver2.toString());
//		System.out.println(practice.getTile(1,4));
//		System.out.println(practice.getTileSymbol(1,4));
//		ver2.movePiece(new int[] {1, 7},new int[] {8,3}, new int[] {9,4}, 1);
//		System.out.println(ver2.toString());
//		Moves move = new Moves();
//		int[] queen = new int[] {10,4};
//		int[] temp = move.getMoves(ver2, queen);
//		System.out.println("Number of valid spaces the queen at (" + queen[0] + ", " + queen[1] + ") can move: ");
//		System.out.println("North: " + temp[0] + "\nNorth East: " + temp[1]);
//		System.out.println("East: " + temp[2] + "\nSouth East: " + temp[3]);
//		System.out.println("South: " + temp[4] + "\nSouth West: " + temp[5]);
//		System.out.println("West: " + temp[6] + "\nNorth West: " + temp[7]);
//		System.out.println(move.validMove(ver2, queen, new int[] {9, 4}));
//		System.out.println(move.validMove(ver2, queen, new int[] {10, 6}));
//		int[][] pos = practice.getPositions(1); //black positions
//		for (int i = 0; i < 4; i++)
//			System.out.println("(" + pos[i][0] + ", " + pos[i][1] + ")");
		for(int i = 0; i < 70; i++){
			RecursiveAI player = new RecursiveAI(practice);

			if(!practice.checkWin(1)) {
				System.out.println("Iteration number " + (i+1));
				player.clone(practice);
				ArrayList<Integer> move = player.ai(1);
				System.out.println("test.java");
				ArrayList<Integer> oldQueen = new ArrayList<Integer>(Arrays.asList(move.get(0), move.get(1)));
				ArrayList<Integer> newQueen = new ArrayList<Integer>(Arrays.asList(move.get(2), move.get(3)));
				ArrayList<Integer> newArrow = new ArrayList<Integer>(Arrays.asList(move.get(4), move.get(5)));
				practice.movePiece(oldQueen, newQueen, newArrow, 1);
				System.out.println(practice.toString());
			}else {
				break;
			}
			if(!practice.checkWin(2)) {
				ArrayList<Integer> move = practice.randomMove(2);
				ArrayList<Integer> oldQueen = new ArrayList<Integer>(Arrays.asList(move.get(0), move.get(1)));
				ArrayList<Integer> newQueen = new ArrayList<Integer>(Arrays.asList(move.get(2), move.get(3)));
				ArrayList<Integer> newArrow = new ArrayList<Integer>(Arrays.asList(move.get(4), move.get(5)));
				practice.movePiece(oldQueen, newQueen, newArrow, 2);
				System.out.println(practice.toString());
			}else {
				break;
			}
			
		}
		//System.out.println(practice.toString());
		
	}
}