package logo.commands;

import logo.Command;
import logo.Interpreter;
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
		
		String assignedVariable = Interpreter.createHelpVariable();
		Interpreter.setVariableValue( assignedVariable, assignedValue );
		
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

		int assignedValue = Interpreter.getVariableValue( assignedVariable );
		int targetValue;
		
		switch ( type ) {

		case LET:
			Interpreter.setVariableValue( targetVariable, assignedValue );
			break;
			
		case INCREMENT:
			targetValue = Interpreter.getVariableValue( targetVariable );
			Interpreter.setVariableValue( targetVariable, targetValue + assignedValue );
			break;
			
		case DECREMENT:
			targetValue = Interpreter.getVariableValue( targetVariable );
			Interpreter.setVariableValue( targetVariable, targetValue - assignedValue );
			break;

		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "(target: " + targetVariable + ", assigned: " + assignedVariable + ", type: " + type + ")";
	}

}
