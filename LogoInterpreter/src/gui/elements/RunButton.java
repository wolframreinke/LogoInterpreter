package gui.elements;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
/**
 * A "run" button. Some properties, like text, preferredSize, and toolTip are automatically set, according to its function.
 * The caption can be changed between "Run" and "Stop"
 * @author Julian Schäfer
 */
public class RunButton extends JButton{
	
	private String buttonCaptionRun = "Run";
	private String buttonCaptionStop = "Stop";
	private String toolTipTextRun = "Run the execution of the current File";
	private String toolTipTextStop = "Stop the execution of the current File";
	
	/**
	 * Creates a button. Some properties, like text, preferredSize, and toolTip are automatically set, according to its function.
	 */
	public RunButton(){
		super();
		this.setText(this.buttonCaptionRun);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText(this.toolTipTextRun);
	}
	
	/**
	 * Sets the caption and the tooltip of the Button to run
	 */
	public void setPropertiesToRun(){
		this.setText(this.buttonCaptionRun);
		this.setToolTipText(this.toolTipTextRun);
	}
	
	/**
	 * Sets the caption and the tooltip of the Button to stop
	 */
	public void setPropertiesToStop(){
		this.setText(this.buttonCaptionStop);
		this.setToolTipText(this.toolTipTextStop);
	}
}