package ubc.cosc322;

import java.util.ArrayList;


public class NodeGraph extends Board{
    public ArrayList<Integer> oldQueen = new ArrayList<Integer>();
    public ArrayList<Integer> newQueen = new ArrayList<Integer>();
    public ArrayList<Integer> newArrow= new ArrayList<Integer>();
    public ArrayList<NodeGraph> children = null;
    public int value;
    public int depth;
    
    
    public NodeGraph() {
        super();
    	this.value = Integer.MIN_VALUE;
    }
    
    public NodeGraph(Board b) {
        super();
    	this.board = b.board;
    }
    public NodeGraph(int val) {
    	super();
    	this.value = val;
    }
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
    
    public void addChild(NodeGraph newChild) {
    	if(children == null) {
    		children = new ArrayList<NodeGraph>();
    		children.add(newChild);
    	}else {
    		children.add(newChild);
    	}
    }
    public void addChildren(ArrayList<NodeGraph> newChildren) {
    	if(children == null) {
    		children = newChildren;
    	} else {
    		for(int i = 0; i < newChildren.size(); i++) {
    			children.add(newChildren.get(i));
    		}
    	}
    }
    
    public int getValue() {
    	return value;
    }
}