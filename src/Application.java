import java.util.Scanner;

public class Application {

	public static void main(String[] args) {
		Environment e = new Environment();
		Agent p1 = new Agent(Environment.X);
		Agent p2 = new Agent(Environment.O);
		int score = Agent.train(e, p1, p2, 50000);
		System.out.println("Score: " + score);
//		p1.setLogging(true);
		p1.setEpsilon(0);
		Game game = new Game(true, p1, new Player("Rishav", Environment.O));
		Scanner scanner = new Scanner(System.in);
//		System.out.print("Would you like to play? (Y/N) ");
		char c = 'y'; // scanner.nextLine().toLowerCase().charAt(0);
		while(c == 'y') {
			System.out.println("Playing . . .");
			game.getEnvironment().clear();
			game.play();
			System.out.print("Would you like to play again? (Y/N) ");
			c = scanner.nextLine().toLowerCase().charAt(0);
		}
		scanner.close();
	}
	
}
