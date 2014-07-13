package gui.elements;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
/**
 * A "new" button. Some properties, like text, preferredSize, and toolTip are automatically set, according to its function.
 * @author Julian Schäfer
 */
public class StepButton extends JButton{
	
	String buttonCaption = "Step";
	String toolTipText = "Execute the nect command";

	/**
	 * Creates a button. Some properties, like text, preferredSize, and toolTip are automatically set, according to its function.
	 */
	public StepButton(){
		super();
		this.setText(this.buttonCaption);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText(this.toolTipText);
	}
}