package gameObjects;

import java.awt.Image;

/**
 * Class for Health potions.
 * Created May 26, 2020
 * @author t4canty
 * @author TJ178
 *
 */
public class Health extends Loot {
	
	//========Constructor========//
	/**
	 * Health items: when a key is pressed, add heath to the player. Cooldown still applies.
	 * @param damage
	 * Pass in any negative number to add heath to the player.
	 * @param name
	 * Name of the potion
	 * @param Sprite
	 * Sprite of the potion
	 * @param cooldown
	 * Cooldown time of the potion in ms.
	 */
	public Health(int hp, String name, Image Sprite, int cooldown) {
		this.number = hp;
		this.Name = name;
		this.Sprite = Sprite;
		this.cooldown = cooldown;
		this.id = Loot.HEALTHPACK; //Special ID reserved for health potions
	}

	@Override
	public void use(Player p) {
		p.hp += number;
	}
}
