package gui.elements;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class StepButton extends JButton{
	
	String buttonCaption = "Step";
	String toolTipText = "Execute the nect command";

	public StepButton(){
		super();
		this.setText(this.buttonCaption);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText(this.toolTipText);
	}
}