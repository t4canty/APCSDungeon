package driver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import gameObjects.Loot;
import gameObjects.Player;

@SuppressWarnings("serial")
public class Inventory extends JFrame implements ActionListener {

	public Inventory(ArrayList<Loot> items, Player player) {
		JPanel i = new JPanel();
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e1) {e1.printStackTrace();}
		//JComponents
		this.setLayout(new BorderLayout());
		JLabel Title = new JLabel("Inventory", SwingConstants.CENTER);
		ButtonGroup inventoryB = new ButtonGroup();
		for(Loot l : items) {
			JRadioButton tmp = new JRadioButton(l.getName());
			tmp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {player.setActiveItem(l);}
			});
			i.add(tmp);
			inventoryB.add(tmp);
		}
		i.setLayout(new GridLayout(items.size(), 0));
		JScrollPane scrollBar = new JScrollPane(i);
		this.add(Title, BorderLayout.PAGE_START);
		this.add(scrollBar);
		Timer t = new Timer(34,this);
		t.start();
		this.setTitle("Inventory");
		this.setBackground(Color.white);
		this.setResizable(false);
		this.setSize(new Dimension(400, 400));
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!this.isFocused())
			dispose();
	}
}
