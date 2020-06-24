package apcs.apcsdungeon;

import apcs.apcsdungeon.displaycomponents.HeadlessGame;
import apcs.apcsdungeon.displaycomponents.LinuxStartup;
import apcs.apcsdungeon.displaycomponents.Startup;
import ch.qos.logback.classic.Level;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameInit {
	private static final Logger logger = LoggerFactory.getLogger(GameInit.class);

	private static final int OS_WINDOWS = 0;
	private static final int OS_LINUX = 1;

	/**
	 * Constructor.
	 *
	 * @param title  Window title
	 * @param bounds Windows bounds
	 * @param isJar
	 * @param path
	 */
	public GameInit(String title, Dimension bounds, boolean isJar, int os, String path) {
		logger.debug("Path:" + path);
		if (os == OS_WINDOWS) {
			new Startup(bounds, title, isJar, path);
		} else if (os == OS_LINUX) {
			new LinuxStartup(bounds, title, isJar, path);
		}

		logger.debug("Created Startup.");
	}

	/**
	 * Game's main method.
	 *
	 * @param args Command-line arguments.
	 */
	public static void main(String[] args) {
		// Root logger
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger)
				org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
		// Argument processing
		for (String arg : args) {
			switch (arg.toLowerCase()) {
				case "-debug":
					root.setLevel(Level.DEBUG);
					break;
				case "-trace":
					root.setLevel(Level.ALL);
					break;
			}
		}

		logger.debug("Headless: " + GraphicsEnvironment.isHeadless());
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
			new GameInit("test", new Dimension(screenSize.height - 50, screenSize.height - 50),
					true, os, env);
		}
	}
}
