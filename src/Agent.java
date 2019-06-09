import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Agent extends Player {

	public static int numOfAgents;
	
	protected double epsilon;
	protected double alpha;
	protected boolean logging;
	
	protected HashMap<Integer, Double> V;
	
	public Agent(int symbol, double epsilon, double alpha) {
		super("Agent " + (numOfAgents + 1), symbol);
		numOfAgents++; 
		this.epsilon = 1;
		this.alpha = alpha;
		logging = false;
	}
	
	public Agent(int symbol) {
		this(symbol, 0.1, 0.5);
	}
	
	@Override
	public void playTurn(Environment e) {
		System.out.println(name + "'s turn");
		int[] nextMove = null;
		if(Math.random() < epsilon) {
			ArrayList<int[]> positions = e.validPositions();
			int index = (int) (Math.random() * positions.size());
			nextMove = positions.get(index);
		} else {
			int bestValue = 0;
			for(int i = 0; i < Environment.LENGTH; i++)
				for(int j = 0; j < Environment.LENGTH; j++)
					if(e.isValid(i, j)) {
						e.place(symbol, i, j);
						int state = e.getState();
						e.place(Environment.EMPTY, i, j);
						if(V.get(state) > bestValue) {
							bestValue = state;
							nextMove = new int[] { i, j };
						}
					}
		}
		System.out.println("Going at " + nextMove[0] + " " + nextMove[1]);
		e.place(symbol, nextMove[0], nextMove[1]);
	}

	public void update(Environment e) {
		int reward = e.reward(symbol);
		double target = reward;
		Collections.reverse(stateHistory);
		for(Integer prev : stateHistory) {
			double value = V.get(prev) + alpha * (target - V.get(prev));
			V.put(prev, value);
			target = value;
		}
		Collections.reverse(stateHistory);
	}
	
	public double V(int state) {
		return V.get(state);
	}
	
	public void setV(HashMap<Integer, Double> V) {
		this.V = V;
	}
	
	public void setLogging(boolean logging) {
		this.logging = logging;
	}
	
}
