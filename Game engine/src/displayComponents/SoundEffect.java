package displayComponents;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffect implements LineListener{
	private ArrayList<Clip> clips = new ArrayList<Clip>();
	private byte[] clipData;
	
	
	public SoundEffect(String filepath) {
		
		System.out.println(filepath);
		try {
	         File temp = new File(filepath);
	         clipData = Files.readAllBytes(temp.toPath());
	         
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }
	   

   	public void play() {
   		ByteArrayInputStream bStream = new ByteArrayInputStream(clipData);
   		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bStream);
			clips.add(AudioSystem.getClip());
			clips.get(clips.size()-1).open(audioStream);
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
   		clips.get(clips.size()-1).start();
   		clips.get(clips.size()-1).addLineListener(this);
	}

	@Override
	public void update(LineEvent event) {
		if(event.getType() == Type.STOP) {
			((Clip)event.getSource()).close();
			clips.remove((Clip)event.getSource());
		}
		
	}
}
