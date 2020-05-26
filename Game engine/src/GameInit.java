import java.awt.Dimension;
import java.nio.file.Path;
import java.nio.file.Paths;

import driver.Driver;

public class GameInit {
	//========Varibles========//
	private Path cDir;
	private Driver jDriver;
	//========Constructor========//
	public GameInit(Path filePath, String title, boolean debug, Dimension bounds) {
		jDriver = new Driver(bounds, title);
		if(debug) System.out.println("Created Jframe");
	}
	//========Main========//
	public static void main(String[] args) {
		GameInit gameInit = new GameInit(Paths.get(".").toAbsolutePath(), "test", true, new Dimension(800, 800));
	}
}
