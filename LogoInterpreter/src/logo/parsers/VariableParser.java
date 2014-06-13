package logo.parsers;

import logo.Command;
import logo.Parser;
import logo.commands.VariableCommand;

public class VariableParser implements Parser {

	private static final String CMD_LET			= "let";
	private static final String CMD_INCREMENT 	= "increment";
	private static final String CMD_DECREMENT	= "decrement";
	
	@Override
	public Command parse( String[] words, int lineNumber ) {
		
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
		
		String targetVariable = words[1];
		String assignedVariable = words[2];
		try {
			int assignedValue = Integer.parseInt( assignedVariable );
			command = new VariableCommand( targetVariable, assignedValue, type );
		}
		catch ( NumberFormatException e ) {
			command = new VariableCommand( targetVariable, assignedVariable, type );
		}
		
		command.setLineNumber( lineNumber );
		return command;
	}

}
