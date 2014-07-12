package logo.parsing;

import java.util.NoSuchElementException;

import logo.commands.Command;
import logo.commands.TurnCommand;
import logo.commands.Variable;

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
public class TurnParser extends Parser {

	// String constants used to recognize logo keywords
	private static final String CMD_LEFT	= "left";
	private static final String CMD_RIGHT	= "right";
	
	/**
	 * Returns the keywords of this Parser, namely
	 * {@value #CMD_LEFT} and
	 * {@link #CMD_RIGHT}
	 */
	@Override
	public String[] getKeywords() {
	
		return new String[] { CMD_LEFT, CMD_RIGHT };
	}
	
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
	 * @param stream
	 * 		The <code>TokenStream</code> which is used to retrieve the tokens to
	 * 		parse.
	 * 
	 * @return				
	 * 		An instance of <code>TurnCommand</code>, initialized with
	 * 		the angle given in the input tokens. If the input
	 * 		could not be parsed correctly, <code>null</code> is returned.
	 */
	@Override
	public Command parse( TokenStream stream ) {
		try {
			String word = stream.getNext();
			
			// found out, whether this is a "left" or "right" statement
			TurnCommand.Type type;
			switch ( word ) {
	
			case CMD_LEFT:
				type = TurnCommand.Type.LEFT;
				break;
				
			case CMD_RIGHT:
				type = TurnCommand.Type.RIGHT;
				break;
			
			default: return null;	// If the first word is neither "left" nor "right",
									// the statement is not parseable by this parser.
				
			}
			
			String argument = stream.getNext();
			TurnCommand result;
			
			// retrieve the turn angle
			try {
				// Check whether the given angle is a number. If so, use the
				// integer constructor of TurnCommand
				int amount = Integer.parseInt( argument );
				result = new TurnCommand( type, amount );
			}
			catch ( NumberFormatException e ) {
				
				// The given angle is certainly not a number. Therefore, use the
				// string constructor of TurnCommand
				Variable angleVariable = Variable.createVariable( argument );
				result = new TurnCommand( type, angleVariable );
			}

			return result;
		}
		catch ( NoSuchElementException e ) {
			
			// the input stream contained less then the required 2 elements
			return null;
		}
	}
}
