package gui.elements;

import javax.swing.JSlider;

@SuppressWarnings("serial")
/**
 * A JSlider. Some properties like Stepping, TickSpacing, ToolTip and Paint options are automatically set according to its function.
 * @author Julian Schäfer
 */
public class SpeedSlider extends JSlider{

	String toolTipText = "Execution Speed";
	
	/**
	 * Creates a JSlider. Some properties like Stepping, TickSpacing, ToolTip and Paint options are automatically set according to its function.
	 */
	public SpeedSlider(){
		super(0, 100, 100);
		this.setMinorTickSpacing(2);
		this.setMajorTickSpacing(20);
		this.setPaintTicks(true);
		this.setPaintLabels(true);
		this.setToolTipText(this.toolTipText);
	}
}
