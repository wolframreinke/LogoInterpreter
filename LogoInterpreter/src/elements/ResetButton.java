package elements;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class ResetButton extends JButton{
	
	String buttonCaption = "Reset";
	String toolTipText = "Save the current file";
	
	ResetButtonActionListener resetButtonActionListener = new ResetButtonActionListener();
	
	public ResetButton(){
		super();
		this.setText(buttonCaption);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText(toolTipText);
		this.addActionListener(resetButtonActionListener);
	}

	public class ResetButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	}
}