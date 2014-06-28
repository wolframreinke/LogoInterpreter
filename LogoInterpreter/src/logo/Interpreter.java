package logo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import logo.commands.Command;
import logo.commands.JumpCommand;
import logo.commands.VariableUndefinedException;
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
 * creates the commands which can be accessed as described above.<br>
 * <code>parse</code> must be called before the first invocation of
 * <code>getNextCommand</code> to retrieve useful results.</p>
 * 
 * <p>The <code>Interpreter</code> works line by line. Therefore a line must not contain
 * more that one statement. Empty lines, as well as leading/trailing whitespaces and
 * comments (introduced by a leading '#') are ignored.</p>
 * 
 * <p>The strings, which are used to identify Logo statements, can be accessed
 * using {@link #getKeywords()}. These keywords can be used to 
 * implement syntax-highlighting and other features.</p>
 * 
 * @author Wolfram Reinke
 * @version 2.4
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
	private int lastLine;
	
	/**
	 * Creates a new <code>Interpreter</code>.
	 */
	public Interpreter() {
		super();
		
		// Add command parsers. To parse more statements, simply add a
		// new implementation of Parser and add it here.
		// TODO Remove these dependencies from here.
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
	 * <p>If one or more syntax errors happen to be found, the previosly parsed
	 * <code>Commands</code> are not deleted, but the <code>SyntaxError</code>s are
	 * returned.</p>
	 *
	 * @param sourceCode		
	 * 		The Logo statements to parse. It is necessary to pass the full Logo source 
	 *		code created by the user to this method. This string must not be 
	 *		<code>null</code>.
	 *
	 * @return
	 * 		The syntax errors which have been found during the parsing procedure. If
	 * 		no errors occurred, the previosly parsed <code>Commands</code> are
	 * 		deleted and <code>null</code> is returned. If one or more errors occured,
	 * 		the errors are returned and the previosly parsed <code>Commands</code> are
	 * 		not deleted.
	 */
	public Collection<SyntaxError> parse( String sourceCode ) {
		
		if ( sourceCode == null )
			throw new IllegalArgumentException( "The source code must not be null." );
		
		// this map contains all parsed commands, and the collection contains
		// all errors that occured during the parsing procedure
		Map<Integer, Command> parsedCommands = new HashMap<Integer, Command>();
		Collection<SyntaxError> errors = new HashSet<SyntaxError>();
		
		// Split the input into an array of statements using the system-dependent
		// line separator
		String[] statements = sourceCode.split( System.lineSeparator() );
		int lineNumber = 1;
		
		for ( String statement : statements ) {
			
			// remove comments and remove unnecessary whitespaces
			String[] parts = statement.split( "#" );
			if ( parts.length != 0 ) {
				statement = parts[0];	
			}	
			statement = statement.trim();
			statement = statement.replace( "#", "" );
			
			if ( !statement.isEmpty() ) {
				// consult each Parser instance to check whether the statement
				// can be parsed
				Command command = null;
				for ( Parser parser : this.parsers ) {
					
					String[] words  = statement.split( "\\s+" );
					Command returnValue = parser.parse( words, lineNumber );
					if ( returnValue != null )
						command = returnValue;
				}
				
				// If no Parser instance was able to parse this statement, a syntax
				// error has to be reported
				if ( command == null )
					errors.add( new SyntaxError( lineNumber, "Unknown command \"" + statement + "\"." ) );
				
				parsedCommands.put( lineNumber, command );	
			}
			
			lineNumber++;
		}
		
		// loops and closing brackets are managed in a stack. If this stack
		// still contains elements, a syntax error has to be reported.
		// TODO Evil Dependency
		int stackSize = LoopParser.getCommandStackSize();
		if ( stackSize != 0 )
			errors.add( new SyntaxError( lineNumber, "Expected " + stackSize + " more \"]\" but found EOF." ) );
		
		if ( errors.isEmpty() ) {
			// set instruction pointer to the first line that contains a statement.
			this.instructionPointer = Collections.min( parsedCommands.keySet() );
			
			// The line number of the last statement.
			this.lastLine = lineNumber - 1;
			
			// clear the command map and save the newly parsed commands
			if ( this.commands == null )
				this.commands = new HashMap<Integer, Command>();
			
			this.commands.clear();
			this.commands.putAll( parsedCommands );
		}
		
		// if no errors occurred, null is returned
		return errors.isEmpty() ? null : errors;
	}
	
	/**
	 * Returns the next <code>Command</code>. The <code>Commands</code> which are seriatim
	 * returned are created in {@link #parse(String)}. So this method needs to be called
	 * before the first invocation of this method. If it was not, an
	 * <code>IllegalStateException</code> is thrown.
	 * 
	 * @return								
	 * 		The next <code>Command</code> from the list of parsed commands, or 
	 * 		<code>null</code>, if there are no more <code>Command</code>s.
	 * 
	 * @throws VariableUndefinedException	
	 * 		This exception is thrown, when the user attempts to gain read access to a 
	 * 		variable which is currently undefined.
	 * 
	 * @throws IllegalStateException		
	 * 		This exception is thrown, if <code>parse</code> was not called before the 
	 * 		first invocation of this method.
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
			if ( this.instructionPointer > this.lastLine )
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
	
	/**
	 * <p>Returns the keywords of this <code>Interpreter</code>, that is, 
	 * the strings that introduce a Logo statement, which can be parsed using 
	 * this interpreter. For instance, these keywords can be used to implement
	 * syntax-highlighting.</p>
	 *
	 * @return	
	 * 		The keywords of this <code>Interpreter</code>.
	 */
	public String[] getKeywords() {
		
		// collect all keywords from the parsers
		// A HashSet will automatically disallow duplicate entries.
		Collection<String> result = new HashSet<String>();
		for ( Parser parser : this.parsers ) {
		
			String[] keywords = parser.getKeywords();
			
			// Parser.getKeywords() may return null, if the implementation
			// of Parser depends not on a specific keyword.
			if ( keywords != null ) {
				
				for ( String keyword : keywords ) {
					result.add( keyword );
				}
			}
		}
		
		return result.toArray( new String[result.size()] );
	}
}
