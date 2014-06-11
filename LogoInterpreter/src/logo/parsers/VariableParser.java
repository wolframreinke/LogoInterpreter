package logo.parsers;

import logo.Command;
import logo.Parser;
import logo.commands.VariableCommand;

public class VariableParser implements Parser {

	private static final String CMD_LET			= "let";
	private static final String CMD_INCREMENT 	= "increment";
	private static final String CMD_DECREMENT	= "decrement";
	
	@Override
	public Command parse( String input, int lineNumber ) {
		
		String[] words = input.split( "\\s+" );
		if ( words.length != 3 )
			return null;
		
		VariableCommand.Type type;
		
		switch ( words[0] ) {
		
		case CMD_LET:
			type = VariableCommand.Type.LET;
			break;
			
		case CMD_INCREMENT:
			type = VariableCommand.Type.INCREMENT;
			break;
			
		case CMD_DECREMENT:
			type = VariableCommand.Type.DECREMENT;
			break;

		default: return null;
		
		}
		
		Command command;
		
		String firstArgument = words[1];
		String secondArgument = words[2];
		try {
			int value = Integer.parseInt( secondArgument );
			command = new VariableCommand( firstArgument, value, type );
		}
		catch ( NumberFormatException e ) {
			command = new VariableCommand( firstArgument, secondArgument, type );
		}
		
		command.setLineNumber( lineNumber );
		return command;
	}

}
