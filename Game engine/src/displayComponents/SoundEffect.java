package displayComponents;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffect {
	private ArrayList<Clip> clips = new ArrayList<Clip>();
	
	public SoundEffect(String filepath) {
		System.out.println(filepath);
		try {
	         URL url = this.getClass().getClassLoader().getResource(filepath);
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filepath));
	         clips.add(AudioSystem.getClip());
	         clips.get(0).open(audioInputStream);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	   }
	   

   	public void play() {
   		if(clip.isRunning()) {
		   clip.stop(); 
   		}
		   clip.setFramePosition(0);
		   clip.start();
	}
   	
   	public void setPos(int framePosition) {
   		clip.setFramePosition(framePosition);
   	}
}
