package logo.parsers;

import logo.Command;
import logo.IParser;
import logo.commands.TurnCommand;

public class TurnParser implements IParser {

	private static final String CMD_LEFT	= "left";
	private static final String CMD_RIGHT	= "right";
	
	@Override
	public Command parse( String input, int lineNumber ) {

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
		
		TurnCommand result;
		int amount;
		try {
			amount = Integer.parseInt( words[1] );
		}
		catch ( NumberFormatException e ) {
			
			result = new TurnCommand( words[1] );
			result.setLineNumber( lineNumber );
			return result;
		}
		
		result = new TurnCommand( factor * amount );
		result.setLineNumber( lineNumber );
		return result;
	}

}
