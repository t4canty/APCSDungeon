package driver;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JTextInput {
	public JPanel frame = new JPanel();
	private JLabel heading;
	private JTextField feild = new JTextField( 7 );
	public JTextInput(String header) {
		frame.setSize(new Dimension(50, 50));
		heading = new JLabel(header);
		frame.add(heading);
		frame.add(feild);
	}
	public String getText() { return feild.getText();}
	public JPanel getFrame() {return frame;}
}
