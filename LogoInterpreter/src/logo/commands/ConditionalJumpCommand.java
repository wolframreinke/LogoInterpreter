package logo.commands;


/**
 * <p>A <code>ConditionalJumpCommand</code> is a {@link Command} which only performs a
 * jump, if the value of a given variable is equal to or less than 0. 
 * <code>ConditionalJumpCommands</code> are used as the internal representation of loop 
 * heads.</p>
 * 
 * <p>A <code>ConditionalJumpCommand</code> is associated to a variable given to it in its
 * constructor. The return value of {@link #getNextCommand()} depends on whether this variable
 * is less than or equal to 0.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.1
 *
 */
public class ConditionalJumpCommand extends Command {

	/**
	 * This attribute indicates whether the loop variable needs to be reset after the
	 * jump is performed.
	 */
	private boolean resetVariable;
	
	/**
	 * The value, the loop variable is reset to, after the jump is performed.
	 */
	private int defaultValue;
	
	/**
	 * The variable whose value is compared to 0, to decide whether to perform
	 * the jump or not.
	 */
	private Variable conditionVariable;
	
	/**
	 * The <code>Command</code> which is returned in {@link #getNextCommand()}, if the
	 * jump is performed.
	 */
	private Command jumpTarget;
	
	/**
	 * <p>Creates a new <code>ConditionalJumpCommand</code> by specifying the
	 * variable whose value is compared to 0 to decide whether to perform the jump or not.
	 * The value of this variable is <i>not</i> reset after performing the jump.</p>
	 *
	 * @param variable	The condition variable which determines the behavior of
	 * 					<code>getJumpTarget</code>.
	 */
	public ConditionalJumpCommand( Variable variable ) {
		this.conditionVariable = variable;
		this.resetVariable = false;
	}
	
	/**
	 * <p>Creates a new <code>ConditionalJumpCommand</code> by specifying a start value.
	 * An internal variable is created and the start value is assigned to this variable.
	 * This internal variable is compared to 0 to decide whether to perform the jump or
	 * not. The value of this variable is reset after the jump is performed.</p>
	 *
	 * @param iterations	The start value of the condition variable which determines the
	 * 						behavior of <code>getJumpTarget</code>.
	 */
	public ConditionalJumpCommand( int iterations ) {
		// save the start value to reset it after the jump
		this.defaultValue = iterations;
		
		// create an internal variable and assign the start value to it
		this.conditionVariable = new Variable();
		this.conditionVariable.setValue( iterations );
		this.resetVariable = true;
	}
	
	/**
	 * This method does nothing. Consequently, neither the condition of the given turtle
	 * is changed, nor the declared exception is thrown.
	 * 
	 * @param turtle						The condition of this turtle is not changed.
	 * @throws VariableUndefinedException	This exception is not thrown.
	 */
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {
		// The conditional jump does not need to do anything here
	}
	
	/**
	 * <p>Returns the <code>Command</code> which shall be executed after the execution
	 * of this <code>Command</code>. If the value of the condition variable (set in the 
	 * {@link #ConditionalJumpCommand(Variable) constructor}) is greater than zero, the
	 * returned <code>Command</code> is the one specified in 
	 * {@link #setJumpTarget(Command)}. That is, the method performs a jump. If the
	 * variable is not greater than zero, the direct successor of this <code>Command</code>
	 * is returned instead.</p>
	 *
	 * @return
	 * 		The <code>Command</code> which shall be executed after the execution of this
	 * 		<code>Command</code>.
	 * 
	 * @throws VariableUndefinedException
	 * 		If the condition variable is undefined.
	 * 
	 */
	@Override
	public Command getNextCommand() throws VariableUndefinedException {

		int conditionValue = this.conditionVariable.getValue();
		
		if ( conditionValue > 0 ) {
			
			// return the direct successor
			return super.getNextCommand();
		}
		else {
		
			// if a local variable was used to store the condition value, it has to
			// be reset for potential further uses of this loop
			if ( this.resetVariable )
				this.conditionVariable.setValue( this.defaultValue );
			
			// Special treatment for jump commands: They would perform a jump, so the
			// direct successor is used
			if ( this.jumpTarget instanceof StaticJumpCommand )
				return ((StaticJumpCommand) this.jumpTarget).getDirectSuccessor();
			
			return this.jumpTarget.getNextCommand();
		}
	}
	
	/**
	 * Returns the name of the condition variable. That is, the variable which determines
	 * the behavior of <code>getJumpTarget</code>.
	 *
	 * @return	The condition variable.
	 */
	public Variable getConditionVariable() {
		return this.conditionVariable;
	}
	
	/**
	 * <p>Sets the <code>Command</code> whose <i>direct</i> successor in the sequence of 
	 * <code>Command</code>s will be returned by {@link #getNextCommand()}, if a jump
	 * is performed.</p>
	 * 
	 * @param target
	 * 		The <code>Command</code> which shall be executed, if a jump is performed.
	 */
	public void setJumpTarget( Command target ) {
		
		this.jumpTarget = target;
	}
	
	@Override
	public String toString() {
		String jumpTarget = String.valueOf( this.jumpTarget.getLineNumber() );
		
		return super.toString() + "JUMP to line " + jumpTarget + " , if (" + this.conditionVariable + ") <= 0";
	}
}
