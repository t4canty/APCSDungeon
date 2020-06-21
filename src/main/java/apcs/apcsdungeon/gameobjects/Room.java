package apcs.apcsdungeon.gameobjects;

import apcs.apcsdungeon.driver.Driver;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Container for all entities and map properties.
 * Created May 26, 2020
 *
 * @author TJ178
 * @author t4canty
 */
public class Room {
	public boolean isJar = false;                //change image loading if image is a jar file
	protected int leftBound;
	protected int topBound;
	protected int bottomBound;            //boundaries for all entities
	protected int rightBound;
	protected Image backgroundSprite;            //image for background
	protected Room rightRoom;                    //linked room to the right
	protected Room leftRoom;                    //linked room to the left
	protected Room topRoom;
	protected Room bottomRoom;
	protected Rectangle leftDoor;                //hitboxes for room portals (doors)
	protected Rectangle rightDoor;
	protected Rectangle topDoor;
	protected Rectangle bottomDoor;
	protected boolean doorOpen;            //if the door to the next room can be walked through
	protected ArrayList<GameObject> entities;    //list of entities within the room
	private Dimension screenSize;
	private double ratio;

	/**
	 * Room Constructor
	 *
	 * @param usableArea      Sets boundaries for entities within the space of the Rectangle
	 * @param background      Image to be used in the background
	 * @param entities        All entities that will be in this room
	 * @param doorOpen        Sets whether or not the right door can be used
	 */
	public Room(Rectangle usableArea, Image background, ArrayList<GameObject> entities, boolean doorOpen,
				Dimension screenSize, double ratio) {
		leftBound = usableArea.x;
		topBound = usableArea.y;
		bottomBound = usableArea.y + usableArea.height;
		rightBound = usableArea.x + usableArea.width;
		this.entities = entities;
		this.doorOpen = doorOpen;
		backgroundSprite = background;
		this.screenSize = screenSize;
		this.ratio = ratio;
	}

	//========Methods========//
	public void paint(Graphics g) {
		g.drawImage(backgroundSprite, 0, 0, screenSize.width, screenSize.height, null);

		if (Driver.debug) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.BLACK);
			if (leftDoor != null) g2d.draw(leftDoor);
			if (rightDoor != null) g2d.draw(rightDoor);
			if (topDoor != null) g2d.draw(topDoor);
			if (bottomDoor != null) g2d.draw(bottomDoor);
		}
	}

	//Method for paiting all the enetities in the room.
	public void paintEntities(Graphics g) {
		collision();
		for (GameObject o : entities) {
			o.paint(g);
		}
	}

	///check if any objects are colliding with each other on the map
	private void collision() {
		for (int i = 0; i < entities.size(); i++) {
			GameObject temp = entities.get(i);
			for (int j = 0; j < entities.size(); j++) {

				//check if projectiles hit the wall
				if (temp instanceof Projectile) {
					if (temp.getHitbox().getX() > rightBound || temp.getHitbox().getX() < leftBound || temp.getHitbox().getY() > bottomBound || temp.getHitbox().getY() < topBound) {
						entities.remove(i);
						i--;
						break;
					}
				}

				if (j != i) {
					GameObject temp2 = entities.get(j);
					//check if a projectile that was shot by the player hit an enemy
					//kill the projectile if so, kill the enemy if it has no health left and spawn its drop
					if (temp instanceof Projectile && !((Projectile) temp).isEnemyFire() && (temp2 instanceof Enemy || temp2 instanceof Chest)) {
						if (temp.getHitbox().intersects(temp2.getHitbox())) {
							if (temp2 instanceof Enemy) {
								((Enemy) temp2).damage(((Projectile) temp).getDamage());
								if (temp2.hp <= 0) {
									if (temp2 instanceof Boss) ((Boss) temp2).onDeath();
									entities.add(new DroppedItem((Enemy) temp2));
									entities.remove(j);
									if (j < i) {
										i--;
									}
								}
							} else {
								((Chest) temp2).damage(((Projectile) temp).getDamage());
								if (temp2.hp <= 0) {
									entities.add(new DroppedItem(temp2.getCenterX(), temp2.getCenterY(), ((Chest) temp2).getDrop(), 25));
									entities.remove(j);
									if (j < i) {
										i--;
									}
								}
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
		for (GameObject e : entities) {
			if (e instanceof Projectile && !((Projectile) e).isEnemyFire())
				if (e.getDistanceFrom(x, y) < (75 * ratio)) {
					return true;
				}
		}
		return false;
	}

	public void removeProjectiles() {
		entities.removeIf(e -> e instanceof Projectile);
	}

	//============Getters/Setters=============//
	//get the bounds as an array
	public int[] getBounds() {
		return new int[]{topBound, rightBound, bottomBound, leftBound};
	}

	//get the bounds as a rectangle
	public Rectangle getRectBounds() {
		return new Rectangle(leftBound, topBound, bottomBound - topBound, rightBound - leftBound);
	}

	public Rectangle getLeftDoor() {
		return leftDoor;
	}

	public Rectangle getRightDoor() {
		return rightDoor;
	}

	public Rectangle getTopDoor() {
		return topDoor;
	}

	public Rectangle getBottomDoor() {
		return bottomDoor;
	}

	//	public void setLeftDoor(Rectangle r) { leftDoor = r;}
//	public void setRightDoor(Rectangle r) { rightDoor = r;}
//	public void setTopDoor(Rectangle r) { topDoor = r;}
//	public void setBottomDoor(Rectangle r) { bottomDoor = r;}
	public Room topRoom() {
		return topRoom;
	}

	public Room bottomRoom() {
		return bottomRoom;
	}

	public Room leftRoom() {
		return leftRoom;
	}

	public Room rightRoom() {
		return rightRoom;
	}

	public void setRightRoom(Room r) {
		rightRoom = r;
		rightDoor = new Rectangle((int) (950 * ratio), (int) (430 * ratio), (int) (40 * ratio), (int) (128 * ratio));
	}

	public void setBottomRoom(Room r) {
		bottomRoom = r;
		bottomDoor = new Rectangle((int) (440 * ratio), (int) (900 * ratio), (int) (128 * ratio), (int) (40 * ratio));
	}

	public void setLeftRoom(Room r) {
		leftRoom = r;
		leftDoor = new Rectangle(0, (int) (430 * ratio), (int) (50 * ratio), (int) (128 * ratio));
	}

	public void setTopRoom(Room r) {
		topRoom = r;
		//topDoor = new Rectangle((int)(440 * ratio), (int)(10 * ratio), (int)(128 * ratio), (int)(40 * ratio));
		topDoor = new Rectangle((int) (440 * ratio), (int) (10 * ratio), (int) (128 * ratio), (int) (40 * ratio));
	}

	public boolean isDoorOpen() {
		return doorOpen;
	}

	public ArrayList<GameObject> getEntities() {
		return entities;
	}

	public void addProp(Prop p) {
		entities.add(p);
	}
}
