package gameObjects;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class AnimatedImage {
	private BufferedImage spritesheet;
	private int numFrames = 60;
	private int currentFrame = 1;
	private int currentFrameX = 0;
	private int currentFrameY = 0;
	private boolean isStatic = false;
	
	public AnimatedImage(String filePath, boolean isJar) {
		if(filePath.isBlank()) {
			filePath = "src/img/noimage.png";
			isStatic = true;
		}
		try {
			if(isJar) {
				spritesheet = getImageFromJar(filePath);
			}else {
				spritesheet = getImageFromFolder(filePath);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public Image getNextFrame() {
		if(isStatic) {
			return (Image) spritesheet;
		}
		int frameY = currentFrame * 512;
		currentFrame++;
		if(currentFrame == numFrames-1) {
			currentFrame = 0;
		}
		
		Image tmp = null;
		try {
			tmp = (Image) spritesheet.getSubimage(0, frameY, 512, 512);
		}catch(RasterFormatException e) {
			e.printStackTrace();
		}
		
		return tmp;
	}
	
	public Image getCurrentFrame() {
		if(isStatic) {
			return (Image) spritesheet;
		}
		return (Image) spritesheet.getSubimage(0, currentFrameY, 512, 512);
	}
	
	public void advanceCurrentFrame() {
		currentFrame++;
		if(currentFrame == numFrames-1) {
			currentFrame = 0;
		}
		currentFrameY = currentFrame * 512;
	}
	
	public BufferedImage getImageFromFolder(String filePath) throws IOException {
		System.out.println(new File(filePath).getAbsolutePath());
		BufferedImage temp = ImageIO.read(new File(filePath));
		if(temp == null)
			throw new IOException();
		return temp;
	}
	
	public BufferedImage getImageFromJar(String filePath) throws IOException {
		if(getClass().getResourceAsStream(filePath) == null) {
			System.err.println("Error, getClass is null");
		}
		InputStream in = getClass().getResourceAsStream(filePath);
		//byte[] arr = new byte[in.available()];
		//in.read(arr);
		return ImageIO.read(in);
	}
}
