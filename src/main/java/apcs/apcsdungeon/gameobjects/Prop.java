package apcs.apcsdungeon.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Class for objects that don't do anything, but have a hitbox and can collide in the world.
 * Comments added on Jun 10, 2020
 *
 * @author t4canty
 * @author TJ178
 */
public class Prop extends GameObject {
	//========Constructors========//

	/**
	 * Constructs a prop object that has a hitbox and can be drawn.
	 *
	 * @param x
	 * @param y
	 * @param sprite
	 */
	public Prop(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.rBox = new Rectangle(x, y, sprite.getWidth(null), sprite.getHeight(null));
		hp = 1;

		initializeTexture(sprite);
	}

	//========Methods========//
	@Override
	public void paint(Graphics g) {
		g.drawImage(texture.getCurrentFrame(), x, y, null);
	}

	@Override
	public void advanceAnimationFrame() {
	}

	private void initializeTexture(BufferedImage sprite) {
		texture = new Texture();
		String defaultAnimationName = "default";
		texture.addAnimation(defaultAnimationName, new Animation(sprite, false,
				true));
		texture.setDefaultAnimation(defaultAnimationName);
	}
}
