package logo.commands;

import logo.ParsingUtils;
import logo.Turtle;
import logo.VariableUndefinedException;

public class StaticJumpCommand extends Command {

	private final int target;
	private final String variable;
	
	public StaticJumpCommand( int target, String variable ) {
		this.target = target;
		this.variable = variable;
	}
	
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {
		
		Integer value = ParsingUtils.getVariableValue( this.variable );
		value--;
		ParsingUtils.setVariableValue( this.variable, value );
		
	}

	public int getTarget() {
		return this.target;
	}
	
	@Override
	public String toString() {
		return super.toString() + "(target: " + this.target + ", variable: " + this.variable + ")";
	}
}
