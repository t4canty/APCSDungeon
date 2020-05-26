package gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Room {
	protected int leftBound;
	protected int topBound;
	protected int bottomBound;			//boundaries for all entities
	protected int rightBound;
	protected Image backgroundSprite;			//image for background
	protected Room rightRoom;					//linked room to the right
	protected Room leftRoom;					//linked room to the left
	protected ArrayList<GameObject> entities;	//list of entities within the room
	public boolean isJar = false;				//change image loading if image is a jar file
	
	protected boolean doorOpen = false;		 	//if the door to the next room can be walked through
	
	
	public Room(Rectangle usableArea, String background, Room leftRoom, ArrayList<GameObject> entities, boolean doorOpen) throws IOException {
		leftBound = usableArea.x;
		topBound = usableArea.y;
		bottomBound = usableArea.y + usableArea.height;
		rightBound = usableArea.x + usableArea.width;
		this.leftRoom = leftRoom;
		this.entities = entities;
		this.doorOpen = doorOpen;
		
		if(leftRoom != null) {
			leftRoom.setRightRoom(this);
		}
		
		if(isJar) {
			getImagesFromJar(background);
		}else {
			getImagesFromFolder(background);
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(backgroundSprite, 0, 0, null);
	}
	
	public void setRightRoom(Room r) {
		rightRoom = r;
	}
	
	public ArrayList<GameObject> getEntities(){
		return entities;
	}
	
	public int[] getBounds() {
		int[] bounds = {topBound, rightBound, bottomBound, leftBound};
		return bounds;
	}
	
	
	public void getImagesFromFolder(String background) throws IOException {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		this.backgroundSprite = toolkit.getImage(background);
		if(backgroundSprite == null) {
			throw new IOException();
		}
	}
	
	public void getImagesFromJar(String background) throws IOException {
		if(getClass().getResourceAsStream(background) == null) {
			System.err.println("Error, getClass is null");
		}
		this.backgroundSprite = ImageIO.read(getClass().getResourceAsStream(background));
	}
}
