package gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import displayComponents.AnimatedImage;

/**
 * Class for different types of projectiles available - determined by id.
 * Created May 26, 2020
 * @author t4canty
 * @author TJ178
 *
 */
public class Projectile extends GameObject {
	//========Variables========//
	private int velocityX = 0;
	private int velocityY = 0;
	private double angle;
	private int damage = 0;
	private boolean isEnemyFire = false;
	private BufferedImage sprite;
	
	//========Constructor========//
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
	public Projectile(int damage, boolean isEnemy, int x, int y, int velocity, double angle, Dimension size, BufferedImage sprite, int id, boolean isJar) {
		this.damage = damage;
		this.isEnemyFire = isEnemy;
		this.x = x;
		this.y = y;
		this.isJar = isJar;
		this.angle = angle;
		this.velocityX = (int)(velocity * Math.cos(angle));
		this.velocityY = (int)(velocity * Math.sin(angle));
		hp = -1;
		this.rBox = new Rectangle(size);
		rBox.x = x;
		rBox.y = y;
		this.sprite = sprite;
	}
	
	//========Methods========//
	@Override
	public void paint(Graphics g) {
		x += velocityX;
		y += velocityY;
		rBox.x = x;
		rBox.y = y;
		Graphics2D g2d = (Graphics2D) g;
		rotateBullet(g2d);	
	}
	private void rotateBullet(Graphics2D g2d) {
		g2d.rotate(angle, rBox.getCenterX(), rBox.getCenterY());
		g2d.drawImage(sprite, x, y, rBox.width, rBox.height, null);
		g2d.draw(rBox);
		g2d.rotate(-angle, rBox.getCenterX(), rBox.getCenterY());
	}
	//========Getters/Setters========//
	public void setSize(Dimension size) {rBox = new Rectangle(size);}
	//public void setSprite(Image Sprite) {this.idleSprite = Sprite;}
	public boolean isEnemyFire() {return isEnemyFire;}
	public int getDamage() {return damage;}

	@Override
	public void advanceAnimationFrame() {
		// TODO Auto-generated method stub
		
	}
}
