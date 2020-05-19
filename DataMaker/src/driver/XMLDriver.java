package driver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class XMLDriver extends JPanel implements ActionListener {
	public XMLDriver() {
		JFrame j = new JFrame();
		j.setSize(new Dimension(500, 500));
		j.setResizable(false);
		j.setTitle("XmlEditor");
		j.setBackground(Color.white);
		Timer t = new Timer(17,this);
		t.start();
		j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		j.setVisible(true);
		j.add(this);
	}
	public void launchXml() {
		XMLDriver x = new XMLDriver();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
