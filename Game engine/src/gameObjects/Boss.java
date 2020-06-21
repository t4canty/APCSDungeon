package gameObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileWriter;

import displayComponents.Startup;
import fileIO.ImageLoader;

public class Boss extends Enemy{
	public Boss(int x, int y, boolean isjar, double ratio) {
		super(x, y, 1000, new Dimension(200, 200), ImageLoader.WSBSKIN, isjar, ratio, new Gun(10, 100, 30, 10, 30, Gun.FEDRESERVE, 5, "Federal Reserve", isjar, ratio));
		movementSpeed = 6;
	}
	public void onDeath() {
		new ImageLoader().unloadWSB();
		try {
			File f = new File(Startup.UnlockPath);
			if(!f.exists()) {
				FileWriter fw = new FileWriter(f);
				fw.write("1");
				fw.close();
			}
		}catch (Exception e) {
			System.err.println("Error writing to unlock file.");
			e.printStackTrace();
		}
	}
	@Override 
	protected void drawGun(Graphics2D g2d){
		g2d.rotate(gunAngle, rBox.getCenterX(), rBox.getCenterY());
		if(Math.abs(gunAngle) > 1.07) {
			g2d.drawImage(activeGun.getSprite(Player.WSB + 1, hp), (int)(rBox.getCenterX()) + 10, (int)(rBox.getCenterY()) + 20, (int)((50) * sFactor), (int)((50) * sFactor), null);
		}else {
			g2d.drawImage(activeGun.getSprite(Player.WSB + 1, hp), (int)(rBox.getCenterX()) + 10, (int)(rBox.getCenterY()) - 20, (int)(50 * sFactor), (int)(50 * sFactor), null);
		}
		if(debug) g2d.drawLine((int)(rBox.getCenterX()), (int)(rBox.getCenterY()), (int)(rBox.getCenterX() + 100), (int)(rBox.getCenterY()));
		g2d.rotate(-gunAngle, rBox.getCenterX(), rBox.getCenterY());
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.red);
		double width = hp/(double)1000;
		width *= rBox.width;
		g.fillRect(rBox.x, rBox.y - 50, (int)(width * sFactor), (int)(20 * sFactor));
		super.paint(g);
	}
}
