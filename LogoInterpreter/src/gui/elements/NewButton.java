package gui.elements;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class NewButton extends JButton{
	
	String buttonCaption = "New";
	String toolTipText = "Create a new File";	
	
	public NewButton(){
		super();
		this.setText(buttonCaption);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText(toolTipText);
	}
}



