package gui.elements;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
/**
 * A "reset" button. Some properties, like text, preferredSize, and toolTip are automatically set, according to its function.
 * @author Julian Schäfer
 */
public class ResetButton extends JButton{
	
	String buttonCaption = "Reset";
	String toolTipText = "Save the current file";

	/**
	 * Creates a button. Some properties, like text, preferredSize, and toolTip are automatically set, according to its function.
	 */
	public ResetButton(){
		super();
		this.setText(this.buttonCaption);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText(this.toolTipText);
	}
}