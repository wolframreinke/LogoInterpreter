package logo;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import logo.commands.ConditionalJumpCommand;
import logo.commands.StaticJumpCommand;
import logo.parsers.LoopParser;
import logo.parsers.MoveParser;
import logo.parsers.SimpleParser;
import logo.parsers.TurnParser;
import logo.parsers.VariableParser;

/**
 * <p>The <code>Interpreter</code> interpretes a set of Logo statements and saves
 * the resulting <code>ICommand</code> objects. These object are created in the
 * {@link #parse(String)} method and can be accessed using 
 * {@link #getNextCommand()} method.</p>
 * 
 * <p>Additionally it holds information about variables used by the user. This
 * information can be used by other classes, which are involved in parsing the
 * Logo statements.</p>
 * 
 * @author Wolfram Reinke
 * @version 2.1
 */
public class Interpreter {

	private static Map<String, Integer> variables = new HashMap<String, Integer>();
	private static Stack<ConditionalJumpCommand> commandStack = new Stack<ConditionalJumpCommand>();
	
	private Set<IParser> parsers;
	
	private List<Command> commands;
	private int commandIndex;
	
	public Interpreter() {
		super();
		
		// Add command parsers.
		parsers = new HashSet<IParser>();
		parsers.add( new SimpleParser() );
		parsers.add( new MoveParser() );
		parsers.add( new TurnParser() );
		parsers.add( new VariableParser() );
		parsers.add( new LoopParser() );
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
		commands = new ArrayList<Command>();
		
		// Split the input into an array of statements using the system-dependent
		// line separator
		String[] statements = sourceCode.split( System.lineSeparator() );
		int lineNumber = 1;
		
		for ( String statement : statements ) {
			
			statement = statement.split( "#" )[0];
			statement = statement.trim();
			statement = statement.replace( "\t", "" );
			if ( statement.isEmpty() )
				continue;
			
			// consult each IParser instance to check whether the statement
			// can be parsed
			Command command = null;
			for ( IParser parser : parsers ) {
				
				Command returnValue = parser.parse( statement, lineNumber );
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
		
		commandIndex = 0;
	}
	
	/**
	 * Returns the next command. You have to call {@link #parse(String)} before
	 * requesting the first command using this method. If you do not, no command
	 * can be returned. Instead, a <code>IllegalStateException</code> is thrown.
	 * 
	 * @return								The next <code>ICommand</code> from the
	 * 										list of commands. If there are no more
	 * 										commands, <code>null</code> is returned.
	 * @throws VariableUndefinedException	If a currently undefined variable is
	 * 										used.
	 * @throws IllegalStateException		If no commands have been parsed yet.
	 */
	public Command getNextCommand() throws VariableUndefinedException, IllegalStateException {
		
		if ( commandIndex >= commands.size() )
			return null;

		Command nextCommand = commands.get( commandIndex );
		commandIndex++;
		
		if ( nextCommand instanceof ConditionalJumpCommand ) {
			ConditionalJumpCommand jump = (ConditionalJumpCommand) nextCommand;
			commandIndex = jump.getTarget() - 1;
		}
		
		if ( nextCommand instanceof StaticJumpCommand ) {
			StaticJumpCommand jump = (StaticJumpCommand) nextCommand;
			commandIndex = jump.getTarget() - 1;
		}
		
		return nextCommand;
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
	public static Integer getVariableValue( String variableName ) throws VariableUndefinedException {
		
		Integer value = variables.get( variableName.toLowerCase() );
		
		// If the return value is null, no entry was found
		// that is, the variable is undefined
		if ( value == null )
			throw new VariableUndefinedException( variableName );
		else
			return value;
	}
	
	/**
	 * Sets the value of the given variable, regardless of whether it exists.
	 * Variable names are case-insensitive. To retrieve the variable values,
	 * use {@link #getVariableValue(String)}.
	 * 
	 * @param variableName				The name of the variable, whose value shall 
	 * 									be set.
	 * @param value						The value of the variable. 
	 * @throws IllegalArgumentException	If the parameter <code>variableName</code>
	 * 									is null or equals "".
	 */
	public static void setVariableValue( String variableName, Integer value ) 
			throws IllegalArgumentException {
		
		if ( variableName == null || variableName.isEmpty() )
			throw new IllegalArgumentException( "The input string must not be null or empty." );
			
		variables.put( variableName.toLowerCase(), value );
	}
	
	/**
	 * <p>Generates a suitable name for a help variable. This name can definitively
	 * not used by the user. Moreover, there are not conflicts with other help
	 * variables.</p>
	 * 
	 * @return		A suitable name for a help variable.
	 */
	public static String createHelpVariable() {
		
		int i = 0;
		String text = String.valueOf( i );
		
		while ( variables.get( text ) != null ) {
			i++;
			text = String.valueOf( i );
		}
		
		return text;
	}
	
	public static void pushJumpCommand( ConditionalJumpCommand command ) {
		commandStack.push( command );
	}
	
	public static ConditionalJumpCommand popJumpCommand() {
		try {
			return commandStack.pop();
		}
		catch ( EmptyStackException e ) {
			return null;
		}
	}
}
