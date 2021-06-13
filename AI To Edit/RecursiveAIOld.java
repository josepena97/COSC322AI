package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;

/** Code for Amazons node graph class - builds and extends a node graph of states
 * 
 * @author Team 05: Jose Pena, Corey Bond, Kshitij Suri, Jun Kang, Alex Rogov
 * 6/7/2021
 */

public class RecursiveAIOld extends Board {

	// Variables
    public RecursiveAIOld parent = null;
    public Board board = null;
    public NodeGraphOld graph;
    public int minmaxcounter = 1;
    /** Constructor with board definition
 	* @param board
	*/
    RecursiveAIOld(Board board) {
        super(board);
        graph = new NodeGraphOld(board);
    }
    
    /** Constructor with board and parent definition
 	* @param parent
 	* @param board
	*/
    RecursiveAIOld(RecursiveAIOld parent, Board board) {
        super(parent);
        this.parent = parent;
    }

    /** AI Method that calls relevant AI algorithm depending on how far along the game is
 	* @param parent
 	* @param board
	*/    
    public ArrayList<Integer> ai(int colour, int counter) {
    	System.out.println("AI is running counter " + counter);
    	
    	if (counter < 5) {
    		int depth = 1;
    		double scale = 1;
    		makeNodes(colour, depth, 'A', scale);
//    		printNodeGraphOld(graph, 1);
//    		//System.out.println("MIN-MAX ALPHA-BETA:");
//    		//System.out.print("All values: \n[" );
//	    	for(int z = 0; z < graph.children.size(); z++) {
//	    		//System.out.print(graph.children.get(z).getValue() + ", ");
//	    		////System.out.print(branching.get(z).toString());
//	    	}
//    		//System.out.println("MIN-MAX ALPHA-BETA:");
    		NodeGraphOld moveNode = miniMax(graph, new NodeGraphOld(-Double.MAX_VALUE, "Alpha"), new NodeGraphOld(Double.MAX_VALUE, "Beta"), true);
//    		System.out.println("DONE MIN-MAX ALPHA-BETA:");
//    		System.out.println("MAX node to add is:" 
//									+ "\n-> Old Queen: " + Arrays.deepToString(moveNode.oldQueen.toArray())
//									+ "\n-> New Queen: " + Arrays.deepToString(moveNode.newQueen.toArray())
//									+ "\n-> New Arrow: " + Arrays.deepToString(moveNode.newArrow.toArray())
//									+ "\n-> Value: " + moveNode.value
//									+ "\n Name: " + moveNode.name
//									+ "\n-> Board: " + moveNode.toString()
//    								);
    		
    		ArrayList<Integer> move = new ArrayList<Integer>(Arrays.asList(moveNode.oldQueen.get(0), moveNode.oldQueen.get(1),
																	moveNode.newQueen.get(0), moveNode.newQueen.get(1),
																	moveNode.newArrow.get(0), moveNode.newArrow.get(1)
																	));
//        	//System.out.println(Arrays.deepToString(move.toArray()));
//    		ArrayList<Integer> oldQueen = new ArrayList<Integer>(Arrays.asList(move.get(0), move.get(1)));
//			ArrayList<Integer> newQueen = new ArrayList<Integer>(Arrays.asList(move.get(2), move.get(3)));
//			ArrayList<Integer> newArrow = new ArrayList<Integer>(Arrays.asList(move.get(4), move.get(5)));
//			super.movePiece(oldQueen, newQueen, newArrow, colour);
    		return move;
    	} else if (counter >= 5 && counter < 15) {
    		int depth = 1;
    		double scale = 0.95;
    		makeNodes(colour, depth, 'A', scale);
    		NodeGraphOld moveNode = miniMax(graph, new NodeGraphOld(-Double.MAX_VALUE, "Alpha"), new NodeGraphOld(Double.MAX_VALUE, "Beta"), true);
    		System.out.println("DONE MIN-MAX ALPHA-BETA:");
    		System.out.println("MAX node to add is:" 
									+ "\n-> Old Queen: " + Arrays.deepToString(moveNode.oldQueen.toArray())
									+ "\n-> New Queen: " + Arrays.deepToString(moveNode.newQueen.toArray())
									+ "\n-> New Arrow: " + Arrays.deepToString(moveNode.newArrow.toArray())
									+ "\n-> Value: " + moveNode.value
									+ "\n Name: " + moveNode.name
									+ "\n-> Board: " + moveNode.toString()
    								);
    		ArrayList<Integer> move = new ArrayList<Integer>(Arrays.asList(moveNode.oldQueen.get(0), moveNode.oldQueen.get(1),
																	moveNode.newQueen.get(0), moveNode.newQueen.get(1),
																	moveNode.newArrow.get(0), moveNode.newArrow.get(1)
																	));
    		return move;    		
    	} else if (counter >= 15 && counter < 20) {
    		int depth = 2;
    		double scale = 0.97;
    		makeNodes(colour, depth, 'A', scale);
    		NodeGraphOld moveNode = miniMax(graph, new NodeGraphOld(-Double.MAX_VALUE, "Alpha"), new NodeGraphOld(Double.MAX_VALUE, "Beta"), true);
    		ArrayList<Integer> move = new ArrayList<Integer>(Arrays.asList(moveNode.oldQueen.get(0), moveNode.oldQueen.get(1),
																	moveNode.newQueen.get(0), moveNode.newQueen.get(1),
																	moveNode.newArrow.get(0), moveNode.newArrow.get(1)
																	));
    		return move; 
    	} else if (counter >= 20 && counter < 28) {
    		int depth = 3;
    		double scale = 0.95;
    		makeNodes(colour, depth, 'A', scale);
    		NodeGraphOld moveNode = miniMax(graph, new NodeGraphOld(-Double.MAX_VALUE, "Alpha"), new NodeGraphOld(Double.MAX_VALUE, "Beta"), true);
    		ArrayList<Integer> move = new ArrayList<Integer>(Arrays.asList(moveNode.oldQueen.get(0), moveNode.oldQueen.get(1),
																	moveNode.newQueen.get(0), moveNode.newQueen.get(1),
																	moveNode.newArrow.get(0), moveNode.newArrow.get(1)
																	));
    		return move; 
    	}else if (counter >= 28) { // && super.counter < 45
    		int depth = 3;
    		double scale = 0.;
    		makeNodes(colour, depth, 'A', scale);
    		NodeGraphOld moveNode = miniMax(graph, new NodeGraphOld(-Double.MAX_VALUE, "Alpha"), new NodeGraphOld(Double.MAX_VALUE, "Beta"), true);
    		ArrayList<Integer> move = new ArrayList<Integer>(Arrays.asList(moveNode.oldQueen.get(0), moveNode.oldQueen.get(1),
																	moveNode.newQueen.get(0), moveNode.newQueen.get(1),
																	moveNode.newArrow.get(0), moveNode.newArrow.get(1)
																	));
    		return move; 
    	}
		return null;
    }

    /** miniMax algorithm to prioritize maximizing the player's moves by minimizing the opponent's available moves
 	* @param node
 	* @param alpha
 	* @param beta
 	* @param maximizingPlayer
	*/    
    public NodeGraphOld miniMax(NodeGraphOld node, NodeGraphOld alpha, NodeGraphOld beta, boolean maximizingPlayer) {
    	if(node.children == null || node.children.size() == 0) {
    		//System.out.println("ENDED");
    		return node;
    	}
    	
//    	System.out.println("\n\n MINMAX \nCounter: " + counter++);
//	    System.out.println("MAX node to add is:" 
//					+ "\n-> Old Queen: " + Arrays.deepToString(node.oldQueen.toArray())
//					+ "\n-> New Queen: " + Arrays.deepToString(node.newQueen.toArray())
//					+ "\n-> New Arrow: " + Arrays.deepToString(node.newArrow.toArray())
//					+ "\n-> Value: " + node.value
//					+ "\nChildren size = " + node.children.size()
//					+ "\n-> Board: " + node.toString()
//				);
    	
    	if(maximizingPlayer) {
    		NodeGraphOld best = new NodeGraphOld(-Double.MAX_VALUE, "BestMax " + minmaxcounter++);
    		for(int i = 0; i < node.children.size(); i++) {
//	    		System.out.println("MAX iteration: " + i);
	    		NodeGraphOld val = miniMax(node.children.get(i), alpha, beta, false);
	    		val.oldQueen = node.children.get(i).oldQueen;
	    		val.newQueen = node.children.get(i).newQueen;
	    		val.newArrow= node.children.get(i).newArrow;
	    		val.board = node.children.get(i).board;
	    		//System.out.println("MAXchild Node val = " +val.value);
//	    		System.out.println("COMPARING:" 
//						+ "\n-> Old Queen: " + Arrays.deepToString(val.oldQueen.toArray())
//						+ "\n-> New Queen: " + Arrays.deepToString(val.newQueen.toArray())
//						+ "\n-> New Arrow: " + Arrays.deepToString(val.newArrow.toArray())
//						+ "\n-> Value: " + val.value
//						+ "\n Name: " + val.name
//						+ "\n-> Board: " + val.toString()
//						);
	    		if(val.value > best.value) { best = val;}
	    		if(best.value > alpha.value) { alpha = best;}
	    		if(beta.value <= alpha.value) { break;}
	    		
    		}
    		//System.out.println("ALPHA BEST: " + alpha.value);
//    		System.out.println("\n Name: " + best.name);
    		return best;
    	} else {
    		NodeGraphOld best = new NodeGraphOld(Double.MAX_VALUE, "BestAlpha " + minmaxcounter++);
    		for(int i = 0; i < node.children.size(); i++) {
//	    		System.out.println("MIN iteration: " + i);
	    		NodeGraphOld val = miniMax(node.children.get(i), alpha, beta, true);
	    		//System.out.println("Node val = " +val.value);
	    		val.oldQueen = node.children.get(i).oldQueen;
	    		val.newQueen = node.children.get(i).newQueen;
	    		val.newArrow= node.children.get(i).newArrow;
	    		val.board = node.children.get(i).board;
	    		if(val.value < best.value) { best = val;}
	    		if(best.value < beta.value) { beta = best;}
	    		if(beta.value <= alpha.value) { break;}

    		}
//    		System.out.println("BETA BEST: " + alpha.value);
//    		System.out.println("\n Name: " + best.name);
    		return best;
    	}
    }
    
    /** Method to make nodes by expanding a graph
 	* @param colour
 	* @param depth
 	* @param strategy
	*/    
    public void makeNodes(int colour, int depth, char strategy, double scale) {
    	for(int i = 0; i < depth; i++) {
//    		System.out.println("EXPAND NODE " + i);
    		if(i > 0 && i % 2 == 1) {
//    			System.out.println("EXPAND OPPONENTS NODE");
    			expandGraph(getOppColour(colour), graph, strategy, i, scale);
    		}
    		if(i % 2 == 0) {
    			expandGraph(colour, graph, strategy, i, scale);
    		}
    	}
    }
    
    /** Method to expand a graph by adding children based on attack mode
 	* @param colour
 	* @param node
 	* @param depth
 	* @param strategy
	*/    
    public void expandGraph(int colour, NodeGraphOld node, char strategy, int depth, double scale) {
    	//System.out.println("Expanding player " + colour);
    	if(node.children == null || node.children.size() == 0) {
//        	//System.out.println("Children = null");
    		if(strategy == 'A') {
//            	System.out.println("Strategy Attack");
    			node.addChildren(nodeAttack(colour, node, depth, scale));
    		}
    		return; 
    	}
    	for(int i = 0; i < node.children.size(); i++) {
//        	System.out.print("Iterating through child " + i);
    		expandGraph(colour, node.children.get(i), strategy, depth + 1, scale);
    	}
    }
    
    /** Method to move a queen based on minimizing opponents possible moves - ONE LEVEL
 	* @param colour
 	* @param node
 	* @param depth
 	* @param strategy
	*/    
    public ArrayList<NodeGraphOld> nodeAttack(int colour, NodeGraphOld node, int depth, double scale) {
    	ArrayList<NodeGraphOld> branching = new ArrayList<NodeGraphOld>();
    	ArrayList<ArrayList<Integer>> queenCurr = node.getPositions(colour);
    	ArrayList<Integer> Qloc = null;
    	ArrayList<Integer> Aloc = null;
    	int whichQueen = 0;
    	int overallmin = Integer.MAX_VALUE;
    	
    	
    	//System.out.println(Arrays.deepToString(queenCurr.toArray()));
    	for (int f = 0; f < 4; f++ ) {	
    		ArrayList<Integer> queenmoves = node.move.getMoves(node, queenCurr.get(f));
    		//DEBUGG
    		//System.out.println("\n" + f + "-> QUEEN:  ");
        	//System.out.println(Arrays.deepToString(queenCurr.get(f).toArray()) + ":  ");
        	//System.out.println(Arrays.deepToString(queenmoves.toArray()));

	    	for (int i = 0; i < 8; i++) { //for each direction < 8
	    		for (int j = 1; j <= queenmoves.get(i); j++) { //< queenmoves.get(i)
	    			//DEBUGG
//	    			//System.out.println("Of " + queenmoves.get(i) + " Moving " + i + ", " + j + "times");
//	            	//System.out.println(Arrays.deepToString(queenmoves.toArray()));


	    			Board brd = new Board();
	    			brd.clone(node);
	    			//DEBUGG
//	    			//System.out.println("Cloning the board: \n" + brd.toString());
	    			//track what the move is
	    			ArrayList<ArrayList<Integer>> ourBefore = brd.getPositions(colour);
	    			int sumB = 0;
					for (int m = 0; m < 4; m++) {
						ArrayList<Integer> ourMoves = brd.move.getMoves(node, ourBefore.get(m));
//    			    	//System.out.println(Arrays.deepToString(opp.get(m).toArray()));
//				    	//System.out.println(Arrays.deepToString(oppmoves.toArray()));

						for (int p = 0; p < 8; p++) {
							sumB += ourMoves.get(p);
						}
					}
	    			
					ArrayList<ArrayList<Integer>> oppBefore = getOppPos(colour);
	    			int sumBefore = 0;
					for (int m = 0; m < 4; m++) {
						ArrayList<Integer> oppmoves = brd.move.getMoves(node, oppBefore.get(m));
//    			    	//System.out.println(Arrays.deepToString(opp.get(m).toArray()));
//				    	//System.out.println(Arrays.deepToString(oppmoves.toArray()));

						for (int p = 0; p < 8; p++) {
							sumBefore += oppmoves.get(p);
						}
					}
	    			
	    			
	    			ArrayList<Integer> newQueen = whereTo(i, queenCurr.get(f), j);
//	    	    	//System.out.println("New Queen position: " + Arrays.deepToString(newQueen.toArray()));

	    			
	    			brd.setTile(queenCurr.get(f).get(0), queenCurr.get(f).get(1), 0);
	    			brd.setTile(newQueen.get(0), newQueen.get(1), colour);
//	    			//System.out.println("Move on cloned board from " + queenCurr.get(f).get(0) + ", " + queenCurr.get(f).get(1)
//	    								+ " to " + newQueen.get(0) + "," + newQueen.get(1) + "\n" + brd.toString());

	    			ArrayList<Integer> arrowmoves = brd.move.getMoves(brd, newQueen);
	    			for (int k = 0; k < 8; k++) { 
//	    				//System.out.println("\n  ARROW MOVES:\n");
	    				for (int l = 1; l <= arrowmoves.get(k); l++) { //for each possible move
	    					Board brd2 = new Board();
	    					brd2.clone(node);
	    					ArrayList<Integer> newArrow =  whereTo(k, newQueen, l);
	    					brd2.movePiece(queenCurr.get(f), newQueen, newArrow, colour);
//	    	    			System.out.println("Arrow number " + k + ": \n" + brd2.toString());
	    					
	    					ArrayList<ArrayList<Integer>> opp = getOppPos(colour);
	    					ArrayList<ArrayList<Integer>> our = brd2.getPositions(colour);
	    					
	    					int sumAfter = 0;
	    					int sumOurs = 0;
	    					for (int m = 0; m < 4; m++) {
	    						ArrayList<Integer> oppmoves = brd2.move.getMoves(brd2, opp.get(m));
//	    		            	System.out.println(Arrays.deepToString(oppmoves.toArray()));
//		    			    	System.out.println(Arrays.deepToString(opp.get(m).toArray()));

	    						for (int p = 0; p < 8; p++) {
	    							sumAfter += oppmoves.get(p);
	    						}
	    					}
	    					for (int m = 0; m < 4; m++) {
	    						ArrayList<Integer> ourmoves = brd2.move.getMoves(brd2, our.get(m));
//	    				    	System.out.println(Arrays.deepToString(ourmoves.toArray()));
//	    				    	System.out.println(Arrays.deepToString(our.get(m).toArray()));

	    						for (int p = 0; p < 8; p++) {
	    							sumOurs += ourmoves.get(p);
	    						}
	    					}
	    					
	    					//DEBUGG
//	    					double wVal = ((sumBefore - sumAfter) * scale) + ((sumOurs * (1 - scale)));
//	    					if(newQueen.isEmpty()) {
//	    						System.out.println("ADDING EMPTY QUEEN");
//		    					System.out.println("Total moves before = " + sumBefore);
//		    					System.out.println("Total moves  after= " + sumAfter);
//		    					System.out.println("Restricted move moves = " + (sumBefore - sumAfter));
//		    					System.out.println("New node to add is:" 
//		    											+ "\n-> Old Queen: " + Arrays.deepToString(queenCurr.get(f).toArray())
//		    											+ "\n-> New Queen: " + Arrays.deepToString(newQueen.toArray())
//		    											+ "\n-> New Arrow: " + Arrays.deepToString(newArrow.toArray())
//		    											+ "\n-> Value1: " + (sumBefore - sumAfter)
//		    											+ "\n-> Value2: " + sumOurs
//		    											+ "\n-> Board: " + brd2.toString()
//		    										);
//	    					}
	    					
	    					NodeGraphOld temp = new NodeGraphOld(queenCurr.get(f), newQueen, newArrow, (sumBefore - sumAfter), (sumOurs - sumB), brd2, depth, scale);
	    					branching.add(temp);
//	    					//System.out.print("All values: \n[" );
//	    			    	for(int z = 0; z < branching.size(); z++) {
//	    			    		//System.out.print(branching.get(z).getValue() + ", ");
//	    			    		////System.out.print(branching.get(z).toString());
//	    			    	}
//	    			    	//System.out.println("] \n Size = " + branching.size() + "\n\n");
	    				}
	    			}
	    		}
	    	}
    	}
//    	//System.out.println("] \n Size = " + branching.size() + "\n\n");
    	return branching;
   }
    
    
   /** Method to provide coordinates of a new position based on provided original position, 
    * and how far to move in a given direction.
 	* @param direction
 	* @param pos
 	* @param howFar
	*/    
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
    
    /** Method to return the opponent's colour
  	* @param colour
 	*/    
    public int getOppColour(int colour) {
    	if (colour==1) return 2;
    	else if (colour==2) return 1;
    	return 0;
    }
    /** Method to return the opponent's piece positions
  	* @param colour
 	*/    
    public ArrayList<ArrayList<Integer>> getOppPos(int colour) {
    	int colour2 = 0;
    	if (colour==1) colour2 = 2;
    	else if (colour==2) colour2 = 1;
    	ArrayList<ArrayList<Integer>> opp = super.getPositions(colour2);
    	return opp;
    }
    
    public void printNodeGraphOld(NodeGraphOld g, int d) {
    	for(int i = 0; i < g.children.size(); i++) {
    		System.out.println( 
    				"DEPTH = " + d
					+ "\n-> Old Queen: " + Arrays.deepToString(g.children.get(i).oldQueen.toArray())
					+ "\n-> New Queen: " + Arrays.deepToString(g.children.get(i).newQueen.toArray())
					+ "\n-> New Arrow: " + Arrays.deepToString(g.children.get(i).newArrow.toArray())
					+ "\n-> Value: " + g.children.get(i).value
					+ "\n Name: " + g.children.get(i).name
					+ "\n-> Board: " + g.children.get(i).toString());
    	}
    }


}