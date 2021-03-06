package gui.elements;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
/**
 * A "new" button. Some properties, like text, preferredSize, and toolTip are automatically set, according to its function.
 * @author Julian Schäfer
 */
public class SaveButton extends JButton{
	
	String buttonCaption = "Save";
	String toolTipText = "Save the current File";

	/**
	 * Creates a button. Some properties, like text, preferredSize, and toolTip are automatically set, according to its function.
	 */
	public SaveButton(){
		super();
		this.setText(this.buttonCaption);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText(this.toolTipText);
	}
}