package ubc.cosc322;

import java.util.ArrayList;

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
	public static final int[] B_POS = {15, 18, 45, 54};
	public static final int[] W_POS = {78, 87, 114, 117};
	
	protected int[] board;
	protected Moves move;
	
	//constructors
	//basic constructor
	public Board() {
		board = new int[N];
		for (int i = 0; i < 4; i++) {
			board[B_POS[i]] = 1;
			board[W_POS[i]] = 2;
		}
		move = new Moves();
	}
	
	//cloning constructor
	public Board(Board source) {
		this();
		System.arraycopy(source.board, 0, board, 0, N);
	}
	
	//methods
	
	//clone method to copy board state
	public void clone(Board source) {
		System.arraycopy(source.board, 0, board, 0, N);
	}
	
	//get tile method to convert row, col to 1d array notation
	public int getTile(int row, int col) {
		return board[row*11+col];
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
		if (board[pos] == POS_BLACK) return "B";
		if (board[pos] == POS_WHITE) return "W";
		if (board[pos] == POS_ARROW) return "*";
		return "-";
	}
	
	//sets tile at row, col to specified value
	public void setTile(int row, int col, int value) {
		board[row*11+col] = value;
	}
	
	//sets tile based on 1D CLI input number
	public void setTileFromCharIndex(int index, int value) {
		board[index-1] = value;
	}
	
	//checks for winner in current game VALIDATE THIS
	public boolean checkWin(int colour) {
		int[][] pieces = this.getPositions(colour);
		for(int i = 0; i < 4; i++) {
			int[] moves = move.getMoves(this, pieces[i]);
			for (int j = 0; j < 8; j++) {
				if (moves[j] > 0)
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
	public void movePiece(int[] queenStartLoc, int[] queenFinLoc, int[] arrowLoc, int colour) {
		Board temp = new Board(this);
		if (move.validMove(temp, queenStartLoc, queenFinLoc)) {
			temp.setTile(queenStartLoc[0], queenStartLoc[1], 0);
			temp.setTile(queenFinLoc[0], queenFinLoc[1], colour);
			if (move.validMove(temp, queenFinLoc, arrowLoc)) {
				temp.setTile(arrowLoc[0], arrowLoc[1], POS_ARROW);
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
			int[][] pos = this.getPositions(colour);
			int sum = 0;
			while(sum == 0) {
				int[] moves = move.getMoves(this, pos[piece]);
				for (int j = 0; j < 8; j++) {
					sum += moves[j];
				}
				if (sum != 0) {break;}
				piece = (int) Math.floor(Math.random()*4);
			}
			Board temp = new Board(this);
			int[] queen = random(temp, colour, pos[piece]);
			temp.setTile(pos[piece][0], pos[piece][1], 0);
			temp.setTile(queen[0], queen[1], colour);
			int[] arrow = random(temp, colour, queen);
			movePiece(pos[piece], queen, arrow, colour);
			ArrayList<Integer> ret = new ArrayList<Integer>(6);
			ret.add(pos[piece][0]);
			ret.add(pos[piece][1]);
			ret.add(queen[0]);
			ret.add(queen[0]);
			ret.add(arrow[0]);
			ret.add(arrow[0]);
			return ret;
		}
		//else end game
		return null;
	}
	
	public int[] random(Board board, int colour, int[] pos) {
		int[] valid = move.getMoves(board, pos);
		int howFar = 0;
		int direction;
		do {
			direction = (int) Math.floor(Math.random()*8);
			howFar = (int) Math.floor(Math.random()*(valid[direction]+1));
		}
		while (howFar == 0);
		int[] queen = new int[2];
		if (direction == Moves.N) {queen[0] = pos[0]-howFar; queen[1] = pos[1];}
		else if (direction == Moves.NE) {queen[0] = pos[0]-howFar; queen[1] = pos[1]+howFar;}
		else if (direction == Moves.E) {queen[0] = pos[0]; queen[1] = pos[1]+howFar;}
		else if (direction == Moves.SE) {queen[0] = pos[0]+howFar; queen[1] = pos[1]+howFar;}
		else if (direction == Moves.S) {queen[0] = pos[0]+howFar; queen[1] = pos[1];}
		else if (direction == Moves.SW) {queen[0] = pos[0]+howFar; queen[1] = pos[1]-howFar;}
		else if (direction == Moves.W) {queen[0] = pos[0]; queen[1] = pos[1]-howFar;}
		else if (direction == Moves.NW) {queen[0] = pos[0]-howFar; queen[1] = pos[1]-howFar;}
		return queen;
	}
	
	//return row, col positions of pieces of specified colour
	public int[][] getPositions(int colour) {
		int[][] pos = new int[4][2];
		int count = 0;
		for (int i = 12; i < N; i++) {
			if (board[i] == colour) {
				pos[count][0] = i/11;
				pos[count][1] = i%11;
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
