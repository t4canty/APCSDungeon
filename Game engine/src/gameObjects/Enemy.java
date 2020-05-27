package gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
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
	private Loot activeGun;
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
		activeGun = new Gun(100, 300, 0, "Bad Gun", super.isJar);
		
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
		//TODO
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
	
	/*
	 * Three different states for AI
	 * 1) running towards player until a certain range
	 * 2) shooting at player once within range
	 * 3) dodging bullets from player
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
	
	private int currentState = 0;
	
	public void runAI(Player player, ArrayList<GameObject> Entities, Room room) {
		switch(currentState) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		}
	}
	
	
}
