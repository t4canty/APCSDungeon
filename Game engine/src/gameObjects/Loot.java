package gameObjects;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

public class Loot {
	private int Damage;
	private int id;
	private Image Sprite;
	private String Name;
	/**
	 * Each loot object represents a gun in the game, with a different sprite and name to display in the Inventory.
	 * @param Damage
	 * Self explanatory.
	 * @param id
	 * The id of the gun - used to determine what kind of bullet to shoot in the projectile class.
	 * @param Name
	 * Name of the gun.
	 */
	public Loot(int Damage, int id, String Name, boolean IsJar) {
		this.Damage = Damage;
		this.id = id;
		this.Name = Name;
	}
	private Image getSpriteFromJar() {
		
	}
	private Image getSpriteFromFolder() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		switch(id) {
		case 0: //badGun
			toolkit.getImage("badgun.png");
			break;
		case 1: //betterGun
			toolkit.getImage("badgun.png");
		case 2: //federalReserve
			toolkit.getImage("badgun.png");
		case 3: //ElPresidente
			toolkit.getImage("badgun.png");
		case 4: //ToiletPaper
			toolkit.getImage("badgun.png");
		}
		//todo
	}
	public int getId() {return id;}
	public Image getSprite() {return Sprite;}
	public String getName() {return Name;}
	public int getDamage() {return Damage;}
}
