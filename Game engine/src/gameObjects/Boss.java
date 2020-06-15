package gameObjects;

import java.awt.Dimension;

import fileIO.ImageLoader;

public class Boss extends Enemy{
	public Boss(int x, int y, boolean isjar, double ratio) {
		super(x, y, 500, new Dimension(100, 100), ImageLoader.WSBSKIN, isjar, ratio);
	}
}
