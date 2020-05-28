package gameObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * Created May 26, 2020
 * @author t4canty
 * @author TJ178
 *
 */
public class Player extends GameObject{
	//========Variables========//
	private Gun activeGun;																		//Currently held gun.
	private int minX = 0;
	private int minY = 0;
	private int maxX;																			
	private int maxY;
	private double gunAngle;
	public boolean isShooting;
	private long lastBulletShot = 0; 															//system time when last bullet was shot, used for cooldown
	ArrayList<Gun> inventory = new ArrayList<Gun>();											//List of guns currently in the player's inventory
	
	//========Constructors========//
	/**
	 * Player constructor with x and y inputs;
	 * @param x
	 * Starting X position for the player on the jframe
	 * @param y
	 * Starting Y position for the player on the jframe
	 * @param size
	 * Size of the player object
	 * @param Sprite1
	 * Full path to the idle sprite
	 * @param Sprite2
	 * Full path to the move sprite
	 * @param Sprite3
	 * Full path to the hurt sprite
	 * @param Sprite
	 * Full path to Attack Sprite
	 * 
	 */
	public Player(int x, int y, Dimension size, String Sprite1, String Sprite2, String Sprite3, String Sprite4) throws IOException {
		this.x = x;
		this.y = y;
		this.hp = 100;
		this.rBox = new Rectangle(size);
		if(isJar)
			getImagesFromJar(Sprite1, Sprite2, Sprite3, Sprite4);
		else
			getImagesFromFolder(Sprite1, Sprite2, Sprite3, Sprite4);
		activeGun = new Gun(100, 300, 0, "Bad Gun", super.isJar);									//Starting gun
		inventory.add(activeGun);
	}
	/**
	 * Player constructor
	 * @param x
	 * Starting X position for the player on the jframe
	 * @param y
	 * Starting Y position for the player on the jframe
	 * @param size
	 * Size of the player object
	 * @param x
	 * Starting X position for the player on the jframe
	 * @param y
	 * Starting Y position for the player on the jframe
	 * @param size
	 * Size of the player object
	 * @param Sprite1
	 * Full path to the idle sprite
	 * @param Sprite2
	 * Full path to the move sprite
	 * @param Sprite3
	 * Full path to the hurt sprite
	 * @param Sprite4 full path to Attack Sprite
	 */
	public Player(Dimension size, String Sprite1, String Sprite2, String Sprite3, String Sprite4) throws IOException {
		this.x = 0;
		this.y = 0;
		this.hp = 100;
		this.rBox = new Rectangle(size);
		if(isJar)
			getImagesFromJar(Sprite1, Sprite2, Sprite3, Sprite4);
		else
			getImagesFromFolder(Sprite1, Sprite2, Sprite3, Sprite4);
		activeGun = new Gun(100, 300, 0, "Bad Gun", super.isJar);
		inventory.add(activeGun);
	}
		/**
		 * 
	 * Player constructor with x and y inputs;
	 * @param x
	 * Starting X position for the player on the jframe
	 * @param y
	 * Starting Y position for the player on the jframe
	 * @param size
	 * Size of the player object
	 * @param Sprite1
	 * Full path to the idle sprite
	 * @param Sprite2
	 * Full path to the move sprite
	 * @param Sprite3
	 * Full path to the hurt sprite
	 * @param Sprite
	 * Full path to Attack Sprite
	 * @param debug
	 * Sets debug flag
	 * 
	 */
	public Player(int x, int y, Dimension size, String Sprite1, String Sprite2, String Sprite3, String Sprite4, boolean debug) throws IOException {
		this(x, y, size, Sprite1, Sprite2, Sprite3, Sprite4);
		this.debug = debug;
	}
	/**
	 * Player constructor
	 * @param x
	 * Starting X position for the player on the jframe
	 * @param y
	 * Starting Y position for the player on the jframe
	 * @param size
	 * Size of the player object
	 * @param x
	 * Starting X position for the player on the jframe
	 * @param y
	 * Starting Y position for the player on the jframe
	 * @param size
	 * Size of the player object
	 * @param Sprite1
	 * Full path to the idle sprite
	 * @param Sprite2
	 * Full path to the move sprite
	 * @param Sprite3
	 * Full path to the hurt sprite
	 * @param Sprite4 full path to Attack Sprite
	 * @param debug
	 * Sets debug flag
	 */
	public Player(Dimension size, String Sprite1, String Sprite2, String Sprite3, String Sprite4, boolean debug) throws IOException {
		this(size, Sprite1, Sprite2, Sprite3, Sprite4);
		this.debug = debug;
	}
	
	//========Methods========//
	@Override
	public void paint(Graphics g) {
		rBox.x = x;																					//set hitbox to curremnt y
		rBox.y = y;																					//Set hitbox to current x
		Graphics2D g2d = (Graphics2D) g; 															//neccessary for drawing gifs
		g2d.setColor(Color.BLACK);
		g2d.drawImage(idleSprite, x, y, null);
		if(debug) g2d.draw(rBox);
		g2d.rotate(gunAngle, rBox.getCenterX(), rBox.getCenterY());
		g2d.drawImage(activeGun.getSprite(), (int)(rBox.getCenterX()) + 10, (int)(rBox.getCenterY()) - 10, null);
		if(debug) g2d.drawLine((int)(rBox.getCenterX()), (int)(rBox.getCenterY()), (int)(rBox.getCenterX() + 100), (int)(rBox.getCenterY()));
	}
	/**
	 * Method to determine if cooldown is over
	 * @return
	 * returns true or false if enough time has passed. 
	 */
	public boolean canShootBullet() {
		//if(debug) System.out.println("Current time to next shot:" + (System.currentTimeMillis() - lastBulletShot) + " IsShooting:" + isShooting);
		if(System.currentTimeMillis() - lastBulletShot > activeGun.getCooldown()) {
			lastBulletShot = System.currentTimeMillis();
			if(debug) System.out.println("True: Current time is now:" + lastBulletShot);
			if(debug) System.out.println("Current Cooldown Time:" + activeGun.getCooldown());
			return true;
		}
		return false;
	}
	/**
	 * Movement method.
	 * @param dir
	 * Determines the direction of the movement.
	 */
	public void move(int dir) {
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
		//TODO
	}
	
	
	/**
	 * Return projectile entity shot from gun
	 */
	//TODO: make this responsive to different types of guns
	public Projectile getNewBullet() {
		return new Projectile(activeGun.getDamage(), false, getCenterX(), getCenterY(), 20, gunAngle, new Dimension(25, 25), null, 0);
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
	
	//========Getters/Setters========//
	public void add(Gun l) {inventory.add(l);}
	public Gun get(int i) {return inventory.get(i);}
	public ArrayList<Gun> getInventory(){return inventory;}
	public double getGunAngle() { return gunAngle; }
	public Gun getActiveGun() { return activeGun; }
	public void setActiveGun(Gun g) {activeGun = g; }
}
