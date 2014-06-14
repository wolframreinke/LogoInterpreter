package logo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import logo.commands.Command;
import logo.commands.ConditionalJumpCommand;
import logo.commands.JumpCommand;
import logo.commands.StaticJumpCommand;
import logo.parsers.LoopParser;
import logo.parsers.MoveParser;
import logo.parsers.Parser;
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
	
	private Set<Parser> parsers;
	
	private List<Command> commands;
	private int commandIndex;
	
	public Interpreter() {
		super();
		
		// Add command parsers.
		this.parsers = new HashSet<Parser>();
		this.parsers.add( new SimpleParser() );
		this.parsers.add( new MoveParser() );
		this.parsers.add( new TurnParser() );
		this.parsers.add( new VariableParser() );
		this.parsers.add( new LoopParser() );
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
		this.commands = new ArrayList<Command>();
		
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
			for ( Parser parser : this.parsers ) {
				
				String[] words  = statement.split( "\\s+" );
				Command returnValue = parser.parse( words, lineNumber );
				if ( returnValue != null )
					command = returnValue;
			}
			
			// If no IParser instance was able to parse this statement, a syntax
			// error has to be reported
			if ( command == null )
				throw new ParsingException( lineNumber, "Syntax error at line " + lineNumber + "." );
			
			this.commands.add( command );
			lineNumber++;
		}
		
		this.commandIndex = 0;
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
		
		if ( this.commands == null )
			throw new IllegalStateException( "No commands have been parsed yet." );
		
		if ( this.commandIndex >= this.commands.size() )
			return null;

		Command nextCommand = this.commands.get( this.commandIndex );
		this.commandIndex++;
		
		// special treatment for jump commands: They are handled internally to implement 
		// jumps, so the user does not need to care about that.
		if ( nextCommand instanceof JumpCommand ) {
			JumpCommand jump = (JumpCommand) nextCommand;
			this.commandIndex = jump.getJumpTarget();
		}
		
		return nextCommand;
	}
}
