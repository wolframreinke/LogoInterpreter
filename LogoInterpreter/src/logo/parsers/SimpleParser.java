package logo.parsers;

import logo.Command;
import logo.IParser;

/**
 * <p><code>IParser</code> instance which parses the Logo statements <code>clear</code>, <code>reset</code>,
 * <code>penup</code> and <code>pendown</code>, i.e. the statements that take no parameters.</p>
 * 
 * <p>The <code>SimpleParser</code>'s {@link #parse(String)} method results in a <code>SimpleCommand</code>
 * instance.</p>
 * 
 * @author Wolfram Reinke
 *
 */
public class SimpleParser implements IParser {

	/**
	 * <p>The <code>SimpleParser</code> returns a <code>SimpleCommand</code> as result of
	 * this method. Following Logo statements can be parsed using this parser:</p>
	 * <ul>
	 * 		<li> "<b><code>clear</code></b>"<br>
	 * 			This comman results in a <code>SimpleCommand</code> whose type is set to
	 * 			<code>ISimpleCommandType.CLEAR</code>.
	 * 		</li>
	 * 		<li> "<b><code>reset</code></b>"<br>
	 * 			This comman results in a <code>SimpleCommand</code> whose type is set to
	 * 			<code>ISimpleCommandType.RESET</code>.
	 * 		</li>
	 * 		<li> "<b><code>penup</code></b>"<br>
	 * 			This comman results in a <code>SimpleCommand</code> whose type is set to
	 * 			<code>ISimpleCommandType.PENUP</code>.
	 * 		</li>
	 * 		<li> "<b><code>pendown</code></b>"<br>
	 * 			This comman results in a <code>SimpleCommand</code> whose type is set to
	 * 			<code>ISimpleCommandType.PENDOWN</code>.
	 * 		</li>
	 * </ul>
	 * 
	 * @param input		The Logo statement to parse.
	 * @return			A fully initialized <code>SimpleCommand</code> instance.
	 */
	@Override
	public Command parse( String input ) {
		return null;
	}

}
