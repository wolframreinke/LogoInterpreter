package logo.parsing;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.NoSuchElementException;

import logo.commands.Command;
import logo.commands.ConditionalJumpCommand;
import logo.commands.StaticJumpCommand;
import logo.commands.Variable;

/**
 * <p><code>LoopParser</code> is an implementation of <code>Parser</code>, which
 * parses the Logo statements "<code>repeat</code>", "<code>[</code>" and
 * "<code>]</code>", i.e. all statements that have something to do with repeat-
 * loops.</p>
 * 
 * <p>A <code>LoopParser</code> returns either a {@link ConditionalJumpCommand}
 * or a {@link StaticJumpCommand} as the result of its 
 * {@link #parse(String[], int)} method.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 */
public class LoopParser extends Parser {

	/**
	 * This stack of <code>ConditionalJumpCommand</code>s is used to keep track
	 * of recognized loop heads. Whenever a closing bracket is encoutered, the
	 * resulting <code>StaticJumpCommand</code> will target the topmost
	 * <code>Command</code> of this stack.
	 */
	private static Deque<ConditionalJumpCommand> cmdStack = new ArrayDeque<ConditionalJumpCommand>();

	// String constants used to recoginze keywords
	private static final String CMD_REPEAT = "repeat";
	private static final String CMD_BEGIN = "[";
	private static final String CMD_END = "]";
	
	/**
	 * The syntax errors which occurred during the parsing procedure
	 */
	private Collection<SyntaxError> syntaxErrors = new HashSet<SyntaxError>();

	/**
	 * Returns the keywords of this <code>Parser</code>, namely 
	 * {@value #CMD_BEGIN}, 
	 * {@value #CMD_END} and 
	 * {@value #CMD_REPEAT}.
	 */
	@Override
	public String[] getKeywords() {

		return new String[] { CMD_REPEAT, CMD_BEGIN, CMD_END };
	}

	/**
	 * <p>This method parses the Logo statements concerning <code>repeat</code>-
	 * loops and results in either a <code>ConditionalJumpCommand</code>
	 * or a <code>StaticJumpCommand</code>,
	 * depending on the type of the given Logo statement. The following
	 * sequences of tokens can be parsed using this method:</p> 
	 * <ul>
	 * 		<li>"<b><code>repeat &lt;iterations&gt; [</code></b>"<br> 
	 * 		The <code>iterations</code> can be an integer value, or a variable
	 * 		identifier. Note that the integer value will be converted into an
	 * 		internal variable to enable counting down of this value.<br> 
	 * 		This statement results in a <code>ConditionalJumpCommand</code>, 
	 * 		whose target line number is the line after the next closing bracket. 
	 * 		The condition variable of this command is the given 
	 * 		<code>iterations</code>.</li><br>
	 * 
	 * 		<li>"<b><code>]</code></b>"<br> 
	 * 		This statement results in a <code>StaticJumpCommand</code>, whose 
	 * 		target line number is the line containing the most recently recognized "repeat &lt;iterations&gt;"
	 * 		statement. The variable of the static jump is the condition variable 
	 * 		of this conditional jump.</li> 
	 * </ul>
	 * 
	 * <p>If the given input tokens could not be parsed correclty,
	 * <code>null</code> is returned. If the given token stream is
	 * <code>null</code>, a <code>NullPointerException</code> is thrown.</p>
	 * 
	 * @param stream
	 * 		The <code>TokenStream</code> which is used to retrieve the tokens
	 * 		to parse.
	 * 
	 * @param lineNumber
	 * 		The line number where the first token was found. This value 
	 * 		is used to create the single <code>Commands</code> and to calculate 
	 * 		the correct jump targets.
	 * 
	 * @return 
	 * 		Either a <code>ConditionalJumpCommand</code> or a
	 * 		<code>StaticJumpCommand</code>
	 * 		depending on the input (see above). If the input could not be parsed,
	 * 		<code>null</code> is returned.
	 */
	@Override
	public Command parse( TokenStream stream, int lineNumber ) {

		try {

			String word = stream.getNext();
			
			// expected input sequence is "repeat <id/num> ["
			if ( word.equals( CMD_REPEAT ) ) {
				
				String argument = stream.getNext();
				
				// The "repeat" statement has to be followed by an opening bracket
				// if this is not the case, a syntax error is reported
				String begin = stream.getNextCarefully();
				if ( begin == null || !begin.equals( CMD_BEGIN ) ) {
					
					this.syntaxErrors.add( new SyntaxError( lineNumber, 
									  "Expected \"[\" but found " 
									+ begin == null ? "EOF" : begin + "." ) );
					
					return null;
				}
				
				// The statement is "repeat n", the head of the loop. This statement
				// is converted into a conditional jump. Unfortunately, we don't
				// know
				// the jump target yet, it's set later, when a closing bracket is
				// recognized.
				ConditionalJumpCommand command;
				try {
					// If the given parameter is a number, use the integer
					// constructor
					// of ConditionalJumpCommand.
					Integer parameter = Integer.parseInt( argument );
					command = new ConditionalJumpCommand( parameter );
				}
				catch ( NumberFormatException e ) {

					// If the given parameter is not a number, use the Variable
					// constructor
					// instead of the integer constructor
					Variable conditionVariable = Variable.createVariable( argument );
					command = new ConditionalJumpCommand( conditionVariable );
				}

				command.setLineNumber( lineNumber );

				// The conditional jump is not completely initialized yet. When the
				// next
				// closing bracket is recognized, the command will be popped from
				// the
				// stack to complete the initialization.
				cmdStack.push( command );
				return command;
			}
			else if ( word.equals( CMD_END ) ) {
				
				// If the command is an closing bracket, its replaced with a
				// static jump to the head of the repeat loop.
				// pop the head of the repeat loop from the stack.
				ConditionalJumpCommand loopHead = cmdStack.poll();
				
				if ( loopHead == null ) {

					this.syntaxErrors.add( new SyntaxError( lineNumber,
							"The token \"[\" is misplaced." ) );
					
					return null;
				}

				// now that we found out where to jump, when the repeat-loop's
				// argument is 0, set the jump target of the head
				loopHead.setJumpTarget( lineNumber + 1 );

				// This is the line, this static jump command jumps to
				int target = loopHead.getLineNumber();

				// The variable used by the repeat-head is necessary to
				// auto-decrement it
				Variable variable = loopHead.getConditionVariable();

				StaticJumpCommand result = new StaticJumpCommand( target, variable );
				result.setLineNumber( lineNumber );
				return result;
			}
			else return null;
		}
		catch ( NoSuchElementException e ) {
			
			// Unexpected end of token stream
			return null;
		}
	}
	
	/**
	 * Returns the syntax errors which occurred during the parsing procedure.
	 * For each unmatched opening bracket, this method will add another syntax
	 * error.
	 * 
	 * @return
	 * 		The syntax errors of this <code>Parser</code>.
	 */
	@Override
	public Collection<SyntaxError> getSyntaxErrors() {

		// add for each unmatched opening bracket a new error
		while ( (cmdStack.poll()) != null ) {
			
			this.syntaxErrors.add( new SyntaxError( -1, "Expected \"]\" but found EOF." ) );
		}
		
		return this.syntaxErrors;
	}

}
