package fileIO;

import java.awt.Image;
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

public class ImageLoader implements Runnable{
	//all stored images
	public static BufferedImage NO_IMAGE;
	public static BufferedImage MARINE_FRONTIDLE, MARINE_SIDEIDLE, MARINE_BACKIDLE, MARINE_FRONTMOVE, MARINE_SIDEMOVE, MARINE_BACKMOVE, MARINE_FRONTHURT, MARINE_SIDEHURT, MARINE_BACKHURT, MARINE_DEATH;
	public static BufferedImage[] MARINESKIN = new BufferedImage[10];
	public static BufferedImage  WSB_FRONTIDLE, WSB_SIDEIDLE, WSB_BACKIDLE, WSB_FRONTMOVE, WSB_SIDEMOVE, WSB_BACKMOVE, WSB_FRONTHURT, WSB_SIDEHURT, WSB_BACKHURT, WSB_DEATH;
	public static BufferedImage[] WSBSKIN = new BufferedImage[10];
	public static BufferedImage  NPC_FRONTIDLE, NPC_SIDEIDLE, NPC_BACKIDLE, NPC_FRONTMOVE, NPC_SIDEMOVE, NPC_BACKMOVE, NPC_FRONTHURT, NPC_SIDEHURT, NPC_BACKHURT, NPC_DEATH;
	public static BufferedImage[] NPCSKIN = new BufferedImage[10];
	public static BufferedImage BADGUN;
	public static BufferedImage PISTOLMAG;
	public static BufferedImage ROOM_1;
	public static BufferedImage BETTERGUN;
	public static BufferedImage ELPRESIDENTE;
	public static BufferedImage TOILETPAPER;
	public static BufferedImage FEDRESERVE;
	public static BufferedImage GOLDBULLET;
	public static BufferedImage BASICBULLET; 
	public static BufferedImage TPBULLET; 
	public static BufferedImage FEDBULLET; 
	public static BufferedImage LASERBEAM;
	public static BufferedImage YARIS;
	private boolean isJar;
	private static boolean debug;
	public static int totalNumberToLoad = 36;
	public static int totalNumberLoaded = 0;
	Thread t;
	
	//called at beginning of program, loads all images
	private static void loadAllImages(boolean isJar) {
		if(isJar) {
			if(debug) System.out.println("Loading images from Jar");
			try {
				NO_IMAGE = getImageFromJar("/img/noimage.png");
				
				MARINE_FRONTIDLE = getImageFromJar("/img/Marine_frontIdle.png");
				MARINESKIN[0] = MARINE_FRONTIDLE;
				MARINE_SIDEIDLE = getImageFromJar("/img/Marine_sideIdle.png");
				MARINESKIN[1] = MARINE_SIDEIDLE;
				MARINE_BACKIDLE = getImageFromJar("/img/Marine_backIdle.png");
				MARINESKIN[2] = MARINE_BACKIDLE;
				MARINE_FRONTMOVE = getImageFromJar("/img/Marine_frontMove.png");
				MARINESKIN[3] = MARINE_FRONTMOVE;
				MARINE_SIDEMOVE = getImageFromJar("/img/Marine_sideMove.png");
				MARINESKIN[4] = MARINE_SIDEMOVE;
				MARINE_BACKMOVE = getImageFromJar("/img/Marine_backMove.png");
				MARINESKIN[5] = MARINE_BACKMOVE;
				MARINE_FRONTHURT = getImageFromJar("/img/Marine_frontHurt.png");
				MARINESKIN[6] = MARINE_FRONTHURT;
				MARINE_SIDEHURT = getImageFromJar("/img/Marine_sideHurt.png");
				MARINESKIN[7] = MARINE_SIDEHURT;
				MARINE_BACKHURT = getImageFromJar("/img/Marine_backHurt.png");
				MARINESKIN[8] = MARINE_BACKHURT;
				MARINESKIN[9] = null;


				WSB_FRONTIDLE = getImageFromJar("/img/WSB_frontIdle.png");
				WSBSKIN[0] = WSB_FRONTIDLE;
				WSB_SIDEIDLE = getImageFromJar("/img/WSB_sideIdle.png");
				WSBSKIN[1] = WSB_SIDEIDLE;
				WSB_BACKIDLE = getImageFromJar("/img/WSB_backIdle.png");
				WSBSKIN[2] = WSB_BACKIDLE;
				WSB_FRONTMOVE = getImageFromJar("/img/WSB_frontMove.png");
				WSBSKIN[3] = WSB_FRONTMOVE;
				WSB_SIDEMOVE = getImageFromJar("/img/WSB_sideMove.png");
				WSBSKIN[4] = WSB_SIDEMOVE;
				WSB_BACKMOVE = getImageFromJar("/img/WSB_backMove.png");
				WSBSKIN[5] = WSB_BACKMOVE;
				WSB_FRONTHURT = getImageFromJar("/img/WSB_frontHurt.png");
				WSBSKIN[6] = WSB_FRONTHURT;
				WSB_SIDEHURT = getImageFromJar("/img/WSB_sideHurt.png");
				WSBSKIN[7] = WSB_SIDEHURT;
				WSB_BACKHURT = getImageFromJar("/img/WSB_backHurt.png");
				WSBSKIN[8] = WSB_BACKHURT;
				WSBSKIN[9] = null;

				NPC_FRONTIDLE = getImageFromJar("/img/NPC_frontIdle.png");
				NPCSKIN[0] = NPC_FRONTIDLE;
				NPC_SIDEIDLE = getImageFromJar("/img/NPC_sideIdle.png");
				NPCSKIN[1] = NPC_SIDEIDLE;
				NPC_BACKIDLE = getImageFromJar("/img/NPC_backIdle.png");
				NPCSKIN[2] = NPC_BACKIDLE;
				NPC_FRONTMOVE = getImageFromJar("/img/NPC_frontMove.png");
				NPCSKIN[3] = NPC_FRONTMOVE;
				NPC_SIDEMOVE = getImageFromJar("/img/NPC_sideMove.png");
				NPCSKIN[4] = NPC_SIDEMOVE;
				NPC_BACKMOVE = getImageFromJar("/img/NPC_backMove.png");
				NPCSKIN[5] = NPC_BACKMOVE;
				NPC_FRONTHURT = getImageFromJar("/img/NPC_frontHurt.png");
				NPCSKIN[6] = NPC_FRONTHURT;
				NPC_SIDEHURT = getImageFromJar("/img/NPC_sideHurt.png");;
				NPCSKIN[7] = NPC_SIDEHURT;
				NPC_BACKHURT = getImageFromJar("/img/NPC_BackHurt.png");
				NPCSKIN[8] = NPC_BACKHURT;
				NPCSKIN[9] = null;
				
				BADGUN = getImageFromJar("/img/badGun.png");
				FEDRESERVE = getImageFromJar("/img/badGun.png"); // Change later
				TOILETPAPER = getImageFromJar("/img/badGun.png"); // Change later
				BETTERGUN = getImageFromJar("/img/BetterGun.png");
				ELPRESIDENTE = getImageFromJar("/img/ElPresidente.png");
				LASERBEAM = getImageFromJar("/img/badGun.png");	//Change later
				YARIS = getImageFromJar("/img/yaris.png");
				PISTOLMAG = getImageFromJar("/img/pistolMag.png");
				GOLDBULLET = getImageFromJar("/img/goldBullet.png");
				BASICBULLET = getImageFromJar("/img/basicBullet.png");
				
				ROOM_1 = getImageFromJar("/img/BG.png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			if(debug) System.out.println("Loading images from Folder");
			try {
				NO_IMAGE = getImageFromFolder("src/img/noimage.png");

				MARINE_FRONTIDLE = getImageFromFolder("src/img/Marine_frontIdle.png");
				MARINESKIN[0] = MARINE_FRONTIDLE;
				MARINE_SIDEIDLE = getImageFromFolder("src/img/Marine_sideIdle.png");
				MARINESKIN[1] = MARINE_SIDEIDLE;
				MARINE_BACKIDLE = getImageFromFolder("src/img/Marine_backIdle.png");
				MARINESKIN[2] = MARINE_BACKIDLE;
				MARINE_FRONTMOVE = getImageFromFolder("src/img/Marine_frontMove.png");
				MARINESKIN[3] = MARINE_FRONTMOVE;
				MARINE_SIDEMOVE = getImageFromFolder("src/img/Marine_sideMove.png");
				MARINESKIN[4] = MARINE_SIDEMOVE;
				MARINE_BACKMOVE = getImageFromFolder("src/img/Marine_backMove.png");
				MARINESKIN[5] = MARINE_BACKMOVE;
				MARINE_FRONTHURT = getImageFromFolder("src/img/Marine_frontHurt.png");
				MARINESKIN[6] = MARINE_FRONTHURT;
				MARINE_SIDEHURT = getImageFromFolder("src/img/Marine_sideHurt.png");
				MARINESKIN[7] = MARINE_SIDEHURT;
				MARINE_BACKHURT = getImageFromFolder("src/img/Marine_backHurt.png");
				MARINESKIN[8] = MARINE_BACKHURT;
				MARINESKIN[9] = null;


				WSB_FRONTIDLE = getImageFromFolder("src/img/WSB_frontIdle.png");
				WSBSKIN[0] = WSB_FRONTIDLE;
				WSB_SIDEIDLE = getImageFromFolder("src/img/WSB_sideIdle.png");
				WSBSKIN[1] = WSB_SIDEIDLE;
				WSB_BACKIDLE = getImageFromFolder("src/img/WSB_backIdle.png");
				WSBSKIN[2] = WSB_BACKIDLE;
				WSB_FRONTMOVE = getImageFromFolder("src/img/WSB_frontMove.png");
				WSBSKIN[3] = WSB_FRONTMOVE;
				WSB_SIDEMOVE = getImageFromFolder("src/img/WSB_sideMove.png");
				WSBSKIN[4] = WSB_SIDEMOVE;
				WSB_BACKMOVE = getImageFromFolder("src/img/WSB_backMove.png");
				WSBSKIN[5] = WSB_BACKMOVE;
				WSB_FRONTHURT = getImageFromFolder("src/img/WSB_frontHurt.png");
				WSBSKIN[6] = WSB_FRONTHURT;
				WSB_SIDEHURT = getImageFromFolder("src/img/WSB_sideHurt.png");
				WSBSKIN[7] = WSB_SIDEHURT;
				WSB_BACKHURT = getImageFromFolder("src/img/WSB_backHurt.png");
				WSBSKIN[8] = WSB_BACKHURT;
				WSBSKIN[9] = null;

				NPC_FRONTIDLE = getImageFromFolder("src/img/NPC_frontIdle.png");
				NPCSKIN[0] = NPC_FRONTIDLE;
				NPC_SIDEIDLE = getImageFromFolder("src/img/NPC_sideIdle.png");
				NPCSKIN[1] = NPC_SIDEIDLE;
				NPC_BACKIDLE = getImageFromFolder("src/img/NPC_backIdle.png");
				NPCSKIN[2] = NPC_BACKIDLE;
				NPC_FRONTMOVE = getImageFromFolder("src/img/NPC_frontMove.png");
				NPCSKIN[3] = NPC_FRONTMOVE;
				NPC_SIDEMOVE = getImageFromFolder("src/img/NPC_sideMove.png");
				NPCSKIN[4] = NPC_SIDEMOVE;
				NPC_BACKMOVE = getImageFromFolder("src/img/NPC_backMove.png");
				NPCSKIN[5] = NPC_BACKMOVE;
				NPC_FRONTHURT = getImageFromFolder("src/img/NPC_frontHurt.png");
				NPCSKIN[6] = NPC_FRONTHURT;
				NPC_SIDEHURT = getImageFromFolder("src/img/NPC_sideHurt.png");;
				NPCSKIN[7] = NPC_SIDEHURT;
				NPC_BACKHURT = getImageFromFolder("src/img/NPC_backHurt.png");
				NPCSKIN[8] = NPC_BACKHURT;
				NPCSKIN[9] = null;


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
		totalNumberLoaded++;
		if(debug) System.out.println(new File(filePath).getAbsolutePath());
		BufferedImage temp = ImageIO.read(new File(filePath));
		if(temp == null)
			throw new IOException();
		return temp;
	}
	//get Image from jar
	public static BufferedImage getImageFromJar(String filePath) throws IOException {
		totalNumberLoaded++;
		if(debug)System.out.println(filePath);
		if(ImageLoader.class.getResourceAsStream(filePath) == null) {
			System.err.println("Error, getClass is null");
		}
		return ImageIO.read((ImageLoader.class.getResourceAsStream(filePath)));
	}
	
	//multithread stuff
	@Override
	public void run() {
		if(debug) System.out.println("Running ImageLoad Thread");
		loadAllImages(isJar);
	}
	
	public void start(boolean isJar, boolean debug) {
		this.debug = debug;
		if(debug) System.out.println("Starting ImageLoad Thread");
		this.isJar = isJar;
		if(t == null)
			t = new Thread(this, "ImageLoader");
		t.start();
	}
	public boolean isAlive() {
		return t.isAlive();
	}
}
