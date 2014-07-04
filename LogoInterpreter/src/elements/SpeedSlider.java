package elements;

import javax.swing.JSlider;

@SuppressWarnings("serial")
public class SpeedSlider extends JSlider{

	String toolTipText = "Execution Speed";
	
	public SpeedSlider(){
		//Sets the properties of the speed slider
		
		super(0, 100, 100);
		this.setMinorTickSpacing(2);
		this.setMajorTickSpacing(20);
		this.setPaintTicks(true);
		this.setPaintLabels(true);
		this.setToolTipText(toolTipText);
	}
}
