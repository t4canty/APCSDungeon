package driver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class Driver extends JPanel implements ActionListener, MouseListener, MouseMotionListener{
	private Dimension bounds;
	private JFrame f;
	private Timer t;
	InputMap inputmap;		//all inputs for keyboard bindings
	ActionMap actionmap;	//actions assigned to individual key bindings
	/**
	 * Driver object allows for a set size to be passed in, allowing for a method to create the jframe and resize it in a settings method.
	 * @param bounds
	 * Size of the jframe
	 * @param title
	 * Title of the window
	 */
	public Driver(Dimension bounds, String title) {
		this.bounds = bounds;
		f = new JFrame();
		f.setTitle(title);
		f.setSize(bounds);
		f.setResizable(false);
		f.setBackground(Color.black);
		f.add(this);
		
		inputmap = this.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		actionmap = this.getActionMap();
		f.addMouseListener(this);
		f.addMouseMotionListener(this);
		//Adding ticking timer
		t = new Timer(17,this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		//set key bindings to default things
		inputmap.put(KeyStroke.getKeyStroke("W"), "move up");
		inputmap.put(KeyStroke.getKeyStroke("A"), "move left");
		inputmap.put(KeyStroke.getKeyStroke("S"), "move down");
		inputmap.put(KeyStroke.getKeyStroke("D"), "move right");
		inputmap.put(KeyStroke.getKeyStroke("E"), "interact");
		inputmap.put(KeyStroke.getKeyStroke("R"), "reload");
		
		actionmap.put("move up", new PlayerAction(0));
		actionmap.put("move left", new PlayerAction(1));
		actionmap.put("move down", new PlayerAction(2));
		actionmap.put("move right", new PlayerAction(3));
		actionmap.put("interact", new PlayerAction(4));
		actionmap.put("reload", new PlayerAction(5));
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
	}
	
	private void changeKeyBinding(String oldkey, String newkey) {
		inputmap.put(KeyStroke.getKeyStroke(newkey), inputmap.get(KeyStroke.getKeyStroke(oldkey)));
		inputmap.remove(KeyStroke.getKeyStroke(oldkey));
	}
	

	private class PlayerAction extends AbstractAction{
		
		private int action = -1;
		
		public PlayerAction(int action) {
			this.action = action;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(action) {
			case 0:
				////move player up
				System.out.println("up");
				break;
			case 1:
				////move player left
				break;
			case 2:
				//move player down
				break;
			case 3:
				//move player right
				break;
			case 4:
				//interact with something somehow
				break;
			case 5:
				//reload weapon
				break;
			default:
				System.out.println("KeyBindings: incorrect PlayerAction action");
			}
			
		}
	}
	
	
	
	/////// Mouse and Keyboard inputs / variables
	
	@Override
	public void mousePressed(MouseEvent e) {
		//start shooting
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//stop shooting
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		//change where player weapon is aiming & shooting
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		//change where player weapon is aiming
		
	}
	
	
	
	//methods we don't care about
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		//start tracking mouse
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//stop tracking mouse
	}
	
}
