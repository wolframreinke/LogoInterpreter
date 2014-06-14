package logo.parsers;

import logo.ParsingUtils;
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
public class LoopParser implements Parser {

	// String constants used to recoginze keywords
	private static final String CMD_REPEAT	= "repeat";
	private static final String CMD_BEGIN	= "[";
	private static final String CMD_END		= "]";
	
	/**
	 * <p>This method parses the Logo statements concerning <code>repeat</code>-
	 * loops and results in either a <code>ConditionalJumpCommand</code>, a
	 * <code>StaticJumpCommand</code> or a <code>IgnoredCommand</code>, depending on
	 * the type of the given Logo statement. The following statements can be 
	 * parsed using this method:</p>
	 * <ul>
	 * 		<li>"<b><code>repeat &lt;iterations&gt;</code></b>"<br>
	 * 			The <code>iterations</code> can be an integer value, or a variable
	 * 			identifier. Note that the integer value will be converted into an
	 * 			internal variable to enable counting down of this value.<br>
	 * 			This statement results in a <code>ConditionalJumpCommand</code>,
	 * 			whose target line number is the line after the next closing bracket.
	 * 			The condition variable of this command is the given 
	 * 			<code>iterations</code>.</li><br>
	 * 		<li>"<b><code>[</code></b>"<br>
	 * 			This statement results in a <code>IgnoredCommand</code>. That's because
	 * 			<code>null</code> cannot be returned, since that would mean that
	 * 			the statement could not be parsed, wich is not the case. Furthermore,
	 * 			when executing the Logo code statement by statement, the opening
	 * 			bracket is not simply skipped.</li><br>
	 * 		<li>"<b><code>]</code></b>"<br>
	 * 			This statement results in a <code>StaticJumpCommand</code>, whose
	 * 			target line number is the line containing the most recently 
	 * 			recognized "repeat &lt;iterations&gt;" statement. The variable of
	 * 			the static jump is the condition variable of this conditional jump.</li>
	 * </ul>
	 * 
	 * <p>If the given input statement could not be parsed correclty, <code>null</code>
	 * is returned. If the given input statement is <code>null</code>, a 
	 * <code>NullPointerException</code> is thrown.</p>
	 * 
	 * @param words			The input statement which shall be parsed. This array
	 * 						must not be <code>null</code>.
	 * @param lineNumber	The line number where the input statement was found.
	 * 						This value is used to create the single 
	 * 						<code>Commands</code> and to calculate the correct
	 * 						jump targets.
	 * @return 				Either a <code>ConditionalJumpCommand</code>, a
	 * 						<code>StaticJumpCommand</code> or a <code>IgnoredCommand</code>,
	 * 						depending on the input (see above). If the input could not
	 * 						be parsed, <code>null</code> is returned.
	 */
	@Override
	public Command parse( String[] words, int lineNumber ) {

		// There two kinds of loop statements, the "repeat n" statement and the
		// "[" and "]" statements. The latter one is covered here, the former one
		// below
		if ( words.length == 1 ) {
			
			if ( words[0].equals( CMD_BEGIN ) ) {
				
				// If the command is an opening bracket, this statement is ignored.
				// Of course, null cannot be returned as this would probably lead
				// to a ParsingException in Interpreter.
				// Moreover, a delay might be added later when executing commands.
				// Therefore a special command is returned.
				IgnoredCommand result = new IgnoredCommand();
				result.setLineNumber( lineNumber );
				return result;
			}
			else if ( words[0].equals( CMD_END ) ) {
				
				// If the command is an closing bracket, its replaced with a 
				// static jump to the head of the repeat loop.
				
				// pop the head of the repeat loop from the stack.
				ConditionalJumpCommand loopHead = ParsingUtils.popConditionalJump();
				
				// now that we found out where to jump, when the repeat-loop's
				// argument is 0, set the jump target of the head
				loopHead.setTargetLineNumber( lineNumber + 1 );
				
				// This is the line, this static jump command jumps to
				int target = loopHead.getLineNumber();
				
				// The variable used by the repeat-head is necessary to auto-decrement
				// it
				String variable = loopHead.getConditionVariable();
				
				StaticJumpCommand result = new StaticJumpCommand( target, variable );
				result.setLineNumber( lineNumber );
				return result;
			}
			else return null; // Everything that consists of one word but is neither
							  // "[" nor "]" cannot be parsed
		}
		else if ( words.length == 2 && words[0].equals( CMD_REPEAT ) ) {

			// The statement is "repeat n", the head of the loop. This statement
			// is converted into a conditional jump. Unfortunately, we don't know
			// the jump target yet, it's set later, when a closing bracket is 
			// recognized.
			ConditionalJumpCommand command;
			try {
				// If the given parameter is a number, use the integer constructor
				// of ConditionalJumpCommand.
				Integer parameter = Integer.parseInt( words[1] );
				command = new ConditionalJumpCommand( parameter );
			}
			catch ( NumberFormatException e ) {
				
				// If the given parameter is not a number, use the String constructor
				// instead of the integer constructor
				command = new ConditionalJumpCommand( words[1] );
			}
			
			command.setLineNumber( lineNumber );
			
			// The conditional jump is not completely initialized yet. When the next
			// closing bracket is recognized, the command will be popped from the 
			// stack to complete the initialization.
			ParsingUtils.pushConditionalJump( command );
			return command;
		}
		else return null; // The given statement must have the length 1 or 2.
	}

}
