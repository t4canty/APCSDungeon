package apcs.apcsdungeon.gameobjects;

import java.awt.image.BufferedImage;

/**
 * Gun magazine Loot object.
 * Created May 28, 2020
 *
 * @author TJ178
 * @author t4canty
 */

public class AmmoMag extends Loot {

	public AmmoMag(int ammoAmount, BufferedImage sprite) {
		number = ammoAmount;
		this.Sprite = new BufferedImage[]{sprite};
		this.id = Loot.PISTOLMAG;
	}

	@Override
	public void use(Player p) {
		p.addAmmo(number);
	}

	@Override
	public BufferedImage getSprite(int n, int hp) {
		return super.getSprite(n);
	}
}
