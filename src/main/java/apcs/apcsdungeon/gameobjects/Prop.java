package apcs.apcsdungeon.gameobjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 * Class for objects that don't do anything, but have a hitbox and can collide in the world.
 * Comments added on Jun 10, 2020
 *
 * @author t4canty
 * @author TJ178
 */
public class Prop extends GameObject {
	//========Variables========//
	protected Image Sprite;

	//========Constructors========//

	/**
	 * Constructs a prop object that has a hitbox and can be drawn.
	 *
	 * @param x
	 * @param y
	 * @param sprite
	 */
	public Prop(int x, int y, Image sprite) {
		this.x = x;
		this.y = y;
		this.Sprite = sprite;
		this.rBox = new Rectangle(x, y, sprite.getWidth(null), sprite.getHeight(null));
		hp = 1;
	}

	//========Methods========//
	@Override
	public void paint(Graphics g) {
		g.drawImage(Sprite, x, y, null);
	}

	@Override
	public void advanceAnimationFrame() {
	}
}
