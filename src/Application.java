import java.io.IOException;
import java.util.Scanner;

import io.bhagat.util.SerializableUtil;

public class Application {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Would you like to train a new agent? (Y/n) ");
		char c = scanner.nextLine().toLowerCase().charAt(0);
		Agent p1 = new Agent(Environment.X);
		if(c == 'y') {
			Environment e = new Environment();
			Agent p2 = new Agent(Environment.O);
			int score = Agent.train(e, p1, p2, 50000);
			try {
				SerializableUtil.serialize(p1, "agent.ser");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
//			p1.setLogging(true);
			System.out.println("Score: " + score);
		} else {
			try {
				p1 = SerializableUtil.deserialize("agent.ser");
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		
		p1.setEpsilon(0);
		Game game = new Game(true, p1, new Player("Rishav", Environment.O));
		System.out.print("Would you like to play? (Y/n) ");
		c = scanner.nextLine().toLowerCase().charAt(0);
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
