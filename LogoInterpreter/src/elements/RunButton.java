package elements;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class RunButton extends JButton{
	
	String buttonCaptionRun = "Run";
	String buttonCaptionStop = "Stop";
	String toolTipText = "Run the current File";
	
	public RunButton(){
		super();
		this.setText(buttonCaptionRun);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText(toolTipText);
	}
	
	public void toggleCaption(){
		if(this.getText() == buttonCaptionRun)
			this.setText(buttonCaptionStop);
		else
			this.setText(buttonCaptionRun);
	}
}