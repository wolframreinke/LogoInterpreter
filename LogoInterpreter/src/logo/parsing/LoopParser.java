package logo.parsing;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.NoSuchElementException;

import logo.commands.Command;
import logo.commands.ConditionalJumpCommand;
import logo.commands.IgnoredCommand;
import logo.commands.StaticJumpCommand;

/**
 * <p><code>LoopParser</code> is an implementation of <code>Parser</code>, which
 * parses the Logo statements "<code>repeat</code>", "<code>[</code>" and
 * "<code>]</code>", i.e. all statements that have something to do with repeat-
 * loops.</p>
 * 
 * <p>A <code>LoopParser</code> returns either a {@link ConditionalJumpCommand},
 * a {@link IgnoredCommand} or a {@link StaticJumpCommand} as the result of its
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
	
	private Collection<SyntaxError> syntaxErrors = new HashSet<SyntaxError>();

	@Override
	public String[] getKeywords() {

		return new String[] { CMD_REPEAT, CMD_BEGIN, CMD_END };
	}

	/**
	 * <p>This method parses the Logo statements concerning <code>repeat</code>-
	 * loops and results in either a <code>ConditionalJumpCommand</code>, a
	 * <code>StaticJumpCommand</code> or a <code>IgnoredCommand</code>,
	 * depending on the type of the given Logo statement. The following
	 * statements can be parsed using this method:</p> 
	 * <ul>
	 * 		<li>"<b><code>repeat &lt;iterations&gt;</code></b>"<br> 
	 * 		The <code>iterations</code> can be an integer value, or a variable
	 * 		identifier. Note that the integer value will be converted into an
	 * 		internal variable to enable counting down of this value.<br> 
	 * 		This statement results in a <code>ConditionalJumpCommand</code>, 
	 * 		whose target line number is the line after the next closing bracket. 
	 * 		The condition variable of this command is the given 
	 * 		<code>iterations</code>.</li><br>
	 * 
	 * 		<li>"<b><code>[</code></b>"<br> 
	 * 		This statement results in a <code>IgnoredCommand</code>. That's 
	 * 		because <code>null</code> cannot be returned, since that would mean 
	 * 		that the statement could not be parsed, wich is not the case. 
	 * 		Furthermore, when executing the Logo code statement by statement, 
	 * 		the opening bracket is not simply skipped.</li><br>
	 * 
	 * 		<li>"<b><code>]</code></b>"<br> 
	 * 		This statement results in a <code>StaticJumpCommand</code>, whose 
	 * 		target line number is the line containing the most recently recognized "repeat &lt;iterations&gt;"
	 * 		statement. The variable of the static jump is the condition variable 
	 * 		of this conditional jump.</li> 
	 * </ul>
	 * 
	 * <p>If the given input statement could not be parsed correclty,
	 * <code>null</code> is returned. If the given input statement is
	 * <code>null</code>, a <code>NullPointerException</code> is thrown.</p>
	 * 
	 * @param words
	 * 		The input statement which shall be parsed. This array must not be
	 * 		<code>null</code>.
	 * 
	 * @param lineNumber
	 * 		The line number where the input statement was found. This value 
	 * 		is used to create the single <code>Commands</code> and to calculate 
	 * 		the correct jump targets.
	 * 
	 * @return 
	 * 		Either a <code>ConditionalJumpCommand</code>, a
	 * 		<code>StaticJumpCommand</code> or a <code>IgnoredCommand</code>,
	 * 		depending on the input (see above). If the input could not be parsed,
	 * 		<code>null</code> is returned.
	 */
	@Override
	public Command parse( TokenStream stream, int lineNumber ) {

		try {

			String word = stream.getNext();
			
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

					// If the given parameter is not a number, use the String
					// constructor
					// instead of the integer constructor
					command = new ConditionalJumpCommand( argument );
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
				ConditionalJumpCommand loopHead = cmdStack.pop();

				// now that we found out where to jump, when the repeat-loop's
				// argument is 0, set the jump target of the head
				loopHead.setJumpTarget( lineNumber + 1 );

				// This is the line, this static jump command jumps to
				int target = loopHead.getLineNumber();

				// The variable used by the repeat-head is necessary to
				// auto-decrement it
				String variable = loopHead.getConditionVariable();

				StaticJumpCommand result = new StaticJumpCommand( target, variable );
				result.setLineNumber( lineNumber );
				return result;
			}
			else return null;
		}
		catch ( NoSuchElementException e ) {
			
			return null;
		}
	}
	
	@Override
	public Collection<SyntaxError> getSyntaxErrors() {

		while ( (cmdStack.poll()) != null ) {
			
			this.syntaxErrors.add( new SyntaxError( -1, "Expected \"]\" but found EOF." ) );
		}
		
		return this.syntaxErrors;
	}

}
