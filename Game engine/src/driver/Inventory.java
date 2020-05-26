package driver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Inventory extends JPanel implements ActionListener {
	public Inventory() {
		JFrame f = new JFrame(); // Local frame
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		f.setTitle("Inventory");
		f.setBackground(Color.white);
		f.setLayout(new GridLayout(2, 0));
		f.setResizable(false);
		f.setSize(new Dimension(400, 400));
		f.setVisible(true);
		Timer t = new Timer(34,this);
		t.start();
		f.add(this);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true);
		
		f.addFocusListener(new FocusListener() {
			private boolean visible = false;
			@Override
			public void focusLost(FocusEvent e) {
				f.dispose();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				visible = true;
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
