package driver;

import java.awt.Rectangle;
import java.util.ArrayList;

import gameObjects.*;

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
									System.out.println("this is a different gameObject");
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
