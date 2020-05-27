package driver;

public class MapPoint {
	private char character;
	private int row;
	private int col;
	private double distFromKirby;
	private double distFromCake; 
	
	public MapPoint(int row, int col, char character) {
		this.row = row;
		this.col = col;
		this.character = character;
		distFromKirby = Integer.MAX_VALUE;
	}
	
	public int row() {
		return row;
	}
	
	public int col() {
		return col;
	}
	
	public char character() {
		return character;
	}
	
	public void setDistFromKirby(double dist) {
		distFromKirby = dist;
	}
	
	public void setDistFromCake(double dist) {
		distFromCake = dist;
	}
	
	public double getDistFromKirby() {
		return distFromKirby;
	}
	
	public double getDistFromCake() {
		return distFromCake;
	}
	
	public String toString() {
		return character + " r: " + row + " c: " + col;
	}
}
