package apcs.apcsdungeon.displaycomponents;

import apcs.apcsdungeon.driver.Driver;
import apcs.apcsdungeon.gameobjects.Gun;
import apcs.apcsdungeon.gameobjects.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Created May 30, 2020
 *
 * @author t4canty
 * @author TJ178
 * Inventory class that launches a separate JFrame to manage inventory, and self-destructs when not in focus.
 */
@SuppressWarnings("serial")
public class Inventory extends JFrame implements ActionListener, KeyListener {
	private Timer t;
	private long startTime;

	/**
	 * Spawns a new JFrame object to manage inventory, which self destructs when finished.
	 *
	 * @param items  ArrayList of items currently inside the player's inventory.
	 * @param player The player object.
	 */
	public Inventory(ArrayList<Gun> items, Player player, Font font) {
		//========Variables========//
		JPanel i = new JPanel();
		JLabel Title = new JLabel("Inventory", SwingConstants.CENTER);
		Title.setFont(font.deriveFont(40f));
		ButtonGroup inventoryB = new ButtonGroup();
		JScrollPane scrollBar = new JScrollPane(i);
		t = new Timer(34, this);

		//========Setup========//
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}         //Replace later with custom buttons - but for now better than the ugly default
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		for (Gun l : items) {                                                             //Loop through the inventory and create a JRadioButton for each
			JRadioButton tmp = new JRadioButton(l.getName());
			//Each button sets the active weapon to their corresponding weapon
			tmp.addActionListener(arg0 -> {
				player.setActiveGun(l);
				requestFocusInWindow();                                                //set focus back to jFrame so keyListener can work
			});
			tmp.setFont(font.deriveFont(30f));
			i.add(tmp);
			inventoryB.add(tmp);
		}

		i.setLayout(new GridLayout(items.size(), 0));                                    //Set the nested JPanel to a gridLayout

		this.setLayout(new BorderLayout());
		this.setTitle("Inventory");
		this.setBackground(Color.white);
		this.setResizable(false);
		this.setSize(new Dimension(400, 400));
		this.addKeyListener(this);

		//========Adding Components========//
		this.add(Title, BorderLayout.PAGE_START);                                        //Adds the title to the center top of the page
		this.add(scrollBar);                                                            //Adds the scrollPane of the nested JPanel

		//========Finalization========//
		this.setVisible(true);
		requestFocusInWindow();                                                            //Set focus so keyListener works
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		startTime = System.currentTimeMillis();
		t.start();                                                                        //Start ticking timer
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (!this.isFocused() && System.currentTimeMillis() - startTime > 120) {                                                            //Disposes of the JFrame when focus is lost
			System.out.println("Lost focus after" + (System.currentTimeMillis() - startTime) + "ms.");
			Driver.doTick = true;
			dispose();
			t.stop();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 69 || e.getKeyCode() == 27) {
			Driver.doTick = true;
			dispose();
			t.stop();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
