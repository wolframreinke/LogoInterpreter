package logo.parsers;

import logo.commands.Command;
import logo.commands.MoveCommand;

/**
 * <p><code>Parser</code> implementation which parses the Logo statements <code>forward</code> and 
 * <code>backward</code>, i.e. the commands which directly change the turtles coordinates. Changing
 * the orientation of the turtle could be considered a move too, but is implemented in
 * {@link TurnParser}.</p>
 * 
 * <p>The <code>MoveParser</code>'s {@link #parse(String)} method results in a 
 * <code>MoveCommand</code> instance.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.1
 */
public class MoveParser implements Parser {

	// String constants to recognize the keywords.
	private static final String CMD_FORWARD 	= "forward";
	private static final String CMD_BACKWARD 	= "backward";
	
	@Override
	public String[] getKeywords() {

		return new String[] { CMD_FORWARD, CMD_BACKWARD };
	}
	
	/**
	 * <p>The <code>MoveParser</code> returns a <code>MoveCommand</code> as a result of this
	 * method. Following Logo statements can be parsed using this parser:</p>
	 * <ul>
	 *		<li>"<b><code>forward &lt;distance&gt;</code></b>"<br>
	 *			The	<code>distance</code> can be either an integer number or 
	 *			a variable identifier. This statement results in a 
	 *			<code>MoveCommand</code> whose <code>distance</code> attribute is
	 *			set to the given distance.
	 *		</li><br>
	 *		<li>"<b><code>backward &lt;distance&gt;</code></b>"<br>
	 *			The	<code>distance</code> can be either an integer number or 
	 *			a variable identifier. This statement results in a 
	 *			<code>MoveCommand</code> whose <code>distance</code> attribute is
	 *			set to the given distance multiplied with -1 to indicate backward
	 *			moving.
	 *		</li>
	 * </ul>
	 * 
	 * <p>If the given statement could not be parsed correctly, <code>null</code> is
	 * returned. If the given statement is <code>null</code> a <code>NullPointerException</code>
	 * is thrown.</p>
	 * 
	 * @param words			The input statement which shall be parsed. This array
	 * 						must not be <code>null</code>.
	 * @param lineNumber	The line number where the input statement was found.
	 * 						This value is used to create the <code>MoveCommand</code>.
	 * @return				An instance of <code>MoveCommand</code>, intialized with
	 * 						the distance given in the input statement.
	 */
	@Override
	public Command parse( String[] words, int lineNumber ) {
		
		if ( words.length != 2 )
			return null;
		
		MoveCommand.Type type;
		switch ( words[0].toLowerCase() ) {
		
		case CMD_FORWARD:	// command is "forward n"
			type = MoveCommand.Type.FORWARD;
			break;
			
		case CMD_BACKWARD:	// command is "backward n"
			type = MoveCommand.Type.BACKWARD;
			break;
			
		default: return null;	// the statement cannot be parsed using this
								// parser
		
		}
		
		MoveCommand result;
		String distanceString = words[1];

		try {
			// Check whether the argument is a number. If so, use the
			// integer-constructor of MoveCommand
			int distance = Integer.parseInt( distanceString );
			result = new MoveCommand( type, distance );
		} 
		catch ( NumberFormatException nfExcp ) {
			
			// distanceString is certainly not a number.
			// It's assumed, that the given string is a variable identifier
			result = new MoveCommand( type, distanceString );
		}
		
		result.setLineNumber( lineNumber );
		return result;
	}

}
