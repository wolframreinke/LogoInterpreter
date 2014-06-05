package logo.commands;

import logo.Command;
import logo.Interpreter;
import logo.Turtle;
import logo.VariableUndefinedException;

public class TurnCommand extends Command {

	private int amount;
	private String variableAmount;
	
	public TurnCommand( int amount ) {
		this.amount = amount;
		this.variableAmount = null;
	}
	
	public TurnCommand( String variableAmount ) {
		this.amount = 0;
		this.variableAmount = variableAmount;
	}
	
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {

		int amount = this.amount;
		if ( variableAmount != null )
			amount = Interpreter.getVariableValue( variableAmount );
		
		turtle.turn( amount );
	}

}
