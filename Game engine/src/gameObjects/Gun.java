package gameObjects;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

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
	//========Final Variables========//


	//========Variables========//
	private BufferedImage bulletSprite;
	private SoundEffect shootSound;
	private SoundEffect reloadSound;
	private long lastShot = 0;
	private int damage;
	private int bulletVelocity = 10;
	private int bulletSize = 10;
	private int ammoInMag = 10;
	private int maxAmmoInMag = 10;
	private int recoil = 5;
	private boolean isJar;

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
	public Gun(int Damage, int cooldown, int maxAmmoInMag, int bulletVelocity, int bulletSize, int id, int recoil, String Name, boolean IsJar, double ratio) {
		damage = Damage;
		this.cooldown = cooldown;
		this.id = id;
		this.Name = Name;
		this.isJar = IsJar;
		this.maxAmmoInMag = maxAmmoInMag;
		this.ammoInMag = maxAmmoInMag;
		this.bulletVelocity = (int) (bulletVelocity * ratio);
		this.bulletSize = (int) (bulletSize * ratio);
		this.recoil = recoil;
		Sprite = getSpriteFromId();
		getSounds();

		reloadSound = SoundLoader.SMALLRELOAD;
	}

	//========Methods========//
	private BufferedImage[] getSpriteFromId() {
		try {
			switch(id) {																//Uses the id to read the sprite from the jar.
			case -2:
				bulletSprite = ImageLoader.YARIS;
				return ImageLoader.BADGUN;
			case -1:
				bulletSprite = ImageLoader.BASICBULLET;
				return ImageLoader.LASERBEAM;
			case 0: //badGun
				bulletSprite = ImageLoader.BASICBULLET;
				return ImageLoader.BADGUN;
			case 1: //betterGun
				bulletSprite = ImageLoader.BASICBULLET;
				return ImageLoader.BETTERGUN;
			case 2: //federalReserve
				bulletSprite = ImageLoader.BASICBULLET;
				return ImageLoader.FEDRESERVE;
			case 3: //ElPresidente
				bulletSprite = ImageLoader.GOLDBULLET;
				return ImageLoader.ELPRESIDENTE;
			case 4: //ToiletPaper
				bulletSprite = ImageLoader.BASICBULLET;
				return ImageLoader.TOILETPAPER;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	private void getSounds() {
		switch(id) {
		case -2:
			shootSound = SoundLoader.B;
			break;
		case -1:
			shootSound = SoundLoader.LASERBEAM;
			break;
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
	public Projectile getGunshot(int x, int y, double angle, GameObject origin) {
		boolean isEnemy = origin instanceof Enemy;
		if(canShoot()) {
			lastShot = System.currentTimeMillis();
			int dir;
			boolean yRecoil, xRecoil;
			if(origin instanceof Enemy) {  
				dir = ((Enemy) origin).getDir();
				yRecoil = ((Enemy) origin).yRecoil(recoil);
				xRecoil = ((Enemy) origin).xRecoil(recoil);
			}
			else {
				dir = ((Player)origin).getDir();
				yRecoil = ((Player) origin).yRecoil(recoil);
				xRecoil = ((Player) origin).xRecoil(recoil);
			}

			switch(dir) {
			case GameObject.UP:
				if(yRecoil)origin.setY(origin.getY()+recoil);
				break;
			case GameObject.DOWN:
				if(yRecoil)origin.setY(origin.getY()-recoil);
				break;
			case GameObject.LEFT:
				if(xRecoil)origin.setX(origin.getX()+recoil);
				break;
			case GameObject.RIGHT:
				if(xRecoil)origin.setX(origin.getX()-recoil);
				break;
			case GameObject.DOWNLEFT:
				if(yRecoil)origin.setY(origin.getY()-recoil/2);
				if(xRecoil)origin.setX(origin.getX()+recoil/2);
				break;
			case GameObject.DOWNRIGHT:
				if(yRecoil)origin.setY(origin.getY()-recoil/2);
				if(xRecoil)origin.setX(origin.getX()+recoil/2);
				break;
			case GameObject.UPLEFT:
				if(yRecoil)origin.setY(origin.getY()+recoil/2);
				if(xRecoil)origin.setX(origin.getX()+recoil/2);
				break;
			case GameObject.UPRIGHT:
				if(yRecoil)origin.setY(origin.getY()+recoil/2);
				if(xRecoil)origin.setX(origin.getX()-recoil/2);
				break;
			}
			ammoInMag--;
			if(id == FEDRESERVE || id == LASERBEAM) {
				if(!shootSound.isActive()) shootSound.play();
			}else {
				shootSound.play();
			}
			return new Projectile(damage, isEnemy, x, y, bulletVelocity, angle, new Dimension(bulletSize, bulletSize), bulletSprite, id, isJar );
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
	public BufferedImage getSprite(int n, int hp) {
		if(lastShot == 0) lastShot = System.currentTimeMillis();
		if( (id == 2 || id == -1) && ( ( (System.currentTimeMillis() - lastShot)  >= cooldown ) || (hp == 0) )  && shootSound.isActive() ) {
			shootSound.stop();
		}
		return Sprite[n];
	}

	//reloads the gun with magic ammo that appears from nowhere
	public void reload() {
		if(ammoInMag != maxAmmoInMag) {
			ammoInMag = maxAmmoInMag;
			reloadSound.play();
		}
	}

	@Override
	public void use(Player p) {
		boolean[] bArr = p.getOwnedGuns();
		if(bArr[id] == false) {
			if(p.getId() == Player.WSB) {
				cooldown /= 2; 
				bulletVelocity += 5;
			}
			p.getInventory().add(this);
			p.ownGun(true, id);
		}else {
			p.addAmmo(ammoInMag);
		}
	}

	//========Getters/Setters========//
	public int getAmmoInMag() { return ammoInMag; }
	public int getMaxAmmoInMag() { return maxAmmoInMag; }
	public SoundEffect getSound() { return shootSound;}
}
