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
    public int value;
    public int depth;
    
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
    public NodeGraph(int val) {
    	super();
    	this.value = val;
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
    					ArrayList<Integer> a, int val, Board b, int d) {
        super();
        this.oldQueen = oldq;
    	this.newQueen = q;
    	this.newArrow = a;
    	this.value = val;
    	super.board = b.board;
    	this.depth = d; 
    }

    /** Method to add a child to the graph
 	* @param newChild
 	*/
    public void addChild(NodeGraph newChild) {
    	if(children == null) {
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
    	if(children == null) {
    		children = newChildren;
    	} else {
    		for(int i = 0; i < newChildren.size(); i++) {
    			children.add(newChildren.get(i));
    		}
    	}
    }
    
    /** Method to return the value of a node
 	*/
    public int getValue() {
    	return value;
    }
}