package gui.elements;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ErrorMessanger extends JTextArea{

	String emptyCaption = "No Errors found\n";
	
	private static final int MAX_NUMBER_OF_ROWS = 9;
	private int currentNumberOfRows = 0;
	
	public ErrorMessanger(){
		super();
		this.setColumns(10);
		this.resetErrorMessages();
		this.setEditable(false);
	}
	
	public void resetErrorMessages(){
		this.setText(this.emptyCaption);
		this.currentNumberOfRows = 0;
	}
	
	public void addErrorMessage(String errorMessage){
		if(this.currentNumberOfRows == 0){
			this.setText( errorMessage);
			this.currentNumberOfRows++;
			return;
		}
		if(this.currentNumberOfRows <= MAX_NUMBER_OF_ROWS){
			this.setText(this.getText() + "\n" + errorMessage);
			return;
		}
		return;
	}
}
