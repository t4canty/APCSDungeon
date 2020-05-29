package fileIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
	
	public static BufferedImage MARINE_FRONTIDLE, MARINE_SIDEIDLE, MARINE_BACKIDLE, MARINE_FRONTMOVE, MARINE_SIDEMOVE, MARINE_BACKMOVE, MARINE_FRONTHURT, MARINE_SIDEHURT, MARINE_BACKHURT;
	public static BufferedImage[] MARINESKIN = new BufferedImage[9];
	
	public static BufferedImage  WSB_FRONTIDLE, WSB_SIDEIDLE, WSB_BACKIDLE, WSB_FRONTMOVE, WSB_SIDEMOVE, WSB_BACKMOVE, WSB_FRONTHURT, WSB_SIDEHURT, WSB_BACKHURT;
	public static BufferedImage[] WSBSKIN = new BufferedImage[9];
	
	public static BufferedImage  NPC_FRONTIDLE, NPC_SIDEIDLE, NPC_BACKIDLE, NPC_FRONTMOVE, NPC_SIDEMOVE, NPC_BACKMOVE, NPC_FRONTHURT, NPC_SIDEHURT, NPC_BACKHURT;
	public static BufferedImage[] NPCSKIN = new BufferedImage[9];
	
	public static BufferedImage BULLET = NO_IMAGE;
	public static BufferedImage BADGUN;
	public static BufferedImage PISTOLMAG;
	
	public static BufferedImage ROOM_1;
	
	
	//called at beginning of program, loads all images
	public static void loadAllImages(boolean isJar) {
		if(isJar) {
			System.out.println("Loading images from Jar");
			try {
			NO_IMAGE = getImageFromJar("/img/noimage.png");
			
			MARINE_FRONTIDLE = getImageFromJar("/img/Marine_frontIdle.png");
			MARINESKIN[0] = MARINE_FRONTIDLE;
			MARINE_SIDEIDLE = getImageFromJar("/img/Marine_sideIdle.png");
			MARINESKIN[1] = MARINE_SIDEIDLE;
			MARINE_BACKIDLE = getImageFromJar("/img/Marine_backIdle.png");
			MARINESKIN[2] = MARINE_BACKIDLE;
			MARINE_FRONTMOVE = MARINE_FRONTIDLE;
			MARINESKIN[3] = MARINE_FRONTMOVE;
			MARINE_SIDEMOVE = MARINE_SIDEIDLE;
			MARINESKIN[4] = MARINE_SIDEMOVE;
			MARINE_BACKMOVE = MARINE_BACKIDLE;
			MARINESKIN[5] = MARINE_BACKMOVE;
			MARINE_FRONTHURT = MARINE_FRONTIDLE;
			MARINESKIN[6] = MARINE_FRONTHURT;
			MARINE_SIDEHURT = MARINE_SIDEIDLE;
			MARINESKIN[7] = MARINE_SIDEHURT;
			MARINE_BACKHURT = MARINE_BACKIDLE;
			MARINESKIN[8] = MARINE_BACKHURT;
			
			
			WSB_FRONTIDLE = getImageFromJar("/img/WSB_frontIdle.png");
			WSBSKIN[0] = WSB_FRONTIDLE;
			WSB_SIDEIDLE = getImageFromJar("/img/WSB_sideIdle.png");
			WSBSKIN[1] = WSB_SIDEIDLE;
			WSB_BACKIDLE = getImageFromJar("/img/WSB_backIdle.png");
			WSBSKIN[2] = WSB_BACKIDLE;
			WSB_FRONTMOVE = WSB_FRONTIDLE;
			WSBSKIN[3] = WSB_FRONTMOVE;
			WSB_SIDEMOVE = WSB_SIDEIDLE;
			WSBSKIN[4] = WSB_SIDEMOVE;
			WSB_BACKMOVE = WSB_BACKIDLE;
			WSBSKIN[5] = WSB_BACKMOVE;
			WSB_FRONTHURT = WSB_FRONTIDLE;
			WSBSKIN[6] = WSB_FRONTHURT;
			WSB_SIDEHURT = WSB_SIDEIDLE;
			WSBSKIN[7] = WSB_SIDEHURT;
			WSB_BACKHURT = WSB_BACKIDLE;
			WSBSKIN[8] = WSB_BACKHURT;
			
			NPC_FRONTIDLE = getImageFromJar("/img/NPC_frontIdle.png");
			NPCSKIN[0] = NPC_FRONTIDLE;
			NPC_SIDEIDLE = getImageFromJar("/img/NPC_sideIdle.png");
			NPCSKIN[1] = NPC_SIDEIDLE;
			NPC_BACKIDLE = getImageFromJar("/img/NPC_backIdle.png");
			NPCSKIN[2] = NPC_BACKIDLE;
			NPC_FRONTMOVE = NPC_FRONTIDLE;
			NPCSKIN[3] = NPC_FRONTMOVE;
			NPC_SIDEMOVE = NPC_SIDEIDLE;
			NPCSKIN[4] = NPC_SIDEMOVE;
			NPC_BACKMOVE = NPC_BACKIDLE;
			NPCSKIN[5] = NPC_BACKMOVE;
			NPC_FRONTHURT = NPC_FRONTIDLE;
			NPCSKIN[6] = NPC_FRONTHURT;
			NPC_SIDEHURT = NPC_SIDEIDLE;
			NPCSKIN[7] = NPC_SIDEHURT;
			NPC_BACKHURT = NPC_BACKIDLE;
			NPCSKIN[8] = NPC_BACKHURT;
			
			
			BADGUN = getImageFromJar("/img/badGun.png");
			PISTOLMAG = getImageFromJar("/img/pistolMag.png");
			
			ROOM_1 = getImageFromJar("/img/testbackground.png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("Loading images from Folder");
			try {
				NO_IMAGE = getImageFromFolder("src/img/noimage.png");
				
				MARINE_FRONTIDLE = getImageFromFolder("src/img/Marine_frontIdle.png");
				MARINESKIN[0] = MARINE_FRONTIDLE;
				MARINE_SIDEIDLE = getImageFromFolder("src/img/Marine_sideIdle.png");
				MARINESKIN[1] = MARINE_SIDEIDLE;
				MARINE_BACKIDLE = getImageFromFolder("src/img/Marine_backIdle.png");
				MARINESKIN[2] = MARINE_BACKIDLE;
				MARINE_FRONTMOVE = MARINE_FRONTIDLE;
				MARINESKIN[3] = MARINE_FRONTMOVE;
				MARINE_SIDEMOVE = MARINE_SIDEIDLE;
				MARINESKIN[4] = MARINE_SIDEMOVE;
				MARINE_BACKMOVE = MARINE_BACKIDLE;
				MARINESKIN[5] = MARINE_BACKMOVE;
				MARINE_FRONTHURT = MARINE_FRONTIDLE;
				MARINESKIN[6] = MARINE_FRONTHURT;
				MARINE_SIDEHURT = MARINE_SIDEIDLE;
				MARINESKIN[7] = MARINE_SIDEHURT;
				MARINE_BACKHURT = MARINE_BACKIDLE;
				MARINESKIN[8] = MARINE_BACKHURT;
				
				
				WSB_FRONTIDLE = getImageFromFolder("src/img/WSB_frontIdle.png");
				WSBSKIN[0] = WSB_FRONTIDLE;
				WSB_SIDEIDLE = getImageFromFolder("src/img/WSB_sideIdle.png");
				WSBSKIN[1] = WSB_SIDEIDLE;
				WSB_BACKIDLE = getImageFromFolder("src/img/WSB_backIdle.png");
				WSBSKIN[2] = WSB_BACKIDLE;
				WSB_FRONTMOVE = WSB_FRONTIDLE;
				WSBSKIN[3] = WSB_FRONTMOVE;
				WSB_SIDEMOVE = WSB_SIDEIDLE;
				WSBSKIN[4] = WSB_SIDEMOVE;
				WSB_BACKMOVE = WSB_BACKIDLE;
				WSBSKIN[5] = WSB_BACKMOVE;
				WSB_FRONTHURT = WSB_FRONTIDLE;
				WSBSKIN[6] = WSB_FRONTHURT;
				WSB_SIDEHURT = WSB_SIDEIDLE;
				WSBSKIN[7] = WSB_SIDEHURT;
				WSB_BACKHURT = WSB_BACKIDLE;
				WSBSKIN[8] = WSB_BACKHURT;
				
				NPC_FRONTIDLE = getImageFromFolder("src/img/NPC_frontIdle.png");
				NPCSKIN[0] = NPC_FRONTIDLE;
				NPC_SIDEIDLE = getImageFromFolder("src/img/NPC_sideIdle.png");
				NPCSKIN[1] = NPC_SIDEIDLE;
				NPC_BACKIDLE = getImageFromFolder("src/img/NPC_backIdle.png");
				NPCSKIN[2] = NPC_BACKIDLE;
				NPC_FRONTMOVE = NPC_FRONTIDLE;
				NPCSKIN[3] = NPC_FRONTMOVE;
				NPC_SIDEMOVE = NPC_SIDEIDLE;
				NPCSKIN[4] = NPC_SIDEMOVE;
				NPC_BACKMOVE = NPC_BACKIDLE;
				NPCSKIN[5] = NPC_BACKMOVE;
				NPC_FRONTHURT = NPC_FRONTIDLE;
				NPCSKIN[6] = NPC_FRONTHURT;
				NPC_SIDEHURT = NPC_SIDEIDLE;
				NPCSKIN[7] = NPC_SIDEHURT;
				NPC_BACKHURT = NPC_BACKIDLE;
				NPCSKIN[8] = NPC_BACKHURT;
				
				
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
	public static BufferedImage getImageFromJar(String filePath) throws IOException {
		System.out.println(filePath);
		if(ImageLoader.class.getResourceAsStream(filePath) == null) {
			System.err.println("Error, getClass is null");
		}
		return ImageIO.read((ImageLoader.class.getResourceAsStream(filePath)));
	}
}