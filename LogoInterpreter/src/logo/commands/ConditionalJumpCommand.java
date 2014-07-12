package logo.commands;


/**
 * <p>A <code>ConditionalJumpCommand</code> is a {@link JumpCommand} which only performs its
 * jump, if the value of a given variable is equal to or less than 0. 
 * <code>ConditionalJumpCommands</code> are used as the internal representation of loop 
 * heads.</p>
 * 
 * <p>A <code>ConditionalJumpCommand</code> is associated to a variable given to it in its
 * constructor. The return value of {@link #getJumpTarget()} depends on whether this variable
 * <= 0.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
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
	
	@Override
	public Command getNextCommand() throws VariableUndefinedException {

		int conditionValue = this.conditionVariable.getValue();
		
		if ( conditionValue > 0 ) {
			return super.getNextCommand();
		}
		else {
		
			if ( this.resetVariable )
				this.conditionVariable.setValue( this.defaultValue );
			
			if ( this.jumpTarget instanceof StaticJumpCommand )
				return ((StaticJumpCommand) this.jumpTarget).getNextCommandWithoutJump();
			
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
	
	public void setJumpTarget( Command target ) {
		
		this.jumpTarget = target;
	}
	
	@Override
	public String toString() {
		String jumpTarget = String.valueOf( this.jumpTarget.getLineNumber() );
		
		return super.toString() + "JUMP to line " + jumpTarget + " , if (" + this.conditionVariable + ") <= 0";
	}
}
