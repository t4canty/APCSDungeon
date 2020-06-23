package apcs.apcsdungeon.displaycomponents;

import apcs.apcsdungeon.io.ImageLoader;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

/**
 * Created May 30, 2020
 *
 * @author t4canty
 * @author TJ178
 * A class that renders an image from a spritesheet.
 */
public class AnimatedImage {
	//========Variables========//
	private BufferedImage spritesheet;
	private int numFrames = 60;
	private int currentFrame = 1;
	private int currentFrameY = 0;
	private boolean isStatic = false;
	private boolean oneTimeAnimation = false;
	private boolean isFinished = false; // used only with one time animation

	//========Constructors========//

	/**
	 * Constructor for an animated image.
	 *
	 * @param source Source spritesheet to use.
	 */
	public AnimatedImage(BufferedImage source) {
		if (source == null) {
			isStatic = true;
			spritesheet = ImageLoader.NO_IMAGE;
		} else {
			this.numFrames = source.getHeight() / source.getWidth();
			spritesheet = source;
		}
	}

	/**
	 * Constructor for an animated image, with an option to only have the animation run once.
	 *
	 * @param source      Source spritesheet to use.
	 * @param onlyRunOnce Sets whether or not the animation should repeat.
	 */
	public AnimatedImage(BufferedImage source, boolean onlyRunOnce) {
		this(source);
		oneTimeAnimation = onlyRunOnce;
	}

	//========Methods========//

	/**
	 * Advances the frame of the animation by one, returning an image object for each frame.
	 *
	 * @return Returns the next frame of the animation.
	 */
	public Image getNextFrame() {
		if (isStatic) return spritesheet;

		int frameY = currentFrame * spritesheet.getWidth();
		if (!isFinished) currentFrame++;

		if (currentFrame == numFrames - 1) {
			if (oneTimeAnimation) {
				isFinished = true;
			} else {
				currentFrame = 0;
			}
		}

		Image tmp;
		try {
			tmp = spritesheet.getSubimage(0, frameY, spritesheet.getWidth(), spritesheet.getWidth());
		} catch (RasterFormatException e) {
			e.printStackTrace();
			tmp = ImageLoader.NO_IMAGE;
		}
		return tmp;
	}

	/**
	 * Advances the frame of the animation by one.
	 */
	public void advanceCurrentFrame() {
		if (!isFinished) currentFrame++;
		if (currentFrame == numFrames - 1) {
			if (oneTimeAnimation) {
				isFinished = true;
			} else {
				currentFrame = 0;
			}
		}
		currentFrameY = currentFrame * spritesheet.getWidth();
	}

	//========Getters/Setters========//
	public Image getCurrentFrame() {
		if (isStatic) return spritesheet;
		return spritesheet.getSubimage(0, currentFrameY, spritesheet.getWidth(), spritesheet.getWidth());
	}

	public boolean isStatic() {
		return isStatic;
	}
}
