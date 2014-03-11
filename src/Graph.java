import java.util.Random;

public class Graph {
	
	Node[][] graph;
	int N;
	double fish;
	int furthestDistance = 0;

	/*
	 * Initializes the graph object taking the size of the graph and the alpha value 
	 * for choosing 5th connection. Also makes the graph as a 2d array of Nodes.
	 * 
	 * Also calculates the maximum amount of steps to go between the farthest two
	 * nodes on the graph.
	 */
	public Graph(final int lattice, double fishIn) {
		graph = new Node[lattice][lattice];
		N = lattice;
		fish = fishIn;
		
		if (N % 2 == 0) {
			// even
			furthestDistance = N;
		} else {
			//odd
			furthestDistance = N - 1;
		}
		
		init();
	}
	
	/*
	 * Init: 
	 * called by the constructor to initialize each node in the graph. first it's left,
	 * right, bottom, and tops then it's fifth connection.
	 * 
	 * 
	 */

	private void init() {
		// TODO Auto-generated method stub
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				graph[i][j] = new Node(i, j);
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				// if the node is on the edges 
				if (i == 0 || j == 0 || j == N - 1 || i == N - 1) {
					initEdge(i, j);
				} else {
					// populate nodes regularly
					graph[i][j].left = graph[i][j - 1];
					graph[i][j].right = graph[i][j + 1];
					graph[i][j].top = graph[i - 1][j];
					graph[i][j].bottom = graph[i + 1][j];
				}
				// fifth edge
				initFifth(i, j);
			}
		}

	}
	
	/*
	 * initFifth:
	 * initiates fifth node according to the alpha(fish) value of the graph. The
	 * fish value will determine if the fifth node is more likely to be closer
	 * or further from the node we are initializing.
	 * 
	 * 
	 */

	private void initFifth(int longitude, int lad) {
		// TODO Auto-generated method stub
		double percent = 0;
		double d = 0.0;
		int radius = 1;
		int k = 0; // farthest node's distance
		boolean flag = true, nodeNotFound = true;
		Random randomNum = new Random();
		
		for (int i = 2; i <= furthestDistance; i++) {
			k = k + 100000/i;
		}

		while(nodeNotFound) {
			// for precision we got the random int between 100000 and 0 and we mod
			// it to fit a percentage
			d = (double) randomNum.nextInt(k+1) / 1000;
			
			// checking all distances j from 2 to the farthest
			flag = true;
			percent = 0;
			for (int j = 2; j <= furthestDistance && flag; j++) {
				// equivalent of the 1/r^fish equation
				
				percent = percent + (100 / (Math.pow(j, fish)));
				if (d <= percent) {					
					radius = j;
					
					flag = false;
	
					// place 5th connection
					int x = 0;
					int y = 0;
					
					//get a random node that is radius away
					while (Math.abs(x) + Math.abs(y) != radius) {
						x = (radius*-1) + (int) (Math.random() * ((radius - (radius*-1)) + 1));
						y = (radius*-1) + (int) (Math.random() * ((radius - (radius*-1)) + 1));
					}
					
					//System.out.println(radius + " : " + x + ", " + y);						

					// SOLVING THE WRAP PROBLEM
					int totalx = longitude + x;
					int totaly = lad + y;
					int wrapx,wrapy;
					
					if(totalx >= 0){
						wrapx = (totalx) - ((totalx / N)*N);
					}else{
						wrapx = N + totalx;
					}
					
					if(totaly >= 0){
						wrapy = totaly - ((totaly / N)*N);
					}else{
						wrapy = N + totaly;
					}
					
					graph[longitude][lad].distantNeighbor = graph[wrapx][wrapy];
					nodeNotFound = false;
				}
			}
		}
	}
	
	/*
	 * travel:
	 * The testing method that takes two nodes and finds the shortest amount of steps between
	 * them. The algorithm favors the distance to the next node by direct neighbors first
	 * it then looks to the fifth connection and picks the shortest. RECURSIVE
	 * 
	 * returns the number of steps taken.
	 */
	public int travel(Node firstNode, Node secondNode) {
		int steps = 0;
		int newLong, newLad, long5, lad5;
		//Node nodeToUse = null;
		
		//base case
		if (firstNode == secondNode) {
			return 0;
		}
		
		//getting distances between nodes
		newLong = Math.abs(firstNode.getLong() - secondNode.getLong());
		newLad = Math.abs(firstNode.getLad() - secondNode.getLad());
		
		long5 = Math.abs(firstNode.distantNeighbor.getLong() - secondNode.getLong());
		lad5 = Math.abs(firstNode.distantNeighbor.getLad() - secondNode.getLad());
		
		if (newLong + newLad <= long5 + lad5) {
			if (newLong >= newLad) {
				if (firstNode.getLong() >= secondNode.getLong()) {
					steps = travel(firstNode.top, secondNode) + 1;
				} else {
					steps = travel(firstNode.bottom, secondNode) + 1;
				}
			} else {
				if (firstNode.getLad() >= secondNode.getLad()) {
					steps = travel(firstNode.left, secondNode) + 1;
				} else {
					steps = travel(firstNode.right, secondNode) + 1;
				}
			}
		} else {
			steps = travel(firstNode.distantNeighbor, secondNode) + 1;
		}
		
		return steps;
	}
	
	/*
	 * initEdge:
	 * initalizes the edge nodes to support wrapping around the graph.
	 */

	private void initEdge(int i, int j) {
		// TODO Auto-generated method stub
		if (i == 0) {
			graph[i][j].top = graph[N - 1][j];
			if (j == 0) {
				graph[i][j].left = graph[i][N - 1];
				graph[i][j].right = graph[i][j + 1];
				graph[i][j].bottom = graph[i + 1][j];
			} else if (j == N - 1) {
				graph[i][j].right = graph[i][0];
				graph[i][j].left = graph[i][j - 1];
				graph[i][j].bottom = graph[i + 1][j];
			} else {
				graph[i][j].left = graph[i][j - 1];
				graph[i][j].right = graph[i][j + 1];
				graph[i][j].bottom = graph[i + 1][j];
			}
		} else if (i == N - 1) {
			graph[i][j].bottom = graph[0][j];
			if (j == 0) {
				graph[i][j].left = graph[i][N - 1];
				graph[i][j].top = graph[i - 1][j];
				graph[i][j].right = graph[i][j + 1];
			} else if (j == N - 1) {
				graph[i][j].right = graph[i][0];
				graph[i][j].top = graph[i - 1][j];
				graph[i][j].left = graph[i][j - 1];
			} else {
				graph[i][j].left = graph[i][j - 1];
				graph[i][j].right = graph[i][j + 1];
				graph[i][j].top = graph[i - 1][j];
			}
		} else if (j == 0) {
			graph[i][j].left = graph[i][N - 1];
			graph[i][j].right = graph[i][j + 1];
			graph[i][j].top = graph[i - 1][j];
			graph[i][j].bottom = graph[i + 1][j];
		} else if (j == N - 1) {
			graph[i][j].right = graph[i][0];
			graph[i][j].bottom = graph[i + 1][j];
			graph[i][j].left = graph[i][j - 1];
			graph[i][j].top = graph[i - 1][j];
		}

	}
	
	/*
	 * display:
	 * displays testing information to the console.
	 */

	public void Display() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.println(graph[i][j].getLong() + ","
						+ graph[i][j].getLad() + ":");
				System.out.println(graph[i][j].distantNeighbor.getLong() + ", " + graph[i][j].distantNeighbor.getLad());
				System.out.println();
			}

		}
	}
	
	/*
	 * Travel:
	 * stub method for recursive travel method. Takes the laditude and longitude of both the
	 * first and second node and inputs it into the recursive travel method.
	 * 
	 * returns the amount of steps between the two nodes.
	 */

	public int travel(int flong, int flad, int slong, int slad) {
		// TODO Auto-generated method stub
		
		return travel(graph[flong][flad], graph[slong][slad]);
	}
}
