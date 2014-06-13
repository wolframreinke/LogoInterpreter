package logo.commands;

import logo.Command;
import logo.ParsingUtils;
import logo.Turtle;
import logo.VariableUndefinedException;

public class VariableCommand extends Command {

	public static enum Type {
		LET,
		INCREMENT,
		DECREMENT;
	}
	
	private Type type;
	private String targetVariable, assignedVariable;
	
	public VariableCommand( String targetVariable, int assignedValue, Type type ) {
		
		String assignedVariable = ParsingUtils.generateHelpVariable();
		ParsingUtils.setVariableValue( assignedVariable, assignedValue );
		
		this.targetVariable = targetVariable;
		this.assignedVariable = assignedVariable;
		this.type = type;
	}
	
	public VariableCommand( String targetVariable, String assignedVariable, Type type ) {
		
		this.targetVariable = targetVariable;
		this.assignedVariable = assignedVariable;
		this.type = type;
	}
	
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {

		int assignedValue = ParsingUtils.getVariableValue( assignedVariable );
		int targetValue;
		
		switch ( type ) {

		case LET:
			ParsingUtils.setVariableValue( targetVariable, assignedValue );
			break;
			
		case INCREMENT:
			targetValue = ParsingUtils.getVariableValue( targetVariable );
			ParsingUtils.setVariableValue( targetVariable, targetValue + assignedValue );
			break;
			
		case DECREMENT:
			targetValue = ParsingUtils.getVariableValue( targetVariable );
			ParsingUtils.setVariableValue( targetVariable, targetValue - assignedValue );
			break;

		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "(target: " + targetVariable + ", assigned: " + assignedVariable + ", type: " + type + ")";
	}

}
