package logo.parsers;

import logo.Command;
import logo.IParser;
import logo.commands.TurnCommand;

public class TurnParser implements IParser {

	private static final String CMD_LEFT	= "left";
	private static final String CMD_RIGHT	= "right";
	
	@Override
	public Command parse( String input ) {

		String[] words = input.split( "\\s+" );
		if ( words.length != 2 )
			return null;
		
		int factor;
		switch ( words[0] ) {

		case CMD_LEFT:
			factor = 1;
			break;
			
		case CMD_RIGHT:
			factor = -1;
			break;
		
		default: return null;
			
		}
		
		int amount;
		try {
			amount = Integer.parseInt( words[1] );
		}
		catch ( NumberFormatException e ) {
			
			return new TurnCommand( words[1] );
		}
		
		return new TurnCommand( factor * amount );
	}

}
