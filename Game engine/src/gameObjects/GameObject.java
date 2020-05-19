package gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * This class is meant to hold the global position of every game object - so excluding text boxes, ui elements, etc.
 * There are 4 types of objects in the game:
 * 1) Enemy - anything that triggers a 'battle' event, or damages player.
 * 2) Player - controlled by the player.
 * 3) Npc - similar to objects, except they contain dialogue. (Note: chests or other objects that require interacting are considered npcs.)
 * 4) Wall/object - static objects that just have collision.
 * @author t54cs
 */
public abstract class GameObject {
	protected Rectangle rBox;
	protected int x, y;
	protected abstract void paint(Graphics g);
}
