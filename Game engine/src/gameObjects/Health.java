package gameObjects;

import java.awt.image.BufferedImage;

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
	 * Health items: when a key is pressed, add heath to the player.
	 * @param damage
	 * Pass in any number to add heath to the player.
	 * @param name
	 * Name of the potion
	 * @param Sprite
	 * Sprite of the potion
	 */
	public Health(int hp, String name, BufferedImage Sprite) {
		this.number = hp;
		this.Name = name;
		BufferedImage tmp[] = {Sprite};
		this.Sprite = tmp;
		this.id = Loot.HEALTHPACK; //Special ID reserved for health potions
	}

	@Override
	public void use(Player p) {
		p.hp += number;
	}
}
