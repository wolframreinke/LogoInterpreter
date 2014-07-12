package logo.commands;

/**
 * <p>A <code>ColorCommand</code> is a {@link Command} which changes the pen color
 * of a turtle.</p>
 * 
 * <p>The color is saved as a color id. This id is a positive integer less then or
 * equal to 3. This id can be specified either as a constant value (using the
 * {@link #ColorCommand(int)} constructor) or as a variable identifier (using the
 * {@link #ColorCommand(String)} constructor) which will be resolved, when the
 * command is executed.</p>
 * 
 * @author Wolfarm Reinke
 * @version 1.0
 *
 */
public class ColorCommand extends Command {

	// id constraints
	public static final int COLOR_ID_MAX	= 3;
	public static final int COLOR_ID_MIN	= 0;
	
	/**
	 * The variable that saves the color id.
	 */
	private final Variable variable;
	
	/**
	 * <p>Creates a new <code>ColorCommand</code> by specifying a constant color id.
	 * When <code>execute</code> is called, the turtle's pen color will be set to
	 * this id.</p>
	 *
	 * @param id
	 * 		The color id of this <code>ColorCommand</code>.
	 */
	public ColorCommand( int colorID ) {

		// save the constant value in a help variable
		this.variable = new Variable();
		this.variable.setValue( colorID );
	}

	/**
	 * <p>Creates a new <code>ColorCommand</code> by specifying a variable.
	 * This identifier will be resolved to a color id in
	 * <code>execute</code>. After that, the turtle's pen color will be set to
	 * this id.</p>
	 *
	 * @param identifier
	 * 		The variable which saves the color id of this 
	 * 		<code>ColorCommand</code>.
	 */
	public ColorCommand( Variable variable ) {

		this.variable = variable;
	}

	/**
	 * <p>Sets the given turtle's pen color id to the color id of this 
	 * <code>ColorCommand</code>. If the id is negative, the id is set to 0. If the
	 * id is greater than 3, it is set to 3 instead.</p>
	 * 
	 * @param turtle
	 * 		The turtle whose pen color id shall be set.
	 * 
	 * @throws VariableUndefinedException
	 * 		If the desired color id is saved in a variable, and this variable is
	 * 		undefined, this exception is thrown.
	 * 
	 */
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {

		int id = this.variable.getValue();
		
		// apply constraints
		if ( id > COLOR_ID_MAX )
			id = COLOR_ID_MAX;
		
		if ( id < COLOR_ID_MIN )
			id = COLOR_ID_MIN;
		
		turtle.setColor( id );
	}

	@Override
	public String toString() {
	
		return super.toString() + "COLOR (" + this.variable + ")";
	}
}
