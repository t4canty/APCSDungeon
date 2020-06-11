package displayComponents;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
import driver.Driver;
import fileIO.ImageLoader;
import fileIO.SoundLoader;
import gameObjects.Player;

/**
 *	
 * Created May 30, 2020
 * @author t4canty
 * @author TJ178
 * Class for an animated startup screen that also spawns the ImageLoader Thread
 * 
 */

public class Startup extends JPanel implements ActionListener{
	//========Varibles========//
	private boolean debug;
	private boolean maxed = false;
	private boolean animationFinished = false;
	private boolean isJar;
	private boolean wsbUnlock = false;
	private boolean secretUnlok = false;
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
	private StatusBar loadingBar;
	private long startTime;
	
	//========Constructors========//
	public Startup(Dimension bounds, String title, boolean debug, boolean isJar, String path) {
		//====Pre-Setup====//
		//Load images before ImageLoader
		try {
			MarineSplash = ImageIO.read((Startup.class.getResourceAsStream("/img/MarineSplash.png")));
			MarineSplash = MarineSplash.getScaledInstance((int) (MarineSplash.getWidth(null) * 0.346), (int) (MarineSplash.getHeight(null) * 0.346), Image.SCALE_SMOOTH);
			WSBSplash = ImageIO.read((Startup.class.getResourceAsStream("/img/WSBSplash.png")));
			WSBSplash = WSBSplash.getScaledInstance((int) (WSBSplash.getWidth(null) * 0.28), (int) (WSBSplash.getHeight(null) * 0.28), Image.SCALE_SMOOTH);
			logo = ImageIO.read((Startup.class.getResourceAsStream("/img/gameLogo.png")));
			bgImg = ImageIO.read((Startup.class.getResourceAsStream("/img/bg_blur.png")));
			readUnlock(new File(path + "unlocks.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startTime = System.currentTimeMillis();

		s = new SoundLoader();															//Start SoundLoader thread
		s.start(isJar, debug);

		i = new ImageLoader();															//Start ImageLoader thread
		i.start(isJar, debug); 

		currentIcon = new ImageIcon(MarineSplash);

		//====Setup====//
		f = new JFrame("Startup");
		this.debug = debug;
		this.setBackground(new Color(255,255,255,50));
		d = bounds;
		t = title;
		selectPanel = new JPanel();
		selectPanel.setBackground(new Color(255,255,255,50));
		anotherFuckingPanelJustForButtons = new JPanel();
		marine = new JRadioButton("Marine");
		wsb = new JRadioButton("WSB");
		secret = new JRadioButton("Secret");
		start = new JButton("Start");
		pictureLabel = new JLabel(currentIcon);
		spriteLabel = new JLabel(new ImageIcon());
		ButtonGroup bg = new ButtonGroup();
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} 		 //Replace later with custom buttons - but for now better than the ugly default
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e1) {e1.printStackTrace();}

		//====Component setup====//
		f.setResizable(false);
		f.setSize(800, 800);

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
		anotherFuckingPanelJustForButtons.add(secret);;

		selectPanel.setLayout(new BorderLayout());
		selectPanel.add(pictureLabel, BorderLayout.LINE_START);
		selectPanel.add(spriteLabel, BorderLayout.LINE_END);
		selectPanel.add(anotherFuckingPanelJustForButtons, BorderLayout.PAGE_END);


		loadingBar = new StatusBar(0, 0, new Dimension(800, 20), Color.GRAY, false, false, 0, "", false, 0, ImageLoader.totalNumberToLoad + SoundLoader.totalNumberToLoad, 0);
		
		this.setLayout(new BorderLayout());
		this.add(selectPanel);
		this.add(start, BorderLayout.PAGE_END);
		
		
		f.add(this);
		f.setBackground(Color.green);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Timer t = new Timer(30, this);
		t.start();
	}

	//========Methods========//

	@Override
	public void paintComponents(Graphics g) {
		g.drawImage(bgImg, 0, 0, f.getWidth(), f.getHeight(), null);
		g.setColor(new Color(0, 0, 0, 100));
		g.drawRect(0, 0, f.getWidth(), f.getHeight());
		anotherFuckingPanelJustForButtons.setBackground(new Color(0, 0, 0, 0));
		marine.setBackground(new Color(200, 200, 200, 100));
		wsb.setBackground(new Color(200, 200, 200, 100));
		secret.setBackground(new Color(200, 200, 200, 100));
		super.paintComponents(g);
	}
	@Override
	public void paint(Graphics g) {
		paintComponents(g);
		g.setColor(new Color(0, 0, 0, alpha));
		g.fillRect(0, 0, f.getWidth(), f.getHeight());								//Animated black screen

		if(i.isAlive() || s.isAlive()) { 
			loadingBar.paint(g);
		}
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a2);//Animated logo
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(ac);
		g2d.drawImage(logo, f.getWidth()/2 - logo.getWidth(null)/2, f.getHeight()/2 - logo.getHeight(null)/2, null);
		
		if(!(i.isAlive() || s.isAlive()) && !animationFinished) {
			loadingBar.setColor(new Color(0, 150, 0));
			loadingBar.paint(g);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		boolean doneLoading = !i.isAlive() && !s.isAlive();
		if(!animationFinished) {													//Function to fade out logo
			if(a2 < 1 && !maxed)
				a2 += 0.01f;
			else if(a2 >= 1) {
				if(!doneLoading)
					a2 = 1f;
				else {
					maxed = true;
					a2 -= 0.01;
				}
			}else if(a2 > 0.1 && maxed) {
				a2 -= 0.015;
			}

			if(a2 < 0.1 && maxed) {
				animationFinished = true;
				a2 = 0f;
			}
		}
		if(a2 > 1) { a2 = (float) 1; }												//Error correction
		
		if(animationFinished && alpha == 0) {										//Re-enable buttons after the animation completes
			secret.setEnabled(true);
			wsb.setEnabled(true);
			marine.setEnabled(true);
		}
		if(alpha > 0 && animationFinished) alpha /= 1.2;
		
		loadingBar.setValue(ImageLoader.totalNumberLoaded + SoundLoader.totalNumberLoaded);

		if(doneLoading && !start.isEnabled()) { 
			sprite = new AnimatedImage(ImageLoader.MARINE_STARTUP);
			start.setEnabled(true);		
			if(debug) System.out.println("Game finished loading.Took " + (System.currentTimeMillis() - startTime) + "ms");
		}

		if(doneLoading) {
			spriteLabel.setIcon(new ImageIcon(sprite.getCurrentFrame()));
			sprite.advanceCurrentFrame();
		}
		repaint();

		//==Buttons==//
		if(e.getActionCommand() != null) {
			switch(e.getActionCommand()) {
			case "l":
				if(debug) System.out.println("Creating Driver");
				new Driver(d, t, debug, id, isJar);
				f.dispose();
				break;
			case "m":
				if(debug) System.out.println("Selected marine");
				id = Player.MARINE;
				pictureLabel.setText("");
				currentIcon = new ImageIcon(MarineSplash);
				sprite = new AnimatedImage(ImageLoader.MARINE_STARTUP);
				pictureLabel.setIcon(currentIcon);
				selectPanel.revalidate();
				break;
			case "w":
				if(debug) System.out.println("Selected WSB");
				if(wsbUnlock) {
					pictureLabel.setText("");
					currentIcon = new ImageIcon(WSBSplash);
					sprite = new AnimatedImage(ImageLoader.WSB_STARTUP);
					pictureLabel.setIcon(currentIcon);
					selectPanel.revalidate();
					id = Player.WSB;
				}else {
					pictureLabel.setIcon(null);
					sprite = new AnimatedImage(ImageLoader.NPC_STARTUP);
					pictureLabel.setText("Not unlocked");
					selectPanel.revalidate();
				}
				break;
			case "s":
				if(debug) System.out.println("Selected Secret");
				if(secretUnlok) {
					pictureLabel.setText("");
					pictureLabel.setIcon(currentIcon);
					selectPanel.revalidate();
					id = Player.SECRET;
				}else {
					pictureLabel.setIcon(null);
					sprite = new AnimatedImage(ImageLoader.NPC_STARTUP);
					pictureLabel.setText("Not unlocked");
					selectPanel.revalidate();
				}
			}
		}
	}
	
	private void readUnlock(File f) throws IOException {									//Private method to read the unlock file.
		if(f.exists()) {
			Scanner s = new Scanner(f);
			if(s.nextLine().equals("1")) wsbUnlock = true;
			else if(s.nextLine().equals("2")) {
				wsbUnlock = true;
				secretUnlok = true;
			}
			s.close();
		}
	}
}
