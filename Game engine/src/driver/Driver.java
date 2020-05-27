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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import gameObjects.GameObject;
import gameObjects.Gun;
import gameObjects.Player;
import gameObjects.Projectile;
import gameObjects.Room;

/**
 * 
 * Created May 26, 2020
 * @author t4canty
 * @author TJ178
 *
 */

public class Driver extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener{
	private Dimension bounds;
	private JFrame f;
	private Timer t;
	private Player player;
	private Room currentRoom;
	private boolean debug;
	
	//keybindings - {up, right, down, left, interact, reload}
	private char[] keybindings = {'w', 'd', 's', 'a', 'e', 'r'};
	
	
	
	/**
	 * Driver object allows for a set size to be passed in, allowing for a method to create the jframe and resize it in a settings method.
	 * @param bounds
	 * Size of the jframe
	 * @param title
	 * Title of the window
	 * @param debug
	 * Enable debug console printouts
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
		f.addKeyListener(this);
		f.addMouseListener(this);
		f.addMouseMotionListener(this);
		
		
		//===========Temporary player initialization for testing===========//
		try {
			player = new Player(100, 100, new Dimension(50,50), "", "", "", "", debug);
			currentRoom = new Room(new Rectangle(100, 100, 700, 700), "img/testbackground.png", null, new ArrayList<GameObject>(), true);
			player.updateBounds(currentRoom.getBounds());
			player.setActiveGun(new Gun(10, 300, 0, "badgun", false));
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
	
	//================== Update Function =================//
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
		
		//move player if any keyboard buttons are pressed
		if(playerUp) player.move(player.UP);
		if(playerRight) player.move(player.RIGHT);
		if(playerDown) player.move(player.DOWN);
		if(playerLeft) player.move(player.LEFT);
		if(playerInteract) { 
			new Inventory(player.getInventory(), player);	//open inventory when key is pressed 
			playerInteract = false;
		}
		if(playerReload) {
			//TODO: implement reloading
		}
		
		//tell player where the mouse is to update the gun
		player.updateGunAngle(mouseX, mouseY);
		
		//player -> enemy projectile & player -> wall collision
		player.checkCollision(currentRoom.getEntities());
		
		//shoot the gun if the cooldown is ready and button is pressed
		boolean canShoot = player.isShooting && player.canShootBullet();
		//if(debug) System.out.println("In Driver: CanShoot:" + canShoot);
		if(canShoot) {
			currentRoom.getEntities().add(player.getNewBullet());	//spawn new projectile from player gun
		}
	}
	
	
	//=================== Paint function =====================//
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		currentRoom.paint(g);			//background
		currentRoom.paintEntities(g);	//all entities within the room
		player.paint(g);
	}
	
	
	
	//================ Mouse / Keyboard input ================//
	
	//variables for movement and interaction keys
	private boolean playerUp = false;
	private boolean playerRight = false;
	private boolean playerDown = false;
	private boolean playerLeft = false;
	private boolean playerInteract = false;
	private boolean playerReload = false;
	
	//mouse location tracker
	private int mouseX = 0;
	private int mouseY = 0;
	
	/** 
	 * Changes default key bindings to a new key
	 * @param oldkey
	 * Key that action was previously set to
	 * @param newkey
	 * New key that action will now set to
	 */
	private void changeKeyBinding(char oldkey, char newkey) {
		for(int i = 0; i < keybindings.length; i++) {
			if(keybindings[i] == oldkey) {
				keybindings[i] = newkey;
				break;
			}
		}
	}
	
	
	//set designated variables to true when specific key is pressed
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
	
	//set designated variables to false when specific key is released
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
	
	//if the left mouse button is pressed, shoot gun
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			player.isShooting = true;
		}
		
	}
	
	//if left mouse button is released, stop shooting gun
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			player.isShooting = false;
		}
		
	}
	
	//if left mouse is pressed & mouse is moving, keep track of location and continue shooting
	@Override
	public void mouseDragged(MouseEvent e) {
		//change where player weapon is aiming & shooting
		mouseX = e.getX();
		mouseY = e.getY();
		player.isShooting = true;
		
	}
	
	//keep track of mouse location when button is not pressed
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
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
}
