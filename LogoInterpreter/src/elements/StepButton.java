package elements;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class StepButton extends JButton{
	
	String buttonCaption = "Step";
	String toolTipText = "Execute the nect command";
	
	StepButtonActionListener stepButtonActionListener = new StepButtonActionListener();
	
	public StepButton(){
		super();
		this.setText(buttonCaption);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText(toolTipText);
		this.addActionListener(stepButtonActionListener);
	}

	public class StepButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	}
}