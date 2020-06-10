package gameObjects;

import java.awt.image.BufferedImage;

/**
 * Gun magazine Loot object.
 * Created May 28, 2020
 * @author TJ178
 * @author t4canty
 *
 */

public class AmmoMag extends Loot {
	
	public AmmoMag(int ammoAmount, BufferedImage sprite) {
		number = ammoAmount;
		BufferedImage tmp[] = {sprite};
		this.Sprite = tmp;
		this.id = Loot.PISTOLMAG;
	}

	@Override
	public void use(Player p) {
		p.addAmmo(number);
	}
}
