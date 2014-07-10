package logo.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>A <code>Variable</code> is a symbolic name which is associated with an integer
 * value. A <code>Variable</code> can be either defined or undefined, depending on
 * whether its value has been set.</p>
 * 
 * <p>Additionally, this class manages all "global" variables. That is, all
 * occurrences of a variable's identifier refer to the same <code>Variable</code>.
 * instance. Those global variables are created using 
 * {@link #createVariable(String)}.</p>
 * 
 * <p>Local variables are created by using {@link #createLocalVariable()}. The
 * returned variable is not shared with other users of this class.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 *
 */
public class Variable {

	/**
	 * The id of the next local variable which is returned by 
	 * <code>createLocalVariable</code>. This attribute is used to generate the
	 * variable's name.
	 */
	private static int localVariableId = 0;
	
	/**
	 * Maps variable identifiers to actual <code>Variable</code>s.
	 */
	private static final Map<String, Variable> EXISTING_VARIABLES 
		= new HashMap<String, Variable>();
	
	/**
	 * <p>Returns an undefined variable with the specified identifier. If there
	 * already exists a global variable with this identifier, this variable is
	 * returned. If no such variable is found, a new variable is created. That
	 * is, the returned variable is share with all other users of this class.</p>
	 *
	 * @param identifier
	 * 		The identifier of the desired variable.
	 * 
	 * @return
	 * 		A <code>Variable</code> with the specified identifier.
	 */
	public static final Variable createVariable( String identifier ) {
		
		if ( Variable.EXISTING_VARIABLES.containsKey( identifier ) )
			return Variable.EXISTING_VARIABLES.get( identifier );
		
		// variable does not exist. create a new one
		Variable variable = new Variable( identifier );
		Variable.EXISTING_VARIABLES.put( identifier, variable );
		
		return variable;
	}
	
	/**
	 * The identifier of this <code>Variable</code>.
	 */
	private String identifier;
	
	/**
	 * Determines, whether this variable is currently defined. A variable is not
	 * defined until its value is set for the first time.
	 */
	private boolean defined;
	
	/**
	 * The value of this <code>Variable</code>.
	 */
	private int value;
	
	/**
	 * <p>Creates a new local Variable with the specified identifier. The
	 * created variable is not shared with other users of this class.</p>
	 *
	 * @param identifier
	 * 		The identifier of the newly created variable.
	 */
	public Variable( String identifier ) {
		
		this.identifier = identifier;
		this.defined = false;
	}
	
	/**
	 * <p>Creates a new local variable with a generated name. The created variable
	 * is not shared with other users of this class.</p>
	 */
	public Variable() {
		
		// generate a (more or less, it's not that important) unique name.
		this.identifier = "local_" + Variable.localVariableId;
		this.defined = false;
		Variable.localVariableId++;
	}
	
	/**
	 * <p>Returns the value of this <code>Variable</code>, if it is currently
	 * defined. If not, a <code>VariableUndefinedException</code> is thrown.</p>
	 *
	 * @return
	 * 		The value of this <code>Variable</code>.
	 * 
	 * @throws VariableUndefinedException
	 * 		If this <code>Variable</code> is currently undefined (that is, the
	 * 		value of the variable is not set)
	 * 
	 */
	public int getValue() throws VariableUndefinedException {
		
		if ( this.defined )
			return this.value;
		else
			throw new VariableUndefinedException( this.identifier );
	}
	
	/**
	 * <p>Sets the value of this <code>Variable</code> to the specified value.
	 * After that call, this <code>Variable</code> is defined, if it was not
	 * before.</p>
	 *
	 * @param value
	 * 		The new value of this <code>Variable</code>.
	 */
	public void setValue( int value ) {
	
		this.defined = true;
		this.value = value;
	}
	
	@Override
	public String toString() {

		if ( this.defined ) 
			return this.identifier + " = " + this.value;
		else
			return this.identifier + " = undefined";
	}
}
