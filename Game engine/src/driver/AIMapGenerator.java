package driver;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

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
	
	final static public byte NULL = -1;
	final static public byte EMPTY = 0;
	final static public byte WALL = 1;
	final static public byte PLAYER = 2;
	final static public byte ENEMY = 3;
	final static public byte P_PROJECTILE = 4;
	final static public byte PATH = 5;
	
	//costs for determining g cost function for A*
	final static public byte HORIZONTALCOST = 10;
	final static public byte DIAGONALCOST = 14;
	
	
	//creates the 64x64 bytemap that is used to pathfind from the screen size
	public static byte[][] generateMap(Player player, Room room, int screenSize){
		int scannerSize = screenSize / 64;
		
		byte[][] map = new byte[64][64];
		for(byte[] i : map) {
			for(byte j : i) {
				j = NULL;
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
					map[i][j] = PLAYER;
					playerFound = true;
					continue;
				}
				
				if(!bounds.contains(scanner)) {
					map[i][j] = WALL;
					continue;
				}
				for(GameObject e : entities) {
					if(scanner.x + scannerSize > e.getX() && scanner.x - scannerSize/2 < e.getX()) {
						if(scanner.y + scannerSize > e.getY() && scanner.y - scannerSize/2 < e.getY()) {
							if(scanner.intersects(e.getHitbox())){
								if(e instanceof Enemy) {
									map[i][j] = ENEMY;
									System.out.println("x:" + i + " y:" + j);
								}else if(e instanceof Projectile && !((Projectile)e).isEnemyFire()) {
									map[i][j] = P_PROJECTILE;
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
					map[i][j] = EMPTY;
				}
			}
		}
		return map;
	}
	
	
	public static byte[][] pathfind(Enemy e, Player p, byte[][] map) {
		MapPoint[][] nodeMap = new MapPoint[64][64];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				nodeMap[i][j] = new MapPoint(i, j, map[i][j]);
			}
		}
		System.out.println(e.getX() / (800/64));
		
		calculate(nodeMap, new MapPoint(e.getX() / (800 / 64), e.getY() / (800 / 64), ENEMY), new MapPoint(p.getX() / (800 / 64), p.getY() / (800 / 64), PLAYER));
		
		for(MapPoint[] f : nodeMap) {
			for(MapPoint j : f) {
				map[j.row()][j.col()] = j.data();
			}
		}
		return map;
	}
	
	
	
	
	
	public static MapPoint[][] calculate(MapPoint[][] data, MapPoint startPoint, MapPoint endPoint) {
		PriorityQueue<MapPoint> openList = initQueue();		//holds all open spaces seen
		ArrayDeque<MapPoint> closedList = new ArrayDeque<MapPoint>();		//holds all spaces that have been visited
		openList.add(startPoint);
		
		boolean[][] seen = new boolean[64][64];
		for(boolean[] i : seen) {
			for(boolean j : i) {
				j = false;
			}
		}
		
		boolean playerFound = false;
		
		while(openList.size() != 0 && !playerFound){
			MapPoint current = openList.remove();
			MapPoint[] successors = getSuccessors(data, current, endPoint);
			System.out.println(current);
			
			for(int i = 0; i < successors.length; i++) {
				if(successors[i] != null && successors[i].data() != WALL) {
					if(successors[i].data() == PLAYER) {
						System.out.println("found player");
						playerFound = true;
						break;
					}
					int f = successors[i].calculateFCost(endPoint.row(), endPoint.col(), HORIZONTALCOST);
					
					if(openList.contains(successors[i]) && successors[i].getFCost() < f) {
						continue;
					}
					
					if(closedList.contains(successors[i]) && successors[i].getFCost() < f) {
						continue;
					}
					
					successors[i].setFCost(f);
					openList.add(successors[i]);
				}
			}
			
			closedList.add(current);
		}
		
		for(MapPoint p : closedList) {
			data[p.row()][p.col()].setData(PATH);
		}
		return data;
	}
	
	private static MapPoint[] getSuccessors(MapPoint[][] data, MapPoint p, MapPoint dest) {
		MapPoint[] successors = new MapPoint[8];
		if(p.row() > 0) {	//top row
			if(p.col() > 0) {	//top left
				successors[0] = data[p.row()-1][p.col()-1];
				successors[0].setParent(p);
			}
			//top middle
			successors[1] = data[p.row()-1][p.col()];
			successors[1].setParent(p);
			
			if(p.col()+1 < data[0].length) { //top right
				successors[2] = data[p.row()-1][p.col()+1];
				successors[2].setParent(p);
			}
		}
		
		if(p.col() > 0) {	//middle left
			successors[3] = data[p.row()][p.col()-1];
			successors[3].setParent(p);
		}
		if(p.col()+1 < data[0].length) { //middle right
			successors[4] = data[p.row()][p.col()+1];
			successors[4].setParent(p);
		}
		
		if(p.row()+1 < data.length) {	//bottom row
			if(p.col() > 0) {	//bottom left
				successors[5] = data[p.row()+1][p.col()-1];
				successors[5].setParent(p);
			}
			//bottom middle
			successors[6] = data[p.row()+1][p.col()];
			successors[6].setParent(p);
			
			if(p.col()+1 < data[0].length) { //bottom right
				successors[7] = data[p.row()+1][p.col()+1];
				successors[7].setParent(p);
			}
		}
		
		return successors;
	}
	
	
	
	//////function used for initializing the priority queue in the A* algo
	
	private static PriorityQueue<MapPoint> initQueue() {
        return new PriorityQueue<MapPoint>(10, new Comparator<MapPoint>() {
            public int compare(MapPoint x, MapPoint y) {
                if (x.getFCost() < y.getFCost()){
                	return -1;
                }
                if (x.getFCost() > y.getFCost())
                {
                    return 1;
                }
                return 0;
            }
        });
    }
		
/*
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
	}*/
}
