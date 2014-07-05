package gui.elements;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class SaveButton extends JButton{
	
	String buttonCaption = "Save";
	String toolTipText = "Save the current File";

	public SaveButton(){
		super();
		this.setText(buttonCaption);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText(toolTipText);
	}
}