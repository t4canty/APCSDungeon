package displayComponents;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.apache.commons.io.IOUtils;
import fileIO.SoundLoader;
/**
 * 
 * Comments added Jun 10, 2020
 * @author t4canty
 * @author TJ178
 * Sound handler class. 
 */
public class SoundEffect implements LineListener{
	//========Variables========//
	private ArrayList<Clip> clips = new ArrayList<Clip>();
	private byte[] clipData;
	private float gain = 1.0f;
	private boolean isContinuousEffect = false;
	
	//========Constructors========//
	/**
	 * Constructs a sound object from the given filepath.
	 * @param filepath
	 * path to the sound file.
	 * @param isJar
	 * set to true when compiling.
	 * @param debug
	 * debug param.
	 */
	public SoundEffect(String filepath, boolean isJar, boolean debug) {
		SoundLoader.totalNumberLoaded++;
		if(debug) System.out.println(filepath);
		try {
	        if(isJar)
	        	clipData = getClipFromJar(filepath);
	        else
	        	clipData = getClipFromFolder(filepath);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	/**
	 * Constructs a sound object from the given filepath.
	 * @param filepath
	 * path to the sound file.
	 * @param isJar
	 * set to true when compiling.
	 * @param debug
	 * debug param.
	 * @param volume
	 * volume level of the clip. 
	 */
	public SoundEffect(String filepath, boolean isJar, boolean debug, double volume) {
		this(filepath, isJar, debug);
		setVolume(volume);
	}
	/**
	 * Constructs a sound object from the given filepath.
	 * @param filepath
	 * path to the sound file.
	 * @param isJar
	 * set to true when compiling.
	 * @param debug
	 * debug param.
	 * @param volume
	 * volume level of the clip.
	 * @param isLoop
	 * Loops the clip if true.
	 */
	public SoundEffect(String filepath, boolean isJar, boolean debug, double volume, boolean isLoop) {
		this(filepath, isJar, debug, volume);
		isContinuousEffect = isLoop;
	}
	/**
	 * Constructs a sound object from the given filepath.
	 * @param filepath
	 * path to the sound file.
	 * @param isJar
	 * set to true when compiling.
	 * @param debug
	 * debug param.
	 * @param isLoop
	 * Loops the clip if true.
	 */
	public SoundEffect(String filepath, boolean isJar, boolean debug, boolean isLoop) {
		this(filepath, isJar, debug);
		isContinuousEffect = isLoop;
	}
	
	//========Methods========// 
	private byte[] getClipFromJar(String fPath) throws IOException {
		return IOUtils.toByteArray(SoundEffect.class.getResourceAsStream(fPath));
	}
	private byte[] getClipFromFolder(String fPath) throws IOException {
		return Files.readAllBytes(new File(fPath).toPath());
	}
	
	/**
	 * starts the audio clip.
	 */
   	public void play() {
   		if(!isContinuousEffect || clips.size() == 0) {
	   		ByteArrayInputStream bStream = new ByteArrayInputStream(clipData);
	   		try {
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(bStream);
				clips.add(AudioSystem.getClip());
				clips.get(clips.size()-1).open(audioStream);
				FloatControl gainControl = (FloatControl) clips.get(clips.size()-1).getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(gain);
			} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
				e.printStackTrace();
			}
	   		if(isContinuousEffect) {
	   			clips.get(clips.size()-1).loop(Clip.LOOP_CONTINUOUSLY);
	   		}else {
	   			clips.get(clips.size()-1).start();
	   		}
	   		clips.get(clips.size()-1).addLineListener(this);
   		}
	}
   	
   	/**
   	 * plays the clip on a loop.
   	 */
   	public void loop() {
   		if(clips.size() == 0) { //Load the sound effect if it isn't already loaded.
   			play();
   		}
   		clips.get(0).loop(Clip.LOOP_CONTINUOUSLY); //Play the clip.
   	}
   	
   	public void stopLoop() {
   		if(clips.size() != 0) {
   			clips.get(0).loop(0);
   		}
   	}
   	
   	public void stop() {
   		if(clips.size() != 0) {
   			clips.get(0).stop();
   		}
   	}
   	
   	public void setVolume(double volume) {
   		gain = 20f * (float) Math.log10(volume);
   	}

	@Override
	public void update(LineEvent event) {
		if(event.getType() == Type.STOP) {
			((Clip)event.getSource()).close();
			clips.remove((Clip)event.getSource());
		}
		
	}
}
