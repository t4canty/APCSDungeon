package fileIO;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageLoader {
	public static BufferedImage MARINE_FRONTIDLE;
	public static BufferedImage MARINE_SIDEIDLE;
	public static BufferedImage NPC_FRONTIDLE;
	public static BufferedImage NPC_SIDEIDLE;
	public static BufferedImage WSB_FRONTIDLE;
	public static BufferedImage WSB_SIDEIDLE;
	public static BufferedImage NO_IMAGE;
	
	
	public static void loadAllImages(boolean isJar) {
		if(isJar) {
			//load from jar
		}else {
			try {
			NO_IMAGE = getImageFromFolder("src/img/noimage.png");
			MARINE_FRONTIDLE = getImageFromFolder("src/img/Marine_frontIdle.png");
			MARINE_SIDEIDLE = getImageFromFolder("src/img/Marine_sideIdle.png");
			NPC_FRONTIDLE = getImageFromFolder("src/img/NPC_frontIdle.png");
			NPC_SIDEIDLE = getImageFromFolder("src/img/NPC_sideIdle.png");
			WSB_FRONTIDLE = getImageFromFolder("src/img/WSB_frontIdle.png");
			WSB_SIDEIDLE = getImageFromFolder("src/img/WSB_sideIdle.png");
			
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static BufferedImage getImageFromFolder(String filePath) throws IOException {
		System.out.println(new File(filePath).getAbsolutePath());
		BufferedImage temp = ImageIO.read(new File(filePath));
		if(temp == null)
			throw new IOException();
		return temp;
	}
	
	/*public static BufferedImage getImageFromJar(String filePath) throws IOException {
		if(getClass().getResourceAsStream(filePath) == null) {
			System.err.println("Error, getClass is null");
		}
		InputStream in = getClass().getResourceAsStream(filePath);
		//byte[] arr = new byte[in.available()];
		//in.read(arr);
		return ImageIO.read(in);
	}*/
}
