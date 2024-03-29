package ubc.cosc322;

import java.util.ArrayList;
import java.util.Collections;

public class Moves {
	
	//constants
	public static final int N = 0;
	public static final int NE = 1;
	public static final int E = 2;
	public static final int SE = 3;
	public static final int S = 4;
	public static final int SW = 5;
	public static final int W = 6;
	public static final int NW = 7;
	
	protected ArrayList<Integer> valid;
	
	//creates a move object that holds an empty array of moves
	public Moves() {
		valid = new ArrayList<Integer>(Collections.nCopies(8, 0));
	}
	
	//returns an array of the number of spaces that can be moved in each direction, clockwise from N
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
	
	//checks if a specified move is valid
	public boolean validMove(Board state, ArrayList<Integer> start, ArrayList<Integer> fin) {
		//int[] val = getMoves(state, start); Could do this if we want - may take more time?
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
	
	//checks how spaces north are valid for movement
	public int moveN(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveNR(state, row, col, 0);
	}	
	//recursive helper method
	public int moveNR(Board state, int row, int col, int val) {
		row -= 1;
		if(row <= 0 || state.getTile(row, col) != 0)
			return val;
		else {
			val += 1;
			return moveNR(state, row, col, val);
		}
	}
	
	//checks how spaces north-east are valid for movement
	public int moveNE(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveNER(state, row, col, 0);
	}
	//recursive helper method
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
	
	//checks how spaces east are valid for movement
	public int moveE(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveER(state, row, col, 0);
	}
	//recursive helper method
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
	
	//checks how spaces south-east are valid for movement
	public int moveSE(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveSER(state, row, col, 0);
	}
	//recursive helper method
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
	
	//checks how spaces south are valid for movement
	public int moveS(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveSR(state, row, col, 0);
	}
	//recursive helper method
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
	
	//checks how spaces south-west are valid for movement
	public int moveSW(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveSWR(state, row, col, 0);
	}
	//recursive helper method
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
	
	//checks how spaces west are valid for movement
	public int moveW(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveWR(state, row, col, 0);
	}
	//recursive helper method
	public int moveWR(Board state, int row, int col, int val) {
		col -= 1;
		if(col <= 0 || state.getTile(row, col) != 0)
			return val;
		else {
			val += 1;
			return moveWR(state, row, col, val);
		}
	}
	
	//checks how spaces north-west are valid for movement
	public int moveNW(Board state, ArrayList<Integer> pos) {
		int row = pos.get(0);
		int col = pos.get(1);
		return moveNWR(state, row, col, 0);
	}
	//recursive helper method
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