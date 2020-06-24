package apcs.apcsdungeon.io;

import apcs.apcsdungeon.displaycomponents.SoundEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created May 30, 2020
 *
 * @author t4canty
 * @author TJ178
 * Class to load all audio files in the bg.
 */
public class SoundLoader implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(SoundLoader.class);
	//========Static Variables========//
	public static SoundEffect FOOTSTEP;
	public static SoundEffect PISTOL_GUNSHOT, AR15_GUNSHOT, MONEYSHOOTER, DEAGLE_GUNSHOT, LAUNCHER_GUNSHOT, LASERBEAM, B;
	public static SoundEffect SMALLRELOAD;
	public static SoundEffect ACTIONMUSIC;
	public static boolean finished = false;
	public static int totalNumberToLoad = 8;
	public static int totalNumberLoaded = 0;
	//========Variables========//
	private Thread t;
	private boolean isJar;

	//========Methods========//
	private static void loadAllSounds(boolean isJar) {
		if (!isJar) {
			PISTOL_GUNSHOT = new SoundEffect("src/sound/pistolgunshot.wav", isJar, 0.5);
			AR15_GUNSHOT = new SoundEffect("src/sound/ar15gunshot.wav", isJar, 0.5);
			MONEYSHOOTER = new SoundEffect("src/sound/brrr.wav", isJar, 0.5, true);
			DEAGLE_GUNSHOT = new SoundEffect("src/sound/thiccgunshot.wav", isJar, 0.5);
			LAUNCHER_GUNSHOT = new SoundEffect("src/sound/grenadelauncher.wav", isJar, 0.5);
			LASERBEAM = new SoundEffect("src/sound/laserbeam.wav", isJar, 0.7, true);
			B = new SoundEffect("src/sound/B.wav", isJar, 1);

			SMALLRELOAD = new SoundEffect("src/sound/smallReload.wav", isJar, 0.5);
			FOOTSTEP = new SoundEffect("src/sound/footsteps.wav", isJar, 0.75);
			ACTIONMUSIC = new SoundEffect("src/sound/gamemusic alleyway loop.wav", isJar);
		} else {
			PISTOL_GUNSHOT = new SoundEffect("/sound/pistolgunshot.wav", isJar, 0.5);
			AR15_GUNSHOT = new SoundEffect("/sound/ar15gunshot.wav", isJar, 0.5);
			MONEYSHOOTER = new SoundEffect("/sound/brrr.wav", isJar, 0.5, true);
			DEAGLE_GUNSHOT = new SoundEffect("/sound/thiccgunshot.wav", isJar, 0.5);
			LAUNCHER_GUNSHOT = new SoundEffect("/sound/grenadelauncher.wav", isJar, 0.5);
			LASERBEAM = new SoundEffect("/sound/laserbeam.wav", isJar, 0.7, true);
			B = new SoundEffect("/sound/B.wav", isJar, 1);

			SMALLRELOAD = new SoundEffect("/sound/smallReload.wav", isJar, 0.5);

			FOOTSTEP = new SoundEffect("/sound/footsteps.wav", isJar, 0.75);

			ACTIONMUSIC = new SoundEffect("/sound/gamemusic alleyway loop.wav", isJar);
		}
		finished = true;
	}

	// multithread stuff.
	@Override
	public void run() {
		logger.debug("Loading Audio Files");
		loadAllSounds(isJar);
	}

	public void start(boolean isJar) {
		this.isJar = isJar;
		logger.debug("Starting AudioLoad Thread");
		if (t == null)
			t = new Thread(this, "AudioLoad");
		t.start();
	}

	//========Getter========//
	public boolean isAlive() {
		return t.isAlive();
	}
}
