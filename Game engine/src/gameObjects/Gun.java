package gameObjects;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.imageio.ImageIO;

import displayComponents.AnimatedImage;
import displayComponents.SoundEffect;
import fileIO.ImageLoader;
import fileIO.SoundLoader;
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
	private AnimatedImage bulletSprite;
	private SoundEffect shootSound;
	private SoundEffect reloadSound;
	private long lastShot = 0;
	private int damage;
	private int bulletVelocity = 10;
	private int bulletSize = 10;
	private int ammoInMag = 10;
	private int maxAmmoInMag = 10;
	private boolean isJar;
	
	public final int BADGUN = 0;
	public final int BETTERGUN = 1;
	public final int FEDRESERVE = 2;
	public final int PRESIDENTE = 3;
	public final int TP = 4;
				
	
	
	//========Constructor========//
	/**
	 * Each loot object represents a gun in the game, with a different sprite and name to display in the Inventory.
	 * @param Damage
	 * Self explanatory.
	 * @param cooldown
	 * Cooldown time (ms) until gun can shoot again
	 * @param maxAmmoInMag
	 * Magazine size
	 * @param bulletVelocity
	 * Speed of bullets shot
	 * @param bulletSize
	 * Size of bullets shot
	 * @param id
	 * The id of the gun - used to determine what kind of bullet to shoot in the projectile class.
	 * @param Name
	 * Name of the gun.
	 */
	public Gun(int Damage, int cooldown, int maxAmmoInMag, int bulletVelocity, int bulletSize, int id, String Name, boolean IsJar) {
		damage = Damage;
		this.cooldown = cooldown;
		this.id = id;
		this.Name = Name;
		this.isJar = IsJar;
		this.maxAmmoInMag = maxAmmoInMag;
		this.ammoInMag = maxAmmoInMag;
		this.bulletVelocity = bulletVelocity;
		this.bulletSize = bulletSize;
		Sprite = ImageLoader.BADGUN;
		getSounds();
		reloadSound = SoundLoader.SMALLRELOAD;
		bulletSprite = null;
	}
	
	//========Methods========//
	private Image getSpriteFromJar() {
		try {
			switch(id) {																	//Uses the id to read the sprite from the jar.
			case 0: //badGun
				return ImageIO.read(getClass().getResourceAsStream("/img/badgun.png"));
			case 1: //betterGun
				return ImageIO.read(getClass().getResourceAsStream("/img/badgun.png"));
			case 2: //federalReserve
				return ImageIO.read(getClass().getResourceAsStream("/img/badgun.png"));
			case 3: //ElPresidente
				return ImageIO.read(getClass().getResourceAsStream("/img/badgun.png"));
			case 4: //ToiletPaper
				return ImageIO.read(getClass().getResourceAsStream("/img/badgun.png"));
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	//Fix this later
	private Image getSpriteFromFolder() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		switch(id){																			//Uses id to read sprite from folder using Toolkit.
		case 0:
			//return toolkit.getImage("src/img/badgun.png");
		case 1: //betterGun
			//return toolkit.getImage("betterGun.png");
		case 2: //federalReserve
			//return toolkit.getImage("federalReserve.png");
		case 3: //ElPresidente
			//return toolkit.getImage("ElPresidente.png");
		case 4: //ToiletPaper
			//return toolkit.getImage("ToiletPaper.png");
		}
		//return null;
		return toolkit.getImage("src/img/badgun.png");
	}
	
	private void getSounds() {
		switch(id) {
		case 0:
			shootSound = SoundLoader.PISTOL_GUNSHOT;
			break;
		case 1:
			shootSound = SoundLoader.AR15_GUNSHOT;
			break;
		case 2:
			shootSound = SoundLoader.MONEYSHOOTER;
			break;
		case 3:
			shootSound = SoundLoader.DEAGLE_GUNSHOT;
			break;
		case 4:
			shootSound = SoundLoader.LAUNCHER_GUNSHOT;
		}
	}
	
	//returns true if the gun is ready to fire
	public boolean canShoot() {
		return System.currentTimeMillis() - lastShot > cooldown && ammoInMag > 0;
	}
	
	//returns a Projectile from the gun if the gun is able to fire- otherwise returns null
	public Projectile getGunshot(int x, int y, double angle, boolean isEnemy) {
		if(canShoot()) {
			lastShot = System.currentTimeMillis();
			ammoInMag--;
			shootSound.play();
			return new Projectile(damage, isEnemy, x, y, bulletVelocity, angle, new Dimension(bulletSize, bulletSize), ImageLoader.BULLET, id, isJar );
		}
		
		return null;
	}
	
	//reloads ammo given a stash to take from- returns the leftover amount of ammo
	public int reload(int totalAmmo) {
		int ammoNeeded = maxAmmoInMag - ammoInMag;
		int temp = totalAmmo;
		if(totalAmmo < ammoNeeded) {
			ammoInMag += totalAmmo;
			totalAmmo = 0;
		}else {
			totalAmmo -= ammoNeeded;
			ammoInMag = maxAmmoInMag;
		}
		if(temp != totalAmmo) {
			reloadSound.play();
		}
		
		return totalAmmo;
	}
	
	@Override
	public Image getSprite() {
		if(id == 2 && System.currentTimeMillis() - lastShot > cooldown) {
			shootSound.stop();
		}
		return Sprite;
	}
	
	//reloads the gun with magic ammo that appears from nowhere
	public void reload() {
		ammoInMag = maxAmmoInMag;
	}
	
	// getters / setters
	public int getAmmoInMag() { return ammoInMag; }
	public int getMaxAmmoInMag() { return maxAmmoInMag; }

	@Override
	public void use(Player p) {
		boolean[] bArr = p.getOwnedGuns();
		if(bArr[id] == false) {
			if(p.getId() == Player.WSB && id == FEDRESERVE) {
				cooldown = 5;
				bulletVelocity += 20;
				damage += 10;
			}
			p.getInventory().add(this);
			p.ownGun(true, id);
		}else {
			p.addAmmo(ammoInMag);
		}
		
	}
}
