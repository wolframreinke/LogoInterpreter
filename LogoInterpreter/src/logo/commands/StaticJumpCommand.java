package logo.commands;

import logo.Turtle;
import logo.VariableUndefinedException;

/**
 * <p>A <code>StaticJumpCommand</code> is a {@link JumpCommand} which performs its jump
 * independent of a condition. Consequently, the jump is always performed.
 * <code>StaticJumpCommand</code>s are used at the end of loops to perform the jump
 * back to the loop's head.</p>
 * 
 * <p>A <code>StaticJumpCommand</code> is associated with a variable given in its
 * constructor. When {@link #execute(Turtle)} is called, this variable is decremented
 * by one. That is, the loop variable is decremented at the end of every loop iteration.
 * </p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 *
 */
public class StaticJumpCommand extends JumpCommand {

	/**
	 * The name of the variable whose value is decremented by one, when 
	 * {@link #execute(Turtle)} is called.
	 */
	private final String variable;
	
	/**
	 * Creates a new <code>StaticJumpCommand</code>. 
	 *
	 * @param jumpTarget	The line number, where the execution of <code>Commands</code> is
	 * 						continued after this <code>StaticJumpCommand</code>.
	 * @param variable		The name of the variable which is decremented by one, whenever
	 * 						<code>execute</code> is called.
	 */
	public StaticJumpCommand( int jumpTarget, String variable ) {

		this.variable = variable;
		super.setJumpTarget( jumpTarget );
	}
	
	/**
	 * <p>Decrements the variable, which has been given to this <code>StaticJumpCommand</code>
	 * in its constructor, by one.</p>
	 * 
	 * @param turtle						The condition of this turtle is not changed.
	 * @throws VariableUndefinedException	This exception is thrown, if the variable which
	 * 										shall be decremented by one is undefined.
	 */
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {
		
		// get, decrement and set variable
		Integer value = Variables.getVariableValue( this.variable );
		value--;
		Variables.setVariableValue( this.variable, value );
		
	}
	
	@Override
	public String toString() {
		String jumpTarget = "undefined";
		try {
			jumpTarget = String.valueOf( super.getJumpTarget() );
		}
		catch ( VariableUndefinedException e ) { /* this cannot happen */ }
		
		return super.toString() + "(target: " + jumpTarget + ", variable: " + this.variable + ")";
	}
}
