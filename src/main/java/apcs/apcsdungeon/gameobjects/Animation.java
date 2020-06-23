package apcs.apcsdungeon.gameobjects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * An animation stores all of its frames in a list, and will cycle through them using {@link #advanceFrame()}. If
 * the animation reached its end and the "one time animation" flag is set to true, the animation will stop being
 * able to cycle through the frames and will set the "finished" flag. The texture this animation belongs to will
 * use this flag to reset this animation using the {@link #reset()} method and switch back to the default animation.
 *
 * @author VTHMgNPipola
 */
public class Animation {
	private final List<BufferedImage> frames;
	private int index;
	private final boolean oneTimeAnimation;
	private final boolean staticFrame;
	private boolean finished;

	/**
	 * Creates a new animation, but with the ability to define this animation as being static. A static animation
	 * can't cycle through frames, since the only frame is the source itself. Calling {@link #advanceFrame()} does
	 * nothing.
	 *
	 * @param source           Source sprite sheet.
	 * @param oneTimeAnimation "One time animation" flag. If true, this animation will automatically swapped by the
	 *                         default animation when it ends. If false, this animation will run indefinitely.
	 * @param staticFrame      "Static frame" flag. If true, the source image will not be sliced, and instead put as one
	 *                         single frame to be displayed. Because of that, calling {@link #advanceFrame()} does
	 *                         nothing, as it is impossible to cycle through frames.
	 */
	public Animation(BufferedImage source, boolean oneTimeAnimation, boolean staticFrame) {
		frames = new ArrayList<>();
		if (staticFrame) {
			frames.add(source);
		} else {
			int sourceWidth = source.getWidth();
			int numFrames = source.getHeight() / sourceWidth;
			for (int i = 0; i < numFrames; i++) {
				frames.add(source.getSubimage(0, i * sourceWidth, sourceWidth, sourceWidth));
			}
		}
		this.staticFrame = staticFrame;
		this.oneTimeAnimation = oneTimeAnimation;
		index = 0;
		finished = false;
	}

	/**
	 * Creates a new animation by slicing a sprite sheet.
	 * <p>
	 * This will fail if the source image height isn't a multiple of the width.
	 *
	 * @param source           Source sprite sheet.
	 * @param oneTimeAnimation "One time animation" flag. If true, this animation will automatically swapped by the
	 *                         default animation when it ends. If false, this animation will run indefinitely.
	 */
	public Animation(BufferedImage source, boolean oneTimeAnimation) {
		this(source, oneTimeAnimation, false);
	}

	/**
	 * The same as {@link #Animation(BufferedImage, boolean)}, but the "one time animation" flag is automatically set
	 * to false.
	 *
	 * @param source Source sprite sheet.
	 */
	public Animation(BufferedImage source) {
		this(source, false);
	}

	/**
	 * Recreates this animation, by slicing a new sprite sheet. The old frames will be discarded, and replaced by the
	 * ones generated from the new sprite sheet.
	 *
	 * @param source Source sprite sheet to be sliced and replace the previous frames.
	 */
	public void recreateAnimation(BufferedImage source) {
		frames.clear();
		if (staticFrame) {
			frames.add(source);
		} else {
			int sourceWidth = source.getWidth();
			int numFrames = source.getHeight() / sourceWidth;
			for (int i = 0; i < numFrames; i++) {
				frames.add(source.getSubimage(0, i * sourceWidth, sourceWidth, sourceWidth));
			}
		}
	}

	/**
	 * Advances the current frame by 1, or set the "finished" flag.
	 */
	public void advanceFrame() {
		if (index == frames.size() - 1) {
			if (oneTimeAnimation) {
				finished = true;
			} else {
				index = 0;
			}
		} else {
			index++;
		}
	}

	/**
	 * @return The currently selected frame.
	 */
	public BufferedImage getCurrentFrame() {
		return frames.get(index);
	}

	public boolean isFinished() {
		return finished;
	}

	/**
	 * Switches the current frame back to 0 and resets the "finished" flag.
	 */
	public void reset() {
		index = 0;
		finished = false;
	}
}
