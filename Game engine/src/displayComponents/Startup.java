package displayComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

import driver.Driver;
import fileIO.ImageLoader;
import fileIO.SoundLoader;
import gameObjects.Player;

public class Startup extends JPanel implements ActionListener{
	private boolean debug;
	private Dimension d;
	private String t;
	private boolean isJar;
	JFrame f;
	ImageLoader i;
	private int id = Player.MARINE;
	private JButton start;
	
	public Startup(Dimension bounds, String title, boolean debug, boolean isJar) {
		i = new ImageLoader();
		i.start(isJar); 
		f = new JFrame("Startup");
		this.debug = debug;
		d = bounds;
		t = title;

		start = new JButton("Start");
		start.setActionCommand("l");
		start.addActionListener(this);
		start.setEnabled(false);

		JRadioButton marine = new JRadioButton("Marine");
		JRadioButton wsb = new JRadioButton("WSB");
		JRadioButton s = new JRadioButton("Secret");
		marine.setActionCommand("m");
		marine.addActionListener(this);
		wsb.setActionCommand("w");
		wsb.addActionListener(this);
		s.setActionCommand("s");
		s.addActionListener(this);

		ButtonGroup bg = new ButtonGroup();

		bg.add(marine);
		bg.add(wsb);
		bg.add(s);
		
		f.setResizable(false);
		f.setBackground(Color.black);
		f.setSize(800, 800);
		f.setLayout(new FlowLayout());
		
		this.add(start);
		this.add(marine);
		this.add(wsb);
		this.add(s);
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
				break;
			case "w":
				if(debug) System.out.println("Selected WSB");
				id = Player.WSB;
				break;
			case "s":
				if(debug) System.out.println("Selected Secret");
				id = Player.SECRET;
			}
		}
	}
}
