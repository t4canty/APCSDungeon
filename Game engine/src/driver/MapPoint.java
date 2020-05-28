package driver;

public class MapPoint {
	private byte data;
	private int row;
	private int col;
	private int f;
	private MapPoint parent;
	
	public MapPoint(int row, int col, byte data) {
		this.row = row;
		this.col = col;
		this.data = data;
		parent = null;
		f = Integer.MAX_VALUE;
	}
	
	
	public MapPoint(int row, int col, byte data, MapPoint parent, int destRow, int destCol, int cost) {
		this.row = row;
		this.col = col;
		this.data = data;
		this.parent = parent;
		int g = parent.getFCost() + cost;
		f = (int)(g + Math.sqrt(Math.abs(destRow - row) + Math.abs(destCol - col)));
	}
	
	public int row() {
		return row;
	}
	
	public int col() {
		return col;
	}
	
	public byte data() {
		return data;
	}
	
	public void setData(byte d) {
		data = d;
	}
	
	public void setFCost(int f) {
		this.f = f;
	}
	
	public int calculateFCost(int destRow, int destCol, int cost) {
		int g = parent.getFCost() + cost;
		return (int)(g + Math.sqrt(Math.abs(destRow - row) + Math.abs(destCol - col)));
	}
	
	public void setParent(MapPoint parent) {
		this.parent = parent;
	}
	
	public int getFCost() {
		return f;
	}
	
	public String toString() {
		return data + " r: " + row + " c: " + col;
	}
}
