package elements;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class RunButton extends JButton{
	
	String buttonCaption = "Run";
	String toolTipText = "Run the current File";
	
	RunButtonActionListener runButtonActionListener = new RunButtonActionListener();
	
	public RunButton(){
		super();
		this.setText(buttonCaption);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText(toolTipText);
		this.addActionListener(runButtonActionListener);
	}

	public class RunButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	}
}