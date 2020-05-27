package gameObjects;

import java.awt.Image;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
/**
 * 
 * Created May 26, 2020
 * @author t4canty
 * @author TJ178
 * Class designed to represent all loot objects in the game, holding things like damage, sprites, types of projectiles, etc.
 *
 */
public class Gun extends Loot {
	//========Variables========//
	private int numBullet;
	
	//========Constructor========//
	/**
	 * Each loot object represents a gun in the game, with a different sprite and name to display in the Inventory.
	 * @param cooldown
	 * Cooldown time (ms) until gun can shoot again
	 * @param Damage
	 * Self explanatory.
	 * @param id
	 * The id of the gun - used to determine what kind of bullet to shoot in the projectile class.
	 * @param Name
	 * Name of the gun.
	 */
	public Gun(int Damage, int cooldown, int id, String Name, boolean IsJar) {
		this.Damage = Damage;
		this.cooldown = cooldown;
		this.id = id;
		this.Name = Name;
		if(IsJar)
			Sprite = getSpriteFromJar();
		else
			Sprite = getSpriteFromFolder();
	}
	
	//========Methods========//
	private Image getSpriteFromJar() {
		try {
			switch(id) {																	//Uses the id to read the sprite from the jar.
			case 0: //badGun
				return ImageIO.read(getClass().getResourceAsStream("img/badgun.png"));
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
		return null;
	}
	private Image getSpriteFromFolder() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		switch(id){																			//Uses id to read sprite from folder using Toolkit.
		case 0:
			return toolkit.getImage("img/badgun.png");
		case 1: //betterGun
			return toolkit.getImage("betterGun.png");
		case 2: //federalReserve
			return toolkit.getImage("federalReserve.png");
		case 3: //ElPresidente
			return toolkit.getImage("ElPresidente.png");
		case 4: //ToiletPaper
			return toolkit.getImage("ToiletPaper.png");
		}
		return null;
	}
}
