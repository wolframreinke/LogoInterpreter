package logo;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import logo.commands.Command;
import logo.commands.JumpCommand;
import logo.parsers.LoopParser;
import logo.parsers.MoveParser;
import logo.parsers.Parser;
import logo.parsers.SimpleParser;
import logo.parsers.TurnParser;
import logo.parsers.VariableParser;

/**
 * <p>An <code>Interpreter</code> interpretes Logo statements and "compiles" them into
 * instances of subclasses of <code>Command</code>. These are internal representations
 * of the given textual Logo commands and can be accessed using {@link #getNextCommand()}.
 * To parse a set of Logo statements, {@link #parse(String)} is used. This method
 * creates the commands which can be accessed as described above.</p>
 * 
 * <p>The <code>Interpreter</code> works line by line. Therefore a line must not contain
 * more that one statement. Empty lines, as well as leading/trailing whitespaces and
 * comments (introduced by a leading '#') are ignored.</p>
 * 
 * @author Wolfram Reinke
 * @version 2.3
 */
public class Interpreter {
	
	/**
	 * The <code>Set</code> of <code>Parser</code> implementations which is used to
	 * interprete Logo statements in the <code>parse</code> method.
	 */
	private Set<Parser> parsers;
	
	/**
	 * The <code>Map</code> which is created from a textual Logo input. This attribute
	 * maps the line numbers, where the commands were found to the commands. These commands
	 * are the "compiled" form of the user's input.
	 */
	private Map<Integer, Command> commands;
	
	/**
	 * The line number of the next statement which is returned by 
	 * <code>getNextCommand</code>. The value of this attribute is influenced by 
	 * jump commands.
	 */
	private int instructionPointer;
	
	/**
	 * The line number of the last statement in the textual Logo input.
	 */
	private int lineCount;
	
	/**
	 * Creates a new <code>Interpreter</code>.
	 */
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
	 * <p>Parses the given set of Logo statements and creates for each statement a
	 * corresponding <code>Command</code>. The created <code>Commands</code> are the
	 * internal representation of textual Logo statements (the "compiled" form of these
	 * statements) and can be accessed using {@link #getNextCommand()}.</p>
	 * 
	 * <p>The Logo statements are separated by using the system-dependent line
	 * separator (see {@link System#lineSeparator()}). Leading and trailing whitespaces
	 * (i.e. everything that is matched by the regular expression <code>/\s+/</code>) in
	 * the single statements are ignored. Empty lines and comments (introduced by a
	 * leading '#') are ignored as well.</p>
	 *
	 * @param sourceCode		The Logo statements to parse. It is necessary to pass the
	 * 							full Logo source code created by the user to this method.
	 * 							This string must not be <code>null</code>.
	 * @throws ParsingException	This exception is thrown, if a syntactial error occurred
	 * 							in the input statements.
	 */
	public void parse( String sourceCode ) throws ParsingException {
		
		if ( sourceCode == null )
			throw new IllegalArgumentException( "The source code must not be null." );
		
		// Clear previosly parsed commands
		this.commands = new HashMap<Integer, Command>();
		
		// Split the input into an array of statements using the system-dependent
		// line separator
		String[] statements = sourceCode.split( System.lineSeparator() );
		int lineNumber = 1;
		
		for ( String statement : statements ) {
			
			// remove comments
			String[] parts = statement.split( "#" );
			if ( parts.length != 0 ) {
				statement = parts[0];	
			}	
			statement = statement.trim();
			statement = statement.replaceAll( "\\s+", " " );
			statement = statement.replace( "#", "" );
			
			if ( !statement.isEmpty() ) {
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
				
				this.commands.put( lineNumber, command );	
			}
			
			lineNumber++;
		}
		
		// set instruction pointer to the first line that contains a statement.
		this.instructionPointer = Collections.min( this.commands.keySet() );
		
		// The line number of the last statement.
		this.lineCount = lineNumber - 1;
	}
	
	/**
	 * Returns the next <code>Command</code>. The <code>Commands</code> which are seriatim
	 * returned are created in {@link #parse(String)}. So this method needs to be called
	 * before the first invocation of this method. If it was not, an
	 * <code>IllegalStateException</code> is thrown.
	 * 
	 * @return								The next <code>Command</code> from the list of
	 * 										parsed commands, or <code>null</code>, if there
	 * 										are no more <code>Command</code>s.
	 * @throws VariableUndefinedException	This exception is thrown, when the user
	 * 										attempts to gain read access to a variable
	 * 										which is currently undefined.
	 * @throws IllegalStateException		This exception is thrown, if <code>parse</code>
	 * 										was not called before the first invocation of
	 * 										this method.
	 */
	public Command getNextCommand() throws VariableUndefinedException, IllegalStateException {
		
		// the parse method has not been called before this invocation
		if ( this.commands == null )
			throw new IllegalStateException( "No commands have been parsed yet." );
		
		// find the next command. The instruction pointer may point to a line that
		// contains no statement. It is incremented until the next line is found, that
		// contains a statement.
		Command nextCommand;
		do {
			
			// if the instruction pointer points to a line after the last line,
			// the execution is finished. To signalize that, null is returned
			if ( this.instructionPointer > this.lineCount )
				return null;
			
			nextCommand = this.commands.get( this.instructionPointer );
			this.instructionPointer++;
			
		} while ( nextCommand == null );
		
		// special treatment for jump commands: They are handled internally to implement 
		// jumps, so the user does not need to care about that.
		if ( nextCommand instanceof JumpCommand ) {
			JumpCommand jump = (JumpCommand) nextCommand;
			this.instructionPointer = jump.getJumpTarget();
		}
		
		return nextCommand;
	}
}
