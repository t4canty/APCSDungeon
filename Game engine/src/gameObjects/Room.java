package gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Container for all entities and map properties
 * Created May 26, 2020
 * @author TJ178
 * @author t4canty
 *
 */

public class Room {
	protected int leftBound;
	protected int topBound;
	protected int bottomBound;			//boundaries for all entities
	protected int rightBound;
	protected Image backgroundSprite;			//image for background
	protected Room rightRoom;					//linked room to the right
	protected Room leftRoom;					//linked room to the left
	protected Rectangle leftDoor;				//hitboxes for room portals (doors)
	protected Rectangle rightDoor;
	protected boolean doorOpen = false;		 	//if the door to the next room can be walked through
	protected ArrayList<GameObject> entities;	//list of entities within the room
	public boolean isJar = false;				//change image loading if image is a jar file
	
	/**
	 * Room Constructor
	 * @param usableArea
	 * Sets boundaries for entities within the space of the Rectangle
	 * @param leftDoorHitbox
	 * Hitbox to go to the room on the left
	 * @param rightDoorHitbox
	 * Hitbox to go to the room on the right
	 * @param background
	 * Image to be used in the background
	 * @param leftRoom
	 * Room to the left of this room
	 * @param entities
	 * All entities that will be in this room
	 * @param doorOpen
	 * Sets whether or not the right door can be used
	 * @throws IOException
	 */
	public Room(Rectangle usableArea, Rectangle leftDoorHitbox, Rectangle rightDoorHitbox, Image background, Room leftRoom, ArrayList<GameObject> entities, boolean doorOpen) throws IOException {
		leftBound = usableArea.x;
		topBound = usableArea.y;
		bottomBound = usableArea.y + usableArea.height;
		rightBound = usableArea.x + usableArea.width;
		this.leftRoom = leftRoom;
		this.entities = entities;
		rightDoor = rightDoorHitbox;
		leftDoor = leftDoorHitbox;
		this.doorOpen = doorOpen;
		backgroundSprite = background;
		
		if(leftRoom != null) {
			leftRoom.setRightRoom(this);
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(backgroundSprite, 0, 0, null);
	}
	
	public void paintEntities(Graphics g) {
		collision();
		
		for(int i = 0; i < entities.size(); i++) {
			GameObject o = entities.get(i);
			o.paint(g);
		}
	}
	
	
	///check if any objects are colliding with each other on the map
	public void collision() {
		for(int i = 0; i < entities.size(); i++) {
			GameObject temp = entities.get(i);
			for(int j = 0; j < entities.size(); j++) {
				
				//check if projectiles hit the wall
				if(temp instanceof Projectile) {
					if(temp.getHitbox().getX() > rightBound || temp.getHitbox().getX() < leftBound || temp.getHitbox().getY() > bottomBound || temp.getHitbox().getY() < topBound) {
						entities.remove(i);
						i--;
						break;
					}
				}
				
				if(j != i) {
					GameObject temp2 = entities.get(j);
					//check if a projectile that was shot by the player hit an enemy
					//kill the projectile if so, kill the enemy if it has no health left and spawn its drop
					if(temp instanceof Projectile && !((Projectile) temp).isEnemyFire() && temp2 instanceof Enemy){
						if(temp.getHitbox().intersects(temp2.getHitbox())) {
							((Enemy) temp2).damage(((Projectile)temp).getDamage());
							if(temp2.hp <= 0) {
								entities.add(new DroppedItem(temp2.getCenterX(), temp2.getCenterY(), ((Enemy)temp2).getDrop(), 25));
								entities.remove(j);
								i--;
							}
							
							entities.remove(i);
							i--;
							break;
						}
					}
				}
			}
		}
	}
	
	
	public boolean isCloseToPlayerProjectile(int x, int y) {
		for(GameObject e : entities) {
			if(e instanceof Projectile && !((Projectile)e).isEnemyFire())
			if(e.getDistanceFrom(x, y) < 75){
				return true;
			}
		}
		return false;
	}
	
	
	//============ Getters / Setters =============//
	
	//get the bounds as an array
	public int[] getBounds() {
		int[] bounds = {topBound, rightBound, bottomBound, leftBound};
		return bounds;
	}
	
	//get the bounds as a rectangle
	public Rectangle getRectBounds() { return new Rectangle(leftBound, topBound, bottomBound-topBound, rightBound-leftBound); }
	public Rectangle getLeftDoor() { return leftDoor; }
	public Rectangle getRightDoor() { return rightDoor; }
	public Room nextRoom() { return rightRoom; }
	public void setRightRoom(Room r) { rightRoom = r; }
	public Room lastRoom() { return leftRoom; }
	public boolean isDoorOpen() { return doorOpen; }
	public ArrayList<GameObject> getEntities(){ return entities; }
}
