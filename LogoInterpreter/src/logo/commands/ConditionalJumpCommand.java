package logo.commands;

import logo.ParsingUtils;
import logo.Turtle;
import logo.VariableUndefinedException;

public class ConditionalJumpCommand extends Command {

	private int targetLineNumber;
	
	private boolean resetVariable;
	private int defaultValue;
	private String conditionVariable;
	
	public ConditionalJumpCommand( String variable ) {
		this.conditionVariable = variable;
		this.resetVariable = false;
	}
	
	public ConditionalJumpCommand( int iterations ) {
		this.defaultValue = iterations;
		
		this.conditionVariable = ParsingUtils.generateHelpVariable();
		ParsingUtils.setVariableValue( this.conditionVariable, iterations );
		this.resetVariable = true;
	}
	
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {
		// The conditional jump does not need to do anything here
	}

	public void setTargetLineNumber( int value ) {
		this.targetLineNumber = value;
	}
	
	public int getTarget() throws VariableUndefinedException {
		
		Integer varValue = ParsingUtils.getVariableValue( this.conditionVariable );
		if ( varValue <= 0 ) {
			if ( this.resetVariable ) {
				ParsingUtils.setVariableValue( this.conditionVariable, this.defaultValue );
			}
			return this.targetLineNumber;
		}
		
		return getLineNumber() + 1;
	}
	
	public String getConditionVariable() {
		return this.conditionVariable;
	}
	
	@Override
	public String toString() {
		return super.toString() + "(target: " + this.targetLineNumber + ", variable: " + this.conditionVariable + ")";
	}
}
