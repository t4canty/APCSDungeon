package fileIO;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import displayComponents.SoundEffect;

public class SoundLoader {
	
	public static SoundEffect FOOTSTEP;
	public static SoundEffect GUNSHOT;
	public static SoundEffect ACTIONMUSIC;
	
	public static boolean finished = false;
	
	
	public static void loadAllSounds(boolean isJar) {
		GUNSHOT = new SoundEffect("src/sound/pistolgunshot.wav");
		FOOTSTEP = new SoundEffect("src/sound/footsteps.wav");
		ACTIONMUSIC = new SoundEffect("src/sound/gamemusic alleyway loop.wav");
		
		finished = true;
	}
	
	
	public static Clip getClipFromFile(String filepath) {
		try {
			AudioSystem.getAudioInputStream(new File(filepath));
			return AudioSystem.getClip();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
