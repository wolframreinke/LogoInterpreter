package logo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import logo.commands.Command;
import logo.commands.VariableUndefinedException;
import logo.parsing.*;

/**
 * <p>An <code>LogoInterpreter</code> interpretes statements and "compiles" them into
 * instances of subclasses of <code>Command</code>. These are internal representations
 * of the given textual commands and can be accessed using {@link #getNextCommand()}.
 * To parse a set of Logo statements, {@link #parse(String)} is used. This method
 * creates the commands which can be accessed as described above.<br>
 * <code>parse</code> must be called before the first invocation of
 * <code>getNextCommand</code> to retrieve useful results.</p>
 * 
 * <p>The <code>LogoInterpreter</code> tokenizes the textual input, therefore a
 * single line may contain more than one statement.</p>
 * 
 * <p>The strings, which are used to identify textual statements, can be accessed
 * using {@link #getKeywords()}. These keywords can be used to implement 
 * syntax-highlighting and other features.</p>
 * 
 * @author Wolfram Reinke
 * @version 2.5
 */
public class LogoInterpreter {
	
	private Command currentCommand;
	
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
		
		// validate input
		if ( sourceCode == null )
			throw new IllegalArgumentException( "The source code must not be null." );
		
		if ( sourceCode.isEmpty() )
			return new ArrayList<SyntaxError>( 0 );
		
		// the parsers used to interpret the input
		Collection<Parser> parsers = this.createParsers();
		
		// parsed commands and occurred errors
		Command firstCommand 	= null,
				previosCommand 	= null;
		Collection<SyntaxError> errors = new ArrayList<SyntaxError>();
		
		// tokenize the source code, this will strip off comments and
		// whitespaces
		TokenStream tokenStream = new TokenStream( sourceCode );
		while ( tokenStream.hasNext() ) {
			
			int lineNumber = tokenStream.getCurrentLineNumber();
			Command currentCommand = null;
			
			// Consult all parsers
			for ( Parser parser : parsers ) {
				
				// the parsers will remove elements from the token stream. this
				// copy is used to free the parsers from rolling back changes.
				TokenStream workingCopy = tokenStream.copy();
				currentCommand = parser.parse( workingCopy );
				
				if ( currentCommand != null ) {
					
					// parsing was successful, original stream is replaced with
					// the copy
					currentCommand.setLineNumber( lineNumber );
					tokenStream = workingCopy;
					
					// build a linked list of commands
					if ( previosCommand != null ) {
						previosCommand.setNextCommand( currentCommand );
						
					}

					previosCommand = currentCommand;
					
					// the first command which is returned by this.getNextCommand
					if ( firstCommand == null )
						firstCommand = currentCommand;
					
					break;
				}
			}
			
			if ( currentCommand == null ) {
				
				// No parser could interpret this statement.
				// remove the invalid token and report a syntax error
				String statement = tokenStream.getNextCarefully();
				errors.add( new SyntaxError( lineNumber, "Unknown command \"" + statement + "\"." ) );
			}
		}
		
		// collect errors from the parsers
		for ( Parser parser : parsers ) {
			
			errors.addAll( parser.getSyntaxErrors() );
		}
		
		if ( errors.isEmpty() ) {
			
			this.currentCommand = firstCommand;
		}
		
		return errors;
	}

	/**
	 * Returns the next <code>Command</code>. The <code>Commands</code> which are seriatim
	 * returned are created in {@link #parse(String)}. So <code>parse</code> 
	 * needs to be called before the first invocation of this method. If it was 
	 * not, an <code>IllegalStateException</code> is thrown.
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
		
		Command result = this.currentCommand;
		// If this is the last command, return null
		if ( result == null )
			return null;
		
		// retrieve next command in the linked list
		this.currentCommand = this.currentCommand.getNextCommand();
		
		return result;
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
