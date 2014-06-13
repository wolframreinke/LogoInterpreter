package logo.parsers;

import logo.Command;
import logo.Parser;
import logo.Interpreter;
import logo.commands.ConditionalJumpCommand;
import logo.commands.NullCommand;
import logo.commands.StaticJumpCommand;

public class LoopParser implements Parser {

	@Override
	public Command parse( String[] words, int lineNumber ) {

		if ( words.length == 1 ) {
			
			if ( words[0].equals( "[" ) ) {
				NullCommand result = new NullCommand();
				result.setLineNumber( lineNumber );
				return result;
			}
			else if ( words[0].equals( "]" ) ) {
				
				ConditionalJumpCommand loopHead = Interpreter.popJumpCommand();
				loopHead.setTargetLineNumber( lineNumber + 1 );
				int target = loopHead.getLineNumber();
				String variable = loopHead.getConditionVariable();
				
				StaticJumpCommand result = new StaticJumpCommand( target, variable );
				result.setLineNumber( lineNumber );
				return result;
			}
			else return null;
		}
		else if ( words.length == 2 ) {
			
			if ( words[0].equals( "repeat" ) ) {
				
				ConditionalJumpCommand command;
				try {
					Integer parameter = Integer.parseInt( words[1] );
					
					command = new ConditionalJumpCommand( parameter );
				}
				catch ( NumberFormatException e ) {
					command = new ConditionalJumpCommand( words[1] );
				}
				
				command.setLineNumber( lineNumber );
				Interpreter.pushJumpCommand( command );
				return command;
			}
			else return null;
		}
		else return null;
	}

}
