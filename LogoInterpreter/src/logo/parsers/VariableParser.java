package logo.parsers;

import logo.Command;
import logo.IParser;

public class VariableParser implements IParser {

	private static final String CMD_LET		= "let";
	private static final String CMD_INCREMENT = "increment";
	private static final String CMD_DECREMENT	= "decrement";
	
	@Override
	public Command parse( String input, int lineNumber ) {
		return null;
	}

}
