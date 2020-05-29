package gameObjects;

import java.awt.Image;

/**
 * Gun magazine Loot object.
 * Created May 28, 2020
 * @author TJ178
 * @author t4canty
 *
 */

public class AmmoMag extends Loot {
	
	public AmmoMag(int ammoAmount, Image sprite) {
		number = ammoAmount;
		this.Sprite = sprite;
		this.id = Loot.PISTOLMAG;
	}

	@Override
	public void use(Player p) {
		p.addAmmo(number);
	}
}
