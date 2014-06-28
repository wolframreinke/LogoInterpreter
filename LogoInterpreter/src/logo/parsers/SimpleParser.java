package logo.parsers;

import logo.commands.Command;
import logo.commands.SimpleCommand;

/**
 * <p><code>Parser</code> implementation which parses the Logo statements <code>clear</code>, <code>reset</code>,
 * <code>penup</code> and <code>pendown</code>, i.e. the statements that take no parameters.</p>
 * 
 * <p>The <code>SimpleParser</code>'s {@link #parse(String)} method results in a <code>SimpleCommand</code>
 * instance.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.1
 */
public class SimpleParser implements Parser {

	// String constants used to recognize the keywords.
	private static final String CMD_CLEAR	= "clear";
	private static final String CMD_RESET	= "home";
	private static final String CMD_PENUP	= "penup";
	private static final String CMD_PENDOWN	= "pendown";
	
	@Override
	public String[] getKeywords() {

		return new String[] { CMD_CLEAR, CMD_RESET, CMD_PENUP, CMD_PENDOWN };
	}

	/**
	 * <p><code>SimpleParser</code>s return a <code>SimpleCommand</code> as the
	 * result of thier <code>parse</code> method. Following Logo statements can
	 * be parsed using this method:</p>
	 * <ul>
	 * 		<li>"<b><code>clear</code></b>"<br>
	 * 			This statement results in a <code>SimpleCommand</code> with the type
	 * 			<code>CLEAR</code>.The resulting command cleans a turtles canvas.
	 * 			</li><br>
	 * 		<li>"<b><code>home</code></b>"<br>
	 * 			This statement results in a <code>SimpleCommand</code> with the type
	 * 			<code>RESET</code>. The resulting command resets all attributes
	 * 			of a turtle.</li><br>
	 * 		<li>"<b><code>penup</code></b>"<br>
	 * 			This statement results in a <code>SimpleCommand</code> with the type
	 * 			<code>PENUP</code>. The resulting command makes a turtle painting.
	 * 			</li><br>
	 * 		<li>"<b><code>pendown</code></b>"<br>
	 * 			This statement results in a <code>SimpleCommand</code> with the type
	 * 			<code>PENDOWN</code>. The resulting command makes a turtle not 
	 * 			painting.</li>
	 * </ul>
	 * 
	 * <p>If the given input statement could not be parsed correctly, <code>null</code>
	 * is returned. If the given input statement itself is <code>null</code>, a 
	 * <code>NullPointerException</code> is thrown.</p>
	 * 
	 * @param words			The input statement which shall be parsed. This array
	 * 						must not be <code>null</code>.
	 * @param lineNumber	The line number where the input statement was found.
	 * 						This value is used to create the <code>SimpleCommand</code>.
	 * @return				An instance of <code>SimpleCommand</code> whose type depends
	 * 						on the input statement. If the given input statement could
	 * 						not be parsed correctly, <code>null</code> is returned.
	 */
	@Override
	public Command parse( String[] words, int lineNumber ) {
		
		// simple commands only consist of one word
		if ( words.length != 1 )
			return null;
		
		Command command;
		// Check which type the resulting command shall have
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
			
		default: return null;	// If none of the types match, the given input is
								// not parseable by this parser.
		
		}
		
		// set the line number of the created statement and return it
		command.setLineNumber( lineNumber );
		return command;
	}

}
