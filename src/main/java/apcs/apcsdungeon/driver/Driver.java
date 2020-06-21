package apcs.apcsdungeon.driver;

import apcs.apcsdungeon.displaycomponents.Inventory;
import apcs.apcsdungeon.displaycomponents.SoundEffect;
import apcs.apcsdungeon.displaycomponents.StatusBar;
import apcs.apcsdungeon.fileio.ImageLoader;
import apcs.apcsdungeon.fileio.SoundLoader;
import apcs.apcsdungeon.gameobjects.Boss;
import apcs.apcsdungeon.gameobjects.Chest;
import apcs.apcsdungeon.gameobjects.Enemy;
import apcs.apcsdungeon.gameobjects.GameObject;
import apcs.apcsdungeon.gameobjects.Health;
import apcs.apcsdungeon.gameobjects.Player;
import apcs.apcsdungeon.gameobjects.Projectile;
import apcs.apcsdungeon.gameobjects.Room;
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
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Created May 26, 2020
 *
 * @author t4canty
 * @author TJ178
 */
public class Driver extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
	public static boolean debug;
	public static boolean doTick = true;    //track if the game should update variables & run the game
	//========Variables========//
	private Dimension bounds;
	private JFrame f;
	private Timer t;
	private Player player;
	private Room currentRoom;
	private Room[] rooms = new Room[7];
	private StatusBar healthBar;
	private SoundEffect backgroundMusic;
	private boolean isJar;
	private int pid;
	private boolean gamePaused = false;        //track if the game is paused with escape
	private long lastEnemySpawn = System.currentTimeMillis(); //temp timer to spawn multiple enemies
	private long lastAnimationUpdate = 0;
	private double ratio;
	private Font font;

	//keybindings - {up, right, down, left, interact, reload}
	private char[] keybindings = {'w', 'd', 's', 'a', 'e', 'r'};

	//========Constructors========//
	//variables for movement and interaction keys
	private boolean playerUp = false;

	//================== Update Function =================//
	private boolean playerRight = false;
	private boolean playerDown = false;
	private boolean playerLeft = false;


	//================ Room initializer (hard coded for now) ==============//

	//bottom : 782, right 830, left: 45, top 40
	/*	left hitbox	 -(0, 430, 50, 128)
	 * 	right hitbox -(950, 430, 40, 128)
	 * 	bottom hitbox-(440, 900, 128, 40)
	 * 	top hitbox   -(440, 10, 128, 40)
	 *
	 */
	private boolean playerInteract = false;
	private boolean playerReload = false;

	//================ Mouse / Keyboard input ================//
	//mouse location tracker
	private int mouseX = 0;
	private int mouseY = 0;

	/**
	 * Driver object allows for a set size to be passed in, allowing for a method to create the jframe and resize it in a settings method.
	 *
	 * @param bounds Size of the JFrame
	 * @param title  Title of the window
	 * @param debug  Enable debug console printouts
	 */
	public Driver(Dimension bounds, String title, boolean debug, int pid, boolean isJar, Font font) {
		this.bounds = bounds;
		Driver.debug = debug;
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
		if (debug) System.out.println("Bounds:" + bounds.height);
		ratio = (bounds.height / (double) 1000);
		if (debug) System.out.println("ratio: " + ratio);
		this.font = font;
		//===========Temporary player initialization for testing===========//
		try {
			player = new Player(100, 100, new Dimension((int) (128 * ratio), (int) (128 * ratio)), pid, isJar, ratio, debug);
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

		healthBar = new StatusBar(10, 10, new Dimension((int) ((player.getHP() * 2) * ratio), 25), Color.MAGENTA, false, false, StatusBar.MIDDLE, "Health", false, 0, player.getHP(), player.getHP());
		backgroundMusic.loop();

		//Adding ticking timer
		t = new Timer(17, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
		if (doTick) {
			if (player.isAlive()) {
				//move player if any keyboard buttons are pressed
				if (playerUp) {
					if (playerRight) {
						player.move(GameObject.UPRIGHT);
					} else if (playerLeft) {
						player.move(GameObject.UPLEFT);
					} else {
						player.move(GameObject.UP);
					}
				} else if (playerDown) {
					if (playerRight) {
						player.move(GameObject.DOWNRIGHT);
					} else if (playerLeft) {
						player.move(GameObject.DOWNLEFT);
					} else {
						player.move(GameObject.DOWN);
					}
				} else if (playerLeft) {
					player.move(GameObject.LEFT);
				} else if (playerRight) {
					player.move(GameObject.RIGHT);
				}
				if (playerInteract) {
					doTick = false;
					playerInteract = false;
					new Inventory(player.getInventory(), player, font);    //open inventory when key is pressed
				}
				if (playerReload) {
					player.reload();
				}

				//tell player where the mouse is to update the gun
				player.updateGunAngle(mouseX, mouseY);

				if (player.isColliding(currentRoom.getRightDoor()) && currentRoom.isDoorOpen()) {
					currentRoom.removeProjectiles();
					currentRoom = currentRoom.rightRoom();
					player.moveTo((int) (75 * ratio), player.getY());
				} else if (player.isColliding(currentRoom.getLeftDoor())) {
					currentRoom.removeProjectiles();
					currentRoom = currentRoom.leftRoom();
					player.moveTo(bounds.width - (int) (100 * ratio) - player.getHitbox().width, player.getY());
				} else if (player.isColliding(currentRoom.getTopDoor())) {
					currentRoom.removeProjectiles();
					currentRoom = currentRoom.topRoom();
					player.moveTo(player.getX(), (int) (750 * ratio));
				} else if (player.isColliding(currentRoom.getBottomDoor())) {
					currentRoom.removeProjectiles();
					currentRoom = currentRoom.bottomRoom();
					player.moveTo(player.getX(), (int) (80 * ratio));
				}

				//shoot the gun if the cooldown is ready and button is pressed
				boolean canShoot = player.isShooting && player.canShootBullet();
				//if(debug) System.out.println("In Driver: CanShoot:" + canShoot);
				if (canShoot) {
					currentRoom.getEntities().add(player.getNewBullet());    //spawn new projectile from player gun
				}
			}

			//player -> enemy projectile & player -> wall collision
			player.checkCollision(currentRoom.getEntities());

			healthBar.setValue(player.getHP());

			//enemy shooting
			for (int i = 0; i < currentRoom.getEntities().size(); i++) {
				GameObject o = currentRoom.getEntities().get(i);
				if (o instanceof Enemy) {
					((Enemy) o).runAI(player, currentRoom);
					if (((Enemy) o).isShooting()) {
						Projectile e = ((Enemy) o).getGunshot();
						if (e != null) {
							currentRoom.getEntities().add(e);
						}
					}
				}
			}


			//timed spawning of enemies (temporary for now)
			if (System.currentTimeMillis() - lastEnemySpawn > 15000) {
				lastEnemySpawn = System.currentTimeMillis();
				Random rand = new Random();
				currentRoom.getEntities().add(new Enemy(rand.nextInt(905 - 48 + 1) + 48, rand.nextInt(870 - 40 + 1) + 40, 30, new Dimension(64, 64), ImageLoader.NPCSKIN, isJar, ratio));
			}

		}
		//update all animations at 30 fps
		if (System.currentTimeMillis() - lastAnimationUpdate > 33) {
			if (doTick) {
				for (GameObject e : currentRoom.getEntities()) {
					e.advanceAnimationFrame();
				}
			}
			if (player.isAlive())
				player.advanceAnimationFrame();
			else
				player.advanceDeathAnimationFrame();

			lastAnimationUpdate = System.currentTimeMillis();
		}
	}

	private void respawn() {
		currentRoom = rooms[0];
		try {
			player = new Player(100, 100, new Dimension((int) (128 * ratio), (int) (128 * ratio)), pid, isJar, ratio, debug);
			player.updateBounds(currentRoom.getBounds());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//===================Paint function=====================//
	public void paint(Graphics g) {
		super.paintComponent(g);

		currentRoom.paint(g);            //background

		g.setColor(Color.black);

		if (debug) {
			g.setColor(new Color(0, 255, 0, 100));
			g.fillRect(currentRoom.getRectBounds().x, currentRoom.getRectBounds().y, currentRoom.getRectBounds().width, currentRoom.getRectBounds().height);
			g.setColor(Color.black);
		}

		if (doTick) currentRoom.paintEntities(g);    //all entities within the room
		player.paint(g);
		g.setFont(font.deriveFont((float) (20 * ratio)));
		g.drawString(player.getAmmoInMag() + " / " + player.getTotalAmmo(), (int) (140 * ratio), (int) (ratio * 700));
		g.drawString("health | " + player.getHP(), (int) (140 * ratio), (int) (715 * ratio));
		if (debug) g.drawString(player.getX() + "   " + player.getY(), (int) (150 * ratio), (int) (730 * ratio));
		if (debug) g.drawString(mouseX + "    " + mouseY, (int) (150 * ratio), (int) (745 * ratio));
		healthBar.paint(g);

		if (!player.isAlive()) {
			doTick = false;
			g.setColor(Color.RED);
			Font f = font.deriveFont((float) (72 * ratio));
			g.setFont(f);
			g.drawString("YOU DIED", (int) (335 * ratio), (int) (450 * ratio));
			Font f2 = f.deriveFont((float) (50 * ratio));
			g.setFont(f2);
			g.drawString("Press Enter  to Respawn", (int) (235 * ratio), (int) (550 * ratio));
		}

		if (gamePaused) {
			g.setColor(Color.BLACK);
			Font f = font.deriveFont((float) (72 * ratio));
			g.setFont(f);
			g.drawString("PAUSED", (int) (335 * ratio), (int) (450 * ratio));
		}

	}

	private void initializeRooms() {
		rooms[0] = new Room(new Rectangle((int) (48 * ratio), (int) (40 * ratio), (int) (905 * ratio), (int) (870 * ratio)), ImageLoader.ROOMS[0], new ArrayList<>(), true, f.getSize(), ratio);

		rooms[1] = new Room(new Rectangle((int) (48 * ratio), (int) (40 * ratio), (int) (905 * ratio), (int) (870 * ratio)), ImageLoader.ROOMS[1], new ArrayList<>(), true, f.getSize(), ratio);
		rooms[1].setLeftRoom(rooms[0]);
		rooms[0].setRightRoom(rooms[1]);

		rooms[1].getEntities().add(new Enemy(450, 450, 30, new Dimension(64, 64), ImageLoader.NPCSKIN, isJar, ratio));
		rooms[1].getEntities().add(new Enemy(450, 600, 30, new Dimension(64, 64), ImageLoader.NPCSKIN, isJar, ratio));


		rooms[2] = new Room(new Rectangle((int) (48 * ratio), (int) (40 * ratio), (int) (905 * ratio), (int) (870 * ratio)), ImageLoader.ROOMS[2], new ArrayList<>(), true, f.getSize(), ratio);
		rooms[2].setLeftRoom(rooms[1]);
		rooms[1].setRightRoom(rooms[2]);

		rooms[2].getEntities().add(new Enemy(450, 450, 50, new Dimension(128, 128), ImageLoader.NPCSKIN, isJar, ratio));
		rooms[2].getEntities().add(new Chest(600, 500, ImageLoader.CHEST, new Health("", ImageLoader.NO_IMAGE), ratio));

		rooms[3] = new Room(new Rectangle((int) (48 * ratio), (int) (40 * ratio), (int) (905 * ratio), (int) (870 * ratio)), ImageLoader.ROOMS[3], new ArrayList<>(), true, f.getSize(), ratio);
		rooms[3].setTopRoom(rooms[1]);
		rooms[1].setBottomRoom(rooms[3]);

		rooms[3].getEntities().add(new Enemy(450, 500, 40, new Dimension(64, 64), ImageLoader.NPCSKIN, isJar, ratio));
		rooms[3].getEntities().add(new Enemy(450, 800, 40, new Dimension(64, 64), ImageLoader.NPCSKIN, isJar, ratio));

		rooms[4] = new Room(new Rectangle((int) (48 * ratio), (int) (40 * ratio), (int) (905 * ratio), (int) (870 * ratio)), ImageLoader.ROOMS[4], new ArrayList<>(), true, f.getSize(), ratio);
		rooms[3].setBottomRoom(rooms[4]);
		rooms[4].setTopRoom(rooms[3]);

		rooms[4].getEntities().add(new Enemy(250, 500, 60, new Dimension(128, 128), ImageLoader.NPCSKIN, isJar, ratio));
		rooms[4].getEntities().add(new Enemy(450, 800, 60, new Dimension(72, 72), ImageLoader.NPCSKIN, isJar, ratio));

		rooms[5] = new Room(new Rectangle((int) (48 * ratio), (int) (40 * ratio), (int) (905 * ratio), (int) (870 * ratio)), ImageLoader.ROOMS[5], new ArrayList<>(), true, f.getSize(), ratio);
		rooms[4].setLeftRoom(rooms[5]);
		rooms[5].setRightRoom(rooms[4]);

		rooms[5].getEntities().add(new Enemy(250, 500, 50, new Dimension(64, 64), ImageLoader.NPCSKIN, isJar, ratio));
		rooms[5].getEntities().add(new Enemy(450, 800, 50, new Dimension(72, 72), ImageLoader.NPCSKIN, isJar, ratio));
		rooms[5].getEntities().add(new Enemy(300, 600, 75, new Dimension(128, 128), ImageLoader.NPCSKIN, isJar, ratio));

		rooms[6] = new Room(new Rectangle((int) (48 * ratio), (int) (40 * ratio), (int) (905 * ratio), (int) (870 * ratio)), ImageLoader.BOSSROOM, new ArrayList<>(), true, f.getSize(), ratio);
		rooms[5].setBottomRoom(rooms[6]);
		rooms[6].setTopRoom(rooms[5]);

		rooms[6].getEntities().add(new Enemy(450, 800, 50, new Dimension(72, 72), ImageLoader.NPCSKIN, isJar, ratio));
		rooms[6].getEntities().add(new Enemy(300, 600, 75, new Dimension(128, 128), ImageLoader.NPCSKIN, isJar, ratio));
		rooms[6].getEntities().add(new Boss(450, 600, isJar, ratio));

		addchests();
	}

	private void addchests() {
		Random rand = new Random();
		for (Room r : rooms) {
			if (rand.nextBoolean()) {
				if (debug) System.out.println("Added a chest in a room.");
				r.addProp(new Chest(rand.nextInt(905 - 48 + 1) + 48, rand.nextInt(870 - 40 + 1) + 40, ImageLoader.CHEST, ratio));
			}
		}
	}

	/**
	 * Changes default key bindings to a new key
	 *
	 * @param oldkey Key that action was previously set to
	 * @param newkey New key that action will now set to
	 */
	private void changeKeyBinding(char oldkey, char newkey) {
		for (int i = 0; i < keybindings.length; i++) {
			if (keybindings[i] == oldkey) {
				keybindings[i] = newkey;
				break;
			}
		}
	}


	//set designated variables to true when specific key is pressed
	@Override
	public void keyPressed(KeyEvent e) {
		char temp = e.getKeyChar();
		if (temp == keybindings[0]) {
			playerUp = true;

		} else if (temp == keybindings[1]) {
			playerRight = true;

		} else if (temp == keybindings[2]) {
			playerDown = true;

		} else if (temp == keybindings[3]) {
			playerLeft = true;

		} else if (temp == keybindings[4]) {
			playerInteract = true;
			playerUp = false;
			playerRight = false;
			playerLeft = false;
			playerDown = false;

		} else if (temp == keybindings[5]) {
			playerReload = true;
		} else if (e.getKeyCode() == 10 && !player.isAlive()) {
			doTick = true;
			respawn();
		} else if (e.getKeyCode() == 27) {
			if (gamePaused) {
				backgroundMusic.play();
			} else {
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

		if (temp == keybindings[0]) {
			playerUp = false;

		} else if (temp == keybindings[1]) {
			playerRight = false;

		} else if (temp == keybindings[2]) {
			playerDown = false;

		} else if (temp == keybindings[3]) {
			playerLeft = false;

		} else if (temp == keybindings[4]) {
			playerInteract = false;

		} else if (temp == keybindings[5]) {
			playerReload = false;
		}

	}

	//if the left mouse button is pressed, shoot gun
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			player.isShooting = true;
		}

	}

	//if left mouse button is released, stop shooting gun
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
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
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
