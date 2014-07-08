package logo.parsers;

import logo.commands.ColorCommand;
import logo.commands.Command;

/**
 * <p>A <code>ColorParser</code> is a {@link Parser} which parses the Logo statement
 * <code>color &lt;id&gt;</code>. The {@link #parse(String[], int)} method of this
 * <code>Parser</code> implementation will return a {@link ColorCommand}.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 *
 */
public class ColorParser implements Parser {

	// String constant to recognize the keyword
	public static final String CMD_COLOR	= "color";
	
	@Override
	public String[] getKeywords() {

		return new String[] { CMD_COLOR };
	}

	/**
	 * <p>This method parses the statement <code><b>color &lt;id&gt;</b></code>, where
	 * <code>id</code> is an integer. At the time of parsing, no constraints regarding
	 * the valid ranges are checked. Alternativly, <code>id</code> can be a
	 * variable identifier.</p>
	 * 
	 * <p>If the input words form a valid <code>color</code> statement, this method
	 * will return a {@link ColorCommand}, whose <code>id</code> is set to the
	 * given value. If the input cannot be parsed, <code>null</code> is returned.</p>
	 * 
	 * @param words
	 * 		The input statement which shall be parsed, splitted into words. This
	 * 		array must not be <code>null</code>.
	 * 
	 * @param lineNumber
	 * 		The line number, where the Logo statement was found. This value is used
	 * 		to construct the <code>ColorCommand</code>.
	 * 
	 * @return
	 * 		A <code>ColorCommand</code>, initialized with the <code>id</code> given
	 * 		in the Logo statement; <code>null</code>, if the statement could not be
	 * 		parsed correctly.
	 * 		
	 */
	@Override
	public Command parse( String[] words, int lineNumber ) {

		// color <id> consists of two words
		if ( words.length == 2 && words[0].equals( CMD_COLOR ) ) {
			
			Command command = null;
			try {
				// try to parse the id from the statements argument
				int id = Integer.parseInt( words[1] );
				command = new ColorCommand( id );
			}
			catch ( NumberFormatException e ) {
				
				// words[1] is no number, therefore it has to be a variable
				// identifier.
				command = new ColorCommand( words[1] );
			}
			
			// complete initalization and return
			command.setLineNumber( lineNumber );
			return command;
		} 
		else return null;
	}

}
