package apcs.apcsdungeon.gameobjects;

import java.awt.image.BufferedImage;

/**
 * Superclass for all game objects that don't really exist, like potions and guns. Superclassed
 * so that enemies can drop either a potion or gun based off a random varible.
 * Created May 26, 2020
 *
 * @author t4canty
 * @author TJ178
 */
public abstract class Loot {
	public final static int YARISGUN = -2;
	public final static int LASERBEAM = -1;
	public final static int BADGUN = 0;
	public final static int BETTERGUN = 1;
	public final static int FEDRESERVE = 2;
	public final static int PRESIDENTE = 3;
	public final static int TP = 4;
	public static int HEALTHPACK = 5;
	public static int PISTOLMAG = 1;
	public static int GUN = 2;
	//========Variables========//
	protected int id;        //id of loot.
	protected BufferedImage[] Sprite;
	protected String Name;
	protected int number;    //number of hp, ammo, etc
	protected int cooldown;

	/**
	 * Action preformed when the player interacts with the object.
	 *
	 * @param p Player object.
	 */
	public abstract void use(Player p);

	//========Getters/Setters========//
	public int getId() {
		return id;
	}

	public BufferedImage getSprite(int n) {
		if (n > Sprite.length)
			throw new IndexOutOfBoundsException();
		else {
			return Sprite[n];
		}
	}

	public abstract BufferedImage getSprite(int n, int hp);

	public String getName() {
		return Name;
	}

	public int getNum() {
		return number;
	}

	public int getCooldown() {
		return cooldown;
	}
}
