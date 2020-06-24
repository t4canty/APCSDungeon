package apcs.apcsdungeon.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;


/**
 * This class is the superclass to all objects that 'exist' in the game, excluding things like rooms or ui.
 * 1) Enemy - anything that damages the player.
 * 2) Player - controlled by the player.
 * 4) Wall/object - static objects that just have collision.
 *
 * @author t4canty
 * @author TJ178
 */
public abstract class GameObject {
	//========Final Variables========//
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int UPRIGHT = 4;
	public static final int DOWNRIGHT = 5;
	public static final int DOWNLEFT = 6;
	public static final int UPLEFT = 7;

	public static final String FRONT_IDLE = "front_idle";
	public static final String SIDE_IDLE = "side_idle";
	public static final String BACK_IDLE = "back_idle";
	public static final String FRONT_MOVE = "front_move";
	public static final String SIDE_MOVE = "side_move";
	public static final String BACK_MOVE = "back_move";
	public static final String FRONT_HURT = "front_hurt";
	public static final String SIDE_HURT = "side_hurt";
	public static final String BACK_HURT = "back_hurt";
	public static final String[] POSITIONS_SEQUENCE = new String[] {FRONT_IDLE, SIDE_IDLE, BACK_IDLE, FRONT_MOVE,
			SIDE_MOVE, BACK_MOVE, FRONT_HURT, SIDE_HURT, BACK_HURT};

	public static final String FRONT_DEATH = "front_death";
	public static final String SIDE_DEATH = "side_death";
	public static final String BACK_DEATH = "back_death";
	public static final String[] DEATH_POSITIONS_SEQUENCE = new String[] {FRONT_DEATH, SIDE_DEATH, BACK_DEATH};
	public boolean isJar = true;
	//========Variables========//
	protected Texture texture;
	protected Rectangle rBox;
	protected int x, y;
	protected int hp;
	protected boolean hasAI = false;

	//========Abstract methods========//
	public abstract void paint(Graphics g);

	public abstract void advanceAnimationFrame();

	//========Methods========//
	public double getDistanceFrom(int x, int y) {
		return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
	}

	// check if this GameObject's hitbox is colliding with another
	public boolean isColliding(Rectangle r) {
		if (r != null) {
			return rBox.intersects(r);
		}
		return false;
	}

	//========Getters/Setters========//
	public Rectangle getHitbox() {
		return rBox;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCenterX() {
		return x + rBox.width / 2;
	}

	public int getCenterY() {
		return y + rBox.height / 2;
	}

	public boolean hasAI() {
		return hasAI;
	}

	public int getHP() {
		return hp;
	}
}
