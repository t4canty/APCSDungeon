package gameObjects;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
		if(IsJar)
			Sprite = getSpriteFromJar();
		else
			Sprite = getSpriteFromFolder();
	}
	private Image getSpriteFromJar() {
<<<<<<< HEAD
		try {
			switch(id) {

			case 0: //badGun
				return ImageIO.read(getClass().getResourceAsStream("badgun.png"));
			case 1: //betterGun
				return ImageIO.read(getClass().getResourceAsStream("betterGun.png"));
			case 2: //federalReserve
				return ImageIO.read(getClass().getResourceAsStream("federalReserve.png"));
			case 3: //ElPresidente
				return ImageIO.read(getClass().getResourceAsStream("ElPresidente.png"));
			case 4: //ToiletPaper
				return ImageIO.read(getClass().getResourceAsStream("ToiletPaper.png"));
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
=======
		//TODO
>>>>>>> refs/remotes/origin/master
		return null;
	}
	private Image getSpriteFromFolder() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		switch(id) {
		case 0: //badGun
			return toolkit.getImage("badgun.png");
		case 1: //betterGun
<<<<<<< HEAD
			return toolkit.getImage("betterGun.png");
=======
			return toolkit.getImage("badgun.png");
>>>>>>> refs/remotes/origin/master
		case 2: //federalReserve
<<<<<<< HEAD
			return toolkit.getImage("federalReserve.png");
=======
			return toolkit.getImage("badgun.png");
>>>>>>> refs/remotes/origin/master
		case 3: //ElPresidente
<<<<<<< HEAD
			return toolkit.getImage("ElPresidente.png");
=======
			return toolkit.getImage("badgun.png");
>>>>>>> refs/remotes/origin/master
		case 4: //ToiletPaper
<<<<<<< HEAD
			return toolkit.getImage("ToiletPaper.png");
=======
			return toolkit.getImage("badgun.png");
>>>>>>> refs/remotes/origin/master
		}
<<<<<<< HEAD
		return null;
		//todo
=======
		//TODO
		return null;
>>>>>>> refs/remotes/origin/master
	}
	public int getId() {return id;}
	public Image getSprite() {return Sprite;}
	public String getName() {return Name;}
	public int getDamage() {return Damage;}
}
