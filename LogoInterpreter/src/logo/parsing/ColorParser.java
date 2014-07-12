package logo.parsing;

import java.util.NoSuchElementException;

import logo.commands.ColorCommand;
import logo.commands.Command;
import logo.commands.Variable;

/**
 * <p>A <code>ColorParser</code> is a {@link Parser} which parses the Logo statement
 * <code>color &lt;id&gt;</code>. The {@link #parse(String[], int)} method of this
 * <code>Parser</code> implementation will return a {@link ColorCommand}.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 *
 */
public class ColorParser extends Parser {

	// String constant to recognize the keyword
	public static final String CMD_COLOR	= "color";
	
	/**
	 * Returns the keyword of this <code>Parser</code>, namely {@value #CMD_COLOR}.
	 */
	@Override
	public String[] getKeywords() {

		return new String[] { CMD_COLOR };
	}

	/**
	 * <p>This method parses the tokens <code><b>color &lt;id&gt;</b></code>, where
	 * <code>id</code> is an integer. At the time of parsing, no constraints regarding
	 * the valid ranges are checked. Alternativly, <code>id</code> can be a
	 * variable identifier.</p>
	 * 
	 * <p>If the input tokens form a valid <code>color</code> statement, this method
	 * will return a {@link ColorCommand}, whose <code>id</code> is set to the
	 * given value. If the input cannot be parsed, <code>null</code> is returned.</p>
	 * 
	 * @param stream
	 * 		The <code>TokenStream</code> which is used to retrieve the input tokens.
	 * 		This stream must not be <code>null</code>.
	 * 
	 * @return
	 * 		A <code>ColorCommand</code>, initialized with the <code>id</code> given
	 * 		in the token stream; <code>null</code>, if the tokens could not be
	 * 		parsed correctly.
	 * 		
	 */
	@Override
	public Command parse( TokenStream stream ) {

		try {
			String word = stream.getNext();

			// only "color" is permitted as the first word
			if ( word.equals( CMD_COLOR ) ) {

				String argument = stream.getNext();

				Command command = null;
				try {
					// try to parse the id from the statements argument
					int id = Integer.parseInt( argument );
					command = new ColorCommand( id );
				}
				catch ( NumberFormatException e ) {

					// argment is no number, therefore it has to be a variable
					// identifier.
					Variable variable = Variable.createVariable( argument );
					command = new ColorCommand( variable );
				}

				return command;
				
			} else return null;
		}
		catch ( NoSuchElementException e ) {

			// reached the end of the stream unsuspectedly early
			return null;
		}
	}
}
