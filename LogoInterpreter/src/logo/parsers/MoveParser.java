package logo.parsers;

import logo.Command;
import logo.IParser;
import logo.commands.MoveCommand;

/**
 * <p><code>IParser</code> implementation which parses the Logo statements <code>forward</code> and 
 * <code>backward</code>, i.e. the commands which directly change the turtles coordinates. Changing
 * the orientation of the turtle could be considered a move too, but is implemented in
 * {@link TurnParser}.</p>
 * 
 *  <p>The <code>MoveParser</code>'s {@link #parse(String)} method results in a 
 *  <code>MoveCommand</code> instance.</p>
 * 
 * @author Wolfram Reinke
 *
 */
public class MoveParser implements IParser {

	private static final String CMD_FORWARD 	= "forward";
	private static final String CMD_BACKWARD 	= "backward";
	
	/**
	 * <p>The <code>MoveParser</code> returns a <code>MoveCommand</code> as a result of this
	 * method. Following Logo statements can be parsed using this parser:</p>
	 * <ul>
	 *		<li>"<b><code>forward &lt;number or identifier&gt; </code></b>"<br>
	 *			
	 *		</li>
	 *		<li>"<b><code>backward &lt;number or identifier&gt;</code></b>"<br>
	 *			
	 *		</li>
	 * </ul>
	 * 
	 * @param input
	 * @return
	 */
	@Override
	public Command parse( String input ) {
		
		String[] words = input.split( "\\s+" );
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
		
		String distanceString = words[1]; // The if-statement above makes this safe
		int distance;

		try {
			distance = Integer.parseInt( distanceString );
		} 
		catch ( NumberFormatException nfExcp ) {
			
			// distanceString is certainly not a number.
			// It's assumed, that the given string is a variable identifier
			return new MoveCommand( distanceString );
		}
		
		return new MoveCommand( factor * distance );
	}

}
