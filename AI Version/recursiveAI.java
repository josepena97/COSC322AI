package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;

public class RecursiveAI extends Board {

    RecursiveAI parent = null;
    Board board = null;

    RecursiveAI(Board board) {
        super();
        this.board = board;
    }
    RecursiveAI(RecursiveAI parent, Board board) {
        super(parent);
        this.parent = parent;
    }

//    private int mode = 0;    //0 = make a border 1 = recursively go back and forth saving as much space as the pieces can
    
    //checks the mode, sends back queen's curr pos, queen's next location, arrow location, colour
//    public void infoGrabber(ArrayList<Integer> queenCurr, int mode, int colour) {
//    	ArrayList<Integer> queenNext = ai(queenCurr, mode, colour);
//    	ArrayList<Integer> arrow = ai(queenCurr, mode, colour);
//    	super.movePiece(queenCurr, queenNext, arrow, colour);
//    }
    
    public ArrayList<Integer> ai(int colour) {
    	if (super.counter < 15) {
    		return firstAI(colour);
    	}
    	else if (super.counter < 30) {
    		return secondAI(colour);
    	}
    	else 
    		return lastAI(colour);
    }
    
    //moves a queen based on minimizing opponents possible moves - ONE LEVEL
    public ArrayList<Integer> firstAI(int colour) {
    	ArrayList<ArrayList<Integer>> queenCurr = super.getPositions(colour);
    	//ArrayList<Integer> sum = null;
    	ArrayList<Integer> Qloc = null;
    	ArrayList<Integer> Aloc = null;
    	int whichQueen = 0;
    	int overallmin = Integer.MAX_VALUE;
    	
    	//System.out.println(Arrays.deepToString(queenCurr.toArray()));
    	for (int f = 0; f < 4; f++ ) {	
    		ArrayList<Integer> queenmoves = super.move.getMoves(this, queenCurr.get(f));
    		//System.out.println("\n" + f + "-> QUEEN:  ");
        	//System.out.println(Arrays.deepToString(queenCurr.get(f).toArray()) + ":  ");
        	//System.out.println(Arrays.deepToString(queenmoves.toArray()));

	    	for (int i = 0; i < 8; i++) { //for each direction
	    		for (int j = 1; j <= queenmoves.get(i); j++) { //for each possible move
	    			//System.out.println("Moving " + i + ", " + j + "times");

	    			Board brd = new Board();
	    			brd.clone(this);
	    			//System.out.println("Cloning the board: \n" + brd.toString());
	    			//track what the move is
	    			ArrayList<Integer> newQueen = whereTo(i, queenCurr.get(f), j);
	    	    	//System.out.println("New Queen position: " + Arrays.deepToString(newQueen.toArray()));

	    			
	    			brd.setTile(queenCurr.get(f).get(0), queenCurr.get(f).get(1), 0);
	    			brd.setTile(newQueen.get(0), newQueen.get(1), colour);
	    			//System.out.println("Move on cloned board from " + queenCurr.get(f).get(0) + ", " + queenCurr.get(f).get(1)
	    			//					+ " to " + newQueen.get(0) + "," + newQueen.get(1) + "\n" + brd.toString());

	    			ArrayList<Integer> arrowmoves = brd.move.getMoves(brd, newQueen);
	    			for (int k = 0; k < 8; k++) { 
	    				for (int l = 1; l <= arrowmoves.get(k); l++) { //for each possible move
	    					Board brd2 = new Board();
	    					brd2.clone(this);
	    					ArrayList<Integer> newArrow =  whereTo(k, newQueen, l);
	    					brd2.movePiece(queenCurr.get(f), newQueen, newArrow, colour);
	    	    			//System.out.println("Arrow number " + k + ": \n" + brd2.toString());

	    					ArrayList<ArrayList<Integer>> opp = getOppPos(colour);
	    					int min = Integer.MAX_VALUE;
	    					for (int m = 0; m < 4; m++) {
	    						ArrayList<Integer> oppmoves = brd2.move.getMoves(brd2, opp.get(m));
		    			    	//System.out.println(Arrays.deepToString(opp.get(m).toArray()));
	    				    	//System.out.println(Arrays.deepToString(oppmoves.toArray()));

	    						for (int p = 0; p < 8; p++) {
	    							min += oppmoves.get(p);
	    						}
	    					}
	    					if (min < overallmin) {
	    						overallmin = min;
	    						whichQueen = f;
	    						Qloc = newQueen;
	    						Aloc = newArrow;
	    					}
	    				}
	    			}
	    		}
	    	}
    	}
    	super.movePiece(queenCurr.get(whichQueen), Qloc, Aloc, colour);
    	ArrayList<Integer> play = new ArrayList<Integer>();
    	play.add(queenCurr.get(whichQueen).get(0)); 
    	play.add(queenCurr.get(whichQueen).get(1));
    	play.add(Qloc.get(0));
    	play.add(Qloc.get(1));
    	play.add(Aloc.get(0));
    	play.add(Aloc.get(1));
    	
    	return play;
   }
    
    // extend firstAI to further levels
    public ArrayList<Integer> secondAI(int colour) {
    	return null;
    }
    
    //find which move maximizes future moves
    public ArrayList<Integer> lastAI(int colour) {
    	return null;
    }
    
    public ArrayList<Integer> whereTo(int direction, ArrayList<Integer> pos, int howFar) {
    	ArrayList<Integer> newpos = new ArrayList<Integer>();
		if (direction == Moves.N) {newpos.add(pos.get(0)-howFar); newpos.add(pos.get(1));}
		else if (direction == Moves.NE) {newpos.add(pos.get(0)-howFar); newpos.add(pos.get(1)+howFar);}
		else if (direction == Moves.E) {newpos.add(pos.get(0)); newpos.add(pos.get(1)+howFar);}
		else if (direction == Moves.SE) {newpos.add(pos.get(0)+howFar); newpos.add(pos.get(1)+howFar);}
		else if (direction == Moves.S) {newpos.add(pos.get(0)+howFar); newpos.add(pos.get(1));}
		else if (direction == Moves.SW) {newpos.add(pos.get(0)+howFar); newpos.add(pos.get(1)-howFar);}
		else if (direction == Moves.W) {newpos.add(pos.get(0)); newpos.add(pos.get(1)-howFar);}
		else if (direction == Moves.NW) {newpos.add(pos.get(0)-howFar); newpos.add(pos.get(1)-howFar);}
		return newpos;
    }
    
    public ArrayList<ArrayList<Integer>> getOppPos(int colour) {
    	int colour2 = 0;
    	if (colour==1) colour2 = 2;
    	else if (colour==2) colour2 = 1;
    	ArrayList<ArrayList<Integer>> opp = super.getPositions(colour2);
    	return opp;
    }
    
    public ArrayList<Integer> getClosest(ArrayList<Integer> queenCurr, int colour) {
    	int colour2 = 0;
    	if (colour==1) colour2 = 2;
    	else if (colour==2) colour2 = 1;
    	ArrayList<ArrayList<Integer>> opp = super.getPositions(colour2);
    	int distance = 20;
    	int closest = 0;
    	for (int i = 0; i < 4; i++) {
    		int row = Math.abs(opp.get(i).get(0) - queenCurr.get(0));
    		int col = Math.abs(opp.get(i).get(1) - queenCurr.get(1));
    		int temp = row+col;
    		if (temp < distance) {
    			distance = temp;
    			closest = i;
    		}
    	}
    	return opp.get(closest);
    }
    

}