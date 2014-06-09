package logo.commands;

import logo.Command;
import logo.Interpreter;
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
		
		Integer value = Interpreter.getVariableValue( variable );
		value--;
		Interpreter.setVariableValue( variable, value );
		
	}

	public int getTarget() {
		return target;
	}
	
	@Override
	public String toString() {
		return super.toString() + "(target: " + target + ", variable: " + variable + ")";
	}
}
