package elements;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class NewButton extends JButton{
	
	String buttonCaption = "New";
	String toolTipText = "Create a new File";	
	
	private NewButtonActionListener newButtonActionListener = new NewButtonActionListener();
	
	public NewButton(){
		super();
		this.setText(buttonCaption);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText(toolTipText);
		this.addActionListener(newButtonActionListener);
	}
	
	public class NewButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	}
}



