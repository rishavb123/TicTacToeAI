import java.util.ArrayList;

public class Agent extends Player {

	public static int numOfAgents;
	
	
	public Agent(int symbol) {
		super("Agent " + (numOfAgents + 1), symbol);
		numOfAgents++; 
	}
	
	@Override
	public void playTurn(Environment e) {
		System.out.println(name + "'s turn");
		ArrayList<int[]> positions = e.validPositions();
		int index = (int) (Math.random() * positions.size());
		int[] position = positions.get(index);
		System.out.println("Going at " + position[0] + " " + position[1]);
		e.place(symbol, position[0], position[1]);
	}
	
}
