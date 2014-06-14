package logo.commands;

import logo.Command;
import logo.ParsingUtils;
import logo.Turtle;
import logo.VariableUndefinedException;

public class MoveCommand extends Command {

	private int distance;
	private String variableDistance;
	
	public MoveCommand( int distance ) {
		this.distance = distance;
		this.variableDistance = null;
	}
	
	public MoveCommand( String distance ) {
		this.variableDistance = distance;
		this.distance = 0;
	}
	
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {
		
		int distance = this.distance;
		if ( this.variableDistance != null ) 
			distance = ParsingUtils.getVariableValue( this.variableDistance );
		
		turtle.move( distance );
	}

	@Override
	public String toString() {
		String distanceString;
		if ( this.variableDistance == null )
			distanceString = "distance: " + String.valueOf( this.distance );
		else
			distanceString = "variable: " + this.variableDistance;
		
		return super.toString() + "(" + distanceString + ")";
	}
}
