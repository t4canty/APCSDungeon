package gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends GameObject{
	private Loot activeGun;
	
	private int minX = 0;
	private int minY = 0;
	private int maxX;
	private int maxY;
	private double gunAngle;
	private ArrayList<Loot> inventory = new ArrayList<Loot>();
	/**
	 * Player constructor with x and y inputs;
	 * @param x
	 * Starting X position for the player on the jframe
	 * @param y
	 * Starting Y position for the player on the jframe
	 * @param size
	 * Size of the player object
	 * @param Sprite1
	 * Full path to the idle sprite
	 * @param Sprite2
	 * Full path to the move sprite
	 * @param Sprite3
	 * Full path to the hurt sprite
	 * @param Sprite4 full path to Attack Sprite
	 */
	public Player(int x, int y, Dimension size, String Sprite1, String Sprite2, String Sprite3, String Sprite4) throws IOException {
		this.x = x;
		this.y = y;
		this.hp = 100;
		this.rBox = new Rectangle(size);
		if(isJar)
			getImagesFromJar(Sprite1, Sprite2, Sprite3, Sprite4);
		else
			getImagesFromFolder(Sprite1, Sprite2, Sprite3, Sprite4);
		activeGun = new Loot(1, 0, "Bad Gun", super.isJar);
		inventory.add(activeGun);
	}
	/**
	 * Player constructor
	 * @param x
	 * Starting X position for the player on the jframe
	 * @param y
	 * Starting Y position for the player on the jframe
	 * @param size
	 * Size of the player object
	 * @param x
	 * Starting X position for the player on the jframe
	 * @param y
	 * Starting Y position for the player on the jframe
	 * @param size
	 * Size of the player object
	 * @param Sprite1
	 * Full path to the idle sprite
	 * @param Sprite2
	 * Full path to the move sprite
	 * @param Sprite3
	 * Full path to the hurt sprite
	 * @param Sprite4 full path to Attack Sprite
	 */
	public Player(Dimension size, String Sprite1, String Sprite2, String Sprite3, String Sprite4) throws IOException {
		this.x = 0;
		this.y = 0;
		this.hp = 100;
		this.rBox = new Rectangle(size);
		if(isJar)
			getImagesFromJar(Sprite1, Sprite2, Sprite3, Sprite4);
		else
			getImagesFromFolder(Sprite1, Sprite2, Sprite3, Sprite4);
		activeGun = new Loot(1, 0, "Bad Gun", super.isJar);
		inventory.add(activeGun);
	}
	@Override
	public void paint(Graphics g) {
		rBox.x = x;
		rBox.y = y;
		
		Graphics2D g2d = (Graphics2D) g; //neccessary for drawing gifs
		g2d.drawImage(idleSprite, x, y, null);
		g2d.draw(rBox);
		
		
		g2d.rotate(gunAngle, rBox.getCenterX(), rBox.getCenterY());
		g2d.drawLine((int)(rBox.getCenterX()), (int)(rBox.getCenterY()), (int)(rBox.getCenterX() + 100), (int)(rBox.getCenterY()));
	}
	
	
	public void move(int dir) {
		switch(dir) {
		case 0:
			y -= 10;
			break;
		case 1:
			x += 10;
			break;
		case 2:
			y += 10;
			break;
		case 3:
			x -= 10;
			break;
		}
		
		if(y < minY) y = minY;
		if(y > maxY) y = maxY;
		if(x < minX) x = minX;
		if(x > maxX) x = maxX;
	}
	
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void updateGunAngle(int mouseX, int mouseY) {
		//double riseRun = (mouseY - y) * 1.0 / (mouseX - x);
		gunAngle = Math.atan2(mouseY - rBox.getCenterY(), mouseX - rBox.getCenterX());
		
	}
	
	public void checkCollision(ArrayList<GameObject> entities) {
		
	}
	
	public void updateBounds(int[] bounds) {
		minY = bounds[0];
		maxX = bounds[1] - rBox.width;
		maxY = bounds[2] - rBox.height;
		minX = bounds[3];
	}

}
