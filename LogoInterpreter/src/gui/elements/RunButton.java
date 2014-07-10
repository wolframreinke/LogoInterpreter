package gui.elements;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class RunButton extends JButton{
	
	String buttonCaptionRun = "Run";
	String buttonCaptionStop = "Stop";
	String toolTipText = "Run the current File";
	
	public RunButton(){
		super();
		this.setText(this.buttonCaptionRun);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText(this.toolTipText);
	}
	
	public void toggleCaption(){
		if(this.getText() == this.buttonCaptionRun)
			this.setText(this.buttonCaptionStop);
		else
			this.setText(this.buttonCaptionRun);
	}
	
	public void setCaptionToRun(){
		this.setText(this.buttonCaptionRun);
	}
	
	public void setCaptionToStop(){
		this.setText(this.buttonCaptionStop);
	}
}