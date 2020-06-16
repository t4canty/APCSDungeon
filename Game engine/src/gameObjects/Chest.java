package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;
import fileIO.ImageLoader;

/**
 * Basic chest entity, drops loot when hit.
 * @author TJ178
 * @author t4canty
 *
 */
public class Chest extends Prop {
	//========Variables========//
	private Loot item;
	private boolean isOpen;
	private double sFactor;
	
	//========Constructors========//
	/**
	 * Creates a chest at the given x and y coords. 
	 * @param x
	 * @param y
	 * @param sprite
	 */
	public Chest(int x, int y, Image sprite, double ratio) {
		super(x, y, sprite);
		sFactor = ratio;
		isOpen = false;
		computeDrop();
	}
	/**
	 * Creates a chest at the given x and y coords with a specific item. 
	 * @param x
	 * @param y
	 * @param sprite
	 */
	public Chest(int x, int y, Image sprite, Loot l, double ratio) {
		super(x, y, sprite);
		isOpen = false;
		item = l;
	}
	
	//========Methods========//
	//private method to fill a chest with a random item. 
	private void computeDrop() {
		int rand = new Random().nextInt(7);
		switch (rand) {
		case 0:																//BadGun
			item = new Gun(10, 300, 5, 5, 15, Gun.BADGUN, 5, "Bad Gun", isJar, sFactor);
			break;
		case 1:																//BetterGun
			item = new Gun(20, 200, 15, 8, 15, Gun.BETTERGUN, 5, "Better Gun", isJar, sFactor);
			
			break;
		case 2:																//FederalReserve
			item = new Gun(10, 50, 30, 10, 30, Gun.FEDRESERVE, 5, "Federal Reserve", isJar, sFactor);
			
			break;
		case 3:																//ElPresidente
			item = new Gun(40, 600, 8, 15, 20, Gun.PRESIDENTE, 9, "El Presidente", isJar, sFactor);
			
			break;
		case 4:																//ToiletPaper
			item = new Gun(100, 10000, 3, 7, 50, Gun.TP, 2, "Toilet Paper", isJar, sFactor);
			
			break;
		case 5:																//Health Item
			item = new Health("Small Heath Potion", ImageLoader.NO_IMAGE);	
			break;
		case 6:
			rand = new Random().nextInt(100);
			item = new AmmoMag(10 + rand, ImageLoader.PISTOLMAG);
		}
		if(debug) System.out.println("Random number in chest ComputeDrop():" + rand + " Drop:" + item.getName());
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		g2d.draw(rBox);
		g.drawImage(Sprite, x, y, rBox.width, rBox.height, null);
	}
	
	//we don't care about this.
	@Override
	public void advanceAnimationFrame() {}
	
	public void openChest(Player p) {
		item.use(p);
		isOpen = true;
	}
	
	//========Getters/Setters========//
	public Loot getDrop() {
		isOpen = true;
		return item;
	}
	public void damage(int hp) {this.hp -= hp;}
}
