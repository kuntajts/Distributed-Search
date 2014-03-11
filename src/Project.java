import java.util.Random;


public class Project {
	
	//fish is the equivalent to alpha
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double fishsize = 1;
		int graphsize = 5, firstx, firsty, secx, secy, adverage = 0;
		Random r = new Random();
		Graph g = new Graph(graphsize, fishsize);
		
		for (int i = 0; i < 1000; i ++) {
			firstx = r.nextInt(graphsize);
			firsty = r.nextInt(graphsize);
			secx = r.nextInt(graphsize);
			secy = r.nextInt(graphsize);
			
			adverage += g.travel(firstx, firsty, secx, secy);
		}
		
		adverage = adverage/1000;
		System.out.println("The adverage amount of steps to get from one node");
		System.out.println("to the next on a " + graphsize + " by " + graphsize + " lattice with the fish of " + fishsize + " is...");
		System.out.println(adverage);
				
		
	}

}
