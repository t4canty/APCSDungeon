import java.awt.Dimension;
import java.nio.file.Path;
import java.nio.file.Paths;

import driver.Driver;
import fileIO.ImageLoader;
import fileIO.SoundLoader;

public class GameInit {
	//========Varibles========//
	private Path cDir;
	private Driver jDriver;
	//========Constructor========//
	public GameInit(Path filePath, String title, boolean debug, Dimension bounds, boolean isJar) {
		ImageLoader.loadAllImages(isJar);
		SoundLoader.loadAllSounds(isJar);
		jDriver = new Driver(bounds, title, debug, isJar);
		if(debug) System.out.println("Created Jframe");
	}
	//========Main========//
	public static void main(String[] args) {
		GameInit gameInit = new GameInit(Paths.get(".").toAbsolutePath(), "test", true, new Dimension(1000, 1000), true);
	}
}
