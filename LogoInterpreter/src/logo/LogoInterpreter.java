package logo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import logo.parsers.LoopParser;
import logo.parsers.MoveParser;
import logo.parsers.Parser;
import logo.parsers.SimpleParser;
import logo.parsers.TurnParser;
import logo.parsers.VariableParser;


public class LogoInterpreter extends Interpreter {
	
	@Override
	protected Collection<Parser> getParsers() {

		Set<Parser> parsers = new HashSet<Parser>();
		
		parsers.add( new MoveParser() );
		parsers.add( new TurnParser() );
		parsers.add( new SimpleParser() );
		parsers.add( new VariableParser() );
		parsers.add( new LoopParser() );
		
		return parsers;
	}
}
