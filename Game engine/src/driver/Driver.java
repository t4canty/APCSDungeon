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
import gameObjects.Loot;
import gameObjects.Player;
import gameObjects.Projectile;
import gameObjects.Room;

public class Driver extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener{
	private Dimension bounds;
	private JFrame f;
	private Timer t;
	private Player player;
	private Room currentRoom;
	private boolean debug;
	
	//Hey Timmy think you can add some comments to your code when you get the chance
	//just to cut down on spaghetti
	
	//keybindings - {up, right, down, left, interact, reload}
	private final char[] keybindings = {'w', 'd', 's', 'a', 'e', 'r'};
	
	/**
	 * Driver object allows for a set size to be passed in, allowing for a method to create the jframe and resize it in a settings method.
	 * @param bounds
	 * Size of the jframe
	 * @param title
	 * Title of the window
	 */
	public Driver(Dimension bounds, String title, boolean debug) {
		this.bounds = bounds;
		this.debug = debug;
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
			player = new Player(100, 100, new Dimension(50,50), "", "", "", "", debug);
			currentRoom = new Room(new Rectangle(100, 100, 700, 700), "img/testbackground.png", null, new ArrayList<GameObject>(), true);
			player.updateBounds(currentRoom.getBounds());
			player.setActiveGun(new Loot(10, 300, 0, "badgun", false));
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
		
		if(playerUp) player.move(player.UP);
		if(playerRight) player.move(player.RIGHT);
		if(playerDown) player.move(player.DOWN);
		if(playerLeft) player.move(player.LEFT);
		if(playerInteract) { 
			new Inventory(player.getInventory(), player); 
			playerInteract = false;
		}
		
		player.updateGunAngle(mouseX, mouseY);
		player.checkCollision(currentRoom.getEntities());
		
		boolean canShoot = player.isShooting && player.canShootBullet();
		//System.out.println("In Driver: CanShoot:" + canShoot);
		if(canShoot) {
			currentRoom.getEntities().add(new Projectile(player.getActiveGun().getDamage(), false, player.getCenterX(), player.getCenterY(), 20, player.getGunAngle(), new Dimension(25, 25), null, 0));
		}
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		//player.paint(g);
		currentRoom.paint(g);
		currentRoom.paintEntities(g);
		player.paint(g);
	}
	
	private void changeKeyBinding(char oldkey, char newkey) {
		for(int i = 0; i < keybindings.length; i++) {
			if(keybindings[i] == oldkey) {
				keybindings[i] = newkey;
				break;
			}
		}
		
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
	
	private int mouseX = 0;
	private int mouseY = 0;
	
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
		if(e.getButton() == MouseEvent.BUTTON1) {
			player.isShooting = true;
		}
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			player.isShooting = false;
		}
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		//change where player weapon is aiming & shooting
		mouseX = e.getX();
		mouseY = e.getY();
		player.isShooting = true;
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		//change where player weapon is aiming
		mouseX = e.getX();
		mouseY = e.getY();
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
