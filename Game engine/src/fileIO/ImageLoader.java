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

	private boolean isJar;
	Thread t;

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
				NPC_BACKHURT = getImageFromJar("/img/NPC_backHurt.png");
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

	@Override
	public void run() {
		System.out.println("Running ImageLoad Thread");
		loadAllImages(isJar);
	}
	public void start(boolean isJar) {
		System.out.println("Starting ImageLoad Thread");
		this.isJar = isJar;
		if(t == null)
			t = new Thread(this, "ImageLoader");
		t.start();
	}
	public boolean isAlive() {
		return t.isAlive();
	}
}
