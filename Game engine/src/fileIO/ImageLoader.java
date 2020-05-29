package fileIO;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Loads images into a static reference so they are kept in memory
 * Created May 28, 2020
 * @author TJ178
 * @author t4canty
 *
 */

public class ImageLoader {
	//all stored images
	public static BufferedImage NO_IMAGE;
	
	public static BufferedImage MARINE_FRONTIDLE;
	public static BufferedImage MARINE_SIDEIDLE;
	public static BufferedImage MARINE_MOVE;
	public static BufferedImage WSB_FRONTIDLE;
	public static BufferedImage WSB_SIDEIDLE;
	
	public static BufferedImage NPC_FRONTIDLE;
	public static BufferedImage NPC_SIDEIDLE;
	
	public static BufferedImage BULLET = NO_IMAGE;
	public static BufferedImage BADGUN;
	public static BufferedImage PISTOLMAG;
	
	public static BufferedImage ROOM_1;
	
	
	//called at beginning of program, loads all images
	public static void loadAllImages(boolean isJar) {
		if(isJar) {
			//load from jar
		}else {
			try {
			NO_IMAGE = getImageFromFolder("src/img/noimage.png");
			MARINE_FRONTIDLE = getImageFromFolder("src/img/Marine_frontIdle.png");
			MARINE_SIDEIDLE = getImageFromFolder("src/img/Marine_sideIdle.png");
			WSB_FRONTIDLE = getImageFromFolder("src/img/WSB_frontIdle.png");
			WSB_SIDEIDLE = getImageFromFolder("src/img/WSB_sideIdle.png");
			
			NPC_FRONTIDLE = getImageFromFolder("src/img/NPC_frontIdle.png");
			NPC_SIDEIDLE = getImageFromFolder("src/img/NPC_sideIdle.png");
			
			BADGUN = getImageFromFolder("src/img/badGun.png");
			PISTOLMAG = getImageFromFolder("src/img/pistolMag.png");
			
			ROOM_1 = getImageFromFolder("src/img/testbackground.png");
			
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//gets an image from the filesystem
	public static BufferedImage getImageFromFolder(String filePath) throws IOException {
		System.out.println(new File(filePath).getAbsolutePath());
		BufferedImage temp = ImageIO.read(new File(filePath));
		if(temp == null)
			throw new IOException();
		return temp;
	}
	
	//TODO: needs to get fixed
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
