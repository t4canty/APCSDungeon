package gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;

//import org.apache.commons.io.IOUtils;

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
	final public int NORTH = 0;
	final public int SOUTH = 2;
	final public int EAST = 3;
	final public int WEST = 1;
	final public int UP = 0;
	final public int DOWN = 2;
	final public int LEFT = 3;
	final public int RIGHT = 1;
	final public int UPRIGHT = 4;
	final public int DOWNRIGHT = 5;
	final public int DOWNLEFT = 6;
	final public int UPLEFT = 7;
	//========Variables========//
	protected Rectangle rBox;
	protected int x, y;
	protected int hp;
	protected AnimatedImage idleSprite;																//Sprites, stored in gif format
	protected AnimatedImage moveSprite;
	protected AnimatedImage attackSprite;
	protected AnimatedImage hurtSprite;
	protected boolean debug = false;
	public boolean isJar = false;
	protected boolean hasAI = false;
	
	//========Abstract methods========//
	public abstract void paint(Graphics g);
	
	//========Methods========//
	/**
	 * When running from eclipse, use this as opposed to ImagesFromJar(). Uses the Toolkit Library to open images.
	 * @param idleSprite
	 * Full path to the Idle Sprite. 
	 * <b>NOTE</b> all GameObjects must have and IdleSprite. All other spites may be null.
	 * @param moveSprite
	 * Full path to the Movement Sprite.
	 * @param hurtSprite
	 * Full path to the Hurt sprite.
	 * @param attackSprite
	 * Full path to the Attack sprite.
	 * @throws IOException
	 * Throws IOException when idleSprite is null. 
	 */
	/*public void getImagesFromFolder(String idleSprite, String moveSprite, String hurtSprite, String attackSprite) throws IOException {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		this.idleSprite = toolkit.getImage(idleSprite);
		this.moveSprite = toolkit.getImage(moveSprite);
		this.attackSprite = toolkit.getImage(hurtSprite);
		this.hurtSprite = toolkit.getImage(attackSprite);
		if(idleSprite == null)
			throw new IOException();
	}*/
	
	/**
	 * Used for compiling a finished jar, reads images from inside the jar. 
	 * @param idleSprite
	 * Full path to the Idle Sprite. 
	 * <b>NOTE</b> all GameObjects must have and IdleSprite. All other spites may be null.
	 * @param moveSprite
	 * Full path to the Movement Sprite.
	 * @param hurtSprite
	 * Full path to the Hurt sprite.
	 * @param attackSprite
	 * Full path to the Attack sprite.
	 * @throws IOException
	 * Throws IOException when idleSprite is null.
	 */
	/*public void getImagesFromJar(String idleSprite, String moveSprite, String hurtSprite, String attackSprite) throws IOException {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		if(getClass().getResourceAsStream(idleSprite) == null) {
			System.err.println("Error, getClass is null");
		}
		InputStream in = getClass().getResourceAsStream(idleSprite);
		this.idleSprite = toolkit.createImage(IOUtils.toByteArray(in));
		in = getClass().getResourceAsStream(moveSprite);
		this.moveSprite = toolkit.createImage(IOUtils.toByteArray(in));
		in = getClass().getResourceAsStream(hurtSprite);
		this.hurtSprite = toolkit.createImage(IOUtils.toByteArray(in));
		in = getClass().getResourceAsStream(attackSprite);
		this.attackSprite = toolkit.createImage(IOUtils.toByteArray(in));
	}*/
	
	public void advanceAnimationFrame() {
		if(idleSprite != null) { idleSprite.advanceCurrentFrame(); }
		if(moveSprite != null) { moveSprite.advanceCurrentFrame(); }
		if(hurtSprite != null) { hurtSprite.advanceCurrentFrame(); }
		if(attackSprite != null) { attackSprite.advanceCurrentFrame(); }
		
	}
	
	public double getDistanceFrom(int x, int y) {
		return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
	}
	
	public boolean isColliding(Rectangle r) {
		if(r != null) {
			return rBox.intersects(r);
		}
		return false;
	}
	
	//========Getters/Setters========//
	public void damage(int hp) {this.hp -= hp;}
	public Rectangle getHitbox() {return rBox;}
	public int getX() { return x; }
	public int getY() { return y; }
	public int getCenterX() { return x + rBox.width/2;}
	public int getCenterY() { return y + rBox.height/2;};
	public boolean hasAI() { return hasAI; }
	public int getHP() { return hp; }
}
