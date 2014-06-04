package parsing;

import java.util.HashSet;
import java.util.Set;

public class ParserManager {

	private Set<IParser> parsers = new HashSet<IParser>();
	private CommandMapper mapper = new CommandMapper();
	
	public ParserManager() {
		
	}
	
	public ICommand parse( String input ) throws ParsingException {
		
		for ( IParser parser : parsers ) {
			
			if ( parser.parse( input ) ) {
				
				ICommand command = mapper.getMapping( parser );
				if ( command == null )
					throw new ParsingException( "Missing ICommand mapping for " + parser.getClass().getName() + "." );
				
				return command;
			}
		}
		
		return null;
	}
}
