package gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Projectile extends GameObject {
	private int velocity;
	/**
	 * Projectile to be drawn on screen
	 * @param x
	 * @param y
	 * @param velocity
	 * @param sprite
	 * @param id
	 * Id of what kind of bullet to shoot - returned from a loot object
	 * @param size
	 */
	public Projectile(int x, int y, float velocity, Image sprite, int id, Dimension size) {
		this.x = x;
		this.y = y;
		hp = -1;
		this.rBox = new Rectangle(size);
		this.idleSprite = sprite;
	}
	@Override
	public void paint(Graphics g) {
	}
	
}
