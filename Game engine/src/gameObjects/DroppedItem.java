package gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Basic Loot container class to represent an item on the ground
 * Created May 28, 2020
 * @author TJ178
 * @author t4canty
 */


public class DroppedItem extends GameObject {
	
	private Loot item;

	/**
	 * Constructor
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
	}
	
	public Loot getItem() {
		return item;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(rBox);
		g2d.drawImage(item.Sprite, x, y, rBox.height, rBox.width, null);

	}

	@Override
	public void advanceAnimationFrame() {
	}

}