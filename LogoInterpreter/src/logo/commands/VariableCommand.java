package logo.commands;

/**
 * <p>A <code>VariableCommand</code> is a {@link Command} which changes the value of
 * a variable. How the variable is changed, depends on the type of the
 * <code>VariableCommand</code>. The {@link #execute(Turtle)} method does not change
 * the condition of the specified turtle, it only changes the variable's value.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 *
 * @see {@link Type VariableCommand.Type}
 */
public class VariableCommand extends Command {

	/**
	 * The <code>Type</code> of a <code>VariableCommand</code> determines, how a 
	 * command's variable is changed in its <code>execute</code> method. Following 
	 * types are possible:
	 * <ul>
	 * 		<li><code><b>LET</b></code><br>
	 * 		The value of the variable is set to the value specified in the command's
	 * 		constructor. The new value of the variable does not depend on the old value
	 * 		of the variable. That is, if the variable is undefined, no exception will
	 * 		be thrown.</li><br>
	 * 		<li><code><b>INCREMENT</b></code><br>
	 * 		The value of the variable is incremented by the value specified in the
	 * 		command's constructor. As the new value of the variable depends partly on its
	 * 		old value, a <code>VariableUndefinedException</code> will be thrown, if the
	 * 		variable is undefined.</li><br>
	 * 		<li><code><b>DECREMENT</b></code><br>
	 * 		The value of the variable is decreased by the value specified in the
	 * 		command's constructor. As the new value of the variable depends partly on its
	 * 		old value, a <code>VariableUndefinedException</code> will be thrown, if the
	 * 		variable is undefined.</li>
	 * </ul>
	 * 
	 * @author Wolfram Reinke
	 * @version 1.0
	 *
	 */
	public static enum Type {
		LET,
		INCREMENT,
		DECREMENT;
	}
	
	/**
	 * The {@link Type} of this <code>VariableCommand</code>.
	 */
	private Type type;
	
	/**
	 * The name of the variable, whose value is changed, when <code>execute</code> is
	 * called.
	 */
	private String targetVariable; 
	
	/**
	 * The name of the variable that saves the new value of the 
	 * <code>targetVariable</code>. When <code>execute</code> is called, the target's
	 * value will be set/changed by this variable's value.
	 */
	private String assignedVariable;
	
	/**
	 * <p>Creates a new <code>VariableCommand</code> by specifiying the target variable,
	 * a constant value, which will be assigned to that variable, and the 
	 * {@link Type type} of this <code>VariableCommand</code>.</p>
	 *
	 * @param targetVariable
	 * 		The name of the variable, whose value is changed, when <code>execute</code>
	 * 		is called.
	 * 
	 * @param assignedValue
	 * 		The value by which the target variable is changed/to which the target
	 * 		variable is set.
	 * 
	 * @param type
	 * 		The type of this <code>VariableCommand</code>.
	 * 
	 * @see {@link Type VariableCommand.Type}
	 */
	public VariableCommand( String targetVariable, int assignedValue, Type type ) {
		
		// the constant value is saved in a help variable.
		this.assignedVariable = Variables.generateHelpVariable();
		Variables.setVariableValue( this.assignedVariable, assignedValue );
		
		this.targetVariable = targetVariable;
		this.type = type;
	}
	
	/**
	 * Creates a new <code>VariableCommand</code> by specifying the target variable,
	 * the assigned variable, which holds the value that is assigned to the target
	 * variable, and the {@link Type type} of this <code>VariableCommand</code>.
	 *
	 * @param targetVariable
	 * 		The name of the variable, whose value is changed in <code>execute</code>.
	 * 
	 * @param assignedVariable
	 * 		The name of the variable, by whose value the target variable is changed/
	 * 		to whose value the target variable is set.
	 * 
	 * @param type
	 * 		The type of this <code>VariableCommand</code>
	 * 
	 * @see {@link Type VariableCommand.Type}
	 * 
	 */
	public VariableCommand( String targetVariable, String assignedVariable, Type type ) {
		
		this.targetVariable = targetVariable;
		this.assignedVariable = assignedVariable;
		this.type = type;
	}
	
	/**
	 * Changes the value of the variable specified in this <code>VariableCommand</code>'s
	 * constructor. How the value is changed depends on the {@link Type type} of this
	 * command. The condition of the given <code>Turtle</code> is not changed.
	 * 
	 * @param turtle
	 * 		The condition of this <code>Turtle</code> is neither changed nor used.
	 * 
	 * @throws VariableUndefinedException
	 * 		If the new value of the target variable depends on the old value of the
	 * 		target variable, and the target value is undefined; Or if the value of the
	 * 		target variable depends on a second variable and this variable is 
	 * 		undefined.<br>
	 * 		See {@link Type VariableCommand.Type} for more information.
	 * 
	 */
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {

		int assignedValue = Variables.getVariableValue( this.assignedVariable );
		int targetValue;
		
		switch ( this.type ) {

		case LET:
			Variables.setVariableValue( this.targetVariable, assignedValue );
			break;
			
		case INCREMENT:
			// retrieve the old value and compute the new one
			targetValue = Variables.getVariableValue( this.targetVariable );
			Variables.setVariableValue( this.targetVariable, targetValue + assignedValue );
			break;
			
		case DECREMENT:
			// retrieve the old value and compute the new one
			targetValue = Variables.getVariableValue( this.targetVariable );
			Variables.setVariableValue( this.targetVariable, targetValue - assignedValue );
			break;

		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "(target: " + this.targetVariable + ", assigned: " + this.assignedVariable + ", type: " + this.type + ")";
	}

}
