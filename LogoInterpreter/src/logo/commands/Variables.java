package logo.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>This class consists exclusivly of static methods that manage variables.
 * It provides the possibility to read and write a variables value by
 * specifying its name, and to generate internal help variables.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 *
 */
public class Variables {

	/**
	 * Help variables are created by converting a number to a string. After
	 * every help variable generation, the next number is returned. This attribute
	 * saves the most recently used number to generate a help variable.
	 */
	private static int lastHelpVariable = 0;

	/**
	 * Maps variable identifiers to the corresponding values.
	 */
	private static Map<String, Integer> variables = new HashMap<String, Integer>();

	
	/**
	 * This class consists exclusivly of static methods and is not expected to
	 * be subclassed. Therefore the private constructor is used. 
	 */
	private Variables() {	
	}
	
	/**
	 * Returns the value of the variable with the specified name. If the variable
	 * is currently undefined, a <code>VariableUndefinedException</code> is 
	 * thrown.
	 *
	 * @param variableName
	 * 		The name of the variable whose value shall be returned.
	 * 
	 * @return
	 * 		The value of the variable with the specified name.
	 * 
	 * @throws VariableUndefinedException
	 * 		If there is no variable-mapping for the specified name, i.e. if the
	 * 		variable is currently undefined.
	 * 
	 * @throws IllegalArgumentException
	 * 		If the specified name is <code>null</code> or empty.
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
	 * Sets the value of the variable with the specified name, regardless of
	 * whether the variable is defined or not.
	 *
	 * @param variableName
	 * 		The name of the variable whose value shall be set.
	 * 
	 * @param value
	 * 		The new value of the variable.
	 * 	
	 * @throws IllegalArgumentException
	 * 		If the specified name is <code>null</code> or empty.
	 */
	public static void setVariableValue( String variableName, Integer value )
			throws IllegalArgumentException {

		if ( variableName == null || variableName.isEmpty() )
			throw new IllegalArgumentException(
					"The variable's name must not be null or empty." );

		variables.put( variableName.toLowerCase(), value );
	}

	/**
	 * <p>Generates an internal help variable. The returned name is only
	 * guaranteed to be undefined when this method is called. The value
	 * could be changed by third parties afterwards anyway.</p>
	 * 
	 * <p>The returned variable name is generated from a number, therefore
	 * it will be the textual representation of an integer.</p>
	 *
	 * @return
	 * 		A name suggestion for an internal help variable. The returned name
	 * 		is not in use when this method is called.
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
}
