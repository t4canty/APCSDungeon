import java.awt.Dimension;
import java.nio.file.Path;
import java.nio.file.Paths;

import displayComponents.Startup;
import driver.Driver;
import fileIO.ImageLoader;

public class GameInit implements Runnable{
	//========Varibles========//
	private Path cDir;
	//========Constructor========//
	public GameInit(Path filePath, String title, boolean debug, Dimension bounds, boolean isJar){
		ImageLoader.loadAllImages(isJar);
		new Startup(bounds, title, debug, isJar);
		if(debug) System.out.println("Created Jframe");
	}
	//========Main========//
	public static void main(String[] args) {
		GameInit gameInit = new GameInit(Paths.get(".").toAbsolutePath(), "test", true, new Dimension(1000, 1000), true);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
