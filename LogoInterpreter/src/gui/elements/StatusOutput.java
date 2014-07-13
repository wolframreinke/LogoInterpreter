package gui.elements;

import java.awt.Color;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class StatusOutput extends JLabel {

	private static final String EXECUTION_STATUS = "Status: ";
	
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

		
		public Color getColor() {
		
			return this.color;
		}
		
		public String getText() {
		
			return this.text;
		}
	}
	
	public StatusOutput(){
		this.setExecutionStatus(Status.OK);
	}
	
	public void setExecutionStatus(Status status){
		this.setForeground( status.getColor() );
		this.setText(EXECUTION_STATUS + status.getText());
	}
}
