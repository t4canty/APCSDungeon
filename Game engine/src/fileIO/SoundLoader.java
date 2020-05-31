package fileIO;
import displayComponents.SoundEffect;

/**
 * 
 * Created May 30, 2020
 * @author t4canty
 * @author TJ178
 * Class to load all audio files in the bg. 
 *
 */
public class SoundLoader implements Runnable {
	
	public static SoundEffect FOOTSTEP;
	public static SoundEffect GUNSHOT;
	public static SoundEffect ACTIONMUSIC;
	private Thread t;
	private boolean isJar;
	private static boolean debug;
	
	private static void loadAllSounds(boolean isJar) {
		if(!isJar) {
			GUNSHOT = new SoundEffect("src/sound/pistolgunshot.wav", isJar, debug);
			FOOTSTEP = new SoundEffect("src/sound/footsteps.wav", isJar, debug);
			ACTIONMUSIC = new SoundEffect("src/sound/gamemusic alleyway loop.wav", isJar, debug);
		}else {
			GUNSHOT = new SoundEffect("/sound/pistolgunshot.wav", isJar, debug);
			FOOTSTEP = new SoundEffect("/sound/footsteps.wav", isJar, debug);
			ACTIONMUSIC = new SoundEffect("/sound/gamemusic alleyway loop.wav", isJar, debug);
		}
	}

	@Override
	public void run() {
		if(debug) System.out.println("Loading Audio Files");
		loadAllSounds(isJar);
	}
	
	public void start(boolean isJar, boolean debug) {
		this.isJar = isJar;
		this.debug = debug;
		if(debug) System.out.println("Starting AudioLoad Thread");
		if(t == null)
			t = new Thread(this, "AudioLoad");
		t.start();
	}
	public boolean isAlive() {return t.isAlive();}
}
