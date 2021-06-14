package ubc.cosc322;

import java.util.ArrayList;

/** Code for Amazons node graph class - builds and extends a node graph of states
 * 
 * @author Team 05: Jose Pena, Corey Bond, Kshitij Suri, Jun Kang, Alex Rogov
 * 6/7/2021
 */

public class NodeGraph extends Board{
	
	// Variables
    public ArrayList<Integer> oldQueen = new ArrayList<Integer>();
    public ArrayList<Integer> newQueen = new ArrayList<Integer>();
    public ArrayList<Integer> newArrow= new ArrayList<Integer>();
    public ArrayList<NodeGraph> children = null;
    public double value;
    public double scale;
    public int enemyMoves;
    public int myMoves;
    public double enemyMovesp;
    public double myMovesp;
    public int enemyArea;
    public int myArea;
    public int depth;
    public String name;
    
    // Generic Constructor
    public NodeGraph() {
        super();
    	this.value = Integer.MIN_VALUE;
    }
    
    /** Constructor with board definition
 	* @param b : board
 	*/
    public NodeGraph(Board b) {
        super(b);
    }

    /** Constructor with value definition
 	* @param val
 	*/
    public NodeGraph(int val1, int val2, double val1p, double val2p, double sc) {
    	super();
    	this.scale = sc;
    	this.enemyMoves = val1;
    	this.myMoves = val2;
    	this.enemyMovesp = val1p;
    	this.myMovesp = val2p;
    	this.value = (val1p * sc) + (val2p * (1-sc));
    }
    
    public NodeGraph(double val) {
    	super();
    	this.value = val;
    }
    public NodeGraph(int val1, int val2, double val, String n) {
    	super();
    	this.enemyMoves = val1;
    	this.myMoves = val2;
    	this.value = val;
    	this.name = n;
    }
    /** Constructor with full details
 	* @param oldq : Old Queen position
 	* @param q : New Queen position
 	* @param a : New arrow position
 	* @param val
 	* @param b : board
 	* @param d : depth
 	*/
    public NodeGraph(ArrayList<Integer> oldq, ArrayList<Integer> q, 
    					ArrayList<Integer> a, int val1, int val2, double val1p, double val2p, 
    					Board b, int d, double sc, int area1, int area2) {
        super();
        this.scale = sc;
        this.oldQueen = oldq;
    	this.newQueen = q;
    	this.newArrow = a;
    	this.enemyMoves = val1;
    	this.myMoves = val2;
    	this.enemyMovesp = val1p;
    	this.myMovesp = val2p;
    	this.value = (val1p * sc) + (val2p * (1-sc));
    	super.board = b.board;
    	this.depth = d; 
    	this.enemyArea = area1;
    	this.myArea = area2;
    	
    }

    /** Method to add a child to the graph
 	* @param newChild
 	*/
    public void addChild(NodeGraph newChild) {
    	if(children == null || children.size() == 0) {
    		children = new ArrayList<NodeGraph>();
    		children.add(newChild);
    	}else {
    		children.add(newChild);
    	}
    }

    /** Method to add multiple children to the graph
 	* @param newChildren
 	*/
    public void addChildren(ArrayList<NodeGraph> newChildren) {
    	if(children == null || children.size() == 0) {
    		children = newChildren;
    	} else {
    		for(int i = 0; i < newChildren.size(); i++) {
    			children.add(newChildren.get(i));
    		}
    	}
    }
    
    /** Method to return the value of a node
 	*/
    public double getValue() {
    	return value;
    }
}