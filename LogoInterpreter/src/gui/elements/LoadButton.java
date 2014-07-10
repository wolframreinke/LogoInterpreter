package gui.elements;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class LoadButton extends JButton{

	String buttonCaption = "Load";
	
	public LoadButton(){
		super();
		this.setText(this.buttonCaption);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText("Load an existing File");
	}
}