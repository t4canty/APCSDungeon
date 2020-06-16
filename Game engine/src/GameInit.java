import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.nio.file.Path;
import java.nio.file.Paths;

import displayComponents.HeadlessGame;
import displayComponents.LinuxStartup;
import displayComponents.Startup;

public class GameInit{
	private static int os;
	//========Constructor========//
	public GameInit(Path filePath, String title, boolean debug, Dimension bounds, boolean isJar, String path){
		if(debug) System.out.println("Path:" + path);
		if(os == 0) {new Startup(bounds, title, debug, isJar, path);}
		else if(os == 1) {new LinuxStartup(bounds, title, debug, isJar, path);}
		
		if(debug) System.out.println("Created Startup.");
	}
	//========Main========//
	public static void main(String[] args) {
		boolean debug = false;
		if(args.length != 0) {
			if(args[0].equals("true")) debug = true;
			else debug = false;
		}
		if(debug) System.out.println("Headless:" + GraphicsEnvironment.isHeadless());
		if(GraphicsEnvironment.isHeadless()) {
			new HeadlessGame();
		}
		else {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			String env;
			if(System.getProperty("os.name").toLowerCase().indexOf("win") != -1) {
				env = System.getenv("APPDATA") + "\\";
				os = 0;
			}else {
				env = System.getenv("HOME") + "/";
				os = 1;
			}
			//For now, the game fills up the max area of a screen, but the scale code should theoretically allow for any square resolution.
			GameInit gameInit = new GameInit(Paths.get(".").toAbsolutePath(), "test", debug, new Dimension(screenSize.height - 50, screenSize.height -50), true, env);
		}
	}
}
