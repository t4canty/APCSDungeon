package driver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Driver extends JPanel implements ActionListener {
	public Driver() {
		JFrame f = new JFrame();
		f.setSize(new Dimension(500, 500));
		f.setResizable(false);
		f.setTitle("DataCreator");
		f.setBackground(Color.white);
		f.add(this);
		Timer t = new Timer(17,this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver driver = new Driver();
	}
	public void paint(Graphics g) {
		super.paintComponents(g);
		g.drawRect(100, 100, 100, 100);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
