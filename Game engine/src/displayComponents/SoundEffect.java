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

public class SoundEffect implements LineListener{
	private ArrayList<Clip> clips = new ArrayList<Clip>();
	private byte[] clipData;
	private float gain = 1.0f;
	
	
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
	
	public SoundEffect(String filepath, boolean isJar, boolean debug, double volume) {
		this(filepath, isJar, debug);
		setVolume(volume);
	}
	   
	private byte[] getClipFromJar(String fPath) throws IOException {
		return IOUtils.toByteArray(SoundEffect.class.getResourceAsStream(fPath));
	}
	private byte[] getClipFromFolder(String fPath) throws IOException {
		return Files.readAllBytes(new File(fPath).toPath());
	}
	
   	public void play() {
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
   		clips.get(clips.size()-1).start();
   		clips.get(clips.size()-1).addLineListener(this);
	}
   	
   	public void loop() {
   		if(clips.size() == 0) {
   			play();
   		}
   		clips.get(0).loop(Clip.LOOP_CONTINUOUSLY);
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
