package logo;

public class Turtle {

	private static final int DEFAULT_X_POSITION 	= 200;
	private static final int DEFAULT_Y_POSITION 	= 200;
	
	private static final int MAX_X_POSITION			= 400;
	private static final int MIN_X_POSITION			= 0;
	private static final int MAX_Y_POSITION			= 400;
	private static final int MIN_Y_POSITION			= 0;
	
	private static final int DEFAULT_ORIENTATION 	= 0;
	
	private double xPosition;
	private double yPosition;
	private double orientation;
	
	private boolean painting;
	
	public Turtle() {
		
		reset();
	}
	
	public void reset() {
		xPosition = DEFAULT_X_POSITION;
		yPosition = DEFAULT_Y_POSITION;
		orientation = DEFAULT_ORIENTATION;
		
		painting = true;
	}
	
	public void move( int distance ) {
		
		xPosition += distance * Math.cos( Math.PI / 180 * orientation );
		yPosition += distance * Math.sin( Math.PI / 180 * orientation );
		
		if ( xPosition > MAX_X_POSITION ) 
			xPosition = xPosition - MAX_X_POSITION + MIN_X_POSITION;
		
		if ( xPosition < MIN_X_POSITION ) 
			xPosition = MAX_X_POSITION + xPosition - MIN_X_POSITION;
		
		if ( yPosition > MAX_Y_POSITION )
			yPosition = yPosition - MAX_Y_POSITION + MIN_Y_POSITION;
		
		if ( yPosition < MIN_Y_POSITION ) 
			yPosition = MAX_Y_POSITION + yPosition - MIN_Y_POSITION;
	}
	
	public void turn( int alpha ) {
		
		orientation += alpha;
		
		if ( orientation >= 360 )
			orientation = 360 - orientation;
	}
	
	public void setPainting( boolean painting ) {
		this.painting = painting;
	}
	
	public double getXPosition() {
		return xPosition;
	}
	
	public double getYPosition() {
		return yPosition;
	}
	
	public double getOrientation() {
		return orientation;
	}
	
	public boolean isPainting() {
		return painting;
	}
	
	@Override
	public String toString() {
		return "Turtle (X=" + (int) xPosition + ", Y=" + (int) yPosition + ", a=" + (int) orientation + ")";
	}
}
