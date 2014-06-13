package logo.commands;

import logo.Command;
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
		
		Integer value = ParsingUtils.getVariableValue( variable );
		value--;
		ParsingUtils.setVariableValue( variable, value );
		
	}

	public int getTarget() {
		return target;
	}
	
	@Override
	public String toString() {
		return super.toString() + "(target: " + target + ", variable: " + variable + ")";
	}
}
