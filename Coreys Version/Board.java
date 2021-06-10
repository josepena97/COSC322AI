package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/** Code for Amazons board class - partially adapted from Billy Spelchan's TicTacToe class
 * 
 * @author Team 05: Corey Bond, Kshitij Suri, Jun Kang, Alex Rogov, Jose Pena
 * 5/22/2021
 */

public class Board {
	
	// Constants
	public static final int POS_AVAILABLE = 0;
	public static final int POS_BLACK = 1;
	public static final int POS_WHITE = 2;
	public static final int POS_ARROW = 3;
	public static final int N = 121;
	public static final ArrayList<Integer> W_POS = new ArrayList<>(Arrays.asList(15, 18, 45, 54));
	public static final ArrayList<Integer> B_POS = new ArrayList<>(Arrays.asList(78, 87, 114, 117));
//	public static int counter;
	
	protected ArrayList<Integer> board;
	protected Moves move;
	
	// Constructors
	
	// Basic constructor
	public Board() {
		board = new ArrayList<Integer>(Collections.nCopies(N,0));
		for (int i = 0; i < 4; i++) {
			board.set(B_POS.get(i), POS_BLACK);
			board.set(W_POS.get(i), POS_WHITE);
		}
//		this.counter = 0;
		move = new Moves();
	}
	
   /** Cloning constructor
	* @param source
	*/
	public Board(Board source) {
		this();
		Collections.copy(this.board, source.board);
	}
	
	//methods
	
   /** Clone method to copy board state
	* @param source
	*/
	public void clone(Board source) {
		Collections.copy(this.board, source.board);
//		counter = source.counter;
	}
		
   /** Get tile method to convert row, col to 1D array index
	* @param row
	* @param col
	*/
	public int getTile(int row, int col) {
		return board.get(row*11+col);
	}
	
   /** Method to retrieve symbolic representation of tile contents from coordinates
	* @param row
	* @param col
	*/
	public String getTileSymbol(int row, int col) {
		int tile = getTile(row, col);
		if (tile == POS_BLACK) return "B";
		if (tile == POS_WHITE) return "W";
		if (tile == POS_ARROW) return "*";
		return ""+(row*11+col+1);
	}
	
   /** Method to retrieve symbolic representation of tile contents from index
	* @param index
	*/
	public String getTileSymbolLinear(int index) {
		if (board.get(index) == POS_BLACK) return "B";
		if (board.get(index) == POS_WHITE) return "W";
		if (board.get(index) == POS_ARROW) return "*";
		return "-";
	}
	
   /** Method to set tile at provided row, col coordinates to specified value
	* @param row
	* @param col
	* @param value
	*/
	public void setTile(int row, int col, int value) {
		board.set(row*11+col, value);
	}
	
   /** Method to set tile at provided index to specified value
	* @param index
	* @param value
	*/
	public void setTileFromCharIndex(int index, int value) {
		board.set(index-1, value);
	}
	
   /** Method to retrieve board array
	*/
	public ArrayList<Integer> getBoard() {
		return this.board;
	}
	
   /** Method to retrieve board object
	*/
	public Board getBoardObject() {
		return this;
	}
	
   /** Method to determine if the game has a winner - based on provided player having remaining moves
	* @param colour
	*/
	public boolean checkWin(int colour) {
		ArrayList<ArrayList<Integer>> pieces = this.getPositions(colour);
		
		for(int i = 0; i < 4; i++) {
			ArrayList<Integer> moves = move.getMoves(this, pieces.get(i));
			for (int j = 0; j < 8; j++) {
				if (moves.get(j) > 0)
					return false;
			}
		}
		
		//if the array of possible moves for the player is empty - then the other team wins
		if (colour == POS_WHITE)
			System.out.println("\n----Winner is: Black----\n");
		else
			System.out.println("\nWinner is: White----\n");
		return true;
	}
	
   /** Method to move piece based on provided start and finish coordinates
	* @param queenStartLoc
	* @param queenFinLoc
	* @param arrowLoc
	* @param colour
	*/
	public void movePiece(ArrayList<Integer> queenStartLoc, ArrayList<Integer> queenFinLoc, ArrayList<Integer> arrowLoc, int colour) {
		Board temp = new Board(this);
		if (move.validMove(temp, queenStartLoc, queenFinLoc) && this.getTile(queenStartLoc.get(0), queenStartLoc.get(1)) == colour) {
			temp.setTile(queenStartLoc.get(0), queenStartLoc.get(1), 0);
			temp.setTile(queenFinLoc.get(0), queenFinLoc.get(1), colour);
			if (move.validMove(temp, queenFinLoc, arrowLoc)) {
				temp.setTile(arrowLoc.get(0), arrowLoc.get(1), POS_ARROW);
				this.clone(temp);
			}else {
				System.out.println("\n----Invalid arrow move! Move not completed----\n");
			}
		}else 
			System.out.println("\n----Invalid queen move! Move not completed----\n");
//		this.counter++;
	}
	
   /** Method to move specified colour randomly based on available positions and valid moves
	* @param colour
	*/
	public ArrayList<Integer> randomMove(int colour) {
		if(!this.checkWin(colour)) { //proceed if game is not yet won
			int piece = (int) Math.floor(Math.random()*4);
			ArrayList<ArrayList<Integer>> pos = this.getPositions(colour);
			int sum = 0;
			while(sum == 0) {
				ArrayList<Integer> moves = move.getMoves(this, pos.get(piece));
				for (int j = 0; j < 8; j++) {
					sum += moves.get(j);
				}
				if (sum != 0) {break;}
				piece = (int) Math.floor(Math.random()*4);
			}
			Board temp = new Board(this);
			ArrayList<Integer> queen = random(temp, colour, pos.get(piece));
			temp.setTile(pos.get(piece).get(0), pos.get(piece).get(1), 0);
			temp.setTile(queen.get(0), queen.get(1), colour);
			ArrayList<Integer> arrow = random(temp, colour, queen);
			ArrayList<Integer> ret = new ArrayList<Integer>(6);
			ret.add(pos.get(piece).get(0));
			ret.add(pos.get(piece).get(1));
			ret.add(queen.get(0));
			ret.add(queen.get(1));
			ret.add(arrow.get(0));
			ret.add(arrow.get(1));
			return ret;
		}
		//else end game
		return null;
	}
	
   /** Helper method for random move generator
	* @param board
	* @param colour
	* @param pos
	*/
	public ArrayList<Integer> random(Board board, int colour, ArrayList<Integer> pos) {
		ArrayList<Integer> valid = move.getMoves(board, pos);
		int howFar = 0;
		int direction;
		do {
			direction = (int) Math.floor(Math.random()*8);
			howFar = (int) Math.floor(Math.random()*(valid.get(direction)+1));
		}
		while (howFar == 0);
		ArrayList<Integer> queen = new ArrayList<Integer>();
		if (direction == Moves.N) {queen.add(pos.get(0)-howFar); queen.add(pos.get(1));}
		else if (direction == Moves.NE) {queen.add(pos.get(0)-howFar); queen.add(pos.get(1)+howFar);}
		else if (direction == Moves.E) {queen.add(pos.get(0)); queen.add(pos.get(1)+howFar);}
		else if (direction == Moves.SE) {queen.add(pos.get(0)+howFar); queen.add(pos.get(1)+howFar);}
		else if (direction == Moves.S) {queen.add(pos.get(0)+howFar); queen.add(pos.get(1));}
		else if (direction == Moves.SW) {queen.add(pos.get(0)+howFar); queen.add(pos.get(1)-howFar);}
		else if (direction == Moves.W) {queen.add(pos.get(0)); queen.add(pos.get(1)-howFar);}
		else if (direction == Moves.NW) {queen.add(pos.get(0)-howFar); queen.add(pos.get(1)-howFar);}
		return queen;
	}
	
   /** Return row, column coordinates of all pieces of specified colour
	* @param board
	* @param colour
	* @param pos
	*/
	public ArrayList<ArrayList<Integer>> getPositions(int colour) {
		ArrayList<ArrayList<Integer>> pos = new ArrayList<ArrayList<Integer>>();
		int count = 0;
		for (int i = 12; i < N; i++) {
			if (board.get(i) == colour && count <= 4) {//changed here
				int row = i/11;
				int column = i%11;
				pos.add(new ArrayList<Integer>(Arrays.asList(i/11, i%11)));
				count++;
			}
		}
		return pos;
	}
	
   /** Converts current board to printable string
	*/
	public String toString() {
		String ret = "\n= = = = = = = = = = = =\n";
		for (int i = 11; i < N; i++) {
			if (i == 12)				ret += "| " + this.getTileSymbolLinear(i) +  " ";
			else if (i == N-1)   		ret += this.getTileSymbolLinear(i) +  " |\n= = = = = = = = = = = =\n";
			else if ((i+1) % 11 == 0)	ret += this.getTileSymbolLinear(i) +  " |\n| ";
			else if (i%11 == 0)			ret += "";
			else						ret += this.getTileSymbolLinear(i) +  " ";
		}
		return ret;
	}
}