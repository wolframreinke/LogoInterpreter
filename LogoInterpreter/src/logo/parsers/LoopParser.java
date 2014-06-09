package logo.parsers;

import logo.Command;
import logo.IParser;
import logo.Interpreter;
import logo.commands.ConditionalJumpCommand;
import logo.commands.StaticJumpCommand;

public class LoopParser implements IParser {

	@Override
	public Command parse( String input ) {

		String[] words = input.split( "\\s+" );
		
		if ( words.length == 1 ) {
			
			if ( words[0].equals( "[" ) )
				return null;
			else if ( words[0].equals( "]" ) ) {
				
				ConditionalJumpCommand loopHead = Interpreter.popJumpCommand();
				int target = loopHead.getLineNumber();
				String variable = loopHead.getConditionVariable();
				
				return new StaticJumpCommand( target, variable );
			}
			else return null;
		}
		else if ( words.length == 2 ) {
			
			if ( words[0].equals( "repeat" ) ) {
				
				ConditionalJumpCommand command;
				try {
					Integer parameter = Integer.parseInt( words[1] );
					
					String varName = Interpreter.createHelpVariable();
					Interpreter.setVariableValue( varName, parameter );
					command = new ConditionalJumpCommand( varName );
				}
				catch ( NumberFormatException e ) {
					command = new ConditionalJumpCommand( words[1] );
				}
				
				Interpreter.pushJumpCommand( command );
				return command;
			}
			else return null;
		}
		else return null;
	}

}
