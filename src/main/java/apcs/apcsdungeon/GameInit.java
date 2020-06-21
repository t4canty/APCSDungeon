package apcs.apcsdungeon;

import apcs.apcsdungeon.displaycomponents.HeadlessGame;
import apcs.apcsdungeon.displaycomponents.LinuxStartup;
import apcs.apcsdungeon.displaycomponents.Startup;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

public class GameInit {
	private static final int OS_WINDOWS = 0;
	private static final int OS_LINUX = 1;

	/**
	 * Constructor.
	 *
	 * @param title  Window title
	 * @param debug  Debug mode
	 * @param bounds Windows bounds
	 * @param isJar
	 * @param path
	 */
	public GameInit(String title, boolean debug, Dimension bounds, boolean isJar, int os, String path) {
		if (debug) System.out.println("Path:" + path);
		if (os == OS_WINDOWS) {
			new Startup(bounds, title, debug, isJar, path);
		} else if (os == OS_LINUX) {
			new LinuxStartup(bounds, title, debug, isJar, path);
		}

		if (debug) System.out.println("Created Startup.");
	}

	/**
	 * Game's main method.
	 *
	 * @param args Command-line arguments.
	 */
	public static void main(String[] args) {
		boolean debug = false;
		// Argument processing
		for (String arg : args) {
			switch (arg.toLowerCase()) {
				case "-debug":
					debug = true;
					break;
			}
		}

		if (debug) System.out.println("Headless: " + GraphicsEnvironment.isHeadless());
		if (GraphicsEnvironment.isHeadless()) {
			new HeadlessGame();
		} else {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int os;
			String env;
			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				env = System.getenv("APPDATA") + "\\";
				os = OS_WINDOWS;
			} else {
				env = System.getenv("HOME") + "/";
				os = OS_LINUX;
			}

			// For now, the game fills up the max area of a screen, but the scale code should theoretically allow for
			// any square resolution.
			new GameInit("test", debug, new Dimension(screenSize.height - 50, screenSize.height - 50),
					true, os, env);
		}
	}
}
