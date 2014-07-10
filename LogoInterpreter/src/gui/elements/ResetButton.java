package gui.elements;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class ResetButton extends JButton{
	
	String buttonCaption = "Reset";
	String toolTipText = "Save the current file";

	public ResetButton(){
		super();
		this.setText(this.buttonCaption);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText(this.toolTipText);
	}
}