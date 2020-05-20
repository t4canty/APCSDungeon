package driver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import JSONCrafter.JSONwriter;

/*
 * This is the janiest of janky solutions in implementing a jframe, and it runs horribly - 
 * but it doesn't really need to. This is meant to be a dev tool, so end users never have to see this garbage, garbage code.
 */
public class Driver extends JPanel implements ActionListener {
	private ArrayList<JTextInput> frames = new ArrayList<JTextInput>();
	private ArrayList<JTextInput> StatBlock = new ArrayList<JTextInput>();
	private int id = -1;
	JButton launchEditor;
	JButton addDrop;
	JFrame f; //Global Frame
	JPanel st; //Stat frame
	JButton ok;
	JScrollPane scroll;
	String Old;

	public Driver() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
		addDrop = new JButton("addDrop");
		addDrop.addActionListener(this);
		addDrop.setActionCommand("addDrop");
		addDrop.setVisible(false);
		ok = new JButton("ok");
		ok.addActionListener(this);
		ok.setActionCommand("write");
		st = new JPanel();
		for(JTextInput t : StatBlock) { //Stat buttons
			st.add(t.getFrame());
		}
		scroll = new JScrollPane(st);
		scroll.setVisible(false);

		f = new JFrame();
		f.setSize(new Dimension(800, 300));
		f.setResizable(false);
		f.setTitle("DataCreator");
		f.setBackground(Color.white);
		f.setLayout(new GridLayout(4, 0));
		for(JTextInput t : frames) { //Name buttons
			f.add(t.getFrame());
		}

		f.add(NPC);
		f.add(Object);
		f.add(Enemy);
		f.add(Player);
		f.add(launchEditor);
		f.add(scroll);
		f.add(addDrop);
		f.add(ok);
		Timer t = new Timer(34,this);
		t.start();
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

	}
	/*
	 * Lord, this ui is hot trash, but it works
	 * xmleditor's gonna be even worse...
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver driver = new Driver();
	}
	@Override
	public void paint(Graphics g) {
		super.paintComponents(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!ok.isEnabled()) {
			if(!frames.get(0).getText().equals(Old))
				ok.setEnabled(true);
		}

		if(e.getActionCommand() != null) {
			if(e.getActionCommand().equals("LaunchEditor")) {new XMLDriver();}
			else if(e.getActionCommand().equals("addDrop")) {
				System.out.println("Before add:" + StatBlock.size());
				StatBlock.add(new JTextInput("Drop"));
				st.add(StatBlock.get(StatBlock.size()-1).getFrame());
				System.out.println("After add:" + StatBlock.size());
				st.revalidate();

			}
			else if (e.getActionCommand().equals("write")){
				switch(id){
				case 0:
					try {
						new JSONwriter(frames.get(0).getText(), Integer.parseInt(frames.get(1).getText()), Integer.parseInt(frames.get(2).getText()), id);
						System.out.println("Done.");
						ok.setEnabled(false);
						Old = frames.get(0).getText();
						System.out.println(Old);
					} catch (NumberFormatException | FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case 1:
					try {
						ArrayList<Integer> n = new ArrayList<Integer>();
						for(int i = 1; i < StatBlock.size(); i++) {
							n.add(Integer.parseInt(StatBlock.get(i).getText()));
						}
						new JSONwriter(frames.get(0).getText(), Integer.parseInt(frames.get(1).getText()), Integer.parseInt(frames.get(2).getText()), id,
								Integer.parseInt(StatBlock.get(0).getText()), Integer.parseInt(StatBlock.get(1).getText()), n);
						System.out.println("Done.");
						ok.setEnabled(false);
						Old = frames.get(0).getText();
						System.out.println(Old);
					} catch (NumberFormatException | FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case 2:
					try {
						ArrayList<Integer> n = new ArrayList<Integer>();
						for(int i = 1; i < StatBlock.size(); i++) {
							n.add(Integer.parseInt(StatBlock.get(i).getText()));
						}
						new JSONwriter(frames.get(0).getText(), Integer.parseInt(frames.get(1).getText()), Integer.parseInt(frames.get(2).getText()), id,
								Integer.parseInt(StatBlock.get(0).getText()), Integer.parseInt(StatBlock.get(1).getText()), n);
						System.out.println("Done.");
						ok.setEnabled(false);
						Old = frames.get(0).getText();
						System.out.println(Old);
					} catch (NumberFormatException | FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case 3:
					try {
						new JSONwriter(frames.get(0).getText(), Integer.parseInt(frames.get(1).getText()), Integer.parseInt(frames.get(2).getText()), id, null);
						System.out.println("Done.");
						ok.setEnabled(false);
						Old = frames.get(0).getText();
						System.out.println(Old);
					} catch (NumberFormatException | FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		if(id == 2 || id == 1) {
			scroll.setVisible(true);
			addDrop.setVisible(true);
		}
		else {
			scroll.setVisible(false);
			addDrop.setVisible(false);
		}
		if(id == 3) {
			launchEditor.setVisible(true);
		}else {
			launchEditor.setVisible(false);
		}
	}
}
