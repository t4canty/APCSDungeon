package gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;


/**
 * This class is the superclass to all objects that 'exist' in the game, excluding things like rooms or ui.
 * 1) Enemy - anything that damages the player.
 * 2) Player - controlled by the player.
 * 4) Wall/object - static objects that just have collision.
 * @author t4canty
 * @author TJ178
 */
public abstract class GameObject {
	//========Final Variables========//
	final static public int NORTH = 0;
	final static public int SOUTH = 2;
	final static public int EAST = 3;
	final static public int WEST = 1;
	final static public int UP = 0;
	final static public int DOWN = 2;
	final static public int LEFT = 3;
	final static public int RIGHT = 1;
	final static public int UPRIGHT = 4;
	final static public int DOWNRIGHT = 5;
	final static public int DOWNLEFT = 6;
	final static public int UPLEFT = 7;
	final static public int FRONTIDLE = 0;
	final static public int SIDEIDLE = 1;
	final static public int BACKIDLE = 2;
	final static public int FRONTMOVE = 3;
	final static public int SIDEMOVE = 4;
	final static public int BACKMOVE = 5;
	final static public int FRONTHURT = 6;
	final static public int SIDEHURT = 7;
	final static public int BACKHURT = 8;
	final static public int FRONTDEATH = 0;
	final static public int SIDEDEATH = 1;
	final static public int BACKDEATH = 2;
	//========Variables========//
	protected Rectangle rBox;
	protected int x, y;
	protected int hp;
	protected boolean debug = false;
	public boolean isJar = true;
	protected boolean hasAI = false;
	
	//========Abstract methods========//
	public abstract void paint(Graphics g);
	public abstract void advanceAnimationFrame();
	
	//========Methods========//
	public double getDistanceFrom(int x, int y) {
		return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
	}
	
	//check if this GameObject's hitbox is colliding with another
	public boolean isColliding(Rectangle r) {
		if(r != null) {
			return rBox.intersects(r);
		}
		return false;
	}
	
	//========Getters/Setters========//
	public Rectangle getHitbox() {return rBox;}
	public int getX() { return x; }
	public int getY() { return y; }
	public void setX(int x) { this.x = x;}
	public void setY(int y) { this.y = y;}
	public int getCenterX() { return x + rBox.width/2;}
	public int getCenterY() { return y + rBox.height/2;};
	public boolean hasAI() { return hasAI; }
	public int getHP() { return hp; }
}
