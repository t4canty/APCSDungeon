package driver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

/*
 * This is the janiest of janky solutions in implementing a jframe, and it runs horribly - 
 * but it doesn't really need to. This is meant to be a dev tool, so end users never have to see this garbage, garbage code.
 */
public class Driver extends JPanel implements ActionListener {
	private ArrayList<JTextInput> frames = new ArrayList<JTextInput>();
	private ArrayList<JTextInput> StatBlock = new ArrayList<JTextInput>();
	private int id = -1;
	JButton launchEditor;
	public Driver() {
		
		frames.add(new JTextInput("Name")); //Every type has this
		frames.add(new JTextInput("Position 1"));
		frames.add(new JTextInput("Position 2"));
		
		StatBlock.add(new JTextInput("HP"));
		StatBlock.add(new JTextInput("Damage"));
		StatBlock.add(new JTextInput("Drop"));
		
		JRadioButton Object = new JRadioButton("Object");
		Object.addActionListener(new ActionListener() {@Override
		public void actionPerformed(ActionEvent e) {id = 0;}});
		JRadioButton Player = new JRadioButton("Player");
		Player.addActionListener(new ActionListener() {@Override
		public void actionPerformed(ActionEvent e) {id = 1;}});
		JRadioButton Enemy = new JRadioButton("Enemy");
		Enemy.addActionListener(new ActionListener() {@Override
		public void actionPerformed(ActionEvent e) {id = 2;}});
		JRadioButton NPC = new JRadioButton("NPC");
		NPC.addActionListener(new ActionListener() {@Override
		public void actionPerformed(ActionEvent e) {id = 3;}});
		ButtonGroup bg = new ButtonGroup();
		bg.add(Object);
		bg.add(Player);
		bg.add(Enemy);
		bg.add(NPC);
		launchEditor = new JButton("Launch Editor");
		launchEditor.addActionListener(this);
		launchEditor.setActionCommand("LaunchEditor");
		launchEditor.setVisible(false);
		
		JFrame f = new JFrame();
		f.setSize(new Dimension(500, 500));
		f.setResizable(false);
		f.setTitle("DataCreator");
		f.setBackground(Color.white);
		f.setLayout(new FlowLayout(FlowLayout.CENTER));
		f.add(this);
		for(JTextInput t : frames) { //Name buttons
			f.add(t.getFrame());
		}
		f.add(NPC);
		f.add(Object);
		f.add(Enemy);
		f.add(Player);
		f.add(launchEditor);
		for(JTextInput t : StatBlock) { //Stat buttons
			t.getFrame().setVisible(false);
			f.add(t.getFrame());
		}
		Timer t = new Timer(34,this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver driver = new Driver();
	}
	@Override
	public void paint(Graphics g) {
		super.paintComponents(g);
		g.drawRect(100, 100, 100, 100);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() != null) {
			XMLDriver x = new XMLDriver();
		}
		if(id == 2 || id == 1)
			for(JTextInput j : StatBlock) {j.getFrame().setVisible(true);}
		else
			for(JTextInput j : StatBlock) {j.getFrame().setVisible(false);}
		
		if(id == 3) {
			launchEditor.setVisible(true);
		}else {
			launchEditor.setVisible(false);
		}
	}
}
