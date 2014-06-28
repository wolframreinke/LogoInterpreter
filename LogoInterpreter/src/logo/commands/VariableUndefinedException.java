package logo.commands;

/**
 * <p>This <code>Exception</code> is thrown, when the user requests read access to a 
 * variable, which has not been defined before. A <code>VariableUndefinedException</code> 
 * saves the name of the variable affected.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 *
 */
@SuppressWarnings( "serial" )
public class VariableUndefinedException extends Exception {

	/**
	 * The name of the variable, which the user illegally attempted to access.
	 */
	private String variableName;
	
	/**
	 * Creates a new <code>VariableUndefinedException</code>. This indicates, that the 
	 * user attempted to access a variable which has not been defined before.
	 *
	 * @param variableName	The name of the affected variable.
	 */
	public VariableUndefinedException( String variableName ) {
		super( "The variable \"" + variableName +"\" is undefined." );
	
		this.variableName = variableName;
	}
	
	/**
	 * Returns the name of the affected variable. The user's attempt to gain read access
	 * to this variable led to the throwing of this <code>Exception</code>.
	 *
	 * @return	The name of the affected variable.
	 */
	public String getVariableName() {
		return this.variableName;
	}
}
