import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

	private static final long serialVersionUID = -5174544703753724015L;

	private static int playerIndex = 1;
	
	protected int symbol;
	protected String name;
	protected ArrayList<Integer> stateHistory;
	
	public Player(String name, int symbol) {
		this.name = name;
		this.symbol = symbol;
		stateHistory = new ArrayList<>();
		playerIndex++;
	}
	
	public Player(int symbol) {
		this("Player " + playerIndex, symbol);
	}
	
	public void playTurn(Environment e) {
		System.out.println(name + "'s turn\nPlace an " + Environment.getSymbol(symbol) + " somewhere");
		System.out.print("Enter an x index between 1 and " + Environment.LENGTH + ": ");
		int i = Game.scanner.nextInt() - 1;
		System.out.print("Enter an y index between 1 and " + Environment.LENGTH + ": ");
		int j = Game.scanner.nextInt() - 1;
		while(!e.isValid(i, j))
		{
			System.out.println("Invalid input!");
			System.out.print("Enter an x index between 1 and " + Environment.LENGTH + ": ");
			i = Game.scanner.nextInt() - 1;
			System.out.print("Enter an y index between 1 and " + Environment.LENGTH + ": ");
			j = Game.scanner.nextInt() - 1;
		}
		e.place(symbol, i, j);
	}
	
	public void updateStateHistory(int state) {
		stateHistory.add(state);
	}
	
	public void resetStateHistory() {
		stateHistory.clear();
	}
	
	public int getSymbol() {
		return symbol;
	}

	public void setSymbol(int symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setName() {
		System.out.print("Enter the name of the player with (" + Environment.getSymbol(symbol) + "): ");
		name = Game.scanner.nextLine();
	}
	
}
