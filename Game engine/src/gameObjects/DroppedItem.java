package gameObjects;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import displayComponents.AnimatedImage;
import fileIO.ImageLoader;

/**
 * Basic Loot container class to represent an item on the ground
 * Created May 28, 2020
 * @author TJ178
 * @author t4canty
 */
public class DroppedItem extends GameObject {
	//========Variables========//
	private Loot item;
	private AnimatedImage[] death = new AnimatedImage[3];
	private boolean isEnemy = false;
	private int gDir;

	//========Constructors========//
	/**
	 * Constructor for a dropped item.
	 * @param x X position on map
	 * @param y Y position on map
	 * @param item Item that this entity contains
	 * @param size Size of icon and hitbox to collect loot
	 */
	public DroppedItem(int x, int y, Loot item, int size) {
		this.x = x;
		this.y = y;
		this.item = item;
		rBox = new Rectangle(new Dimension(size,size));
		rBox.x = x;
		rBox.y = y;
		isEnemy = false;
	}

	public DroppedItem(Enemy e) {
		this.x = e.getX();
		this.y = e.getY();
		this.item = e.getDrop();
		this.rBox = e.getHitbox();
		gDir = e.getDir();
		if(e instanceof Boss) {
			death[BACKDEATH] = new AnimatedImage(ImageLoader.WSB_BACKDEATH, true);
			death[FRONTDEATH] = new AnimatedImage(ImageLoader.WSB_FRONTDEATH, true);
			death[SIDEDEATH] = new AnimatedImage(ImageLoader.WSB_SIDEDEATH, true);
		}else {
			death[BACKDEATH] = new AnimatedImage(ImageLoader.NPC_BACKDEATH, true);
			death[FRONTDEATH] = new AnimatedImage(ImageLoader.NPC_FRONTDEATH, true);
			death[SIDEDEATH] = new AnimatedImage(ImageLoader.NPC_SIDEDEATH, true);
		}
		isEnemy = true;
	}

	//========Methods========//
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if(debug) g2d.draw(rBox);

		if(!isEnemy) g2d.drawImage(item.Sprite[0], x, y, rBox.height, rBox.width, null);
		else {
			switch (gDir) {
			case UP:
				g2d.drawImage(death[BACKDEATH].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
				break;
			case DOWN:
				g2d.drawImage(death[FRONTDEATH].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
				break;
			case LEFT:
				g2d.drawImage(death[SIDEDEATH].getCurrentFrame(), x, y, rBox.width, rBox.height, null);
				break;
			case RIGHT:
				g2d.drawImage(death[SIDEDEATH].getCurrentFrame(), x + rBox.width, y, -rBox.width, rBox.height, null);
			}

			Composite old = g2d.getComposite();
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);//Animated logo
			g2d.setComposite(ac);
			g2d.drawImage(item.Sprite[0], x, y, rBox.height, rBox.width, null);
			g2d.setComposite(old);
		}
	}

	@Override
	public void advanceAnimationFrame() {
		if(isEnemy) {
			for(AnimatedImage i : death) {
				i.advanceCurrentFrame();
			}
		}
	}

	//========Getter========//
	public Loot getItem() {return item;}
}
