package logo.commands;

import logo.Command;
import logo.Interpreter;
import logo.Turtle;
import logo.VariableUndefinedException;

public class ConditionalJumpCommand extends Command {

	private int targetLineNumber;
	
	private boolean resetVariable;
	private int defaultValue;
	private String conditionVariable;
	
	public ConditionalJumpCommand( String variable ) {
		this.conditionVariable = variable;
		resetVariable = false;
	}
	
	public ConditionalJumpCommand( int iterations ) {
		defaultValue = iterations;
		
		conditionVariable = Interpreter.createHelpVariable();
		Interpreter.setVariableValue( conditionVariable, iterations );
		resetVariable = true;
	}
	
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {
	}

	public void setTargetLineNumber( int value ) {
		this.targetLineNumber = value;
	}
	
	public int getTarget() throws VariableUndefinedException {
		
		Integer varValue = Interpreter.getVariableValue( conditionVariable );
		if ( varValue <= 0 ) {
			if ( resetVariable ) {
				Interpreter.setVariableValue( conditionVariable, defaultValue );
			}
			return targetLineNumber;
		}
		else {
			return getLineNumber() + 1;
		}
	}
	
	public String getConditionVariable() {
		return conditionVariable;
	}
	
	@Override
	public String toString() {
		return super.toString() + "(target: " + targetLineNumber + ", variable: " + conditionVariable + ")";
	}
}
