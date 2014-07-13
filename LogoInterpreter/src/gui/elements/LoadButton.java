package gui.elements;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
/**
 * A "load" button. Some properties, like text, preferredSize, and toolTip are automatically set, according to its function.
 * @author Julian Schäfer
 */
public class LoadButton extends JButton{

	String buttonCaption = "Load";
	
	/**
	 * Creates a button. Some properties, like text, preferredSize, and toolTip are automatically set, according to its function.
	 */
	public LoadButton(){
		super();
		this.setText(this.buttonCaption);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText("Load an existing File");
	}
}