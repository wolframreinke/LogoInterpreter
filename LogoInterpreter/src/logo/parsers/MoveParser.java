package logo.parsers;

import logo.ICommand;
import logo.IParser;

/**
 * <p><code>IParser</code> implementation which parses the Logo statements <code>forward</code> and 
 * <code>backward</code>, i.e. the commands which directly change the turtles coordinates. Changing
 * the orientation of the turtle could be considered a move too, but is implemented in
 * {@link TurnParser}.</p>
 * 
 *  <p>The <code>MoveParser</code>'s {@link #parse(String)} method results in a 
 *  <code>MoveCommand</code> instance.</p>
 * 
 * @author Wolfram Reinke
 *
 */
public class MoveParser implements IParser {

	/**
	 * <p>The <code>MoveParser</code> returns a <code>MoveCommand</code> as a result of this
	 * method. Following Logo statements can be parsed using this parser:</p>
	 * <ul>
	 *		<li>"<b><code>forward &lt;number or identifier&gt; </code></b>"<br>
	 *			
	 *		</li>
	 *		<li>"<b><code>backward &lt;number or identifier&gt;</code></b>"<br>
	 *			
	 *		</li>
	 * </ul>
	 * 
	 * @param input
	 * @return
	 */
	@Override
	public ICommand parse( String input ) {
		return null;
	}

}
