package driver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import gameObjects.Gun;
import gameObjects.Player;


/**
 * 
 * @author t4canty
 * Inventory class that launches a separate JFrame to manage inventory, and self-destructs when not in focus.
 *
 */
@SuppressWarnings("serial")
public class Inventory extends JFrame implements ActionListener, KeyListener {

	/**
	 * Spawns a new JFrame object to manage inventory, which self destructs when finished. 
	 * @param items
	 * ArrayList of items currently inside the player's inventory.
	 * @param player
	 * The player object. 
	 */
	public Inventory(ArrayList<Gun> items, Player player) {
		//========Variables========//
		JPanel i = new JPanel();
		JLabel Title = new JLabel("Inventory", SwingConstants.CENTER);
		ButtonGroup inventoryB = new ButtonGroup();
		JScrollPane scrollBar = new JScrollPane(i);
		Timer t = new Timer(34,this);

		//========Setup========//
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} 		 //Replace later with custom buttons - but for now better than the ugly default
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e1) {e1.printStackTrace();}
		
		for(Gun l : items) {															 //Loop through the inventory and create a JRadioButton for each
			JRadioButton tmp = new JRadioButton(l.getName());
			tmp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.setActiveGun(l);
					System.out.println("tick");
					} //Each button sets the active weapon to their corresponding weapon
			});
			i.add(tmp);
			inventoryB.add(tmp);
		}
		
		i.setLayout(new GridLayout(items.size(), 0));									//Set the nested JPanel to a gridLayout
		
		this.addKeyListener(this);
		this.setLayout(new BorderLayout());
		this.setTitle("Inventory");														
		this.setBackground(Color.white);
		this.setResizable(false);
		this.setSize(new Dimension(400, 400));

		//========Adding Components========//
		this.add(Title, BorderLayout.PAGE_START);										//Adds the title to the center top of the page
		this.add(scrollBar);															//Adds the scrollPane of the nested JPanel


		//========Finalization========//
		this.setVisible(true);
		System.out.println(requestFocusInWindow());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		t.start();																		//Start ticking timer
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!this.isFocused())															//Disposes of the JFrame when focus is lost
			dispose();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		if(e.getKeyCode() == 69 || e.getKeyCode() == 27) {
			dispose();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
