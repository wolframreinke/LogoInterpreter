package elements;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ErrorMessanger extends JTextArea{

	String emptyCaption = "No Errors found";
	
	public ErrorMessanger(){
		super();
		this.setColumns(10);
		this.insert(emptyCaption, 0);
		this.setEditable(false);
	}
}
