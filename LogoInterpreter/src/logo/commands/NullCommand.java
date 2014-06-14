package logo.commands;

import logo.Turtle;
import logo.VariableUndefinedException;

public class NullCommand extends Command {

	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {
		// A null command does nothing
	}
}
