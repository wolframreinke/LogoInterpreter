package logo.commands;


/**
 * <p>A <code>SimpleCommand</code> is a {@link Command} which is created from "simple" Logo
 * statements, i.e. statements that have no parameters. Those statements are <code>reset</code>, 
 * <code>clear</code>, <code>penup</code> and <code>pendown</code>.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 *
 * @see {@link Type SimpleCommand.Type}
 */
public class SimpleCommand extends Command {
	
	/**
	 * The <code>Type</code> of a <code>SimpleCommand</code> specifies, which action is performed
	 * on a turtle in <code>SimpleCommand.execute</code>. Following actions can be performed:
	 * <ul>
	 * 		<li><code>reset,</code></li>
	 * 		<li><code>clear,</code></li>
	 * 		<li><code>penup,</code></li>
	 * 		<li><code>pendown</code></li>
	 * </ul>
	 */
	public static enum Type {
		RESET,
		CLEAR,
		PENUP,
		PENDOWN
	}
	
	/**
	 * The type of this <code>SimpleCommand</code>. The behavior of the <code>execute</code>
	 * method depends on this type.
	 */
	private final Type type;
	
	/**
	 * Creates a new instance of <code>SimpleCommand</code> by setting the {@link Type}. This type
	 * determines the behavior of the <code>execute</code> method.
	 *
	 * @param type	The type of the new <code>SimpleCommand</code>.
	 */
	public SimpleCommand( Type type ) {
		this.type = type;
	}
	
	/**
	 * Executes this <code>SimpleCommand</code>. Depending on the <code>Type</code> of this
	 * command, either the action <code>reset</code>, <code>clear</code>, <code>penup</code> or
	 * <code>pendown</code> is performed.
	 * 
	 * @param turtle	The turtle whose condition shall be changed.
	 */
	@Override
	public void execute( Turtle turtle ) {
		
		// How the turtle is changed depends fully on the
		// type of this simple command
		switch ( this.type ) {
		
		case RESET:
			turtle.reset();
			break;
			
		case CLEAR:	// TODO implement the CLEAR command
			break;
			
		case PENUP:
			turtle.setPainting( false );
			break;
			
		case PENDOWN:
			turtle.setPainting( true );
			break;
			
		}
	}

	@Override
	public String toString() {
		return super.toString() + "(type: " + this.type + ")";
	}
}
