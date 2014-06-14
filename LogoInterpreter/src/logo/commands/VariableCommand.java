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

		int assignedValue = ParsingUtils.getVariableValue( this.assignedVariable );
		int targetValue;
		
		switch ( this.type ) {

		case LET:
			ParsingUtils.setVariableValue( this.targetVariable, assignedValue );
			break;
			
		case INCREMENT:
			targetValue = ParsingUtils.getVariableValue( this.targetVariable );
			ParsingUtils.setVariableValue( this.targetVariable, targetValue + assignedValue );
			break;
			
		case DECREMENT:
			targetValue = ParsingUtils.getVariableValue( this.targetVariable );
			ParsingUtils.setVariableValue( this.targetVariable, targetValue - assignedValue );
			break;

		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "(target: " + this.targetVariable + ", assigned: " + this.assignedVariable + ", type: " + this.type + ")";
	}

}
