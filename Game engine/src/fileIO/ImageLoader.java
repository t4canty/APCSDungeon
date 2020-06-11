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

public class ImageLoader implements Runnable{
	//========Static Variables========//
	public static BufferedImage NO_IMAGE;
	public static BufferedImage MARINE_STARTUP, WSB_STARTUP, SECRET_STARTUP, NPC_STARTUP;
	public static BufferedImage MARINE_FRONTIDLE, MARINE_SIDEIDLE, MARINE_BACKIDLE, MARINE_FRONTMOVE, MARINE_SIDEMOVE, MARINE_BACKMOVE, MARINE_FRONTHURT, MARINE_SIDEHURT, MARINE_BACKHURT, MARINE_DEATH;
	public static BufferedImage[] MARINESKIN = new BufferedImage[10];
	public static BufferedImage  WSB_FRONTIDLE, WSB_SIDEIDLE, WSB_BACKIDLE, WSB_FRONTMOVE, WSB_SIDEMOVE, WSB_BACKMOVE, WSB_FRONTHURT, WSB_SIDEHURT, WSB_BACKHURT, WSB_DEATH;
	public static BufferedImage[] WSBSKIN = new BufferedImage[10];
	public static BufferedImage  NPC_FRONTIDLE, NPC_SIDEIDLE, NPC_BACKIDLE, NPC_FRONTMOVE, NPC_SIDEMOVE, NPC_BACKMOVE, NPC_FRONTHURT, NPC_SIDEHURT, NPC_BACKHURT, NPC_DEATH;
	public static BufferedImage[] NPCSKIN = new BufferedImage[10];
	public static BufferedImage  SECRET_FRONTIDLE, SECRET_SIDEIDLE, SECRET_BACKIDLE, SECRET_FRONTMOVE, SECRET_SIDEMOVE, SECRET_BACKMOVE, SECRET_FRONTHURT, SECRET_SIDEHURT, SECRET_BACKHURT, SECRET_DEATH;
	public static BufferedImage[] SECRETSKIN = new BufferedImage[10];
	public static BufferedImage BADGUN[] = new BufferedImage[4];
	public static BufferedImage PISTOLMAG;
	public static BufferedImage ROOMS[] = new BufferedImage[6];
	public static BufferedImage BOSSROOM;
	public static BufferedImage BETTERGUN[] = new BufferedImage[4];
	public static BufferedImage ELPRESIDENTE[] = new BufferedImage[4];
	public static BufferedImage TOILETPAPER[] = new BufferedImage[4];
	public static BufferedImage FEDRESERVE[] = new BufferedImage[4];
	public static BufferedImage GOLDBULLET;
	public static BufferedImage BASICBULLET; 
	public static BufferedImage TPBULLET; 
	public static BufferedImage FEDBULLET;
	public static BufferedImage LASERBEAM[] = new BufferedImage[4];
	public static BufferedImage YARIS;
	public static BufferedImage CHEST;
	
	//========Variables========//
	public static int totalNumberToLoad = 78;
	public static int totalNumberLoaded = 0;
	private static boolean debug;
	private boolean isJar;	
	private Thread t;
	
	//========Methods========//
	//called at beginning of program, loads all images
	private static void loadAllImages(boolean isJar) {
		if(isJar) {
			if(debug) System.out.println("Loading images from Jar");
			try {
				NO_IMAGE = getImageFromJar("/img/noimage.png");
				CHEST = getImageFromJar("/img/chest.png");

				MARINE_STARTUP = getImageFromJar("/img/spritesheets/Marine/Marine_frontIdle_hands.png");
				WSB_STARTUP = getImageFromJar("/img/spritesheets/WSB/WSB_frontIdle_hands.png");
				NPC_STARTUP = getImageFromJar("/img/spritesheets/NPC/NPC_frontIdle_hands.png");
				SECRET_STARTUP = getImageFromJar("/img/spritesheets/Secret/Secret_frontIdle_hands.png");

				MARINE_FRONTIDLE = getImageFromJar("/img/spritesheets/Marine/Marine_frontIdle.png");
				MARINESKIN[0] = MARINE_FRONTIDLE;
				MARINE_SIDEIDLE = getImageFromJar("/img/spritesheets/Marine/Marine_sideIdle.png");
				MARINESKIN[1] = MARINE_SIDEIDLE;
				MARINE_BACKIDLE = getImageFromJar("/img/spritesheets/Marine/Marine_backIdle.png");
				MARINESKIN[2] = MARINE_BACKIDLE;
				MARINE_FRONTMOVE = getImageFromJar("/img/spritesheets/Marine/Marine_frontMove.png");
				MARINESKIN[3] = MARINE_FRONTMOVE;
				MARINE_SIDEMOVE = getImageFromJar("/img/spritesheets/Marine/Marine_sideMove.png");
				MARINESKIN[4] = MARINE_SIDEMOVE;
				MARINE_BACKMOVE = getImageFromJar("/img/spritesheets/Marine/Marine_backMove.png");
				MARINESKIN[5] = MARINE_BACKMOVE;
				MARINE_FRONTHURT = getImageFromJar("/img/spritesheets/Marine/Marine_frontHurt.png");
				MARINESKIN[6] = MARINE_FRONTHURT;
				MARINE_SIDEHURT = getImageFromJar("/img/spritesheets/Marine/Marine_sideHurt.png");
				MARINESKIN[7] = MARINE_SIDEHURT;
				MARINE_BACKHURT = getImageFromJar("/img/spritesheets/Marine/Marine_backHurt.png");
				MARINESKIN[8] = MARINE_BACKHURT;
				MARINESKIN[9] = null;


				WSB_FRONTIDLE = getImageFromJar("/img/spritesheets/WSB/WSB_frontIdle.png");
				WSBSKIN[0] = WSB_FRONTIDLE;
				WSB_SIDEIDLE = getImageFromJar("/img/spritesheets/WSB/WSB_sideIdle.png");
				WSBSKIN[1] = WSB_SIDEIDLE;
				WSB_BACKIDLE = getImageFromJar("/img/spritesheets/WSB/WSB_backIdle.png");
				WSBSKIN[2] = WSB_BACKIDLE;
				WSB_FRONTMOVE = getImageFromJar("/img/spritesheets/WSB/WSB_frontMove.png");
				WSBSKIN[3] = WSB_FRONTMOVE;
				WSB_SIDEMOVE = getImageFromJar("/img/spritesheets/WSB/WSB_sideMove.png");
				WSBSKIN[4] = WSB_SIDEMOVE;
				WSB_BACKMOVE = getImageFromJar("/img/spritesheets/WSB/WSB_backMove.png");
				WSBSKIN[5] = WSB_BACKMOVE;
				WSB_FRONTHURT = getImageFromJar("/img/spritesheets/WSB/WSB_frontHurt.png");
				WSBSKIN[6] = WSB_FRONTHURT;
				WSB_SIDEHURT = getImageFromJar("/img/spritesheets/WSB/WSB_sideHurt.png");
				WSBSKIN[7] = WSB_SIDEHURT;
				WSB_BACKHURT = getImageFromJar("/img/spritesheets/WSB/WSB_backHurt.png");
				WSBSKIN[8] = WSB_BACKHURT;
				WSBSKIN[9] = null;

				NPC_FRONTIDLE = getImageFromJar("/img/spritesheets/NPC/NPC_frontIdle.png");
				NPCSKIN[0] = NPC_FRONTIDLE;
				NPC_SIDEIDLE = getImageFromJar("/img/spritesheets/NPC/NPC_sideIdle.png");
				NPCSKIN[1] = NPC_SIDEIDLE;
				NPC_BACKIDLE = getImageFromJar("/img/spritesheets/NPC/NPC_backIdle.png");
				NPCSKIN[2] = NPC_BACKIDLE;
				NPC_FRONTMOVE = getImageFromJar("/img/spritesheets/NPC/NPC_frontMove.png");
				NPCSKIN[3] = NPC_FRONTMOVE;
				NPC_SIDEMOVE = getImageFromJar("/img/spritesheets/NPC/NPC_sideMove.png");
				NPCSKIN[4] = NPC_SIDEMOVE;
				NPC_BACKMOVE = getImageFromJar("/img/spritesheets/NPC/NPC_backMove.png");
				NPCSKIN[5] = NPC_BACKMOVE;
				NPC_FRONTHURT = getImageFromJar("/img/spritesheets/NPC/NPC_frontHurt.png");
				NPCSKIN[6] = NPC_FRONTHURT;
				NPC_SIDEHURT = getImageFromJar("/img/spritesheets/NPC/NPC_sideHurt.png");;
				NPCSKIN[7] = NPC_SIDEHURT;
				NPC_BACKHURT = getImageFromJar("/img/spritesheets/NPC/NPC_BackHurt.png");
				NPCSKIN[8] = NPC_BACKHURT;
				NPCSKIN[9] = null;
				
				SECRET_FRONTIDLE = getImageFromJar("/img/spritesheets/Secret/Secret_frontIdle.png");
				SECRETSKIN[0] = SECRET_FRONTIDLE;
				SECRET_SIDEIDLE = getImageFromJar("/img/spritesheets/Secret/Secret_sideIdle.png");
				SECRETSKIN[1] = SECRET_SIDEIDLE;
				SECRET_BACKIDLE = getImageFromJar("/img/spritesheets/Secret/Secret_backIdle.png");
				SECRETSKIN[2] = SECRET_BACKIDLE;
				SECRET_FRONTMOVE = getImageFromJar("/img/spritesheets/Secret/Secret_frontMove.png");
				SECRETSKIN[3] = SECRET_FRONTMOVE;
				SECRET_SIDEMOVE = getImageFromJar("/img/spritesheets/Secret/Secret_sideMove.png");
				SECRETSKIN[4] = SECRET_SIDEMOVE;
				SECRET_BACKMOVE = getImageFromJar("/img/spritesheets/Secret/Secret_backMove.png");
				SECRETSKIN[5] = SECRET_BACKMOVE;
				SECRET_FRONTHURT = getImageFromJar("/img/spritesheets/Secret/Secret_frontHurt.png");
				SECRETSKIN[6] = SECRET_FRONTHURT;
				SECRET_SIDEHURT = getImageFromJar("/img/spritesheets/Secret/Secret_sideHurt.png");;
				SECRETSKIN[7] = SECRET_SIDEHURT;
				SECRET_BACKHURT = getImageFromJar("/img/spritesheets/Secret/Secret_BackHurt.png");
				SECRETSKIN[8] = SECRET_BACKHURT;
				SECRETSKIN[9] = null;

				BADGUN[0] = getImageFromJar("/img/guns/badGun.png");		//Gun sprites with no hand
				FEDRESERVE[0] = getImageFromJar("/img/guns/badGun.png"); // Change later
				TOILETPAPER[0] = getImageFromJar("/img/guns/badGun.png"); // Change later
				BETTERGUN[0] = getImageFromJar("/img/guns/BetterGun.png");
				ELPRESIDENTE[0] = getImageFromJar("/img/guns/ElPresidente.png");
				LASERBEAM[0] = getImageFromJar("/img/guns/badGun.png");	//Change later


				BADGUN[1] = getImageFromJar("/img/guns/badGun_marineHand.png");		//Gun sprites with no hand
				FEDRESERVE[1] = getImageFromJar("/img/guns/badGun_marineHand.png"); // Change later
				TOILETPAPER[1] = getImageFromJar("/img/guns/badGun_marineHand.png"); // Change later
				BETTERGUN[1] = getImageFromJar("/img/guns/BetterGun_marineHand.png");
				ELPRESIDENTE[1] = getImageFromJar("/img/guns/ElPresidente_marineHand.png");
				LASERBEAM[1] = getImageFromJar("/img/guns/badGun_marineHand.png");	//Change later

				BADGUN[2] = getImageFromJar("/img/guns/badGun_wsb.png");		//Gun sprites with no hand
				FEDRESERVE[2] = getImageFromJar("/img/guns/badGun_wsb.png"); // Change later
				TOILETPAPER[2] = getImageFromJar("/img/guns/badGun_wsb.png"); // Change later
				BETTERGUN[2] = getImageFromJar("/img/guns/BetterGun_wsb.png");
				ELPRESIDENTE[2] = getImageFromJar("/img/guns/ElPresidente_wsb.png");
				LASERBEAM[2] = getImageFromJar("/img/guns/badGun_wsb.png");	//Change later

				BADGUN[3] = getImageFromJar("/img/guns/badGun_npc.png");		//Gun sprites with no hand
				FEDRESERVE[3] = getImageFromJar("/img/guns/badGun_npc.png"); // Change later
				TOILETPAPER[3] = getImageFromJar("/img/guns/badGun_npc.png"); // Change later
				BETTERGUN[3] = getImageFromJar("/img/guns/BetterGun_npc.png");
				ELPRESIDENTE[3] = getImageFromJar("/img/guns/ElPresidente_npc.png");
				LASERBEAM[3] = getImageFromJar("/img/guns/badGun_npc.png");	//Change later

				YARIS = getImageFromJar("/img/guns/yaris.png");
				PISTOLMAG = getImageFromJar("/img/guns/pistolMag.png");
				GOLDBULLET = getImageFromJar("/img/guns/goldBullet.png");
				BASICBULLET = getImageFromJar("/img/guns/basicBullet.png");

				ROOMS[0] = getImageFromJar("/img/rooms/room1.png");
				ROOMS[1] = getImageFromJar("/img/rooms/room2.png");
				ROOMS[2] = getImageFromJar("/img/rooms/room3.png");
				ROOMS[3] = getImageFromJar("/img/rooms/room4.png");
				ROOMS[4] = getImageFromJar("/img/rooms/room5.png");
				ROOMS[5] = getImageFromJar("/img/rooms/room6.png");
				BOSSROOM = getImageFromJar("/img/rooms/bossroom.png");
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
	
	//========Getter========//
	public boolean isAlive() {return t.isAlive();}
}
