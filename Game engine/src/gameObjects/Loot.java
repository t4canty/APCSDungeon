package gameObjects;

import java.awt.Image;
import java.awt.image.BufferedImage;
/**
 * Superclass for all game objects that don't really exist, like potions and guns. Superclassed
 * so that enemies can drop either a potion or gun based off a random varible.
 * Created May 26, 2020
 * @author t4canty
 * @author TJ178
 *
 */

public abstract class Loot {
	//========Variables========//
	protected int id;		//type number
	protected BufferedImage Sprite[];
	protected String Name;
	protected int number;	//number of hp, ammo, etc
	protected int cooldown;
	
	public static int HEALTHPACK = 0;
	public static int PISTOLMAG = 1;
	public static int GUN = 2;
	
	public abstract void use(Player p);
	
	//========Getters/Setters========//
	public int getId() {return id;}
	public BufferedImage getSprite(int n) {
		if(n > Sprite.length)
			throw new IndexOutOfBoundsException();
		else {
			return Sprite[n];
		}}
	public String getName() {return Name;}
	public int getNum() {return number;}
	public int getCooldown() {return cooldown;}
}
