package gameObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.Clip;

import displayComponents.AnimatedImage;
import displayComponents.SoundEffect;
import fileIO.ImageLoader;
import fileIO.SoundLoader;

/**
 * 
 * Created May 26, 2020
 * @author t4canty
 * @author TJ178
 *
 */
public class Player extends GameObject{
	//Finals
	final public static int MARINE = 0;
	final public static int WSB = 1;
	final public static int SECRET = 2;
	
	//========Variables========//
	private Gun activeGun;																		//Currently held gun.
	private int minX = 0;	//bounds of room
	private int minY = 0;
	private int maxX;													
	private int maxY;
	private int graphicsDir;		//direction that a player is holding their gun
	private AnimatedImage[] skin = new AnimatedImage[10];
	private SoundEffect footsteps = SoundLoader.FOOTSTEP;
	private long lastWalk = 0; 		//last time player moved - used for idle vs moving animation
	private long lastDamageTaken = 0;//last time the player took damage - used for hurt animation
	private double gunAngle;
	public boolean isShooting;
	private boolean isAlive = true;
	private int ammo = 20;
	ArrayList<Gun> inventory = new ArrayList<Gun>();											//List of guns currently in the player's inventory
	private boolean[] CollectedGuns = { false, false, false, false, false};
	private int id;
	
	
	
	
	//========Constructors========//
	/**
	 * Player constructor with x and y inputs;
	 * @param x
	 * Starting X position for the player on the jframe
	 * @param y
	 * Starting Y position for the player on the jframe
	 * @param size
	 * Size of the player object
	 * @param skin
	 * Player skin to use
	 * 
	 */
	public Player(int x, int y, Dimension size, int pid, boolean isJar) throws IOException {
		this.x = x;
		this.y = y;
		this.hp = 100;
		this.rBox = new Rectangle(size);
		this.isJar = isJar;
		this.id = pid;
		getPlayerSkin(pid);
		activeGun = new Gun(10, 300, 10, 10, 10, 0, "Bad Gun", super.isJar);
		inventory.add(activeGun);
	}
	/**
	 * Bare Player constructor
	 * @param size
	 * Size of the hitbox for the player
	 * @param skin
	 * Player skin to use
	 */
	public Player(Dimension size, int pid, boolean isJar) throws IOException {
		this(0, 0, size, pid, isJar);
	}
		/**
		 * 
	 * Player constructor with x and y inputs, debug option
	 * @param x
	 * Starting X position for the player on the jframe
	 * @param y
	 * Starting Y position for the player on the jframe
	 * @param size
	 * Size of the player object
	 * @param pid
	 * Id of the char to use
	 * @param debug
	 * Sets debug flag
	 * 
	 */
	public Player(int x, int y, Dimension size, int pid, boolean isJar, boolean debug) throws IOException {
		this(x, y, size, pid, isJar);
		this.debug = debug;
		if(debug)
			inventory.add(new Gun(9999, 50, 99999, 10, 10, 0, "EZ Death Lazer", super.isJar));
	}
	/**
	 * Player constructor with debug option
	 * @param size
	 * Size of the player object
	 * @param skin
	 * player skin to use
	 * @param debug
	 * Sets debug flag
	 */
	public Player(Dimension size,int pid, boolean isJar, boolean debug) throws IOException {
		this(size, pid, isJar);
		this.debug = debug;
		if(debug)
			inventory.add(new Gun(9999, 50, 99999, 10, 10, 0, "EZ Death Lazer", super.isJar));
	}
	
	//========Methods========//
	@Override
	public void paint(Graphics g) {
		rBox.x = x;																					//set hitbox to curremnt y
		rBox.y = y;																					//Set hitbox to current x
		Graphics2D g2d = (Graphics2D) g; 															//neccessary for drawing gifs
		g2d.setColor(Color.BLACK);
		
		if(Math.abs(gunAngle) > 2.35) {
			graphicsDir = LEFT;
		}else if(Math.abs(gunAngle) < .79) {
			graphicsDir = RIGHT;
		}else if(gunAngle > 0) {
			graphicsDir = DOWN;
		}else {
			graphicsDir = UP;
		}
		
		
		if(isAlive) {
			if(System.currentTimeMillis() - lastWalk < 75 && !(System.currentTimeMillis() - lastDamageTaken < 50)) {
				
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
				footsteps.loop();
				//System.out.println("moving");
				///hurt sprites
			} else if(System.currentTimeMillis() - lastDamageTaken < 500){
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
				//System.out.println("hurt");
				
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
				footsteps.stop();
				//System.out.println("idle");
			}
		}else {
			g2d.drawImage(skin[DEATH].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
		}
		
		if(graphicsDir != UP) {
			drawGun(g2d);
		}
		
		if(debug) g2d.draw(rBox);
		
	}
	
	
	//Used to draw the gun onto the screen
	private void drawGun(Graphics2D g2d) {
		g2d.rotate(gunAngle, rBox.getCenterX(), rBox.getCenterY());
		if(Math.abs(gunAngle) > 1.07) {
			g2d.drawImage(activeGun.getSprite(), (int)(rBox.getCenterX()) + 13, (int)(rBox.getCenterY()) + 20, 50, -50, null);
		}else {
			g2d.drawImage(activeGun.getSprite(), (int)(rBox.getCenterX()) + 13, (int)(rBox.getCenterY()) - 20, 50, 50, null);
		}
		
		//if(debug) g2d.drawLine((int)(rBox.getCenterX()), (int)(rBox.getCenterY()), (int)(rBox.getCenterX() + 100), (int)(rBox.getCenterY()));
		g2d.rotate(-gunAngle, rBox.getCenterX(), rBox.getCenterY());
	}
	
	
	@Override
	public void advanceAnimationFrame() {
		for(AnimatedImage i : skin) {
			if(i != null && !i.isStatic()) {
				i.advanceCurrentFrame();
			}
		}
		
	}
	
	/**
	 * Method to determine if cooldown is over
	 * @return
	 * returns true or false if enough time has passed. 
	 */
	public boolean canShootBullet() {
		return activeGun.canShoot();
	}
	/**
	 * Movement method.
	 * @param dir
	 * Determines the direction of the movement.
	 */
	public void move(int dir) {
		lastWalk = System.currentTimeMillis();
		switch(dir) {
		case UP:
			y -= 10;
			break;
		case RIGHT:
			x += 10;
			break;
		case DOWN:
			y += 10;
			break;
		case LEFT:
			x -= 10;
			break;
		case UPRIGHT:
			y -= 5;
			x += 5;
			break;
		case UPLEFT:
			y -= 5;
			x -= 5;
			break;
		case DOWNRIGHT:
			y += 5;
			x += 5;
			break;
		case DOWNLEFT:
			y += 5;
			x -= 5;
			break;
			
		}
		if(y < minY) y = minY;																			//Collision on the bounds of the room
		if(y > maxY) y = maxY;
		if(x < minX) x = minX;
		if(x > maxX) x = maxX;
	}
	/**
	 * Teleport player to given x/y coordinates. 
	 * @param x
	 * @param y
	 */
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Update method to determine the angle of the gun
	 * @param mouseX
	 * @param mouseY
	 */
	public void updateGunAngle(int mouseX, int mouseY) {
		gunAngle = Math.atan2(mouseY - rBox.getCenterY(), mouseX - rBox.getCenterX());
	}
	/**
	 * Method to check all currently colliding entities.
	 * @param entities
	 */
	public void checkCollision(ArrayList<GameObject> entities) {
		for(int i = 0; i < entities.size(); i++) {
			if(entities.get(i).getHitbox().intersects(rBox)) {
				if(entities.get(i) instanceof Projectile) {
					if(((Projectile) entities.get(i)).isEnemyFire()) {
						if(isAlive) hp -= ((Projectile) entities.get(i)).getDamage();
						lastDamageTaken = System.currentTimeMillis();
						entities.remove(i);
						i--;
					}
				}else if(entities.get(i) instanceof DroppedItem) {
					((DroppedItem)entities.get(i)).getItem().use(this);
					entities.remove(i);
					i--;
				}
			}
		}
		
		
		if(hp <= 0) {
			isAlive = false;
		}
	}
	
	
	/**
	 * Return projectile entity shot from gun
	 */
	//TODO: make this responsive to different types of guns
	public Projectile getNewBullet() {
		return activeGun.getGunshot(getCenterX(), getCenterY(), gunAngle, false);
	}
	
	public void reload() {
		ammo = activeGun.reload(ammo);
	}
	
	
	/**
	 * Sets collision bounds for the JFrame.
	 * @param bounds
	 * Bounds read from a room object. 
	 */
	public void updateBounds(int[] bounds) {
		minY = bounds[0];
		maxX = bounds[1] - rBox.width;
		maxY = bounds[2] - rBox.height;
		minX = bounds[3];
	}
	private void getPlayerSkin(int id) {
		switch (id) {
		case MARINE:
			for(int i = 0; i < skin.length; i++) {
				this.skin[i] = new AnimatedImage(ImageLoader.MARINESKIN[i]);
			}
			hp += hp/2;
			break;
		case WSB:
			for(int i = 0; i < skin.length; i++) {
				this.skin[i] = new AnimatedImage(ImageLoader.WSBSKIN[i]);
			}
			break;
		case SECRET:
			for(int i = 0; i < skin.length; i++) {
				//this.skin[i] = new AnimatedImage(ImageLoader.SECRETSKIN[i]);
				this.skin[i] = new AnimatedImage(ImageLoader.MARINESKIN[i]);
			}
		}
	}

	//========Getters/Setters========//
	public void add(Gun l) {inventory.add(l);}
	public Gun get(int i) {return inventory.get(i);}
	public ArrayList<Gun> getInventory(){return inventory;}
	public double getGunAngle() { return gunAngle; }
	public Gun getActiveGun() { return activeGun; }
	public void setActiveGun(Gun g) {activeGun = g; }
	public int getTotalAmmo() { return ammo; }
	public int getAmmoInMag() { return activeGun.getAmmoInMag(); }
	public void addAmmo(int amt) { ammo += amt; }
	public boolean[] getOwnedGuns() { return CollectedGuns; }
	public void ownGun(boolean b, int n ) { CollectedGuns[n] = b; }
	public int getId() { return id; }
	public boolean isAlive() { return isAlive;	}
}
