import java.util.ArrayList;

public class Agent extends Player {

	public static int numOfAgents;
	
	protected ArrayList<Integer> stateHistory;
	
	public Agent(int symbol) {
		super("Agent " + numOfAgents, symbol);
		numOfAgents++; 
		
		stateHistory = new ArrayList<>();
	}
	
	@Override
	public void playTurn(Environment e) {
		System.out.println(name + "'s turn");
		ArrayList<int[]> positions = e.validPositions();
		int index = (int) (Math.random() * positions.size());
		int[] position = positions.get(index);
		System.out.println("Going at " + position[0] + " " + position[1]);
		e.place(symbol, position[0], position[1]);
		updateStateHistory(e.getState());
	}
	
	public void updateStateHistory(int state) {
		stateHistory.add(state);
	}
	
	public void resetStateHistory() {
		stateHistory.clear();
	}
	
}
