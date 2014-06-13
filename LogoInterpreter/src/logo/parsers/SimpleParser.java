package logo.parsers;

import logo.Command;
import logo.Parser;
import logo.commands.SimpleCommand;

/**
 * <p><code>IParser</code> instance which parses the Logo statements <code>clear</code>, <code>reset</code>,
 * <code>penup</code> and <code>pendown</code>, i.e. the statements that take no parameters.</p>
 * 
 * <p>The <code>SimpleParser</code>'s {@link #parse(String)} method results in a <code>SimpleCommand</code>
 * instance.</p>
 * 
 * @author Wolfram Reinke
 *
 */
public class SimpleParser implements Parser {

	private static final String CMD_CLEAR	= "clear";
	private static final String CMD_RESET	= "home";
	private static final String CMD_PENUP	= "penup";
	private static final String CMD_PENDOWN	= "pendown";
	
	/**
	 * <p>The <code>SimpleParser</code> returns a <code>SimpleCommand</code> as result of
	 * this method. Following Logo statements can be parsed using this parser:</p>
	 * <ul>
	 * 		<li> "<b><code>clear</code></b>"<br>
	 * 			This comman results in a <code>SimpleCommand</code> whose type is set to
	 * 			<code>ISimpleCommandType.CLEAR</code>.
	 * 		</li>
	 * 		<li> "<b><code>reset</code></b>"<br>
	 * 			This comman results in a <code>SimpleCommand</code> whose type is set to
	 * 			<code>ISimpleCommandType.RESET</code>.
	 * 		</li>
	 * 		<li> "<b><code>penup</code></b>"<br>
	 * 			This comman results in a <code>SimpleCommand</code> whose type is set to
	 * 			<code>ISimpleCommandType.PENUP</code>.
	 * 		</li>
	 * 		<li> "<b><code>pendown</code></b>"<br>
	 * 			This comman results in a <code>SimpleCommand</code> whose type is set to
	 * 			<code>ISimpleCommandType.PENDOWN</code>.
	 * 		</li>
	 * </ul>
	 * 
	 * @param input		The Logo statement to parse.
	 * @return			A fully initialized <code>SimpleCommand</code> instance.
	 */
	@Override
	public Command parse( String[] words, int lineNumber ) {
		
		if ( words.length != 1 )
			return null;
		
		Command command;
		switch ( words[0] ) {
		
		case CMD_CLEAR:
			command = new SimpleCommand( SimpleCommand.Type.CLEAR );
			break;
			
		case CMD_RESET:
			command = new SimpleCommand( SimpleCommand.Type.RESET );
			break;
			
		case CMD_PENUP:
			command = new SimpleCommand( SimpleCommand.Type.PENUP );
			break;

		case CMD_PENDOWN:
			command = new SimpleCommand( SimpleCommand.Type.PENDOWN );
			break;
			
		default: return null;
		
		}
		
		command.setLineNumber( lineNumber );
		return command;
	}

}
