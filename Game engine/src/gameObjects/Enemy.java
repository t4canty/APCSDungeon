package gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import displayComponents.AnimatedImage;
import fileIO.ImageLoader;

/**
 * Class for Enemy Objects, including fields such as health, item drops, and position - as well as necessary paint methods and bg methods.
 * Inherits health, position, hitbox, and sprites from GameObject parent.
 * <br> 
 * Created May 26, 2020
 * @author t4canty
 * @author TJ178
 *  
 */
public class Enemy extends GameObject{
	//========Variables========//
	private Loot drop;
	protected Gun activeGun;
	protected int movementSpeed = 4;
	private boolean isShooting = false;
	protected double gunAngle;
	private int graphicsDir;
	private long lastDamageTaken = 0;
	private long lastWalk = 0;
	protected AnimatedImage[] skin = new AnimatedImage[9];
	Random r = new Random();
	double r1 = Math.random() + 1;
	protected double sFactor;
	private Room cRoom;
	//========Constructor========//
	/**
	 * Enemy constructor with x and y inputs;
	 * @param x
	 * Starting X position for the enemy on the jframe
	 * @param y
	 * Starting Y position for the enemy on the jframe
	 * @param hp
	 * Amount of HP enemy starts with
	 * @param size
	 * Size of the player object
	 * @param skin
	 * Skin of sprites to use
	 */
	public Enemy(int x, int y, int hp, Dimension size, BufferedImage[] skin, boolean isJar, double ratio) {
		this.x = (int)(x * ratio);
		this.y = (int)(y * ratio);
		this.hp = (int) (hp * r1);
		this.rBox = new Rectangle((int)((size.width * r1) * ratio), (int)((size.height *r1) * ratio));
		this.isJar = isJar;
		sFactor = ratio;
		rBox.x = x;
		rBox.y = y;
		movementSpeed = (int)(movementSpeed * ratio);
		for(int i = 0; i < skin.length; i++) {
			this.skin[i] = new AnimatedImage(skin[i]);
		}
		computeDrop();
	}
	
	public Enemy(int x, int y, int hp, Dimension size, BufferedImage[] skin, boolean isJar, double ratio, Gun ActiveGun) {
	this(x, y, hp, size, skin, isJar, ratio);
	this.rBox = new Rectangle((int)(size.width * ratio), (int)(size.height * ratio));
	this.activeGun = ActiveGun;
	drop = activeGun;
	}

	//========Methods========//
	@Override
	public void paint(Graphics g) {
		rBox.x = x;
		rBox.y = y;

		Graphics2D g2d = (Graphics2D) g;
		if(debug) g2d.draw(rBox);

		if(Math.abs(gunAngle) > 2.35) {
			graphicsDir = LEFT;
		}else if(Math.abs(gunAngle) < .79) {
			graphicsDir = RIGHT;
		}else if(gunAngle > 0) {
			graphicsDir = DOWN;
		}else {
			graphicsDir = UP;
		}

		if(System.currentTimeMillis() - lastWalk < 75 && !(System.currentTimeMillis() - lastDamageTaken < 20)) {

			//moving sprites
			switch(graphicsDir) {
			case LEFT:
				g2d.drawImage(skin[SIDEMOVE].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
				break;
			case RIGHT:
				g2d.drawImage(skin[SIDEMOVE].getCurrentFrame(), x + rBox.width, y, -rBox.width, rBox.height, null);
				break;
			case UP:
				drawGun(g2d);
				g2d.drawImage(skin[BACKMOVE].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
				break;
			case DOWN:
				g2d.drawImage(skin[FRONTMOVE].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
			}

			///hurt sprites
		} else if(System.currentTimeMillis() - lastDamageTaken < 20){
			switch(graphicsDir) {
			case LEFT:
				g2d.drawImage(skin[SIDEHURT].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
				break;
			case RIGHT:
				g2d.drawImage(skin[SIDEHURT].getCurrentFrame(), x + rBox.width, y, -rBox.width, rBox.height, null);
				break;
			case UP:
				drawGun(g2d);
				g2d.drawImage(skin[BACKHURT].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
				break;
			case DOWN:
				g2d.drawImage(skin[FRONTHURT].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
			}

			//idle sprites
		} else {
			switch(graphicsDir) {
			case LEFT:
				g2d.drawImage(skin[SIDEIDLE].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
				break;
			case RIGHT:
				g2d.drawImage(skin[SIDEIDLE].getCurrentFrame(), x + rBox.width, y, -rBox.width, rBox.height, null);
				break;
			case UP:
				drawGun(g2d);
				g2d.drawImage(skin[BACKIDLE].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
				break;
			case DOWN:
				g2d.drawImage(skin[FRONTIDLE].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
			}
		}

		if(graphicsDir != UP) {
			drawGun(g2d);
		}

	}

	//draw gun on the screen dependent on the angle it's aiming
	protected void drawGun(Graphics2D g2d) {
		g2d.rotate(gunAngle, rBox.getCenterX(), rBox.getCenterY());
		if(Math.abs(gunAngle) > 1.07) {
			g2d.drawImage(activeGun.getSprite(3, hp), (int)(rBox.getCenterX()) + 10, (int)(rBox.getCenterY()) + 20, (int)((25*r1) * sFactor), (int)((-25 * r1) * sFactor), null);
		}else {
			g2d.drawImage(activeGun.getSprite(3, hp), (int)(rBox.getCenterX()) + 10, (int)(rBox.getCenterY()) - 20, (int)((25 * r1) * sFactor), (int)((25 * r1) * sFactor), null);
		}
		if(debug) g2d.drawLine((int)(rBox.getCenterX()), (int)(rBox.getCenterY()), (int)(rBox.getCenterX() + 100), (int)(rBox.getCenterY()));
		g2d.rotate(-gunAngle, rBox.getCenterX(), rBox.getCenterY());
	}

	//update all of the animations
	@Override
	public void advanceAnimationFrame() {
		for(AnimatedImage i : skin) {
			i.advanceCurrentFrame();
		}
	}

	//computes a random item to drop when the enemy is killed
	private void computeDrop() {
		int rand = new Random().nextInt(100);
		//int rand = 44;
		if(rand < 15) {															//BadGun
			drop = new Gun(10, 300, 5, 5, 15, Gun.BADGUN, 5, "Bad Gun", isJar, sFactor);
			activeGun = (Gun) drop;
		}else if(rand < 40) {//BetterGun
			drop = new Gun(20, 200, 15, 8, 15, Gun.BETTERGUN, 5, "Better Gun", isJar, sFactor);
			activeGun = (Gun) drop;
		}else if(rand < 45) {												//FederalReserve
			//drop = new Gun(10, 50, 30, 10, 30, Gun.FEDRESERVE, 5, "Federal Reserve", isJar, sFactor);
			drop = new Gun(20, 200, 15, 8, 15, Gun.BETTERGUN, 5, "Better Gun", isJar, sFactor);
			activeGun = (Gun) drop;
		}else if(rand < 55) {										//ElPresidente
			drop = new Gun(40, 600, 8, 15, 20, Gun.PRESIDENTE, 9, "El Presidente", isJar, sFactor);
			activeGun = (Gun) drop;
		}else if(rand < 60) {										//ToiletPaper
			drop = new Gun(100, 1000, 3, 7, 50, Gun.TP, 2, "Toilet Paper", isJar, sFactor);
			activeGun = (Gun) drop;
		}else if(rand < 75) {										//Health Item
			drop = new Health("Small Heath Potion", ImageLoader.NO_IMAGE);
			activeGun = new Gun(5, 700, 10, 10, 10, 0, 5, "Bad Gun", isJar, sFactor);											//TODO Fix later to include actual sprite
		}else {
			rand = new Random().nextInt(100);
			drop = new AmmoMag(10 + rand, ImageLoader.PISTOLMAG);
			activeGun = new Gun(5, 700, 10, 10, 10, 0, 5, "Bad Gun", isJar, sFactor);
		}
		
		if(debug) System.out.println("Random number in ComputeDrop():" + rand + " Drop:" + drop.getName());
	}

	//get a new projectile from the gun
	public Projectile getGunshot() {
		return activeGun.getGunshot(getCenterX(), getCenterY(), gunAngle, this);
	}

	//damage this enemy
	public void damage(int hp) {
		this.hp -= hp;
		lastDamageTaken = System.currentTimeMillis(); //keep track of when damage is taken to show hurt animation
	}

	/*
	 * Three different states for AI
	 * 1) running towards player until a certain range
	 * 2) running away from player when they're too close
	 * 3) shooting at player once within range // if they're too close
	 * 4) dodging bullets from player
	 * 
	 * Things AI needs to check for in step 1
	 * 1) make sure it doesn't run into walls or other enemies
	 * 		figuring out how to get around other things will be complicated but not impossible
	 * 		pathfinding will have to have something to do with squares an enemy can walk on, perhaps generate a path for each enemy for each frame? idk how long that would take
	 * 
	 * Things AI needs to check for in step 2
	 * 1) make sure that there's a clear line to the player
	 * 2) make sure that it shoots out the appropriate projectile for the gun it's holding
	 * 
	 * Things AI needs to check for in step 3
	 * 1) don't dodge bullets from other enemies
	 * 2) probably just move away from a certain radius of a projectile if one gets close
	 * 3) perhaps a sort of risk/reward system where small bullets are less likely to get it to run rather than fight
	 */

	public void runAI(Player player, Room room) {
		cRoom = room;
		if(activeGun.getAmmoInMag() == 0) activeGun.reload();

		//first determine which state the AI will operate within
		int currentState = 0;
		int distFromPlayer = (int)getDistanceFrom(player.getCenterX(), player.getCenterY());
		if(distFromPlayer > (250 * sFactor)) {
			currentState = 0;
		}else if(distFromPlayer < rBox.height * 2) {
			currentState = 1;
		}else if(room.isCloseToPlayerProjectile(getCenterX(), getCenterY())) {
			currentState = 3;
		}else {
			currentState = 2;
		}

		//check if this enemy is intersecting others, if so move away from the other enemies
		int eX = 0;
		int eY = 0;
		for(GameObject e : room.getEntities()) {
			if(e instanceof Enemy && e.isColliding(rBox) && !e.equals(this)) {
				currentState = 4;
				eX = e.getCenterX();
				eY = e.getCenterY();
				distFromPlayer = (int)getDistanceFrom(eX, eY);
			}
		}

		//act accordingly based on that state
		switch(currentState) {
		case 0:
			//move towards player
			x += movementSpeed * (player.getCenterX() - getCenterX()) / distFromPlayer;
			y += movementSpeed * (player.getCenterY() - getCenterY()) / distFromPlayer;
			lastWalk = System.currentTimeMillis();
			isShooting = false;
			break;
		case 1:
			//move away from player
			x -= movementSpeed * (player.getCenterX() - getCenterX()) / distFromPlayer;
			y -= movementSpeed * (player.getCenterY() - getCenterY()) / distFromPlayer;
			lastWalk = System.currentTimeMillis();
			isShooting = true;
			break;
		case 2:
			//shoot player
			isShooting = true;
			break;
		case 3:
			//TODO: run away from player gunshots
			break;
		case 4:
			//avoid intersecting other enemies
			x -= movementSpeed * (eX - getCenterX()) / distFromPlayer;
			y -= movementSpeed * (eY - getCenterY()) / distFromPlayer;
			lastWalk = System.currentTimeMillis();
		}

		//make sure to stay within room bounds
		if(y < room.topBound) y = room.topBound;
		if(y > room.bottomBound - rBox.height) y = room.bottomBound - rBox.height;
		if(x < room.leftBound) x = room.leftBound;
		if(x > room.rightBound - rBox.width) x = room.rightBound - rBox.width;;

		//update angle of held gun
		gunAngle = Math.atan2(player.getCenterY() - rBox.getCenterY(), player.getCenterX() - rBox.getCenterX());
	}
	
	//========Getters/Setters========//
	public boolean xRecoil(int recoil) {
		if(cRoom == null) return false;
		if(x - recoil < cRoom.leftBound || x + recoil > cRoom.rightBound) return false;
		return true;
	}
	public boolean yRecoil(int recoil) {
		if(cRoom == null) return false;
		if(y - recoil < cRoom.topBound || y + recoil  > cRoom.bottomBound) return false;
		return true;
	}
	
	
	public boolean isShooting() { return isShooting; }
//	public void stopSound() {if(activeGun.getSound().isActive()) activeGun.getSound().stop();}
	public Loot getDrop() { return drop; }
	public int getDir() { return graphicsDir; }
}
