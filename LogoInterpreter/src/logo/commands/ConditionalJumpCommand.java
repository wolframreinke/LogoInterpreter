package logo.commands;

import logo.Command;
import logo.Interpreter;
import logo.Turtle;
import logo.VariableUndefinedException;

public class ConditionalJumpCommand extends Command {

	private int targetLineNumber;
	
	private String conditionVariable;
	
	public ConditionalJumpCommand( String variable ) {
		this.conditionVariable = variable;
	}
	
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {
	}

	public void setTargetLineNumber( int value ) {
		this.targetLineNumber = value;
	}
	
	public int getTarget() throws VariableUndefinedException {
		
		Integer varValue = Interpreter.getVariableValue( conditionVariable );
		if ( varValue == 0 )
			return targetLineNumber;
		else
			return getLineNumber();
	}
	
	public String getConditionVariable() {
		return conditionVariable;
	}
}
