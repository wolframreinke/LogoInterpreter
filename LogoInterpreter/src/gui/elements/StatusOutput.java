package gui.elements;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class StatusOutput extends JLabel {

	private static final String EXECUTION_STATUS = "Execution Status: ";
	
	public static final String OK = "OK";
	public static final String PARSING = "PARSING";
	public static final String PARSER_ERROR = "PARSER ERROR";
	public static final String DRAWING = "DRAWING";
	
	public StatusOutput(){
		this.setExecutionStatus(StatusOutput.OK);
	}
	
	public void setExecutionStatus(String status){
		this.setText(EXECUTION_STATUS + status);
	}
}
