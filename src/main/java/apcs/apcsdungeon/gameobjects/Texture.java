package apcs.apcsdungeon.gameobjects;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * A texture is an object that contains all animations of a game object. An identifier is assigned to each animation,
 * that can be controlled by {@link #advanceFrame()} and {@link #getCurrentFrame()}, and modified using
 * {@link #changeAnimation(String)}.
 * <p>
 * This class works basically the same way that the old "AnimatedImage" worked, but better. Each animation will load
 * a sprite sheet, that it will then subdivide to create each individual frame, that must be a square. Each frame must
 * be directly below the previous one. The operation will fail if the image height isn't a multiple of the image width.
 *
 * @author VTHMgNPipola
 */
public class Texture {
	private static final Map<String, Texture> textures = new HashMap<>();

	private final Map<String, Animation> animations;
	private String defaultAnimation;
	private String selectedAnimation;

	public static Texture createTexture(String name) {
		if (!textures.containsKey(name)) {
			Texture texture = new Texture();
			textures.put(name, texture);
			return texture;
		} else {
			return getTexture(name);
		}
	}

	public static Texture getTexture(String name) {
		return textures.get(name);
	}

	public Texture() {
		animations = new HashMap<>();
	}

	/**
	 * Advances by 1 the current frame of the currently selected animation.
	 */
	public void advanceFrame() {
		animations.get(selectedAnimation).advanceFrame();
	}

	/**
	 * Advaces by 1 the current frame of all animations contained inside this texture.
	 */
	public void advanceAllFrames() {
		animations.forEach((k, v) -> v.advanceFrame());
	}

	/**
	 * @return The current frame of the currently selected animation.
	 */
	public BufferedImage getCurrentFrame() {
		Animation animation = animations.get(selectedAnimation);
		if (animation != null && animation.isFinished()) {
			animation.reset();
			selectedAnimation = defaultAnimation;
		} else if (animation == null) {
			animation = animations.get(defaultAnimation);
		}
		return animation.getCurrentFrame();
	}

	/**
	 * Changes the current animation.
	 *
	 * @param name Name of the animation.
	 */
	public void changeAnimation(String name) {
		selectedAnimation = name;
	}

	/**
	 * Changes the default animation, that will be used when an one time animation ends.
	 *
	 * @param defaultAnimation New one time animation.
	 */
	public void setDefaultAnimation(String defaultAnimation) {
		this.defaultAnimation = defaultAnimation;
	}

	/**
	 * Inserts a new animation to this texture.
	 *
	 * If this is the first animation being added, it will be automatically selected and set as the default animation.
	 *
	 * @param animationName Name of the animation
	 * @param animation     Animation to be added.
	 */
	public void addAnimation(String animationName, Animation animation) {
		if (animations.size() == 0) {
			defaultAnimation = selectedAnimation = animationName;
		}
		animations.put(animationName, animation);
	}

	/**
	 * Inserts a new infinitely running animation to this texture.
	 *
	 * @param animationName Name of the animation.
	 * @param source        Source sprite sheet.
	 */
	public void addAnimation(String animationName, BufferedImage source) {
		addAnimation(animationName, new Animation(source, false));
	}

	/**
	 * @return The currently selected animation.
	 */
	public Animation getCurrentAnimation() {
		return animations.get(selectedAnimation);
	}

	/**
	 * @param name Name of the animation to be retrieved.
	 * @return The animation with the selected name, or null if no animation with the name is found.
	 */
	public Animation getAnimation(String name) {
		return animations.get(name);
	}
}
