package logo;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import logo.commands.ConditionalJumpCommand;

/**
 * <p>
 * Provides necessary methods for both, parsing and runtime. It maintains the
 * variables used in Logo scripts and a stack of <code>JumpCommands</code> which
 * is used to properly implement nested loops.
 * </p>
 * 
 * <p>
 * This implementation is provisional. It works, but compromises the principles
 * of encapsulation. Therefore, it will be removed with one of the following
 * versions.
 * </p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 * 
 */
public class ParsingUtils {

	/**
	 * The name of the last help variable, which has been generated using
	 * <code>generateHelpVariable</code>. The name of help variables are
	 * ascending numbers.
	 */
	private static int lastHelpVariable = 0;

	/**
	 * The variables used by the user (explicitly) and by some subclasses of
	 * <code>Command</code> (implicitly). This attribute maps the name of a
	 * variable to its value.
	 */
	private static Map<String, Integer> variables = new HashMap<String, Integer>();

	/**
	 * The stack of <code>ConditionalJumpCommands</code> which stores the
	 * repeat-loop heads. This stack is used to implemented nested loops.
	 */
	private static Stack<ConditionalJumpCommand> commandStack = new Stack<ConditionalJumpCommand>();

	/**
	 * The constructor of <code>ParsingUtils</code> is private as it only
	 * contains static methods and attributes.
	 */
	private ParsingUtils() {

	}

	/**
	 * Returns the value of the variable with the given name. If the variable is
	 * not defined, a <code>VariableUndefinedException</code> is thrown.
	 * 
	 * @param variableName 					The name of the variable, whose value shall be 
	 * 										returned.
	 * @return 								The value of the variable with the given name.
	 * @throws VariableUndefinedException	If the requested variable is undefined.
	 * @throws IllegalArgumentException		If the name of the variable is <code>null</code> or
	 * 										empty.
	 */
	public static Integer getVariableValue( String variableName )
			throws VariableUndefinedException, IllegalArgumentException {
		
		if ( variableName == null || variableName.isEmpty() )
			throw new IllegalArgumentException( 
					"The variable's name must not be null or empty." );
		
		Integer value = variables.get( variableName.toLowerCase() );

		// If the return value is null, no entry was found.
		// that is, the variable is undefined
		if ( value == null )
			throw new VariableUndefinedException( variableName );

		return value;
	}

	/**
	 * Sets the value of the variable with the given name. If the variable was
	 * undefined before the call to this method, it will be defined after that
	 * call.
	 * 
	 * @param variableName				The name of the variable, whose value shall be
	 * 									changed.
	 * @param value						The new value of the variable.
	 * @throws IllegalArgumentException	If the given variable name is <code>null</code>
	 * 									or empty.
	 */
	public static void setVariableValue( String variableName, Integer value )
			throws IllegalArgumentException {

		if ( variableName == null || variableName.isEmpty() )
			throw new IllegalArgumentException(
					"The variable's name must not be null or empty." );

		variables.put( variableName.toLowerCase(), value );
	}

	/**
	 * Returns a variable identifier, which is not assigned yet. The returned name is
	 * an integer number.
	 *
	 * @return	The name of a help variable.
	 */
	public static String generateHelpVariable() {

		// create a variable name out of a number
		String varName;
		do {

			varName = String.valueOf( lastHelpVariable );
			lastHelpVariable++;

		} while ( variables.get( varName ) != null );

		return varName;
	}

	/**
	 * Pushes the given <code>ConditionalJumpCommand</code> to the command stack. This 
	 * stack is used to implement nested loops.
	 *
	 * @param jump	The <code>ConditionalJumpCommand</code> to push.
	 */
	public static void pushConditionalJump( ConditionalJumpCommand jump ) {

		commandStack.push( jump );
	}

	/**
	 * Pops the topmost <code>ConditionalJumpCommand</code> from the stack of commands and
	 * returns it. This stack is used to implement nested loops.
	 *
	 * @return	The topmost <code>ConditionalJumpCommand</code> from the stack of 
	 * 			commands.
	 */
	public static ConditionalJumpCommand popConditionalJump() {

		if ( commandStack.isEmpty() )
			return null;
		
		return commandStack.pop();
	}
}
