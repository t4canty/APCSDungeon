import java.awt.Dimension;
import java.awt.Toolkit;
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
		boolean debug = false;
		if(args.length != 0) {
			if(args[0].equals("true"))
				debug = true;
		}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		GameInit gameInit = new GameInit(Paths.get(".").toAbsolutePath(), "test", debug, new Dimension(1000, 1000), true);
	}
}
