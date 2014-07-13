package gui.elements;

import java.awt.Color;

import javax.swing.JLabel;

@SuppressWarnings("serial")
/**
 * A JLabel with extended functionality. The Text of the Label can be changed between 5 given values. To each value belongs a different color.
 * Both are defined by an inline enum.
 * @author Julian Schäfer
 */
public class StatusOutput extends JLabel {

	private static final String EXECUTION_STATUS = "Status: ";
	
	//Defines an enum for the the different states, that are possible.
	//Each enum consists of the state and a connected color
	public static enum Status {
		
		OK ("OK", Color.GREEN.darker()),
		PARSING ("PARSING", Color.CYAN),
		PARSER_ERROR ("PARSING ERROR", Color.RED),
		DRAWING ("DRAWING", Color.BLUE),
		PAUSED ("PAUSED", Color.GRAY);
		
		private Color color;
		private String text;

		Status(String text, Color color) {
			this.text = text;
			this.color = color;
		}
		
		public Color getColor(){
			return this.color;
		}
		
		public String getText(){
			return this.text;
		}
	}
	
	/**
	 * Creates a statusOutput and sets it to "OK".
	 */
	public StatusOutput(){
		this.setExecutionStatus(Status.OK);
	}
	
	/**
	 * Sets the executionStatus to a given status.
	 * @param status
	 * The possible states are provided by the enum <code>Status</code>, that is part of this class.
	 * <br>Possible values are defined by the enum.
	 */
	public void setExecutionStatus(Status status){
		this.setForeground(status.getColor());
		this.setText(EXECUTION_STATUS + status.getText());
	}
}
