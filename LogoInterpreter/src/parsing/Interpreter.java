package parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Interpreter {

	private static Map<String, Integer> variables = new HashMap<String, Integer>();
	
	private Set<IParser> parsers;
	private List<ICommand> commands;
	
	public Interpreter() {
		super();
		
		// Add command parsers.
		parsers = new HashSet<IParser>();
	}
	
	/**
	 * <p>Parses the given Logo code and saves the resulting commands. These 
	 * commands can be accessed using {@link #getNextCommand()}.</p>
	 * 
	 * <p>This method will overwrite all previosly parsed commands. If you
	 * want to parse the user input statement by statement, you have
	 * to retrieve the parsed <code>ICommand</code> before calling this
	 * method another time.</p>
	 * 
	 * @param sourceCode					The source code to parse. This string
	 * 										must not be <code>null</code>.
	 * @throws ParsingException				Reports a syntax error if a command
	 * 										could not be parsed.
	 * @throws IllegalArgumentException		If the <code>sourceCode</code> is
	 * 										<code>null</code>. If thrown, the
	 * 										previosly parsed commands are not
	 * 										overwritten.
	 */
	public void parse( String sourceCode ) throws ParsingException {
		
		if ( sourceCode == null )
			throw new IllegalArgumentException( "The source code must not be null." );
		
		// Clear previosly parsed commands
		commands = new ArrayList<ICommand>();
		
		// Split the input into an array of statements using the system-dependent
		// line separator
		String[] statements = sourceCode.split( System.lineSeparator() );
		int lineNumber = 1;
		
		for ( String statement : statements ) {
			
			// consult each IParser instance to check whether the statement
			// can be parsed
			ICommand command = null;
			for ( IParser parser : parsers ) {
				
				ICommand returnValue = parser.parse( statement );
				if ( returnValue != null )
					command = returnValue;
			}
			
			// If no IParser instance was able to parse this statement, a syntax
			// error has to be reported
			if ( command == null )
				throw new ParsingException( lineNumber, "Syntax error at line " + lineNumber + "." );
			
			commands.add( command );
			lineNumber++;
		}
	}
	
	/**
	 * Returns the next command. You have to call {@link #parse(String)} before
	 * requesting the first command using this method. If you do not, no command
	 * can be returned. Instead, a <code>IllegalStateException</code> is thrown.
	 * 
	 * @return								The next <code>ICommand</code> from the
	 * 										list of commands. If there are no more
	 * 										commands, <code>null</code> is returned.
	 * @throws VariableUndefinedException	If a variable is used, which is
	 * 										currently undefined.
	 * @throws IllegalStateException		If no commands have been parsed yet.
	 */
	public ICommand getNextCommand() throws VariableUndefinedException, IllegalStateException {
		return null;
	}
	
	/**
	 * Returns the value of the variable, if it exists. If not, a
	 * <code>VariableUndefinedException</code> is thrown. Variable names are
	 * case-insensitive.
	 * 
	 * @param variableName					The name of the desired variable.
	 * @return								The value of the variable.
	 * @throws VariableUndefinedException	If the variable is undefined, i.e. if
	 * 										the user has not used this variable name
	 * 										before.
	 */
	static Integer getVariableValue( String variableName ) throws VariableUndefinedException {
		
		Integer value = variables.get( variableName.toLowerCase() );
		
		// If the return value is null, no entry was found
		// that is, the variable is undefined
		if ( value == null )
			throw new VariableUndefinedException( variableName );
		else
			return value;
	}
}
