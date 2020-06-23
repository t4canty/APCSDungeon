package apcs.apcsdungeon.gameobjects;

import apcs.apcsdungeon.io.ImageLoader;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basic Loot container class to represent an item on the ground
 * Created May 28, 2020
 *
 * @author TJ178
 * @author t4canty
 */
public class DroppedItem extends GameObject {
	private static final Logger logger = LoggerFactory.getLogger(DroppedItem.class);

	//========Variables========//
	private Loot item;
	private boolean isEnemy;
	private int graphicsDir;

	//========Constructors========//

	/**
	 * Constructor for a dropped item.
	 *
	 * @param x    X position on map
	 * @param y    Y position on map
	 * @param item Item that this entity contains
	 * @param size Size of icon and hitbox to collect loot
	 */
	public DroppedItem(int x, int y, Loot item, int size) {
		this.x = x;
		this.y = y;
		this.item = item;
		rBox = new Rectangle(new Dimension(size, size));
		rBox.x = x;
		rBox.y = y;
		isEnemy = false;
	}

	public DroppedItem(Enemy e) {
		this.x = e.getX();
		this.y = e.getY();
		this.item = e.getDrop();
		this.rBox = e.getHitbox();
		graphicsDir = e.getDir();
		initializeTexture(e);
		isEnemy = true;
	}

	private void initializeTexture(Enemy e) {
		if (e instanceof Boss) {
			String wsbDeathName = "wsb_death";
			texture = Texture.getTexture(wsbDeathName);
			if (texture == null) {
				texture = Texture.createTexture(wsbDeathName);
				texture.addAnimation(FRONT_DEATH, new Animation(ImageLoader.WSB_FRONTDEATH));
				texture.addAnimation(SIDE_DEATH, new Animation(ImageLoader.WSB_SIDEDEATH));
				texture.addAnimation(BACK_DEATH, new Animation(ImageLoader.WSB_BACKDEATH));
			}
		} else {
			String npcDeathName = "npc_death";
			texture = Texture.getTexture(npcDeathName);
			if (texture == null) {
				texture = Texture.createTexture(npcDeathName);
				texture.addAnimation(FRONT_DEATH, new Animation(ImageLoader.NPC_FRONTDEATH));
				texture.addAnimation(SIDE_DEATH, new Animation(ImageLoader.NPC_SIDEDEATH));
				texture.addAnimation(BACK_DEATH, new Animation(ImageLoader.NPC_BACKDEATH));
			}
		}
	}

	//========Methods========//
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (logger.isDebugEnabled()) g2d.draw(rBox);

		if (!isEnemy) g2d.drawImage(item.Sprite[0], x, y, rBox.height, rBox.width, null);
		else {
			drawSprite(g2d);

			Composite old = g2d.getComposite();
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f); // Animated logo
			g2d.setComposite(ac);
			g2d.drawImage(item.Sprite[0], x, y, rBox.height, rBox.width, null);
			g2d.setComposite(old);
		}
	}

	private void drawSprite(Graphics2D g2d) {
		boolean invertHorizontal = false;
		switch (graphicsDir) {
			case LEFT:
				texture.changeAnimation(SIDE_DEATH);
				break;
			case RIGHT:
				texture.changeAnimation(SIDE_DEATH);
				invertHorizontal = true;
				break;
			case UP:
				texture.changeAnimation(BACK_DEATH);
				break;
			case DOWN:
				texture.changeAnimation(FRONT_DEATH);
		}

		int xx = invertHorizontal ? x + rBox.width : x;
		int width = invertHorizontal ? -rBox.width : rBox.width;
		g2d.drawImage(texture.getCurrentFrame(), xx, y, width, rBox.height,
				null);
	}

	@Override
	public void advanceAnimationFrame() {
		if (isEnemy) {
			texture.advanceAllFrames();
		}
	}

	//========Getter========//
	public Loot getItem() {
		return item;
	}
}
