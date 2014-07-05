package logo.commands;

/**
 * <p>A <code>Turtle</code> paints graphics on a canvas. This interface
 * is used by subclasses of {@link Command}, to change the condition of
 * the turtle.k</p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 */
public interface Turtle {

	/**
	 * Resets this <code>Turtle</code>. That is, the position is set to the
	 * default position, but the canvas is not cleared.
	 */
	public abstract void reset();
	
	/**
	 * Clears the canvas of the turtle without changing other attributes of
	 * the turtle (e.g. position, orientation, ...)
	 */
	public abstract void clear();
	
	/**
	 * <p>Moves the turtle by the given distance. Positive values for 
	 * <code>distance</code> indicate forward moving, negative values indicate
	 * backward moving.</p>
	 * 
	 * @param distance
	 * 		The distance, the turtle is moved by. Positive values stand for
	 * 		forward moving, backward moving is indicated by negative values.
	 */
	public abstract void move( int distance );	
	
	/**
	 * <p>Turns this <code>Turtle</code> by the given angle. Positive values
	 * for <code>alpha</code> indicate clockwise rotation, negative values
	 * stand for counter-clockwise rotation</p>
	 * 
	 * @param alpha
	 * 		The angle in degrees, the turtle is rotated by. Positive values
	 * 		stand for clockwise rotation, negative values stand for counter-
	 * 		clockwise rotation.
	 */
	public abstract void turn( int alpha );
	
	/**
	 * Determines, wheather the turtle paints on the canvas while moving.
	 * 
	 * @param painting
	 * 		<code>true</code>, if the turtle shall paint while moving and
	 * 		<code>false</code> otherwise.
	 */
	public abstract void setPainting( boolean painting );
	
	/**
	 * Sets the pen color of this <code>Turtle</code>. 
	 *
	 * @param ColorID
	 * 		The color's ID, as specified by the user in a <code>color 
	 * 		&lt;id&gt;</code> command.
	 */
	public abstract void setColor(int ColorID);
}
