package logo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import logo.commands.Command;
import logo.commands.JumpCommand;
import logo.commands.VariableUndefinedException;
import logo.parsing.ColorParser;
import logo.parsing.LoopParser;
import logo.parsing.MoveParser;
import logo.parsing.Parser;
import logo.parsing.SimpleParser;
import logo.parsing.SyntaxError;
import logo.parsing.TokenStream;
import logo.parsing.TurnParser;
import logo.parsing.VariableParser;

/**
 * <p>An <code>LogoInterpreter</code> interpretes statements and "compiles" them into
 * instances of subclasses of <code>Command</code>. These are internal representations
 * of the given textual commands and can be accessed using {@link #getNextCommand()}.
 * To parse a set of Logo statements, {@link #parse(String)} is used. This method
 * creates the commands which can be accessed as described above.<br>
 * <code>parse</code> must be called before the first invocation of
 * <code>getNextCommand</code> to retrieve useful results.</p>
 * 
 * <p>The <code>LogoInterpreter</code> works line by line. Therefore a line must not contain
 * more that one statement. Empty lines, as well as leading/trailing whitespaces and
 * comments (introduced by a leading '#') are ignored.</p>
 * 
 * <p>The strings, which are used to identify textual statements, can be accessed
 * using {@link #getKeywords()}. These keywords can be used to implement 
 * syntax-highlighting and other features.</p>
 * 
 * @author Wolfram Reinke
 * @version 2.5
 */
public class LogoInterpreter {
	
	/**
	 * The <code>Map</code> which is created from a textual input. This attribute
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
	 * Creates a new <code>LogoInterpreter</code>.
	 */
	public LogoInterpreter() {
		
		super();
	}
	
	/**
	 * Returns the {@link Parser parsers} of this <code>LogoInterpreter</code>.
	 * These parsers are used to interprete the input in <code>parse</code>.
	 *
	 * @return
	 * 		The <code>Parser</code>s of this <code>LogoInterpreter</code>, which 
	 * 		are used to parse the user input.
	 */
	private Collection<Parser> createParsers() {
		
		Collection<Parser> result = new HashSet<Parser>( 6 );
		result.add( new MoveParser() );
		result.add( new TurnParser() );
		result.add( new SimpleParser() );
		result.add( new LoopParser() );
		result.add( new VariableParser() );
		result.add( new ColorParser() );
		
		return result;
	}
	
	/**
	 * <p>Parses the given set of statements and creates for each statement a
	 * corresponding <code>Command</code>. The created <code>Commands</code> are the
	 * internal representation of textual statements (the "compiled" form of these
	 * statements) and can be accessed using {@link #getNextCommand()}.</p>
	 * 
	 * <p>The statements are separated by using the system-dependent line
	 * separator (see {@link System#lineSeparator()}). Leading and trailing whitespaces
	 * (i.e. everything that is matched by the regular expression <code>/\s+/</code>) in
	 * the single statements are ignored. Empty lines and comments (introduced by a
	 * leading '#') are ignored as well.</p>
	 * 
	 * <p>If one or more syntax errors happen to be found, the previosly parsed
	 * <code>Commands</code> are not deleted, but the <code>SyntaxError</code>s are
	 * returned. If no syntax errors occurred, the previosly parsed commands are
	 * replaced by the new ones.</p>
	 *
	 * @param sourceCode		
	 * 		The statements to parse. It is necessary to pass the full source 
	 *		code created by the user to this method. This string must not be 
	 *		<code>null</code>.
	 *
	 * @return
	 * 		The syntax errors which have been found during the parsing procedure. If
	 * 		no errors occurred, returned collection is empty.
	 */
	public Collection<SyntaxError> parse( String sourceCode ) {
		
		if ( sourceCode == null )
			throw new IllegalArgumentException( "The source code must not be null." );
		
		if ( sourceCode.isEmpty() )
			return new ArrayList<SyntaxError>( 0 );
		
		Collection<Parser> parsers = this.createParsers();
		
		Map<Integer, Command> parsedCommands = new HashMap<Integer, Command>();
		Collection<SyntaxError> errors = new ArrayList<SyntaxError>();
		int lineNumber = 1;
		
		TokenStream tokenStream = TokenStream.tokenize( sourceCode );
		while ( tokenStream.hasNext() ) {
			
			lineNumber = tokenStream.getCurrentLineNumber();
			Command command = null;
			
			for ( Parser parser : parsers ) {
				
				TokenStream workingCopy = tokenStream.copy();
				command = parser.parse( workingCopy, lineNumber );
				
				if ( command != null ) {
					
					parsedCommands.put( lineNumber, command );
					tokenStream = workingCopy;
					break;
				}
			}
			
			if ( command == null ) {
				
				String statement = tokenStream.getNextCarefully();
				errors.add( new SyntaxError( lineNumber, "Unknown command \"" + statement + "\"." ) );
			}
		}
		
		for ( Parser parser : parsers ) {
			
			errors.addAll( parser.getSyntaxErrors() );
		}
		
		if ( errors.isEmpty() ) {
			
			this.instructionPointer = Collections.min( parsedCommands.keySet() );
			
			this.lastLine = lineNumber;
			
			if ( this.commands == null )
				this.commands = new HashMap<Integer, Command>();
			
			this.commands.clear();
			this.commands.putAll( parsedCommands );
		}
		
		return errors;
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
	 * <p>Returns the keywords of this <code>LogoInterpreter</code>, that is, 
	 * the strings that introduce a Logo statement, which can be parsed using 
	 * this interpreter. For instance, these keywords can be used to implement
	 * syntax-highlighting.</p>
	 *
	 * @return	
	 * 		The keywords of this <code>LogoInterpreter</code>.
	 */
	public Collection<String> getKeywords() {
		
		// collect all keywords from the parsers
		// A HashSet will automatically disallow duplicate entries.
		Collection<String> result = new HashSet<String>();
		for ( Parser parser : this.createParsers() ) {
		
			String[] keywords = parser.getKeywords();
			
			// Parser.getKeywords() may return null, if the implementation
			// of Parser depends not on a specific keyword.
			if ( keywords != null ) {
				
				for ( String keyword : keywords ) {
					result.add( keyword );
				}
			}
		}
		
		return result;
	}
}
