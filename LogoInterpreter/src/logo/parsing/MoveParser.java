package logo.parsing;

import java.util.NoSuchElementException;

import logo.commands.Command;
import logo.commands.MoveCommand;
import logo.commands.Variable;

/**
 * <p><code>Parser</code> subclass which parses the tokens <code>forward</code> and 
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
public class MoveParser extends Parser {

	// String constants to recognize the keywords.
	private static final String CMD_FORWARD 	= "forward";
	private static final String CMD_BACKWARD 	= "backward";
	
	/**
	 * Returns the keywords of this parser, namely 
	 * {@value #CMD_FORWARD} and {@value #CMD_BACKWARD}.
	 * 
	 * @return
	 */
	@Override
	public String[] getKeywords() {

		return new String[] { CMD_FORWARD, CMD_BACKWARD };
	}
	
	/**
	 * <p>The <code>MoveParser</code> returns a <code>MoveCommand</code> as a result of this
	 * method. Following sequences of tokens can be parsed using this parser:</p>
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
	 * <p>If the given stream could not be parsed correctly, <code>null</code> is
	 * returned. If the given stream is<code>null</code> a <code>NullPointerException</code>
	 * is thrown.</p>
	 * 
	 * @param stream			
	 * 		The <code>TokenStream</code> which is used to retrieve the input to
	 * 		parse. This stream must not be <code>null</code>.
	 * 
	 * @return				
	 * 		An instance of <code>MoveCommand</code>, intialized with
	 * 		the distance given in the input tokens.
	 */
	@Override
	public Command parse( TokenStream stream ) {
		try {
			String word = stream.getNext();
			
			MoveCommand.Type type;
			switch ( word ) {
			
			case CMD_FORWARD:		// command is "forward n"
				type = MoveCommand.Type.FORWARD;
				break;
				
			case CMD_BACKWARD:		// command is "backward n"
				type = MoveCommand.Type.BACKWARD;
				break;
				
			default: return null;	// the statement cannot be parsed using this
									// parser
			
			}
			
			String argument = stream.getNext();
			Command command;
			try {
			
				// try to parse a number out of the argument
				int distance = Integer.parseInt( argument );
				command = new MoveCommand( type, distance );
			}
			catch ( NumberFormatException e ) {
				
				// the argument is certainly not a number. therefore the
				// variable constructor is used
				Variable distanceVariable = Variable.createVariable( argument );
				command = new MoveCommand( type, distanceVariable );
			}
			
			return command;
		}
		catch ( NoSuchElementException e ) {
			
			// end of token stream was reached to early
			return null;
		}
	}
}
