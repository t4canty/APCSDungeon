package gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Prop extends GameObject{
	protected Image Sprite;
	public Prop(int x, int y, Image sprite) {
		this.x = x;
		this.y = y;
		this.Sprite = sprite;
		this.rBox = new Rectangle(x, y, sprite.getWidth(null), sprite.getHeight(null));
		hp = 1;
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(Sprite, x, y,null);
	}

	@Override
	public void advanceAnimationFrame() {
		// TODO Auto-generated method stub
		
	}

}
