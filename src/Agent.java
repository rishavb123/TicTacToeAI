import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Agent extends Player implements Serializable {

	private static final long serialVersionUID = -2413391241316590719L;

	public static int numOfAgents;
	
	protected double epsilon;
	protected double alpha;
	protected boolean logging;
	
	protected HashMap<Integer, Double> V;
	
	public Agent(int symbol, double epsilon, double alpha) {
		super("Agent " + (numOfAgents + 1), symbol);
		numOfAgents++; 
		this.epsilon = epsilon;
		this.alpha = alpha;
		logging = false;
	}
	
	public Agent(int symbol) {
		this(symbol, 0.1, 0.5);
	}
	
	@Override
	public void playTurn(Environment e) {
		int[] nextMove = null;
		if(Math.random() < epsilon) {
			ArrayList<int[]> positions = e.validPositions();
			int index = (int) (Math.random() * positions.size());
			nextMove = positions.get(index);
		} else {
			double bestValue = -10;
			for(int i = 0; i < Environment.LENGTH; i++)
				for(int j = 0; j < Environment.LENGTH; j++) {
					if(e.isEmpty(i, j)) {
						e.place(symbol, i, j);
						int state = e.getState();
						e.place(Environment.EMPTY, i, j);
						if(logging) {
							System.out.println("(" + i + ", " + j + ") -> " + V.get(state));
							System.out.println("\t" + V.get(state) + " > " + bestValue + ": " + (V.get(state) > bestValue));
						}
						if(V.get(state) >= bestValue) {
							bestValue = V.get(state);
							nextMove = new int[] { i, j };
							if(logging)
								System.out.println("\tSelecting (" + nextMove[0] + ", " + nextMove[1] + ")");
						}
					}
				}
		}
		if(logging)
			System.out.println("Going at (" + nextMove[0] + ", " + nextMove[1] + ")");
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
	
	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}



	private static class StateWinnerEndedTriple {
		
		public int state;
		public int winner;
		public boolean ended;
		
		public StateWinnerEndedTriple(int state, int winner, boolean ended) {
			this.state = state;
			this.winner = winner;
			this.ended = ended;
		}
	}
	
	private static ArrayList<StateWinnerEndedTriple> getStateHashAndWinner(Environment e, int i, int j) {
		ArrayList<StateWinnerEndedTriple> results = new ArrayList<>();
		for(int symbol: Environment.symbols) {
			e.place(symbol, i, j);
			if(j == Environment.LENGTH - 1)
				if(i == Environment.LENGTH - 1) 
					results.add(new StateWinnerEndedTriple(e.getState(), e.getWinner(), e.gameOver(true)));
				else
					results.addAll(getStateHashAndWinner(e, i + 1, 0));
			else
				results.addAll(getStateHashAndWinner(e, i, j + 1));
		}
		return results;
	}
	
	private static ArrayList<StateWinnerEndedTriple> getStateHashAndWinner(Environment e) {
		return getStateHashAndWinner(e, 0, 0);
	}
	
	private static HashMap<Integer, Double> initialV(int symbol, ArrayList<StateWinnerEndedTriple> stateWinnerTriples) {
		HashMap<Integer, Double> V = new HashMap<>();
		for(StateWinnerEndedTriple state: stateWinnerTriples)
			if(state.ended)
				if(state.winner == symbol)
					V.put(state.state, 1.0);
				else
					V.put(state.state, 0.0);
			else
				V.put(state.state, 0.5);
		return V;
	}
	
	public static int train(Environment e, Agent p1, Agent p2, int iterations) {
		ArrayList<StateWinnerEndedTriple> stateWinnerTriples = getStateHashAndWinner(e);
		p1.setV(initialV(Environment.X, stateWinnerTriples));
		p2.setV(initialV(Environment.O, stateWinnerTriples));
		e.clear();
		int score = 0;
		for(int t = 0; t < iterations; t++)
		{
			Game game = new Game(false, false, e, p1, p2);
			game.play();
			p1.update(e);
			p2.update(e);
			p1.resetStateHistory();
			p2.resetStateHistory();
			score += e.getWinner();
			e.clear();
		}
		return score;
	}
	
}
