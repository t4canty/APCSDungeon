package gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class is meant to hold the global position of every game object - so excluding text boxes, ui elements, etc.
 * There are 4 types of objects in the game:
 * 1) Enemy - anything that triggers a 'battle' event, or damages player.
 * 2) Player - controlled by the player.
 * 3) Npc - similar to objects, except they contain dialogue. (Note: chests or other objects that require interacting are considered npcs.)
 * 4) Wall/object - static objects that just have collision.
 * @author t54cs
 */
public abstract class GameObject {
	protected Rectangle rBox;
	protected int x, y;
	protected int hp;
	//Sprites, stored in gif format
	protected Image idleSprite;
	protected Image moveSprite;
	protected Image attackSprite;
	protected Image hurtSprite;
	public boolean isJar = false;
	
	public abstract void paint(Graphics g);
	
	public void getImagesFromFolder(String idleSprite, String moveSprite, String hurtSprite, String attackSprite) throws IOException {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		this.idleSprite = toolkit.getImage(idleSprite);
		this.moveSprite = toolkit.getImage(moveSprite);
		this.attackSprite = toolkit.getImage(hurtSprite);
		this.hurtSprite = toolkit.getImage(attackSprite);
		if(idleSprite == null)
			throw new IOException();
	}
	
	public void getImagesFromJar(String idleSprite, String moveSprite, String hurtSprite, String attackSprite) throws IOException {
		if(getClass().getResourceAsStream(idleSprite) == null) {
			System.err.println("Error, getClass is null");
		}
		this.idleSprite = ImageIO.read(getClass().getResourceAsStream(idleSprite));
		this.moveSprite = ImageIO.read(getClass().getResourceAsStream(moveSprite));
		this.hurtSprite = ImageIO.read(getClass().getResourceAsStream(hurtSprite));
		this.attackSprite = ImageIO.read(getClass().getResourceAsStream(attackSprite));
	}
	
	public Rectangle getHitbox() {
		return rBox;
	}
	
	public boolean isColliding(Rectangle r) {
		return rBox.contains(r);
	}
}
