package fileIO;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import displayComponents.SoundEffect;

public class SoundLoader implements Runnable{
	
	public static SoundEffect FOOTSTEP;
	public static SoundEffect GUNSHOT;
	public static SoundEffect ACTIONMUSIC;
	
	public static boolean finished = false;
	public static int totalNumberToLoad = 3;
	public static int totalNumberLoaded = 0;
	

	private boolean isJar;
	Thread t;
	
	public static void loadAllSounds(boolean isJar) {
		GUNSHOT = new SoundEffect("src/sound/pistolgunshot.wav");
		FOOTSTEP = new SoundEffect("src/sound/footsteps.wav");
		ACTIONMUSIC = new SoundEffect("src/sound/gamemusic alleyway loop.wav");
		
		finished = true;
	}
	
	
	public static Clip getClipFromFile(String filepath) {
		totalNumberLoaded++;
		try {
			AudioSystem.getAudioInputStream(new File(filepath));
			return AudioSystem.getClip();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public void run() {
		System.out.println("Running SoundLoad Thread");
		loadAllSounds(isJar);
	}
	
	public void start(boolean isJar) {
		System.out.println("Starting SoundLoad Thread");
		this.isJar = isJar;
		if(t == null)
			t = new Thread(this, "SoundLoader");
		t.start();
	}
	public boolean isAlive() {
		return t.isAlive();
	}

}
