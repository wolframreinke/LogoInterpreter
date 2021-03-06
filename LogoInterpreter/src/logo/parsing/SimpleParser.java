package logo.parsing;

import java.util.NoSuchElementException;

import logo.commands.Command;
import logo.commands.SimpleCommand;

/**
 * <p><code>Parser</code> implementation which parses the tokens <code>clear</code>, <code>reset</code>,
 * <code>penup</code> and <code>pendown</code>, i.e. the statements that take no parameters.</p>
 * 
 * <p>The <code>SimpleParser</code>'s {@link #parse(String)} method results in a <code>SimpleCommand</code>
 * instance.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.1
 */
public class SimpleParser extends Parser {

	// String constants used to recognize the keywords.
	private static final String CMD_CLEAR	= "clear";
	private static final String CMD_RESET	= "home";
	private static final String CMD_PENUP	= "penup";
	private static final String CMD_PENDOWN	= "pendown";
	
	/**
	 * Returns the keywords of this <code>Parser</code>, namely
	 * {@value #CMD_CLEAR},
	 * {@value #CMD_PENDOWN},
	 * {@value #CMD_PENUP} and
	 * {@value #CMD_RESET}.
	 */
	@Override
	public String[] getKeywords() {

		return new String[] { CMD_CLEAR, CMD_RESET, CMD_PENUP, CMD_PENDOWN };
	}

	/**
	 * <p><code>SimpleParser</code>s return a <code>SimpleCommand</code> as the
	 * result of thier <code>parse</code> method. Following tokens can
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
	 * <p>If the given input could not be parsed correctly, <code>null</code>
	 * is returned. If the given token stream itself is <code>null</code>, a 
	 * <code>NullPointerException</code> is thrown.</p>
	 * 
	 * @param stream			
	 * 		The <code>TokenStream</code> which is used to retrieve the tokens to
	 * 		parse.
	 * 
	 * @return				
	 * 		An instance of <code>SimpleCommand</code> whose type depends
	 * 		on the input token. If the given input statement could
	 * 		not be parsed correctly, <code>null</code> is returned.
	 */
	@Override
	public Command parse( TokenStream stream ) {
		
		try {
			String word = stream.getNext();
			
			Command command;
			// Check which type the resulting command shall have
			switch ( word ) {
			
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
			
			return command;
			
		}
		catch ( NoSuchElementException e ) {
			
			// the token stream is empty
			return null;
		}
	}
}
