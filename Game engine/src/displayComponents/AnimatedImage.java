package displayComponents;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import fileIO.ImageLoader;

public class AnimatedImage {
	private BufferedImage spritesheet;
	private int numFrames = 60;
	private int currentFrame = 1;
	private int currentFrameY = 0;
	private boolean isStatic = false;
	private boolean oneTimeAnimation = false;
	private boolean isFinished = false;	//used only with one time animation
	
	public AnimatedImage(BufferedImage source) {
		if(source == null) {
			isStatic = true;
			spritesheet = ImageLoader.NO_IMAGE;
		}else {
			this.numFrames = source.getHeight() / source.getWidth();
			spritesheet = source;
		}
	}
	
	public AnimatedImage(BufferedImage source, boolean onlyRunOnce) {
		this(source);
		oneTimeAnimation  = onlyRunOnce;
	}
	
	public Image getNextFrame() {
		if(isStatic) {
			return (Image) spritesheet;
		}
		int frameY = currentFrame * spritesheet.getWidth();
		if(!isFinished) currentFrame++;
		
		if(currentFrame == numFrames-1) {
			if(oneTimeAnimation) {
				isFinished = true;
			}else {
				currentFrame = 0;
			}
		}
		
		Image tmp = null;
		try {
			tmp = (Image) spritesheet.getSubimage(0, frameY, spritesheet.getWidth(), spritesheet.getWidth());
		}catch(RasterFormatException e) {
			e.printStackTrace();
		}
		
		return tmp;
	}
	
	public Image getCurrentFrame() {
		if(isStatic) {
			return (Image) spritesheet;
		}
		return (Image) spritesheet.getSubimage(0, currentFrameY, spritesheet.getWidth(), spritesheet.getWidth());
	}
	
	public void advanceCurrentFrame() {
		if(!isFinished) currentFrame++;
		if(currentFrame == numFrames-1) {
			if(oneTimeAnimation) {
				isFinished = true;
			}else {
				currentFrame = 0;
			}
		}
		currentFrameY = currentFrame * spritesheet.getWidth();
	}
	
	public boolean isStatic() {
		return isStatic;
	}
}
