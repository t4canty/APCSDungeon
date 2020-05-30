package displayComponents;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * Large spaghetti code imported from TJ178/YarisFlight imported from WVSoDA/SpaceBase
 * 
 * @author TJ178
 *
 */


public class StatusBar {
	private int maxValue;
	private int minValue;
	
	public int currentValue;
	private double currentPercent;
	private Rectangle currentBar;
	
	private boolean verticalBar;
	private boolean showMaxMin;
	private boolean changeColor;
	private String label;
	private Color barColor;
	
	private int labelPosition;
	public static int TOP = 1;
	public static int MIDDLE = 0;
	public static int BOTTOM = -1;
	
	private int xCoord;
	private int yCoord;
	private int width;
	private int height;
	private Rectangle outline;
	
	/**
	 * Constructor for an outlined status bar.
	 * @param x
	 * X coordinate
	 * @param y
	 * Y coordinate
	 * @param bounds
	 * Contains width / height
	 * @param barColor
	 * Color of bar if changeColors is false
	 * @param changeColors
	 * Enable the bar to automatically change colors based on value
	 * @param isVertical
	 * Enable if the bar is a vertically read status bar
	 * @param labelPosition
	 * Determines where the label is placed: TOP, MIDDLE, or BOTTOM
	 * @param label
	 * Label of the status bar
	 * @param showLimits
	 * Display the limits of the status bar on the respective ends
	 * @param min
	 * Minimum value of the status bar
	 * @param max
	 * Maximum value of the status bar
	 * @param value
	 * Preset current value for status bar to display
	 */
	
	
	public StatusBar(int x, int y, Dimension bounds, Color barColor, boolean changeColors, boolean isVertical, int labelPosition, String label, boolean showLimits, 
			int min, int max, int value) {
		
		xCoord = x;
		yCoord  = y;
		width = bounds.width;
		height = bounds.height;
		outline = new Rectangle(bounds);
		outline.x = x;
		outline.y = y;
		
		
		this.barColor = barColor;
		this.changeColor = changeColors;
		this.verticalBar = isVertical;
		this.labelPosition = labelPosition;
		this.label = label;
		showMaxMin = showLimits;
		minValue = min;
		maxValue = max;
		currentValue = value;
	}
	
	public void setValue(int v){
		currentValue = v;
	}
	
	public double getValue() {
		return currentValue;
	}


	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		//set the size of the status rectangle
		int max = maxValue - minValue;
		int current = (int) (currentValue - minValue);
		if (!verticalBar){
			currentPercent = current*width/max;
			currentPercent /= width;
			currentBar = new Rectangle(xCoord, yCoord, (int)(currentPercent*width), outline.height);
		}else{
			currentPercent = current*height/max;
			currentPercent /= height;
			currentBar = new Rectangle(xCoord, yCoord, outline.width, (int)(currentPercent*height));
		}
		
		//change the color based on percentage if applicable
		if (changeColor){
			if (currentPercent >= .5){
				g2d.setColor(Color.GREEN);
			}else if ((currentPercent > .25) && (currentPercent < .5)){
				g2d.setColor(Color.YELLOW);
			}else if (currentPercent < .25){
				g2d.setColor(Color.RED);
			}
		}else{
			g2d.setColor(barColor);
		}
		
		//draw the statusbar
		g2d.fill(currentBar);
		g2d.setColor(Color.black);
		
		//draw the label and min/max at correct position, if applicable
		if (labelPosition == TOP && !verticalBar){
			g2d.drawString(label, (xCoord+width/2) - (label.length()*4), yCoord-5);
			if (showMaxMin){
				g2d.drawString(Integer.toString(maxValue), (xCoord+width-20), yCoord-5);
				g2d.drawString(Integer.toString(minValue), xCoord, yCoord-5);
			}
		}else if (!verticalBar && labelPosition == BOTTOM){
			g2d.drawString(label, (xCoord+width/2) - (label.length()*4), yCoord+height+15);
			if (showMaxMin){
				g2d.drawString(Integer.toString(maxValue), (xCoord+width-20), yCoord+height+15);
				g2d.drawString(Integer.toString(minValue), xCoord, yCoord+height+15);
			}
		}else if (labelPosition == MIDDLE && !verticalBar){
			g2d.drawString(label, (xCoord+width/2) - (label.length()*4), (yCoord+height/2)+5);
			if (showMaxMin){
				g2d.drawString(Integer.toString(maxValue), (xCoord+width-20), (yCoord+height/2)+5);
				g2d.drawString(Integer.toString(minValue), xCoord, (yCoord+height/2)+5);
			}
		}else if (verticalBar){
			g2d.drawString(label, (xCoord+width/2)-(label.length()*4), yCoord +height+15);
			if (showMaxMin){
				g2d.drawString(Integer.toString(maxValue), xCoord, yCoord+height-5);
				g2d.drawString(Integer.toString(minValue), xCoord, yCoord+15);
			}
		}
		
		g2d.setColor(Color.black);
		g2d.draw(outline);
	}
}