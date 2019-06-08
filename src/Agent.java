
public class Agent extends Player {

	public static int numOfAgents;
	
	public Agent(int symbol) {
		super("Agent " + numOfAgents, symbol);
		numOfAgents++; 
	}

}
