package logo.commands;

import logo.ParsingUtils;
import logo.Turtle;
import logo.VariableUndefinedException;

public class StaticJumpCommand extends JumpCommand {

	private final String variable;
	
	public StaticJumpCommand( int jumpTarget, String variable ) {

		this.variable = variable;
		super.setJumpTarget( jumpTarget );
	}
	
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {
		
		Integer value = ParsingUtils.getVariableValue( this.variable );
		value--;
		ParsingUtils.setVariableValue( this.variable, value );
		
	}
	
	@Override
	public String toString() {
		String jumpTarget = "undefined";
		try {
			jumpTarget = String.valueOf( super.getJumpTarget() );
		}
		catch ( VariableUndefinedException e ) { /* this cannot happen */ }
		
		return super.toString() + "(target: " + jumpTarget + ", variable: " + this.variable + ")";
	}
}
