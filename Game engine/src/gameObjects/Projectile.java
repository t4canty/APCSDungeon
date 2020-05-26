package gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Projectile extends GameObject {
	private int velocityX = 0;
	private int velocityY = 0;
	private int damage = 0;
	private boolean isEnemyFire = false;
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
	 * determines whether the projectile hurts enemies or the player
	 * @param isEnemy
	 */
	public Projectile(int damage, boolean isEnemy, int x, int y, int velocity, double angle, Dimension size, Image sprite, int id) {
		this.damage = damage;
		this.isEnemyFire = isEnemy;
		this.x = x;
		this.y = y;
		
		this.velocityX = (int)(velocity * Math.cos(angle));
		this.velocityY = (int)(velocity * Math.sin(angle));
		hp = -1;
		this.rBox = new Rectangle(size);
		rBox.x = x;
		rBox.y = y;
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
	
	public boolean isEnemyFire() {return isEnemyFire;}
	public int getDamage() {return damage;}
}
