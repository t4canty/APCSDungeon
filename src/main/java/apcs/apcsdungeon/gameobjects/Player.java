package apcs.apcsdungeon.gameobjects;

import apcs.apcsdungeon.displaycomponents.SoundEffect;
import apcs.apcsdungeon.io.ImageLoader;
import apcs.apcsdungeon.io.SoundLoader;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created May 26, 2020
 *
 * @author t4canty
 * @author TJ178
 */
public class Player extends GameObject {
	private static final Logger logger = LoggerFactory.getLogger(Player.class);

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
	private SoundEffect footsteps = SoundLoader.FOOTSTEP;
	private long lastWalk = 0; // last time player moved - used for idle vs moving animation
	private long lastDamageTaken = 0; // last time the player took damage - used for hurt animation
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
		initializeTexture();
		this.x = x;
		this.y = y;
		this.hp = 100;
		this.rBox = new Rectangle(size);
		this.isJar = isJar;
		this.id = pid;
		scaleFactor = ratio;
		getPlayerSkin(pid);
		activeGun = new Gun(10, 300, 10, 10, 10, 0, 5,
				"Bad Gun", super.isJar, scaleFactor);
		inventory.add(activeGun);
		if (pid == SECRET) velocity = 15;
		velocity = (int) (velocity * scaleFactor);

		if (logger.isDebugEnabled()) {
			inventory.add(new Gun(10, 50, 99999, 10, 10, -1,
					0, "EZ Death Lazer", super.isJar, scaleFactor));
			inventory.add(new Gun(10000, 250, 1, 3, 256, -2,
					40, "Yaris", super.isJar, scaleFactor));
			hp = 9999;
		}
	}

	/**
	 * Bare Player constructor
	 *
	 * @param size Size of the hitbox for the player
	 */
	public Player(Dimension size, int pid, boolean isJar, double ratio) {
		this(0, 0, size, pid, isJar, ratio);
		if (logger.isDebugEnabled()) {
			inventory.add(new Gun(9999, 50, 99999, 10, 10, 0,
					0, "EZ Death Lazer", super.isJar, scaleFactor));
		}
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
				drawSprite(g2d, SIDE_MOVE, BACK_MOVE, FRONT_MOVE);
				footsteps.loop();
			} else if (System.currentTimeMillis() - lastDamageTaken < 500) {
				drawSprite(g2d, SIDE_HURT, BACK_HURT, FRONT_HURT);
			} else {
				drawSprite(g2d, SIDE_IDLE, BACK_IDLE, FRONT_IDLE);
				footsteps.stop();
			}
		} else {
			drawSprite(g2d, SIDE_DEATH, BACK_DEATH, FRONT_DEATH);
		}

		if (graphicsDir != UP) {
			drawGun(g2d);
		}

		if (logger.isDebugEnabled()) g2d.draw(rBox);
	}

	private void drawSprite(Graphics2D g2d, String sideAnimation, String backAnimation, String frontAnimation) {
		boolean invertHorizontal = false;
		switch (graphicsDir) {
			case LEFT:
				texture.changeAnimation(sideAnimation);
				break;
			case RIGHT:
				texture.changeAnimation(sideAnimation);
				invertHorizontal = true;
				break;
			case UP:
				drawGun(g2d);
				texture.changeAnimation(backAnimation);
				break;
			case DOWN:
				texture.changeAnimation(frontAnimation);
		}

		int xx = invertHorizontal ? x + rBox.width : x;
		int width = invertHorizontal ? -rBox.width : rBox.width;
		g2d.drawImage(texture.getCurrentFrame(), xx, y, width, rBox.height, null);
	}

	// Used to draw the gun onto the screen
	private void drawGun(Graphics2D g2d) {
		if (isAlive) {
			g2d.rotate(gunAngle, rBox.getCenterX(), rBox.getCenterY());
			if (Math.abs(gunAngle) > 1.07) {
				g2d.drawImage(activeGun.getSprite(id + 1, hp), (int) (rBox.getCenterX()) + 13,
						(int) (rBox.getCenterY()) + 20, (int) (50 * scaleFactor), (int) (-50 * scaleFactor),
						null);
			} else {
				g2d.drawImage(activeGun.getSprite(id + 1, hp), (int) (rBox.getCenterX()) + 13,
						(int) (rBox.getCenterY()) - 20, (int) (50 * scaleFactor), (int) (50 * scaleFactor),
						null);
			}

			g2d.rotate(-gunAngle, rBox.getCenterX(), rBox.getCenterY());
		}

	}

	@Override
	public void advanceAnimationFrame() {
		for (String position : POSITIONS_SEQUENCE) {
			texture.getAnimation(position).advanceFrame();
		}
	}

	private void initializeTexture() {
		texture = Texture.createTexture("player");

		// Default skin is marine
		for (int i = 0; i < POSITIONS_SEQUENCE.length; i++) {
			texture.addAnimation(POSITIONS_SEQUENCE[i],
					new Animation(ImageLoader.MARINESKIN[i], false));
		}
		texture.addAnimation(FRONT_DEATH, new Animation(ImageLoader.MARINE_FRONTDEATH, true));
		texture.addAnimation(SIDE_DEATH, new Animation(ImageLoader.MARINE_SIDEDEATH, true));
		texture.addAnimation(BACK_DEATH, new Animation(ImageLoader.MARINE_BACKDEATH, true));
	}

	public void advanceDeathAnimationFrame() {
		for (String position : DEATH_POSITIONS_SEQUENCE) {
			texture.getAnimation(position).advanceFrame();
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
	// TODO: make this responsive to different types of guns
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
		BufferedImage[] skin = null;
		BufferedImage frontDeath = null, sideDeath = null, backDeath = null;
		switch (id) {
			case MARINE:
				skin = ImageLoader.MARINESKIN;
				frontDeath = ImageLoader.MARINE_FRONTDEATH;
				sideDeath = ImageLoader.MARINE_SIDEDEATH;
				backDeath = ImageLoader.MARINE_BACKDEATH;
				hp += hp / 2;
				break;
			case WSB:
				skin = ImageLoader.WSBSKIN;
				frontDeath = ImageLoader.WSB_FRONTDEATH;
				sideDeath = ImageLoader.WSB_SIDEDEATH;
				backDeath = ImageLoader.WSB_BACKDEATH;
				break;
			case SECRET:
				skin = ImageLoader.SECRETSKIN;
				frontDeath = ImageLoader.SECRET_FRONTDEATH;
				sideDeath = ImageLoader.SECRET_SIDEDEATH;
				backDeath = ImageLoader.SECRET_BACKDEATH;
		}
		assert skin != null;
		for (int i = 0; i < POSITIONS_SEQUENCE.length; i++) {
			texture.getAnimation(POSITIONS_SEQUENCE[i]).recreateAnimation(skin[i]);
		}
		texture.getAnimation(FRONT_DEATH).recreateAnimation(frontDeath);
		texture.getAnimation(SIDE_DEATH).recreateAnimation(sideDeath);
		texture.getAnimation(BACK_DEATH).recreateAnimation(backDeath);
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
