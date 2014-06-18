package logo.commands;

import logo.Turtle;
import logo.VariableUndefinedException;

/**
 * <p>A <code>IgnoredCommand</code> is a {@link Command} which does exactly nothing. Parsers which
 * intend to ignore a statement can return a <code>IgnoredCommand</code>. See {@link Parser} and
 * its implementations to get more information.</p>
 * 
 * <p>This class exists primarily for the sake of clarification. Without this class, it would
 * have been necessary to abuse another implementation of <code>Command</code>. For instance, it
 * would have been possible to use a <code>MoveCommand</code> and set its distance to 0.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 */
public class IgnoredCommand extends Command {

	/**
	 * This method does nothing. This implementation of <code>Command</code> can be used by an
	 * implementation of <code>Parser</code> to ignore a Logo statement.
	 * 
	 * @param turtle						The condition of this turtle is not changed.
	 * @throws VariableUndefinedException	This exception is not thrown.
	 */
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {
		// this command does nothing
	}
}
