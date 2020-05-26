package gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Projectile extends GameObject {
	private int velocityX = 0;
	private int velocityY = 0;
	/**
	 * Projectile to be drawn on screen
	 * @param x
	 * @param y
	 * @param velocityX
	 * @param velocityY
	 * @param sprite
	 * @param id
	 * Id of what kind of bullet to shoot - returned from a loot object
	 * @param size
	 */
	public Projectile(int x, int y, int velocityX, int velocityY, Image sprite, int id, Dimension size) {
		this.x = x;
		this.y = y;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		hp = -1;
		this.rBox = new Rectangle(size);
		this.idleSprite = sprite;
	}
	@Override
	public void paint(Graphics g) {
		x += velocityX;
		y += velocityY;
		rBox.x = x;
		rBox.y = y;
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(rBox);
		
	}
	public void setSize(Dimension size) {
		rBox = new Rectangle(size);
	}
	public void setSprite(Image Sprite) {
		this.idleSprite = Sprite;
	}
}
