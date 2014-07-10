package logo.parsing;

import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;

import logo.commands.Command;
import logo.commands.Variable;
import logo.commands.VariableCommand;

/**
 * <p><code>Parser</code> implementation which parses the Logo statements <code>let</code>,
 * <code>increment</code> and <code>decrement</code>, i.e. the commands which change the
 * values of variables used in the users Logo scripts. <code>repeat</code>-loops also
 * change the values of variables, they are implemented in their own class 
 * (namely {@link LoopParser}) anyway.</p>
 * 
 * <p>The <code>VariableParser</code>'s <code>parse</code> method results in a
 * {@link VariableCommand} object.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 */
public class VariableParser extends Parser {

	// String constants to recognize keywords
	private static final String CMD_LET			= "let";
	private static final String CMD_INCREMENT 	= "increment";
	private static final String CMD_DECREMENT	= "decrement";
	
	/**
	 * The syntax errors which occurred during the parsing procedure.
	 */
	private Collection<SyntaxError> syntaxErrors = new HashSet<SyntaxError>( 1 );
	
	/**
	 * Returns the keywords of this <code>Parser</code>, namely,
	 * {@value #CMD_LET},
	 * {@value #CMD_INCREMENT} and
	 * {@value #CMD_DECREMENT}.
	 */
	@Override
	public String[] getKeywords() {

		return new String[] { CMD_LET, CMD_INCREMENT, CMD_DECREMENT };
	}
	
	/**
	 * <p>The <code>VariableParser</code> returns a <code>VariableCommand</code>
	 * as the result of this method. Following Logo statements can be parsed
	 * using this method:</p>
	 * <ul>
	 * 		<li> "<b><code>let &lt;target&gt; &lt;value&gt;</code></b>"<br>
	 * 			The <code>target</code> is interpreted as a variable identifier,
	 * 			so in "<code>let 1 2</code>" the "1" is interpreted as a variable
	 * 			named "1". The <code>value</code> can be either an integer value
	 * 			or a variable identifier.
	 * 			This kind of statement results in a <code>VariableCommand</code> with
	 * 			the type <code>LET</code>.</li><br>
	 * 		<li> "<b><code>increment &lt;target&gt; &lt;value&gt;</code></b>"<br>
	 * 			The <code>target</code> and the <code>value</code> are interpreted
	 * 			as described above.
	 * 			This kind of statement results in a <code>VariableCommand</code> with
	 * 			the type <code>INCREMENT</code>.</li><br>
	 * 		<li> "<b><code>decrement &lt;target&gt; &lt;value&gt;</code></b>"<br>
	 * 			The <code>target</code> and the <code>value</code> are interpreted
	 * 			as described above.
	 * 			This kind of statement results in a <code>VariableCommand</code> with
	 * 			the type <code>DECREMENT</code>.</li><br>
	 * </ul>
	 * 
	 * <p>If the given statement could not be parsed, <code>null</code> is returned.
	 * If words is <code>null</code>, a <code>NullPointerException</code> is 
	 * thrown.</p>
	 * 
	 * @param words			The input statement which shall be parsed, splitted into
	 * 						single words. This array must not be <code>null</code>.
	 * @param lineNumber	The line number, where the statement was found. This
	 * 						value is used to create the <code>VariableCommand</code>.
	 * @return				An instance of <code>VariableCommand</code>, initialized
	 * 						with the values given in the Logo statement. If the 
	 * 						statement could not be parsed correctly, <code>null</code>
	 * 						is returned.
	 */
	@Override
	public Command parse( TokenStream stream, int lineNumber ) {
		
		try {
			String word = stream.getNext();
			VariableCommand.Type type;
			
			// Find out the type of the create command
			switch ( word ) {
			
			case CMD_LET:
				type = VariableCommand.Type.LET;
				break;
				
			case CMD_INCREMENT:
				type = VariableCommand.Type.INCREMENT;
				break;
				
			case CMD_DECREMENT:
				type = VariableCommand.Type.DECREMENT;
				break;

			default: return null;	// If none of the given keywords matches,
									// the statement is not parseable by this parser
			
			}
			
			Command command;
			
			// The name of the target variable is the first argument.
			// It does not need to be checked.
			String targetVariable = stream.getNext();
			String assignedVariable = stream.getNext();
			
			try {
				// Check whether the second argument of the command is a number.
				// If so, use the integer-constructor.
				int assignedValue = Integer.parseInt( assignedVariable );
				Variable target = Variable.createVariable( targetVariable );
				
				command = new VariableCommand( target, assignedValue, type );
			}
			catch ( NumberFormatException e ) {
				
				// The second argument is certainly not a number. Therefore, use
				// the String-constructor of VariableCommand
				Variable target = Variable.createVariable( targetVariable );
				Variable assigned = Variable.createVariable( assignedVariable );
				
				command = new VariableCommand( target, assigned, type );
			}
			
			// Set the line number for later use and return the created command.
			command.setLineNumber( lineNumber );
			return command;
		}
		catch ( NoSuchElementException e ) {
			
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 * 		The syntax errors which occurred during the parsing procedure.
	 */
	@Override
	public Collection<SyntaxError> getSyntaxErrors() {
	
		return this.syntaxErrors;
	}
}
