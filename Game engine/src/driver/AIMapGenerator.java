package driver;

import java.awt.Rectangle;
import java.util.ArrayList;

import gameObjects.Enemy;
import gameObjects.GameObject;
import gameObjects.Player;
import gameObjects.Projectile;
import gameObjects.Room;

/**
 * Class for AI maps.
 * Comments added on Jun 10, 2020
 * @author TJ178
 *
 */
public class AIMapGenerator {
	/*
	 * Map codes
	 * n -> null	//this shouldn't exist in finished map
	 * p -> player	//should only exist in one square
	 * x -> wall
	 * e -> enemy	//should only be as many as there are total enemies
	 * o -> player projectile
	 * . -> empty
	 */
	
	/**
	 * Generates a map of the given room for the enemy AI to use. 
	 * @param player
	 * Player object.
	 * @param room
	 * Room to generate a map for.
	 * @param screenSize
	 * Size of the given Jframe. 
	 * @return
	 */
	public static char[][] generateMap(Player player, Room room, int screenSize){
		int scannerSize = screenSize / 64;
		
		char[][] map = new char[64][64];
		for(char[] i : map) {
			for(char j : i) {
				j = 'n';
			}
		}
		Rectangle scanner = new Rectangle(0, 0, scannerSize, scannerSize);
		ArrayList<GameObject> entities = room.getEntities();
		Rectangle bounds = room.getRectBounds();
		boolean playerFound = false;
		boolean entityFound = false;
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				scanner.setLocation(j * scannerSize, i * scannerSize);
				if(!playerFound && scanner.intersects(player.getHitbox())) {
					map[i][j] = 'p';
					playerFound = true;
					continue;
				}
				
				if(!bounds.contains(scanner)) {
					map[i][j] = 'x';
					continue;
				}
				for(GameObject e : entities) {
					if(scanner.x + scannerSize > e.getX() && scanner.x - scannerSize/2 < e.getX()) {
						if(scanner.y + scannerSize > e.getY() && scanner.y - scannerSize/2 < e.getY()) {
							if(scanner.intersects(e.getHitbox())){
								if(e instanceof Enemy) {
									map[i][j] = 'e';
								}else if(e instanceof Projectile && !((Projectile)e).isEnemyFire()) {
									map[i][j] = 'o';
								}else {
									System.err.println("this is a different gameObject, somthing is wrong.");
								}
								entityFound = true;
								break;
							}
						}
					}
				}
				if(entityFound) {
					entityFound = false;
				}else {
					map[i][j] = '.';
				}
			}
		}
		return map;
	}
}
