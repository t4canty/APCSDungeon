package apcs.apcsdungeon.displaycomponents;

import apcs.apcsdungeon.driver.Driver;
import apcs.apcsdungeon.fileio.ImageLoader;
import apcs.apcsdungeon.fileio.SoundLoader;
import apcs.apcsdungeon.gameobjects.Player;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Created May 30, 2020
 *
 * @author t4canty
 * @author TJ178
 * Class for an animated startup screen that also spawns the ImageLoader Thread
 */

public class LinuxStartup extends JPanel implements ActionListener {
	//========Varibles========/
	private boolean debug;
	private boolean maxed = false;
	private boolean animationFinished = false;
	private boolean isJar;
	private boolean wsbUnlock = false;
	private boolean secretUnlock = false;
	private int id = Player.MARINE;
	private int alpha = 255;
	private float a2 = 0.0f;
	private Dimension d;
	private String t;
	private JFrame f;
	private ImageLoader i;
	private SoundLoader s;
	private JButton start;
	private JLabel pictureLabel;
	private JLabel spriteLabel;
	private AnimatedImage sprite;
	private JPanel selectPanel;
	private JPanel anotherFuckingPanelJustForButtons;
	private JRadioButton marine;
	private JRadioButton wsb;
	private JRadioButton secret;
	private Image logo;
	private Image bgImg;
	private Image MarineSplash;
	private Image WSBSplash;
	private Image SecretSplash;
	private ImageIcon currentIcon;
	private LinuxLoadingBar loadingBar;
	private long startTime;
	private double ratio;
	private Font font;

	//========Constructors========//
	public LinuxStartup(Dimension bounds, String title, boolean debug, boolean isJar, String path) {

		//====Pre-Setup====//
		f = new JFrame("Startup");
		System.setProperty("sun.java2d.opengl", "true");
		setDoubleBuffered(false);
		ratio = bounds.height / (double) 800;
		try { // Load images before ImageLoader
			MarineSplash = ImageIO.read((Startup.class.getResourceAsStream("/img/MarineSplash.png")));
			MarineSplash = MarineSplash.getScaledInstance((int) (MarineSplash.getWidth(null) * (0.346 * ratio)), (int) (MarineSplash.getHeight(null) * (0.346 * ratio)), Image.SCALE_SMOOTH);
			WSBSplash = ImageIO.read((Startup.class.getResourceAsStream("/img/WSBSplash.png")));
			WSBSplash = WSBSplash.getScaledInstance((int) (WSBSplash.getWidth(null) * (0.28 * ratio)), (int) (WSBSplash.getHeight(null) * (0.28 * ratio)), Image.SCALE_SMOOTH);
			SecretSplash = ImageIO.read((Startup.class.getResourceAsStream("/img/secret_splash.png")));
			SecretSplash = SecretSplash.getScaledInstance((int) (SecretSplash.getWidth(null) * (ratio * 0.425)), (int) (SecretSplash.getHeight(null) * (0.425 * ratio)), Image.SCALE_SMOOTH);
			logo = ImageIO.read((Startup.class.getResourceAsStream("/img/gameLogo.png")));
			bgImg = ImageIO.read((Startup.class.getResourceAsStream("/img/bg_blur.png")));
			InputStream is = Startup.class.getResourceAsStream("/fonts/ARCADECLASSIC.TTF");
			font = Font.createFont(Font.TRUETYPE_FONT, is);
			readUnlock(new File(path + "unlocks.txt"));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
			f.dispose();
		}
		startTime = System.currentTimeMillis();

		s = new SoundLoader(); // Start SoundLoader thread
		s.start(isJar, debug);

		i = new ImageLoader(); // Start ImageLoader thread
		i.start(isJar, debug);

		currentIcon = new ImageIcon(MarineSplash);
		//====Setup====//

		this.debug = debug;

		d = bounds;
		t = title;
		selectPanel = new JPanel(new BorderLayout(), false); // for fucks sake, 4 HOURS of work to find out all I needed to do was disable double buffering
		anotherFuckingPanelJustForButtons = new JPanel(false);
		anotherFuckingPanelJustForButtons.setBackground(new Color(0, 0, 0, 0));
		marine = new JRadioButton("Marine");
		wsb = new JRadioButton("WSB");
		secret = new JRadioButton("Secret");
		start = new JButton("Start");
		pictureLabel = new JLabel(currentIcon);
		spriteLabel = new JLabel(new ImageIcon());
		ButtonGroup bg = new ButtonGroup();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} // Replace later with custom buttons - but for now better than the ugly default
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
			f.dispose();
		}

		//====Component setup====//
		f.setResizable(false);
		f.setSize(bounds);
		this.setBackground(new Color(255, 255, 255, 50));

		start.setFont(font.deriveFont(40f));
		marine.setFont(font.deriveFont(40f));
		wsb.setFont(font.deriveFont(40f));
		secret.setFont(font.deriveFont(40f));
		pictureLabel.setFont(font.deriveFont(40f));
		start.setActionCommand("l");
		start.addActionListener(this);
		start.setEnabled(false);
		marine.setActionCommand("m");
		marine.addActionListener(this);
		wsb.setActionCommand("w");
		wsb.addActionListener(this);
		secret.setActionCommand("s");
		secret.addActionListener(this);
		secret.setEnabled(false);
		wsb.setEnabled(false);
		marine.setEnabled(false);


		bg.add(marine);
		bg.add(wsb);
		bg.add(secret);

		anotherFuckingPanelJustForButtons.setLayout(new GridLayout(1, 3));
		anotherFuckingPanelJustForButtons.add(marine);
		anotherFuckingPanelJustForButtons.add(wsb);
		anotherFuckingPanelJustForButtons.add(secret);

		selectPanel.setOpaque(false);
		selectPanel.setBackground(new Color(0, 255, 0, 0));
		selectPanel.add(pictureLabel, BorderLayout.LINE_START);
		selectPanel.add(spriteLabel, BorderLayout.LINE_END);
		selectPanel.add(anotherFuckingPanelJustForButtons, BorderLayout.PAGE_END);

		loadingBar = new LinuxLoadingBar(0, 0, new Dimension(f.getWidth(), 20), Color.GRAY, 0, ImageLoader.totalNumberToLoad + SoundLoader.totalNumberToLoad, 0);

		this.setBackground(new Color(0, 0, 0, 40));
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.add(selectPanel);
		this.add(start, BorderLayout.PAGE_END);

		f.add(this);
		f.setBackground(Color.black);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Timer t = new Timer(30, this);
		t.start();
	}

	//========Methods========//
//	@Override
//	public void paintComponent(Graphics g) {
//		this.setBackground(new Color(0f, 0f, 0f, 0.2f ));
//		g.drawImage(bgImg, 0, 0, this.getWidth(), this.getHeight(), null);
//	}

	@Override
	public void paintComponents(Graphics g) {
		this.setBackground(new Color(0f, 0f, 0f, 0.2f));
		g.drawImage(bgImg, 0, 0, this.getWidth(), this.getHeight(), null);
		super.paintComponents(g);
	}

	@Override
	public void paint(Graphics g) {
		paintComponents(g);
		g.setColor(new Color(0, 0, 0, alpha));
		g.fillRect(0, 0, this.getWidth(), this.getHeight()); // Animated black screen

		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a2);//Animated logo
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(ac);
		g2d.drawImage(logo, this.getWidth() / 2 - logo.getWidth(null) / 2, this.getHeight() / 2 - logo.getHeight(null) / 2, null);
		if (i.isAlive() || s.isAlive()) {
			Rectangle r = loadingBar.paint();
			g.setColor(loadingBar.getColor());
			g.fillRect(r.x, r.y, r.width, r.height);

		} else if (!(i.isAlive() || s.isAlive()) && !animationFinished) {
			Rectangle r = loadingBar.paint();
			g.setColor(new Color(0, 150, 0));
			g.fillRect(r.x, r.y, r.width, r.height);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		boolean doneLoading = !i.isAlive() && !s.isAlive();
		if (!animationFinished) { // Function to fade out logo
			if (a2 < 1 && !maxed)
				a2 += 0.01f;
			else if (a2 >= 1) {
				if (!doneLoading)
					a2 = 1f;
				else {
					maxed = true;
					a2 -= 0.01;
				}
			} else if (a2 > 0.1 && maxed) {
				a2 -= 0.015;
			}

			if (a2 < 0.1 && maxed) {
				animationFinished = true;
				a2 = 0f;
			}
		}
		if (a2 > 1) {
			a2 = (float) 1;
		} // Error correction

		if (animationFinished && alpha == 0) { // Re-enable buttons after the animation completes
			secret.setEnabled(true);
			wsb.setEnabled(true);
			marine.setEnabled(true);
			start.setEnabled(true);
		}

		loadingBar.setValue(ImageLoader.totalNumberLoaded + SoundLoader.totalNumberLoaded);

		if (doneLoading && animationFinished && alpha == 255) {
			sprite = new AnimatedImage(ImageLoader.MARINE_STARTUP);
			if (debug)
				System.out.println("Game finished loading.Took " + (System.currentTimeMillis() - startTime) + "ms");
		}

		if (alpha > 0 && animationFinished) alpha /= 1.2;

		if (doneLoading && animationFinished) {
			spriteLabel.setIcon(new ImageIcon(sprite.getCurrentFrame().getScaledInstance((int) (sprite.getCurrentFrame().getHeight(null) * ratio), (int) (sprite.getCurrentFrame().getHeight(null) * ratio), Image.SCALE_SMOOTH)));
			sprite.advanceCurrentFrame();
		}


		//==Buttons==//
		if (e.getActionCommand() != null) {
			switch (e.getActionCommand()) {
				case "l":
					if (debug) System.out.println("Creating Driver");
					i.unloadImages(id);
					new Driver(d, t, debug, id, isJar, font);
					f.dispose();
					break;
				case "m":
					if (debug) System.out.println("Selected marine");
					id = Player.MARINE;
					pictureLabel.setText("");
					currentIcon = new ImageIcon(MarineSplash);
					sprite = new AnimatedImage(ImageLoader.MARINE_STARTUP);
					pictureLabel.setIcon(currentIcon);
					break;
				case "w":
					if (debug) System.out.println("Selected WSB");
					if (wsbUnlock) {
						pictureLabel.setText("");
						currentIcon = new ImageIcon(WSBSplash);
						sprite = new AnimatedImage(ImageLoader.WSB_STARTUP);
						pictureLabel.setIcon(currentIcon);
						id = Player.WSB;
					} else {
						pictureLabel.setIcon(null);
						sprite = new AnimatedImage(ImageLoader.NPC_STARTUP);
						pictureLabel.setText("Not unlocked");
					}
					break;
				case "s":
					if (debug) System.out.println("Selected Secret");
					if (secretUnlock) {
						pictureLabel.setText("");
						currentIcon = new ImageIcon(SecretSplash);
						sprite = new AnimatedImage(ImageLoader.SECRET_STARTUP);
						pictureLabel.setIcon(currentIcon);
						selectPanel.revalidate();
						id = Player.SECRET;
					} else {
						pictureLabel.setIcon(null);
						sprite = new AnimatedImage(ImageLoader.NPC_STARTUP);
						pictureLabel.setText("Not unlocked");
					}
			}
		}
	}

	private void readUnlock(File f) throws IOException { // Private method to read the unlock file.
		if (f.exists()) {
			Scanner s = new Scanner(f);
			String u = s.nextLine();
			if (u.equals("1")) wsbUnlock = true;
			else if (u.equals("2")) {
				wsbUnlock = true;
				secretUnlock = true;
			}
			s.close();
		}
	}

	private static class LinuxLoadingBar {
		public int currentValue;
		private int maxValue;
		private int minValue;
		private double currentPercent;

		private Color barColor;

		private int xCoord;
		private int yCoord;
		private int width;
		private Rectangle outline;

		public LinuxLoadingBar(int x, int y, Dimension bounds, Color barColor, int min, int max, int value) {
			xCoord = x;
			yCoord = y;
			width = bounds.width;
			outline = new Rectangle(bounds);
			outline.x = x;
			outline.y = y;

			this.barColor = barColor;
			minValue = min;
			maxValue = max;
			currentValue = value;
		}

		public Rectangle paint() {
			int max = maxValue - minValue;
			int current = (currentValue - minValue);
			currentPercent = current * width / max;
			currentPercent /= width;
			return new Rectangle(xCoord, yCoord, (int) (currentPercent * width), outline.height);

		}

		public void setValue(int v) {
			currentValue = v;
		}

		public Color getColor() {
			return barColor;
		}

		public void setColor(Color c) {
			barColor = c;
		}
	}
}
