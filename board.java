package ubc.cosc322;

/** Code for Amazons board class - partially adapted from Billy Spelchan's TicTacToe class
 * 
 * @author Team 05: Corey, Suri, Jun, Alex, Jose
 * 5/22/2021
 */

public class board {
	//constants
	public static final int POS_AVAILABLE = 0;
	public static final int POS_BLACK = 1;
	public static final int POS_WHITE = 2;
	public static final int POS_ARROW = 3;
	public static final int N = 121;
	public static final int[] B_POS = {15, 18, 45, 54};
	public static final int[] W_POS = {78, 87, 114, 117};
	
	protected int[] board;
	
	//constructors
	//basic constructor
	public board() {
		board = new int[N];
		for (int i = 0; i < 4; i++) {
			board[B_POS[i]] = 1;
			board[W_POS[i]] = 2;
		}
	}
	
	//cloning constructor
	public board(board src) {
		this();
		System.arraycopy(src.board, 0, board, 0, N);
	}
	
	//methods
	
}
