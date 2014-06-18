package logo.parsers;

import logo.commands.Command;
import logo.commands.TurnCommand;

/**
 * <p><code>TurnParser</code> is a <code>Parser</code> implementation which parses the
 * statements "<code>left</code>" and "<code>right</code>", i.e. the statements which
 * change the orientation of a turtle.</p>
 * 
 * <p>A call to the {@link #parse(String[], int)} method of this class results in
 * a {@link TurnCommand}.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 */
public class TurnParser implements Parser {

	// String constants used to recognize logo keywords
	private static final String CMD_LEFT	= "left";
	private static final String CMD_RIGHT	= "right";
	
	/**
	 * <p>A <code>TurnParser</code> returns a <code>TurnCommand</code> as the result
	 * of this method. Following logo statements can be parsed using this method:</p>
	 * <ul>
	 * 		<li>"<b><code>left &lt;angle&gt;</code></b>"<br>
	 * 			The <code>angle</code> can be an integer number or a variable
	 * 			identifier. This statement results in a <code>TurnCommand</code> whose
	 * 			<code>amount</code> attribute is set to the given angle.</li><br>
	 * 		<li>"<b><code>right &lt;angle&gt;</code></b>"<br>
	 * 			The <code>angle</code> can be an integer number or a variable
	 * 			identifier. This statement results in a <code>TurnCommand</code> whose
	 * 			<code>amount</code> attribute is set to the given angle multiplied by
	 * 			-1 to indicate the clockwise rotation.</li>
	 * </ul>
	 * 
	 * <p>If the given input statement could not be parsed correctly, <code>null</code>
	 * is returned. If the given input statement is <code>null</code>, a 
	 * <code>NullPointerException</code> is thrown.</p>
	 * 
	 * @param words			The input statement which shall be parsed. This value must
	 * 						not be <code>null</code>.
	 * @param lineNumber	The line number where the input statement was found. This
	 * 						value is used to create the <code>TurnCommand</code>.
	 * @return				An instance of <code>TurnCommand</code>, initialized with
	 * 						the angle given in the input statement. If the input
	 * 						could not be parsed correctly, <code>null</code> is returned.
	 */
	@Override
	public Command parse( String[] words, int lineNumber ) {

		// left/right commands consist of two words
		if ( words.length != 2 )
			return null;
		
		TurnCommand.Type type;
		switch ( words[0] ) {

		case CMD_LEFT:
			type = TurnCommand.Type.LEFT;
			break;
			
		case CMD_RIGHT:
			type = TurnCommand.Type.RIGHT;
			break;
		
		default: return null;	// If the first word is neither "left" nor "right",
								// the statement is not parseable by this parser.
			
		}
		
		TurnCommand result;
		
		// retrieve the turn angle
		try {
			// Check whether the given angle is a number. If so, use the
			// integer constructor of TurnCommand
			int amount = Integer.parseInt( words[1] );
			result = new TurnCommand( type, amount );
		}
		catch ( NumberFormatException e ) {
			
			// The given angle is certainly not a number. Therefore, use the
			// string constructor of TurnCommand
			result = new TurnCommand( type, words[1] );
		}
		
		result.setLineNumber( lineNumber );
		return result;
	}

}
