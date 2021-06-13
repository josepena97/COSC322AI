package ubc.cosc322;

import java.util.ArrayList;

/** Code for Amazons node graph class - builds and extends a node graph of states
 * 
 * @author Team 05: Jose Pena, Corey Bond, Kshitij Suri, Jun Kang, Alex Rogov
 * 6/7/2021
 */

public class NodeGraphOld extends Board{
	
	// Variables
    public ArrayList<Integer> oldQueen = new ArrayList<Integer>();
    public ArrayList<Integer> newQueen = new ArrayList<Integer>();
    public ArrayList<Integer> newArrow= new ArrayList<Integer>();
    public ArrayList<NodeGraphOld> children = null;
    public double value;
    public double scale;
    public int value1;
    public int value2;
    public int depth;
    public String name;
    
    // Generic Constructor
    public NodeGraphOld() {
        super();
    	this.value = Integer.MIN_VALUE;
    }
    
    /** Constructor with board definition
 	* @param b : board
 	*/
    public NodeGraphOld(Board b) {
        super(b);
    }

    /** Constructor with value definition
 	* @param val
 	*/
    public NodeGraphOld(int val1, int val2, double sc) {
    	super();
    	this.scale = sc;
    	this.value1 = val1;
    	this.value2 = val2;
    	this.value = (val1 * sc) + (val2 * (1-sc));
    }
    
    public NodeGraphOld(double val) {
    	super();
    	this.value = val;
    }
    public NodeGraphOld(double val, String n) {
    	super();
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
    public NodeGraphOld(ArrayList<Integer> oldq, ArrayList<Integer> q, 
    					ArrayList<Integer> a, int val1, int val2, Board b, int d, double sc) {
        super();
        this.scale = sc;
        this.oldQueen = oldq;
    	this.newQueen = q;
    	this.newArrow = a;
    	this.value1 = val1;
    	this.value2 = val2;
    	this.value = (val1 * sc) + (val2 * (1-sc));
    	super.board = b.board;
    	this.depth = d; 
    }

    /** Method to add a child to the graph
 	* @param newChild
 	*/
    public void addChild(NodeGraphOld newChild) {
    	if(children == null || children.size() == 0) {
    		children = new ArrayList<NodeGraphOld>();
    		children.add(newChild);
    	}else {
    		children.add(newChild);
    	}
    }

    /** Method to add multiple children to the graph
 	* @param newChildren
 	*/
    public void addChildren(ArrayList<NodeGraphOld> newChildren) {
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