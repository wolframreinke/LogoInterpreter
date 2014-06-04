package parsing;

import java.util.HashMap;
import java.util.Map;

public class CommandMapper {

	private Map<IParser, ICommand> mapping = new HashMap<IParser, ICommand>();
	
	public ICommand getMapping( IParser parser ) {
		
		return mapping.get( parser );
	}
	
	public void addMapping( IParser parser, ICommand command ) {
		
		mapping.put( parser, command );
	}
}
