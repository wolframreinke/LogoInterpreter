package logo.commands;

import logo.ParsingUtils;
import logo.Turtle;
import logo.VariableUndefinedException;

public class ConditionalJumpCommand extends JumpCommand {

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
	
	@Override
	public int getJumpTarget() throws VariableUndefinedException {
		
		Integer varValue = ParsingUtils.getVariableValue( this.conditionVariable );
		if ( varValue <= 0 ) {
			if ( this.resetVariable ) {
				ParsingUtils.setVariableValue( this.conditionVariable, this.defaultValue );
			}
			return super.getJumpTarget();
		}
		
		return super.getLineNumber() + 1;
	}
	
	public String getConditionVariable() {
		return this.conditionVariable;
	}
	
	@Override
	public String toString() {
		String jumpTarget;
		try {
			jumpTarget = String.valueOf( super.getJumpTarget() );
		}
		catch ( VariableUndefinedException e ) {
			jumpTarget = "undefined";
		}
		return super.toString() + "(target: " + jumpTarget + ", variable: " + this.conditionVariable + ")";
	}
}
