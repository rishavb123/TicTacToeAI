import java.util.ArrayList;

public class Environment {

	public static final int EMPTY = 0;
	public static final int X = -1;
	public static final int O = 1;
	public static final int[] symbols = new int[] { EMPTY, X, O };
	public static final int LENGTH = 3;
	public static final int numState = (int) Math.pow(3, LENGTH * LENGTH);;
	
	private int[][] board;
	private int winner;
	private boolean ended;
	
	public Environment() {
		board = new int[LENGTH][LENGTH];
	}
	
	public boolean isEmpty(int i, int j) {
		return board[i][j] == 0;
	}
	
	public boolean isValid(int i, int j) {
		return i >= 0 && j >= 0 && i < Environment.LENGTH && j < Environment.LENGTH && isEmpty(i, j);
	}
	
	public ArrayList<int[]> validPositions() {
		ArrayList<int[]> positions = new ArrayList<>();
		for(int i = 0; i < LENGTH; i++)
			for(int j = 0; j < LENGTH; j++)
				if(isValid(i, j))
					positions.add(new int[] { i, j });
		return positions;
	}
	
	public int reward(int symbol) {
		return winner == symbol? 1 : 0;
	}
	
	public int getState() {
		int k = 0;
		int h = 0;
		for(int i = 0; i < LENGTH; i++)
			for(int j = 0; j < LENGTH; j++) {
				int v = 0;
				if(board[i][j] == X)
					v = 1;
				else if(board[i][j] == O)
					v = 2;
				h += Math.pow(3, k) * v;
				k++;
			}
		return h;
	}
	
	public boolean gameOver(boolean forceRecalculate) {
		if(!forceRecalculate)
			return ended;
		
		int[] players = { X, O };
		
		for(int i = 0; i < LENGTH; i++) {
			int sum = 0;
			for(int j = 0; j < LENGTH; j++)
				sum += board[i][j];
			for(int player: players) {
				if(sum == player * LENGTH) {
					winner = player;
					ended = true;
					return true;
				}
			}
		}
		
		for(int i = 0; i < LENGTH; i++)
			for(int player: players) {
				int sum = 0;
				for(int j = 0; j < LENGTH; j++)
					sum += board[j][i];
				if(sum == player * LENGTH) {
					winner = player;
					ended = true;
					return true;
				}
			}
		
		int sum = 0;
		for(int i = 0; i < LENGTH; i++)
			sum += board[i][i];
		int sum2 = 0;
		for(int i = 0; i < LENGTH; i++)
			sum2 += board[LENGTH - 1 - i][i];
		for(int player: players) {
			if(sum == player * LENGTH) {
				winner = player;
				ended = true;
				return true;
			}
			if(sum2 == player * LENGTH) {
				winner = player;
				ended = true;
				return true;
			}
				
		}
		
		boolean filled = true;
		for(int i = 0; i < LENGTH; i++)
			for(int j = 0; j < LENGTH; j++)
				if(isEmpty(i, j))
					filled = false;
		if(filled) {
			winner = 0;
			ended = true;
			return true;
		}
		
		return false;
	}
	
	public boolean gameOver() {
		return gameOver(false);
	}
	
	public void place(int symbol, int i, int j) {
		board[i][j] = symbol;
	}
	
	public void drawBoard() {
		System.out.println("---------------------------------------------------------\n");
		
		System.out.print("-");
		for(int j = 0; j < LENGTH; j++)
			System.out.print("----");
		System.out.println();
		
		for(int i = 0; i < LENGTH; i++) {
			for(int j = 0; j < LENGTH; j++)
				System.out.print("| " + getSymbol(board[i][j]) + " ");
			System.out.println("|");
			System.out.print("-");
			for(int j = 0; j < LENGTH; j++)
				System.out.print("----");
			System.out.println();
		}
				
		
		System.out.println("\n---------------------------------------------------------");
	}
	
	public int getWinner() {
		return winner;
	}

	public boolean isEnded() {
		return ended;
	}

	public static String getSymbol(int symbol) {
		switch(symbol) {
			case -1:
				return "X";
			case 1:
				return "O";
			case 0:
				return " ";
			default:
				return "*";
		}
	}
	
}
