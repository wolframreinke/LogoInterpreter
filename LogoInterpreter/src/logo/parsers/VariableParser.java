package logo.parsers;

import logo.Command;
import logo.Parser;
import logo.commands.VariableCommand;

/**
 * <p><code>IParser</code> implementation which parses the Logo statements <code>let</code>,
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
public class VariableParser implements Parser {

	private static final String CMD_LET			= "let";
	private static final String CMD_INCREMENT 	= "increment";
	private static final String CMD_DECREMENT	= "decrement";
	
	/**
	 * <p>The <code>VariableParser</code> returns a <code>VariableCommand</code>
	 * as the result of this method. Following Logo statements can be parsed
	 * using this method:</p>
	 * <ul>
	 * 		<li> "<b><code>let &lt;target&gt; &lt;value&gt;</code></b>"<br>
	 * 			The <code>target</code> is interpreted as a variable identifier,
	 * 			so in "<code>let 1 2</code>" the "1" is interpreted as a variable
	 * 			named "1". (TODO?)
	 * 			This kind of statement result in a <code>VariableCommand</code> with
	 * 			the type <code>LET</code>.</li><br>
	 * 		<li> "<b><code>increment &lt;target&gt; &lt;value&gt;</code></b>"<br>
	 * 			The <code>target</code> is interpreted as a variable identifier
	 * 			as described above.</li><br>
	 * 		<li> "<b><code>decrement &lt;target&gt; &lt;value&gt;</code></b>"<br></li><br>
	 * </ul>
	 * 
	 * @param words
	 * @param lineNumber
	 * @return
	 */
	@Override
	public Command parse( String[] words, int lineNumber ) {
		
		if ( words.length != 3 )
			return null;
		
		VariableCommand.Type type;
		
		switch ( words[0] ) {
		
		case CMD_LET:
			type = VariableCommand.Type.LET;
			break;
			
		case CMD_INCREMENT:
			type = VariableCommand.Type.INCREMENT;
			break;
			
		case CMD_DECREMENT:
			type = VariableCommand.Type.DECREMENT;
			break;

		default: return null;
		
		}
		
		Command command;
		
		String targetVariable = words[1];
		String assignedVariable = words[2];
		try {
			int assignedValue = Integer.parseInt( assignedVariable );
			command = new VariableCommand( targetVariable, assignedValue, type );
		}
		catch ( NumberFormatException e ) {
			command = new VariableCommand( targetVariable, assignedVariable, type );
		}
		
		command.setLineNumber( lineNumber );
		return command;
	}

}
