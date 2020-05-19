import java.awt.Dimension;
import java.nio.file.Path;

import driver.Driver;

public class GameInit {
	private Path filesPath;
	private Driver jDriver;
	public GameInit(Path filePath, String title, boolean debug, Dimension bounds) {
		jDriver = new Driver(bounds, title);
		if(debug) System.out.println("Created Jframe");
	}
	public static void main(String[] args) {
		GameInit gameInit = new GameInit(null, "test", true, new Dimension(1000, 1000));
	}
}
