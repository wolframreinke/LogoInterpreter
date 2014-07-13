package gui.elements;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
/**
 * A modified JTextArea. Can display up to 9 error messenges. Methods for adding a message in the String format and clearing the messenger are existing.
 * @author Julian Schäfer
 */
public class ErrorMessenger extends JTextArea{

	public String emptyCaption = "No Errors found\n";
	
	//Due to gui design reasons, the maximum number of rows is limited to 9
	private static final int MAX_NUMBER_OF_ROWS = 9;
	private int currentNumberOfRows = 0;
	
	/**
	 * Creates a ErrorMessanger. Some properties are automatically set, according to its use.
	 */
	public ErrorMessenger(){
		super();
		this.resetErrorMessenges();
		this.setEditable(false);
	}
	
	/**
	 * Clears all ErrorMEssages of the messenger.
	 */
	public void resetErrorMessenges(){
		this.setText(this.emptyCaption);
		this.currentNumberOfRows = 0;
	}
	
	/**
	 * Adds a new errormessenge into a new line, if there are currently less than 9 messages.
	 * @param errorMessange
	 * The errormessenge. Please keep in mind, that this method will automatically add "\n" to your string, in order to display it in a new line.
	 * If there are more than 9 errors, only the first 9 errors will be displayed, all other errormessenges will disappear.
	 */
	public void addErrorMessenge(String errorMessenge){
		//If the messanger is empty, no new lines are needed.
		if(this.currentNumberOfRows == 0){
			this.setText(errorMessenge);
		}
		else if(this.currentNumberOfRows < MAX_NUMBER_OF_ROWS){
			this.setText(this.getText() + "\n" + errorMessenge);
		}
		this.currentNumberOfRows++;
		return;
	}
}
