package gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Basic chest entity, drops loot when hit
 * @author TJ178
 * @author t4canty
 *
 */

public class Chest extends GameObject {
	private BufferedImage sprite;
	private Loot item;
	private boolean isOpen;
	
	public Chest(int x, int y, Dimension size, Loot item, BufferedImage sprite) {
		rBox = new Rectangle(size);
		rBox.x = x;
		rBox.y = y;
		this.hp = 10;
		this.x = x;
		this.y = y;
		this.item = item;
		this.sprite = sprite;
		isOpen = false;
	}
	
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(sprite, x, y, rBox.width, rBox.height, null);
	}

	@Override
	public void advanceAnimationFrame() {
	}
	
	public void openChest(Player p) {
		item.use(p);
		isOpen = true;
	}
	
	public Loot getDrop() {
		isOpen = true;
		return item;
	}
	
	public void damage(int hp) {
		this.hp -= hp;
	}

}
