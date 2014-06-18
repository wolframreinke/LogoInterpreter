package logo;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import logo.commands.ConditionalJumpCommand;

public class ParsingUtils {

	private static int lastHelpVariable = 0;
	
	private static Map<String, Integer> variables = new HashMap<String, Integer>();
	private static Stack<ConditionalJumpCommand> commandStack = new Stack<ConditionalJumpCommand>();
	
	public ParsingUtils() {}
	
	public static Integer getVariableValue( String variableName ) throws VariableUndefinedException {
		
		Integer value = variables.get( variableName.toLowerCase() );
		
		// If the return value is null, no entry was found
		// that is, the variable is undefined
		if ( value == null )
			throw new VariableUndefinedException( variableName );
		
		return value;
	}
	
	public static void setVariableValue( String variableName, Integer value ) 
			throws IllegalArgumentException {
		
		if ( variableName == null || variableName.isEmpty() )
			throw new IllegalArgumentException( "The input string must not be null or empty." );
			
		variables.put( variableName.toLowerCase(), value );
	}
	
	public static String generateHelpVariable() {
		
		// create a variable name out of a number
		String varName;
		do {
			
			varName = String.valueOf( lastHelpVariable );
			lastHelpVariable++;
			
		} while ( variables.get( varName ) != null );
		
		return varName;
	}
	
	public static void pushConditionalJump( ConditionalJumpCommand jump ) {
		commandStack.push( jump );
	}
	
	public static ConditionalJumpCommand popConditionalJump() {
		try {
			return commandStack.pop();
		}
		catch ( EmptyStackException e ) {
			return null;
		}
	}
}
