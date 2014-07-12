package gui.elements;

import java.awt.Color;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class StatusOutput extends JLabel {

	public static enum Status {
		
		OK ("OK", Color.GREEN.darker()),
		PARSING ("PARSING", Color.BLUE),
		PARSER_ERROR ("PARSING ERROR", Color.RED),
		DRAWING ("DRAWING", Color.CYAN),
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
	
	private static final String EXECUTION_STATUS = "Status: ";
	
	public StatusOutput(){
		this.setExecutionStatus(Status.OK);
	}
	
	public void setExecutionStatus(Status status){
		this.setForeground( status.getColor() );
		this.setText(EXECUTION_STATUS + status.getText());
	}
}
