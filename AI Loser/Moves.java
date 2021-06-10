package ubc.cosc322;

import java.util.ArrayList;
import java.util.Collections;

/** Code for Amazons move class - finds and returns available moves
 * 
 * @author Team 05: Corey Bond, Kshitij Suri, Jun Kang, Alex Rogov, Jose Pena
 * 5/22/2021
 */

public class Moves {
	
	// Constants
	public static final int N = 0;
	public static final int NE = 1;
	public static final int E = 2;
	public static final int SE = 3;
	public static final int S = 4;
	public static final int SW = 5;
	public static final int W = 6;
	public static final int NW = 7;
	
	protected ArrayList<Integer> valid;
	
	// Constructor - creates a move object that holds an empty array of moves
	public Moves() {
		valid = new ArrayList<Integer>(Collections.nCopies(8, 0));
	}
	
   /** Returns an array of the number of spaces that can be moved in each direction, clockwise from N
	* @param state
	* @param pos
	*/
	public ArrayList<Integer> getMoves(Board state, ArrayList<Integer> pos) {
		valid.set(N, moveN(state, pos));
		valid.set(NE, moveNE(state, pos));
		valid.set(E, moveE(state, pos));
		valid.set(SE, moveSE(state, pos));
		valid.set(S, moveS(state, pos));
		valid.set(SW, moveSW(state, pos));
		valid.set(W, moveW(state, pos));
		valid.set(NW, moveNW(state, pos));
		return valid;
	}
	
   /** Checks if a specified move is valid
	* @param state
	* @param start
	* @param fin
	*/	
	public boolean validMove(Board state, ArrayList<Integer> start, ArrayList<Integer> fin) {
		int r = fin.get(0) - start.get(0);
		int c = fin.get(1) - start.get(1);
		if (r < 0 && c == 0) {int val = moveN(state, start); return (Math.abs(r) <= val);}
		else if (r < 0 && c > 0 && Math.abs(r) == c) {int val = moveNE(state, start); return (Math.abs(r) <= val);}
		else if (r == 0 && c > 0) {int val = moveE(state, start); return (c <= val);}
		else if (r > 0 && c > 0 && r == c) {int val = moveSE(state, start); return (c <= val);}
		else if (r > 0 && c == 0) {int val = moveS(state, start); return (r <= val);}
		else if (r > 0 && c < 0 && Math.abs(c) == r) {int val = moveSW(state, start); return (r <= val);}
		else if (r == 0 && c < 0) {int val = moveW(state, start); return (Math.abs(c) <= val);}
		else if (r < 0 && c < 0 && r == c) {int val = moveNW(state, start); return (Math.abs(c) <= val);}
		else return false;
	}
	
   /** Checks how many spaces North are valid for movement
	* @param state
	* @param pos
	*/	
	public int moveN(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveNR(state, row, col, 0);
	}	
   /** Recursive helper method
	* @param state
	* @param row
	* @param col
	* @param val
	*/	
	public int moveNR(Board state, int row, int col, int val) {
		row -= 1;
		if(row <= 0 || state.getTile(row, col) != 0)
			return val;
		else {
			val += 1;
			return moveNR(state, row, col, val);
		}
	}
	
   /** Checks how many spaces North-East are valid for movement
	* @param state
	* @param pos
	*/	
	public int moveNE(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveNER(state, row, col, 0);
	}
   /** Recursive helper method
	* @param state
	* @param row
	* @param col
	* @param val
	*/	
	public int moveNER(Board state, int row, int col, int val) {
		row -= 1;
		col += 1;
		if(row <= 0 || col > 10)
			return val;
		if(state.getTile(row, col) != 0)
			return val;
		else {
			val += 1;
			return moveNER(state, row, col, val);
		}
	}
	
   /** Checks how many spaces East are valid for movement
	* @param state
	* @param pos
	*/	
	public int moveE(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveER(state, row, col, 0);
	}
   /** Recursive helper method
	* @param state
	* @param row
	* @param col
	* @param val
	*/	
	public int moveER(Board state, int row, int col, int val) {
		col += 1;
		if(col > 10)
			return val;
		if(state.getTile(row, col) != 0)
			return val;
		else {
			val += 1;
			return moveER(state, row, col, val);
		}
	}
	
   /** Checks how many spaces South-East are valid for movement
	* @param state
	* @param pos
	*/	
	public int moveSE(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveSER(state, row, col, 0);
	}
   /** Recursive helper method
	* @param state
	* @param row
	* @param col
	* @param val
	*/	
	public int moveSER(Board state, int row, int col, int val) {
		row += 1;
		col += 1;
		if(row > 10 || col > 10)
			return val;
		if(state.getTile(row, col) != 0)
			return val;
		else {
			val += 1;
			return moveSER(state, row, col, val);
		}
	}
	
   /** Checks how many spaces South are valid for movement
	* @param state
	* @param pos
	*/	
	public int moveS(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveSR(state, row, col, 0);
	}
   /** Recursive helper method
	* @param state
	* @param row
	* @param col
	* @param val
	*/	
	public int moveSR(Board state, int row, int col, int val) {
		row += 1;
		if(row > 10)
			return val;
		if(state.getTile(row, col) != 0)
			return val;		
		else {
			val += 1;
			return moveSR(state, row, col, val);
		}
	}
	
   /** Checks how many spaces South-West are valid for movement
	* @param state
	* @param pos
	*/	
	public int moveSW(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveSWR(state, row, col, 0);
	}
   /** Recursive helper method
	* @param state
	* @param row
	* @param col
	* @param val
	*/	
	public int moveSWR(Board state, int row, int col, int val) {
		row += 1;
		col -= 1;
		if(row > 10 || col <= 0)
			return val;
		if(state.getTile(row, col) != 0)
			return val;		
		else {
			val += 1;
			return moveSWR(state, row, col, val);
		}
	}
	
   /** Checks how many spaces West are valid for movement
	* @param state
	* @param pos
	*/	
	public int moveW(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveWR(state, row, col, 0);
	}
   /** Recursive helper method
	* @param state
	* @param row
	* @param col
	* @param val
	*/	
	public int moveWR(Board state, int row, int col, int val) {
		col -= 1;
		if(col <= 0 || state.getTile(row, col) != 0)
			return val;
		else {
			val += 1;
			return moveWR(state, row, col, val);
		}
	}
	
   /** Checks how many spaces North-West are valid for movement
	* @param state
	* @param pos
	*/	
	public int moveNW(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveNWR(state, row, col, 0);
	}
   /** Recursive helper method
	* @param state
	* @param row
	* @param col
	* @param val
	*/	
	public int moveNWR(Board state, int row, int col, int val) {
		row -= 1;
		col -= 1;
		if(row <= 0 || col <= 0 || state.getTile(row, col) != 0)
			return val;
		else {
			val += 1;
			return moveNWR(state, row, col, val);
		}
	}
	
	
}