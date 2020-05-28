package gameObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * Created May 26, 2020
 * @author t4canty
 * @author TJ178
 * Class for Enemy Objects, including fields such as health, item drops, and position - as well as necessary paint methods and bg methods.
 * Inherits health, position, hitbox, and sprites from GameObject parent.  
 */
public class Enemy extends GameObject{
	//========Variables========//
	private Loot drop;
	private Gun activeGun;
	private int movementSpeed = 4;
	private boolean isShooting = false;
	private double gunAngle;
	//========Constructor========//
	/**
	 * Enemy constructor with x and y inputs;
	 * @param x
	 * Starting X position for the player on the jframe
	 * @param y
	 * Starting Y position for the player on the jframe
	 * @param size
	 * Size of the player object
	 * @param drop
	 * What thing this enemy drops - input null for nothing
	 * @param Sprite1
	 * Full path to the idle sprite
	 * @param Sprite2
	 * Full path to the move sprite
	 * @param Sprite3
	 * Full path to the hurt sprite
	 * @param Sprite4 full path to Attack Sprite
	 */
	public Enemy(int x, int y, int hp, Dimension size, String Sprite1, String Sprite2, String Sprite3, String Sprite4) throws IOException {
		this.x = x;
		this.y = y;
		this.hp = 100;
		this.rBox = new Rectangle(size);
		rBox.x = x;
		rBox.y = y;
		activeGun = new Gun(100, 700, 0, "Bad Gun", super.isJar);
		
		if(isJar)
			getImagesFromJar(Sprite1, Sprite2, Sprite3, Sprite4);
		else
			getImagesFromFolder(Sprite1, Sprite2, Sprite3, Sprite4);
		computeDrop();
	}
	//========Getters/setters========//
	public Loot getDrop() {return drop;}
	//========Methods========//
	@Override
	public void paint(Graphics g) {
		rBox.x = x;
		rBox.y = y;
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(rBox);
		
		g2d.rotate(gunAngle, rBox.getCenterX(), rBox.getCenterY());
		g2d.drawImage(activeGun.getSprite(), (int)(rBox.getCenterX()) + 10, (int)(rBox.getCenterY()) - 10, null);
		if(debug) g2d.drawLine((int)(rBox.getCenterX()), (int)(rBox.getCenterY()), (int)(rBox.getCenterX() + 100), (int)(rBox.getCenterY()));
		g2d.rotate(-gunAngle, rBox.getCenterX(), rBox.getCenterY());
	}
	
	
	private void computeDrop() {
		int rand = new Random().nextInt(6);
		switch (rand) {
		case 0:																//BadGun
			drop = new Gun(1, 300, 0, "Bad Gun", isJar);
			break;
		case 1:																//BetterGun
			drop = new Gun(2, 200, 1, "Better Gun", isJar);
			break;
		case 2:																//FederalReserve
			drop = new Gun(1, 100, 2, "Federal Reserve", isJar);
			break;
		case 3:																//ElPresidente
			drop = new Gun(4, 600, 3, "El Presidente", isJar);
			break;
		case 4:																//ToiletPaper
			drop = new Gun(10, 1000, 4, "Toilet Paper", isJar);
			break;
		case 5:																//Health Item
			drop = new Health(-10, "Small Heath Potio", null, 2000); 		//TODO Fix later to include actual sprite
		}
		if(debug) System.out.println("Random number in ComputeDrop():" + rand + " Drop:" + drop.getName());
	}
	
	public Projectile getGunshot() {
		return activeGun.getGunshot(getCenterY(), getCenterY(), gunAngle, true);
	}
	
	/*
	 * Three different states for AI
	 * 1) running towards player until a certain range
	 * 2) running away from player when they're too close
	 * 3) shooting at player once within range // if they're too close
	 * 4) dodging bullets from player
	 * 
	 * Things AI needs to check for in step 1
	 * 1) make sure it doesn't run into walls or other enemies
	 * 		figuring out how to get around other things will be complicated but not impossible
	 * 		pathfinding will have to have something to do with squares an enemy can walk on, perhaps generate a path for each enemy for each frame? idk how long that would take
	 * 
	 * Things AI needs to check for in step 2
	 * 1) make sure that there's a clear line to the player
	 * 2) make sure that it shoots out the appropriate projectile for the gun it's holding
	 * 
	 * Things AI needs to check for in step 3
	 * 1) don't dodge bullets from other enemies
	 * 2) probably just move away from a certain radius of a projectile if one gets close
	 * 3) perhaps a sort of risk/reward system where small bullets are less likely to get it to run rather than fight
	 */
	
	public void runAI(Player player, Room room) {
		int currentState = 0;
		int distFromPlayer = (int)getDistanceFrom(player.getCenterX(), player.getCenterY());
		if(distFromPlayer > 250) {
			currentState = 0;
		}else if(distFromPlayer < rBox.height * 2) {
			currentState = 1;
		}else if(room.isCloseToPlayerProjectile(getCenterX(), getCenterY())) {
			currentState = 3;
		}else {
			currentState = 2;
		}
		
		//System.out.println(currentState);
		
		switch(currentState) {
		case 0:
			x += movementSpeed * (player.getCenterX() - getCenterX()) / distFromPlayer;
			y += movementSpeed * (player.getCenterY() - getCenterY()) / distFromPlayer;
			isShooting = false;
			break;
		case 1:
			x -= movementSpeed * (player.getCenterX() - getCenterX()) / distFromPlayer;
			y -= movementSpeed * (player.getCenterY() - getCenterY()) / distFromPlayer;
			isShooting = true;
			break;
		case 2:
			isShooting = true;
			break;
		}
		
		if(y < room.topBound) y = room.topBound;				//Collision on the bounds of the room
		if(y > room.bottomBound - rBox.height) y = room.bottomBound - rBox.height;
		if(x < room.leftBound) x = room.leftBound;
		if(x > room.rightBound - rBox.width) x = room.rightBound - rBox.width;;
		
		gunAngle = Math.atan2(player.getCenterY() - rBox.getCenterY(), player.getCenterX() - rBox.getCenterX());
	}
	
	
	// Getters / Setters
	public boolean isShooting() { return isShooting; }
}
