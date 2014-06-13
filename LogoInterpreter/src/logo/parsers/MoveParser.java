package logo.parsers;

import logo.Command;
import logo.Parser;
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
		
		// factor will be either -1 or 1 to distinguish between forward and
		// backward moving. This factor will be multiplied with the actual distance
		int factor;
		switch ( words[0].toLowerCase() ) {
		
		case CMD_FORWARD:	// command is "forward n"
			factor = 1;
			break;
			
		case CMD_BACKWARD:	// command is "backward n"
			factor = -1;
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
			result = new MoveCommand( factor * distance );
		} 
		catch ( NumberFormatException nfExcp ) {
			
			// distanceString is certainly not a number.
			// It's assumed, that the given string is a variable identifier
			result = new MoveCommand( distanceString );
			result.setLineNumber( lineNumber );
			return result;
		}
		
		result.setLineNumber( lineNumber );
		return result;
	}

}
