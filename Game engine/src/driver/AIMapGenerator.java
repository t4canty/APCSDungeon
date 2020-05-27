package driver;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayDeque;
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
	
	
	public static char[][] pathfind(Enemy e, char[][] map) {
		/*ArrayList<MapPoint> points = new ArrayList<MapPoint>();
		points.add(new MapPoint(e.getX() / 64, e.getY() / 64, 'e'));
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				if(map[i][j] == '.' || map[i][j] == 'p') {
					points.add(new MapPoint(i, j, map[i][j]));
				}
			}
		}*/
		
		calculate(map);
		
		/*for(MapPoint p : points) {
			map[p.row()][p.col()] = p.character();
		}*/
		return map;
	}
	
	
	
	
	
	public static char[][] calculate(char[][] map) {
		ArrayDeque<MapPoint> queue = new ArrayDeque<MapPoint>();		//holds all open spaces seen
		ArrayDeque<MapPoint> dequeue = new ArrayDeque<MapPoint>();		//holds all spaces that have been visited
		
		char[][] data = map;
		boolean[][] seen = new boolean[64][64];
		for(boolean[] i : seen) {
			for(boolean j : i) {
				j = false;
			}
		}
		

			for(int row = 0; row < data.length; row++) {
				for(int col = 0; col < data[0].length; col++) {
					if(data[row][col] == 'e') {
						queue.add(new MapPoint(row, col, 'e'));
						row = data.length;
						col = data[0].length;
					}
				}
			}
		
		while(true) {
			MapPoint currentPoint = queue.remove();
			while(true) {
				dequeue.add(currentPoint);
				int row = currentPoint.row();
				int col = currentPoint.col();
				
				/// add point to the north
				if(row > 0 && !seen[row-1][col]) {
					if(data[row-1][col] == '.' || data[row-1][col] == 'p') {
						queue.add(new MapPoint(row-1, col, data[row-1][col]));
						seen[row-1][col] = true;
						
						if(data[row-1][col] == 'p') {
							currentPoint = new MapPoint(row-1, col, data[row-1][col]);
							break;
						}
					}
				}
				/// add point to the south
				if(row < data.length-1 && !seen[row+1][col]) {
					if(data[row+1][col] == '.' || data[row+1][col] == 'p') {
						queue.add(new MapPoint(row+1, col, data[row+1][col]));
						seen[row+1][col] = true;
						
						if(data[row+1][col] == 'p') {
							currentPoint = new MapPoint(row+1, col, data[row+1][col]);
							break;
						}
					}
				}
				/// add point to the east
				if(col < data[0].length-1 && !seen[row][col+1]) {
					if(data[row][col+1] == '.' || data[row][col+1] == 'p') {
						queue.add(new MapPoint(row, col+1, data[row][col+1]));
						seen[row][col+1] = true;
						
						if(data[row][col+1] == 'p') {
							currentPoint = new MapPoint(row, col+1, data[row][col+1]);
							break;
						}
					}
				}
				
				/// add point to the west
				if(col > 0 && !seen[row][col-1]) {
					
					if(data[row][col-1] == '.' || data[row][col-1] == 'p') {
						queue.add(new MapPoint(row, col-1, data[row][col-1]));
						seen[row][col-1] = true;
						
						if(data[row][col-1] == 'p') {
							currentPoint = new MapPoint(row, col-1, data[row][col-1]);
							break;
						}
					}
				}
				
				if(queue.size() == 0) {
					return null;
				}
				
				currentPoint = queue.remove();
				
			}
			
			if(currentPoint.character() == 'p') {
				dequeue.add(currentPoint);
				//System.out.println("found player");

				ArrayList<MapPoint> possiblePoints = new ArrayList<MapPoint>();
				for(MapPoint p : dequeue) {
					possiblePoints.add(dequeue.remove());
				}
				
				pathfind(map, possiblePoints);
				
				for(MapPoint p : possiblePoints) {
					if(p.character() != 'e') {
						data[p.row()][p.col()] = '+';
					}
				}
				break;
			}
			
		}
		
		return map;
	}
	
	
	
	public static void pathfind(char[][] map, ArrayList<MapPoint> possiblePoints) {
		ArrayList<MapPoint> countedPoints = new ArrayList<MapPoint>();
		
		//System.out.println(possiblePoints);
		
		MapPoint point = null;
		for(MapPoint p : possiblePoints) {
			if(p.character() == 'e') {
				point = p;
				point.setDistFromKirby(0);
				countedPoints.add(point);
			}
			
			//dequeuePoints.add(possiblePoints.remove());
		}
		
		while(possiblePoints.size() != 0) {
			//System.out.println("Current Point: " + point);
			int row = point.row();
			int col = point.col();
			
			for(MapPoint p : possiblePoints) {
				//System.out.println("checking: " + p);
				if(p.row() == row + 1 && p.col() == col) {
					if(p.getDistFromKirby() > point.getDistFromKirby() + 1) {
						p.setDistFromKirby(point.getDistFromKirby()+1);
						countedPoints.add(p);
					}
				}
				
				if(p.row() == row - 1 && p.col() == col) {
					if(p.getDistFromKirby() > point.getDistFromKirby() + 1) {
						p.setDistFromKirby(point.getDistFromKirby()+1);
						countedPoints.add(p);
					}
				}
				
				if(p.row() == row && p.col() == col - 1) {
					if(p.getDistFromKirby() > point.getDistFromKirby() + 1) {
						p.setDistFromKirby(point.getDistFromKirby()+1);
						countedPoints.add(p);
					}
				}
				
				if(p.row() == row && p.col() == col + 1) {
					if(p.getDistFromKirby() > point.getDistFromKirby() + 1) {
						p.setDistFromKirby(point.getDistFromKirby()+1);
						countedPoints.add(p);
					}
				}
				
				//System.out.println("Dist for p: " + p.getDistFromKirby());
			}
			
			point = possiblePoints.remove(0);
		}
		//System.out.println("last counted point");
		//System.out.println(countedPoints.get(countedPoints.size()-1));
		
		/// SET CURRENT POINT TO CAKE
		for(MapPoint p : countedPoints) {
			if(p.character() == 'p') {
				point = p;
				break;
			}
		}
		
		while(true) {
			//System.out.println("Current Point: " + point);
			int row = point.row();
			int col = point.col();
			
			for(MapPoint p : countedPoints) {
				//System.out.println("checking: " + p);
				if(p.row() == row + 1 && p.col() == col) {
					if(p.getDistFromKirby() < point.getDistFromKirby()) {
						point = p;
						possiblePoints.add(p);
						continue;
					}
				}
				
				if(p.row() == row - 1 && p.col() == col) {
					if(p.getDistFromKirby() < point.getDistFromKirby()) {
						point = p;
						possiblePoints.add(p);
						continue;
					}
				}
				
				if(p.row() == row && p.col() == col - 1) {
					if(p.getDistFromKirby() < point.getDistFromKirby()) {
						point = p;
						possiblePoints.add(p);
						continue;
					}
				}
				
				if(p.row() == row && p.col() == col + 1) {
					if(p.getDistFromKirby() < point.getDistFromKirby()) {
						point = p;
						possiblePoints.add(p);
						continue;
					}
				}
				
				
				if(point.character() == 'e') {
					break;
				}
				
			}
			if(point.character() == 'e') {
				break;
			}
		}
	}
}
