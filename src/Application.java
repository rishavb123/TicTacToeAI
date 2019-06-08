public class Application {

	public static void main(String[] args) {
		Game game = new Game(true, new Player(Environment.X), new Agent(Environment.O));
		game.play();
	}
	
}
