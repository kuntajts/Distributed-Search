
public class Node {
	Node left;
	Node right;
	Node top;
	Node bottom;
	Node distantNeighbor;
	int lad;
	int longitude;
	
	/*
	 * constructor for intializing where the node is on the graph and sets all neighbors
	 * to null.
	 */
	public Node(int longIn, int ladIn){
		left = null;
		right = null;
		top = null;
		bottom = null;
		distantNeighbor = null;	
		
		longitude = longIn;
		lad = ladIn;
	}
	public int getLong() {
		return longitude;
	}
	
	
	public int getLad() {
		return lad;
	}
	
	public void setLong(int newLong) {
		longitude = newLong;
	}
	
	public void setLad(int newLad) {
		lad = newLad;
	}
	
	
}
