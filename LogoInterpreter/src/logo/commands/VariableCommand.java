package logo.commands;

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
		
		String assignedVariable = Variables.generateHelpVariable();
		Variables.setVariableValue( assignedVariable, assignedValue );
		
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

		int assignedValue = Variables.getVariableValue( this.assignedVariable );
		int targetValue;
		
		switch ( this.type ) {

		case LET:
			Variables.setVariableValue( this.targetVariable, assignedValue );
			break;
			
		case INCREMENT:
			targetValue = Variables.getVariableValue( this.targetVariable );
			Variables.setVariableValue( this.targetVariable, targetValue + assignedValue );
			break;
			
		case DECREMENT:
			targetValue = Variables.getVariableValue( this.targetVariable );
			Variables.setVariableValue( this.targetVariable, targetValue - assignedValue );
			break;

		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "(target: " + this.targetVariable + ", assigned: " + this.assignedVariable + ", type: " + this.type + ")";
	}

}
