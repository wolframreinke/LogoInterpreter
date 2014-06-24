package logo.commands;

import logo.Turtle;
import logo.VariableUndefinedException;

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
public class ConditionalJumpCommand extends JumpCommand {

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
	 * The name of the variable whose value is compared to 0, to decide whether to perform
	 * the jump or not.
	 */
	private String conditionVariable;
	
	/**
	 * <p>Creates a new <code>ConditionalJumpCommand</code> by specifying the name of the
	 * variable whose value is compared to 0 to decide whether to perform the jump or not.
	 * The value of this variable is <b>not</b> reset after performing the jump.</p>
	 *
	 * @param variable	The condition variable which determines the behavior of
	 * 					<code>getJumpTarget</code>.
	 */
	public ConditionalJumpCommand( String variable ) {
		this.conditionVariable = variable;
		this.resetVariable = false;
	}
	
	/**
	 * <p>Creates a new <code>ConditionalJumpCommand</code> by specifying a start value.
	 * An internal variable is created and the start value is assigned to this variable.
	 * This internal variable is compared to 0 to decide whether to perform the jump or
	 * not. The value of this variable is reset after the jump is performed.</p>
	 *
	 * <p>Note: As an internal variable is created to store the value of the loop
	 * variable, unexpected behavior may originate in unintended changes of this variable
	 * by third parties.</p>
	 *
	 * @param iterations	The start value of the condition variable which determines the
	 * 						behavior of <code>getJumpTarget</code>.
	 */
	public ConditionalJumpCommand( int iterations ) {
		// save the start value to reset it after the jump
		this.defaultValue = iterations;
		
		// create an internal variable and assign the start value to it
		this.conditionVariable = Variables.generateHelpVariable();
		Variables.setVariableValue( this.conditionVariable, iterations );
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
	 * <p>Returns the line number, where the execution of <code>Commands</code> shall be
	 * continued after the recognition of this command. The return value of this method
	 * depends on the condition variable of this <code>ConditionalJumpCommand</code>. If
	 * it is less than or equal to 0, a jump is performed. That is, the returned line
	 * number is not the next line number. If it is greater than 0, the line number of
	 * the next <code>Command</code> is returned, that is, no jump is performed.</p>
	 * 
	 * <p>If this <code>ConditionalJumpCommand</code> was created by using the
	 * constructor {@link #ConditionalJumpCommand(int)}, the condition variable is 
	 * reset to its default value.</p>
	 * 
	 * @return								The line number of the next <code>Command</code>
	 * 										to execute.
	 * @throws VariableUndefinedException	If the condition variable is undefined.
	 */
	@Override
	public int getJumpTarget() throws VariableUndefinedException {
		
		Integer varValue = Variables.getVariableValue( this.conditionVariable );
		if ( varValue <= 0 ) {
			if ( this.resetVariable ) {
				Variables.setVariableValue( this.conditionVariable, this.defaultValue );
			}
			return super.getJumpTarget();
		}
		
		return super.getLineNumber() + 1;
	}
	
	/**
	 * Returns the name of the condition variable. That is, the variable which determines
	 * the behavior of <code>getJumpTarget</code>.
	 *
	 * @return	The condition variable.
	 */
	public String getConditionVariable() {
		return this.conditionVariable;
	}
	
	@Override
	public String toString() {
		String jumpTarget;
		try {
			jumpTarget = String.valueOf( super.getJumpTarget() );
		}
		catch ( VariableUndefinedException e ) {
			jumpTarget = "undefined";
		}
		return super.toString() + "(target: " + jumpTarget + ", variable: " + this.conditionVariable + ")";
	}
}
