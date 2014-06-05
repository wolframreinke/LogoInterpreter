package logo.commands;

import logo.Command;
import logo.Turtle;

public class SimpleCommand extends Command {
	
	public static enum Type {
		RESET,
		CLEAR,
		PENUP,
		PENDOWN
	}
	
	private final Type type;
	
	public SimpleCommand( Type type ) {
		this.type = type;
	}
	
	@Override
	public void execute( Turtle turtle ) {
		
		switch ( type ) {
		
		case RESET:
			turtle.reset();
			break;
			
		case CLEAR:
			break;
			
		case PENUP:
			turtle.setPainting( false );
			break;
			
		case PENDOWN:
			turtle.setPainting( true );
			break;
			
		}
	}

}
