package driver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

import displayComponents.Inventory;
import displayComponents.SoundEffect;
import displayComponents.StatusBar;
import fileIO.ImageLoader;
import fileIO.SoundLoader;
import gameObjects.Chest;
import gameObjects.Enemy;
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
	private Room[] rooms = new Room[6];
	private StatusBar healthBar;
	private SoundEffect backgroundMusic;
	public static boolean debug;
	private boolean isJar;
	private int pid;
	public static boolean doTick = true;	//track if the game should update variables & run the game
	private boolean gamePaused = false;		//track if the game is paused with escape
	private long lastEnemySpawn = System.currentTimeMillis(); //temp timer to spawn multiple enemies
	private long lastAnimationUpdate = 0;
	
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
	public Driver(Dimension bounds, String title, boolean debug, int pid, boolean isJar) {
		this.bounds = bounds;
		this.debug = debug;
		this.pid = pid;
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
			player = new Player(100, 100, new Dimension(128,128), pid, isJar, debug);
			initializeRooms();
			currentRoom = rooms[0];
			player.updateBounds(currentRoom.getBounds());
			backgroundMusic = SoundLoader.ACTIONMUSIC;
			backgroundMusic.setVolume(0.1);
			//player.setActiveGun(new Gun(10, 300, 0, "badgun", false));
			//currentRoom.getEntities().add(new Enemy(200, 200, 200, new Dimension(64,64), ImageLoader.NPC_FRONTIDLE, ImageLoader.NPC_FRONTIDLE, ImageLoader.NPC_FRONTIDLE, ImageLoader.NPC_FRONTIDLE));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		healthBar = new StatusBar(10, 10, new Dimension(200, 25), Color.MAGENTA, false, false, StatusBar.MIDDLE, "Health", false, 0, 100, 100);
		backgroundMusic.loop();
		
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
		
		if(doTick) {
			if(player.isAlive()) {
				//move player if any keyboard buttons are pressed
				boolean diag = false;
				if(playerUp) {
					if(playerRight) {
						player.move(player.UPRIGHT);
					}else if(playerLeft) {
						player.move(player.UPLEFT);
					}else {
						player.move(player.UP);
					}
				}else if(playerDown) {
					if(playerRight) {
						player.move(player.DOWNRIGHT);
					}else if(playerLeft) {
						player.move(player.DOWNLEFT);
					}else {
						player.move(player.DOWN);
					}
				}else if(playerLeft) {
					player.move(player.LEFT);
				}else if(playerRight) {
					player.move(player.RIGHT);
				}
				if(playerInteract) {
					doTick = false;
					new Inventory(player.getInventory(), player);	//open inventory when key is pressed 
					playerInteract = false;
				}
				if(playerReload) {
					player.reload();
				}
				
				//tell player where the mouse is to update the gun
				player.updateGunAngle(mouseX, mouseY);
				
				if(player.isColliding(currentRoom.getRightDoor()) && currentRoom.isDoorOpen()) {
					currentRoom = currentRoom.rightRoom();
					player.moveTo(75, player.getY());
				}else if(player.isColliding(currentRoom.getLeftDoor())) {
					currentRoom = currentRoom.leftRoom();
					player.moveTo(bounds.width - 100 - player.getHitbox().width, player.getY());
				}else if(player.isColliding(currentRoom.getTopDoor())){
					currentRoom = currentRoom.topRoom();
					player.moveTo(player.getX(), 750);
				}else if(player.isColliding(currentRoom.getBottomDoor())) {
					currentRoom = currentRoom.bottomRoom();
					player.moveTo(player.getX(), 80);
				}
				
				//shoot the gun if the cooldown is ready and button is pressed
				boolean canShoot = player.isShooting && player.canShootBullet();
				//if(debug) System.out.println("In Driver: CanShoot:" + canShoot);
				if(canShoot) {
					currentRoom.getEntities().add(player.getNewBullet());	//spawn new projectile from player gun
				}
			}
			
			//player -> enemy projectile & player -> wall collision
			player.checkCollision(currentRoom.getEntities());
			
			healthBar.setValue(player.getHP());
			
			//enemy shooting
			for(int i = 0; i < currentRoom.getEntities().size(); i++) {
				GameObject o = currentRoom.getEntities().get(i);
				if(o instanceof Enemy) {
					((Enemy) o).runAI(player, currentRoom);
					if(((Enemy) o).isShooting()) {
						Projectile e = ((Enemy)o).getGunshot();
						if(e != null) {
							currentRoom.getEntities().add(e);
						}
					}
				}
			}
			
			
			//timed spawning of enemies (temporary for now)
			/*if(System.currentTimeMillis() - lastEnemySpawn > 10000) {
				lastEnemySpawn = System.currentTimeMillis();
				try {
					currentRoom.getEntities().add(new Enemy(200, 200, 200, new Dimension(64,64), ImageLoader.NPCSKIN, isJar));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/
			
			//update all animations at 30 fps
			if(System.currentTimeMillis() - lastAnimationUpdate > 33) {
				for(GameObject e : currentRoom.getEntities()) {
					e.advanceAnimationFrame();
				}
				player.advanceAnimationFrame();
				lastAnimationUpdate = System.currentTimeMillis();
			}
		}
	}
	
	
	private void respawn() {
		currentRoom = rooms[0];
		try {
			player = new Player(100, 100, new Dimension(128,128), pid, isJar, debug);
			player.updateBounds(currentRoom.getBounds());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//=================== Paint function =====================//
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		currentRoom.paint(g);			//background
		if(doTick) currentRoom.paintEntities(g);	//all entities within the room
		player.paint(g);
		g.drawString(player.getAmmoInMag() + "/" + player.getTotalAmmo(), 150, 700);
		g.drawString("health: " + player.getHP(), 150, 715);
		if(debug) g.drawString(player.getX() + "   " + player.getY(), 150, 730);
		if(debug) g.drawString(mouseX + "    " + mouseY, 150, 745);
		healthBar.paint(g);
	
		if(!player.isAlive()) {
			doTick = false;
			g.setColor(Color.RED);
			Font f = new Font("Electrolize", Font.PLAIN, 72);
			g.setFont(f);
			g.drawString("YOU DIED", 335, 450);
			Font f2 = f.deriveFont(50f);
			g.setFont(f2);
			g.drawString("Press Enter to Respawn", 235, 550);
		}
		
		if(gamePaused) {
			g.setColor(Color.BLACK);
			Font f = new Font("Electrolize", Font.PLAIN, 72);
			g.setFont(f);
			g.drawString("PAUSED", 335, 450);
		}
		
	}
	
	
	//================ Room initializer (hard coded for now) ==============//
	
	//bottom : 782, right 830, left: 45, top 40
	/*	left hitbox	 -(0, 430, 50, 128)
	 * 	right hitbox -(950, 430, 40, 128)
	 * 	bottom hitbox-(440, 900, 128, 40)
	 * 	top hitbox   -(440, 10, 128, 40)
	 * 
	 */
	
	
	private void initializeRooms() throws IOException {
		ArrayList<GameObject> temp = new ArrayList<GameObject>();
		//temp.add()
		rooms[0] = new Room(new Rectangle(48, 40, 905, 870), ImageLoader.ROOMS[0], new ArrayList<GameObject>(), true, f.getSize());
		
		rooms[1] = new Room(new Rectangle(48, 40, 905, 870),  ImageLoader.ROOMS[1], new ArrayList<GameObject>(), true, f.getSize());
		rooms[1].setLeftRoom(rooms[0]);
		rooms[0].setRightRoom(rooms[1]);
		
		rooms[2] = new Room(new Rectangle(48, 40, 905, 870), ImageLoader.ROOMS[2], new ArrayList<GameObject>(), true, f.getSize());
		rooms[2].setLeftRoom(rooms[1]);
		rooms[1].setRightRoom(rooms[2]);
		
		rooms[3] = new Room(new Rectangle(48, 40, 905, 870), ImageLoader.ROOMS[3], new ArrayList<GameObject>(), true, f.getSize());
		rooms[3].setTopRoom(rooms[1]);
		rooms[1].setBottomRoom(rooms[3]);
		
		rooms[4] = new Room(new Rectangle(48, 40, 905, 870), ImageLoader.ROOMS[4], new ArrayList<GameObject>(), true, f.getSize());
		rooms[3].setBottomRoom(rooms[4]);
		rooms[4].setTopRoom(rooms[3]);
		
		rooms[5] = new Room(new Rectangle(48, 40, 905, 870), ImageLoader.ROOMS[5], new ArrayList<GameObject>(), true, f.getSize());
		rooms[4].setLeftRoom(rooms[5]);
		rooms[5].setRightRoom(rooms[4]);
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
			playerUp = false;
			playerRight = false;
			playerLeft = false;
			playerDown = false;
			
		}else if(temp == keybindings[5]) {
			playerReload = true;
		}else if(e.getKeyCode() == 10 && !player.isAlive()) {
			doTick = true;
			respawn();
		}else if(e.getKeyCode() == 27) {
			if(gamePaused) {
				backgroundMusic.play();
			}else {
				backgroundMusic.stop();
			}
			gamePaused = !gamePaused;
			doTick = !doTick;
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
