package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;

public class RecursiveAI extends Board {

    RecursiveAI parent = null;
    Board board = null;
    NodeGraph graph;

    RecursiveAI(Board board) {
        super(board);
        graph = new NodeGraph(board);
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
    		int depth = 1;
    		makeNodes(colour, depth, 'A');
//    		System.out.println("MIN-MAX ALPHA-BETA:");
//    		System.out.print("All values: \n[" );
//	    	for(int z = 0; z < graph.children.size(); z++) {
//	    		System.out.print(graph.children.get(z).getValue() + ", ");
//	    		//System.out.print(branching.get(z).toString());
//	    	}
//    		System.out.println("MIN-MAX ALPHA-BETA:");
    		NodeGraph moveNode = miniMax(graph, new NodeGraph(Integer.MIN_VALUE), new NodeGraph(Integer.MAX_VALUE), true);
//    		System.out.println("DONE MIN-MAX ALPHA-BETA:");
//    		System.out.println("MAX node to add is:" 
//									+ "\n-> Old Queen: " + Arrays.deepToString(moveNode.oldQueen.toArray())
//									+ "\n-> New Queen: " + Arrays.deepToString(moveNode.newQueen.toArray())
//									+ "\n-> New Arrow: " + Arrays.deepToString(moveNode.newArrow.toArray())
//									+ "\n-> Value: " + moveNode.value
//									+ "\n-> Board: " + moveNode.toString()
//								);
    		ArrayList<Integer> move = new ArrayList<Integer>(Arrays.asList(moveNode.oldQueen.get(0), moveNode.oldQueen.get(1),
																	moveNode.newQueen.get(0), moveNode.newQueen.get(1),
																	moveNode.newArrow.get(0), moveNode.newArrow.get(1)
																	));
        	System.out.println(Arrays.deepToString(move.toArray()));
//    		ArrayList<Integer> oldQueen = new ArrayList<Integer>(Arrays.asList(move.get(0), move.get(1)));
//			ArrayList<Integer> newQueen = new ArrayList<Integer>(Arrays.asList(move.get(2), move.get(3)));
//			ArrayList<Integer> newArrow = new ArrayList<Integer>(Arrays.asList(move.get(4), move.get(5)));
//			super.movePiece(oldQueen, newQueen, newArrow, colour);
    		return move;
    	}
		return null;
    }
    public int counter = 0;
    
    public NodeGraph miniMax(NodeGraph node, NodeGraph alpha, NodeGraph beta, boolean maximizingPlayer) {
//    	System.out.println("Counter: " + counter++);
    	if(node.children == null) {
//    		System.out.println("TERMINO");
    		return node;
    	}
    	
    	if(maximizingPlayer) {
    		NodeGraph best = new NodeGraph(Integer.MIN_VALUE);
    		for(int i = 0; i < node.children.size(); i++) {
//    			System.out.println("MAX iteration: " + i);
    			NodeGraph val = miniMax(node.children.get(i), alpha, beta, false);
//    			System.out.println("Node val = " +val.value);
    			if(val.value > best.value) best = val;
    			if(best.value > alpha.value) alpha = best;
    			if(best.value >= beta.value) {
//    				System.out.println("PRUNNING " + beta.value);
    				break;
    			}
    		}
//    		System.out.println("ALPHA BEST: " + alpha.value);
    		return best;
    	} else {
    		NodeGraph best = new NodeGraph(Integer.MAX_VALUE);
    		for(int i = 0; i < node.children.size(); i++) {
    			NodeGraph val = miniMax(node.children.get(i), alpha, beta, true);
    			
    			if(val.value < best.value) best = val;
    			if(best.value < beta.value) beta = best;
    			if(best.value <= alpha.value) break;
    		}
//    		System.out.println("BETA BEST: " + alpha.value);

    		return best;
    	}
    }
    
    public void makeNodes(int colour, int depth, char strategy) {
    	for(int i = 0; i < depth; i++) {
    		expandGraph(colour, graph, strategy, i);
    	}
    }
    
    public void expandGraph(int colour, NodeGraph node, char strategy, int depth) {
    	if(node.children == null) {
    		if(strategy == 'A') {
    			node.addChildren(nodeAttack(colour, node, depth));
    		}
    		return; 
    	}
    	for(int i = 0; i < node.children.size(); i++) {
    		expandGraph(colour, node.children.get(i), strategy, depth + 1);
    	}
    }
    //moves a queen based on minimizing opponents possible moves - ONE LEVEL
    public ArrayList<NodeGraph> nodeAttack(int colour, NodeGraph node, int depth) {
    	ArrayList<NodeGraph> branching = new ArrayList<NodeGraph>();
    	ArrayList<ArrayList<Integer>> queenCurr = node.getPositions(colour);
    	ArrayList<Integer> Qloc = null;
    	ArrayList<Integer> Aloc = null;
    	int whichQueen = 0;
    	int overallmin = Integer.MAX_VALUE;
    	
    	
    	System.out.println(Arrays.deepToString(queenCurr.toArray()));
    	for (int f = 0; f < 4; f++ ) {	
    		ArrayList<Integer> queenmoves = node.move.getMoves(node, queenCurr.get(f));
    		//DEBUGG
//    		System.out.println("\n" + f + "-> QUEEN:  ");
//        	System.out.println(Arrays.deepToString(queenCurr.get(f).toArray()) + ":  ");
//        	System.out.println(Arrays.deepToString(queenmoves.toArray()));

	    	for (int i = 0; i < 8; i++) { //for each direction < 8
	    		for (int j = 1; j <= queenmoves.get(i); j++) { //< queenmoves.get(i)
	    			//DEBUGG
//	    			System.out.println("Of " + queenmoves.get(i) + " Moving " + i + ", " + j + "times");
//	            	System.out.println(Arrays.deepToString(queenmoves.toArray()));


	    			Board brd = new Board();
	    			brd.clone(node);
	    			//DEBUGG
	    			System.out.println("Cloning the board: \n" + brd.toString());
	    			//track what the move is
					ArrayList<ArrayList<Integer>> oppBefore = getOppPos(colour);
	    			int sumBefore = 0;
					for (int m = 0; m < 4; m++) {
						ArrayList<Integer> oppmoves = brd.move.getMoves(node, oppBefore.get(m));
//    			    	System.out.println(Arrays.deepToString(opp.get(m).toArray()));
//				    	System.out.println(Arrays.deepToString(oppmoves.toArray()));

						for (int p = 0; p < 8; p++) {
							sumBefore += oppmoves.get(p);
						}
					}
	    			
	    			
	    			ArrayList<Integer> newQueen = whereTo(i, queenCurr.get(f), j);
//	    	    	System.out.println("New Queen position: " + Arrays.deepToString(newQueen.toArray()));

	    			
	    			brd.setTile(queenCurr.get(f).get(0), queenCurr.get(f).get(1), 0);
	    			brd.setTile(newQueen.get(0), newQueen.get(1), colour);
//	    			System.out.println("Move on cloned board from " + queenCurr.get(f).get(0) + ", " + queenCurr.get(f).get(1)
//	    								+ " to " + newQueen.get(0) + "," + newQueen.get(1) + "\n" + brd.toString());

	    			ArrayList<Integer> arrowmoves = brd.move.getMoves(brd, newQueen);
	    			for (int k = 0; k < 8; k++) { 
//	    				System.out.println("\n  ARROW MOVES:\n");
	    				for (int l = 1; l <= arrowmoves.get(k); l++) { //for each possible move
	    					Board brd2 = new Board();
	    					brd2.clone(node);
	    					ArrayList<Integer> newArrow =  whereTo(k, newQueen, l);
	    					brd2.movePiece(queenCurr.get(f), newQueen, newArrow, colour);
//	    	    			System.out.println("Arrow number " + k + ": \n" + brd2.toString());

	    					ArrayList<ArrayList<Integer>> opp = getOppPos(colour);
	    					
	    					int sumAfter = 0;
	    					for (int m = 0; m < 4; m++) {
	    						ArrayList<Integer> oppmoves = brd2.move.getMoves(brd2, opp.get(m));
//	    		            	System.out.println(Arrays.deepToString(oppmoves.toArray()));
//		    			    	System.out.println(Arrays.deepToString(opp.get(m).toArray()));
//	    				    	System.out.println(Arrays.deepToString(oppmoves.toArray()));

	    						for (int p = 0; p < 8; p++) {
	    							sumAfter += oppmoves.get(p);
	    						}
	    					}
	    					//DEBUGG
	    					System.out.println("Total moves before = " + sumBefore);
	    					System.out.println("Total moves  after= " + sumAfter);
	    					System.out.println("Restricted move moves = " + (sumBefore - sumAfter));
	    					System.out.println("New node to add is:" 
	    											+ "\n-> Old Queen: " + Arrays.deepToString(queenCurr.get(f).toArray())
	    											+ "\n-> New Queen: " + Arrays.deepToString(newQueen.toArray())
	    											+ "\n-> New Arrow: " + Arrays.deepToString(newArrow.toArray())
	    											+ "\n-> Value: " + (sumBefore - sumAfter)
	    											+ "\n-> Board: " + brd2.toString()
	    										);
	    					
	    					NodeGraph temp = new NodeGraph(queenCurr.get(f), newQueen, newArrow, (sumBefore - sumAfter), brd2, depth);
	    					branching.add(temp);
	    					System.out.print("All values: \n[" );
	    			    	for(int z = 0; z < branching.size(); z++) {
	    			    		System.out.print(branching.get(z).getValue() + ", ");
	    			    		//System.out.print(branching.get(z).toString());
	    			    	}
	    			    	System.out.println("] \n Size = " + branching.size() + "\n\n");
	    				}
	    			}
	    		}
	    	}
    	}
//    	System.out.println("] \n Size = " + branching.size() + "\n\n");
    	return branching;
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
    

    

}