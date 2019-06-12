import java.util.Scanner;

public class Game {

	public static final int NUM_OF_PLAYERS = 2;
	public static final Scanner scanner = new Scanner(System.in);
	
	private Player[] players;
	private Environment environment;
	
	private boolean verbose;
	private boolean draw;
	
	public Game(boolean verbose, boolean draw, Environment e, Player... players) {
		this.verbose = verbose;
		this.players = players;
		this.draw = draw;
		environment = e;		
	}
	
	public Game(boolean draw, Player... players) {
		this(true, draw, new Environment(), players);		
	}
	
	public Game (Player... players) {
		this(true, false, new Environment(), players);
	}
	
	public Game(boolean draw) {
		Player[] players = new Player[NUM_OF_PLAYERS];
		for(int i = 0; i < NUM_OF_PLAYERS; i++)
			players[i] = new Player(Environment.symbols[i + 1]);
		this.players = players;
		this.draw = draw;
		this.verbose = true;
		environment = new Environment();
	}
	
	public Game() {
		this(true);
	}
	
	public void setup() {
		for(int i = 0; i < NUM_OF_PLAYERS; i++)
			players[i].setName();
	}
	
	public void play() {
		int playerIndex = -1;
		if(draw)
			environment.drawBoard();
		while(!environment.gameOver(true)) {
			playerIndex++;
			playerIndex = playerIndex % NUM_OF_PLAYERS;
			Player currentPlayer = players[playerIndex];
			currentPlayer.playTurn(environment);
			if(draw)
				environment.drawBoard();
			int state = environment.getState();
			for(int i = 0; i < NUM_OF_PLAYERS; i++)
				players[i].updateStateHistory(state);
		}
		if(verbose)
			System.out.println("The Player With " + Environment.getSymbol(environment.getWinner()) + " is the winner");
	}

	public boolean isDrawing() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public Player[] getPlayers() {
		return players;
	}

	public Environment getEnvironment() {
		return environment;
	}

}
