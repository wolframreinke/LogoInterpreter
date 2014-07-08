package logo;

import java.util.Collection;
import java.util.HashSet;

import logo.parsers.*;

/**
 * <p>A <code>LogoInterpreter</code> is a {@link Interpreter} which interprets Logo
 * statements and "compiles" them into instances of subclasses of {@link Command}.
 * This class provides the <code>Parser</code>s which are necessary to parse the
 * Logo commands. Apart from that, it does not change the behavior of its
 * superclass.</p>
 * 
 * @author tungsten
 * @version 1.0
 *
 */
public class LogoInterpreter extends Interpreter {
	
	/**
	 * {@inheritDoc}
	 * 
	 * A <code>LogoInterpreter</code> returns all parsers which are necessary to
	 * parse Logo statements.
	 * 
	 * @return
	 * 		The <code>Parser</code>s of this <code>Interpreter</code>.
	 */
	@Override
	protected Collection<Parser> getParsers() {

		Collection<Parser> parsers = new HashSet<Parser>( 6 );
		
		// add all parsers that are needed to parse the Logo statements.
		parsers.add( new MoveParser() );
		parsers.add( new TurnParser() );
		parsers.add( new SimpleParser() );
		parsers.add( new VariableParser() );
		parsers.add( new LoopParser() );
		parsers.add( new ColorParser() );
		
		return parsers;
	}
}
