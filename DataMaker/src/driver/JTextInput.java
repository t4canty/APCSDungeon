package driver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JTextInput {
	public JPanel frame = new JPanel();
	private JLabel heading;
	private JTextField feild = new JTextField( 7 );
	private JButton ok = new JButton("Ok");
	public JTextInput(String header) {
		heading = new JLabel(header);
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(getText());
			}
		});
		ok.setVisible(false);
		frame.add(heading);
		frame.add(feild);
		frame.add(ok);
	}
	public String getText() { return feild.getText();}
	public JPanel getFrame() {return frame;}
}
