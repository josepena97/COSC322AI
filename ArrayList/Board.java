package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/** Code for Amazons board class - partially adapted from Billy Spelchan's TicTacToe class
 * 
 * @author Team 05: Corey, Suri, Jun, Alex, Jose
 * 5/22/2021
 */

public class Board {
	//constants
	public static final int POS_AVAILABLE = 0;
	public static final int POS_BLACK = 1;
	public static final int POS_WHITE = 2;
	public static final int POS_ARROW = 3;
	public static final int N = 121;
	public static final ArrayList<Integer> B_POS = new ArrayList<>(Arrays.asList(15, 18, 45, 54));
	public static final ArrayList<Integer> W_POS = new ArrayList<>(Arrays.asList(78, 87, 114, 117));
	
	protected ArrayList<Integer> board;
	protected Moves move;
	
	//constructors
	//basic constructor
	public Board() {
		board = new ArrayList<>(N);
		for (int i = 0; i < 4; i++) {
			board.set(B_POS.get(i), 1);
			board.set(W_POS.get(i), 2);
		}
		move = new Moves();
	}
	
	//cloning constructor
	public Board(Board source) {
		this();
		Collections.copy(this.board, source.board);
	}
	
	//methods
	
	//clone method to copy board state
	public void clone(Board source) {
		Collections.copy(this.board, source.board);
	}
	
	//get tile method to convert row, col to 1d array notation
	public int getTile(int row, int col) {
		return board.get(row*11+col);
	}
	
	//returns symbolic representation of tile contents
	public String getTileSymbol(int row, int col) {
		int tile = getTile(row, col);
		if (tile == POS_BLACK) return "B";
		if (tile == POS_WHITE) return "W";
		if (tile == POS_ARROW) return "*";
		return ""+(row*11+col+1);
	}
	
	//returns symbolic representation of tile contents
	public String getTileSymbolLinear(int pos) {
		if (board.get(pos) == POS_BLACK) return "B";
		if (board.get(pos) == POS_WHITE) return "W";
		if (board.get(pos) == POS_ARROW) return "*";
		return "-";
	}
	
	//sets tile at row, col to specified value
	public void setTile(int row, int col, int value) {
		board.set(row*11+col, value);
	}
	
	//sets tile based on 1D CLI input number
	public void setTileFromCharIndex(int index, int value) {
		board.set(index-1, value);
	}
	
	//checks for winner in current game VALIDATE THIS
	public boolean checkWin(int colour) {
		ArrayList<ArrayList<Integer>> pieces = this.getPositions(colour);
		
		for(int i = 0; i < 4; i++) {
			ArrayList<Integer> moves = move.getMoves(this, pieces.get(i));
			for (int j = 0; j < 8; j++) {
				if (moves.get(j) > 0)
					return false;
			}
		}
		//if the array of possible moves for the player is empty - then the other team wins! Booooooo, or yay?
		if (colour == 1)
			System.out.println("Winner is: White");
		else
			System.out.println("Winner is: Black");
		return true;
	}
	
	//moves piece
	public void movePiece(ArrayList<Integer> queenStartLoc, ArrayList<Integer> queenFinLoc, ArrayList<Integer> arrowLoc, int colour) {
		Board temp = new Board(this);
		if (move.validMove(temp, queenStartLoc, queenFinLoc)) {
			temp.setTile(queenStartLoc.get(0), queenStartLoc.get(1), 0);
			temp.setTile(queenFinLoc.get(0), queenFinLoc.get(1), colour);
			if (move.validMove(temp, queenFinLoc, arrowLoc)) {
				temp.setTile(arrowLoc.get(0), arrowLoc.get(1), POS_ARROW);
				this.clone(temp);
			}else {
				System.out.println("Invalid arrow move! Move not completed");//end game
			}
		}else 
			System.out.println("Invalid queen move! Move not completed");//end game
	}
	
	public ArrayList<Integer> randomMove(int colour) {
		//creates a random move based on piece positions and valid moves
		if(!this.checkWin(colour)) {
			int piece = (int) Math.floor(Math.random()*4); //CONTINUE: need to pick a new piece if the piece has no valid moves
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
			movePiece(pos.get(piece), queen, arrow, colour);
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
	
	public ArrayList<Integer> random(Board board, int colour, ArrayList<Integer> pos) {
		ArrayList<Integer> valid = move.getMoves(board, pos);
		int howFar = 0;
		int direction;
		do {
			direction = (int) Math.floor(Math.random()*8);
			howFar = (int) Math.floor(Math.random()*(valid.get(direction)+1));
		}
		while (howFar == 0);
		ArrayList<Integer> queen = new ArrayList<Integer>(2);
		if (direction == Moves.N) {queen.set(0, pos.get(0)-howFar); queen.set(1, pos.get(1));}
		else if (direction == Moves.NE) {queen.set(0, pos.get(0)-howFar); queen.set(1, pos.get(1)+howFar);}
		else if (direction == Moves.E) {queen.set(0, pos.get(0)); queen.set(1, pos.get(1)+howFar);}
		else if (direction == Moves.SE) {queen.set(0, pos.get(0)+howFar); queen.set(1, pos.get(1)+howFar);}
		else if (direction == Moves.S) {queen.set(0, pos.get(0)+howFar); queen.set(1, pos.get(1));}
		else if (direction == Moves.SW) {queen.set(0, pos.get(0)+howFar); queen.set(1, pos.get(1)-howFar);}
		else if (direction == Moves.W) {queen.set(0, pos.get(0)); queen.set(1, pos.get(1)-howFar);}
		else if (direction == Moves.NW) {queen.set(0, pos.get(0)-howFar); queen.set(1, pos.get(1)-howFar);}
		return queen;
	}
	
	//return row, col positions of pieces of specified colour
	public ArrayList<ArrayList<Integer>> getPositions(int colour) {
		ArrayList<ArrayList<Integer>> pos = new ArrayList<ArrayList<Integer>>();
		int count = 0;
		for (int i = 12; i < N; i++) {
			if (board.get(i) == colour) {
				pos.get(count).set(0, i/11);
				pos.get(count).set(1, i%11);
				count++;
			}
		}
		return pos;
	}
	
	//method to convert board to printable string
	public String toString() {
		String ret = "The current game state is: \n= = = = = = = = = = = =\n";
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