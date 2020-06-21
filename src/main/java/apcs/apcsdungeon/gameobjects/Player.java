package apcs.apcsdungeon.gameobjects;

import apcs.apcsdungeon.displaycomponents.AnimatedImage;
import apcs.apcsdungeon.displaycomponents.SoundEffect;
import apcs.apcsdungeon.fileio.ImageLoader;
import apcs.apcsdungeon.fileio.SoundLoader;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Created May 26, 2020
 *
 * @author t4canty
 * @author TJ178
 */
public class Player extends GameObject {
	//========Final Variables========//
	final public static int MARINE = 0;
	final public static int WSB = 1;
	final public static int SECRET = 2;
	public boolean isShooting;
	ArrayList<Gun> inventory = new ArrayList<>(); // List of guns currently in the player's inventory
	//========Variables========//
	private Gun activeGun; // Currently held gun.
	private int minX = 0; // bounds of room
	private int minY = 0;
	private int maxX;
	private int maxY;
	private int graphicsDir; // direction that a player is holding their gun
	private int velocity = 10;
	private AnimatedImage[] skin = new AnimatedImage[9];
	private AnimatedImage[] death = new AnimatedImage[3];
	private SoundEffect footsteps = SoundLoader.FOOTSTEP;
	private long lastWalk = 0; // last time player moved - used for idle vs moving animation
	private long lastDamageTaken = 0;//last time the player took damage - used for hurt animation
	private double gunAngle;
	private double scaleFactor;
	private boolean isAlive = true;
	private int ammo = 20;
	private boolean[] CollectedGuns = {true, false, false, false, false};
	private int id;

	//========Constructors========//

	/**
	 * Player constructor with x and y inputs;
	 *
	 * @param x    Starting X position for the player on the JFrame
	 * @param y    Starting Y position for the player on the JFrame
	 * @param size Size of the player object
	 */
	public Player(int x, int y, Dimension size, int pid, boolean isJar, double ratio) {
		this.x = x;
		this.y = y;
		this.hp = 100;
		this.rBox = new Rectangle(size);
		this.isJar = isJar;
		this.id = pid;
		scaleFactor = ratio;
		getPlayerSkin(pid);
		activeGun = new Gun(10, 300, 10, 10, 10, 0, 5, "Bad Gun", super.isJar, scaleFactor);
		inventory.add(activeGun);
		if (pid == SECRET) velocity = 15;
		velocity = (int) (velocity * scaleFactor);
	}

	/**
	 * Bare Player constructor
	 *
	 * @param size Size of the hitbox for the player
	 */
	public Player(Dimension size, int pid, boolean isJar, double ratio) {
		this(0, 0, size, pid, isJar, ratio);
	}

	/**
	 * Player constructor with x and y inputs, debug option
	 *
	 * @param x     Starting X position for the player on the jframe
	 * @param y     Starting Y position for the player on the jframe
	 * @param size  Size of the player object
	 * @param pid   Id of the char to use
	 * @param debug Sets debug flag
	 */
	public Player(int x, int y, Dimension size, int pid, boolean isJar, double ratio, boolean debug) {
		this(x, y, size, pid, isJar, ratio);
		this.debug = debug;
		if (debug) {
			inventory.add(new Gun(10, 50, 99999, 10, 10, -1, 0, "EZ Death Lazer", super.isJar, scaleFactor));
			inventory.add(new Gun(10000, 250, 1, 3, 256, -2, 40, "Yaris", super.isJar, scaleFactor));
		}
		if (debug) {
			hp = 9999;
		}
	}

	/**
	 * Player constructor with debug option
	 *
	 * @param size  Size of the player object
	 * @param debug Sets debug flag
	 */
	public Player(Dimension size, int pid, boolean isJar, double ratio, boolean debug) {
		this(size, pid, isJar, ratio);
		this.debug = debug;
		if (debug)
			inventory.add(new Gun(9999, 50, 99999, 10, 10, 0, 0, "EZ Death Lazer", super.isJar, scaleFactor));
	}

	//========Methods========//
	@Override
	public void paint(Graphics g) {
		rBox.x = x; // set hitbox to curremnt y
		rBox.y = y; // Set hitbox to current x
		Graphics2D g2d = (Graphics2D) g; // neccessary for drawing gifs
		g2d.setColor(Color.BLACK);

		if (Math.abs(gunAngle) > 2.35) {
			graphicsDir = LEFT;
		} else if (Math.abs(gunAngle) < .79) {
			graphicsDir = RIGHT;
		} else if (gunAngle > 0) {
			graphicsDir = DOWN;
		} else {
			graphicsDir = UP;
		}

		if (isAlive) {
			if (System.currentTimeMillis() - lastWalk < 75 && !(System.currentTimeMillis() - lastDamageTaken < 50)) {

				//moving sprites
				switch (graphicsDir) {
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
			} else if (System.currentTimeMillis() - lastDamageTaken < 500) {
				switch (graphicsDir) {
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
				switch (graphicsDir) {
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
		} else {
			switch (graphicsDir) {
				case LEFT:
					g2d.drawImage(death[SIDEDEATH].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
					break;
				case RIGHT:
					g2d.drawImage(death[SIDEDEATH].getCurrentFrame(), x + rBox.width, y, -rBox.width, rBox.height, null);
					break;
				case UP:
					g2d.drawImage(death[BACKDEATH].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
					break;
				case DOWN:
					g2d.drawImage(death[FRONTDEATH].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
			}
		}

		if (graphicsDir != UP) {
			drawGun(g2d);
		}

		if (debug) g2d.draw(rBox);

	}


	//Used to draw the gun onto the screen
	private void drawGun(Graphics2D g2d) {
		if (isAlive) {
			g2d.rotate(gunAngle, rBox.getCenterX(), rBox.getCenterY());
			if (Math.abs(gunAngle) > 1.07) {
				g2d.drawImage(activeGun.getSprite(id + 1, hp), (int) (rBox.getCenterX()) + 13, (int) (rBox.getCenterY()) + 20, (int) (50 * scaleFactor), (int) (-50 * scaleFactor), null);
			} else {
				g2d.drawImage(activeGun.getSprite(id + 1, hp), (int) (rBox.getCenterX()) + 13, (int) (rBox.getCenterY()) - 20, (int) (50 * scaleFactor), (int) (50 * scaleFactor), null);
			}

			//if(debug) g2d.drawLine((int)(rBox.getCenterX()), (int)(rBox.getCenterY()), (int)(rBox.getCenterX() + 100), (int)(rBox.getCenterY()));
			g2d.rotate(-gunAngle, rBox.getCenterX(), rBox.getCenterY());
		}

	}


	@Override
	public void advanceAnimationFrame() {
		for (AnimatedImage i : skin) {
			if (i != null && !i.isStatic()) {
				i.advanceCurrentFrame();
			}
		}

	}

	public void advanceDeathAnimationFrame() {
		for (AnimatedImage i : death) {
			i.advanceCurrentFrame();
		}
	}

	/**
	 * Method to determine if cooldown is over
	 *
	 * @return returns true or false if enough time has passed.
	 */
	public boolean canShootBullet() {
		return activeGun.canShoot();
	}

	/**
	 * Movement method.
	 *
	 * @param dir Determines the direction of the movement.
	 */
	public void move(int dir) {
		lastWalk = System.currentTimeMillis();
		switch (dir) {
			case UP:
				y -= velocity;
				break;
			case RIGHT:
				x += velocity;
				break;
			case DOWN:
				y += velocity;
				break;
			case LEFT:
				x -= velocity;
				break;
			case UPRIGHT:
				y -= velocity / 2;
				x += velocity / 2;
				break;
			case UPLEFT:
				y -= velocity / 2;
				x -= velocity / 2;
				break;
			case DOWNRIGHT:
				y += velocity / 2;
				x += velocity / 2;
				break;
			case DOWNLEFT:
				y += velocity / 2;
				x -= velocity / 2;
				break;

		}
		if (y < minY)
			y = minY; // Collision on the bounds of the room
		if (y > maxY) y = maxY;
		if (x < minX) x = minX;
		if (x > maxX) x = maxX;
	}

	/**
	 * Teleport player to given x/y coordinates.
	 *
	 * @param x
	 * @param y
	 */
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Update method to determine the angle of the gun
	 *
	 * @param mouseX
	 * @param mouseY
	 */
	public void updateGunAngle(int mouseX, int mouseY) {
		gunAngle = Math.atan2(mouseY - rBox.getCenterY(), mouseX - rBox.getCenterX());
	}

	/**
	 * Method to check all currently colliding entities.
	 *
	 * @param entities
	 */
	public void checkCollision(ArrayList<GameObject> entities) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getHitbox().intersects(rBox)) {
				if (entities.get(i) instanceof Projectile) {
					if (((Projectile) entities.get(i)).isEnemyFire()) {
						if (isAlive) hp -= ((Projectile) entities.get(i)).getDamage();
						lastDamageTaken = System.currentTimeMillis();
						entities.remove(i);
						i--;
					}
				} else if (entities.get(i) instanceof DroppedItem) {
					((DroppedItem) entities.get(i)).getItem().use(this);
					entities.remove(i);
					i--;
				}
			}
		}


		if (hp <= 0) {
			isAlive = false;
		}
	}


	/**
	 * Return projectile entity shot from gun
	 */
	//TODO: make this responsive to different types of guns
	public Projectile getNewBullet() {
		return activeGun.getGunshot(getCenterX(), getCenterY(), gunAngle, this);
	}

	public void reload() {
		if (activeGun.id == 0)
			activeGun.reload();
		else
			ammo = activeGun.reload(ammo);
	}


	/**
	 * Sets collision bounds for the JFrame.
	 *
	 * @param bounds Bounds read from a room object.
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
				for (int i = 0; i < skin.length; i++) {
					this.skin[i] = new AnimatedImage(ImageLoader.MARINESKIN[i]);
				}
				this.death[BACKDEATH] = new AnimatedImage(ImageLoader.MARINE_BACKDEATH, true);
				this.death[SIDEDEATH] = new AnimatedImage(ImageLoader.MARINE_SIDEDEATH, true);
				this.death[FRONTDEATH] = new AnimatedImage(ImageLoader.MARINE_FRONTDEATH, true);
				hp += hp / 2;
				break;
			case WSB:
				for (int i = 0; i < skin.length; i++) {
					this.skin[i] = new AnimatedImage(ImageLoader.WSBSKIN[i]);
				}
				this.death[BACKDEATH] = new AnimatedImage(ImageLoader.WSB_BACKDEATH, true);
				this.death[SIDEDEATH] = new AnimatedImage(ImageLoader.WSB_SIDEDEATH, true);
				this.death[FRONTDEATH] = new AnimatedImage(ImageLoader.WSB_FRONTDEATH, true);
				break;
			case SECRET:
				for (int i = 0; i < skin.length; i++) {
					this.skin[i] = new AnimatedImage(ImageLoader.SECRETSKIN[i]);
				}
				this.death[BACKDEATH] = new AnimatedImage(ImageLoader.SECRET_BACKDEATH, true);
				this.death[SIDEDEATH] = new AnimatedImage(ImageLoader.SECRET_SIDEDEATH, true);
				this.death[FRONTDEATH] = new AnimatedImage(ImageLoader.SECRET_FRONTDEATH, true);
		}
	}

	//========Getters/Setters========//
	public boolean xRecoil(int recoil) {
		return x - recoil >= minX && x + recoil <= maxX;
	}

	public boolean yRecoil(int recoil) {
		return y - recoil >= minY && y + recoil <= maxY;
	}

	public void add(Gun l) {
		inventory.add(l);
	}

	public Gun get(int i) {
		return inventory.get(i);
	}

	public ArrayList<Gun> getInventory() {
		return inventory;
	}

	public double getGunAngle() {
		return gunAngle;
	}

	public Gun getActiveGun() {
		return activeGun;
	}

	public void setActiveGun(Gun g) {
		activeGun = g;
	}

	public int getTotalAmmo() {
		return ammo;
	}

	public int getAmmoInMag() {
		return activeGun.getAmmoInMag();
	}

	public void addAmmo(int amt) {
		ammo += amt;
	}

	public boolean[] getOwnedGuns() {
		return CollectedGuns;
	}

	public void ownGun(boolean b, int n) {
		CollectedGuns[n] = b;
	}

	public int getId() {
		return id;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public int getDir() {
		return graphicsDir;
	}

}
