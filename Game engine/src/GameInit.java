import java.awt.Dimension;
import java.nio.file.Path;
import java.nio.file.Paths;
import displayComponents.Startup;

public class GameInit{
	//========Constructor========//
	public GameInit(Path filePath, String title, boolean debug, Dimension bounds, boolean isJar){
		new Startup(bounds, title, debug, isJar);
		if(debug) System.out.println("Created Jframe");
	}
	//========Main========//
	public static void main(String[] args) {
		boolean debug = true;
		if(args.length != 0) {
			if(args[0].equals("true"))
				debug = true;
		}
		GameInit gameInit = new GameInit(Paths.get(".").toAbsolutePath(), "test", debug, new Dimension(1000, 1000), true);
	}
}
