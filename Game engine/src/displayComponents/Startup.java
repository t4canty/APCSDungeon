package displayComponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
import gameObjects.Player;

public class Startup extends JPanel implements ActionListener{
	private boolean debug;
	private Dimension d;
	private String t;
	private boolean isJar;
	JFrame f;
	ImageLoader i;
	JButton start;
	JLabel pictureLabel;
	JPanel selectPanel;
	private int id = Player.MARINE;
	public Startup(Dimension bounds, String title, boolean debug, boolean isJar) {
		i = new ImageLoader();
		i.start(isJar); 
		f = new JFrame("Startup");
		this.debug = debug;
		d = bounds;
		t = title;
		selectPanel = new JPanel();
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} 		 //Replace later with custom buttons - but for now better than the ugly default
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e1) {e1.printStackTrace();}
		
		start = new JButton("Start");
		start.setActionCommand("l");
		start.addActionListener(this);
		start.setEnabled(false);
		
		ImageIcon Placeholder = null;
		try {
			Placeholder = new ImageIcon(ImageIO.read((Startup.class.getResourceAsStream("/img/noimage.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JPanel anotherFuckingPanelJustForButtons = new JPanel();
		anotherFuckingPanelJustForButtons.setLayout(new GridLayout(1, 3));
		
		JRadioButton marine = new JRadioButton("Marine");
		JRadioButton wsb = new JRadioButton("WSB");
		JRadioButton s = new JRadioButton("Secret");
		marine.setActionCommand("m");
		marine.addActionListener(this);
		wsb.setActionCommand("w");
		wsb.addActionListener(this);
		s.setActionCommand("s");
		s.addActionListener(this);
		
		pictureLabel = new JLabel(Placeholder);

			
		ButtonGroup bg = new ButtonGroup();

		bg.add(marine);
		bg.add(wsb);
		bg.add(s);
		
		f.setResizable(false);
		f.setBackground(Color.black);
		f.setSize(800, 800);
		this.setLayout(new BorderLayout());
		
		selectPanel.setLayout(new BorderLayout());
		selectPanel.add(pictureLabel);
		
		anotherFuckingPanelJustForButtons.add(marine);
		anotherFuckingPanelJustForButtons.add(wsb);
		anotherFuckingPanelJustForButtons.add(s);
		
		selectPanel.add(anotherFuckingPanelJustForButtons, BorderLayout.PAGE_END);
		
		
		this.add(selectPanel);
		
		this.add(start, BorderLayout.PAGE_END);
		f.add(this);

		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Timer t = new Timer(30, this);
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!i.isAlive()) start.setEnabled(true);		
		if(e.getActionCommand() != null) System.out.println(e.getActionCommand());
		if(e.getActionCommand() != null) {
			switch(e.getActionCommand()) {
			case "l":
				System.out.println("Creating Driver");
				new Driver(d, t, debug, id, isJar);
				f.dispose();
				break;
			case "m":
				if(debug) System.out.println("Selected marine");
				id = Player.MARINE;
				//pictureLabel = new ImageIcon(marineSplash)  set this later
				pictureLabel.setText("test");
				selectPanel.revalidate();
				break;
			case "w":
				if(debug) System.out.println("Selected WSB");
				pictureLabel.setIcon(null);
				selectPanel.revalidate();
				id = Player.WSB;
				break;
			case "s":
				if(debug) System.out.println("Selected Secret");
				pictureLabel.setIcon(null);
				selectPanel.revalidate();
				id = Player.SECRET;
			}
		}
	}
}
