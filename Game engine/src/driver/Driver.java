package driver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import gameObjects.GameObject;
import gameObjects.Player;
import gameObjects.Room;

public class Driver extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener{
	private Dimension bounds;
	private JFrame f;
	private Timer t;
	private Player player;
	private Room currentRoom;
	
	//keybindings - {up, right, down, left, interact, reload}
	private final char[] keybindings = {'w', 'd', 's', 'a', 'e', 'r'};
	
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
		
		//inputmap = this.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		//actionmap = this.getActionMap();
		f.addKeyListener(this);
		f.addMouseListener(this);
		f.addMouseMotionListener(this);
		
		
		//set key bindings to default things
		/*inputmap.put(KeyStroke.getKeyStroke("W"), "move up");
		inputmap.put(KeyStroke.getKeyStroke("A"), "move left");
		inputmap.put(KeyStroke.getKeyStroke("S"), "move down");
		inputmap.put(KeyStroke.getKeyStroke("D"), "move right");
		inputmap.put(KeyStroke.getKeyStroke("E"), "interact");
		inputmap.put(KeyStroke.getKeyStroke("R"), "reload");
		*/
		
		
		try {
			player = new Player(100, 100, new Dimension(50,50), "", "", "", "");
			currentRoom = new Room(new Rectangle(100, 100, 900, 900), "img/testbackground.png", null, new ArrayList<GameObject>(), true);
			player.updateBounds(currentRoom.getBounds());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//Adding ticking timer
		t = new Timer(17,this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
		
		if(playerUp) player.move(0);
		if(playerRight) player.move(1);
		if(playerDown) player.move(2);
		if(playerLeft) player.move(3);
		if(playerInteract) { 
			new Inventory(); 
			playerInteract = false;
		}
		
		player.checkCollision(currentRoom.getEntities());
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		//player.paint(g);
		currentRoom.paint(g);
		player.paint(g);
	}
	
	private void changeKeyBinding(String oldkey, String newkey) {
		/*inputmap.put(KeyStroke.getKeyStroke(newkey), inputmap.get(KeyStroke.getKeyStroke(oldkey)));
		inputmap.remove(KeyStroke.getKeyStroke(oldkey));*/
	}
	
	///Actions that are bound to keys
	/*private class PlayerAction extends AbstractAction{
		
		private int action = -1;
		
		public PlayerAction(int action) {
			this.action = action;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(action) {
			case 0:
				////move player up
				player.move(0);
				break;
			case 1:
				////move player left
				player.move(3);
				break;
			case 2:
				//move player down
				player.move(2);
				break;
			case 3:
				//move player right
				player.move(1);
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
	}*/
	
	
	
	/////// Mouse and Keyboard inputs / variables
	private boolean playerUp = false;
	private boolean playerRight = false;
	private boolean playerDown = false;
	private boolean playerLeft = false;
	private boolean playerInteract = false;
	private boolean playerReload = false;
	
	@Override
	public void keyPressed(KeyEvent e) {
		char temp = e.getKeyChar();
		
		if(temp == keybindings[0]) {
			playerUp = true;
			
		}else if(temp == keybindings[1]) {
			playerRight = true;
			
		}else if(temp == keybindings[2]) {
			playerDown = true;
			
		}else if(temp == keybindings[3]) {
			playerLeft = true;
			
		}else if(temp == keybindings[4]) {
			playerInteract = true;
			
		}else if(temp == keybindings[5]) {
			playerReload = true;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		char temp = e.getKeyChar();
		
		if(temp == keybindings[0]) {
			playerUp = false;
			
		}else if(temp == keybindings[1]) {
			playerRight = false;
			
		}else if(temp == keybindings[2]) {
			playerDown = false;
			
		}else if(temp == keybindings[3]) {
			playerLeft = false;
			
		}else if(temp == keybindings[4]) {
			playerInteract = false;
			
		}else if(temp == keybindings[5]) {
			playerReload = false;
		}
		
	}
	
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
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
